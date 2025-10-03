package com.chat.wkredpacket.ui;

import static com.luck.picture.lib.utils.ToastUtils.showToast;

import android.annotation.SuppressLint;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.fastjson.JSONObject;


import com.chat.base.base.WKBaseFragment;
import com.chat.base.utils.StringUtils;
import com.chat.wkredpacket.R;
import com.chat.wkredpacket.databinding.RedpacketPagerPtBinding;
import com.chat.wkredpacket.entity.CreateRedpacketsEntity;
import com.chat.wkredpacket.entity.GetPayTokenEntity;
import com.chat.wkredpacket.entity.PassworedfreeItemEntity;
import com.chat.wkredpacket.entity.RedpackgePayResultEntity;
import com.chat.wkredpacket.model.WKRedPacketContent;
import com.chat.wkredpacket.service.WKCommonModel;
import com.chat.wkredpacket.utils.RedPacketPayPasswordVerifyDialog;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.xinbida.wukongim.WKIM;
import com.xinbida.wukongim.entity.WKChannelType;
import com.xinbida.wukongim.entity.WKMsgSetting;

import java.text.DecimalFormat;
import java.util.Map;


/**
 *
 * 普通红包
 */
public class PtRedPacketFragment extends WKBaseFragment<RedpacketPagerPtBinding> {

    private double money = 0;

    LoadingPopupView loadingPopup;

    String redpacketNo;
    String blessing;

    String channelId;
    byte channelType;
    @Override
    protected boolean isShowBackLayout() {
        return false;
    }

    @Override
    protected RedpacketPagerPtBinding getViewBinding() {
        return RedpacketPagerPtBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {
        boolean showCount = getArguments().getBoolean("showCount",false);
        channelId =  getArguments().getString("channelId","");
        channelType = getArguments().getByte("channelType");
        if(channelType == WKChannelType.PERSONAL){
            wkVBinding.groupRedPacketNumLl.setVisibility(View.GONE);
        }
        if(showCount){
            wkVBinding.llSq.setVisibility(View.VISIBLE);
        }else{
            wkVBinding.llSq.setVisibility(View.GONE);
        }
        wkVBinding.etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                int posDot = s.indexOf(".");
                if (posDot > 0 && (s.length() - posDot - 1 > 2)) {
                    editable.delete(posDot + 3, posDot + 4);
                }
                if (editable.toString().equals("")) {
                    money = 0;
                } else {
                    try {
                        money = Double.parseDouble(editable.toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                DecimalFormat df = new DecimalFormat("#0.00");

                wkVBinding.tvMoney.setText("" + df.format(money));

            }
        });
        wkVBinding.btCommit.setOnClickListener(v -> {

            String blessing =wkVBinding.etMessage.getText().toString();
            if(blessing == null || blessing.equals("")){
                blessing = wkVBinding.etMessage.getHint().toString();
            }

            if(showCount){
                if(StringUtils.isBlank(wkVBinding.etAcount.getText().toString())){
                    showToast(getActivity(),getString(R.string.string_srhbgs));
                    return;
                }
            }
            if(money==0){
                showToast(getActivity(),getString(R.string.input_gift_count));
                return;
            }
            loadingPopup = new XPopup.Builder(getActivity()).asLoading();
            loadingPopup.setTitle(getString(R.string.loading));
            loadingPopup.show();
            //1:创建红包
            JSONObject reqJson = new JSONObject();
            reqJson.put("scene_id", channelId);
            reqJson.put("scene_type", (int)channelType);
            reqJson.put("amount", StringUtils.yuan2fen(wkVBinding.tvMoney.getText().toString()));
            reqJson.put("type", 1);
            if(!showCount){
                wkVBinding.etAcount.setText("1");
            }
            String redpacketNum = wkVBinding.etAcount2.getText().toString();
            if(redpacketNum == null || redpacketNum.equals("")){
                redpacketNum = "1";
            }
            reqJson.put("num", Integer.parseInt(redpacketNum));
            reqJson.put("blessing", blessing);
            String finalBlessing = blessing;
            WKCommonModel.getInstance().createRedPacket(reqJson, new WKCommonModel.IGetCreateRedPacketListener() {
                @Override
                public void onResult(CreateRedpacketsEntity createRedpacketsEntity) {
                    String redpacketNo = createRedpacketsEntity.getRedpacket_no();
                    //获取红包项目是否免密
                    WKCommonModel.getInstance().getpasswordfreeopen("redpacket", new WKCommonModel.IGetpasswordfreeopenListener() {
                        @Override
                        public void onResult(PassworedfreeItemEntity passworedfreeItemEntity) {
                            if(loadingPopup!=null){
                                loadingPopup.dismiss();
                            }
                            //如果是免密就不需要弹出支付密码
                            if(passworedfreeItemEntity.getOpen() == 1){
                                JSONObject request = new JSONObject();
                                request.put("amount", StringUtils.yuan2fen(wkVBinding.tvMoney.getText().toString()));
                                request.put("item","redpacket");
                                request.put("password", "");
                                WKCommonModel.getInstance().getpaytoken(request, new WKCommonModel.IGetPayTokenListener() {
                                    @Override
                                    public void onResult(GetPayTokenEntity result) {

                                        if(result!=null && result.getPay_token()!=null){
                                            String payToken = result.getPay_token();
                                            //支付红包
                                            payRedpacket(redpacketNo,payToken, finalBlessing);
                                        }
                                        if(loadingPopup!=null){
                                            loadingPopup.dismiss();
                                        }
                                    }
                                });
                            }else{
                                RedPacketPayPasswordVerifyDialog dialog = new RedPacketPayPasswordVerifyDialog(getContext());
                                dialog.setAction(getString(R.string.string_pthb));
                                dialog.setMoney(money+"");
                                dialog.setOnInputFinishListener(password -> {
                                    JSONObject request = new JSONObject();
                                    request.put("amount", StringUtils.yuan2fen(wkVBinding.tvMoney.getText().toString()));
                                    request.put("item","redpacket");
                                    request.put("password", password);
                                    WKCommonModel.getInstance().getpaytoken(request, new WKCommonModel.IGetPayTokenListener() {
                                        @Override
                                        public void onResult(GetPayTokenEntity result) {
                                            if(result!=null && result.getPay_token()!=null){
                                                String payToken = result.getPay_token();
                                                //支付红包
                                                payRedpacket(redpacketNo,payToken, finalBlessing);
                                            }
                                        }
                                    });

                                });
                                try {
                                    dialog.show();
                                    if(loadingPopup!=null){
                                        loadingPopup.dismiss();
                                    }
                                } catch (Exception ignored) {
                                    System.out.println("========");
                                    // 线程切换可能导致弹对话框时activity已经关闭，show会抛异常，
                                }
                            }
                        }
                    });


                }
            });
        });

    }
    public void payRedpacket(String redpacketNo,String payToken,String blessing){
        loadingPopup.show();
        WKCommonModel.getInstance().payRedpackge(redpacketNo,payToken, new WKCommonModel.IpayRedpackgeListener() {
            @Override
            public void onResult(RedpackgePayResultEntity redpackgePayResultEntity) {
                Map<String, Object> result = redpackgePayResultEntity.getResult();
                if(loadingPopup!=null){
                    loadingPopup.dismiss();
                    //发送红包消息
                    WKRedPacketContent wkRedPacketContent = new WKRedPacketContent(redpacketNo,blessing);
                    WKMsgSetting setting = new WKMsgSetting();
                    WKIM.getInstance().getMsgManager().sendMessage(wkRedPacketContent, setting, channelId, channelType);
                    getActivity().finish();
                }
            }
        });
    }



}
