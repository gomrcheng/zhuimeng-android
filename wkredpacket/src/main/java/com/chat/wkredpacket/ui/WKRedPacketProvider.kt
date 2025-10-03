package com.chat.wkpay.redpackge

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.alibaba.fastjson.JSONObject
import com.chat.base.config.WKConfig
import com.chat.base.endpoint.EndpointManager
import com.chat.base.msgitem.WKChatBaseProvider
import com.chat.base.msgitem.WKChatIteMsgFromType
import com.chat.base.msgitem.WKContentType
import com.chat.base.msgitem.WKUIChatMsgItemEntity
import com.chat.base.utils.WKToastUtils
import com.chat.wkredpacket.R
import com.chat.wkredpacket.model.RedDialogBean
import com.chat.wkredpacket.service.WKCommonModel
import com.chat.wkredpacket.ui.RedDetailActivity
import com.chat.wkredpacket.utils.RedDialog
import com.lxj.xpopup.XPopup
import com.xinbida.wukongim.WKIM
import com.xinbida.wukongim.entity.WKChannelType


class WKRedPacketProvider : WKChatBaseProvider() {

    private var dialog: Dialog? = null

    private var isShow = false

    override fun getChatViewItem(parentView: ViewGroup, from: WKChatIteMsgFromType): View? {
        return LayoutInflater.from(context).inflate(R.layout.chat_item_redpackge, parentView, false)
    }

    override fun setData(
        adapterPosition: Int,
        parentView: View,
        uiChatMsgItemEntity: WKUIChatMsgItemEntity,
        from: WKChatIteMsgFromType
    ) {
        val jsonObjectst = uiChatMsgItemEntity.wkMsg.content as String;
        val jsonObject = JSONObject.parseObject(jsonObjectst) as JSONObject;
        val redContent = jsonObject.getString("blessing");
        val redpacket_no = jsonObject.getString("redpacket_no");
        val cardNameTv = parentView.findViewById<TextView>(R.id.red_packet_content)
        val tvRecType =parentView.findViewById<TextView>(R.id.tv_rec_type)
        val contentLayout = parentView.findViewById<RelativeLayout>(R.id.contentLayout)
        var tvTime = parentView.findViewById<TextView>(R.id.tvTime);
        cardNameTv.text = redContent
        var timeStr = uiChatMsgItemEntity.wkMsg.createdAt.split(" ")[1];
        val formattedTime = timeStr.substring(0, 5) // 获取前5个字符 "10:25"
        tvTime.text = formattedTime
        if(uiChatMsgItemEntity.wkMsg.localExtraMap!=null) {
            if (uiChatMsgItemEntity.wkMsg.localExtraMap["isOpen"] != null) {
                if (uiChatMsgItemEntity.wkMsg.localExtraMap["isOpen"] == 0) {
                    contentLayout.alpha = 0.6f
                    tvRecType.setText("已领取")
                }else{
                    contentLayout.alpha = 1f
                }
            }else{
                contentLayout.alpha = 1f
            }
        }else{
            contentLayout.alpha = 1f
        }

        resetCellBackground(parentView, uiChatMsgItemEntity, from)
        parentView.findViewById<View>(R.id.contentLayout).setOnClickListener {
            var loadingPopup = XPopup.Builder(context)
                .asLoading(context.getString(R.string.loading))
            loadingPopup.show()
            //1：判断钱包是否设置支付密码
            WKCommonModel.getInstance().getWalletInfo { amount, status, password_is_set ->
                loadingPopup.dismiss()
                if (password_is_set == 0 && status == 1) {
                    EndpointManager.getInstance().invoke("set_pay_password", context)
                } else if (status == 0) {
                    WKToastUtils.getInstance().showToast("钱包状态异常")
                }else {
                    //2:抢红包
                    WKCommonModel.getInstance().getRedpacket(redpacket_no){result ->
                        var isshowOpen = false;
                        if(from == WKChatIteMsgFromType.SEND && uiChatMsgItemEntity.wkMsg.channelType == WKChannelType.PERSONAL){
                            isshowOpen = false;
                        }
                        var redpacket_no = result.redpacketNo as String;
                        var blessing = result.blessing as String;//红包标题
                        var sender_uid = result.senderUid as String;
                        var sender_name = result.senderName as String;
                        when(result.status){
                            0 -> {
                                //等待支付
                            }
                            1 -> {
                                //已支付，展示抢logo
                                //如果当前红包是我发的 并且是个人红包只查看
                                if(from == WKChatIteMsgFromType.SEND && uiChatMsgItemEntity.wkMsg.channelType == WKChannelType.PERSONAL){
                                    isshowOpen = false;
                                }else{
                                    isshowOpen = true;
                                }
                            }
                            2 -> {
                                //红包已领取
                                blessing = "红包已领"
                                isshowOpen = false;
                            }
                            3 -> {
                                //红包领取完毕
                                blessing = "红包领取完毕"
                                isshowOpen = false;
                            }
                            4 -> {
                                //红包已过期
                                blessing = "红包已过期"
                                isshowOpen = false;
                            }
                        }
                        showRedPacketDialog(sender_name,sender_uid,redpacket_no,blessing,uiChatMsgItemEntity,isshowOpen);
                    }
                }
            }
//            //如果当前红包是我发的 并且是个人红包只查看
//            if(from == WKChatIteMsgFromType.SEND && uiChatMsgItemEntity.wkMsg.channelType == WKChannelType.PERSONAL){
////                WKCommonModel.getInstance().getRedPacketDetail(id.toString(),
////                    IRedPacketDetailListener { result ->
////                        if(result.finished_count >0){
////                            updateLocalStatus(uiChatMsgItemEntity)
////                        }
////                        val intent = Intent(context, RedDetailActivity::class.java)
////                        intent.putExtra("id", id.toString())
////                        context.startActivity(intent)
////                    })
//
//            }else{
//                if(uiChatMsgItemEntity.wkMsg.localExtraMap!=null&& uiChatMsgItemEntity.wkMsg.localExtraMap["isOpen"] != null &&
//                    uiChatMsgItemEntity.wkMsg.localExtraMap["isOpen"] == 0){ // 等待领取
//                    val intent = Intent(context, RedDetailActivity::class.java)
////                    intent.putExtra("id", id.toString())
//                    context.startActivity(intent)
//                }else{
//                    //查看红包详情
////                    WKCommonModel.getInstance().getRedPacketDetail(id.toString(),
////                        IRedPacketDetailListener { result ->
////                            val uname = result.uname //发送人昵称
////                            val uid = result.uid //发送人uid
////                            val fee = result.fee //红包总金额
////                            showFinishDialog(uname,uid, result.status, fee.toString(),""+id,redContent.content,uiChatMsgItemEntity)
////
////                        })
//                }
//            }
        }
    }


