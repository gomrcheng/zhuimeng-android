package com.chat.wkwallet.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.chat.wkwallet.R;

import java.util.List;

public class YearPickerAdapter  extends RecyclerView.Adapter<YearPickerAdapter.ViewHolder> {

    private List<String> years;
    private String selectedYear;
    private OnYearSelectedListener listener;

    public String getSelectedYear() {
        return selectedYear;
    }

    public interface OnYearSelectedListener {
        void onYearSelected(String year);
    }

    public YearPickerAdapter(List<String> years, String selectedYear) {
        this.years = years;
        this.selectedYear = selectedYear;
    }

    public void setOnYearSelectedListener(OnYearSelectedListener listener) {
        this.listener = listener;
    }

    public void setSelectedYear(String selectedYear) {
        this.selectedYear = selectedYear;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_year, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String year = years.get(position);
        holder.tvYear.setText(year);

        if (year.equals(selectedYear)) {
            holder.tvYear.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.colorDark));
            holder.tvYear.setTextSize(22);
        } else {
            holder.tvYear.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.black));
            holder.tvYear.setTextSize(16);
        }

        holder.itemView.setOnClickListener(v -> {
            selectedYear = year;
            notifyDataSetChanged();
            if (listener != null) {
                listener.onYearSelected(year);
            }
        });
    }

    @Override
    public int getItemCount() {
        return years.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvYear = itemView.findViewById(R.id.tvYear);
        }
    }
}