package com.chat.wktransfer.entity;

import android.os.Parcel;

import com.xinbida.wukongim.msgmodel.WKMessageContent;

import org.json.JSONException;
import org.json.JSONObject;


public class WKTransferTimeOutContent extends WKMessageContent {


    // 定义需发送给对方的字段
    public String end_date; //
    public double fee; //
    public int count; //
    public String red_packet_name;//
    public String from_uid;
    public Long redPacketId;
    public String start_date;
    public int status;



    public WKTransferTimeOutContent() {
        this.type = 91; //指定消息类型
    }

    public WKTransferTimeOutContent(String content) {
        this.content = content;
        this.type = 91; //指定消息类型
    }
    @Override
    public JSONObject encodeMsg() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", "");
//            jsonObject.put("name", name);
//            jsonObject.put("avatar", avatar);
//            jsonObject.put("content", content);
//            jsonObject.put("urls", new JSONArray(urls));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public WKMessageContent decodeMsg(JSONObject jsonObject) {
        content = jsonObject.optString("fee");
        redPacketId = jsonObject.optLong("transferId");
        from_uid = jsonObject.optString("from_uid");
        start_date = jsonObject.optString("start_date");
        return this;
    }

    protected WKTransferTimeOutContent(Parcel in) {
        super(in);
        end_date = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }


    public static final Creator<WKTransferTimeOutContent> CREATOR = new Creator<WKTransferTimeOutContent>() {
        @Override
        public WKTransferTimeOutContent createFromParcel(Parcel in) {
            return new WKTransferTimeOutContent(in);
        }

        @Override
        public WKTransferTimeOutContent[] newArray(int size) {
            return new WKTransferTimeOutContent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String getDisplayContent() {
        return "[转账退回]";
    }
}
