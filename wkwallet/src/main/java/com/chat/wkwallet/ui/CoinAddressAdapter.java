package com.chat.wkwallet.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chat.wkwallet.R;
import com.chat.wkwallet.entity.BankCardDetailEntity;
import com.chat.wkwallet.entity.UserWalletAddress;

import java.util.List;


public class CoinAddressAdapter extends RecyclerView.Adapter<CoinAddressAdapter.CoinAddressViewHolder> {
    private List<UserWalletAddress> coinWalletList;

    private OnItemClickListener listener;

    public CoinAddressAdapter(List<UserWalletAddress> coinWalletList) {
        this.coinWalletList = coinWalletList;
    }
    public interface OnItemClickListener {
        void onItemClick(UserWalletAddress item);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(UserWalletAddress item);
    }

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    @Override
    public CoinAddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coin_address, parent, false);
        return new CoinAddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CoinAddressViewHolder holder, int position) {
        UserWalletAddress item = coinWalletList.get(position);
        holder.bind(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            }
        });
        // 设置长按监听
        holder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(item);
                return true;
            }
            return false;
        });
    }
    public void removeItem(UserWalletAddress item) {
        int position = coinWalletList.indexOf(item);
        if (position != -1) {
            coinWalletList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return coinWalletList != null ? coinWalletList.size() : 0;
    }

    class CoinAddressViewHolder extends RecyclerView.ViewHolder {
        private TextView mainCoinTypeTv, coinSymbolTv, coinAddressTv;

        public CoinAddressViewHolder(View itemView) {
            super(itemView);
            mainCoinTypeTv = itemView.findViewById(R.id.tv_main_coin_type);
            coinSymbolTv = itemView.findViewById(R.id.tv_coin_symbol);
            coinAddressTv = itemView.findViewById(R.id.tv_coin_address);
        }

        public void bind(UserWalletAddress address) {
            if (address.getSymbol() != null) {
                mainCoinTypeTv.setText(address.getAlias());
            }
            if (address.getAddress() != null) {
                coinAddressTv.setText(address.getAddress());
            }
        }
    }
}
