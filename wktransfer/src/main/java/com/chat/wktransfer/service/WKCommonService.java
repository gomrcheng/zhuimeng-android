package com.chat.wktransfer.service;

import com.alibaba.fastjson.JSONObject;
import com.chat.base.net.entity.CommonResponse;
import com.chat.wktransfer.entity.GetPayTokenEntity;
import com.chat.wktransfer.entity.PassworedfreeItemEntity;
import com.chat.wktransfer.entity.TransferGetEntity;
import com.chat.wktransfer.entity.WalletInfo;
import com.chat.wktransfer.entity.WalletPasswordFreeItemsEntity;

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

    @POST("impay/wallet/smscode/paypwd")
    Observable<CommonResponse> smscodeSend();

    @POST("impay/wallet/smscode/paypwdverity")
    Observable<CommonResponse> paypwdverity(@Body JSONObject jsonObject);

    @POST("impay/wallet/password")
    Observable<CommonResponse> setpaypassword(@Body JSONObject jsonObject);



    @POST("impay/bankcards")
    Observable<CommonResponse> addbankcards(@Body JSONObject reqJson);


    @POST("impay/withdraw")
    Observable<CommonResponse> withdraw(@Body JSONObject jsonObject);


    @GET("impay/wallet/passwordfree/items")
    Observable<List<WalletPasswordFreeItemsEntity>> getwalletpasswordfreeitems();

    @POST("impay/wallet/pay/token")
    Observable<GetPayTokenEntity> getpaytoken(@Body JSONObject jsonObject);


    @POST("impay/wallet/passwordfree/{action}")
    Observable<CommonResponse> setPasswordfree(@Path("action") String action,@Body JSONObject jsonObject);






    @GET("impay/wallet/passwordfree/items/{item}/open")
    Observable<PassworedfreeItemEntity> getpasswordfreeopen(@Path("item") String item);


    @POST("transfers")
    Observable<CommonResponse> createtransfers(@Body JSONObject reqJson);

    @GET("transfers/{transfer_no}")
    Observable<TransferGetEntity> transferGet(@Path("transfer_no") String transfer_no);

    @POST("transfers/{transfer_no}/receive")
    Observable<CommonResponse> transferRec(@Path("transfer_no") String transferId);

}
