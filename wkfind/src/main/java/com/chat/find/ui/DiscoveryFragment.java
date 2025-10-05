package com.chat.find.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chat.base.act.WKWebViewActivity;
import com.chat.base.base.WKBaseFragment;
import com.chat.base.config.WKApiConfig;
import com.chat.base.config.WKBinder;
import com.chat.base.config.WKConfig;
import com.chat.base.config.WKSharedPreferencesUtil;
import com.chat.base.endpoint.EndpointCategory;
import com.chat.base.endpoint.EndpointManager;
import com.chat.base.endpoint.EndpointSID;
import com.chat.base.endpoint.entity.ChatChooseContacts;
import com.chat.base.endpoint.entity.ChatViewMenu;
import com.chat.base.endpoint.entity.ChooseChatMenu;
import com.chat.base.endpoint.entity.PersonalInfoMenu;
import com.chat.base.entity.PopupMenuItem;
import com.chat.base.net.ICommonListener;
import com.chat.base.ui.Theme;
import com.chat.base.utils.WKDialogUtils;
import com.chat.base.utils.WKLogUtils;
import com.chat.base.utils.WKToastUtils;
import com.chat.base.utils.singleclick.SingleClickUtil;
import com.chat.find.R;
import com.chat.find.databinding.FragDiscoveryLayoutBinding;
import com.chat.find.entity.DiscoveryInfoMenu;
import com.chat.find.service.FindModel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

/**
 * 2019-11-12 14:58
 *
 */
public class DiscoveryFragment extends WKBaseFragment<FragDiscoveryLayoutBinding> {
    private DiscoveryItemAdapter adapter;

    TextView titleTv;

    ValueCallback<Uri> mUploadMessage;
    ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final int FILE_CHOOSER_RESULT_CODE = 101;

    private List<DiscoveryInfoMenu> endpoints = new ArrayList<>();

    public static int  addUrlIndex = 0;//浏览的url 是否增加

    @Override
    protected boolean isShowBackLayout() {
        return false;
    }

