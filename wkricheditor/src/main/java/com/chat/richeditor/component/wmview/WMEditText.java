package com.chat.richeditor.component.wmview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.widget.AppCompatEditText;

import com.chat.base.utils.WKReader;
import com.chat.richeditor.component.span.WMListClickToSwitchSpan;
import com.chat.richeditor.component.toolitem.WMToolItem;

import java.util.ArrayList;
import java.util.List;

public class WMEditText extends AppCompatEditText {
    private static final String TAG = "WMEditText";

    private List<WMToolItem> tools = new ArrayList<>();

    private boolean editable = true;


    TextWatcher textWatcher = new TextWatcher() {
        int input_start;
        int input_end;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            input_start = start;
            input_end = start + count;

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (editable) {
                if (input_end > input_start) {
                    for (WMToolItem tool : tools) {
                        tool.applyStyle(input_start, input_end);
                    }
                }
            }
        }
    };

    public WMEditText(Context context) {
        this(context, null);
    }

    public WMEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WMEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView();

    }

    @SuppressLint("NewApi")
    public void InitView() {
        //  this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // this.setLayerType(LAYER_TYPE_SOFTWARE,null);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.requestFocus();
        this.setBackgroundColor(0);
//        int p = WMUtil.getPixelByDp(getContext(), 25);
//        this.setPadding(p, p, p, p);
//        int space = WMUtil.getPixelByDp(getContext(), 10);
//        this.setLineSpacing(space, 1);
//        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        this.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE
                | EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        addTextChangedListener(textWatcher);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        if (editable) {
            if (WKReader.isNotEmpty(tools)) {
                for (WMToolItem tool : tools) {
                    tool.onSelectionChanged(selStart, selEnd);
                }
            }
        }
        super.onSelectionChanged(selStart,selEnd);
    }


    public void setupWithToolContainer(WMToolContainer toolContainer) {
        this.tools = toolContainer.getTools();
        for (WMToolItem tool : tools) {
            tool.setEditText(this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = false;
        float x = event.getX() - getPaddingLeft();
        float y = event.getY() - getPaddingTop();
        Editable s = getEditableText();
        WMListClickToSwitchSpan[] clickToSwitchSpans = s.getSpans(0, s.length(), WMListClickToSwitchSpan.class);

        for (WMListClickToSwitchSpan clickToSwitchSpan : clickToSwitchSpans) {
            if (clickToSwitchSpan.onTouchEvent(event, x, y)) {
                flag = true;
            }
        }
        invalidate();
        return flag || super.onTouchEvent(event);
    }



    public void setEditable(boolean editable) {
        this.editable = editable;
        setEnabled(editable);
        setFocusable(editable);
    }
}