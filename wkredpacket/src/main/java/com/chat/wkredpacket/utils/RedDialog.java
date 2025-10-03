package com.chat.wkredpacket.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chat.base.config.WKApiConfig;
import com.chat.base.glide.GlideUtils;
import com.chat.wkredpacket.R;
import com.chat.wkredpacket.model.RedDialogBean;


public class RedDialog extends Dialog {

    private RelativeLayout mRedRl;
    private ImageView mAvatarIv;
    private ImageView mOpenIv, mCloseIv;
    private TextView mNameTv, mContentTv;
    private RedDialogBean mRedDialogBean;
    private boolean isMySend;
    private Context mContext;
    private TextView tv_red_tail;
    private FrameAnimation mFrameAnimation;
    private boolean showOpen;

    private int[] mImgResIds = new int[]{
            R.mipmap.icon_open_red_packet1,
            R.mipmap.icon_open_red_packet2,
            R.mipmap.icon_open_red_packet3,
            R.mipmap.icon_open_red_packet4,
            R.mipmap.icon_open_red_packet5,
            R.mipmap.icon_open_red_packet6,
            R.mipmap.icon_open_red_packet7,
            R.mipmap.icon_open_red_packet7,
            R.mipmap.icon_open_red_packet8,
            R.mipmap.icon_open_red_packet9,
            R.mipmap.icon_open_red_packet4,
            R.mipmap.icon_open_red_packet10,
            R.mipmap.icon_open_red_packet11,
    };

    private OnClickRedListener mOnClickRedListener;

    public RedDialog(Context context, RedDialogBean redDialogBean, OnClickRedListener onClickRedListener) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.mRedDialogBean = redDialogBean;
        this.mOnClickRedListener = onClickRedListener;
    }

    public RedDialog(Context context, RedDialogBean redDialogBean, OnClickRedListener onClickRedListener, boolean isWho) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.mRedDialogBean = redDialogBean;
        this.mOnClickRedListener = onClickRedListener;
        this.isMySend = isWho;
    }

    public RedDialog(Context context, RedDialogBean redDialogBean, OnClickRedListener onClickRedListener, boolean isWho,boolean showOpenIv) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.mRedDialogBean = redDialogBean;
        this.mOnClickRedListener = onClickRedListener;
        this.isMySend = isWho;
        this.showOpen = showOpenIv;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_red_packet);
        initView();
        initData();
        initEvent();

        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams lp = window.getAttributes();
        // lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        // lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setGravity(Gravity.CENTER);
    }

    private void initView() {
        mRedRl = findViewById(R.id.rl_red);
        mAvatarIv = findViewById(R.id.iv_avatar);
        mNameTv = findViewById(R.id.tv_name);
        mContentTv = findViewById(R.id.tv_msg);
        mOpenIv = findViewById(R.id.iv_open);
        mCloseIv = findViewById(R.id.iv_close);
        tv_red_tail = findViewById(R.id.tv_red_tail);

    }

    private void initData() {
        GlideUtils.getInstance().showImg(mContext,WKApiConfig.getAvatarUrl(mRedDialogBean.getUserId()),mAvatarIv);
        mNameTv.setText(mRedDialogBean.getUserName());
        mContentTv.setText(mRedDialogBean.getWords());
//        mAvatarIv.showAvatar(mRedDialogBean.getUserId(), WKChannelType.PERSONAL);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_red);
        mRedRl.setAnimation(animation);
        if(!showOpen){
            mOpenIv.setVisibility(View.GONE);
            tv_red_tail.setVisibility(View.VISIBLE);
        }else{
            tv_red_tail.setVisibility(View.GONE);
        }
    }

    private void initEvent() {
        tv_red_tail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickRedListener.clickTail();
                stopAnim();
                dismiss();
            }
        });
        mOpenIv.setOnClickListener(v -> {
            if (mFrameAnimation != null) {
                return;
            }
            startAnim();
            if (mOnClickRedListener != null) {
                mOnClickRedListener.clickRed();
            }
        });

        mCloseIv.setOnClickListener(v -> {
            stopAnim();
            dismiss();
        });
    }

    private void startAnim() {
        mFrameAnimation = new FrameAnimation(mOpenIv, mImgResIds, 125, true);
        mFrameAnimation.setAnimationListener(new FrameAnimation.AnimationListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }

            @Override
            public void onAnimationRepeat() {

            }

            @Override
            public void onAnimationPause() {
                mOpenIv.setBackgroundResource(R.mipmap.icon_open_red_packet1);
            }
        });
    }

    private void stopAnim() {
        if (mFrameAnimation != null) {
            mFrameAnimation.release();
            mFrameAnimation = null;
        }
    }

    public interface OnClickRedListener {
        void clickRed();

        void clickTail();
    }
}
