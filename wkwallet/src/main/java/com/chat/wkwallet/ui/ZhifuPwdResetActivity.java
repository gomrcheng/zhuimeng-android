package com.chat.wkwallet.ui;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import com.chat.base.base.WKBaseActivity;
import com.chat.base.net.ICommonListener;
import com.chat.base.ui.Theme;
import com.chat.base.utils.WKToastUtils;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActZhifuPwdResetLayoutBinding;
import com.chat.wkwallet.service.WKCommonModel;

import java.util.Objects;

/**
 * 2020-11-02 10:19
 * 聊天密码
 */
public class ZhifuPwdResetActivity extends WKBaseActivity<ActZhifuPwdResetLayoutBinding> {
    @Override
    protected ActZhifuPwdResetLayoutBinding getViewBinding() {
        return ActZhifuPwdResetLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setTitle(TextView titleTv) {
        titleTv.setText(getString(R.string.string_xgzfmm));
    }

    @Override
    protected void initView() {
        wkVBinding.submitBtn.getBackground().setTint(Theme.colorAccount);
    }

    @Override
    protected void initListener() {
        wkVBinding.loginPwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        wkVBinding.chatPwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        wkVBinding.chatPwdEtAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        wkVBinding.submitBtn.setOnClickListener(v -> {

            String oldPwd = Objects.requireNonNull(wkVBinding.loginPwdEt.getText()).toString();
            String newPwd = Objects.requireNonNull(wkVBinding.chatPwdEt.getText()).toString();
            String newPwdAgain = Objects.requireNonNull(wkVBinding.chatPwdEtAgain.getText()).toString();
            if (!newPwd.equals(newPwdAgain)) {
                showToast(getString(R.string.string_lcmmbyz));
            } else {
                loadingPopup.setTitle(getString(R.string.string_xgz));
                loadingPopup.show();
                WKCommonModel.getInstance().passwordSet(oldPwd,newPwd, new ICommonListener() {
                    @Override
                    public void onResult(int code, String msg) {
                        loadingPopup.dismiss();
                        if(code==200){
                            WKToastUtils.getInstance().showToast(getString(R.string.string_xgcg));
                            finish();
                        }else{
                            WKToastUtils.getInstance().showToast(msg);
                        }

                    }
                });


                loadingPopup.dismiss();
            }

        });
    }

    private void checkInput() {
        String loginPwd = Objects.requireNonNull(wkVBinding.loginPwdEt.getText()).toString();
        String chatPwd = Objects.requireNonNull(wkVBinding.chatPwdEt.getText()).toString();
        String chatPwdAgain = Objects.requireNonNull(wkVBinding.chatPwdEtAgain.getText()).toString();

        if (!TextUtils.isEmpty(loginPwd)
                && !TextUtils.isEmpty(chatPwd)
                && !TextUtils.isEmpty(chatPwdAgain)
                && loginPwd.length() >= 6
                && chatPwd.length() == 6
                && chatPwdAgain.length() == 6) {
            wkVBinding.submitBtn.setAlpha(1f);
            wkVBinding.submitBtn.setEnabled(true);
        } else {
            wkVBinding.submitBtn.setEnabled(false);
            wkVBinding.submitBtn.setAlpha(0.2f);
        }
    }

}
