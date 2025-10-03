package com.chat.wkredpacket.entity;

import java.util.Map;

public class RedpackgePayResultEntity {
    /**
     * 跳转方式
     */
    private String forwardType;
    /**
     * 第三方交易编号
     */
    private String outTradeNo;
    /**
     * 支付编号
     */
    private String payNo;
    /**
     * 支付结果
     */
    private Map<String, Object> result;
    /**
     * 交易编号
     */
    private String tradeNo;

    public String getForwardType() { return forwardType; }
    public void setForwardType(String value) { this.forwardType = value; }

    public String getOutTradeNo() { return outTradeNo; }
    public void setOutTradeNo(String value) { this.outTradeNo = value; }

    public String getPayNo() { return payNo; }
    public void setPayNo(String value) { this.payNo = value; }

    public Map<String, Object> getResult() { return result; }
    public void setResult(Map<String, Object> value) { this.result = value; }

    public String getTradeNo() { return tradeNo; }
    public void setTradeNo(String value) { this.tradeNo = value; }
}
