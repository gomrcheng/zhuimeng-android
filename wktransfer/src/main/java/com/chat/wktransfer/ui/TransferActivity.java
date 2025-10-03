package com.chat.wktransfer.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.chat.base.base.WKBaseActivity;
import com.chat.base.endpoint.EndpointManager;
import com.chat.base.net.ICommonListener;
import com.chat.base.utils.StringUtils;
import com.chat.base.utils.WKDialogUtils;
import com.chat.base.utils.WKToastUtils;
import com.chat.wktransfer.R;
import com.chat.wktransfer.databinding.ActSendTransferLayoutBinding;
import com.chat.wktransfer.entity.GetPayTokenEntity;
import com.chat.wktransfer.entity.PassworedfreeItemEntity;
import com.chat.wktransfer.service.WKCommonModel;
import com.chat.wktransfer.utils.KeyBoad;
import com.chat.wktransfer.utils.TransferPayPasswordVerifyDialog;
import com.chat.wktransfer.utils.VerifyDialog;
import com.xinbida.wukongim.WKIM;
import com.xinbida.wukongim.entity.WKChannel;


/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class TransferActivity extends WKBaseActivity<ActSendTransferLayoutBinding> {

    private final int redPacketType=2;
    double money = 0;
    private String remark="";
    private KeyBoad keyBoad;
    private boolean isUiCreat = false;
    String uid;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        isUiCreat = true;
    }

    @Override
    protected ActSendTransferLayoutBinding getViewBinding() {
        return ActSendTransferLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setTitle(TextView titleTv) {
        titleTv.setText(getString(R.string.string_zz));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        loadingPopup.setTitle(getString(R.string.loading));
        loadingPopup.show();
        WKCommonModel.getInstance().getWalletInfo(new WKCommonModel.IWalletInfoListener() {
            @Override
            public void onResult(int amount, int status, int password_is_set) {
                loadingPopup.dismiss();
                if(password_is_set == 0 && status == 1){
                    //进入设置支付密码
                    WKDialogUtils.getInstance().showDialog(TransferActivity.this, "设置支付密码", "您还未设置支付密码，确定进入设置支付密码后使用改功能", true, "", "确定", 0, ContextCompat.getColor(TransferActivity.this, R.color.red), index -> {
                        if (index == 1) {
                            WKToastUtils.getInstance().showToast("请先设置支付密码");
                            EndpointManager.getInstance().invoke("set_pay_password",TransferActivity.this);
                        }
                        finish();
                    });
                }else if(status == 0){
                    WKToastUtils.getInstance().showToast("钱包状态异常");
                    finish();
                }

            }
        });





        uid = getIntent().getStringExtra("channelId");
        byte channelType =  getIntent().getExtras().getByte("channelType");
        String name = getIntent().getStringExtra("transferName");
        WKChannel channel = WKIM.getInstance().getChannelManager().getChannel(uid, channelType);
        if (channel != null) {
            String avatarKey = channel.avatarCacheKey;
            wkVBinding.tmIv.showAvatar(uid, channelType, avatarKey);
        }
        wkVBinding.tvName.setText(name);
        keyBoad = new KeyBoad(this, getWindow().getDecorView(), wkVBinding.etMoney);
        initKeyBoad();

        wkVBinding.etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                int posDot = s.indexOf(".");
                if (posDot > 0 && (s.length() - posDot - 1 > 2)) {
                    editable.delete(posDot + 3, posDot + 4);
                }
                if (editable.toString().equals("")) {
                    money = 0;
                } else {
                    try {
                        money = Double.parseDouble(editable.toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        wkVBinding.transferEditDescTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyDialog verifyDialog = new VerifyDialog(TransferActivity.this);
                verifyDialog.setVerifyClickListener(getString(R.string.string_tjzzsm), getString(R.string.string_max10text),
                        remark, 10, new VerifyDialog.VerifyClickListener() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void send(String str) {
                                remark = str;
                                if (TextUtils.isEmpty(remark)) {
                                    wkVBinding.transferDescTv.setText("");
                                    wkVBinding.transferDescTv.setVisibility(View.GONE);
                                    wkVBinding.transferEditDescTv.setText(getString(R.string.string_tjzzsm));
                                } else {
                                    wkVBinding.transferDescTv.setText(str);
                                    wkVBinding.transferDescTv.setVisibility(View.VISIBLE);
                                    wkVBinding.transferEditDescTv.setText(getString(R.string.string_xiugai));
                                }
                                keyBoad.show();
                            }

                        });
                verifyDialog.setOkButton(R.string.sure);
                keyBoad.dismiss();
                Window window = verifyDialog.getWindow();

                if (window != null) {
                    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); // 软键盘弹起
                }
                verifyDialog.show();
            }
        });
        wkVBinding.btCommit.setOnClickListener(v -> {
            //获取红包项目是否免密
            loadingPopup.show();
            loadingPopup.setTitle(getString(R.string.loading));
            WKCommonModel.getInstance().getpasswordfreeopen("transfer", new WKCommonModel.IGetpasswordfreeopenListener() {
                @Override
                public void onResult(PassworedfreeItemEntity passworedfreeItemEntity) {
                    if(loadingPopup!=null){
                        loadingPopup.dismiss();
                    }
                    //如果是免密就不需要弹出支付密码
                    if(passworedfreeItemEntity.getOpen() == 1){
                        JSONObject request = new JSONObject();
                        request.put("amount", StringUtils.yuan2fen(wkVBinding.etMoney.getText().toString()));
                        request.put("item","transfer");
                        request.put("password", "");
                        WKCommonModel.getInstance().getpaytoken(request, new WKCommonModel.IGetPayTokenListener() {
                            @Override
                            public void onResult(GetPayTokenEntity result) {
                                if(result!=null && result.getPay_token()!=null){
                                    String payToken = result.getPay_token();
                                    //创建转账
                                    createTransfers(payToken,Integer.parseInt(request.getString("amount")),uid,wkVBinding.transferEditDescTv.getText().toString());
                                }
                                if(loadingPopup!=null){
                                    loadingPopup.dismiss();
                                }
                            }
                        });
                    }else{
                        TransferPayPasswordVerifyDialog dialog = new TransferPayPasswordVerifyDialog(TransferActivity.this);
                        dialog.setAction(getString(R.string.string_zz));
                        dialog.setMoney(money+"");
                        dialog.setOnInputFinishListener(password -> {
                            JSONObject request = new JSONObject();
                            request.put("amount", StringUtils.yuan2fen(wkVBinding.etMoney.getText().toString()));
                            request.put("item","transfer");
                            request.put("password", password);
                            WKCommonModel.getInstance().getpaytoken(request, new WKCommonModel.IGetPayTokenListener() {
                                @Override
                                public void onResult(GetPayTokenEntity result) {
                                    if(result!=null && result.getPay_token()!=null){
                                        String payToken = result.getPay_token();
                                        //创建转账
                                        createTransfers(payToken,Integer.parseInt(request.getString("amount")),uid,wkVBinding.transferEditDescTv.getText().toString());
                                    }
                                    if(loadingPopup!=null){
                                        loadingPopup.dismiss();
                                    }
                                }
                            });

                        });
                        try {
                            dialog.show();
                        } catch (Exception ignored) {
                            // 线程切换可能导致弹对话框时activity已经关闭，show会抛异常，
                        }
                    }
                }
            });
        });
    }

    private void createTransfers(String payToken, int amount, String uid, String remark) {
        WKCommonModel.getInstance().createtransfers(payToken, amount, uid, remark, new ICommonListener() {
            @Override
            public void onResult(int code, String msg) {
                if(code == 200){
                    if(loadingPopup!=null){
                        loadingPopup.dismiss();
                    }
                    finish();
                }
            }
        });
    }

    private void initKeyBoad() {
        wkVBinding.etMoney.setFocusable(true);
        wkVBinding.etMoney.setOnFocusChangeListener((v, hasFocus) -> {
            if (keyBoad != null && isUiCreat) {
                keyBoad.refreshKeyboardOutSideTouchable(!hasFocus);
            } else if (isUiCreat) {
                keyBoad.show();
            }
            if (hasFocus) {
                wkVBinding.etMoney.post(() -> {
                    keyBoad.show();
                });
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(wkVBinding.etMoney.getWindowToken(), 0);
            }
        });

        wkVBinding.etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (text.startsWith(".")) {
                    wkVBinding.etMoney.setText("0" + text);
                } else if (text.startsWith("0") && !text.contains(".") && text.length() > 1) {
                    wkVBinding.etMoney.setText(text.substring(1, text.length()));
                }

            }
        });

        wkVBinding.etMoney.setOnClickListener(v -> {
            if (keyBoad != null) {
                keyBoad.show();
            }
        });
    }
}
