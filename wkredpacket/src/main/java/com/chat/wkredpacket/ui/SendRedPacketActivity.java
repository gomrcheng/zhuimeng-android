package com.chat.wkredpacket.ui;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.chat.base.base.WKBaseActivity;
import com.chat.base.config.WKConstants;
import com.chat.base.endpoint.EndpointCategory;
import com.chat.base.endpoint.EndpointManager;
import com.chat.base.net.HttpResponseCode;
import com.chat.base.utils.WKDialogUtils;
import com.chat.base.utils.WKToastUtils;
import com.chat.wkredpacket.R;
import com.chat.wkredpacket.databinding.ActivityRedpacketBinding;
import com.chat.wkredpacket.service.WKCommonModel;
import com.chat.wkredpacket.utils.FragmentViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.xinbida.wukongim.WKIM;
import com.xinbida.wukongim.entity.WKChannel;
import com.xinbida.wukongim.entity.WKChannelType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class SendRedPacketActivity extends WKBaseActivity<ActivityRedpacketBinding> {

    private List<String> mTitleList = new ArrayList<>();
    private ArrayList<Fragment> fragments;

    @Override
    protected ActivityRedpacketBinding getViewBinding() {
        return ActivityRedpacketBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initData() {
        super.initData();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //钱包状态前置判断
        loadingPopup.show();
        loadingPopup.setTitle(getString(R.string.loading));
        WKCommonModel.getInstance().getWalletInfo(new WKCommonModel.IWalletInfoListener() {
            @Override
            public void onResult(int amount, int status, int password_is_set) {
                loadingPopup.dismiss();
                if(password_is_set == 0 && status == 1){
                    //进入设置支付密码
                    WKDialogUtils.getInstance().showDialog(SendRedPacketActivity.this, "设置支付密码", "您还未设置支付密码，确定进入设置支付密码后使用改功能", true, "", "确定", 0, ContextCompat.getColor(SendRedPacketActivity.this, R.color.red), index -> {
                        if (index == 1) {
                            WKToastUtils.getInstance().showToast("请先设置支付密码");
                            EndpointManager.getInstance().invoke("set_pay_password",SendRedPacketActivity.this);
                        }
                        finish();
                    });

                }else if(status == 0){
                    WKToastUtils.getInstance().showToast("钱包状态异常");
                    finish();
                }
            }
        });

        wkVBinding.tvTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fragments = new ArrayList<>();
        String channelId = getIntent().getStringExtra("channelId");
        //频道类型
        byte channelType = getIntent().getByteExtra("channelType", WKChannelType.PERSONAL);
        if(channelType != WKChannelType.PERSONAL){
            wkVBinding.tvTitleCenter.setText("群红包");
            mTitleList.add("拼手气");
            mTitleList.add("普通");
            SqRedPacketFragment sqRedPacketFragment = new SqRedPacketFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString("channelId",channelId);
            bundle2.putByte("channelType",channelType);
            sqRedPacketFragment.setArguments(bundle2);
            fragments.add(sqRedPacketFragment);
            PtRedPacketFragment ptRedPacketFragment =  new PtRedPacketFragment();
            Bundle bundle = new Bundle();
            bundle.getBoolean("showCount",true);
            bundle.putString("channelId",channelId);
            bundle.putByte("channelType",channelType);
            ptRedPacketFragment.setArguments(bundle);
            fragments.add(ptRedPacketFragment);
        }else{
            wkVBinding.tvTitleCenter.setText("个人红包");
            wkVBinding.redpacketTablLl.setVisibility(View.GONE);
            mTitleList.add("");
            PtRedPacketFragment ptRedPacketFragment =  new PtRedPacketFragment();
            Bundle bundle = new Bundle();
            bundle.getBoolean("showCount",false);
            bundle.putString("channelId",channelId);
            bundle.putByte("channelType",channelType);
            ptRedPacketFragment.setArguments(bundle);
            fragments.add(ptRedPacketFragment);
        }
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments, mTitleList);
        wkVBinding.viewpagertRedpacket.setAdapter(adapter);
        wkVBinding.viewpagertRedpacket.setOffscreenPageLimit(fragments.size());
        wkVBinding.viewpagertRedpacket.setCurrentItem(0, false);
        wkVBinding.tabSegment.setupWithViewPager(wkVBinding.viewpagertRedpacket, false);
        wkVBinding.tvTitleLeft.setOnClickListener( v -> {finish();});


        TabLayout tabLayout = findViewById(R.id.tab_segment);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                TextView customView = (TextView) getLayoutInflater().inflate(R.layout.custom_tab_item, null);
                customView.setText(tab.getText());
                tab.setCustomView(customView);
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    TextView textView = (TextView) tab.getCustomView();
                    textView.setSelected(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    TextView textView = (TextView) tab.getCustomView();
                    textView.setSelected(false);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

}
