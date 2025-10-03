package com.chat.wkredpacket.model;

import android.os.Parcel;

import com.chat.base.config.WKConfig;
import com.xinbida.wukongim.message.type.WKMsgContentType;
import com.xinbida.wukongim.msgmodel.WKMessageContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WKRedPacketRecContent extends WKMessageContent {

    // 定义需发送给对方的字段
    @Override
    public WKMessageContent decodeMsg(JSONObject jsonObject) {
        try {
            String content = jsonObject.optString("content");
            JSONArray extraArr = jsonObject.getJSONArray("extra");
            for (int i = 0; i < extraArr.length(); i++) {
                JSONObject extra = extraArr.getJSONObject(i);
                String uid = extra.getString("uid");
                String name = extra.getString("name");
                if(uid.equals(WKConfig.getInstance().getUid())){
                    name = "你";
                }
                if(i==0){
                    content = content.replace("{0}"," "+name+" ");
                }else{
                    content = content.replace("{1}"," "+name+" ");
                }
                this.content = content;

            }
        }catch (Exception e){
            System.out.println("=======e:"+e);
        }
        return this;
    }

    public WKRedPacketRecContent(Parcel in) {
        super(in);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }


    public static final Creator<WKRedPacketRecContent> CREATOR = new Creator<WKRedPacketRecContent>() {
        @Override
        public WKRedPacketRecContent createFromParcel(Parcel in) {
            return new WKRedPacketRecContent(in);
        }

        @Override
        public WKRedPacketRecContent[] newArray(int size) {
            return new WKRedPacketRecContent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String getDisplayContent() {
        return "[红包领取]";
    }

}
