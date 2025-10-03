package com.chat.wkwallet.ui;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chat.base.base.WKBaseActivity;
import com.chat.base.config.WKConfig;
import com.chat.base.config.WKSharedPreferencesUtil;
import com.chat.base.net.ICommonListener;
import com.chat.base.ui.Theme;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActResetPayPwdLayoutBinding;
import com.chat.wkwallet.entity.CountryCodeEntity;
import com.chat.wkwallet.service.WKCommonModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.ResourceObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 2020-11-25 11:21
 * 重置登录密码
 */
public class WKResetPayPwdActivity extends WKBaseActivity<ActResetPayPwdLayoutBinding>  {

    private String code = "0086";

    private final int totalTime = 60;

    @Override
    protected ActResetPayPwdLayoutBinding getViewBinding() {
        return ActResetPayPwdLayoutBinding.inflate(getLayoutInflater());
    }


    @SuppressLint("StringFormatInvalid")
    @Override
    protected void initView() {
        wkVBinding.sureBtn.getBackground().setTint(Theme.colorAccount);
        wkVBinding.getVerCodeBtn.getBackground().setTint(Theme.colorAccount);
        Theme.setPressedBackground(wkVBinding.backIv);
        wkVBinding.backIv.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(this, R.color.colorDark), PorterDuff.Mode.MULTIPLY));
        boolean canEditPhone = getIntent().getBooleanExtra("canEditPhone", false);
        wkVBinding.nameEt.setEnabled(canEditPhone);
        wkVBinding.nameEt.setText(WKConfig.getInstance().getUserInfo().phone);
        String zone = WKConfig.getInstance().getUserInfo().zone;
        if (!TextUtils.isEmpty(zone)) {
            code = zone;
            String codeName = code.substring(2);
            wkVBinding.codeTv.setText(String.format("+%s", codeName));
        }
        if (!canEditPhone || !TextUtils.isEmpty(Objects.requireNonNull(wkVBinding.nameEt.getText()).toString())) {
            wkVBinding.getVerCodeBtn.setEnabled(true);
            wkVBinding.getVerCodeBtn.setAlpha(1);
        }

        wkVBinding.resetLoginPwdTv.setText(String.format(getString(R.string.auth_phone_tips), getString(R.string.app_name)));
    }

    @Override
    protected void initListener() {
        wkVBinding.nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    wkVBinding.getVerCodeBtn.setEnabled(true);
                    wkVBinding.getVerCodeBtn.setAlpha(1f);
                } else {
                    wkVBinding.getVerCodeBtn.setEnabled(false);
                    wkVBinding.getVerCodeBtn.setAlpha(0.2f);
                }
                checkStatus();
            }
        });
        wkVBinding.verfiEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkStatus();
            }
        });
        wkVBinding.sureBtn.setOnClickListener(v -> {

            String phone = Objects.requireNonNull(wkVBinding.nameEt.getText()).toString();
            String verCode = wkVBinding.verfiEt.getText().toString();
            if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(verCode)) {
                Intent intent = new Intent(WKResetPayPwdActivity.this, InputzhifuPwdActivity.class);
                WKSharedPreferencesUtil.getInstance().putSP("sms_code", verCode);
                startActivity(intent);
            }

        });
        wkVBinding.getVerCodeBtn.setOnClickListener(v -> {
            String phone = wkVBinding.nameEt.getText().toString();
            if (!TextUtils.isEmpty(phone)) {
                WKCommonModel.getInstance().smscodeSend(new ICommonListener() {
                    @Override
                    public void onResult(int code, String msg) {
                        if(code==200){
                            startTimer();
                        }
                    }
                });

            }
        });
        wkVBinding.backIv.setOnClickListener(v -> finish());
    }


    private void checkStatus() {
        String phone = wkVBinding.nameEt.getText().toString();
        String verCode = wkVBinding.verfiEt.getText().toString();
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(verCode) ) {
            wkVBinding.sureBtn.setAlpha(1f);
            wkVBinding.sureBtn.setEnabled(true);
        } else {
            wkVBinding.sureBtn.setAlpha(0.2f);
            wkVBinding.sureBtn.setEnabled(false);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            CountryCodeEntity entity = data.getParcelableExtra("entity");
            assert entity != null;
            code = entity.code;
            String codeName = code.substring(2);
            wkVBinding.codeTv.setText(String.format("+%s", codeName));
        }
    }

    public void startTimer() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(totalTime + 1)
                .map(takeValue -> totalTime - takeValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<>() {
                    @Override
                    public void onComplete() {
                        wkVBinding.getVerCodeBtn.setEnabled(true);
                        wkVBinding.getVerCodeBtn.setAlpha(1f);
                        wkVBinding.getVerCodeBtn.setText(R.string.get_verf_code);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                    }

                    @Override
                    public void onNext(@NotNull Long value) {
                        wkVBinding.getVerCodeBtn.setEnabled(false);
                        wkVBinding.getVerCodeBtn.setAlpha(0.2f);
                        wkVBinding.getVerCodeBtn.setEnabled(true);
                        wkVBinding.getVerCodeBtn.setText(String.format("%s%s s", getString(R.string.recapture), value));
                    }
                });
    }
}
