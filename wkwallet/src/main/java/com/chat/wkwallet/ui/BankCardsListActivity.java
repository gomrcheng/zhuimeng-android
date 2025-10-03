package com.chat.wkwallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.chat.base.base.WKBaseActivity;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActBankcardsListLayoutBinding;
import com.chat.wkwallet.entity.BankCardDetailEntity;
import com.chat.wkwallet.service.WKCommonModel;

import java.util.List;

/**
 *
 */

public class BankCardsListActivity extends WKBaseActivity<ActBankcardsListLayoutBinding> {

    boolean is_withdraw = false;

    @Override
    protected void onResume() {
        super.onResume();
        is_withdraw = getIntent().getBooleanExtra("is_withdraw", false);
        WKCommonModel.getInstance().getbankcards(new WKCommonModel.IGetBankcardsListener() {
            @Override
            public void onResult(List<BankCardDetailEntity> result) {
                BankCardsAdapter adapter = new BankCardsAdapter(result);
                adapter.setOnItemClickListener(new BankCardsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BankCardDetailEntity item) {
                        // 将选中的银行卡信息返回给 WithdrawActivity
                        if(is_withdraw){
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("selected_bank_card", item);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        }
                    }
                });
                wkVBinding.rvBankCards.setAdapter(adapter);
                wkVBinding.rvBankCards.setLayoutManager(new LinearLayoutManager(BankCardsListActivity.this));
            }
        });
    }

    @Override
    protected ActBankcardsListLayoutBinding getViewBinding() {
        return ActBankcardsListLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected String getRightTvText(TextView textView) {
        return "";
    }

    @Override
    protected void setTitle(TextView titleTv) {
        super.setTitle(titleTv);
        titleTv.setText(getString(R.string.bank_cards));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wkVBinding.addBankCardsLl.setOnClickListener(v -> {
            startActivity(new Intent(BankCardsListActivity.this, AddBankCards1Activity.class));
            finish();
        });

    }
}
