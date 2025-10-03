package com.chat.wkredpacket;
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
import com.chat.wkpay.redpackge.WKRedPacketProvider;
import com.chat.wkpay.redpackge.WKRedPacketTipsRecProvider;
import com.chat.wkredpacket.model.WKRedPacketContent;
import com.chat.wkredpacket.model.WKRedPacketRecContent;
import com.chat.wkredpacket.ui.SendRedPacketActivity;
import com.xinbida.wukongim.WKIM;

import java.lang.ref.WeakReference;

public class WKRedpacketApplication {
    private WKRedpacketApplication() {

    }

    private static class WKRedpacketApplicationBinder {
        static final WKRedpacketApplication wkredpacketApplication = new WKRedpacketApplication();
    }

    public static WKRedpacketApplication getInstance() {
        return WKRedpacketApplicationBinder.wkredpacketApplication;
    }

    private WeakReference<IConversationContext> iConversationContext;
    public void init(Context context) {

        com.chat.base.common.WKCommonModel.getInstance().getAppConfig((code, msg, wkappConfig) -> {
            if (code == HttpResponseCode.success) {
                if(wkappConfig.redpacket_enabled.equals("1")){
                    //添加聊天面板菜单 红包
                    EndpointManager.getInstance().setMethod(EndpointCategory.chatFunction + "_redpackge", EndpointCategory.chatFunction, 20, object -> new ChatFunctionMenu("", R.mipmap.redpackge, context.getString(R.string.title_red_packet), (iConversationContext) -> {
                        WKRedpacketApplication.this.iConversationContext = new WeakReference<>((IConversationContext) object);
                        Intent intent;
                        intent = new Intent(context, SendRedPacketActivity.class);
                        intent.putExtra("channelId", iConversationContext.getChatChannelInfo().channelID);
                        intent.putExtra("channelType", iConversationContext.getChatChannelInfo().channelType);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }));
                }
            }
        });


        //注册红包消息类型
        EndpointManager.getInstance().setMethod(EndpointCategory.msgConfig + WKContentType.WK_RED_PACKAGE, object -> new MsgConfig());
        WKIM.getInstance().getMsgManager().registerContentMsg(WKRedPacketContent.class);
        WKMsgItemViewManager.getInstance().addChatItemViewProvider(WKContentType.WK_RED_PACKAGE, new WKRedPacketProvider());

        //注册红包提示消息
        EndpointManager.getInstance().setMethod(EndpointCategory.msgConfig + WKContentType.WK_RED_PACKAGE_REC, object -> new MsgConfig());
        WKIM.getInstance().getMsgManager().registerContentMsg(WKRedPacketRecContent.class);
        WKMsgItemViewManager.getInstance().addChatItemViewProvider(WKContentType.WK_RED_PACKAGE_REC, new WKRedPacketTipsRecProvider());


    }
}
