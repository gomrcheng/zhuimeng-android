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
import com.chat.wkwallet.R;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    private List<CurrencyItem> dataList;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(CurrencyItem item);
    }

    public CurrencyAdapter(List<CurrencyItem> dataList, OnItemClickListener listener, Context context) {
        this.dataList = dataList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CurrencyItem item = dataList.get(position);
        GlideUtils.getInstance().showImg(this.context, item.iconUrl, holder.ivIcon);
        holder.tvName.setText(item.name);
        if(item.type!=null){
            holder.tvCoinType.setText(item.type);
        }
        holder.tvCoinTips.setText(item.tips);
        holder.tvCoinMinTrade.setText(item.minTrade);
        // 设置选中状态
        holder.ivCheck.setVisibility(item.isSelected ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName,tvCoinType,tvCoinTips,tvCoinMinTrade;
        ImageView ivCheck;

        ViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCoinType = itemView.findViewById(R.id.tv_coin_type);
            tvCoinTips = itemView.findViewById(R.id.tv_coin_tips);
            tvCoinMinTrade = itemView.findViewById(R.id.tv_coin_minTrade);
            ivCheck = itemView.findViewById(R.id.iv_check);
        }
    }

    // 数据模型类
    public static class CurrencyItem {
        String iconUrl;
        String name;
        String type;
        String minTrade;
        String tips;
        boolean isSelected;

        public CurrencyItem(String iconUrl, String name,String type,String minTrade,String tips, boolean isSelected) {
            this.iconUrl = iconUrl;
            this.name = name;
            this.type = type;
            this.minTrade = minTrade;
            this.tips = tips;
            this.isSelected = isSelected;
        }
    }
}
