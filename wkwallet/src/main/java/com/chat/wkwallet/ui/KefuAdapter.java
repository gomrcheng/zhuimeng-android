package com.chat.wkwallet.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chat.base.ui.components.AvatarView;
import com.chat.wkwallet.R;
import com.chat.wkwallet.entity.KefuEntity;
import com.xinbida.wukongim.entity.WKChannelType;

import java.util.List;

public class KefuAdapter extends RecyclerView.Adapter<KefuAdapter.ViewHolder> {

    private List<KefuEntity> customers;

    public KefuAdapter(List<KefuEntity> customers) {
        this.customers = customers;
    }
    public interface OnItemClickListener {
        void onItemClick(KefuEntity customer, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kefu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KefuEntity customer = customers.get(position);
        holder.tvName.setText(customer.getName());
        holder.ivAvatar.showAvatar(customer.getUid(), WKChannelType.PERSONAL, "");
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
        AvatarView ivAvatar;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.userCardAvatarIv);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}