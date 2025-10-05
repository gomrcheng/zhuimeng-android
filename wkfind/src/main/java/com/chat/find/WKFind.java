package com.chat.find;

import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.chat.base.config.WKSharedPreferencesUtil;
import com.chat.base.endpoint.EndpointManager;
import com.chat.base.endpoint.entity.ChatSettingCellMenu;
import com.chat.base.entity.UserInfoEntity;
import com.chat.base.entity.UserInfoSetting;
import com.chat.base.entity.WKAPPConfig;
import com.chat.find.entity.WkFindConfig;
import com.chat.find.ui.DiscoveryFragment;
import com.chat.find.ui.FindFragment;
import com.google.gson.Gson;

/**
 * 首页tab发现模块
 */
public class WKFind {
    private WKFind() {
    }

    private static class ConfigBinder {
        private static final WKFind WK_CONFIG = new WKFind();
    }

    public void init(){

        EndpointManager.getInstance().setMethod("get_workplace_fragment", object -> {
                return new DiscoveryFragment();
        });
    }

    public Fragment getWorkplaceFragment() {
        return new FindFragment();
    }

    public static WKFind getInstance() {
        return ConfigBinder.WK_CONFIG;
    }

    public void setUid(String uid) {
        WKSharedPreferencesUtil.getInstance().putSP("wk_uid", uid);
    }

    public String getUid() {
        return WKSharedPreferencesUtil.getInstance().getSP("wk_uid");
    }

    public void setToken(String token) {
        WKSharedPreferencesUtil.getInstance().putSP("wk_token", token);
    }

    public String getToken() {
        return WKSharedPreferencesUtil.getInstance().getSP("wk_token");
    }



    public void saveFindConfig(WkFindConfig wkFindConfig) {
        String json = new Gson().toJson(wkFindConfig);
        WKSharedPreferencesUtil.getInstance().putSP("find_config", json);
    }

    public WkFindConfig getFindConfig() {
        String json = WKSharedPreferencesUtil.getInstance().getSP("find_config");
        WkFindConfig wkFindConfig = null;
        if (!TextUtils.isEmpty(json)) {
            wkFindConfig = new Gson().fromJson(json, WkFindConfig.class);
        }
        if (wkFindConfig == null) {
            wkFindConfig = new WkFindConfig();
        }
        return wkFindConfig;
    }


    public void saveUserInfo(UserInfoEntity userInfoEntity) {
        String json = new Gson().toJson(userInfoEntity);
        WKSharedPreferencesUtil.getInstance().putSP("user_info", json);
    }

    public UserInfoEntity getUserInfo() {
        String json = WKSharedPreferencesUtil.getInstance().getSP("user_info");
        UserInfoEntity userInfoEntity = null;
        if (!TextUtils.isEmpty(json)) {
            userInfoEntity = new Gson().fromJson(json, UserInfoEntity.class);
        }
        if (userInfoEntity == null) {
            userInfoEntity = new UserInfoEntity();
        }
        if (userInfoEntity.setting == null)
            userInfoEntity.setting = new UserInfoSetting();
        return userInfoEntity;
    }
}
