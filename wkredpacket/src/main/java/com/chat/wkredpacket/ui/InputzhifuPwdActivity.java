package com.chat.wkredpacket.ui;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.chat.base.base.WKBaseActivity;
import com.chat.base.config.WKSharedPreferencesUtil;
import com.chat.base.net.ICommonListener;
import com.chat.base.utils.WKToastUtils;
import com.chat.wkredpacket.R;
import com.chat.wkredpacket.databinding.ActInputZhifPwdLayoutBinding;
import com.chat.wkredpacket.service.WKCommonModel;

/**
 * 2021/8/9 16:38
 */
public class InputzhifuPwdActivity extends WKBaseActivity<ActInputZhifPwdLayoutBinding> {
    String oldPwd;
    private boolean isSetNewPwd;
    private String set2;


    @Override
    protected ActInputZhifPwdLayoutBinding getViewBinding() {
        return ActInputZhifPwdLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setTitle(TextView titleTv) {
        titleTv.setText(getString(R.string.string_qszzfmm));
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void initView() {
        wkVBinding.pwdView.setPwdViewBg();
        wkVBinding.pwdView.hideCloseIV();
        set2 = getIntent().getStringExtra("set2");
        if(set2!=null && !set2.equals("")){
            setTitle(getString(R.string.string_qszzfmm2));
        }
        wkVBinding.passwordInputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable psw) {
                String numPwd = wkVBinding.passwordInputView.getText().toString();
                if (psw.length() == 6) {
                    if(set2!=null && !set2.equals("")){
                        //设置支付密码接口调用
                        if(set2.equals(numPwd)){
                            String smscode = WKSharedPreferencesUtil.getInstance().getSP("sms_code","123456");
                            WKCommonModel.getInstance().setpaypassword(smscode,numPwd, new ICommonListener() {
                                @Override
                                public void onResult(int code, String msg) {
                                    if(code == 200){
                                        finish();
                                        //进入钱包页面
                                    }
                                }
                            });
                        }else{
                            WKToastUtils.getInstance().showToast("两次密码不一致");
                        }
                    }else{
                        Intent intent = new Intent(InputzhifuPwdActivity.this, InputzhifuPwdActivity.class);
                        intent.putExtra("set2",numPwd);
                        startActivity( intent);
                    }
                }

            }
        });
    }
}
