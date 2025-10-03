package com.chat.wkwallet.entity;

import java.io.Serializable;

public class CoinTypeInfo implements Serializable {

    private int  id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    /**
     * 币种余额
     */
    private Long balance;
    /**
     * 币种名称
     */
    private String coinName;
    /**
     * 币种类型
     */
    private String coinType;
    /**
     * 小数位数
     */
    private String decimals;
    /**
     * 币种logo地址
     */
    private String logo;
    /**
     * 主币种类型
     */
    private String mainCoinType;
    /**
     * 主币种单位
     */
    private String mainSymbol;
    /**
     * 币种简称
     */
    private String name;
    /**
     * 币种符号
     */
    private String symbol;
    /**
     * 代币状态，0:主币 1:代币
     */
    private Long tokenStatus;

    private String tips;
    private String minTrade;

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getMinTrade() {
        return minTrade;
    }

    public void setMinTrade(String minTrade) {
        this.minTrade = minTrade;
    }

    public Long getBalance() { return balance; }
    public void setBalance(Long value) { this.balance = value; }

    public String getCoinName() { return coinName; }
    public void setCoinName(String value) { this.coinName = value; }

    public String getCoinType() { return coinType; }
    public void setCoinType(String value) { this.coinType = value; }

    public String getDecimals() { return decimals; }
    public void setDecimals(String value) { this.decimals = value; }

    public String getLogo() { return logo; }
    public void setLogo(String value) { this.logo = value; }

    public String getMainCoinType() { return mainCoinType; }
    public void setMainCoinType(String value) { this.mainCoinType = value; }

    public String getMainSymbol() { return mainSymbol; }
    public void setMainSymbol(String value) { this.mainSymbol = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String value) { this.symbol = value; }

    public Long getTokenStatus() { return tokenStatus; }
    public void setTokenStatus(Long value) { this.tokenStatus = value; }


}
