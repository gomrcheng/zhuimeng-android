package com.chat.richeditor.component.toolitem;

import android.content.Context;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ParagraphStyle;
import android.view.View;

import com.chat.richeditor.R;
import com.chat.richeditor.component.span.WMListClickToSwitchSpan;
import com.chat.richeditor.component.util.WMUtil;
import com.chat.richeditor.component.wmview.WMImageButton;

import java.util.ArrayList;
import java.util.List;

public class WMToolListClickToSwitch extends WMToolItem implements View.OnClickListener {

    private static final String TAG = "WMToolListClickToSwitch";

    private WMImageButton checkButton;
    private WMImageButton starButton;

    @Override
    public void applyStyle(int start, int end) {
        Editable s = getEditText().getEditableText();
        if (start > 0 && s.charAt(start - 1) == '\n') {
            return;
        }

        WMListClickToSwitchSpan[] styles = s.getSpans(start - 1, start, WMListClickToSwitchSpan.class);
        if (styles != null && styles.length > 0) {
            WMListClickToSwitchSpan style = styles[styles.length - 1];
            int spanStart = s.getSpanStart(style);
            int spanEnd = s.getSpanEnd(style);
            if (s.subSequence(start, end).toString().equals("\n")) {
                if (spanEnd == end - 1) {
                    if (spanEnd == spanStart + 1) {
                        s.delete(end - 2, end);
                    } else {
                        s.insert(end, "\u200B");
                        s.setSpan(new WMListClickToSwitchSpan(style.getType(), false), end, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        }
    }

    @Override
    public List<View> getView(final Context context) {
        WMListClickToSwitchSpan.context = context;
        checkButton = new WMImageButton(context);
        starButton = new WMImageButton(context);
        checkButton.setImageResource(R.drawable.icon_text_checkview);
        starButton.setImageResource(R.drawable.icon_text_star);

        checkButton.setOnClickListener(this);
        starButton.setOnClickListener(this);

        List<View> views = new ArrayList<>();
        views.add(checkButton);
        views.add(starButton);
        return views;
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        Editable s = getEditText().getEditableText();
        if (selStart < s.length()) {
            char Char = s.charAt(selStart);
            if (Char == '\u200B') {
                WMListClickToSwitchSpan[] listBulletSpans = s.getSpans(selStart, selStart + 1, WMListClickToSwitchSpan.class);
                if (listBulletSpans != null && listBulletSpans.length > 0) {
                    if (selStart + 1 > selEnd) {
                        selEnd = selStart + 1;
                    }
                    getEditText().setSelection(selStart + 1, selEnd);
                }
            }
        }
    }

    @Override
    public void onCheckStateUpdate() {

    }

    @Override
    public void onClick(View v) {
        if (null == getEditText()) {
            return;
        }
        WMListClickToSwitchSpan.WMDrawableType type = WMListClickToSwitchSpan.WMDrawableType.CHECK;
        if (v == starButton) {
            type = WMListClickToSwitchSpan.WMDrawableType.STAR;
        }

        Editable s = getEditText().getEditableText();
        int selStart = getEditText().getSelectionStart();
        int selEnd = getEditText().getSelectionEnd();

        int textLead = WMUtil.getTextLead(selStart, getEditText());
        int textEnd = WMUtil.getTextEnd(selEnd, getEditText());

        boolean hasOthers = false;
        WMListClickToSwitchSpan[] spans = s.getSpans(textLead, textEnd, WMListClickToSwitchSpan.class);
        for (WMListClickToSwitchSpan span : spans) {
            if (span.getType() != type) {
                hasOthers = true;
            }
        }
        if (!hasOthers  && spans.length == WMUtil.getParagraphCount(getEditText(), selStart, selEnd)) {
            for (WMListClickToSwitchSpan span : spans) {
                int spanStart = s.getSpanStart(span);
                if (s.charAt(spanStart) == '\u200B') {
                    s.delete(spanStart, spanStart + 1);
                    s.removeSpan(span);
                }
            }
        } else {
            for (int i = textLead; i <= textEnd; ) {
                int end = WMUtil.getTextEnd(i, getEditText());
                WMListClickToSwitchSpan[] spans1 = s.getSpans(i, end, WMListClickToSwitchSpan.class);
                if (spans1 == null || spans1.length == 0 || spans1[spans1.length - 1].getType() != type) {
                    ParagraphStyle[] paragraphStyles = s.getSpans(i, end, ParagraphStyle.class);
                    for (ParagraphStyle paragraphStyle : paragraphStyles) {
                        int spanStart = s.getSpanStart(paragraphStyle);
                        int spanEnd = s.getSpanEnd(paragraphStyle);
                        int flag = s.getSpanFlags(paragraphStyle);
                        s.removeSpan(paragraphStyle);
                        if (spanStart < i) {
                            s.setSpan(paragraphStyle, spanStart, i, flag);
                        }
                        if (spanEnd > end) {
                            s.setSpan(paragraphStyle, end, spanEnd, flag);
                        }
                    }
                    if (i == s.length() || s.charAt(i) != '\u200B') {
                        s.insert(i, "\u200B");
                        textEnd++;
                        end = WMUtil.getTextEnd(i, getEditText());
                    }

                    s.setSpan(new WMListClickToSwitchSpan(type, false), i, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                i = end;
                if (i == textEnd) {
                    break;
                }
            }
            onSelectionChanged(getEditText().getSelectionStart(), getEditText().getSelectionEnd());

        }
    }

}