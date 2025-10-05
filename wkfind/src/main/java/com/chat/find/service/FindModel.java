package com.chat.find.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chat.base.base.WKBaseModel;
import com.chat.base.net.ICommonListener;
import com.chat.base.net.IRequestResultListener;
import com.chat.find.entity.DiscvoeryEntity;
import com.chat.find.entity.WkFindBanner;
import com.chat.find.entity.WkFindList;

import java.util.List;


public class FindModel extends WKBaseModel {
    private FindModel() {
    }

    private static class LoginModelBinder {
        private static final FindModel loginModel = new FindModel();
    }

    public static FindModel getInstance() {
        return LoginModelBinder.loginModel;
    }

    /**
     * 获取banner列表
     */
    void getBannerList(final IFindBannerListener listener) {
        request(createService(FindService.class).getBannerList(), new IRequestResultListener<>() {
            @Override
            public void onSuccess(List<WkFindBanner> result) {
                listener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {

            }
        });
    }

    /**
     * 获取发现 列表
     */
    void getFindList(final IFindListListener listener) {
        request(createService(FindService.class).getFindList(), new IRequestResultListener<>() {
            @Override
            public void onSuccess(List<WkFindList> result) {
                listener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {

            }
        });
    }

    public void discovery(final ICommonListener iCommonListener) {
        request(createService(FindService.class).discovery(), new IRequestResultListener<>() {
            @Override
            public void onSuccess(List<DiscvoeryEntity> result) {
                if (iCommonListener != null) {
                    JSONArray resJsonArr = new JSONArray();
                    for (DiscvoeryEntity entity : result) {
                        JSONObject jsonObject = (JSONObject) JSON.toJSON(entity);
                        resJsonArr.add(jsonObject);
                    }
                    iCommonListener.onResult(200, resJsonArr.toJSONString());
                }
            }

            @Override
            public void onFail(int code, String msg) {
                if (iCommonListener != null)
                    iCommonListener.onResult(code, msg);
            }
        });
    }


    public interface IFindBannerListener {
        void onResult(List<WkFindBanner> list);
    }
    public interface IFindListListener {
        void onResult(List<WkFindList> list);
    }
}
