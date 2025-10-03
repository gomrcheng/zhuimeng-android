package com.chat.wkwallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chat.base.base.WKBaseActivity;
import com.chat.base.utils.WKToastUtils;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActAddBankcards1LayoutBinding;
import com.chat.wkwallet.entity.BankCardInfoEntity;
import com.chat.wkwallet.entity.NameAuthEntity;
import com.chat.wkwallet.service.WKCommonModel;

/**
 *
 */

public class AddBankCards1Activity extends WKBaseActivity<ActAddBankcards1LayoutBinding> {


    boolean isAuth = false;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected ActAddBankcards1LayoutBinding getViewBinding() {
        return ActAddBankcards1LayoutBinding.inflate(getLayoutInflater());
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
        WKCommonModel.getInstance().nameauthget(new WKCommonModel.INameAuthListener() {
            @Override
            public void onResult(NameAuthEntity result) {
                if(result.getIsAuth() == 1){
                    wkVBinding.chikarenll.setVisibility(View.VISIBLE);
                    wkVBinding.etCardName.setText(""+result.getData().getName());
                    wkVBinding.etCardName.setEnabled(false);
                    wkVBinding.etCardName.setFocusable(false);
                    wkVBinding.etCardName.setClickable(false);
                    isAuth = true;
                }else{
                    wkVBinding.chikarenll.setVisibility(View.GONE);
                }
            }
        });
        wkVBinding.btnNext.setOnClickListener(v -> {
            WKCommonModel.getInstance().bankcardinfo(""+wkVBinding.etCardNumber.getText(), new WKCommonModel.IBankCardInfoListener() {

                @Override
                public void onResult(BankCardInfoEntity result) {
                    if(result.getVaild() == 0){
                        WKToastUtils.getInstance().showToast("银行卡无效");
                    }else{
                        Intent intent = new Intent(AddBankCards1Activity.this, AddBankCards2Activity.class);
                        intent.putExtra("bank_name",result.getData().getBankname());
                        intent.putExtra("bank_card",wkVBinding.etCardNumber.getText().toString());
                        intent.putExtra("bankcard_type",result.getData().getBankcardType());
                        intent.putExtra("bank_code",result.getData().getBankcode());
                        intent.putExtra("holder_name",wkVBinding.etCardName.getText().toString());
                        intent.putExtra("is_auth",isAuth);
                        startActivity(intent);
                        finish();
                    }
                }
            });

        });

        wkVBinding.etCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && wkVBinding.etCardNumber.getText().length()>0) {
                    wkVBinding.btnNext.setEnabled(true);
                    wkVBinding.btnNext.setAlpha(1f);
                } else {
                    wkVBinding.btnNext.setEnabled(false);
                    wkVBinding.btnNext.setAlpha(0.2f);
                }
            }
        });

        wkVBinding.etCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 &&  wkVBinding.etCardNumber.getText().length()>0) {
                    wkVBinding.btnNext.setEnabled(true);
                    wkVBinding.btnNext.setAlpha(1f);
                } else {
                    wkVBinding.btnNext.setEnabled(false);
                    wkVBinding.btnNext.setAlpha(0.2f);
                }
            }
        });


//        wkVBinding.addBankCardsLl.setOnClickListener(v -> {
//
//
//
//        });

//        if(lastBalance!=null && !lastBalance.equals("")){
//            wkVBinding.tvCurrentBalance.setText(""+lastBalance + "元");
//        }
//        RecyclerView rvQuickAmounts = findViewById(R.id.rv_quick_amounts);
//        rvQuickAmounts.setLayoutManager(new GridLayoutManager(this, 3)); // 每行 3 个
//        WKCommonModel.getInstance().rechargeconfig(new WKCommonModel.IRechargeConfigListener() {
//            @Override
//            public void onResult(RechargeConfigEntity result) {
//                List<String> quickAmounts = new ArrayList<>();
//                for (Integer amount : result.getAmounts()) {
//                    quickAmounts.add(""+amount);
//                }
//                QuickAmountAdapter adapter = new QuickAmountAdapter(quickAmounts);
//                rvQuickAmounts.setAdapter(adapter);
//                adapter.setOnAmountClickListener(amount -> {
//                    wkVBinding.etAmount.setText(amount);
//                });
//            }
//        });
//        wkVBinding.etAmount.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (editable.length() > 0) {
//                    wkVBinding.btnNext.setEnabled(true);
//                    wkVBinding.btnNext.setAlpha(1f);
//                } else {
//                    wkVBinding.btnNext.setEnabled(false);
//                    wkVBinding.btnNext.setAlpha(0.2f);
//                }
//            }
//        });
    }
}
