package com.chat.wkredpacket.entity;

import java.io.Serializable;

public class Record implements Serializable {
    /**
     * 是否是最佳手气(0否 1是)
     */
    private int is_luck;
    /**
     * 打开人的名称
     */
    private String name;
    /**
     * 打开金额(单位分)
     */
    private int open_amount;
    /**
     * 打开时间(10位时间戳)
     */
    private Long open_time;
    /**
     * 打开人的UID
     */
    private String uid;

    public int getIs_luck() {
        return is_luck;
    }

    public void setIs_luck(int is_luck) {
        this.is_luck = is_luck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOpen_amount() {
        return open_amount;
    }

    public void setOpen_amount(int open_amount) {
        this.open_amount = open_amount;
    }

    public Long getOpen_time() {
        return open_time;
    }

    public void setOpen_time(Long open_time) {
        this.open_time = open_time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
