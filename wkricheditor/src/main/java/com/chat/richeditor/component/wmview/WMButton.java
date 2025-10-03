package com.chat.richeditor.component.wmview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class WMButton extends AppCompatButton {

    public WMButton(@NonNull Context context) {
        super(context);
    }

    public WMButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WMButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}