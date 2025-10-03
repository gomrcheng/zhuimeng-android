package com.chat.wkwallet;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.alibaba.fastjson.JSONObject;
import com.chat.base.act.PlayVideoActivity;
import com.chat.base.config.WKConfig;
import com.chat.base.endpoint.EndpointCategory;
import com.chat.base.endpoint.EndpointManager;
import com.chat.base.endpoint.entity.ChatFunctionMenu;
import com.chat.base.endpoint.entity.MsgConfig;
import com.chat.base.endpoint.entity.PersonalInfoMenu;
import com.chat.base.endpoint.entity.PlayVideoMenu;
import com.chat.base.msg.IConversationContext;
import com.chat.base.msgitem.WKContentType;
import com.chat.base.msgitem.WKMsgItemViewManager;
import com.chat.base.net.HttpResponseCode;
import com.chat.base.net.ICommonListener;
import com.chat.base.utils.WKReader;
import com.chat.base.utils.WKToastUtils;
import com.chat.wkwallet.entity.GetPayTokenEntity;
import com.chat.wkwallet.service.WKCommonModel;
import com.chat.wkwallet.ui.MianmiActivity;
import com.chat.wkwallet.ui.WKResetPayPwdActivity;
import com.chat.wkwallet.ui.WalletActivity;
import com.chat.wkwallet.utils.PayPasswordVerifyDialog;
import com.tencent.bugly.crashreport.CrashReport;
import com.xinbida.wukongim.WKIM;
import com.xinbida.wukongim.entity.WKChannelType;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

public class WKWalletApplication {
    private WKWalletApplication() {

    }

    private static class PayApplicationBinder {
        static final WKWalletApplication walletApplication = new WKWalletApplication();
    }

    public static WKWalletApplication getInstance() {
        return PayApplicationBinder.walletApplication;
    }

    private WeakReference<IConversationContext> iConversationContext;
    public void init(Context context) {

        com.chat.base.common.WKCommonModel.getInstance().getAppConfig((code, msg, wkappConfig) -> {
            if (code == HttpResponseCode.success) {
                if(wkappConfig.wallet_enabled.equals("1")){
                    // 添加icon
                    EndpointManager.getInstance().setMethod("wk_wallet", EndpointCategory.personalCenter, 1200, object -> new PersonalInfoMenu(R.mipmap.icon_qianbao, context.getString(R.string.qianbao), () -> {
                        WKCommonModel.getInstance().getWalletInfo(new WKCommonModel.IWalletInfoListener() {
                            @Override
                            public void onResult(int amount, int status, int password_is_set) {
                                Intent intent = new Intent(context, WalletActivity.class);
                                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });
                    }));
                }
            }
        });

        //暴露设置支付密码出去
        EndpointManager.getInstance().setMethod("set_pay_password", object -> {
            if (object instanceof Context context1) {
                Intent intent = new Intent(context1, WKResetPayPwdActivity.class);
                context1.startActivity(intent);
            }
            return null;
        });

        //暴露输入支付密码出去
        EndpointManager.getInstance().setMethod("input_pay_password", object -> {
            if (object instanceof Context context1) {
                Intent intent = new Intent(context1, WKResetPayPwdActivity.class);
                context1.startActivity(intent);
            }
            return null;
        });



    }
}
