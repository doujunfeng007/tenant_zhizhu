package com.minigod.zero.biz.common.enums;

/**
 *
 * 机构状态 类型
 * @author bpmx
 */
public enum IsvStatusEnum {
    NORMAL(1, "生效"), STOP(2, "失效"),INIT(0,"筹备中");

    private final int code;
    private final String info;

    IsvStatusEnum(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
