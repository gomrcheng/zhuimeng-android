package com.chat.wkwallet.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chat.base.glide.GlideUtils;
import com.chat.base.ui.components.AvatarView;
import com.chat.wkwallet.R;
import com.chat.wkwallet.entity.ChannelsEntity;
import com.chat.wkwallet.entity.KefuEntity;
import com.xinbida.wukongim.entity.WKChannelType;

import java.util.List;

public class RechargeChannelsAdapter extends RecyclerView.Adapter<RechargeChannelsAdapter.ViewHolder> {

    private List<ChannelsEntity> customers;
    private Context context;

    public RechargeChannelsAdapter(Context context,List<ChannelsEntity> customers) {
        this.customers = customers;
        this.context = context;
    }
    public interface OnItemClickListener {
        void onItemClick(ChannelsEntity customer, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recharge_channels, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChannelsEntity customer = customers.get(position);
        holder.tvRechargeChannel.setText(customer.getTitle());
        holder.tvItemRechargeChannelType.setText(""+customer.getType());
        GlideUtils.getInstance().showImg(context, customer.getIcon(), holder.ivRechargeChannel);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(customer, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRechargeChannel;
        TextView tvRechargeChannel,tvItemRechargeChannelType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRechargeChannel = itemView.findViewById(R.id.item_recharge_channel_iv);
            tvRechargeChannel = itemView.findViewById(R.id.item_recharge_channel_tv);
            tvItemRechargeChannelType = itemView.findViewById(R.id.item_recharge_channel_type_tv);
        }
    }
}