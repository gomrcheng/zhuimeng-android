package com.chat.find.entity;

/**
 * 发现列表bean
 */
public class WkFindList {
    private int id;
    private String createdAt;
    private String updatedAt;
    private String name;
    private String icon;
    private String web_route;
    private int has_encrypt;
    private String password;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWeb_route() {
        return web_route;
    }

    public void setWeb_route(String web_route) {
        this.web_route = web_route;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
