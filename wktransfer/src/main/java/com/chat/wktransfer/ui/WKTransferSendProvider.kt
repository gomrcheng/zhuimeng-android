package com.chat.wktransfer.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.alibaba.fastjson.JSONObject
import com.chat.base.config.WKConfig
import com.chat.base.msgitem.WKChatBaseProvider
import com.chat.base.msgitem.WKChatIteMsgFromType
import com.chat.base.msgitem.WKContentType
import com.chat.base.msgitem.WKUIChatMsgItemEntity
import com.chat.base.utils.StringUtils
import com.chat.wktransfer.R


class WKTransferSendProvider : WKChatBaseProvider() {



    override fun getChatViewItem(parentView: ViewGroup, from: WKChatIteMsgFromType): View? {
        return LayoutInflater.from(context).inflate(R.layout.chat_item_transfer_send, parentView, false)
    }


    @SuppressLint("SetTextI18n")
    override fun setData(
        adapterPosition: Int,
        parentView: View,
        uiChatMsgItemEntity: WKUIChatMsgItemEntity,
        from: WKChatIteMsgFromType
    ) {

        val jsonObjectst = uiChatMsgItemEntity.wkMsg.content as String;
        val jsonObject = JSONObject.parseObject(jsonObjectst) as JSONObject;
        val cardNameTv = parentView.findViewById<TextView>(R.id.transerf_send_content)
        cardNameTv.text = StringUtils.fen2yuan(jsonObject.getIntValue("amount")) + "  ¥"
        val transferTitleTv = parentView.findViewById<TextView>(R.id.transferTitle)
        val tvTime = parentView.findViewById<TextView>(R.id.tvTime)
            val time = uiChatMsgItemEntity.wkMsg.createdAt.split(" ")[1]  // 获取时间部分 "12:30:29"
            val hourAndMinute = time.split(":").take(2).joinToString(":")  // 提取小时和分钟并拼接 "12:30"
            tvTime.text = hourAndMinute
        val bg = parentView.findViewById<ConstraintLayout>(R.id.contentLayout2)
        if(uiChatMsgItemEntity.wkMsg.fromUID == WKConfig.getInstance().uid){
            bg.setBackgroundResource(R.mipmap.transfer_not_receive_right_icon)
            val showName: String = if (TextUtils.isEmpty(uiChatMsgItemEntity.wkMsg.channelInfo.channelRemark)) uiChatMsgItemEntity.wkMsg.channelInfo.channelName else uiChatMsgItemEntity.wkMsg.channelInfo.channelRemark
            transferTitleTv.text = context.getString(R.string.string_zzg)+"$showName"
        }else{
            bg.setBackgroundResource(R.mipmap.transfer_not_receive_left_icon)
            transferTitleTv.text = context.getString(R.string.string_zzgn)
        }
        var record_no = jsonObject.getString("record_no");
        if(uiChatMsgItemEntity.wkMsg.localExtraMap!=null) {
            if (uiChatMsgItemEntity.wkMsg.localExtraMap["isOpen"] != null) {
                if (uiChatMsgItemEntity.wkMsg.localExtraMap["isOpen"] == 0) {
                    bg.alpha = 0.6f
                    transferTitleTv.text = context.getString(R.string.string_ysk)
                }else{
                    bg.alpha = 1f
                }
            }else{
                bg.alpha = 1f
            }
        }else{
            bg.alpha = 1f
        }
        resetCellBackground(parentView, uiChatMsgItemEntity, from)
        parentView.findViewById<View>(R.id.contentLayout2).setOnClickListener {
            val intent = Intent(context, TransferMoneyDetailActivity::class.java)
            intent.putExtra("transfer_no", record_no)
            if(uiChatMsgItemEntity.wkMsg.fromUID == WKConfig.getInstance().uid){
                intent.putExtra("is_my_send", true)
            }else{
                intent.putExtra("is_my_send", false)
            }
            context.startActivity(intent)
        }
    }



    override val itemViewType: Int
    get() = WKContentType.WK_TRANSFER

    override fun resetCellBackground(
        parentView: View,
        uiChatMsgItemEntity: WKUIChatMsgItemEntity,
        from: WKChatIteMsgFromType
    ) {
        super.resetCellBackground(parentView, uiChatMsgItemEntity, from)
        val bgType = getMsgBgType(
            uiChatMsgItemEntity.previousMsg,
            uiChatMsgItemEntity.wkMsg,
            uiChatMsgItemEntity.nextMsg
        )
//        val contentLayout = parentView.findViewById<BubbleLayout>(R.id.contentLayout)
//        contentLayout.setAll(bgType, from, 88)
//        parentView.findViewById<View>(R.id.contentLayout).setBackgroundColor(Color.parseColor("#FFCC8A"));
//        parentView.findViewById<View>(R.id.contentLayout).setPadding(0,0,0,0,)
    }

    override fun resetCellListener(
        position: Int,
        parentView: View,
        uiChatMsgItemEntity: WKUIChatMsgItemEntity,
        from: WKChatIteMsgFromType
    ) {
        super.resetCellListener(position, parentView, uiChatMsgItemEntity, from)
       // val contentLayout = parentView.findViewById<BubbleLayout>(R.id.contentLayout)
    }


}