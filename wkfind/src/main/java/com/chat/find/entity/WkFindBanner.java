package com.chat.find.entity;

/**
 * 发现轮播图bean
 */
public class WkFindBanner {
    private String banner_no;
    private String title;
    private String cover;
    private int jump_type;
    private String route;
    private String description;

    public String getBanner_no() {
        return banner_no;
    }

    public void setBanner_no(String banner_no) {
        this.banner_no = banner_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getJump_type() {
        return jump_type;
    }

    public void setJump_type(int jump_type) {
        this.jump_type = jump_type;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
