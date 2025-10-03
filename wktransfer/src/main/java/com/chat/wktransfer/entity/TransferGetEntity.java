package com.chat.wktransfer.entity;

public class TransferGetEntity {
    /**
     * 转账金额，单位为分
     */
    private int amount;
    /**
     * 创建时间，格式为yyyy-MM-dd HH:mm:ss
     */
    private String createdAt;
    /**
     * 转账编号
     */
    private String no;
    /**
     * 收款时间，格式为yyyy-MM-dd HH:mm:ss，未收款时为空
     */
    private String receiveAt;
    /**
     * 退款时间，格式为yyyy-MM-dd HH:mm:ss，未退款时为空
     */
    private String refundAt;
    /**
     * 转账状态
     */
    private int status;
    /**
     * 收款人姓名
     */
    private String toName;
    /**
     * 收款人用户ID
     */
    private String toUid;

    public int getAmount() { return amount; }
    public void setAmount(int value) { this.amount = value; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String value) { this.createdAt = value; }

    public String getNo() { return no; }
    public void setNo(String value) { this.no = value; }

    public String getReceiveAt() { return receiveAt; }
    public void setReceiveAt(String value) { this.receiveAt = value; }

    public String getRefundAt() { return refundAt; }
    public void setRefundAt(String value) { this.refundAt = value; }

    public int getStatus() { return status; }
    public void setStatus(int value) { this.status = value; }

    public String getToName() { return toName; }
    public void setToName(String value) { this.toName = value; }

    public String getToUid() { return toUid; }
    public void setToUid(String value) { this.toUid = value; }
}
