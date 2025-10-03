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
import com.chat.base.entity.BottomSheetItem;
import com.chat.base.utils.WKDialogUtils;
import com.chat.base.utils.WKToastUtils;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActAddBankcards1LayoutBinding;
import com.chat.wkwallet.databinding.ActAddCoinAddressLayoutBinding;
import com.chat.wkwallet.entity.BankCardInfoEntity;
import com.chat.wkwallet.entity.CoinTypeInfo;
import com.chat.wkwallet.entity.GetCoinTypesEntity;
import com.chat.wkwallet.entity.NameAuthEntity;
import com.chat.wkwallet.service.WKCommonModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class AddCoinAddressActivity extends WKBaseActivity<ActAddCoinAddressLayoutBinding> {


    boolean isAuth = false;
    int id;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected ActAddCoinAddressLayoutBinding getViewBinding() {
        return ActAddCoinAddressLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected String getRightTvText(TextView textView) {
        return "";
    }

    @Override
    protected void setTitle(TextView titleTv) {
        super.setTitle(titleTv);
        titleTv.setText("添加地址");
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wkVBinding.etCoinMainCoinType.setOnClickListener(v2 -> {
            //查询可用币种
            WKCommonModel.getInstance().getUdunCoinTypes(new WKCommonModel.IGetUdunCoinTypesListener() {
                @Override
                public void onResult(GetCoinTypesEntity coinTypeInfoList) {
                    //U盾充值
                    List<BottomSheetItem> list = new ArrayList<>();
                    for (int i = 0; i < coinTypeInfoList.getCoinTypes().size(); i++) {
                        CoinTypeInfo coinTypeInfo = coinTypeInfoList.getCoinTypes().get(i);
                        BottomSheetItem bottomSheetItem = new BottomSheetItem(coinTypeInfo.getCoinName(), 0, () -> {
                            wkVBinding.etCoinMainCoinType.setText(coinTypeInfo.getCoinName());
                            id = coinTypeInfo.getId();
                        });
                        list.add(bottomSheetItem);
                    }
                    WKDialogUtils.getInstance().showBottomSheet(AddCoinAddressActivity.this, "", false, list);
                }
            });
        });

        wkVBinding.btnNext.setOnClickListener(v -> {
            String address = wkVBinding.etCoinAddressNumber.getText().toString();
            JSONObject reqJson = new JSONObject();
            reqJson.put("id", id);
            reqJson.put("address", address);
            WKCommonModel.getInstance().addCoinAddress(reqJson,(code,msg) -> {
                if(code == 200){
                    finish();
                }else{
                    WKToastUtils.getInstance().showToast(msg);
                }
            });

        });

        wkVBinding.etCoinMainCoinType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && wkVBinding.etCoinAddressNumber.getText().length() > 0) {
                    wkVBinding.btnNext.setEnabled(true);
                    wkVBinding.btnNext.setAlpha(1f);
                } else {
                    wkVBinding.btnNext.setEnabled(false);
                    wkVBinding.btnNext.setAlpha(0.2f);
                }
            }
        });
        wkVBinding.etCoinAddressNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() > 0 && wkVBinding.etCoinMainCoinType.getText().length() > 0) {
                        wkVBinding.btnNext.setEnabled(true);
                        wkVBinding.btnNext.setAlpha(1f);
                    } else {
                        wkVBinding.btnNext.setEnabled(false);
                        wkVBinding.btnNext.setAlpha(0.2f);
                    }
                }
            });
    }
}
