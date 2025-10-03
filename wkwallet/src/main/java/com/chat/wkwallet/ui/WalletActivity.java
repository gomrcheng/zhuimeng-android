package com.chat.wkwallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chat.base.base.WKBaseActivity;
import com.chat.base.entity.BottomSheetItem;
import com.chat.base.utils.StringUtils;
import com.chat.base.utils.WKDialogUtils;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActWalletLayoutBinding;
import com.chat.wkwallet.entity.ChannelsEntity;
import com.chat.wkwallet.entity.CoinTypeInfo;
import com.chat.wkwallet.entity.GetCoinTypesEntity;
import com.chat.wkwallet.entity.RechargeConfigEntity;
import com.chat.wkwallet.service.WKCommonModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的钱包
 */

public class WalletActivity extends WKBaseActivity<ActWalletLayoutBinding> {

    private String last_amount;
    @Override
    protected void onResume() {
        super.onResume();
        loadingPopup.show();
        loadingPopup.setTitle(getString(R.string.loading));
        WKCommonModel.getInstance().getWalletInfo(new WKCommonModel.IWalletInfoListener() {
            @Override
            public void onResult(int amount, int status, int password_is_set) {
                last_amount = StringUtils.fen2yuan( amount);
                wkVBinding.tvBalance.setText(last_amount);
                loadingPopup.dismiss();
            }
        });
    }

    @Override
    protected ActWalletLayoutBinding getViewBinding() {
        return ActWalletLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected String getRightTvText(TextView textView) {
        return "";
    }

    @Override
    protected void setTitle(TextView titleTv) {
        super.setTitle(titleTv);
        titleTv.setText(getString(R.string.qianbao));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wkVBinding.tvRecharge.setOnClickListener(v -> {

            loadingPopup.show();
            loadingPopup.setTitle(getString(R.string.loading));
            WKCommonModel.getInstance().rechargeconfig(new WKCommonModel.IRechargeConfigListener() {
                @Override
                public void onResult(RechargeConfigEntity result) {
                    List<ChannelsEntity> channels = result.getChannels();
                    List<BottomSheetItem> list = new ArrayList<>();
                    for (int i = 0; i < channels.size(); i++) {
                        int type = channels.get(i).getType();
                        String icon = channels.get(i).getIcon();
                        String title = channels.get(i).getTitle();
                        if(type==4){
                            BottomSheetItem bottomSheetItem = new BottomSheetItem("虚拟货币", 0, () -> {
                                Intent intent = new Intent(WalletActivity.this, RechargeCoinActivity.class);
                                intent.putExtra("last_balance", last_amount);
                                startActivity(intent);
                            });
                            list.add(bottomSheetItem);
                        }else{
                            BottomSheetItem bottomSheetItem = new BottomSheetItem(title, 0, () -> {
                                Intent intent = new Intent(WalletActivity.this, RechargeActivity.class);
                                intent.putExtra("last_balance", last_amount);
                                startActivity(intent);
                            });
                            list.add(bottomSheetItem);
                        }
                   }
                   WKDialogUtils.getInstance().showBottomSheet(WalletActivity.this,"",false,list);
                   loadingPopup.dismiss();
                }
            });






        });
        wkVBinding.mianmiLl.setOnClickListener(v -> {
            Intent intent = new Intent(WalletActivity.this, MianmiActivity.class);
            startActivity(intent);
        });
        wkVBinding.tvWithdraw.setOnClickListener(v -> {
            List<BottomSheetItem> list = new ArrayList<>();
            BottomSheetItem bottomSheetItem = new BottomSheetItem("银行卡提现", 0, () -> {
                Intent intent = new Intent(WalletActivity.this, WithdrawActivity.class);
                intent.putExtra("last_balance", last_amount);
                startActivity(intent);
            });
            list.add(bottomSheetItem);
            bottomSheetItem = new BottomSheetItem("虚拟货币提现", 0, () -> {
                Intent intent = new Intent(WalletActivity.this, WithdrawCoinActivity.class);
                intent.putExtra("last_balance", last_amount);
                startActivity(intent);
            });
            list.add(bottomSheetItem);
            WKDialogUtils.getInstance().showBottomSheet(WalletActivity.this,"",false,list);



        });
        wkVBinding.withdrawAccoutLl.setOnClickListener(v -> {
            List<BottomSheetItem> list = new ArrayList<>();
            BottomSheetItem bottomSheetItem = new BottomSheetItem("银行卡", 0, () -> {
                Intent intent = new Intent(WalletActivity.this, BankCardsListActivity.class);
                startActivity(intent);
            });
            list.add(bottomSheetItem);
            bottomSheetItem = new BottomSheetItem("虚拟货币", 0, () -> {
                Intent intent = new Intent(WalletActivity.this, CoinListActivity.class);
                startActivity(intent);
            });
            list.add(bottomSheetItem);
            WKDialogUtils.getInstance().showBottomSheet(WalletActivity.this,"",false,list);
        });
        wkVBinding.transationRecordLL.setOnClickListener(v -> {
            Intent intent = new Intent(WalletActivity.this, TransactionRecordActivity.class);
            startActivity(intent);
        });
        wkVBinding.payPwdLl.setOnClickListener(v -> {
            Intent intent = new Intent(WalletActivity.this, WKResetPayPwdActivity.class);
            startActivity(intent);
        });
        wkVBinding.kefuLl.setOnClickListener(v -> {
            Intent intent = new Intent(WalletActivity.this, KefuListActivity.class);
            startActivity(intent);
        });
        wkVBinding.redpackteRecordLl.setOnClickListener(v -> {
            Intent intent = new Intent(WalletActivity.this, RedPacketRecordActivity.class);
            startActivity(intent);
        });

    }

}
