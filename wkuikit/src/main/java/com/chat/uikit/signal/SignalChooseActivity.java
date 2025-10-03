package com.chat.uikit.signal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chat.base.WKBaseApplication;
import com.chat.base.base.WKBaseActivity;
import com.chat.base.config.WKApiConfig;
import com.chat.base.config.WKConfig;
import com.chat.base.config.WKSharedPreferencesUtil;
import com.chat.base.endpoint.EndpointManager;
import com.chat.base.glide.GlideUtils;
import com.chat.base.net.RetrofitUtils;
import com.chat.base.utils.WKToastUtils;
import com.chat.uikit.R;
import com.chat.uikit.databinding.ActMyHeadPortraitLayoutBinding;
import com.chat.uikit.databinding.ActSingalChooseLayoutBinding;
import com.xinbida.wukongim.WKIM;
import com.xinbida.wukongim.entity.WKChannel;
import com.xinbida.wukongim.entity.WKChannelType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SignalChooseActivity extends WKBaseActivity<ActSingalChooseLayoutBinding> implements SignalNodeAdapter.OnSignalNodeClickListener {
    @Override
    protected ActSingalChooseLayoutBinding getViewBinding() {
        return ActSingalChooseLayoutBinding.inflate(getLayoutInflater());
    }
    @Override
    protected void setTitle(TextView titleTv) {
        titleTv.setText("选择节点");
    }

    private SignalNodeAdapter adapter;
    private List<SignalNode> nodeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupRecyclerView();
    }


    private void setupRecyclerView() {
        adapter = new SignalNodeAdapter(nodeList);
        adapter.setOnNodeClickListener(this); // 设置监听器
        wkVBinding.recyclerViewNodes.setLayoutManager(new LinearLayoutManager(this));
        wkVBinding.recyclerViewNodes.setAdapter(adapter);
        fetchServerTime();
    }


    private void fetchServerTime() {
        // 使用项目中已有的网络请求方式
        // 这里假设你使用的是你项目中的网络库
        try {
            // 示例：使用一个提供时间戳的公共API
            String url = WKApiConfig.lineConfig;
            // 如果你有项目中的网络请求工具类，可以这样调用
            // 以下是一个示例实现，你需要根据实际的网络库进行调整

            // 示例使用OKHttp（如果项目中有使用）
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    runOnUiThread( () -> {
                        WKToastUtils.getInstance().showToast("拉取可用节点配置json失败");
                    });
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    String responseData = response.body().string();
                    JSONObject resJson = JSONObject.parseObject(responseData);
                    JSONArray backupServers = resJson.getJSONArray("backupServers");
                    JSONObject mainServer = resJson.getJSONObject("mainServer");
                    String mainUrl = mainServer.getString("url");
                    String mainName = mainServer.getString("name");
                    String mainDesc = mainServer.getString("desc");
                    String mainSsl = mainServer.getString("ssl");
                    if(mainSsl.equals("true")){
                        mainUrl = "https://"+mainUrl;
                    }else{
                        mainUrl = "http://"+mainUrl;
                    }
                    fetchServerTime2(mainUrl,mainName,mainDesc);
                    for (int i = 0; i < backupServers.size(); i++) {
                        JSONObject jsonObject = backupServers.getJSONObject(i);
                        String url = jsonObject.getString("url");
                        String name = jsonObject.getString("name");
                        String desc = jsonObject.getString("desc");
                        String ssl = jsonObject.getString("ssl");
                        if(ssl.equals("true")){
                            url = "https://"+url;
                        }else{
                            url = "http://"+url;
                        }
                        fetchServerTime2(url,name,desc);
                    }
                    response.close();
                }
            });


        } catch (Exception e) {
            android.util.Log.e("ChatFragment", "时间请求异常: " + e.getMessage());
        }
    }

    private void fetchServerTime2(String url, String name, String desc) {
        new Thread(() -> {
            try {
                long serverTimeMillisStar = System.currentTimeMillis();

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                // 同步执行请求
                okhttp3.Response response = client.newCall(request).execute();

                long serverTimeMillis = System.currentTimeMillis() - serverTimeMillisStar;

                if (response.body() != null) {
                    response.close();
                }

                // 切换到主线程更新UI
                runOnUiThread(() -> {
                    String ssl = "";
                    if(url.contains("https")){
                        ssl = "true";
                    }else{
                        ssl = "false";
                    }
                    SignalNode signalNode = new SignalNode(name,
                            serverTimeMillis + "ms", desc, url,ssl);
                    nodeList.add(signalNode);
                    if (adapter != null) {
                        adapter.notifyItemInserted(nodeList.size() - 1);
                    }
                });
            } catch (Exception e) {
//                android.util.Log.e("SignalChooseActivity", "时间请求异常: " + e.getMessage());
//                // 错误处理
//                runOnUiThread(() -> {
//                    SignalNode signalNode = new SignalNode(name,
//                            "超时", desc, url);
//                    nodeList.add(signalNode);
//                    if (adapter != null) {
//                        adapter.notifyItemInserted(nodeList.size() - 1);
//                    }
//                });
            }
        }).start();
    }

    @Override
    public void onNodeClick(SignalNode node, int position) {
        //切换线路
        loadingPopup.show();
        loadingPopup.setTitle(getString(R.string.loading));
        new Handler(Objects.requireNonNull(Looper.myLooper())).postDelayed(() -> {
            WKApiConfig.initBaseURLIncludeIP(node.getRealLine());
            WKSharedPreferencesUtil.getInstance().putSP("api_base_url", node.getRealLine());
            EndpointManager.getInstance().invoke("update_base_url", node.getRealLine());
            RetrofitUtils.getInstance().clearRetrofit();//直接清理掉链接缓存重置
            finish();
//            EndpointManager.getInstance().invoke("wk_logout", null);
//            WKConfig.getInstance().clearInfo();
//            WKIM.getInstance().getConnectionManager().disconnect(true);
//            WKSharedPreferencesUtil.getInstance().putSP("qiehuan","1");
//            WKSharedPreferencesUtil.getInstance().putSP("first_chat","0");
//            //关闭UI层数据库
//            loadingPopup.dismiss();
        }, 1000);
    }
}
