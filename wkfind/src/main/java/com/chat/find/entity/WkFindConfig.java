package com.chat.find.entity;

/**
 * 发现模块配置
 */
public class WkFindConfig {

    private int hasBtnNav;
    private int hasBanner;
    private int hasFindList;
    private String createdAt;
    private String exploreTitle;
    private String icon;
    private String clickIcon;
    private int id;
    private String updatedAt;
    private String password;
    private int has_encrypt;

    public int getHas_encrypt() {
        return has_encrypt;
    }

    public void setHas_encrypt(int has_encrypt) {
        this.has_encrypt = has_encrypt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClickIcon() {
        return clickIcon;
    }

    public void setClickIcon(String clickIcon) {
        this.clickIcon = clickIcon;
    }

    public int getHasBtnNav() {
        return hasBtnNav;
    }

    public void setHasBtnNav(int hasBtnNav) {
        this.hasBtnNav = hasBtnNav;
    }

    public int getHasBanner() {
        return hasBanner;
    }

    public void setHasBanner(int hasBanner) {
        this.hasBanner = hasBanner;
    }

    public int getHasFindList() {
        return hasFindList;
    }

    public void setHasFindList(int hasFindList) {
        this.hasFindList = hasFindList;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getExploreTitle() {
        return exploreTitle;
    }

    public void setExploreTitle(String exploreTitle) {
        this.exploreTitle = exploreTitle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
