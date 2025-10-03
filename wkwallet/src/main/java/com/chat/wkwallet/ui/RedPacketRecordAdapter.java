package com.chat.wkwallet.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chat.base.ui.components.AvatarView;
import com.chat.base.utils.StringUtils;
import com.chat.wkwallet.R;
import com.chat.wkwallet.entity.RedPacketRecordEntity;
import com.xinbida.wukongim.entity.WKChannelType;

import java.util.List;

public class RedPacketRecordAdapter extends RecyclerView.Adapter<RedPacketRecordAdapter.ViewHolder> {

    private List<RedPacketRecordEntity> dataList;
    private boolean isLoading = false;
    private boolean hasMore = true;
    private OnLoadMoreListener onLoadMoreListener;


    public RedPacketRecordAdapter(List<RedPacketRecordEntity> dataList) {
        this.dataList = dataList;
    }

    public void addData(List<RedPacketRecordEntity> newData) {
        if (newData != null && !newData.isEmpty()) {
            dataList.addAll(newData);
            notifyDataSetChanged();
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
    }

    public void setLoading(boolean loading) {
        this.isLoading = loading;
    }

    public void setHasMore(boolean more) {
        this.hasMore = more;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_red_packet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RedPacketRecordEntity record = dataList.get(position);
        holder.tvName.setText(record.getSender_name());
        holder.tvDate.setText(record.getOpen_date());
        holder.tvAmount.setText(StringUtils.fen2yuan(record.getOpen_amount()));
        holder.tvUid.setText(record.getSender_uid());
        holder.ivUserAvatar.showAvatar(record.getSender_uid(), WKChannelType.PERSONAL, "");
//        GlideUtils.loadImage(holder.ivUserAvatar, record.getName(), R.drawable.ic_avatar_default);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AvatarView ivUserAvatar;
        TextView tvName, tvDate, tvAmount,tvUid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserAvatar = itemView.findViewById(R.id.ivUserAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvUid = itemView.findViewById(R.id.tvUid);
        }
    }
}
