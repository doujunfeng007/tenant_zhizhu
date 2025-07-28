package com.minigod.minigodinformation.enums;

public enum AnnouncementStatusEnum {
    SAVED(1, "已保存"),
    PENDING_REVIEW(2, "待审核"),
    REJECTED(3, "已驳回"),
    REVIEWED(4, "已审核"),
    PUBLISHED(5, "已发布");

    private final int code;
    private final String value;

    AnnouncementStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static AnnouncementStatusEnum getByCode(int code) {
        for (AnnouncementStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的代码: " + code);
    }
}








