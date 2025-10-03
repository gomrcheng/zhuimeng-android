package com.chat.file;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chat.base.act.WKWebViewActivity;
import com.chat.base.base.WKBaseActivity;
import com.chat.base.config.WKApiConfig;
import com.chat.base.config.WKConstants;
import com.chat.base.net.ud.WKProgressManager;
import com.chat.base.net.ud.WKDownloader;
import com.chat.base.ui.Theme;
import com.chat.base.utils.AndroidUtilities;
import com.chat.base.utils.StringUtils;
import com.chat.base.utils.WKFileUtils;
import com.chat.base.utils.WKLogUtils;
import com.chat.base.utils.singleclick.SingleClickUtil;
import com.chat.file.databinding.ActChatFileLayoutBinding;
import com.chat.file.msgitem.FileContent;
import com.seapeak.docviewer.config.DocConfig;
import com.seapeak.docviewer.config.DocType;
import com.xinbida.wukongim.WKIM;
import com.xinbida.wukongim.entity.WKMsg;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * 2020-05-06 17:38
 * 聊天文件查看
 */
public class ChatFileActivity extends WKBaseActivity<ActChatFileLayoutBinding> {
    FileContent fileContent;
    private WKMsg msg;

    @Override
    protected ActChatFileLayoutBinding getViewBinding() {
        return ActChatFileLayoutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setTitle(TextView titleTv) {
        titleTv.setText(R.string.str_file_file);
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void initView() {
        wkVBinding.openBtn.getBackground().setTint(Theme.colorAccount);
        String clientMsgNo = getIntent().getStringExtra("clientMsgNo");
        msg = WKIM.getInstance().getMsgManager().getWithClientMsgNO(clientMsgNo);
        if (msg == null) {
            showToast("页面参数有误");
            new Handler(Objects.requireNonNull(Looper.myLooper())).postDelayed(this::finish, 1000);
            return;
        }

        fileContent = (FileContent) msg.baseContentMsgModel;
        if (fileContent != null && !TextUtils.isEmpty(fileContent.name)) {
            wkVBinding.nameTv.setText(fileContent.name);
            if (fileContent.name.contains(".")) {
                String type = fileContent.name.substring(fileContent.name.lastIndexOf(".") + 1);
                if (!TextUtils.isEmpty(type))
                    wkVBinding.typeTv.setText(type.toUpperCase());
                else wkVBinding.typeTv.setText(R.string.unknown_file);
            } else wkVBinding.typeTv.setText(R.string.unknown_file);
            wkVBinding.sizeTv.setText(StringUtils.sizeFormatNum2String(fileContent.size));
        }

    }

    @Override
    protected void initListener() {
        WKLogUtils.e("文件路径" + fileContent.localPath);
        if (fileContent != null && !TextUtils.isEmpty(fileContent.localPath)) {
            wkVBinding.progressBar.setVisibility(View.GONE);
            wkVBinding.openBtn.setText(R.string.open_file);
        } else {
            wkVBinding.openBtn.setText(R.string.download_open);
            wkVBinding.progressBar.setVisibility(View.VISIBLE);
        }
        SingleClickUtil.onSingleClick(wkVBinding.openBtn, view1 -> openFile());
    }

    private void openFile() {
//
        if (fileContent != null && !TextUtils.isEmpty(fileContent.localPath)) {
            handleWriteExternalStorage(fileContent.localPath);
            // WKFileUtils.getInstance().openFileByPath(this, fileContent.localPath);
        } else {
            if (fileContent != null) {
                String showUrl = WKApiConfig.getShowUrl(fileContent.url);
                if (TextUtils.isEmpty(showUrl)) {
                    showToast(R.string.file_is_deleted);
                    finish();
                    return;
                }
                String fileDir = WKConstants.imageDir + msg.channelType + "/" + msg.channelID + "/";
                WKFileUtils.getInstance().createFileDir(fileDir);
                String filePath = fileDir + msg.clientSeq + "." + fileContent.name.substring(fileContent.name.lastIndexOf(".") + 1);
                String taskTag = UUID.randomUUID().toString().replaceAll("-", "");
                WKDownloader.Companion.getInstance().download(showUrl, filePath, new WKProgressManager.IProgress() {
                    @Override
                    public void onProgress(@Nullable Object tag, int progress) {
                        wkVBinding.progressBar.setProgress(progress);
                        if (progress >= 100) {
                            wkVBinding.openBtn.setEnabled(true);
                            wkVBinding.openBtn.setAlpha(1f);
                            wkVBinding.openBtn.setText(R.string.open_file);
                            wkVBinding.progressBar.setVisibility(View.GONE);
                        } else {
                            wkVBinding.openBtn.setEnabled(false);
                            wkVBinding.openBtn.setAlpha(0.2f);
                            wkVBinding.progressBar.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onSuccess(@Nullable Object tag, @Nullable String path) {
                        fileContent.localPath = filePath;
                        WKIM.getInstance().getMsgManager().updateContentAndRefresh(msg.clientMsgNO, fileContent, false);
                        handleWriteExternalStorage(fileContent.localPath);
                    }

                    @Override
                    public void onFail(@Nullable Object tag, @Nullable String msg) {

                    }
                });
            }
        }

    }

    public void myOpenFile(String loadPath){
        String showUrl = WKApiConfig.getShowUrl(fileContent.url);
//        Intent intent =  new Intent(this, WKWebViewActivity.class);
//        intent.putExtra("gone", true);
//        intent.putExtra("url", "https://view.officeapps.live.com/op/view.aspx?src="+WKApiConfig.getShowUrl(fileContent.url));
//        startActivity(intent);
        Intent intent = new Intent(ChatFileActivity.this, PreviewActivity.class);
        intent.putExtra("config", new DocConfig(loadPath,  getDocTypeByFileName(fileContent.name)));
        startActivity( intent);
    }
    private DocType getDocTypeByFileName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }

        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (suffix) {
            case "doc":
                return DocType.WORD;
            case "docx":
                return DocType.WORD;
            case "xls":
                return DocType.EXCEL;
            case "xlsx":
                return DocType.EXCEL;
            case "ppt":
                return DocType.PPT;
            case "pptx":
                return DocType.PPT;
            case "pdf":
                return DocType.PDF;
            case "txt":
                return DocType.TXT;
            default:
                return null;
        }
    }

    private boolean isDocOrXlsxFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        String lowerCasePath = path.toLowerCase();
        return lowerCasePath.endsWith(".doc") || lowerCasePath.endsWith(".xlsx") ||  lowerCasePath.endsWith(".txt") ||  lowerCasePath.endsWith(".docx") ||  lowerCasePath.endsWith(".xls");
    }

    public void handleWriteExternalStorage(String path) {
        if(isDocOrXlsxFile(path)){
            myOpenFile(path);
        }else{
            File sandFile = new File(path);
            if (!sandFile.exists()) {
                showToast(R.string.file_is_deleted);
                finish();
                return;
            }
            String typeName = path.substring(path.lastIndexOf(".") + 1);
            String name = msg.clientMsgNO + "." + typeName;
            new Thread(() -> {
                Uri externalUri;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    File externalFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File destFile = new File(externalFile + File.separator + name);
                    externalUri = Uri.fromFile(destFile);
                } else {
                    ContentResolver resolver = getContentResolver();
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Downloads.DISPLAY_NAME, name);
                    values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
                    Uri uri = MediaStore.Files.getContentUri("external");
                    externalUri = resolver.insert(uri, values);
                }

                boolean ret = WKFileUtils.getInstance().copyFileToExternalUri(ChatFileActivity.this, sandFile.getAbsolutePath(), externalUri);
                if (ret) {
                    String sharePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                    AndroidUtilities.runOnUIThread(() -> WKFileUtils.getInstance().openFileByPath(ChatFileActivity.this, sharePath + "/" + name));
                }
            }).start();
        }
    }
}
