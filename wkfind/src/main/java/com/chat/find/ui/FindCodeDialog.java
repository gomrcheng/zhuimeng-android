package com.chat.find.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.chat.find.R;


/**
 * 发现   权限控制弹出框
 */
public class FindCodeDialog extends Dialog {

    private TextView dialogTitle,confirm,cancel;
    private String contextString,cancelString,confirmString;
    private EditText etCode;
    private String titleDialog;

    public void setTitleDialog(String titleDialog) {
        this.titleDialog = titleDialog;
    }

    private Context context;
    public FindCodeDialog(@NonNull Context context) {
        super(context);
        this.context =context;
    }

    public void setContextString(String contextString) {
        this.contextString = contextString;
    }
    public void setCancelString(String cancelString) {
        this.cancelString = cancelString;
    }
    public void setConfirmString(String confirmString) {
        this.confirmString = confirmString;
    }

    public String getContextString() {
        return contextString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_find);
        Window window = this.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView();

    }

    private void initView() {
        dialogTitle = findViewById(R.id.dialog_title);
        confirm = findViewById(R.id.version_queren);
        cancel = findViewById(R.id.version_quxiao);
        etCode = findViewById(R.id.etCode);
        dialogTitle.setText(titleDialog);
        if(!TextUtils.isEmpty(cancelString)){
            cancel.setText(cancelString);
        }
        if(!TextUtils.isEmpty(confirmString)){
            confirm.setText(confirmString);
        }
        cancel.setOnClickListener(v -> {
            if (cancelListener != null){
                cancelListener.onCancelListener();
            }
        });
        confirm.setOnClickListener(v -> {
            if (confirmListener != null){
                String text = etCode.getText().toString().trim();
                confirmListener.onConfirmListener(text);
            }
        });

    }
    private CancelListener cancelListener;
    private ConfirmListener confirmListener;

    public void setOnCancelListener(CancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public void setOnConfirmListener(ConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    public interface ConfirmListener{
        void onConfirmListener(String text);
    }
    public interface CancelListener{
        void onCancelListener();
    }
}
