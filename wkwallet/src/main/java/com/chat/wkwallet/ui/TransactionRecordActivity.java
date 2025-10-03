package com.chat.wkwallet.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chat.base.base.WKBaseActivity;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActTransactionRecordLayoutBinding;
import com.chat.wkwallet.entity.TransactionRecordEntity;
import com.chat.wkwallet.service.WKCommonModel;

import java.util.ArrayList;
import java.util.List;

public class TransactionRecordActivity extends WKBaseActivity<ActTransactionRecordLayoutBinding> {

    private TransactionAdapter adapter;
    private List<TransactionRecordEntity> records = new ArrayList<>();
    private int page = 1;
    private int page_size = 10;
    private boolean isLoading = false;

    @Override
    protected ActTransactionRecordLayoutBinding getViewBinding() {
        return ActTransactionRecordLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupRecyclerView();
        loadFirstPage(); // 加载第一页
    }

    @Override
    protected void setTitle(TextView titleTv) {
        super.setTitle(titleTv);
        titleTv.setText(getString(R.string.jiaoyijilu));
    }

    private void setupRecyclerView() {
        adapter = new TransactionAdapter(records);
        wkVBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        wkVBinding.recyclerView.setAdapter(adapter);

        // 上拉加载更多
        wkVBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && !isLoading) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && !isLoading) {
                        loadNextPage();
                    }
                }
            }
        });
    }

    private void loadFirstPage() {
        isLoading = true;
        // 模拟网络请求

        WKCommonModel.getInstance().getwalletrecords(1, page_size, new WKCommonModel.IGetWalletRecordsListener() {
            @Override
            public void onResult(List<TransactionRecordEntity> result) {
                records.addAll(result);
                adapter.notifyDataSetChanged();
                isLoading = false;
            }
        });

    }

    //

    private void loadNextPage() {
        isLoading = true;
        TextView tvLoadMore = findViewById(R.id.tvLoadMore);
        tvLoadMore.setVisibility(View.VISIBLE);
        WKCommonModel.getInstance().getwalletrecords(++page, page_size, new WKCommonModel.IGetWalletRecordsListener() {
            @Override
            public void onResult(List<TransactionRecordEntity> result) {
                records.addAll(result);
                adapter.notifyDataSetChanged();
                tvLoadMore.setVisibility(View.GONE);
                isLoading = false;
            }
        });
    }

}
