package com.chat.file;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chat.base.base.WKBaseActivity;
import com.chat.file.databinding.ActChatFileLayoutBinding;
import com.chat.file.databinding.ActChooseFileLayoutBinding;
import com.chat.file.databinding.ActPreviewBinding;
import com.seapeak.docviewer.DocViewerFragment;
import com.seapeak.docviewer.config.DocConfig;

public class PreviewActivity extends WKBaseActivity<ActPreviewBinding> {

    @Override
    protected ActPreviewBinding getViewBinding() {
        return ActPreviewBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setTitle(TextView titleTv) {
        titleTv.setText("文件预览");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DocConfig config = (DocConfig) getIntent().getSerializableExtra("config");
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new DocViewerFragment(config)).commit();
    }
}
