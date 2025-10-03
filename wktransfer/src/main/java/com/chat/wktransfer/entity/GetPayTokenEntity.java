package com.chat.wktransfer.entity;

public class GetPayTokenEntity {
    private String pay_token;
    private boolean passwordfree;

    public String getPay_token() {
        return pay_token;
    }

    public void setPay_token(String pay_token) {
        this.pay_token = pay_token;
    }

    public boolean isPasswordfree() {
        return passwordfree;
    }

    public void setPasswordfree(boolean passwordfree) {
        this.passwordfree = passwordfree;
    }
}
