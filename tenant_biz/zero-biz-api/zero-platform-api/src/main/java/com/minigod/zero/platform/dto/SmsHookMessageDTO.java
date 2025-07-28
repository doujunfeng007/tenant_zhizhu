package com.minigod.zero.platform.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class SmsHookMessageDTO {
    private Date user_receive_time; // 用户接收时间
    private String nationcode; // 国家代码
    private String mobile; // 手机号码
    private String report_status; // 报告状态
    private String errmsg; // 错误信息
    private String description; // 描述
    private String sid; // SID

    // Getter 和 Setter 方法


}
