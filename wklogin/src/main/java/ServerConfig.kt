package com.test.demo

import com.google.gson.annotations.SerializedName

/**
 * 服务器节点配置
 */
data class ServerNode(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
    @SerializedName("ssl") val ssl: String,
    @SerializedName("desc") val desc: String? = null
) {
    /**
     * 获取完整的服务器地址
     */
    fun getServerUrl(): String {
        val protocol = if (ssl.equals("true", ignoreCase = true)) "https" else "http"
        return "$protocol://$url"
    }
}

/**
 * 服务器配置响应
 */
data class ServerConfigResponse(
    @SerializedName("mainServer") val mainServer: ServerNode?,
    @SerializedName("backupServers") val backupServers: List<ServerNode>?
) {
    /**
     * 获取所有可用的服务器节点
     */
    fun getAllServers(): List<ServerNode> {
        val servers = mutableListOf<ServerNode>()
        mainServer?.let { servers.add(it) }
        backupServers?.let { servers.addAll(it) }
        return servers
    }
}
