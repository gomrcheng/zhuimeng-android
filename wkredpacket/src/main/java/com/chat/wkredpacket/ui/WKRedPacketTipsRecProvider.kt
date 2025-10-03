package com.chat.wkpay.redpackge

import android.graphics.Color
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chat.base.config.WKConfig
import com.chat.base.msgitem.WKChatBaseProvider
import com.chat.base.msgitem.WKChatIteMsgFromType
import com.chat.base.msgitem.WKContentType
import com.chat.base.msgitem.WKUIChatMsgItemEntity
import com.chat.base.ui.components.SystemMsgBackgroundColorSpan
import com.chat.base.utils.AndroidUtilities
import com.chat.wkredpacket.R

class WKRedPacketTipsRecProvider : WKChatBaseProvider() {
    override fun getChatViewItem(parentView: ViewGroup, from: WKChatIteMsgFromType): View? {
        return LayoutInflater.from(context).inflate(R.layout.chat_redpacket_notify_layout, parentView, false)

    }

    override fun setData(
        adapterPosition: Int,
        parentView: View,
        uiChatMsgItemEntity: WKUIChatMsgItemEntity,
        from: WKChatIteMsgFromType
    ) {

        resetCellBackground(parentView, uiChatMsgItemEntity, from)
    }

    override val layoutId: Int
        get() = R.layout.chat_redpacket_notify_layout
    override val itemViewType: Int
        get() = WKContentType.WK_RED_PACKAGE_REC

    override fun convert(helper: BaseViewHolder, item: WKUIChatMsgItemEntity) {
        super.convert(helper, item)
        var isReEdit: Boolean
        val textView: TextView = helper.getView(R.id.contentTv)

        textView.text = "-";
        val jsonObjectst = item.wkMsg.content as String
        val jsonObject = JSONObject.parseObject(jsonObjectst) as JSONObject;
//        var finish_uid = jsonObject.getOrDefault("finish_uid","-") as String;
//        var finish_name = jsonObject.getOrDefault("finish_uname","-") as String;
//        var from_name = jsonObject.getOrDefault("from_name","-") as String;
//        var content = "" as String;
//        content = if(finish_uid.equals(WKConfig.getInstance().uid)){
//            context.getString(com.chat.wkpay.R.string.string_nlql)+from_name+ context.getString(com.chat.wkpay.R.string.string_dhb)
//        }else{
//            finish_name+ context.getString(com.chat.wkpay.R.string.string_lql)+from_name+context.getString(com.chat.wkpay.R.string.string_dhb)
//        }


        var content = jsonObject.getString("content") ?: "-"
        val extraArr = jsonObject.getJSONArray("extra")
        if (extraArr != null) {
            for (i in 0 until extraArr.size) {
                val extra = extraArr.getJSONObject(i)
                val uid = extra.getString("uid")
                var name = extra.getString("name")
                if (uid == WKConfig.getInstance().uid) {
                    name = "你"
                }
                if (i == 0) {
                    content = content.replace("{0}", " $name ")
                        .replace(Regex("[\"“”]"), "")  // 一次性替换所有类型的引号
                } else {
                    content = content.replace("{1}", " $name ")
                        .replace(Regex("[\"“”]"), "")  // 一次性替换所有类型的引号
                }
            }
        }

        helper.setVisible(R.id.contentTv,true)
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.setShadowLayer(AndroidUtilities.dp(5f).toFloat(), 0f, 0f, 0)
        // 红包被领取的提示
        textView.setShadowLayer(AndroidUtilities.dp(5f).toFloat(), 0f, 0f, 0)
        val str = SpannableString(content)
        str.setSpan(
            SystemMsgBackgroundColorSpan(
                ContextCompat.getColor(
                    context,
                    R.color.screen_bg
                ), AndroidUtilities.dp(5f), AndroidUtilities.dp((2 * 5).toFloat())
            ), 0, content!!.length, 0
        )
        textView.text = str
    }


}