package com.minigod.zero.dbs.enums;

/**
 * 消息响应码定义
 *
 */
public enum CamtReportTypeEnum {

    CAMT052(52, "CAMT052"),
    CAMT053(53, "CAMT053");

    private int code;
    private String message;

    CamtReportTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static CamtReportTypeEnum getTypeEnum(int code) {
        for (CamtReportTypeEnum typeEnum : CamtReportTypeEnum.values()) {
            if (typeEnum.getCode() == code) {
                return typeEnum;
            }
        }
        return null;
    }
}
