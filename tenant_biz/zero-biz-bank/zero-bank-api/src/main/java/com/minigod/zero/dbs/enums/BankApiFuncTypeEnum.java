package com.minigod.zero.dbs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 银行业务功能Api
 */
@Getter
@AllArgsConstructor
public enum BankApiFuncTypeEnum {

    UN_KNOW(0, "UN_KNOW", "未知"),
    /**
     * 服务类
     */
    BLE(1, "BLE", "BLE 查询账户"),
    EDDA_SETUP(2, "EDDA_SETUP", "EDDA 授权"),
    EDDA_ENQUIRY(3, "EDDA_ENQUIRY", "EDDA 授权查询"),
    /**
     * DBS出金
     */
    FPS_GPC(4, "FPS_GPC", "FPS 授权扣款(实时)"),
    FPS_GPP(5, "FPS_GPP", "FPS 转账银行账号(实时)"),
    FPS_PPP(6, "FPS_PPP", "FPS 转账FpsId/Emal/Phone(实时)"),
    ACT(7, "ACT", "同行转账(延时)"),
    CHATS(8, "CHATS", "跨行转账(延时)"),
    TT(12, "TT", "TT 海外转账(延时)"),
    /**
     * 报表
     */
    CAMT53(9, "CAMT53", "日报表"),
    CAMT52(10, "CAMT52", "小时报表"),
    VAC(11, "VAC", "VAC 子账户操作"),

    FPSID_ENQUIRY(13, "FPSID_ENQUIRY", "FPSID enquiry"),

    RFD(14, "RFD", "RFD"),
    TSE(15, "TSE", "TSE 交易状态查询"),

    /**
     * 通知类
     */
    ICN(100, "ICN", "FPS银行通知"),
    IDN(101, "IDN", "网银/ACT银行通知"),
    EDDA_ACK2(102, "EDDA_ACK2", "EDDA 授权通知"),
    REMIT_ACK(103, "REMIT_ACK", "ACT/CHATS 转账通知(包含 ACK2 和 ACK3)"),

    ;

    private int index;
    private String func;
    private String desc;


    public static BankApiFuncTypeEnum getTypeEnum(String func) {
        return Arrays.stream(BankApiFuncTypeEnum.values()).filter(typeEnum -> typeEnum.getFunc().equals(func)).findFirst().orElse(null);
    }
}
