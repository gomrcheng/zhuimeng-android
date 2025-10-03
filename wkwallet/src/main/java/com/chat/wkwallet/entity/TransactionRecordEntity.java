package com.chat.wkwallet.entity;

public class TransactionRecordEntity {
    /**
     * 金额(单位分)
     */
    private Long amount;
    /**
     * 余额(单位分)
     */
    private Long balance;
    /**
     * 记录产生时间
     */
    private String createdAt;
    /**
     * 记录ID
     */
    private String recordid;
    /**
     * 备注
     */
    private String remark;
    /**
     * 记录标题
     */
    private String title;

    public Long getAmount() { return amount; }
    public void setAmount(Long value) { this.amount = value; }

    public Long getBalance() { return balance; }
    public void setBalance(Long value) { this.balance = value; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String value) { this.createdAt = value; }

    public String getRecordid() { return recordid; }
    public void setRecordid(String value) { this.recordid = value; }

    public String getRemark() { return remark; }
    public void setRemark(String value) { this.remark = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }
}
