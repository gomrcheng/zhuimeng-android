package com.chat.wkredpacket.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chat.base.config.WKApiConfig;
import com.chat.base.glide.GlideUtils;
import com.chat.wkredpacket.R;
import com.chat.wkredpacket.model.RedOneBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RedDetailAdapter extends RecyclerView.Adapter<RedDetailAdapter.ViewHolder> {
    private Context context;
    private List<RedOneBean> list;

    public RedDetailAdapter(Context context, List<RedOneBean> poiInfos) {
        this.context = context;
        this.list = poiInfos;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.reditem_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RedOneBean redOneBean = list.get(position);

        GlideUtils.getInstance().showImg(context, WKApiConfig.getAvatarUrl(redOneBean.getUid()) + "?width=50&height=50", holder.mIvAvatar);

        holder.mTvName.setText(redOneBean.getUname());
        holder.mTvTime.setText(redOneBean.getSave_date());
        holder.mTvMoney.setText(redOneBean.getFee() + context.getString(R.string.rmb));

        if (redOneBean.isIs_luck()) {
            holder.redPackItemLuckyLayout.setVisibility(View.VISIBLE);
        } else {
            holder.redPackItemLuckyLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mIvAvatar;
        TextView mTvName;
        TextView mTvTime;
        TextView mTvMoney;
        LinearLayout redPackItemLuckyLayout;

        public ViewHolder(View view) {
            super(view);
            mIvAvatar = view.findViewById(R.id.red_head_iv);
            mTvName = view.findViewById(R.id.username_tv);
            mTvTime = view.findViewById(R.id.opentime_tv);
            mTvMoney = view.findViewById(R.id.money_tv);
            redPackItemLuckyLayout = view.findViewById(R.id.best_lucky_ll);
        }
    }
}
