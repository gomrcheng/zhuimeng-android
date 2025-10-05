package com.chat.find.entity;

import com.chat.base.endpoint.entity.BaseEndpoint;
import com.chat.base.endpoint.entity.PersonalInfoMenu;

public class DiscoveryInfoMenu extends BaseEndpoint {
    public PersonalInfoMenu.IPersonalInfoMenuClick iPersonalInfoMenuClick;
    public boolean isNewVersionIv = false;
    public String imgUrl;

    public DiscoveryInfoMenu(String imgUrl,int imgResourceID, String text, PersonalInfoMenu.IPersonalInfoMenuClick iPersonalInfoMenuClick) {
        this.imgUrl = imgUrl;
        this.imgResourceID = imgResourceID;
        this.text = text;
        this.iPersonalInfoMenuClick = iPersonalInfoMenuClick;
    }

    public DiscoveryInfoMenu(int imgResourceID, String text, PersonalInfoMenu.IPersonalInfoMenuClick iPersonalInfoMenuClick) {
        this.imgResourceID = imgResourceID;
        this.text = text;
        this.iPersonalInfoMenuClick = iPersonalInfoMenuClick;
    }

    public void setIsNewVersionIv(boolean is) {
        isNewVersionIv = is;
    }

    public interface IPersonalInfoMenuClick {
        void onClick();
    }
}
