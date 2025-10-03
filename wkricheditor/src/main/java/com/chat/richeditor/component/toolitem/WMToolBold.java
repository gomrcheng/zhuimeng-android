package com.chat.richeditor.component.toolitem;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.chat.base.ui.Theme;
import com.chat.richeditor.R;
import com.chat.richeditor.component.wmview.WMEditText;
import com.chat.richeditor.component.wmview.WMImageButton;

import java.util.ArrayList;
import java.util.List;

public class WMToolBold extends WMToolItem {
    private static final String TAG = "WMToolBold";

    public void setStyle(int start, int end) {
        int start_fact = start;
        int end_fact = end;
        Editable s = getEditText().getEditableText();
        StyleSpan[] styleSpans = s.getSpans(start - 1, end + 1, StyleSpan.class);
        for (StyleSpan styleSpan : styleSpans) {
            if (styleSpan.getStyle() == Typeface.BOLD) {
                int spanStart = s.getSpanStart(styleSpan);
                int spanEnd = s.getSpanEnd(styleSpan);
                if (spanStart != spanEnd) {
                    if (spanStart < start) {
                        start_fact = spanStart;
                    }

                    if (spanEnd > end) {
                        end_fact = spanEnd;
                    }

                    if (spanStart <= start && spanEnd >= end) {
                        return;
                    } else {
                        s.removeSpan(styleSpan);
                    }
                }
            }
        }
        s.setSpan(new StyleSpan(Typeface.BOLD), start_fact, end_fact, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public void removeStyle(int start, int end) {
        Editable s = getEditText().getEditableText();
        StyleSpan[] styleSpans = s.getSpans(start, end, StyleSpan.class);
        for (StyleSpan styleSpan : styleSpans) {
            if (styleSpan.getStyle() == Typeface.BOLD) {
                int spanStart = s.getSpanStart(styleSpan);
                int spanEnd = s.getSpanEnd(styleSpan);
                if (spanStart != spanEnd) {
                    if (spanStart <= start && spanEnd >= end) {
                        s.removeSpan(styleSpan);
                        s.setSpan(new StyleSpan(Typeface.BOLD), spanStart, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        s.setSpan(new StyleSpan(Typeface.BOLD), end, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        }
    }

    @Override
    public void applyStyle(int start, int end) {
        if (getStyle_state()) {
            setStyle(start, end);
        } else {
            removeStyle(start, end);
        }
    }

    @Override
    public List<View> getView(Context context) {
        WMImageButton imageButton = new WMImageButton(context);

        imageButton.setImageResource(R.drawable.icon_text_bold);

        view = imageButton;
        view.setOnClickListener(v -> {
            if (null == getEditText()) {
                return;
            }
            WMEditText editText = getEditText();
            Editable s = editText.getEditableText();
            int selStart = editText.getSelectionStart();
            int selEnd = editText.getSelectionEnd();
            if (selStart < selEnd) {
                if (getStyle_state()) {
                    removeStyle(selStart, selEnd);
                } else {
                    setStyle(selStart, selEnd);
                }
            }
            setStyle_state(!getStyle_state());
        });
        List<View> views = new ArrayList<>();
        views.add(view);
        Theme.setColorFilter(context, (AppCompatImageButton) view, R.color.popupTextColor);
        return views;
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        if (null == getEditText()) {
            return;
        }
        boolean Bold_flag = false;
        WMEditText editText = this.getEditText();
        Editable s = editText.getEditableText();
        if (selStart > 0 && selStart == selEnd) {
            StyleSpan[] styleSpans = s.getSpans(selStart - 1, selStart, StyleSpan.class);
            for (StyleSpan styleSpan : styleSpans) {
                if (styleSpan.getStyle() == Typeface.BOLD) {
                    if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                        Bold_flag = true;
                        StyleSpan[] styleSpans_next = s.getSpans(selStart, selStart + 1, StyleSpan.class);
                        for (StyleSpan styleSpan_next : styleSpans_next) {
                            if (styleSpan_next.getStyle() == Typeface.BOLD) {
                                if (s.getSpanStart(styleSpan_next) != s.getSpanEnd(styleSpan_next)) {
                                    if (styleSpan_next != styleSpan) {
                                        setStyle(selStart - 1, selStart + 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (selStart != selEnd) {
            StyleSpan[] styleSpans = s.getSpans(selStart, selEnd, StyleSpan.class);
            for (StyleSpan styleSpan : styleSpans) {
                if (styleSpan.getStyle() == Typeface.BOLD) {
                    if (s.getSpanStart(styleSpan) <= selStart
                            && s.getSpanEnd(styleSpan) >= selEnd) {
                        if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                            Bold_flag = true;
                        }
                    }
                }
            }
        }
        setStyle_state(Bold_flag);
    }

    @Override
    public void onCheckStateUpdate() {
        ((AppCompatImageButton) view).setColorFilter(new PorterDuffColorFilter(getStyle_state() ? Theme.colorAccount : ContextCompat.getColor(getEditText().getContext(), R.color.popupTextColor), PorterDuff.Mode.MULTIPLY));
    }

}