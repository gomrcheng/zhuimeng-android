package com.chat.wkredpacket.entity;

public class RedPacketGetEntity {
    /**
     * 祝福语
     */
    private String blessing;
    /**
     * 红包编号
     */
    private String redpacketNo;
    /**
     * 发红包人的名字
     */
    private String senderName;
    /**
     * 发红包人的UID
     */
    private String senderUid;
    /**
     * 红包状态(0等待支付 1已支付 2当前用户已领取 3已领取完毕 4已过期)
     */
    private int status;

    public String getBlessing() { return blessing; }
    public void setBlessing(String value) { this.blessing = value; }

    public String getRedpacketNo() { return redpacketNo; }
    public void setRedpacketNo(String value) { this.redpacketNo = value; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String value) { this.senderName = value; }

    public String getSenderUid() { return senderUid; }
    public void setSenderUid(String value) { this.senderUid = value; }

    public int getStatus() { return status; }
    public void setStatus(int value) { this.status = value; }
}
