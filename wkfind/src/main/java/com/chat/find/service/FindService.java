package com.chat.find.service;


import com.chat.find.entity.DiscvoeryEntity;
import com.chat.find.entity.WkFindBanner;
import com.chat.find.entity.WkFindList;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface FindService {

    //获取banner列表
    @GET("workplace/banner")
    Observable<List<WkFindBanner>> getBannerList();

    //获取发现 应用列表
    @GET("workplace/app")
    Observable<List<WkFindList>> getFindList();

    //获取发现 应用列表
    @GET("workplace/app")
    Observable<List<DiscvoeryEntity>> discovery();


}
