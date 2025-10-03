package com.chat.wktransfer.entity;

import android.os.Parcel;

import com.chat.base.msgitem.WKContentType;
import com.xinbida.wukongim.msgmodel.WKMessageContent;

import org.json.JSONException;
import org.json.JSONObject;


public class WKTransferSendContent extends WKMessageContent {


    // 定义需发送给对方的字段
    public int amout; //
    public String record_no; //

    public WKTransferSendContent() {
        this.type = WKContentType.WK_TRANSFER; //指定消息类型
    }

    public WKTransferSendContent(String content) {
        this.content = content;
        this.type = WKContentType.WK_TRANSFER; //指定消息类型
    }
    @Override
    public JSONObject encodeMsg() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("amout", amout);
            jsonObject.put("record_no", record_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public WKMessageContent decodeMsg(JSONObject jsonObject) {
        amout = jsonObject.optInt("amout");
        record_no = jsonObject.optString("record_no");
        return this;
    }

    protected WKTransferSendContent(Parcel in) {
        super(in);
        amout = in.readInt();
        record_no = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }


    public static final Creator<WKTransferSendContent> CREATOR = new Creator<WKTransferSendContent>() {
        @Override
        public WKTransferSendContent createFromParcel(Parcel in) {
            return new WKTransferSendContent(in);
        }

        @Override
        public WKTransferSendContent[] newArray(int size) {
            return new WKTransferSendContent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String getDisplayContent() {
        return "[转账]";
    }
}
