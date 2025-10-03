package com.chat.wkwallet.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.chat.base.base.WKBaseActivity;
import com.chat.base.net.ICommonListener;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActMianmiLayoutBinding;
import com.chat.wkwallet.entity.GetPayTokenEntity;
import com.chat.wkwallet.entity.WalletPasswordFreeItemsEntity;
import com.chat.wkwallet.service.WKCommonModel;
import com.chat.wkwallet.utils.PayPasswordVerifyDialog;

import java.util.List;

/**
 * 我的钱包
 */

public class MianmiActivity extends WKBaseActivity<ActMianmiLayoutBinding> {

    @Override
    protected void onResume() {
        super.onResume();
        wkVBinding.switchRedPacket.setOnCheckedChangeListener(null);
        wkVBinding.switchTransfer.setOnCheckedChangeListener(null);
        WKCommonModel.getInstance().getwalletpasswordfreeitems(new WKCommonModel.IGetwalletpasswordfreeitemsListener() {
            @Override
            public void onResult(List<WalletPasswordFreeItemsEntity> result) {
                for (int i = 0; i < result.size(); i++) {
                    String title = result.get(i).getTitle();
                    Long open = result.get(i).getOpen();
                    if(title.equals("红包免密")){
                        wkVBinding.switchRedPacket.setChecked(open == 1);
                    }
                    if(title.equals("转账免密")){
                        wkVBinding.switchTransfer.setChecked(open == 1);
                    }
                }
            }
        });
        wkVBinding.switchRedPacket.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!buttonView.isPressed()) {
                return;
            }
            // 先恢复原来的状态，等接口调用完成后再更新
            boolean originalState = !isChecked;
            wkVBinding.switchRedPacket.setChecked(originalState);
            if(isChecked){
                PayPasswordVerifyDialog dialog = new PayPasswordVerifyDialog(MianmiActivity.this);
                dialog.setAction(getString(R.string.mianmi_pay));
                dialog.setMoney("");
                dialog.setOnInputFinishListener(password -> {
                    JSONObject request = new JSONObject();
                    request.put("amount", 0);
                    request.put("item","");
                    request.put("password", password);
                    WKCommonModel.getInstance().getpaytoken(request, new WKCommonModel.IGetPayTokenListener() {
                        @Override
                        public void onResult(GetPayTokenEntity result) {
                            loadingPopup.show();
                            loadingPopup.setTitle(getString(R.string.loading));
                            String payToken = result.getPay_token();
                            JSONObject request = new JSONObject();
                            request.put("pay_token", payToken);
                            request.put("item","redpacket");
                            WKCommonModel.getInstance().setPasswordfree("open",request, new ICommonListener() {
                                @Override
                                public void onResult(int code, String msg) {
                                    loadingPopup.dismiss();
                                    if(code == 200){
                                        wkVBinding.switchRedPacket.setChecked(true);
                                    }else{
                                        // 失败时恢复开关原来的状态
                                        wkVBinding.switchRedPacket.setChecked(false);
                                        showToast(msg);
                                    }
                                }
                            });
                        }
                    });
                });
                dialog.show();
            }else{
                JSONObject request = new JSONObject();
                request.put("item","redpacket");
                WKCommonModel.getInstance().setPasswordfree("close",request, new ICommonListener() {
                    @Override
                    public void onResult(int code, String msg) {
                        loadingPopup.dismiss();
                        if(code == 200){
                            wkVBinding.switchRedPacket.setChecked(false);
                        }else{
                            // 失败时恢复开关原来的状态
                            wkVBinding.switchRedPacket.setChecked(true);
                            showToast(msg);
                        }
                    }
                });
            }
        });

        wkVBinding.switchTransfer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!buttonView.isPressed()) {
                return;
            }
            // 先恢复原来的状态，等接口调用完成后再更新
            boolean originalState = !isChecked;
            wkVBinding.switchTransfer.setChecked(originalState);
            if(isChecked){
                PayPasswordVerifyDialog dialog = new PayPasswordVerifyDialog(MianmiActivity.this);
                dialog.setAction(getString(R.string.mianmi_pay));
                dialog.setMoney("");
                dialog.setOnInputFinishListener(password -> {
                    JSONObject request = new JSONObject();
                    request.put("amount", 0);
                    request.put("item","");
                    request.put("password", password);
                    WKCommonModel.getInstance().getpaytoken(request, new WKCommonModel.IGetPayTokenListener() {
                        @Override
                        public void onResult(GetPayTokenEntity result) {
                            loadingPopup.show();
                            loadingPopup.setTitle(getString(R.string.loading));
                            String payToken = result.getPay_token();
                            JSONObject request = new JSONObject();
                            request.put("pay_token", payToken);
                            request.put("item","transfer");
                            WKCommonModel.getInstance().setPasswordfree("open",request, new ICommonListener() {
                                @Override
                                public void onResult(int code, String msg) {
                                    loadingPopup.dismiss();
                                    if(code == 200){
                                        wkVBinding.switchTransfer.setChecked(true);
                                    }else{
                                        showToast(msg);
                                    }
                                }
                            });
                        }
                    });
                });
                dialog.show();
            }else{
                JSONObject request = new JSONObject();
                request.put("item","transfer");
                WKCommonModel.getInstance().setPasswordfree("close",request, new ICommonListener() {
                    @Override
                    public void onResult(int code, String msg) {
                        loadingPopup.dismiss();
                        if(code == 200){
                            wkVBinding.switchTransfer.setChecked(false);
                        }else{
                            showToast(msg);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected ActMianmiLayoutBinding getViewBinding() {
        return ActMianmiLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected String getRightTvText(TextView textView) {
        return "";
    }

    @Override
    protected void setTitle(TextView titleTv) {
        super.setTitle(titleTv);
        titleTv.setText(getString(R.string.mianmi_pay));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
