package com.chat.wktransfer.entity;

public class PayOrderDetailBean {

    private String order_no;
    private String sub_order_no;
    private String type;
    private String fee;
    private Integer source;
    private String source_name;
    private String operation_date;
    private String pay_way;
    private String save_way;
    private ExtraBean extra;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getSub_order_no() {
        return sub_order_no;
    }

    public void setSub_order_no(String sub_order_no) {
        this.sub_order_no = sub_order_no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFee() {
        return fee.contains("-")?fee:"+"+fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getOperation_date() {
        return operation_date;
    }

    public void setOperation_date(String operation_date) {
        this.operation_date = operation_date;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getSave_way() {
        return save_way;
    }

    public void setSave_way(String save_way) {
        this.save_way = save_way;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public static class ExtraBean {
        private Integer sender;
        private String sender_uid;
        private String sender_name;
        private String sender_uname;
        private Integer to_channel_type;
        private String to_uid;
        private String to_uname;
        private String transfer_end_date;
        private String transfer_finish_date;
        private Integer transfer_id;
        private String transfer_remark;
        private String transfer_start_date;
        private Integer transfer_status;
        private String transfer_status_name;
        private Integer red_packet_count;
        private String red_packet_end_date;
        private Integer red_packet_fee;
        private String red_packet_finish_date;
        private Integer red_packet_id;
        private String red_packet_name;
        private String red_packet_start_date;
        private Integer red_packet_status;
        private String red_packet_status_name;

        public String getSender_uname() {
            return sender_uname;
        }

        public void setSender_uname(String sender_uname) {
            this.sender_uname = sender_uname;
        }

        public Integer getSender() {
            return sender;
        }

        public void setSender(Integer sender) {
            this.sender = sender;
        }

        public String getSender_uid() {
            return sender_uid;
        }

        public void setSender_uid(String sender_uid) {
            this.sender_uid = sender_uid;
        }

        public String getSender_name() {
            return sender_name;
        }

        public void setSender_name(String sender_name) {
            this.sender_name = sender_name;
        }

        public Integer getTo_channel_type() {
            return to_channel_type;
        }

        public void setTo_channel_type(Integer to_channel_type) {
            this.to_channel_type = to_channel_type;
        }

        public String getTo_uid() {
            return to_uid;
        }

        public void setTo_uid(String to_uid) {
            this.to_uid = to_uid;
        }

        public String getTo_uname() {
            return to_uname;
        }

        public void setTo_uname(String to_uname) {
            this.to_uname = to_uname;
        }

        public String getTransfer_end_date() {
            return transfer_end_date;
        }

        public void setTransfer_end_date(String transfer_end_date) {
            this.transfer_end_date = transfer_end_date;
        }

        public String getTransfer_finish_date() {
            return transfer_finish_date;
        }

        public void setTransfer_finish_date(String transfer_finish_date) {
            this.transfer_finish_date = transfer_finish_date;
        }

        public Integer getTransfer_id() {
            return transfer_id;
        }

        public void setTransfer_id(Integer transfer_id) {
            this.transfer_id = transfer_id;
        }

        public String getTransfer_remark() {
            return transfer_remark;
        }

        public void setTransfer_remark(String transfer_remark) {
            this.transfer_remark = transfer_remark;
        }

        public String getTransfer_start_date() {
            return transfer_start_date;
        }

        public void setTransfer_start_date(String transfer_start_date) {
            this.transfer_start_date = transfer_start_date;
        }

        public Integer getTransfer_status() {
            return transfer_status;
        }

        public void setTransfer_status(Integer transfer_status) {
            this.transfer_status = transfer_status;
        }

        public String getTransfer_status_name() {
            return transfer_status_name;
        }

        public void setTransfer_status_name(String transfer_status_name) {
            this.transfer_status_name = transfer_status_name;
        }

        public Integer getRed_packet_count() {
            return red_packet_count;
        }

        public void setRed_packet_count(Integer red_packet_count) {
            this.red_packet_count = red_packet_count;
        }

        public String getRed_packet_end_date() {
            return red_packet_end_date;
        }

        public void setRed_packet_end_date(String red_packet_end_date) {
            this.red_packet_end_date = red_packet_end_date;
        }

        public Integer getRed_packet_fee() {
            return red_packet_fee;
        }

        public void setRed_packet_fee(Integer red_packet_fee) {
            this.red_packet_fee = red_packet_fee;
        }

        public String getRed_packet_finish_date() {
            return red_packet_finish_date;
        }

        public void setRed_packet_finish_date(String red_packet_finish_date) {
            this.red_packet_finish_date = red_packet_finish_date;
        }

        public Integer getRed_packet_id() {
            return red_packet_id;
        }

        public void setRed_packet_id(Integer red_packet_id) {
            this.red_packet_id = red_packet_id;
        }

        public String getRed_packet_name() {
            return red_packet_name;
        }

        public void setRed_packet_name(String red_packet_name) {
            this.red_packet_name = red_packet_name;
        }

        public String getRed_packet_start_date() {
            return red_packet_start_date;
        }

        public void setRed_packet_start_date(String red_packet_start_date) {
            this.red_packet_start_date = red_packet_start_date;
        }

        public Integer getRed_packet_status() {
            return red_packet_status;
        }

        public void setRed_packet_status(Integer red_packet_status) {
            this.red_packet_status = red_packet_status;
        }

        public String getRed_packet_status_name() {
            return red_packet_status_name;
        }

        public void setRed_packet_status_name(String red_packet_status_name) {
            this.red_packet_status_name = red_packet_status_name;
        }
    }
}
