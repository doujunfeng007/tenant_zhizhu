package com.minigod.zero.bpmn.module.pi.enums;

public enum ApplicationType {
    SUBMIT(1, "提交/办理"),
    REVOKE(2, "撤销");

    private final int code;
    private final String description;

    ApplicationType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ApplicationType fromCode(int code) {
        for (ApplicationType type : ApplicationType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的申请类型代码: " + code);
    }
}
