package com.chat.wkwallet.entity;

public class RedPacketSendRecordEntity {
    /**
     * 红包金额(单位分)
     */
    private Long amount;
    /**
     * 创建日期
     */
    private String createDate;
    /**
     * 红包数量
     */
    private Long num;
    /**
     * 红包已打开数量
     */
    private Long openNum;
    /**
     * 红包编号
     */
    private String redpacketNo;
    /**
     * 红包类型(1固定金额 2随机金额)
     */
    private Long type;

    public Long getAmount() { return amount; }
    public void setAmount(Long value) { this.amount = value; }

    public String getCreateDate() { return createDate; }
    public void setCreateDate(String value) { this.createDate = value; }

    public Long getNum() { return num; }
    public void setNum(Long value) { this.num = value; }

    public Long getOpenNum() { return openNum; }
    public void setOpenNum(Long value) { this.openNum = value; }

    public String getRedpacketNo() { return redpacketNo; }
    public void setRedpacketNo(String value) { this.redpacketNo = value; }

    public Long getType() { return type; }
    public void setType(Long value) { this.type = value; }
}
