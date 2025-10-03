package com.test.demo

import android.content.Context
import android.util.Log
import com.chat.base.config.WKApiConfig
import com.chat.base.config.WKSharedPreferencesUtil
import com.chat.base.endpoint.EndpointManager
import com.chat.base.net.RetrofitUtils
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.util.concurrent.TimeUnit

/**
 * 极简多线路管理器
 * JSON配置地址在TSApplication中管理
 */
object MultiRouteManager {
    
    private const val TAG = "MultiRoute"
    private const val TIMEOUT = 5000L // 增加到10秒
    
    private val gson = Gson()
    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)
        .build()


    suspend fun checkPrimaryUrlAndFallback(primaryUrl: String,context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                if (MultiRouteManager.isServerHealthy(primaryUrl)) {
                    MultiRouteManager.saveServerUrl(primaryUrl,context)
                    return@launch
                }

                // 主URL不可用，获取备用节点

                val jsonConfigUrl =  WKSharedPreferencesUtil.getInstance().getSP("api_config_json");
                val backupServerUrl = MultiRouteManager.getBestServerUrl(jsonConfigUrl)

                if (!backupServerUrl.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        WKApiConfig.initBaseURLIncludeIP(backupServerUrl)
                    }
                    MultiRouteManager.saveServerUrl(backupServerUrl,context)
                } else {
                    MultiRouteManager.saveServerUrl(primaryUrl,context)
                }

            } catch (e: Exception) {
                MultiRouteManager.saveServerUrl(primaryUrl,context)
            }
        }
    }
    
    /**
     * 异步获取最佳可用服务器地址
     * @param jsonUrl JSON配置地址
     */
    suspend fun getBestServerUrl(jsonUrl: String): String? = withContext(Dispatchers.IO) {
        try {
            Log.i(TAG, "开始获取JSON配置: $jsonUrl")
            
            val request = Request.Builder().url(jsonUrl).build()
            val response = httpClient.newCall(request).execute()
            
            if (!response.isSuccessful) {
                Log.w(TAG, "JSON请求失败: ${response.code}")
                return@withContext null
            }
            
            val json = response.body?.string()
            if (json.isNullOrEmpty()) {
                Log.w(TAG, "JSON响应为空")
                return@withContext null
            }
            
            Log.d(TAG, "JSON响应: $json")
            
            val config = gson.fromJson(json, ServerConfigResponse::class.java)
            
            // 构建所有可用服务器列表
            val allServers = mutableListOf<ServerNode>()
            
            // 主服务器优先
            config.mainServer?.let { server ->
                if (server.url.isNotBlank()) {
                    allServers.add(server)
                }
            }
            
            // 添加备用服务器
            config.backupServers?.forEach { server ->
                if (server.url.isNotBlank()) {
                    allServers.add(server)
                }
            }
            
            if (allServers.isEmpty()) {
                Log.w(TAG, "JSON中无有效服务器配置")
                return@withContext null
            }
            
            // 逐个检查服务器可用性并返回第一个可用的
            for ((index, server) in allServers.withIndex()) {
                val protocol = if (server.ssl == "true") "https" else "http"
                val serverUrl = "$protocol://${server.url}"
                
                val serverType = if (index == 0) "主服务器" else "备用服务器${index}"
                Log.i(TAG, "检查服务器可用性 [${index + 1}/${allServers.size}] $serverType: $serverUrl")
                
                if (isServerHealthy(serverUrl)) {
                    Log.i(TAG, "🎯 选中节点: [$serverType] $serverUrl")
                    return@withContext serverUrl
                } else {
                    Log.w(TAG, "❌ 节点不可用: [$serverType] $serverUrl")
                }
            }
            
            Log.e(TAG, "所有服务器都不可用，将返回主服务器作为默认选择")
            // 如果所有服务器都不可用，返回主服务器让应用自行处理
            config.mainServer?.let { server ->
                if (server.url.isNotBlank()) {
                    val protocol = if (server.ssl == "true") "https" else "http"
                    return@withContext "$protocol://${server.url}"
                }
            }
            
            null
            
        } catch (e: Exception) {
            Log.e(TAG, "获取JSON配置异常: ${e.message}")
            null
        }
    }
    
    /**
     * 检查服务器是否可用
     */
    suspend fun isServerHealthy(serverUrl: String): Boolean = withContext(Dispatchers.IO) {
        try {
            // 使用健康检查接口
            val healthCheckUrl = "$serverUrl/v1/common/appconfig"
            val request = Request.Builder()
                .url(healthCheckUrl)
                .build()
            
            val response = httpClient.newCall(request).execute()
            val isHealthy = response.isSuccessful
            
            Log.d(TAG, "服务器健康检查 $serverUrl: ${if (isHealthy) "✅ 可用" else "❌ 不可用 (${response.code})"}")
            isHealthy
            
        } catch (e: Exception) {
            Log.d(TAG, "服务器健康检查 $serverUrl: ❌ 异常 - ${e.message}")
            false
        }
    }
    
    /**
     * 获取服务器节点列表（不检测可用性）
     */
    suspend fun getServerNodes(jsonUrl: String): List<ServerNode> = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder().url(jsonUrl).build()
            val response = httpClient.newCall(request).execute()
            
            if (!response.isSuccessful) {
                return@withContext emptyList()
            }
            
            val json = response.body?.string()
            if (json.isNullOrEmpty()) {
                return@withContext emptyList()
            }
            
            val config = gson.fromJson(json, ServerConfigResponse::class.java)
            val allServers = mutableListOf<ServerNode>()
            
            config.mainServer?.let { server ->
                if (server.url.isNotBlank()) {
                    allServers.add(server)
                }
            }
            
            config.backupServers?.forEach { server ->
                if (server.url.isNotBlank()) {
                    allServers.add(server)
                }
            }
            
            return@withContext allServers
            
        } catch (e: Exception) {
            Log.e(TAG, "获取服务器节点列表异常: ${e.message}")
            emptyList()
        }
    }
    
    /**
     * 测试服务器延迟（毫秒）
     */
    suspend fun testServerPing(serverUrl: String): Int = withContext(Dispatchers.IO) {
        // 重试3次
        repeat(3) { attempt ->
            try {
                val startTime = System.currentTimeMillis()
                val healthCheckUrl = "$serverUrl/v1/common/appconfig"
                val request = Request.Builder()
                    .url(healthCheckUrl)
                    .header("User-Agent", "Android-Client")
                    .build()
                
                val response = httpClient.newCall(request).execute()
                val endTime = System.currentTimeMillis()
                
                // 200成功，404说明服务器可达但接口不存在，都算有效
                if (response.isSuccessful || response.code == 404) {
                    val ping = (endTime - startTime).toInt()
                    Log.d(TAG, "服务器延迟测试 $serverUrl: ${ping}ms (尝试${attempt + 1})")
                    response.close()
                    return@withContext ping
                } else {
                    Log.d(TAG, "服务器延迟测试 $serverUrl: HTTP ${response.code} (尝试${attempt + 1})")
                    response.close()
                }
                
                if (attempt < 2) { // 不是最后一次，等待后重试
                    delay(300) // 等待300ms后重试
                }
                
            } catch (e: Exception) {
                Log.d(TAG, "服务器延迟测试 $serverUrl: 异常 - ${e.message} (尝试${attempt + 1})")
                if (attempt < 2) { // 不是最后一次，等待后重试
                    delay(300)
                }
            }
        }
        
        Log.w(TAG, "服务器延迟测试 $serverUrl: 所有尝试失败")
        return@withContext -1
    }
    
    /**
     * 保存服务器地址到缓存
     */
    fun saveServerUrl(serverUrl: String,context: Context) {
        var url = serverUrl.replace("/web/", "")
        WKApiConfig.initBaseURLIncludeIP(url)
        WKSharedPreferencesUtil.getInstance().putSP("api_base_url", url)
        EndpointManager.getInstance().invoke("update_base_url", serverUrl)
        RetrofitUtils.getInstance().clearRetrofit();//直接清理掉链接缓存重置
        Log.i(TAG, "服务器地址已保存: $url")
    }
}
