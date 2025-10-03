package com.chat.wkwallet.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.chat.wkwallet.R;

import java.util.List;

public class QuickAmountAdapter extends RecyclerView.Adapter<QuickAmountAdapter.ViewHolder> {

    private List<String> amounts; // 存储金额字符串，如 "0", "1", "10"...
    private OnAmountClickListener listener; // 点击监听器

    public QuickAmountAdapter(List<String> amounts) {
        this.amounts = amounts;
    }

    // 设置点击监听器
    public void setOnAmountClickListener(OnAmountClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quick_amount, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String amount = amounts.get(position);
        holder.tvAmount.setText("¥" + amount);

        // 设置点击事件
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAmountClick(amount);
            }
        });
    }

    @Override
    public int getItemCount() {
        return amounts.size();
    }

    // ViewHolder 类
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmount;

        ViewHolder(View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tv_amount);
        }
    }

    // 点击事件接口
    public interface OnAmountClickListener {
        void onAmountClick(String amount);
    }
}
