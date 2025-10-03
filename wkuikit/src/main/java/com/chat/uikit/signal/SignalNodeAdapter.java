package com.chat.uikit.signal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chat.base.config.WKApiConfig;
import com.chat.uikit.R;

import java.util.List;

public class SignalNodeAdapter extends RecyclerView.Adapter<SignalNodeAdapter.ViewHolder> {

    private List<SignalNode> nodes;

    public SignalNodeAdapter(List<SignalNode> nodes) {
        this.nodes = nodes;
    }
    // 添加接口定义
    public interface OnSignalNodeClickListener {
        void onNodeClick(SignalNode node, int position);
    }
    private OnSignalNodeClickListener listener; // 添加监听器字段

    // 添加设置监听器的方法
    public void setOnNodeClickListener(OnSignalNodeClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.signal_item_node, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SignalNode node = nodes.get(position);
        holder.tvNodeName.setText(node.getName());
        holder.tvSpeed.setText(node.getSpeed());
        holder.tvDesc.setText(node.getDesc());
        holder.tvrealLine.setText(node.getRealLine());
        Integer nodeSpeed = Integer.parseInt(node.getSpeed().replaceAll("[^0-9]", ""));
        if(nodeSpeed<300){
            holder.ivSignalIcon.setImageResource(R.mipmap.dian_green);
        }else if(nodeSpeed>300  && nodeSpeed<600){
            holder.ivSignalIcon.setImageResource(R.mipmap.dian_yellow);
        }else{
            holder.ivSignalIcon.setImageResource(R.mipmap.dian_red);
        }

        if(WKApiConfig.baseUrl.contains(node.getRealLine())){
            holder.ivSelect.setVisibility(View.VISIBLE);
        }
        // 点击事件：切换选中状态
        // 修改点击事件，添加回调
        holder.itemView.setOnClickListener(v -> {
            // 通知Activity
            if (listener != null) {
                listener.onNodeClick(node, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNodeName;
        TextView tvSpeed;
        TextView tvDesc;
        TextView tvrealLine;
        ImageView ivSignalIcon;
        ImageView ivSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNodeName = itemView.findViewById(R.id.tvNodeName);
            ivSelect = itemView.findViewById(R.id.iv_item_select);
            tvSpeed = itemView.findViewById(R.id.tvSpeed);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            ivSignalIcon = itemView.findViewById(R.id.ivSignalIcon);
            tvrealLine = itemView.findViewById(R.id.tv_line_real);
        }
    }
}
