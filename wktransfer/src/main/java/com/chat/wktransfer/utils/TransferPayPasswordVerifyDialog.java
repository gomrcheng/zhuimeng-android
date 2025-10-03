package com.chat.wktransfer.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chat.base.endpoint.EndpointManager;
import com.chat.base.utils.WKDialogUtils;
import com.chat.wktransfer.R;


public class TransferPayPasswordVerifyDialog extends Dialog {
    private TextView tvAction;
    private View llMoney;
    private TextView tvMoney;
    private TransferPasswordInputView passwordInputView;
    private final int PASS_WORD_LENGTH = 6;
    private Context context;

    private String action;
    private String money;

    private OnInputFinishListener onInputFinishListener;

    public TransferPayPasswordVerifyDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
    }

    public TransferPayPasswordVerifyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TransferPayPasswordVerifyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_pay_password_verify_dialog);
        setCanceledOnTouchOutside(false);
        initView();
        Window window = getWindow();
        if (window != null) {
            // 设置全屏宽度
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

            // 设置Dialog显示在屏幕底部
            window.setGravity(Gravity.BOTTOM);  // 设置Dialog底部对齐

            // 设置背景透明
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // 设置背景透明度（可选）
            window.setDimAmount(0.4f);

            // 设置垂直偏移量（如果需要，可以调整Dialog的位置）
            WindowManager.LayoutParams params = window.getAttributes();
            params.y = 0;  // 设置距离屏幕底部的距离
            window.setAttributes(params);
        }

    }

    private void initView() {
        findViewById(R.id.tv_forget_password).setOnClickListener(v -> {
            WKDialogUtils.getInstance().showDialog(context, "设置支付密码", "是否忘记支付密码", true, "否", "是", 0, ContextCompat.getColor(context, R.color.red), index -> {
                if (index == 1) {
                    EndpointManager.getInstance().invoke("set_pay_password",context);
                }
            });
        });
        findViewById(R.id.ivClose).setOnClickListener(v -> {
            cancel();
        });
        tvAction = findViewById(R.id.tvAction);
        if (action != null) {
            tvAction.setText(action);
        }
        llMoney = findViewById(R.id.llMoney);
        tvMoney = findViewById(R.id.tvMoney);
        if (!TextUtils.isEmpty(money)) {
            tvMoney.setText(money);
            llMoney.setVisibility(View.VISIBLE);
        } else {
            llMoney.setVisibility(View.GONE);
        }
        passwordInputView = findViewById(R.id.passwordInputView);
        passwordInputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == PASS_WORD_LENGTH) {
                    dismiss();
                    if (onInputFinishListener != null) {
                        onInputFinishListener.onInputFinish(s.toString());
                    }
                }
            }
        });
        // 在Dialog显示后请求焦点并弹出输入法
        passwordInputView.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(passwordInputView, InputMethodManager.SHOW_IMPLICIT);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (ScreenUtil.getScreenWidth(getContext()) * 0.7);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void setAction(String action) {
        this.action = action;
        if (tvAction != null) {
            tvAction.setText(action);
        }
    }

    public void setMoney(String money) {
        this.money = money;
        if (tvMoney != null) {
            tvMoney.setText(money);
        }
        if (llMoney != null) {
            if (!TextUtils.isEmpty(money)) {
                llMoney.setVisibility(View.VISIBLE);
            } else {
                llMoney.setVisibility(View.GONE);
            }
        }
    }




    public void setOnInputFinishListener(OnInputFinishListener onInputFinishListener) {
        this.onInputFinishListener = onInputFinishListener;
    }

    public interface OnInputFinishListener {
        void onInputFinish(String password);
    }
}
