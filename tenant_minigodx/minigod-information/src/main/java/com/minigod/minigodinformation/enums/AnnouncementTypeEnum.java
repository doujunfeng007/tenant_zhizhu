package com.minigod.minigodinformation.enums;

public enum AnnouncementTypeEnum {
    RATING_DOCUMENT(1, "评级文件"),
    PERFORMANCE_ANNOUNCEMENT(2, "业绩公告"),
    PERIODIC_REPORT(3, "定期报告"),
    DIVIDEND_ANNOUNCEMENT(4, "派息公告"),
    SIGNIFICANT_EVENT(5, "重大事项"),
    TRADING_HALT_ANNOUNCEMENT(6, "停牌公告");

    private final int code;
    private final String value;

    AnnouncementTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static AnnouncementTypeEnum getByCode(int code) {
        for (AnnouncementTypeEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的代码: " + code);
    }
}







