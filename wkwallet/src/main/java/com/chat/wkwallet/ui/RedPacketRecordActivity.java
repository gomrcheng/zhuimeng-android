package com.chat.wkwallet.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chat.base.base.WKBaseActivity;
import com.chat.base.config.WKConfig;
import com.chat.base.utils.StringUtils;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActRedpacketRecordBinding;
import com.chat.wkwallet.entity.RedPacketRecordEntity;
import com.chat.wkwallet.entity.RedPacketRecordInTotalEntity;
import com.chat.wkwallet.entity.RedPacketSendRecordEntity;
import com.chat.wkwallet.service.WKCommonModel;
import com.xinbida.wukongim.entity.WKChannelType;

import java.util.ArrayList;
import java.util.List;

public class RedPacketRecordActivity  extends WKBaseActivity<ActRedpacketRecordBinding> {

    private RedPacketRecordAdapter adapter;
    private List<RedPacketRecordEntity> records = new ArrayList<>();

    private String selectedYear = "2025"; // 默认年份
    private int page = 1;
    private int pageSize = 20;
    private boolean isLoading = false;
    private boolean hasMore = true;

    private TextView rightTextView;

    @Override
    protected ActRedpacketRecordBinding getViewBinding() {
        return ActRedpacketRecordBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setTitle(TextView titleTv) {
        super.setTitle(titleTv);
        titleTv.setText(getString(R.string.honbaojilu));
    }

    @Override
    protected void rightLayoutClick() {
        super.rightLayoutClick();
        showYearPickerDialog();
    }

    private void showYearPickerDialog() {
        // 创建年份列表（可以根据需要调整范围）
        List<String> years = new ArrayList<>();
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = currentYear; i >= 2020; i--) {
            years.add(String.valueOf(i));
        }

        // 创建对话框
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        androidx.appcompat.app.AlertDialog dialog;

        // 自定义布局
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_year_picker, null);
        builder.setView(dialogView);

        dialog = builder.create();
        dialog.show();

        // 初始化 RecyclerView
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
        YearPickerAdapter yearAdapter = new YearPickerAdapter(years, selectedYear);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(yearAdapter);

        // 设置年份选择监听器
        yearAdapter.setOnYearSelectedListener(year -> {
            selectedYear = year;
        });

        // 取消按钮
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // 确定按钮
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            selectedYear = yearAdapter.getSelectedYear(); // 获取当前选中的年份
            updateYearData(selectedYear);
            dialog.dismiss();
        });
    }

    private void updateYearData(String year) {
        // 更新右上角显示的年份
        rightTextView.setText( year);
        // 重新加载数据
        page = 1;
        loadReceivedData(page);
        loadRecTotal();
    }

    // 添加获取选中年份的方法到 YearPickerAdapter
// 在 YearPickerAdapter 中添加：
    public String getSelectedYear() {
        return selectedYear;
    }

    @Override
    protected String getRightTvText(TextView textView) {
        this.rightTextView = textView;
        return  getSelectedYear();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
        loadReceivedData(1);
        loadRecTotal();
        wkVBinding.ivAvatar.showAvatar(WKConfig.getInstance().getUid(),WKChannelType.PERSONAL);
    }

    private void loadRecTotal() {
        WKCommonModel.getInstance().getRedPacketRecordsTotal(selectedYear,new WKCommonModel.IGetRedPacketRecordsInTotalListener() {
            @Override
            public void onResult(RedPacketRecordInTotalEntity result) {

                wkVBinding.tvUserName.setText(WKConfig.getInstance().getUserName() + "共收到");
                wkVBinding.tvTotalAmount.setText("¥"+ StringUtils.fen2yuan(result.getTotal_amount()));
                wkVBinding.tvReceivedCount.setText("收到红包"+result.getTotal_num()+"个");
                wkVBinding.tvBestLuck.setText("最佳红包"+result.getLuck_num()+"个");
                wkVBinding.redpacketRecordShu.setVisibility(View.VISIBLE);
                wkVBinding.tvBestLuck.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadSendTotal() {
        WKCommonModel.getInstance().getRedPacketRecordsOutTotal(selectedYear,new WKCommonModel.IGetRedPacketRecordsInTotalListener() {
            @Override
            public void onResult(RedPacketRecordInTotalEntity result) {

                wkVBinding.tvUserName.setText(WKConfig.getInstance().getUserName() + "共发出");
                wkVBinding.tvTotalAmount.setText("¥"+ StringUtils.fen2yuan(result.getTotal_amount()));
                wkVBinding.tvReceivedCount.setText("发出红包"+result.getTotal_num()+"个");
                wkVBinding.redpacketRecordShu.setVisibility(View.GONE);
                wkVBinding.tvBestLuck.setVisibility(View.GONE);
            }
        });
    }

    private void setupViews() {

        // 标签页切换
        wkVBinding.tvReceived.setOnClickListener(v -> {
            wkVBinding.tvReceived.setSelected(true);
            wkVBinding.tvSent.setSelected(false);
            page = 1;
            loadReceivedData(page);
            loadRecTotal();
        });

        wkVBinding.tvSent.setOnClickListener(v -> {
            wkVBinding.tvSent.setSelected(true);
            wkVBinding.tvReceived.setSelected(false);
            page = 1;
            loadSentData(page);
            loadSendTotal();
        });

        // 初始化 RecyclerView
        adapter = new RedPacketRecordAdapter(records);
        wkVBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        wkVBinding.recyclerView.setAdapter(adapter);

        // 添加滚动监听器
        wkVBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    // 检测是否滑到底部
                    if (!isLoading && hasMore && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        page++;
                        loadMoreData(page);
                    }
                }
            }
        });

        // 默认选中“收到的红包”
        wkVBinding.tvReceived.setSelected(true);
    }

    private void loadReceivedData(int page) {
        WKCommonModel.getInstance().getRedPacketRecordsIn(selectedYear, page, pageSize, new WKCommonModel.IGetRedPacketRecordsInListener() {
            @Override
            public void onResult(List<RedPacketRecordEntity> result) {
                records.clear();
                records.addAll(result);
                adapter.notifyDataSetChanged();
                hasMore = result.size() >= 20; // 假设每页20条
            }
        });

    }

    private void loadSentData(int page) {
        WKCommonModel.getInstance().getRedPacketRecordsOut(selectedYear, page, pageSize, new WKCommonModel.IGetRedPacketRecordsOutListener() {
            @Override
            public void onResult(List<RedPacketSendRecordEntity> result) {
                records.clear();
                for (int i = 0; i < result.size(); i++) {
                    Long amount = result.get(i).getAmount();
                    String createDate = result.get(i).getCreateDate();
                    RedPacketRecordEntity redPacketRecordEntity = new RedPacketRecordEntity();
                    redPacketRecordEntity.setOpen_amount(amount.intValue());
                    redPacketRecordEntity.setOpen_date(createDate);
                    redPacketRecordEntity.setSender_name(WKConfig.getInstance().getUserName());
                    redPacketRecordEntity.setSender_uid(WKConfig.getInstance().getUid());
                    records.add(redPacketRecordEntity);
                }
                adapter.notifyDataSetChanged();
                hasMore = result.size() >= 20; // 假设每页20条
            }
        });
    }

    private void loadMoreData(int page) {
        isLoading = true;
        wkVBinding.tvLoading.setVisibility(View.VISIBLE);

        if (wkVBinding.tvReceived.isSelected()) {
            loadReceivedData(page);
        } else {
            loadSentData(page);
        }
    }


}
