package com.chat.wkwallet.ui;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chat.base.base.WKBaseActivity;
import com.chat.base.config.WKConfig;
import com.chat.base.endpoint.EndpointManager;
import com.chat.base.entity.BottomSheetItem;
import com.chat.base.glide.GlideUtils;
import com.chat.base.utils.ImageUtils;
import com.chat.base.utils.WKDialogUtils;
import com.chat.base.utils.WKToastUtils;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActRechargeCoinLayoutBinding;
import com.chat.wkwallet.databinding.ActRechargeLayoutBinding;
import com.chat.wkwallet.entity.CoinTypeInfo;
import com.chat.wkwallet.entity.GetCoinInfoEntity;
import com.chat.wkwallet.entity.GetCoinTypesEntity;
import com.chat.wkwallet.entity.RechargeConfigEntity;
import com.chat.wkwallet.service.WKCommonModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的钱包
 */

public class RechargeCoinActivity extends WKBaseActivity<ActRechargeCoinLayoutBinding> {

    private String coinType = "195";
    private String coinName = "USDT-TRC20";
    private String coinIconUrl = "";

    private Map<String,String> coinHintMap = new HashMap<>();
    private Map<String,String> coinMinTradeMap = new HashMap<>();

    public static List<CoinTypeInfo> coinTypeInfoListAll;

    @Override
    protected void onResume() {
        super.onResume();
        loadingPopup.show();
        loadingPopup.setTitle(getString(R.string.loading));

        //查询可用币种
        WKCommonModel.getInstance().getUdunCoinTypes(new WKCommonModel.IGetUdunCoinTypesListener() {
            @Override
            public void onResult(GetCoinTypesEntity coinTypeInfoList) {
                coinTypeInfoListAll = coinTypeInfoList.getCoinTypes();
                for (int i = 0; i < coinTypeInfoListAll.size(); i++) {
                    String coinName = coinTypeInfoListAll.get(i).getName();
                    String coinHint = coinTypeInfoList.getCoinTypes().get(i).getTips();
                    String coinMinTrade = coinTypeInfoList.getCoinTypes().get(i).getMinTrade();
                    coinHintMap.put(coinName,coinHint);
                    coinMinTradeMap.put(coinName,coinMinTrade);
                }
                loadingPopup.dismiss();
                getCoinInfo();
            }
        });
    }

    @Override
    protected ActRechargeCoinLayoutBinding getViewBinding() {
        return ActRechargeCoinLayoutBinding.inflate(getLayoutInflater());
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
        wkVBinding.llRechargeCoinChooseCoin.setOnClickListener(v -> {
            showCurrencyPopup();
        });
        wkVBinding.btnSave.setOnClickListener(v -> {
            if (wkVBinding.ivRechargeCoinQrCode.getDrawable() != null) {
                // 获取二维码图片
                wkVBinding.ivRechargeCoinQrCode.setDrawingCacheEnabled(true);
                wkVBinding.ivRechargeCoinQrCode.buildDrawingCache();
                Bitmap bitmap = Bitmap.createBitmap(wkVBinding.ivRechargeCoinQrCode.getDrawingCache());
                wkVBinding.ivRechargeCoinQrCode.setDrawingCacheEnabled(false);

                // 调用保存图片的方法
                ImageUtils.getInstance().saveBitmap(RechargeCoinActivity.this, bitmap, true, path -> showToast(R.string.saved_album));

            } else {
                WKToastUtils.getInstance().showToast("二维码加载失败,无法保存");
            }
        });
        wkVBinding.btnCopy.setOnClickListener(v -> {
            // 获取地址文本
            String address = wkVBinding.tvAddress.getText().toString().trim();

            // 检查地址是否为空
            if (!address.isEmpty()) {
                // 复制到剪贴板
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("address", address);
                clipboardManager.setPrimaryClip(clipData);

                // 显示成功提示
                WKToastUtils.getInstance().showToast(getString(R.string.copyed));
            } else {
                // 地址为空时提示
                WKToastUtils.getInstance().showToastFail("复制失败,地址为空");
            }
        });
    }

    public void getCoinInfo() {
        loadingPopup.show();
        WKCommonModel.getInstance().getCoinAddress(Integer.parseInt(coinType), new WKCommonModel.IGetCoinAddress() {
            @Override
            public void onResult(GetCoinInfoEntity getCoinInfoEntity) {
                String qrCode = getCoinInfoEntity.getAddress();
                Bitmap mBitmap = (Bitmap) EndpointManager.getInstance().invoke("create_qrcode", qrCode);
                wkVBinding.ivRechargeCoinQrCode.setImageBitmap(mBitmap);
                wkVBinding.tvRechargeCoinHint.setText(getCoinInfoEntity.getTips());
                wkVBinding.tvAddress.setText(qrCode);
                if(!coinIconUrl.isEmpty()){
                    GlideUtils.getInstance().showImg(RechargeCoinActivity.this, coinIconUrl, wkVBinding.ivRechargeCoinIcon);
                }
                wkVBinding.tvRechargeCoinName.setText(coinName);
                wkVBinding.tvRechargeCoinHint.setText(coinHintMap.get(coinName));
                wkVBinding.tvMinTrade.setText(coinMinTradeMap.get(coinName));
                loadingPopup.dismiss();
            }
        });
    }


    private void showCurrencyPopup() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_currency_select);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setGravity(Gravity.BOTTOM); // 弹出位置底部
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            // 设置背景变暗（遮罩效果）
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setDimAmount(0.2f); // 背景变暗程度（0~1），0.5 是中等变暗
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 设置背景透明度（可选）
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        RecyclerView rvList = dialog.findViewById(R.id.rv_currency_list);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        // 动态数据示例
        List<CurrencyAdapter.CurrencyItem> items = new ArrayList<>();
        for (int i = 0; i < coinTypeInfoListAll.size(); i++) {
            if(coinName.equals(coinTypeInfoListAll.get(i).getName())){
                items.add(new CurrencyAdapter.CurrencyItem(coinTypeInfoListAll.get(i).getLogo(),
                        coinTypeInfoListAll.get(i).getName(),
                        coinTypeInfoListAll.get(i).getMainCoinType(),
                        coinTypeInfoListAll.get(i).getMinTrade(),
                        coinTypeInfoListAll.get(i).getTips(),
                        true));
            }else{
                items.add(new CurrencyAdapter.CurrencyItem(coinTypeInfoListAll.get(i).getLogo(),
                        coinTypeInfoListAll.get(i).getName(),
                        coinTypeInfoListAll.get(i).getMainCoinType(),
                        coinTypeInfoListAll.get(i).getMinTrade(),
                        coinTypeInfoListAll.get(i).getTips(),
                        false));
            }
        }

        CurrencyAdapter adapter = new CurrencyAdapter(items, new CurrencyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CurrencyAdapter.CurrencyItem item) {
                // 更新选中状态
                for (CurrencyAdapter.CurrencyItem i : items) {
                    i.isSelected = false;
                }
                item.isSelected = true;
                // 关闭弹窗
                coinName = item.name;
                coinType = item.type;
                coinIconUrl = item.iconUrl;
                getCoinInfo();
                dialog.dismiss();
            }
        },this);

        rvList.setAdapter(adapter);

        dialog.show();
    }
}
