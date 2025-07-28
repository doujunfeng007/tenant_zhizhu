package com.minigod.zero.bpm.vo;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: sunline
 * @date: 2018/7/4 10:00
 * @version: v1.0
 */
@Data
public class OpenAccountBankVerityInfo {

    private String clientName; // 客户姓名
    private String phoneNumber; // 手机号码
    private String idNo; // 身份证
    private String bankCard; // 银行卡号
    private int verifyCount; // 验证次数
    private int verifyStatus; // 验证状态
    private Date createTime; // 验证日期

}
