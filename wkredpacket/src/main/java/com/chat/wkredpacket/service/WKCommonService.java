package com.chat.wkredpacket.service;

import com.alibaba.fastjson.JSONObject;
import com.chat.base.net.entity.CommonResponse;
import com.chat.wkredpacket.entity.CreateRedpacketsEntity;
import com.chat.wkredpacket.entity.GetPayTokenEntity;
import com.chat.wkredpacket.entity.OpenRedPackgeEntity;
import com.chat.wkredpacket.entity.PassworedfreeItemEntity;
import com.chat.wkredpacket.entity.RedPacketDetailEntity;
import com.chat.wkredpacket.entity.RedPacketGetEntity;
import com.chat.wkredpacket.entity.RedpackgePayResultEntity;
import com.chat.wkredpacket.entity.WalletBean;
import com.chat.wkredpacket.entity.WalletInfo;
import com.chat.wkredpacket.entity.WalletPasswordFreeItemsEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 4/21/21 6:25 PM
 */
interface WKCommonService {



    //钱包、红包、转账相关
    @GET("impay/wallet/my")
    Observable<WalletInfo> getWalletMy();

    @POST("impay/wallet/pay/token")
    Observable<GetPayTokenEntity> getpaytoken(@Body JSONObject jsonObject);

    @POST("redpackets")
    Observable<CreateRedpacketsEntity> createRedPacket(@Body JSONObject jsonObject);

    @GET("impay/wallet/passwordfree/items/{item}/open")
    Observable<PassworedfreeItemEntity> getpasswordfreeopen(@Path("item") String item);

    @POST("redpackets/{redpacket_no}/pay")
    Observable<RedpackgePayResultEntity> payRedpackge(@Body JSONObject reqJson, @Path("redpacket_no") String redpacket_no);

    @GET("redpackets/{redpacket_no}/get")
    Observable<RedPacketGetEntity> getRedpacket(@Path("redpacket_no")String redpacket_no);

    @POST("redpackets/{redpacket_no}/open")
    Observable<CommonResponse> openRedpacket(@Path("redpacket_no")String redpacket_no,@Body JSONObject reqJson);

    @GET("redpackets/{redpacket_no}")
    Observable<RedPacketDetailEntity> getRedPacketDetail(@Path("redpacket_no")String redpacket_no);

    @POST("impay/wallet/password")
    Observable<CommonResponse> setpaypassword(@Body JSONObject jsonObject);


}
