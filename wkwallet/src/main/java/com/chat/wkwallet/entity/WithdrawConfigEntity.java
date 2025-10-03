package com.chat.wkwallet.entity;

public class WithdrawConfigEntity {
    /**
     * 提现说明
     */
    private String[] explain;
    /**
     * 最大提现金额(单位分)
     */
    private Long maxWithdrawAmount;
    /**
     * 最小提现金额(单位分)
     */
    private Long minWithdrawAmount;
    /**
     * 可用金额(可提现金额)(单位分)
     */
    private Long usableAmount;

    public String[] getExplain() { return explain; }
    public void setExplain(String[] value) { this.explain = value; }

    public Long getMaxWithdrawAmount() { return maxWithdrawAmount; }
    public void setMaxWithdrawAmount(Long value) { this.maxWithdrawAmount = value; }

    public Long getMinWithdrawAmount() { return minWithdrawAmount; }
    public void setMinWithdrawAmount(Long value) { this.minWithdrawAmount = value; }

    public Long getUsableAmount() { return usableAmount; }
    public void setUsableAmount(Long value) { this.usableAmount = value; }
}
