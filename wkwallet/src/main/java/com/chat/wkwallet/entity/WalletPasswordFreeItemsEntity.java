package com.chat.wkwallet.entity;

public class WalletPasswordFreeItemsEntity {
    /**
     * 项目描述
     */
    private String desc;
    /**
     * 免密项目
     */
    private String item;
    /**
     * 剩余次数(-1无限制)
     */
    private Long leftNum;
    /**
     * 是否开启(0否 1是)
     */
    private Long open;
    /**
     * 项目标题
     */
    private String title;

    public String getDesc() { return desc; }
    public void setDesc(String value) { this.desc = value; }

    public String getItem() { return item; }
    public void setItem(String value) { this.item = value; }

    public Long getLeftNum() { return leftNum; }
    public void setLeftNum(Long value) { this.leftNum = value; }

    public Long getOpen() { return open; }
    public void setOpen(Long value) { this.open = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }
}
