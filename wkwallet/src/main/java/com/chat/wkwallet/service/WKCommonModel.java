package com.chat.wkwallet.service;

import com.alibaba.fastjson.JSONObject;
import com.chat.base.base.WKBaseModel;
import com.chat.base.net.ICommonListener;
import com.chat.base.net.IRequestResultListener;
import com.chat.base.net.entity.CommonResponse;
import com.chat.base.utils.WKToastUtils;
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


/**
 * 4/21/21 6:23 PM
 */
public class WKCommonModel extends WKBaseModel {

    private WKCommonModel() {
    }

    public void userBalanceOpen(String numPwd,final ICommonListener iCommonListener) {

        JSONObject reqJson = new JSONObject();
        reqJson.put("password", numPwd);
        request(createService(WKCommonService.class).userOpenBalance(reqJson), new IRequestResultListener<CommonResponse>() {

            @Override
            public void onSuccess(CommonResponse result) {
                iCommonListener.onResult(result.status, result.msg);
            }

            @Override
            public void onFail(int code, String msg) {
                iCommonListener.onResult(code, msg);
            }
        });

    }

    public void balanceRecharge(int i, ICommonListener iCommonListener) {

        JSONObject reqJson = new JSONObject();
        reqJson.put("fee", i);
        request(createService(WKCommonService.class).userRecharge(reqJson), new IRequestResultListener<CommonResponse>() {

            @Override
            public void onSuccess(CommonResponse result) {
                iCommonListener.onResult(result.status, result.msg);
            }

            @Override
            public void onFail(int code, String msg) {
                iCommonListener.onResult(code, msg);
            }
        });

    }

    public void passwordSet(String oldPwd, String newPwd, ICommonListener iCommonListener) {
        JSONObject reqJson = new JSONObject();
        /**
         * {
         *   "pay_password": "string",
         *   "old_pay_password": "string",
         *   "login_password": "string"
         * }
         */
        reqJson.put("pay_password", newPwd);
        reqJson.put("old_pay_password", oldPwd);
        request(createService(WKCommonService.class).passwordSet(reqJson), new IRequestResultListener<CommonResponse>() {

            @Override
            public void onSuccess(CommonResponse result) {
                iCommonListener.onResult(result.status, result.msg);
            }

            @Override
            public void onFail(int code, String msg) {
                iCommonListener.onResult(code, msg);
            }
        });

    }

    public void sendRedPacket(JSONObject reqJson, ICommonListener iCommonListener) {
        request(createService(WKCommonService.class).sendRedPacket(reqJson), new IRequestResultListener<CommonResponse>() {

            @Override
            public void onSuccess(CommonResponse result) {
                iCommonListener.onResult(result.status, result.msg);
            }

            @Override
            public void onFail(int code, String msg) {
                iCommonListener.onResult(code, msg);
            }
        });
    }

    public void sendTransfer(JSONObject reqJson, ICommonListener iCommonListener) {
        request(createService(WKCommonService.class).sendTransfer(reqJson), new IRequestResultListener<CommonResponse>() {

            @Override
            public void onSuccess(CommonResponse result) {
                iCommonListener.onResult(result.status, result.msg);
            }

            @Override
            public void onFail(int code, String msg) {
                iCommonListener.onResult(code, msg);
            }
        });
    }

    public void addbankcards(JSONObject reqJson, ICommonListener iCommonListener) {
        request(createService(WKCommonService.class).addbankcards(reqJson), new IRequestResultListener<CommonResponse>() {

            @Override
            public void onSuccess(CommonResponse result) {
                iCommonListener.onResult(result.status, result.msg);
            }

            @Override
            public void onFail(int code, String msg) {
                iCommonListener.onResult(code, msg);
            }
        });
    }

    public void withdraw(JSONObject jsonObject,ICommonListener iCommonListener) {
        request(createService(WKCommonService.class).withdraw(jsonObject), new IRequestResultListener<CommonResponse>() {

            @Override
            public void onSuccess(CommonResponse result) {
                iCommonListener.onResult(result.status, result.msg);
            }

            @Override
            public void onFail(int code, String msg) {
                iCommonListener.onResult(code, msg);
            }
        });
    }

