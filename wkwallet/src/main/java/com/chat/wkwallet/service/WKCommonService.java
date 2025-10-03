package com.chat.wkwallet.service;

import com.alibaba.fastjson.JSONObject;
import com.chat.base.net.entity.CommonResponse;
import com.chat.wkwallet.entity.BankCardDetailEntity;
import com.chat.wkwallet.entity.BankCardInfoEntity;
import com.chat.wkwallet.entity.CoinRate;
import com.chat.wkwallet.entity.CoinTypeInfo;
import com.chat.wkwallet.entity.GetCoinInfoEntity;
import com.chat.wkwallet.entity.GetCoinRateEntity;
import com.chat.wkwallet.entity.GetCoinTypesEntity;
import com.chat.wkwallet.entity.GetPayTokenEntity;
import com.chat.wkwallet.entity.GetUserWalletAddresses;
import com.chat.wkwallet.entity.KefuEntity;
import com.chat.wkwallet.entity.NameAuthEntity;
import com.chat.wkwallet.entity.PassworedfreeItemEntity;
import com.chat.wkwallet.entity.RechargeConfigEntity;
import com.chat.wkwallet.entity.RedPacketRecordEntity;
import com.chat.wkwallet.entity.RedPacketRecordInTotalEntity;
import com.chat.wkwallet.entity.RedPacketSendRecordEntity;
import com.chat.wkwallet.entity.TransactionRecordEntity;
import com.chat.wkwallet.entity.WalletBean;
import com.chat.wkwallet.entity.WalletInfo;
import com.chat.wkwallet.entity.WalletPasswordFreeItemsEntity;
import com.chat.wkwallet.entity.WithdrawConfigEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @POST("balance/user/open")
    Observable<CommonResponse> userOpenBalance(@Body JSONObject reqJson);

    @POST("balance/recharge")
    Observable<CommonResponse> userRecharge(@Body JSONObject reqJson);

    @POST("balance/password/set")
    Observable<CommonResponse> passwordSet(@Body JSONObject reqJson);

    @POST("balance/record")
    Observable<WalletBean> getBalanceRecord(@Body JSONObject reqJson);

    @POST("balance/red_packet/send")
    Observable<CommonResponse> sendRedPacket(@Body JSONObject reqJson);

    @POST("balance/transfer/send")
    Observable<CommonResponse> sendTransfer(@Body JSONObject reqJson);

    @POST("balance/transfer/operation")
    Observable<CommonResponse> recTransfer(@Body JSONObject jsonObject);



    @POST("impay/wallet/smscode/paypwd")
    Observable<CommonResponse> smscodeSend();

    @POST("impay/wallet/smscode/paypwdverity")
    Observable<CommonResponse> paypwdverity(@Body JSONObject jsonObject);

    @POST("impay/wallet/password")
    Observable<CommonResponse> setpaypassword(@Body JSONObject jsonObject);

    @GET("pay/rechargeconfig")
    Observable<RechargeConfigEntity> rechargeconfig();

    @GET("impay/withdrawconfig")
    Observable<WithdrawConfigEntity> withdrawconfig();

    @GET("impay/nameauth")
    Observable<NameAuthEntity> nameauthget();

    @GET("impay/bankcard/info")
    Observable<BankCardInfoEntity> bankcardinfo(@Query("bankcard") String cardNum);

    @POST("impay/bankcards")
    Observable<CommonResponse> addbankcards(@Body JSONObject reqJson);

    @GET("impay/bankcards")
    Observable<List<BankCardDetailEntity>> getbankcards();

    @POST("impay/wallet/pay/token")
    Observable<GetPayTokenEntity> getpaytoken(@Body JSONObject jsonObject);

    @POST("impay/withdraw")
    Observable<CommonResponse> withdraw(@Body JSONObject jsonObject);

    @GET("impay/wallet/records")
    Observable<List<TransactionRecordEntity>> getwalletrecords(@Query("page_index")int page_index, @Query("page_size")int page_size);

    @GET("impay/wallet/passwordfree/items")
    Observable<List<WalletPasswordFreeItemsEntity>> getwalletpasswordfreeitems();

    @POST("impay/wallet/passwordfree/{action}")
    Observable<CommonResponse> setPasswordfree(@Path("action") String action,@Body JSONObject jsonObject);

    @GET("user/customerservices")
    Observable<List<KefuEntity>> customerservices();

    @GET("redpacket/record/in")
    Observable<List<RedPacketRecordEntity>> getRedPacketRecordsIn(@Query("year")String year, @Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize);


    @GET("redpacket/statistics/in")
    Observable<RedPacketRecordInTotalEntity> getRedPacketRecordsTotal(@Query("year")String year);

    @GET("redpacket/statistics/out")
    Observable<RedPacketRecordInTotalEntity> getRedPacketRecordsOutTotal(@Query("year")String year);

    @GET("redpacket/record/out")
    Observable<List<RedPacketSendRecordEntity>> getRedPacketRecordsOut(@Query("year")String year, @Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize);

    @GET("impay/wallet/passwordfree/items/{item}/open")
    Observable<PassworedfreeItemEntity> getpasswordfreeopen(@Path("item") String item);

    @GET("udun/coin_types")
    Observable<GetCoinTypesEntity> getUdunCoinTypes();

    @POST("udun/address")
    Observable<GetCoinInfoEntity> getCoinInfo(@Body JSONObject reqJson);

    @GET("udun/user/addresses")
    Observable<GetUserWalletAddresses> getcoinAddresses(@Query("mainCoinType")int mainCoinType,
                                                        @Query("status")int status,
                                                        @Query("page")int page,
                                                        @Query("size")int size);
    @GET("udun/user/addresses")
    Observable<GetUserWalletAddresses> getcoinAddressesAll(@Query("page")int page,
                                                        @Query("size")int size);

    @POST("udun/user/address")
    Observable<CommonResponse> addCoinAddress(@Body JSONObject reqJson);

    @DELETE("udun/user/address/{id}")
    Observable<CommonResponse> deleteAddress(@Path("id") int id);

    @GET("udun/coin_types/{id}/rate")
    Observable<GetCoinRateEntity> getRate(@Path("id") String id);

}