    @SuppressLint("ResourceType")
    fun showRedPacketDialog(
        name:String,
        uid: String,
        redpacket_no: String,
        content:String,
        uiChatMsgItemEntity:WKUIChatMsgItemEntity,
        showOpen:Boolean
    ) {
        this.context = context
        //发送者昵称，uid，红包类型，红包状态，红包金额
        val redDialogBean = RedDialogBean(
            uid, name,
            content, redpacket_no
        )
         mRedDialog = RedDialog(context, redDialogBean, object : RedDialog.OnClickRedListener {
            override fun clickRed() {
                // 打开红包
                openRedPacket(redpacket_no,uiChatMsgItemEntity)

            }

            override fun clickTail() {
                val intent = Intent(context, RedDetailActivity::class.java)
                intent.putExtra("redpacket_no", redpacket_no)
                context.startActivity(intent)
            }
        }, WKConfig.getInstance().uid.equals(uid),showOpen)
        mRedDialog!!.show()
        isShow = true
    }
    private var mRedDialog: RedDialog? = null
    private fun openRedPacket(redpacket_no:String,uiChatMsgItemEntity:WKUIChatMsgItemEntity){
        WKCommonModel.getInstance().openRedPacket(redpacket_no,uiChatMsgItemEntity.wkMsg.channelID,uiChatMsgItemEntity.wkMsg.channelType){code, status ->
            if(code == 200){
                mRedDialog!!.dismiss()
                updateLocalStatus(uiChatMsgItemEntity)
                val intent = Intent(context, RedDetailActivity::class.java)
                intent.putExtra("redpacket_no", redpacket_no)
                context.startActivity(intent)
            }
        }

//        WKCommonModel.getInstance().getUserBalance { balance, balance_enabled ->
//            val intent: Intent
//            if (balance_enabled) {
//                WKCommonModel.getInstance().openRedPackge(id.toLong()){ res: OpenRedPackgeEntity? ->
//                    if (res != null) {
//                        if(res.code == 0 || res.code == 2){
//                            mRedDialog!!.dismiss()
//                            updateLocalStatus(uiChatMsgItemEntity)
//                            val intent = Intent(context, RedDetailActivity::class.java)
//                            intent.putExtra("id", id)
//                            context.startActivity(intent)
//                        }else{
//                            when (res.code) {
//                                3-> {
//                                    //过期
//                                    WKToastUtils.getInstance().showToast(context.getString(R.string.string_hbygq))
//                                    updateLocalStatus(uiChatMsgItemEntity)
//                                }
//                                4-> {
//                                    //领完
//                                    WKToastUtils.getInstance().showToast(context.getString(R.string.string_lwyb))
//                                    updateLocalStatus(uiChatMsgItemEntity)
//                                }
//                                1-> {
//                                    WKToastUtils.getInstance().showToast(context.getString(R.string.string_lqsb))
//                                }
//
//                            }
//                            mRedDialog!!.dismiss()
//                        }
//
//
//                    }else{
//                        mRedDialog!!.dismiss()
//                        WKToastUtils.getInstance().showToast(context.getString(R.string.string_dkhbyc))
//                    }
//                }
//            } else {
//                //进入设置支付密码
//                intent = Intent(context, InputzhifuPwdActivity::class.java)
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                context.startActivity(intent)
//            }
//        }

    }

    private fun updateLocalStatus(uiChatMsgItemEntity:WKUIChatMsgItemEntity){

        val hashMap = HashMap<String, Any>()
        hashMap["isOpen"] = 0
        try {
            WKIM.getInstance().msgManager.updateLocalExtraWithClientMsgNO(
                uiChatMsgItemEntity.wkMsg.clientMsgNO,
                hashMap
            )
        } catch (e: Exception) {
        }
    }



    override val itemViewType: Int
    get() = WKContentType.WK_RED_PACKAGE

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
        val contentLayout = parentView.findViewById<RelativeLayout>(R.id.contentLayout)
        if(from == WKChatIteMsgFromType.SEND){
            contentLayout.setBackgroundResource(R.mipmap.envelope_not_receive_right_icon)

        }else {
            contentLayout.setBackgroundResource(R.mipmap.envelope_not_receive_left_icon)
        }


    }

    override fun resetCellListener(
        position: Int,
        parentView: View,
        uiChatMsgItemEntity: WKUIChatMsgItemEntity,
        from: WKChatIteMsgFromType
    ) {
        super.resetCellListener(position, parentView, uiChatMsgItemEntity, from)
    }

}