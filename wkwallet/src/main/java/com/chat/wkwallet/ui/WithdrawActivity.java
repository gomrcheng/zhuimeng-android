package com.chat.wkwallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.chat.base.base.WKBaseActivity;
import com.chat.base.net.ICommonListener;
import com.chat.base.utils.StringUtils;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActWithdrawLayoutBinding;
import com.chat.wkwallet.entity.BankCardDetailEntity;
import com.chat.wkwallet.entity.GetPayTokenEntity;
import com.chat.wkwallet.entity.NameAuthEntity;
import com.chat.wkwallet.entity.WithdrawConfigEntity;
import com.chat.wkwallet.service.WKCommonModel;
import com.chat.wkwallet.utils.PayPasswordVerifyDialog;

/**
 * 我的钱包
 */

public class WithdrawActivity extends WKBaseActivity<ActWithdrawLayoutBinding> {


    private static final int REQUEST_SELECT_BANK_CARD = 1001;

    Long bankcard_id;
    String holder_name;
    String account_type;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected ActWithdrawLayoutBinding getViewBinding() {
        return ActWithdrawLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected String getRightTvText(TextView textView) {
        return "";
    }

    @Override
    protected void setTitle(TextView titleTv) {
        super.setTitle(titleTv);
        titleTv.setText(getString(R.string.withdrawal));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingPopup.show();
        loadingPopup.setTitle(getString(R.string.loading));
        WKCommonModel.getInstance().withdrawconfig(new WKCommonModel.IWithdrawConfigListener() {
            @Override
            public void onResult(WithdrawConfigEntity result) {
                String[] explain = result.getExplain();
                StringBuffer sb = new StringBuffer();
                sb.append(getString(R.string.withdrawal_hint_st)+"\n");
                for (int i = 0; i < explain.length; i++) {
                    sb.append(explain[i]+"\n");
                }
                wkVBinding.tvWithdrawShuoming.setText(sb.toString());
                int fee = Math.toIntExact(result.getUsableAmount());
                wkVBinding.tvBalanceKeyong.setText(""+ StringUtils.fen2yuan(fee));
                loadingPopup.dismiss();
            }
        });
        wkVBinding.tvBalanceAll.setOnClickListener(v -> {
            wkVBinding.etAmount.setText(wkVBinding.tvBalanceKeyong.getText().toString());
        });
        wkVBinding.chooseZhanghuAccountLl.setOnClickListener(v -> {
            Intent intent = new Intent(WithdrawActivity.this, BankCardsListActivity.class);
            intent.putExtra("is_withdraw",true);
            startActivityForResult(intent, REQUEST_SELECT_BANK_CARD);
        });
        wkVBinding.btnWithdraw.setOnClickListener(v -> {
            PayPasswordVerifyDialog dialog = new PayPasswordVerifyDialog(WithdrawActivity.this);
            dialog.setAction(getString(R.string.withdrawal));
            dialog.setMoney(wkVBinding.etAmount.getText().toString());
            dialog.setOnInputFinishListener(password -> {
                int amount = StringUtils.yuan2fen(wkVBinding.etAmount.getText().toString());
                JSONObject request = new JSONObject();
                request.put("amount", amount);
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
                        request.put("amount",amount);
                        request.put("bankcard_id", bankcard_id);
                        request.put("holder_name", holder_name);
                        request.put("account_type", "BANK");
                        WKCommonModel.getInstance().withdraw(request, new ICommonListener() {
                            @Override
                            public void onResult(int code, String msg) {
                                loadingPopup.dismiss();
                                if(code == 200){
                                    finish();
                                }else{
                                    showToast(msg);
                                }
                            }
                        });
                    }
                });
            });
            dialog.show();
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_BANK_CARD && resultCode == RESULT_OK && data != null) {
            // 获取返回的银行卡信息
            BankCardDetailEntity selectedCard = (BankCardDetailEntity) data.getSerializableExtra("selected_bank_card");

            // 处理选中的银行卡信息，例如显示卡号等
            if (selectedCard != null) {
                wkVBinding.withdrawBankCardsNumber.setText(selectedCard.getBankname()+"("+getLastFourCharsReverse(selectedCard.getBankcard())+")");
                bankcard_id = selectedCard.getid();
                WKCommonModel.getInstance().nameauthget(new WKCommonModel.INameAuthListener() {
                    @Override
                    public void onResult(NameAuthEntity result) {
                        if(result.getIsAuth() == 1){
                            holder_name = result.getData().getName();
                        }
                    }
                });

            }
        }
    }
    public static String getLastFourCharsReverse(String str) {
        if (str == null || str.length() < 5) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int start = Math.max(0, str.length() - 4);

        for (int i = start; i < str.length(); i++) {
            sb.append(str.charAt(i));
        }

        return sb.toString();
    }
}
