package com.chat.wkwallet.entity;

public class NameAuthDataEntity {
    /**
     * 证件编号
     */
    private String certNo;
    /**
     * 证件类型(1身份证)
     */
    private Long certType;
    /**
     * 真实名字
     */
    private String name;

    public String getcertNo() { return certNo; }
    public void setcertNo(String value) { this.certNo = value; }

    public Long getcertType() { return certType; }
    public void setcertType(Long value) { this.certType = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
}
