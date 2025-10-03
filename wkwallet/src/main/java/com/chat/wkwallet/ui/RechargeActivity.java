package com.chat.wkwallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chat.base.base.WKBaseActivity;
import com.chat.base.utils.WKToastUtils;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActRechargeLayoutBinding;
import com.chat.wkwallet.entity.RechargeConfigEntity;
import com.chat.wkwallet.service.WKCommonModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的钱包
 */

public class RechargeActivity extends WKBaseActivity<ActRechargeLayoutBinding> {

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected ActRechargeLayoutBinding getViewBinding() {
        return ActRechargeLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected String getRightTvText(TextView textView) {
        return "";
    }

    @Override
    protected void setTitle(TextView titleTv) {
        super.setTitle(titleTv);
        titleTv.setText(getString(R.string.recharge_hint));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lastBalance = getIntent().getStringExtra("last_balance");
        if(lastBalance!=null && !lastBalance.equals("")){
            wkVBinding.tvCurrentBalance.setText(""+lastBalance + "元");
        }
        RecyclerView rvQuickAmounts = findViewById(R.id.rv_quick_amounts);
        rvQuickAmounts.setLayoutManager(new GridLayoutManager(this, 3)); // 每行 3 个
        WKCommonModel.getInstance().rechargeconfig(new WKCommonModel.IRechargeConfigListener() {
            @Override
            public void onResult(RechargeConfigEntity result) {
                List<String> quickAmounts = new ArrayList<>();
                for (Integer amount : result.getAmounts()) {
                    quickAmounts.add(""+amount);
                }
                QuickAmountAdapter adapter = new QuickAmountAdapter(quickAmounts);
                rvQuickAmounts.setAdapter(adapter);
                adapter.setOnAmountClickListener(amount -> {
                    wkVBinding.etAmount.setText(amount);
                });
            }
        });
        wkVBinding.etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    wkVBinding.btnNext.setEnabled(true);
                    wkVBinding.btnNext.setAlpha(1f);
                } else {
                    wkVBinding.btnNext.setEnabled(false);
                    wkVBinding.btnNext.setAlpha(0.2f);
                }
            }
        });
        wkVBinding.btnNext.setOnClickListener(v -> {
            WKToastUtils.getInstance().showToast("暂未开放...");
        });
    }
}
