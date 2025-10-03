package com.chat.wkredpacket.service;

import com.alibaba.fastjson.JSONObject;
import com.chat.base.base.WKBaseModel;
import com.chat.base.net.ICommonListener;
import com.chat.base.net.IRequestResultListener;
import com.chat.base.net.entity.CommonResponse;
import com.chat.base.utils.WKToastUtils;
import com.chat.wkredpacket.entity.CreateRedpacketsEntity;
import com.chat.wkredpacket.entity.GetPayTokenEntity;
import com.chat.wkredpacket.entity.PassworedfreeItemEntity;
import com.chat.wkredpacket.entity.RedPacketDetailEntity;
import com.chat.wkredpacket.entity.RedPacketGetEntity;
import com.chat.wkredpacket.entity.RedpackgePayResultEntity;
import com.chat.wkredpacket.entity.WalletInfo;

import java.util.List;


/**
 * 4/21/21 6:23 PM
 */
public class WKCommonModel extends WKBaseModel {

    private WKCommonModel() {
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

    public void openRedPacket(String redpacket_no,String channelId,byte channelType, ICommonListener iCommonListener) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("channel_id", channelId);
        jsonObject.put("channel_type",  (int) channelType);
        request(createService(WKCommonService.class).openRedpacket(redpacket_no,jsonObject), new IRequestResultListener<CommonResponse>() {
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

    public interface IGetRedPacketListener {
        void onResult(RedPacketGetEntity redPacketGetEntity);
    }

    public interface IWalletInfoListener {
        void onResult(int amount, int status,int password_is_set);
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

    public void getRedpacket(String redpacket_no,final IGetRedPacketListener iGetCreateRedPacketListener) {
        request(createService(WKCommonService.class).getRedpacket(redpacket_no), new IRequestResultListener<RedPacketGetEntity>() {
            @Override
            public void onSuccess(RedPacketGetEntity result) {
                iGetCreateRedPacketListener.onResult(result);
            }
            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });
    }


    public interface IpayRedpackgeListener {
        void onResult(RedpackgePayResultEntity redpackgePayResultEntity);
    }
    public void payRedpackge(String redpacket_no,String pay_token ,final IpayRedpackgeListener iGetCreateRedPacketListener) {
        JSONObject reqJson = new JSONObject();
        reqJson.put("pay_token", pay_token);
        request(createService(WKCommonService.class).payRedpackge(reqJson,redpacket_no), new IRequestResultListener<RedpackgePayResultEntity>() {
            @Override
            public void onSuccess(RedpackgePayResultEntity result) {
                iGetCreateRedPacketListener.onResult(result);
            }
            @Override
            public void onFail(int code, String msg) {
                WKToastUtils.getInstance().showToast(msg);
            }
        });

    }

    public interface IRedPacketDetailListener {
        void onResult(RedPacketDetailEntity result);
    }
    public void getRedPacketDetail(String redpacket_no,final IRedPacketDetailListener iRedPacketDetailListener) {
        request(createService(WKCommonService.class).getRedPacketDetail(redpacket_no), new IRequestResultListener<RedPacketDetailEntity>() {

            @Override
            public void onSuccess(RedPacketDetailEntity result) {
                iRedPacketDetailListener.onResult(result);
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

    public interface IGetCreateRedPacketListener {
        void onResult(CreateRedpacketsEntity createRedpacketsEntity);
    }
    public void createRedPacket(JSONObject jsonObject,IGetCreateRedPacketListener iGetCreateRedPacketListener) {
        request(createService(WKCommonService.class).createRedPacket(jsonObject), new IRequestResultListener<CreateRedpacketsEntity>() {

            @Override
            public void onSuccess(CreateRedpacketsEntity result) {
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











}
