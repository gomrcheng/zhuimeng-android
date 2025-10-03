package com.chat.wkwallet.entity;

public class BankCardInfoDataEntity {
    /**
     * 银行卡类型(DC储蓄卡 CC信用卡)
     */
    private String bankcardType;
    /**
     * 银行代码
     */
    private String bankcode;
    /**
     * 银行名称
     */
    private String bankname;

    public String getBankcardType() { return bankcardType; }
    public void setBankcardType(String value) { this.bankcardType = value; }

    public String getBankcode() { return bankcode; }
    public void setBankcode(String value) { this.bankcode = value; }

    public String getBankname() { return bankname; }
    public void setBankname(String value) { this.bankname = value; }
}
