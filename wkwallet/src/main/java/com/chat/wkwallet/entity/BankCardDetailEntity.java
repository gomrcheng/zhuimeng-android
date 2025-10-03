package com.chat.wkwallet.entity;

import java.io.Serializable;

public class BankCardDetailEntity implements Serializable {
    /**
     * 银行封面图 URL
     */
    private String bankCover;
    /**
     * 银行logo URL
     */
    private String bankLogo;
    /**
     * 银行卡号(脱敏)
     */
    private String bankcard;
    /**
     * 银行卡类型(DC储蓄卡 CC信用卡)
     */
    private String bankcardType;
    /**
     * 银行卡类型名称
     */
    private String bankcardTypeName;
    /**
     * 银行代码
     */
    private String bankcode;
    /**
     * 银行名称
     */
    private String bankname;
    /**
     * 银行卡ID
     */
    private Long id;

    public String getBankCover() { return bankCover; }
    public void setBankCover(String value) { this.bankCover = value; }

    public String getBankLogo() { return bankLogo; }
    public void setBankLogo(String value) { this.bankLogo = value; }

    public String getBankcard() { return bankcard; }
    public void setBankcard(String value) { this.bankcard = value; }

    public String getBankcardType() { return bankcardType; }
    public void setBankcardType(String value) { this.bankcardType = value; }

    public String getBankcardTypeName() { return bankcardTypeName; }
    public void setBankcardTypeName(String value) { this.bankcardTypeName = value; }

    public String getBankcode() { return bankcode; }
    public void setBankcode(String value) { this.bankcode = value; }

    public String getBankname() { return bankname; }
    public void setBankname(String value) { this.bankname = value; }

    public Long getid() { return id; }
    public void setid(Long value) { this.id = value; }
}
