package com.chat.wkwallet.entity;

public class GetCoinInfoEntity {
    /**
     * 钱包地址
     */
    private String address;
    /**
     * 地址别名
     */
    private String alias;
    /**
     * 地址余额
     */
    private String balance;
    /**
     * 回调URL
     */
    private String callurl;
    /**
     * 币种类型
     */
    private Long coinType;
    /**
     * 地址ID
     */
    private Long id;
    /**
     * 商户ID
     */
    private String merchantid;
    /**
     * 状态，0:未激活 1:正常 2:禁用
     */
    private Long status;
    /**
     * 币种符号
     */
    private String symbol;
    /**
     * 用户ID
     */
    private String userid;
    /**
     * 钱包ID
     */
    private String walletid;

    private String tips;

    public String getAddress() { return address; }
    public void setAddress(String value) { this.address = value; }

    public String getAlias() { return alias; }
    public void setAlias(String value) { this.alias = value; }

    public String getBalance() { return balance; }
    public void setBalance(String value) { this.balance = value; }

    public String getCallurl() { return callurl; }
    public void setCallurl(String value) { this.callurl = value; }

    public Long getCoinType() { return coinType; }
    public void setCoinType(Long value) { this.coinType = value; }


    public Long getid() { return id; }
    public void setid(Long value) { this.id = value; }


    public String getMerchantid() { return merchantid; }
    public void setMerchantid(String value) { this.merchantid = value; }

    public Long getStatus() { return status; }
    public void setStatus(Long value) { this.status = value; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String value) { this.symbol = value; }


    public String getUserid() { return userid; }
    public void setUserid(String value) { this.userid = value; }

    public String getWalletid() { return walletid; }
    public void setWalletid(String value) { this.walletid = value; }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