    public void addCoinAddress(JSONObject reqJson, ICommonListener iCommonListener) {
        request(createService(WKCommonService.class).addCoinAddress(reqJson), new IRequestResultListener<CommonResponse>() {
            @Override
            public void onSuccess(CommonResponse result) {
                iCommonListener.onResult(result.status, result.msg);
            }
            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });

    }

    public void deleteAddress(int id, ICommonListener iCommonListener) {
        request(createService(WKCommonService.class).deleteAddress(id), new IRequestResultListener<CommonResponse>() {
            @Override
            public void onSuccess(CommonResponse result) {
                iCommonListener.onResult(result.status, result.msg);
            }
            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });

    }

    public interface IGetRateListener {
        void onResult(GetCoinRateEntity getCoinRateEntity);
    }
    public void getRate(String coinId,final IGetRateListener iGetRateListener) {
        request(createService(WKCommonService.class).getRate(coinId), new IRequestResultListener<GetCoinRateEntity>() {
            @Override
            public void onSuccess(GetCoinRateEntity result) {
                iGetRateListener.onResult(result);
            }
            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }

    public interface IGetcoinAddresses {
        void onResult(GetUserWalletAddresses getUserWalletAddresses);
    }
    public void getcoinAddressesAll(int page,int size, final IGetcoinAddresses iGetCoinAddress) {
        request(createService(WKCommonService.class).getcoinAddressesAll(page, size), new IRequestResultListener<GetUserWalletAddresses>() {
            @Override
            public void onSuccess(GetUserWalletAddresses result) {
                iGetCoinAddress.onResult(result);
            }
            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }

    public interface IGetCoinAddress {
        void onResult(GetCoinInfoEntity getCoinInfoEntity);
    }

    public void getCoinAddress(int main_coin_type, final IGetCoinAddress iGetCoinAddress) {
        JSONObject reqJson = new JSONObject();
        reqJson.put("main_coin_type", main_coin_type);
        request(createService(WKCommonService.class).getCoinInfo(reqJson), new IRequestResultListener<GetCoinInfoEntity>() {

            @Override
            public void onSuccess(GetCoinInfoEntity result) {
                iGetCoinAddress.onResult(result);
            }
            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });

    }

    public interface IGetUdunCoinTypesListener {
        void onResult(GetCoinTypesEntity coinTypeInfoList);
    }
    public void getUdunCoinTypes(final IGetUdunCoinTypesListener iGetUdunCoinTypesListener) {
        request(createService(WKCommonService.class).getUdunCoinTypes(), new IRequestResultListener<GetCoinTypesEntity>() {

            @Override
            public void onSuccess(GetCoinTypesEntity result) {
                iGetUdunCoinTypesListener.onResult(result);
            }
            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }


    public interface IGetpasswordfreeopenListener {
        void onResult(PassworedfreeItemEntity passworedfreeItemEntity);
    }
    public void getpasswordfreeopen(String item,final IGetpasswordfreeopenListener iGetCreateRedPacketListener) {
        request(createService(WKCommonService.class).getpasswordfreeopen(item), new IRequestResultListener<PassworedfreeItemEntity>() {

            @Override
            public void onSuccess(PassworedfreeItemEntity result) {
                iGetCreateRedPacketListener.onResult(result);
            }
            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }



    public interface IGetRedPacketRecordsOutListener {
        void onResult(List<RedPacketSendRecordEntity> result);
    }
    public void getRedPacketRecordsOut(String selectedYear, int page, int pageSize, IGetRedPacketRecordsOutListener iGetRedPacketRecordsOutListener) {
        request(createService(WKCommonService.class).getRedPacketRecordsOut(selectedYear,page,pageSize), new IRequestResultListener<List<RedPacketSendRecordEntity>>() {
            @Override
            public void onSuccess(List<RedPacketSendRecordEntity> result) {
                iGetRedPacketRecordsOutListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }


    public interface IGetRedPacketRecordsInTotalListener {
        void onResult(RedPacketRecordInTotalEntity result);
    }
    public void getRedPacketRecordsTotal(String year,final  IGetRedPacketRecordsInTotalListener iGetRedPacketRecordsInTotalListener) {
        request(createService(WKCommonService.class).getRedPacketRecordsTotal(year), new IRequestResultListener<RedPacketRecordInTotalEntity>() {
            @Override
            public void onSuccess(RedPacketRecordInTotalEntity result) {
                iGetRedPacketRecordsInTotalListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }

    public void getRedPacketRecordsOutTotal(String year,final  IGetRedPacketRecordsInTotalListener iGetRedPacketRecordsInTotalListener) {
        request(createService(WKCommonService.class).getRedPacketRecordsOutTotal(year), new IRequestResultListener<RedPacketRecordInTotalEntity>() {
            @Override
            public void onSuccess(RedPacketRecordInTotalEntity result) {
                iGetRedPacketRecordsInTotalListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }


    public interface IGetRedPacketRecordsInListener {
        void onResult(List<RedPacketRecordEntity> result);
    }
    public void getRedPacketRecordsIn(String year,int page_index,int page_size,final IGetRedPacketRecordsInListener iGetRedPacketRecordsInListener) {
        request(createService(WKCommonService.class).getRedPacketRecordsIn(year,page_index,page_size), new IRequestResultListener<List<RedPacketRecordEntity>>() {
            @Override
            public void onSuccess(List<RedPacketRecordEntity> result) {
                iGetRedPacketRecordsInListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });

    }

    public interface IGetKefuListListener {
        void onResult(List<KefuEntity> result);
    }
    public void customerservices(final IGetKefuListListener iGetKefuListListener) {
        request(createService(WKCommonService.class).customerservices(), new IRequestResultListener<List<KefuEntity>>() {
            @Override
            public void onSuccess(List<KefuEntity> result) {
                iGetKefuListListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }

    public interface IGetwalletpasswordfreeitemsListener {
        void onResult(List<WalletPasswordFreeItemsEntity> result);
    }
    public void getwalletpasswordfreeitems(IGetwalletpasswordfreeitemsListener iGetwalletpasswordfreeitemsListener) {
        request(createService(WKCommonService.class).getwalletpasswordfreeitems(), new IRequestResultListener<List<WalletPasswordFreeItemsEntity>>() {
            @Override
            public void onSuccess(List<WalletPasswordFreeItemsEntity> result) {
                iGetwalletpasswordfreeitemsListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }

    public interface IGetWalletRecordsListener {
        void onResult(List<TransactionRecordEntity> result);
    }
    public void getwalletrecords(int page,int size,IGetWalletRecordsListener iGetWalletRecordsListener) {
        request(createService(WKCommonService.class).getwalletrecords(page,size), new IRequestResultListener<List<TransactionRecordEntity>>() {
            @Override
            public void onSuccess(List<TransactionRecordEntity> result) {
                iGetWalletRecordsListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });

    }


    public interface IGetPayTokenListener {
        void onResult(GetPayTokenEntity result);
    }
    public void getpaytoken(JSONObject jsonObject,IGetPayTokenListener iGetPayTokenListener) {
        request(createService(WKCommonService.class).getpaytoken(jsonObject), new IRequestResultListener<GetPayTokenEntity>() {
            @Override
            public void onSuccess(GetPayTokenEntity result) {
                iGetPayTokenListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }

    public void setPasswordfree(String action,JSONObject jsonObject,final ICommonListener iCommonListener) {
        request(createService(WKCommonService.class).setPasswordfree(action,jsonObject), new IRequestResultListener<CommonResponse>() {
            @Override
            public void onSuccess(CommonResponse result) {
                iCommonListener.onResult(result.status, result.msg);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }

    public interface IGetBankcardsListener {
        void onResult(List<BankCardDetailEntity> result);
    }
    public void getbankcards(IGetBankcardsListener getBankcardsListener) {
        request(createService(WKCommonService.class).getbankcards(), new IRequestResultListener<List<BankCardDetailEntity>>() {
            @Override
            public void onSuccess(List<BankCardDetailEntity> result) {
                getBankcardsListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }

    public interface IBankCardInfoListener {
        void onResult(BankCardInfoEntity result);
    }
    public void bankcardinfo(String cardNum,IBankCardInfoListener bankCardInfoListener) {
        request(createService(WKCommonService.class).bankcardinfo(cardNum), new IRequestResultListener<BankCardInfoEntity>() {

            @Override
            public void onSuccess(BankCardInfoEntity result) {
                bankCardInfoListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });

    }

    public interface INameAuthListener {
        void onResult(NameAuthEntity result);
    }
    public void nameauthget(INameAuthListener nameAuthListener) {
        request(createService(WKCommonService.class).nameauthget(), new IRequestResultListener<NameAuthEntity>() {

            @Override
            public void onSuccess(NameAuthEntity result) {
                nameAuthListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }

    public interface IWithdrawConfigListener {
        void onResult(WithdrawConfigEntity result);
    }
    public void withdrawconfig(IWithdrawConfigListener withdrawConfigListener) {
        request(createService(WKCommonService.class).withdrawconfig(), new IRequestResultListener<WithdrawConfigEntity>() {

            @Override
            public void onSuccess(WithdrawConfigEntity result) {
                withdrawConfigListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }

    public interface IRechargeConfigListener {
        void onResult(RechargeConfigEntity result);
    }
    public void rechargeconfig(IRechargeConfigListener rechargeConfigListener) {
        request(createService(WKCommonService.class).rechargeconfig(), new IRequestResultListener<RechargeConfigEntity>() {

            @Override
            public void onSuccess(RechargeConfigEntity result) {
                rechargeConfigListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }



    private static class CommonModelBinder {
        final static WKCommonModel model = new WKCommonModel();
    }

    public static WKCommonModel getInstance() {
        return CommonModelBinder.model;
    }



    public interface IBalanceRecordListener {
        void onResult(WalletBean result);
    }

    public void balanceRecord(final IBalanceRecordListener iBalanceRecordListener) {
        request(createService(WKCommonService.class).getBalanceRecord(new JSONObject()), new IRequestResultListener<WalletBean>() {

            @Override
            public void onSuccess(WalletBean result) {
                iBalanceRecordListener.onResult(result);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast("获取资金明细异常");
            }
        });
    }


    public void getWalletInfo(final IWalletInfoListener walletInfoListener) {
        request(createService(WKCommonService.class).getWalletMy(), new IRequestResultListener<WalletInfo>() {

            @Override
            public void onSuccess(WalletInfo result) {
                walletInfoListener.onResult(result.getAmount(),result.getStatus(), result.getPassword_is_set());
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast("钱包服务接口异常");
            }
        });
    }

    public void smscodeSend(final ICommonListener iCommonListener) {
        request(createService(WKCommonService.class).smscodeSend(), new IRequestResultListener<CommonResponse>() {

            @Override
            public void onSuccess(CommonResponse result) {
                iCommonListener.onResult(result.status, result.msg);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }

    public void paypwdverity(String code,final ICommonListener iCommonListener) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        request(createService(WKCommonService.class).paypwdverity(jsonObject), new IRequestResultListener<CommonResponse>() {

            @Override
            public void onSuccess(CommonResponse result) {
                iCommonListener.onResult(result.status, result.msg);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }

    public void setpaypassword(String smscode, String numPwd, ICommonListener iCommonListener) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",smscode);
        jsonObject.put("password",numPwd);
        request(createService(WKCommonService.class).setpaypassword(jsonObject), new IRequestResultListener<CommonResponse>() {

            @Override
            public void onSuccess(CommonResponse result) {
                iCommonListener.onResult(result.status, result.msg);
            }

            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }


    public interface IBalanceListener {
        void onResult(double balance, boolean balance_enabled);
    }

    public interface IWalletInfoListener {
        void onResult(int amount, int status,int password_is_set);
    }

}
