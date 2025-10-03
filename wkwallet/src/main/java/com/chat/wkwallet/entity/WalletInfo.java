package com.chat.wkwallet.entity;

public class WalletInfo {
    private int amount;
    private int status;
    private int password_is_set;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPassword_is_set() {
        return password_is_set;
    }

    public void setPassword_is_set(int password_is_set) {
        this.password_is_set = password_is_set;
    }
}
