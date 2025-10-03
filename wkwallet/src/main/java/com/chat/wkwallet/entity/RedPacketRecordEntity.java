package com.chat.wkwallet.entity;

public class RedPacketRecordEntity {
    private String sender_name;
    private String open_date;
    private int open_amount;
    private String sender_uid;

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public int getOpen_amount() {
        return open_amount;
    }

    public void setOpen_amount(int open_amount) {
        this.open_amount = open_amount;
    }

    public String getSender_uid() {
        return sender_uid;
    }

    public void setSender_uid(String sender_uid) {
        this.sender_uid = sender_uid;
    }
}
