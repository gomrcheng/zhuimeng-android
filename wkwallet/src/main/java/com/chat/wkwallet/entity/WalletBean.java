package com.chat.wkwallet.entity;

import java.util.List;

public class WalletBean {
    public List<DataBean> list;
    public int count;

    public static class DataBean {
        public String fee;
        public String type; //out 支出 in 收入
        public String date;
        public int source;//
        public String source_name;
        public int id;
        public String channel_id;
        public String getFee() {
            return fee.contains("-")?fee:"+"+fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }
        /**
         * -1 // 客服充值
         * 0  // 充值
         * 1  // 单独转账-增加
         * 2  // 单独转账-扣除
         * 3  // 单独转账-返还
         * 4  // 单独红包-增加
         * 5  // 单独红包-扣除
         * 6  // 单独红包-返还
         * 7  // 群聊红包-增加
         * 8  // 群聊红包-扣除
         *  9 // 群聊红包-返还
         */
    }
}
