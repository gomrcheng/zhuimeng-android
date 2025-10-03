package com.chat.wkredpacket.model;

import android.os.Parcel;

import com.chat.base.msgitem.WKContentType;
import com.xinbida.wukongim.message.type.WKMsgContentType;
import com.xinbida.wukongim.msgmodel.WKMessageContent;

import org.json.JSONException;
import org.json.JSONObject;

public class WKRedPacketContent extends WKMessageContent {
    // 定义需发送给对方的字段
    public String redpacket_no; //
    public String blessing;//

    public WKRedPacketContent() {
        this.type = WKContentType.WK_RED_PACKAGE; //指定消息类型
    }

    public WKRedPacketContent(String redpacket_no,String blessing) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("redpacket_no", redpacket_no);
            jsonObject.put("blessing", blessing);
            this.content = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.redpacket_no = redpacket_no;
        this.blessing = blessing;
        this.type = WKContentType.WK_RED_PACKAGE; //指定消息类型
    }
    @Override
    public JSONObject encodeMsg() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("redpacket_no", redpacket_no);
            jsonObject.put("blessing", blessing);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public WKMessageContent decodeMsg(JSONObject jsonObject) {
        redpacket_no = jsonObject.optString("red_packet_name");
        blessing = jsonObject.optString("blessing");
        return this;
    }

    public WKRedPacketContent(Parcel in) {
        super(in);
        redpacket_no = in.readString();
        blessing = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }


    public static final Creator<WKRedPacketContent> CREATOR = new Creator<WKRedPacketContent>() {
        @Override
        public WKRedPacketContent createFromParcel(Parcel in) {
            return new WKRedPacketContent(in);
        }

        @Override
        public WKRedPacketContent[] newArray(int size) {
            return new WKRedPacketContent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String getDisplayContent() {
        return "[红包]";
    }
}
