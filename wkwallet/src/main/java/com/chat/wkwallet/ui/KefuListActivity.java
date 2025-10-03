package com.chat.wkwallet.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chat.base.base.WKBaseActivity;
import com.chat.base.endpoint.EndpointManager;
import com.chat.base.endpoint.EndpointSID;
import com.chat.base.endpoint.entity.ChatViewMenu;
import com.chat.wkwallet.R;
import com.chat.wkwallet.databinding.ActKefuListLayoutBinding;
import com.chat.wkwallet.entity.KefuEntity;
import com.chat.wkwallet.service.WKCommonModel;
import com.xinbida.wukongim.entity.WKChannelType;

import java.util.ArrayList;
import java.util.List;

public class KefuListActivity extends WKBaseActivity<ActKefuListLayoutBinding> {

    private KefuAdapter adapter;
    private List<KefuEntity> customers = new ArrayList<>();



    @Override
    protected ActKefuListLayoutBinding getViewBinding() {
        return ActKefuListLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setTitle(TextView titleTv) {
        super.setTitle(titleTv);
        titleTv.setText(getString(R.string.kefu_list));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupRecyclerView();
    }


    private void setupRecyclerView() {
        adapter = new KefuAdapter(customers);
        wkVBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        wkVBinding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new KefuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(KefuEntity customer, int position) {
                EndpointManager.getInstance().invoke(EndpointSID.chatView, new ChatViewMenu(KefuListActivity.this, customer.getUid(), WKChannelType.PERSONAL, 0, false));
            }
        });
        WKCommonModel.getInstance().customerservices(new WKCommonModel.IGetKefuListListener() {
            @Override
            public void onResult(List<KefuEntity> result) {
                customers.addAll(result);
                adapter.notifyDataSetChanged();
            }
        });
    }

}