    @Override
    protected FragDiscoveryLayoutBinding getViewBinding() {
        return FragDiscoveryLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void refreshData(){
        //获取的数据
        //发现数据接口请求
        /**
         * sort_num: 排序
         * icon: 图标
         * name: 名称
         * web_route: 链接
         */
        endpoints.clear();
        endpoints.add(new DiscoveryInfoMenu(R.mipmap.icon_moments, getString(R.string.tab_text_monments), new PersonalInfoMenu.IPersonalInfoMenuClick() {
            @Override
            public void onClick() {
                EndpointManager.getInstance().invoke("start_monents",getActivity());
            }
        }));
        adapter.setList(endpoints);
        FindModel.getInstance().discovery(new ICommonListener() {
            @Override
            public void onResult(int code, String msg) {
                System.out.println("==sb:"+msg);
                try {
                    JSONArray resJson =JSONArray.parseArray(msg);
                    for (int i = 0; i < resJson.size(); i++) {
                        JSONObject jsonObject = resJson.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String icon = jsonObject.getString("icon");
                        String url = jsonObject.getString("web_route");
                        DiscoveryInfoMenu personalInfoMenu2 = new DiscoveryInfoMenu(WKApiConfig.baseUrl+icon,0,name,  new PersonalInfoMenu.IPersonalInfoMenuClick() {
                            @Override
                            public void onClick() {
//                                Toast.makeText(getContext(), "url:"+url, Toast.LENGTH_SHORT).show();
                                webViewOpen(url);
                            }
                        });
                        endpoints.add(personalInfoMenu2);
                    }
                    adapter.setList(endpoints);
                }catch (Exception e){
                    WKToastUtils.getInstance().showToast("获取发现页数据异常");
                } finally {
                    // 结束刷新动画
                    wkVBinding.swipeRefreshLayout.setRefreshing(false);
                }

            }
        });

    }



    @Override
    protected void initView() {


        EndpointManager.getInstance().setMethod("", EndpointCategory.wkRefreshMailList, object -> {
            endpoints.clear();
            try{
                endpoints.add(new DiscoveryInfoMenu(R.mipmap.icon_moments, requireContext().getString(R.string.tab_text_monments), new PersonalInfoMenu.IPersonalInfoMenuClick() {
                    @Override
                    public void onClick() {
                        WKSharedPreferencesUtil.getInstance().putInt(WKConfig.getInstance().getUid() + "_moments_msg_count", 0);
                        EndpointManager.getInstance().invoke("start_monents",getActivity());
                    }
                }));
                adapter.setList(endpoints);
                adapter.notifyDataSetChanged();
                System.out.println("===========redClikck");
            }catch (Exception e){

            }
            return null;
        });

        addUrlIndex=0;
        wkVBinding.recyclerView.setNestedScrollingEnabled(false);
        adapter = new DiscoveryItemAdapter(new ArrayList<>());
        initAdapter(wkVBinding.recyclerView, adapter);
        //设置数据item
        //固定朋友圈第一个位置
        // 初始化 SwipeRefreshLayout
        wkVBinding.swipeRefreshLayout.setOnRefreshListener(() -> {
            // 下拉刷新时执行的操作
            refreshData();
        });
        endpoints.clear();
        endpoints.add(new DiscoveryInfoMenu(R.mipmap.icon_moments, getString(R.string.tab_text_monments), new PersonalInfoMenu.IPersonalInfoMenuClick() {
            @Override
            public void onClick() {
                EndpointManager.getInstance().invoke("start_monents",getActivity());
            }
        }));
        adapter.setList(endpoints);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initListener() {
        adapter.setOnItemClickListener((adapter1, view, position) -> SingleClickUtil.determineTriggerSingleClick(view, view1 -> {
            DiscoveryInfoMenu menu = (DiscoveryInfoMenu) adapter1.getItem(position);
            if (menu != null && menu.iPersonalInfoMenuClick != null) {
                menu.iPersonalInfoMenuClick.onClick();
            }
        }));
        wkVBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                if (!TextUtils.isEmpty(s) && !"about:blank".equals(s) ) {
                    titleTv.setText(s);
                }
            }

            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i > 99) {
                    wkVBinding.progress.setVisibility(View.GONE);
//                    hideLoadingDialog();

                } else {
                    wkVBinding.progress.setVisibility(View.VISIBLE);
                    wkVBinding.progress.setProgress(i);
                }
            }

//            @Override
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//                mUploadMessage = uploadMsg;
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                i.addCategory(Intent.CATEGORY_OPENABLE);
//                i.setType("*/*");
//                WKWebViewActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILE_CHOOSER_RESULT_CODE);
//            }

            // For Android 5.0+
            public boolean onShowFileChooser(WebView webView, android.webkit.ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {

                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILE_CHOOSER_RESULT_CODE);
                return true;
            }

        });
    }


    public void webViewOpen(String url){
        wkVBinding.recyclerView.setVisibility(View.GONE);
        wkVBinding.progress.setVisibility(View.VISIBLE);
        wkVBinding.webView.setVisibility(View.VISIBLE);
        wkVBinding.moreIv.setVisibility(View.VISIBLE);
        wkVBinding.backTextView.setVisibility(View.VISIBLE);
        initWebViewSetting();
        if (!url.startsWith("http") && !url.startsWith("HTTP") && !url.startsWith("file"))
            url = "http://" + url;
//        wkVBinding.webView.loadUrl("file:///android_asset/web/report.html");
        if (url.equals(WKApiConfig.baseWebUrl + "report.html")) {
            String wk_theme_pref = Theme.getTheme();
            url = String.format("%s?uid=%s&token=%s&mode=%s", url, WKConfig.getInstance().getUid(), WKConfig.getInstance().getToken(), wk_theme_pref);
        }
        Log.e("加载的URL", url);
        wkVBinding.webView.loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSetting() {
        WebSettings webSettings = wkVBinding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setUseWideViewPort(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webSettings.setAppCacheEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false); // 禁止保存表单
        webSettings.setDomStorageEnabled(true);
//        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        //webSettings.setAllowFileAccess(true);
        webSettings.setAllowUniversalAccessFromFileURLs(false);
        webSettings.setAllowFileAccessFromFileURLs(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(0);
        }
        if (WKBinder.isDebug && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            android.webkit.WebView.setWebContentsDebuggingEnabled(true);
        }
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        wkVBinding.webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        // wkVBinding.webView.setBackgroundColor(ContextCompat.getColor(this, R.color.homeColor));
    }



}
