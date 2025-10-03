package com.chat.wkredpacket.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.chat.base.base.WKBaseActivity;
import com.chat.base.config.WKApiConfig;
import com.chat.base.glide.GlideRequestOptions;

import com.chat.base.utils.StringUtils;
import com.chat.wkredpacket.R;
import com.chat.wkredpacket.databinding.ActivityRedpacketDetailsBinding;
import com.chat.wkredpacket.entity.RedPacketDetailEntity;
import com.chat.wkredpacket.model.RedOneBean;
import com.chat.wkredpacket.service.WKCommonModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedDetailActivity extends WKBaseActivity<ActivityRedpacketDetailsBinding> {

    private List<RedOneBean> list;
    private RedDetailAdapter adapter;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected ActivityRedpacketDetailsBinding getViewBinding() {
        return ActivityRedpacketDetailsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setTitle(TextView titleTv) {
        titleTv.setText(getString(R.string.string_hbxq));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String redpacket_no = getIntent().getStringExtra("redpacket_no");
        list = new ArrayList<>();
        adapter = new RedDetailAdapter(this, list);
        wkVBinding.redDetailsLsv.setLayoutManager(new LinearLayoutManager(this));
        // 设置初始的适配器
        wkVBinding.redDetailsLsv.setAdapter(adapter);
        loadingPopup.show();
        loadingPopup.setTitle(getString(R.string.loading));
        //查看红包详情
        WKCommonModel.getInstance().getRedPacketDetail(redpacket_no, new WKCommonModel.IRedPacketDetailListener() {
            @Override
            public void onResult(RedPacketDetailEntity result) {
                String redPacketName = result.getBlessing();//红包名
                String uname = result.getSenderName();//发送人昵称
                String uid = result.getSenderUid();//发送人uid
                String fee = StringUtils.fen2yuan(result.getAmount());//红包总金额
                List<com.chat.wkredpacket.entity.Record> records =result.getRecords();
                List<RedOneBean> finishHistory = new ArrayList<>();
                for (int i = 0; i < records.size(); i++) {
                    RedOneBean redOneBean = new RedOneBean();
                    int openAmount = records.get(i).getOpen_amount();
                    String yuan = StringUtils.fen2yuan(openAmount);
                    redOneBean.setFee(yuan);
                    redOneBean.setUid(records.get(i).getUid());
                    redOneBean.setUname(records.get(i).getName());
                    redOneBean.setSave_date(simpleDateFormat.format(new Date(TimeUnit.SECONDS.toMillis(records.get(i).getOpen_time()))));
                    finishHistory.add(redOneBean);
                }
                wkVBinding.redNicknameTv.setText(getString(R.string.someone_s_red_packet_place_holder, uname));
                wkVBinding.redWordsTv.setText(redPacketName);
                wkVBinding.getMoneyTv.setText(String.valueOf(fee));
                Glide.with(RedDetailActivity.this).load(WKApiConfig.getAvatarUrl(uid))
                        .apply(GlideRequestOptions.getInstance().normalRequestOption())
                        .into(wkVBinding.avatarIv);
                // 更新数据列表
                list.clear();
                list.addAll(finishHistory);
                // 更新适配器
                adapter.notifyDataSetChanged();
                if(list.isEmpty()){
                    wkVBinding.tvContent.setText(getString(R.string.string_hbylq)+result.getOpenNum()+"/"+result.getTotalNum()+getString(R.string.string_sy)+StringUtils.fen2yuan(result.getBalance())+"/"+StringUtils.fen2yuan(result.getAmount())+getString(R.string.string_wlw));

                }else{
                    try {
                        // 获取红包开始时间和保存时间

                        // 设置UI显示
                        wkVBinding.tvContent.setText(getString(R.string.string_hbylq) + result.getOpenNum()+"/"+result.getTotalNum() +
                                getString(R.string.string_sy) + StringUtils.fen2yuan(result.getBalance())+"/"+StringUtils.fen2yuan(result.getAmount()) );
                    } catch (Exception e) {
                        // 异常处理
                        wkVBinding.tvContent.setText(getString(R.string.string_hbylq)  + result.getOpenNum() + "/" + result.getTotalNum() +
                                getString(R.string.string_sy) +  StringUtils.fen2yuan(result.getBalance()) + "/" + StringUtils.fen2yuan(result.getAmount()) + getString(R.string.string_hbylw));
                    }

                }
                loadingPopup.dismiss();
            }
        });
//        wkVBinding.titleLy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        wkVBinding.redBackTv.setOnClickListener(v ->{
            finish();
        });

    }

}
