package com.chat.wkwallet.entity;

public class BankCardInfoEntity {
    private BankCardInfoDataEntity data;
    /**
     * 银行卡是否有效(0无效 1有效)
     */
    private Long vaild;

    public BankCardInfoDataEntity getData() { return data; }
    public void setData(BankCardInfoDataEntity value) { this.data = value; }

    public Long getVaild() { return vaild; }
    public void setVaild(Long value) { this.vaild = value; }


}
