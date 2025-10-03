package com.chat.wkwallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chat.base.base.WKBaseActivity;
import com.chat.base.endpoint.EndpointManager;
import com.chat.base.utils.WKDialogUtils;
import com.chat.base.utils.WKToastUtils;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActBankcardsListLayoutBinding;
import com.chat.wkwallet.databinding.ActCoinListLayoutBinding;
import com.chat.wkwallet.entity.BankCardDetailEntity;
import com.chat.wkwallet.entity.GetUserWalletAddresses;
import com.chat.wkwallet.entity.UserWalletAddress;
import com.chat.wkwallet.service.WKCommonModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class CoinListActivity extends WKBaseActivity<ActCoinListLayoutBinding> {

    boolean is_withdraw = false;

    @Override
    protected ActCoinListLayoutBinding getViewBinding() {
        return ActCoinListLayoutBinding.inflate(getLayoutInflater());
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadingPopup.show();
        loadingPopup.setTitle(getString(R.string.loading));
        is_withdraw = getIntent().getBooleanExtra("is_withdraw", false);
        WKCommonModel.getInstance().getcoinAddressesAll(1,20, new WKCommonModel.IGetcoinAddresses() {

            @Override
            public void onResult(GetUserWalletAddresses getUserWalletAddresses) {
                loadingPopup.dismiss();
                CoinAddressAdapter adapter = new CoinAddressAdapter(getUserWalletAddresses.getList());
                wkVBinding.rvCoinList.setAdapter(adapter);
                wkVBinding.rvCoinList.setLayoutManager(new LinearLayoutManager(CoinListActivity.this));
                adapter.setOnItemClickListener(item -> {
                    if(is_withdraw){
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("selected_coin_address", item);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });
                adapter.setOnItemLongClickListener(item -> {
                    WKDialogUtils.getInstance().showDialog(CoinListActivity.this, "删除", "您确定删除绑定的该地址？", true, "取消", "删除", 0, ContextCompat.getColor(CoinListActivity.this, R.color.red), index -> {
                        if (index == 1) {
                            int id = item.getid();
                            WKCommonModel.getInstance().deleteAddress(id,(code,msg) ->{
                                adapter.removeItem(item);
                            });
                        }
                    });
                });
            }
        });
    }

    @Override
    protected String getRightTvText(TextView textView) {
        return "";
    }

    @Override
    protected void setTitle(TextView titleTv) {
        super.setTitle(titleTv);
        titleTv.setText("我的地址");
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wkVBinding.addCoinAddressLl.setOnClickListener(v -> {
            startActivity(new Intent(CoinListActivity.this, AddCoinAddressActivity.class));
        });
    }
}
