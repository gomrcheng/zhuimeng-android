package com.chat.find.ui;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.chat.base.config.WKApiConfig;
import com.chat.base.endpoint.entity.PersonalInfoMenu;
import com.chat.find.R;
import com.chat.find.entity.WkFindList;

import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * 2020-08-12 14:40
 * 个人中心
 */
public class FindItemAdapter extends BaseQuickAdapter<WkFindList, BaseViewHolder> {
    FindItemAdapter(List<WkFindList> list) {
        super(R.layout.item_find_layout, list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, WkFindList wkFindList) {
        baseViewHolder.setText(R.id.nameTv, wkFindList.getName());
        ImageView iv = baseViewHolder.getView(R.id.imageView);
        Glide.with(getContext())
                .load(WKApiConfig.getShowUrl(wkFindList.getIcon()))
                .into(iv);
    }
}
