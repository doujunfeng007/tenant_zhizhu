package com.minigod.minigodinformation.enums;

/**
 * 公告审批流程枚举
 */
public enum ExchangeAnnFlow {
    defaultFlow(0, "未知"),
    TIJIAO(1, "提交"),
    SHENHE(2, "审核"),
    CHONGXIN_TIJIAO(3, "重新提交"),
    JIESHU(4, "结束");

    private final int code;
    private final String value;

    ExchangeAnnFlow(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static ExchangeAnnFlow fromCode(int code) {
        for (ExchangeAnnFlow flow : values()) {
            if (flow.getCode() == code) {
                return flow;
            }
        }
        return defaultFlow;
    }
}
