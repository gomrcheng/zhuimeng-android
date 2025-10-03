package com.chat.wkwallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.chat.base.base.WKBaseActivity;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActAddBankcards2LayoutBinding;
import com.chat.wkwallet.service.WKCommonModel;

/**
 *
 */

public class AddBankCards2Activity extends WKBaseActivity<ActAddBankcards2LayoutBinding> {

    String bank_name;
    String bank_card;
    String bankcard_type;
    String bank_code;
    String holder_name;
    boolean is_auth = false;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected ActAddBankcards2LayoutBinding getViewBinding() {
        return ActAddBankcards2LayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected String getRightTvText(TextView textView) {
        return "";
    }

    @Override
    protected void setTitle(TextView titleTv) {
        super.setTitle(titleTv);
        titleTv.setText(getString(R.string.add_bank_cards_hint));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bank_name = getIntent().getStringExtra("bank_name");
        bank_card = getIntent().getStringExtra("bank_card");
        bankcard_type = getIntent().getStringExtra("bankcard_type");
        bank_code = getIntent().getStringExtra("bank_code");
        holder_name = getIntent().getStringExtra("holder_name");
        is_auth = getIntent().getBooleanExtra("is_auth",false);
        wkVBinding.shimingll.setVisibility(is_auth?View.GONE: View.VISIBLE);

        wkVBinding.tvCardTypeValue.setText(bank_name);
        wkVBinding.etBankCardPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 ) {
                    wkVBinding.btnNext.setEnabled(true);
                    wkVBinding.btnNext.setAlpha(1f);
                } else {
                    wkVBinding.btnNext.setEnabled(false);
                    wkVBinding.btnNext.setAlpha(0.2f);
                }
            }
        });
        wkVBinding.btnNext.setOnClickListener(v -> {
            JSONObject reqJson = new JSONObject();
            reqJson.put("bankcard", bank_card);
            reqJson.put("bankcode", bank_code);
            reqJson.put("phone", wkVBinding.etBankCardPhone.getText().toString());
            reqJson.put("bankname",bank_name);
            reqJson.put("bankcard_type",bankcard_type);
            reqJson.put("cert_no",wkVBinding.etCertNumber.getText().toString());
            reqJson.put("cert_type",1);
            if(holder_name.equals("") || holder_name==null){
                holder_name = wkVBinding.etRealName.getText().toString();
            }
            reqJson.put("holder_name",holder_name);
            WKCommonModel.getInstance().addbankcards(reqJson, (code, message) -> {
                if(code == 200){
                    //绑定成功返回银行卡列表
                    startActivity(new Intent(AddBankCards2Activity.this, BankCardsListActivity.class));
                    finish();
                }else{
                    showToast(message);
                }
            });
        });


    }
}
