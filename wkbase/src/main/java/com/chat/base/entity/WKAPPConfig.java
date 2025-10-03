package com.chat.base.entity;

import com.alibaba.fastjson.JSONObject;

public class WKAPPConfig {
    public int version;
    public String web_url;
    public int phone_search_off;
    public int shortno_edit_off;
    public int revoke_second;
    public int register_invite_on;
    public int send_welcome_message_on;
    public int invite_system_account_join_group_on;
    public int register_user_must_complete_info_on;
    public int can_modify_api_url;
    public JSONObject webrtc;
    public JSONObject agora;
    public String rtc_provider;

    public String wallet_enabled;
    public String redpacket_enabled;
    public String transfer_enabled;

    public JSONObject message_send_config;
//    public String file_upload_max_size_mb;//文件最大上传
//    public String image_upload_max_size_mb;//图片最大上传
//    public String recall_time_limit_minutes;//最大撤回时间 -1无限制
//    public String text_max_length;//最大发送文本长度
//    public String voice_max_duration_seconds;//最大发送语音时间

}

