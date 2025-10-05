package com.chat.find.entity;

public class DiscvoeryEntity {
    private String app_id;
    private int sort_num;
    private String name;
    private String icon;
    private String description;
    private int status;
    private int jump_type;
    private String app_route;
    private String web_route;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    // Getter and Setter methods
    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public int getSort_num() {
        return sort_num;
    }

    public void setSort_num(int sort_num) {
        this.sort_num = sort_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getJump_type() {
        return jump_type;
    }

    public void setJump_type(int jump_type) {
        this.jump_type = jump_type;
    }

    public String getApp_route() {
        return app_route;
    }

    public void setApp_route(String app_route) {
        this.app_route = app_route;
    }

    public String getWeb_route() {
        return web_route;
    }

    public void setWeb_route(String web_route) {
        this.web_route = web_route;
    }
}
