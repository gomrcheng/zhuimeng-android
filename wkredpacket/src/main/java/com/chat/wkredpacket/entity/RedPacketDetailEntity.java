package com.chat.wkredpacket.entity;

import java.io.Serializable;
import java.util.List;

public class RedPacketDetailEntity implements Serializable {
    /**
     * 红包总金额(单位分)
     */
    private int amount;
    /**
     * 红包剩余金额(单位分)
     */
    private int balance;
    /**
     * 祝福语
     */
    private String blessing;
    /**
     * 频道类型(1个人红包 2群红包)
     */
    private Long channelType;
    /**
     * 红包多久抢完(单位秒)
     */
    private Long finishedSpend;
    /**
     * 红包抢完时间(10位时间戳)
     */
    private Long finishedTime;
    /**
     * 我领取的金额(单位分)
     */
    private int myOpenAmount;
    /**
     * 红包已打开数量
     */
    private int openNum;
    /**
     * 红包领取记录
     */
    private List<Record> records;
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
     * 红包状态
     */
    private int status;
    /**
     * 红包总数量
     */
    private int totalNum;

    public int getAmount() { return amount; }
    public void setAmount(int value) { this.amount = value; }

    public int getBalance() { return balance; }
    public void setBalance(int value) { this.balance = value; }

    public String getBlessing() { return blessing; }
    public void setBlessing(String value) { this.blessing = value; }

    public Long getChannelType() { return channelType; }
    public void setChannelType(Long value) { this.channelType = value; }

    public Long getFinishedSpend() { return finishedSpend; }
    public void setFinishedSpend(Long value) { this.finishedSpend = value; }

    public Long getFinishedTime() { return finishedTime; }
    public void setFinishedTime(Long value) { this.finishedTime = value; }

    public int getMyOpenAmount() { return myOpenAmount; }
    public void setMyOpenAmount(int value) { this.myOpenAmount = value; }

    public int getOpenNum() { return openNum; }
    public void setOpenNum(int value) { this.openNum = value; }

    public List<Record> getRecords() { return records; }
    public void setRecords(List<Record> records) { this.records = records; }

    public String getRedpacketNo() { return redpacketNo; }
    public void setRedpacketNo(String value) { this.redpacketNo = value; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String value) { this.senderName = value; }

    public String getSenderUid() { return senderUid; }
    public void setSenderUid(String value) { this.senderUid = value; }

    public int getStatus() { return status; }
    public void setStatus(int value) { this.status = value; }

    public int getTotalNum() { return totalNum; }
    public void setTotalNum(int value) { this.totalNum = value; }
}
