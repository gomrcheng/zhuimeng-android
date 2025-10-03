package com.chat.wktransfer;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;

import com.chat.base.endpoint.EndpointCategory;
import com.chat.base.endpoint.EndpointManager;
import com.chat.base.endpoint.entity.ChatFunctionMenu;
import com.chat.base.endpoint.entity.MsgConfig;
import com.chat.base.endpoint.entity.PersonalInfoMenu;
import com.chat.base.msg.IConversationContext;
import com.chat.base.msgitem.WKContentType;
import com.chat.base.msgitem.WKMsgItemViewManager;
import com.chat.base.net.HttpResponseCode;
import com.chat.base.utils.WKToastUtils;
import com.chat.wktransfer.entity.WKTransferSendContent;
import com.chat.wktransfer.ui.TransferActivity;
import com.chat.wktransfer.ui.WKTransferSendProvider;
import com.xinbida.wukongim.WKIM;
import com.xinbida.wukongim.entity.WKChannelType;

import java.lang.ref.WeakReference;


/**
 * 支付模块
 */
public class WKTransferApplication {
    private WKTransferApplication() {

    }

    private static class WKTraksferApplicationBinder {
        static final WKTransferApplication wktransferApplication = new WKTransferApplication();
    }

    public static WKTransferApplication getInstance() {
        return WKTraksferApplicationBinder.wktransferApplication;
    }

    private WeakReference<IConversationContext> iConversationContext;

    public void init(Context context) {

        com.chat.base.common.WKCommonModel.getInstance().getAppConfig((code, msg, wkappConfig) -> {
            if (code == HttpResponseCode.success) {
                if(wkappConfig.transfer_enabled.equals("1")){
                    //添加聊天面板菜单 转账
                    EndpointManager.getInstance().setMethod(EndpointCategory.chatFunction + "_transfer", EndpointCategory.chatFunction, 19, object -> new ChatFunctionMenu("", R.mipmap.transfer, context.getString(R.string.string_zz), (iConversationContext) -> {
                        WKTransferApplication.this.iConversationContext = new WeakReference<>((IConversationContext) object);
                        String channelID = iConversationContext.getChatChannelInfo().channelID;
                        byte channelType = iConversationContext.getChatChannelInfo().channelType;
                        String channelRemark = iConversationContext.getChatChannelInfo().channelRemark;
                        System.out.println("============channelRemark:"+channelRemark);
                        if(channelType == WKChannelType.GROUP){
                            WKToastUtils.getInstance().showToastNormal("暂不支持群聊转账");
                        }else{
                            Intent intent = new Intent(context, TransferActivity.class);
                            intent.putExtra("channelId", channelID);
                            intent.putExtra("channelType", channelType);
                            intent.putExtra("transferName", iConversationContext.getChatChannelInfo().channelName);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }));
                }
            }
        });

        //注册转账消息类型
        EndpointManager.getInstance().setMethod(EndpointCategory.msgConfig + WKContentType.WK_TRANSFER, object -> new MsgConfig());
        WKIM.getInstance().getMsgManager().registerContentMsg(WKTransferSendContent.class);
        WKMsgItemViewManager.getInstance().addChatItemViewProvider(WKContentType.WK_TRANSFER, new WKTransferSendProvider());//发送转账
//        //转账接收状态
//        EndpointManager.getInstance().setMethod(EndpointCategory.msgConfig + 89, object -> new MsgConfig());
//        WKIM.getInstance().getMsgManager().registerContentMsg(WKTransferRecContent.class);
//        WKMsgItemViewManager.getInstance().addChatItemViewProvider(89, new WKTransferRecProvider());//接收转账
//        //转账退回
//        EndpointManager.getInstance().setMethod(EndpointCategory.msgConfig + 91, object -> new MsgConfig());
//        WKIM.getInstance().getMsgManager().registerContentMsg(WKTransferTimeOutContent.class);
//        WKMsgItemViewManager.getInstance().addChatItemViewProvider(91, new WKTransferTipsTimeOutProvider());//自动退回
//
//
//        WKMsgItemViewManager.getInstance().addChatItemViewProvider(90, new WKTransferTipsNoRecProvider());//拒收




    }
}
