package com.minigod.zero.biz.common.enums;

/**
 *
 * 用户开户状态 类型
 * @author bpmx
 */
public enum UserOpenStatusEnum {
    DISABLED(0, "销户"),
    OPEN(1, "已开户"),
    UNOPEN(2, "未开户"),
    ;

    private final Integer code;
    private final String info;

    UserOpenStatusEnum(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
