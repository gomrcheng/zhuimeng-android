package com.chat.wkwallet.entity;

import java.io.Serializable;

public class UserWalletAddress implements Serializable {
    /**
     * 钱包地址
     */
    private String address;
    /**
     * 地址别名
     */
    private String alias;
    /**
     * 地址ID
     */
    private int id;
    /**
     * 是否已验证，0:未验证 1:已验证
     */
    private Long isVerified;
    /**
     * 主币种类型
     */
    private Long mainCoinType;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态，0:禁用 1:正常
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

    public String getAddress() { return address; }
    public void setAddress(String value) { this.address = value; }

    public String getAlias() { return alias; }
    public void setAlias(String value) { this.alias = value; }


    public int getid() { return id; }
    public void setid(int value) { this.id = value; }

    public Long getIsVerified() { return isVerified; }
    public void setIsVerified(Long value) { this.isVerified = value; }

    public Long getMainCoinType() { return mainCoinType; }
    public void setMainCoinType(Long value) { this.mainCoinType = value; }

    public String getRemark() { return remark; }
    public void setRemark(String value) { this.remark = value; }

    public Long getStatus() { return status; }
    public void setStatus(Long value) { this.status = value; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String value) { this.symbol = value; }

    public String getUserid() { return userid; }
    public void setUserid(String value) { this.userid = value; }
}
