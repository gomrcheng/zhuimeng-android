package com.chat.wktransfer.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import com.chat.base.base.WKBaseActivity;
import com.chat.base.config.WKConfig;
import com.chat.base.net.ICommonListener;
import com.chat.base.utils.StringUtils;
import com.chat.base.utils.WKToastUtils;
import com.chat.wktransfer.R;
import com.chat.wktransfer.databinding.ActivityTransferMoneyDetailBinding;
import com.chat.wktransfer.entity.TranferDetailBean;
import com.chat.wktransfer.entity.TransferGetEntity;
import com.chat.wktransfer.service.WKCommonModel;
import com.xinbida.wukongim.WKIM;

import java.util.HashMap;


/**
 * 转账详情
 */
public class TransferMoneyDetailActivity extends WKBaseActivity<ActivityTransferMoneyDetailBinding> {

    private String transfer_no;
    private boolean isMySend;// 转账人为我
    private String mToUserName;// 收账人昵称
    @Override
    protected ActivityTransferMoneyDetailBinding getViewBinding() {
        return ActivityTransferMoneyDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取转账详情

    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData() {
        super.initData();
        transfer_no = getIntent().getExtras().getString("transfer_no");
        isMySend = getIntent().getBooleanExtra("is_my_send",false);
        WKCommonModel.getInstance().transferGet(transfer_no, new WKCommonModel.ItransferGetListener() {
            @Override
            public void onResult(TransferGetEntity transferGetEntity) {
                mToUserName =  transferGetEntity.getToName();
                TranferDetailBean entity = new TranferDetailBean();
                entity.setTransfer_id(transferGetEntity.getNo());
                double fee = Double.parseDouble(StringUtils.fen2yuan(transferGetEntity.getAmount()));
                entity.setFee(fee);
                entity.setOperation_date(transferGetEntity.getReceiveAt());
                entity.setStart_date(transferGetEntity.getCreatedAt());
                entity.setEnd_date(transferGetEntity.getRefundAt());
                entity.setStatus(transferGetEntity.getStatus());
                setData(entity);
            }
        });
        wkVBinding.ivTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

      private void setData(TranferDetailBean data) {
          wkVBinding.tsSureBtn.setVisibility(View.GONE);
          wkVBinding.tsMoney.setText("￥" + data.getFee());
          wkVBinding.tsTime1Tv.setText(getString(R.string.transfer_time, data.getStart_date()));
          //"1 等待中 2 已领取 3 已退回 4 已自动退回"
        if (data.getStatus() == 1) {// 待领取
            wkVBinding.tsStatusIv.setImageResource(R.mipmap.ic_ts_status2);
            if (isMySend) {
                wkVBinding.tsTip1Tv.setText(getString(R.string.transfer_wait_receive1, mToUserName));
                wkVBinding.tsTip2Tv.setText(getString(R.string.transfer_receive_status1));
                wkVBinding.tsTip3Tv.setText(getString(R.string.transfer_receive_click_status1));
            } else {
                wkVBinding.tsSureBtn.setVisibility(View.VISIBLE);
                wkVBinding.tsTip1Tv.setText(getString(R.string.transfer_push_receive1));
                wkVBinding.tsTip2Tv.setText(getString(R.string.transfer_push_receive2));
                wkVBinding.tsSureBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WKCommonModel.getInstance().transferRec(data.getTransfer_id(), new ICommonListener() {
                            @Override
                            public void onResult(int code, String msg) {
                                if (code == 200) {
                                    WKToastUtils.getInstance().showToast(getString(R.string.string_jszzcg));
                                    updateLocalStatus();
                                    finish();
                                } else {
                                    WKToastUtils.getInstance().showToast(msg);
                                }
                            }
                        });
                    }
                });
            }
        } else if (data.getStatus() == 2) {// 已收钱
            updateLocalStatus();
            wkVBinding.tsStatusIv.setImageResource(R.mipmap.ic_ts_status1);
            if (isMySend) {
                wkVBinding.tsTip1Tv.setText(getString(R.string.transfer_wait_receive2, mToUserName));
                wkVBinding.tsTip2Tv.setText(getString(R.string.transfer_receive_status2));
                wkVBinding.tsTip3Tv.setVisibility(View.GONE);
            } else {
                wkVBinding.tsTip1Tv.setText(getString(R.string.transfer_push_receive3));
                wkVBinding.tsTip2Tv.setVisibility(View.GONE);
                wkVBinding.tsTip3Tv.setText(getString(R.string.transfer_receive_click_status2));
            }
            wkVBinding.tsTime2Tv.setText(getString(R.string.transfer_receive_time, data.getOperation_date()));
        } else {// 已退回
            updateLocalStatus();
            wkVBinding.tsStatusIv.setImageResource(R.mipmap.ic_ts_status3);
            wkVBinding.tsTip1Tv.setText(getString(R.string.transfer_wait_receive3));
            if (isMySend) {
                wkVBinding.tsTip2Tv.setText(getString(R.string.transfer_receive_status3));
                wkVBinding.tsTip3Tv.setText(getString(R.string.transfer_receive_click_status2));
            }
            wkVBinding.tsTime2Tv.setText(getString(R.string.transfer_out_time, data.getEnd_date()));
        }
    }
    private void updateLocalStatus(){
        String clientMsgNO = getIntent().getExtras().getString("messageId");
        HashMap hashMap = new HashMap<String, Integer>();
        hashMap.put("isOpen",0);
        try {
            WKIM.getInstance().getMsgManager().updateLocalExtraWithClientMsgNO(clientMsgNO, hashMap);
        } catch (Exception e) {
        }
    }

}
