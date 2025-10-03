package com.chat.wkwallet.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chat.base.utils.StringUtils;
import com.chat.wkwallet.R;
import com.chat.wkwallet.entity.TransactionRecordEntity;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<TransactionRecordEntity> records;

    public TransactionAdapter(List<TransactionRecordEntity> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionRecordEntity record = records.get(position);
        holder.type.setText(record.getTitle());
        holder.amount.setText(StringUtils.fen2yuan(Integer.parseInt(""+record.getAmount())));
        holder.time.setText(record.getCreatedAt());
        holder.balance.setText("余额 " + StringUtils.fen2yuan(Integer.parseInt(""+record.getBalance())));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView type, amount, time, balance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tv_type);
            amount = itemView.findViewById(R.id.tv_amount);
            time = itemView.findViewById(R.id.tv_time);
            balance = itemView.findViewById(R.id.tv_balance);
        }
    }
}
