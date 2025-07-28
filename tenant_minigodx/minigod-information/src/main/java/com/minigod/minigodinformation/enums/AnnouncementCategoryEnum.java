package com.minigod.minigodinformation.enums;

public enum AnnouncementCategoryEnum {
    EXCHANGE(1, "交易所"),
    MARKET_PARTICIPANT(2, "市场参与者");

    private final int code;
    private final String description;

    AnnouncementCategoryEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static AnnouncementCategoryEnum getValue(int code) {
        for (AnnouncementCategoryEnum participant : values()) {
            if (participant.getCode() == code) {
                return participant;
            }
        }
        throw new IllegalArgumentException("无效的代码: " + code);
    }
}




