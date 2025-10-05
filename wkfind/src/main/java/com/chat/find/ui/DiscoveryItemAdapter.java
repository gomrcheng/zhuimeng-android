package com.chat.find.ui;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.chat.base.config.WKConfig;
import com.chat.base.config.WKSharedPreferencesUtil;
import com.chat.base.endpoint.entity.PersonalInfoMenu;
import com.chat.base.ui.components.CounterView;
import com.chat.find.R;
import com.chat.find.entity.DiscoveryInfoMenu;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 2020-08-12 14:40
 */
public class DiscoveryItemAdapter extends BaseQuickAdapter<DiscoveryInfoMenu, BaseViewHolder> {
    DiscoveryItemAdapter(List<DiscoveryInfoMenu> list) {
        super(R.layout.item_frag_discovery_layout, list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DiscoveryInfoMenu menu) {
        baseViewHolder.setText(R.id.nameTv, menu.text);
        //朋友圈红点
        int count = WKSharedPreferencesUtil.getInstance().getInt(WKConfig.getInstance().getUid() + "_moments_msg_count");
        if(menu.text.equals(R.string.tab_text_monments)){
            CounterView counterView = baseViewHolder.getView(R.id.msgCountTv2);
            if(count >=1 ){
                counterView.setColors(R.color.white, R.color.reminderColor);
                counterView.setCount(count, true);
                counterView.setGravity(Gravity.END);
                counterView.setVisibility(View.VISIBLE);
            }else{
                counterView.setVisibility(View.GONE);
            }
        }

        if(menu.imgResourceID ==0 ){
            Glide.with(getContext())
                    .load(menu.imgUrl)
                    .into((ImageView) baseViewHolder.findView(R.id.imageView));
        }else{
            baseViewHolder.setImageResource(R.id.imageView, menu.imgResourceID);
        }
        if (menu.isNewVersionIv) {
            baseViewHolder.setVisible(R.id.newVersionIv, true);
        } else {
            baseViewHolder.setVisible(R.id.newVersionIv, false);
        }
    }
}
