package com.chat.uikit.signal;

import java.io.Serializable;

public class SignalNode implements Serializable {
    private String name;
    private String speed;
    private String desc;
    private String ssl;
    private String realLine;
    private boolean isSelected;

    public SignalNode(String name, String speed, String desc,String realLine,String ssl) {
        this.name = name;
        this.speed = speed;
        this.desc = desc;
        this.realLine = realLine;
        this.isSelected = false;
        this.ssl = ssl;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpeed() { return speed; }
    public void setSpeed(String speed) { this.speed = speed; }


    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRealLine() {
        return realLine;
    }

    public void setRealLine(String realLine) {
        this.realLine = realLine;
    }

    public String getSsl() {
        return ssl;
    }

    public void setSsl(String ssl) {
        this.ssl = ssl;
    }
}
