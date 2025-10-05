package com.chat.find.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.chat.base.act.WKWebViewActivity;
import com.chat.base.base.WKBaseFragment;
import com.chat.base.config.WKApiConfig;
import com.chat.base.utils.WKDialogUtils;
import com.chat.base.utils.singleclick.SingleClickUtil;
import com.chat.find.WKFind;
import com.chat.find.databinding.FragFindLayoutBinding;
import com.chat.find.entity.WkFindBanner;
import com.chat.find.entity.WkFindList;
import com.chat.find.service.FindContract;
import com.chat.find.service.FindPresenter;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.RectangleIndicator;
import java.util.ArrayList;
import java.util.List;

/**
 * 发现
 */
public class FindFragment extends WKBaseFragment<FragFindLayoutBinding> implements FindContract.FindView {
    private FindItemAdapter adapter;
    private BannerImageAdapter bannerImageAdapter = null;//banner 适配器

    private FindPresenter presenter = null;


    @Override
    protected FragFindLayoutBinding getViewBinding() {
        return FragFindLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        String title = WKFind.getInstance().getFindConfig().getExploreTitle();//模块标题
        int hasBanner = WKFind.getInstance().getFindConfig().getHasBanner();//是否有banner
        int hasFindList = WKFind.getInstance().getFindConfig().getHasFindList();//是否有list
        hasBanner = 1;
        hasFindList = 1;
        if(!TextUtils.isEmpty(title)){
            wkVBinding.llFindTitle.setVisibility(View.VISIBLE);
            wkVBinding.tvFindTitle.setText(title);
        }else{
            wkVBinding.llFindTitle.setVisibility(View.GONE);
        }
        if(hasBanner == 1){
            wkVBinding.headerBanner.setVisibility(View.VISIBLE);
            presenter.getBannerList();
        }else {
            wkVBinding.headerBanner.setVisibility(View.GONE);
        }
        adapter = new FindItemAdapter(new ArrayList<>());
        initAdapter(wkVBinding.recyclerView, adapter);
        if(hasFindList == 1){
            wkVBinding.recyclerView.setVisibility(View.VISIBLE);
            presenter.getFindList();
        }else {
            wkVBinding.recyclerView.setVisibility(View.GONE);
        }
    }

    private void initBanner(List<WkFindBanner> list){
        if(null == bannerImageAdapter){
            wkVBinding.headerBanner.setIndicator(new RectangleIndicator(getContext()));
            bannerImageAdapter = new BannerImageAdapter<>(list) {
                @Override
                public void onBindView(BannerImageHolder holder, WkFindBanner data, int position, int size) {
                    Glide.with(holder.itemView)
                            .load(WKApiConfig.getShowUrl(data.getCover()))
                            .into(holder.imageView);
                }
            };
            wkVBinding.headerBanner.setAdapter(bannerImageAdapter);
        }else{
            wkVBinding.headerBanner.setDatas(list);
        }
        wkVBinding.headerBanner.setOnBannerListener((data, position) -> {
            if (data != null) {
               showWebView(list.get(position).getRoute());
            }
        });
    }

    private void showWebView(String url){
        Intent intent = new Intent(getActivity(), WKWebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    protected void initPresenter() {
        presenter =  new FindPresenter(this);
    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener((adapter1, view, position) -> SingleClickUtil.determineTriggerSingleClick(view, view1 -> {
            WkFindList findBean = adapter.getData().get(position);
            if(findBean.getHas_encrypt() == 1){
                //有密码需要弹框验证
                String password = findBean.getPassword();
                FindCodeDialog findCodeDialog = new FindCodeDialog(getContext());
                findCodeDialog.setTitleDialog("请输入密码");
                findCodeDialog.setOnConfirmListener(text -> {
                    if(TextUtils.isEmpty(text)){
                        showTipDialog("密码不能为空");
                    }else{
                        if(text.equals(password)){
                            findCodeDialog.cancel();
                            showWebView(findBean.getWeb_route());
                        }else{
                            showTipDialog("输入密码不正确");
                        }
                    }
                });
                findCodeDialog.setOnCancelListener(() -> {
                    findCodeDialog.cancel();
                });
                findCodeDialog.show();
            }else{
                showWebView(findBean.getWeb_route());
            }
        }));
    }


    private void showTipDialog(String content){
        WKDialogUtils.getInstance().showSingleBtnDialog(getContext(), "", content, "", null);

    }
    @Override
    protected void initData() {

    }


    @Override
    protected void setTitle(TextView titleTv) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showError(String msg) {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void bannerResult(List<WkFindBanner> list) {
        initBanner(list);
    }

    @Override
    public void FindResult(List<WkFindList> list) {
            //设置数据item
            adapter.setList(list);
    }
}
