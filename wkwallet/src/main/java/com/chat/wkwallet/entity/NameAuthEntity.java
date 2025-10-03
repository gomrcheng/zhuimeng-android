package com.chat.wkwallet.entity;

public class NameAuthEntity {
    private NameAuthDataEntity data;
    /**
     * 是否已认证(0否 1是)
     */
    private Long isAuth;

    public NameAuthDataEntity getData() { return data; }
    public void setData(NameAuthDataEntity value) { this.data = value; }

    public Long getIsAuth() { return isAuth; }
    public void setIsAuth(Long value) { this.isAuth = value; }
}

