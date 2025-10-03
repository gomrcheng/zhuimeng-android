package com.chat.wkwallet.entity;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

public class RechargeConfigEntity {
    private List<Integer> amounts;
    private Integer wallet_amount;
    private List<ChannelsEntity> channels;

    public List<Integer> getAmounts() {
        return amounts;
    }

    public void setAmounts(List<Integer> amounts) {
        this.amounts = amounts;
    }

    public Integer getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(Integer wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    public List<ChannelsEntity> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelsEntity> channels) {
        this.channels = channels;
    }
}
