package com.chat.wkwallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chat.base.base.WKBaseActivity;
import com.chat.base.endpoint.EndpointManager;
import com.chat.base.endpoint.EndpointSID;
import com.chat.base.endpoint.entity.ChatViewMenu;
import com.chat.base.entity.BottomSheetItem;
import com.chat.base.utils.WKDialogUtils;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActKefuListLayoutBinding;
import com.chat.wkwallet.databinding.ActRechargeChannelsListLayoutBinding;
import com.chat.wkwallet.entity.CoinTypeInfo;
import com.chat.wkwallet.entity.GetCoinTypesEntity;
import com.chat.wkwallet.entity.KefuEntity;
import com.chat.wkwallet.entity.RechargeConfigEntity;
import com.chat.wkwallet.service.WKCommonModel;
import com.xinbida.wukongim.entity.WKChannelType;

import java.util.ArrayList;
import java.util.List;

public class RechargeChannelsListActivity extends WKBaseActivity<ActRechargeChannelsListLayoutBinding> {


    @Override
    protected ActRechargeChannelsListLayoutBinding getViewBinding() {
        return ActRechargeChannelsListLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setTitle(TextView titleTv) {
        super.setTitle(titleTv);
        titleTv.setText(getString(R.string.recharge_channel_hint));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupRecyclerView();
    }


    private void setupRecyclerView() {
        loadingPopup.show();
        loadingPopup.setTitle(getString(R.string.loading));
        WKCommonModel.getInstance().rechargeconfig(new WKCommonModel.IRechargeConfigListener() {
            @Override
            public void onResult(RechargeConfigEntity result) {
                RechargeChannelsAdapter rechargeChannelsAdapter = new RechargeChannelsAdapter(RechargeChannelsListActivity.this, result.getChannels());
                wkVBinding.rvChooseChannels.setLayoutManager(new LinearLayoutManager(RechargeChannelsListActivity.this));
                wkVBinding.rvChooseChannels.setAdapter(rechargeChannelsAdapter);
                loadingPopup.dismiss();
                rechargeChannelsAdapter.setOnItemClickListener((customer, position) -> {
                    System.out.println("customer:"+customer);
                    if(customer.getType() == 4){
                        //查询可用币种
                        WKCommonModel.getInstance().getUdunCoinTypes(new WKCommonModel.IGetUdunCoinTypesListener() {
                            @Override
                            public void onResult(GetCoinTypesEntity coinTypeInfoList) {
                                //U盾充值
                                List<BottomSheetItem> list = new ArrayList<>();
                                for (int i = 0; i < coinTypeInfoList.getCoinTypes().size(); i++) {
                                    CoinTypeInfo coinTypeInfo = coinTypeInfoList.getCoinTypes().get(i);
                                    BottomSheetItem bottomSheetItem = new BottomSheetItem(coinTypeInfo.getCoinName(), 0, () -> goToUDunPay(coinTypeInfo.getMainCoinType(),coinTypeInfo.getCoinName()));
                                    list.add(bottomSheetItem);
                                }
                                WKDialogUtils.getInstance().showBottomSheet(RechargeChannelsListActivity.this,"",false,list);
                            }
                        });
                    }
                });
            }
        });
    }

    private void goToUDunPay(String conType,String coinName){
        System.out.println("conType:"+conType);
        //进入获取钱包地址界面
        Intent intent = new Intent(this,RechargeCoinActivity.class);
        intent.putExtra("coinType",conType);
        intent.putExtra("coinName",coinName);
        startActivity( intent);
        finish();


    }

}