package com.chat.wktransfer.service;

import com.alibaba.fastjson.JSONObject;
import com.chat.base.base.WKBaseModel;
import com.chat.base.net.ICommonListener;
import com.chat.base.net.IRequestResultListener;
import com.chat.base.net.entity.CommonResponse;
import com.chat.base.utils.WKToastUtils;
import com.chat.wktransfer.entity.GetPayTokenEntity;
import com.chat.wktransfer.entity.PassworedfreeItemEntity;
import com.chat.wktransfer.entity.PayOrderDetailBean;
import com.chat.wktransfer.entity.TranferDetailBean;
import com.chat.wktransfer.entity.TransferGetEntity;
import com.chat.wktransfer.entity.WalletBean;
import com.chat.wktransfer.entity.WalletInfo;

import java.util.List;


/**
 * 4/21/21 6:23 PM
 */
public class WKCommonModel extends WKBaseModel {

    private WKCommonModel() {
    }

    public void createtransfers(String pay_token,int amount,String to_uid,String remark,final ICommonListener iCommonListener) {
        JSONObject reqJson = new JSONObject();
        reqJson.put("pay_token", pay_token);
        reqJson.put("amount", amount);
        reqJson.put("to_uid", to_uid);
        reqJson.put("remark", remark);
        request(createService(WKCommonService.class).createtransfers(reqJson), new IRequestResultListener<CommonResponse>() {
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

    public void transferRec(String transferId, ICommonListener iCommonListener) {
        request(createService(WKCommonService.class).transferRec(transferId), new IRequestResultListener<CommonResponse>() {
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

    public interface ItransferGetListener {
        void onResult(TransferGetEntity transferGetEntity);
    }
    public void transferGet(String transfer_no,final ItransferGetListener iTransferGetListener) {
        request(createService(WKCommonService.class).transferGet(transfer_no), new IRequestResultListener<TransferGetEntity>() {
            @Override
            public void onSuccess(TransferGetEntity result) {
                iTransferGetListener.onResult(result);
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
                iGetPayTokenListener.onResult(new GetPayTokenEntity());
            }
        });
    }






    private static class CommonModelBinder {
        final static WKCommonModel model = new WKCommonModel();
    }

    public static WKCommonModel getInstance() {
        return CommonModelBinder.model;
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

    public interface IWalletInfoListener {
        void onResult(int amount, int status,int password_is_set);
    }


    public interface IGetTransferDetail {
        void onResult(TranferDetailBean entity);
    }



}
