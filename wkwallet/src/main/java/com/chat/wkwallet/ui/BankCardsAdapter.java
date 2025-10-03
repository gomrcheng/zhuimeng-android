package com.chat.wkwallet.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chat.wkwallet.R;
import com.chat.wkwallet.entity.BankCardDetailEntity;

import java.util.List;


public class BankCardsAdapter extends RecyclerView.Adapter<BankCardsAdapter.BankCardViewHolder> {
    private List<BankCardDetailEntity> bankCardsList;

    private OnItemClickListener listener;

    public BankCardsAdapter(List<BankCardDetailEntity> bankCardsList) {
        this.bankCardsList = bankCardsList;
    }
    public interface OnItemClickListener {
        void onItemClick(BankCardDetailEntity item);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public BankCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bank_card, parent, false);
        return new BankCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BankCardViewHolder holder, int position) {
        BankCardDetailEntity card = bankCardsList.get(position);
        holder.bind(card);
        BankCardDetailEntity item = bankCardsList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankCardsList != null ? bankCardsList.size() : 0;
    }

    class BankCardViewHolder extends RecyclerView.ViewHolder {
        private TextView bankNameTv;
        private TextView cardTypeTv;
        private TextView cardNumberTv;

        public BankCardViewHolder(View itemView) {
            super(itemView);
            // 绑定item_bank_card.xml中的视图组件
            bankNameTv = itemView.findViewById(R.id.tv_card_name);
            cardTypeTv = itemView.findViewById(R.id.tv_card_type);
            cardNumberTv = itemView.findViewById(R.id.tv_card_number);
        }

        public void bind(BankCardDetailEntity card) {
            // 根据BankCardDetailEntity的字段设置数据
            // 注意：以下字段名需要根据BankCardDetailEntity的实际字段进行调整
            if (card.getBankname() != null) {
                bankNameTv.setText(card.getBankname());
            }

            if (card.getBankcardType() != null) {
                cardTypeTv.setText(card.getBankcardType());
            }

            if (card.getBankcard() != null) {
                // 格式化卡号显示，隐藏部分数字
                cardNumberTv.setText(card.getBankcard());
            }
        }
    }
}
