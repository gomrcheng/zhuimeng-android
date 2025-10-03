package com.chat.wkredpacket.entity;

public class OpenRedPackgeEntity {

    private int code;
    private String from_uname;
    private String from_uid;
    private int red_packet_type;
    private double red_packet_fee;
    private double finished_fee;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFrom_uname() {
        return from_uname;
    }

    public void setFrom_uname(String from_uname) {
        this.from_uname = from_uname;
    }

    public String getFrom_uid() {
        return from_uid;
    }

    public void setFrom_uid(String from_uid) {
        this.from_uid = from_uid;
    }

    public int getRed_packet_type() {
        return red_packet_type;
    }

    public void setRed_packet_type(int red_packet_type) {
        this.red_packet_type = red_packet_type;
    }

    public double getRed_packet_fee() {
        return red_packet_fee;
    }

    public void setRed_packet_fee(double red_packet_fee) {
        this.red_packet_fee = red_packet_fee;
    }

    public double getFinished_fee() {
        return finished_fee;
    }

    public void setFinished_fee(double finished_fee) {
        this.finished_fee = finished_fee;
    }
}
