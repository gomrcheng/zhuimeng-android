package com.chat.wkredpacket.model;

import java.util.List;

public class RedPackgeDetilBean {

    private String red_packet_name;
    private double fee;
    private String uid;
    private String uname;
    private int status;
    private String start_time;
    private String end_time;
    private String finish_time;
    private double total_second;
    private int finished_count;
    private int total_count;
    private double finished_fee;

    public double getFinished_fee() {
        return finished_fee;
    }

    public void setFinished_fee(double finished_fee) {
        this.finished_fee = finished_fee;
    }

    public int getFinished_count() {
        return finished_count;
    }

    public void setFinished_count(int finished_count) {
        this.finished_count = finished_count;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    private List<RedOneBean> finish_history;

    public String getRed_packet_name() {
        return red_packet_name;
    }

    public void setRed_packet_name(String red_packet_name) {
        this.red_packet_name = red_packet_name;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public double getTotal_second() {
        return total_second;
    }

    public void setTotal_second(double total_second) {
        this.total_second = total_second;
    }

    public List<RedOneBean> getFinish_history() {
        return finish_history;
    }

    public void setFinish_history(List<RedOneBean> finish_history) {
        this.finish_history = finish_history;
    }
}

