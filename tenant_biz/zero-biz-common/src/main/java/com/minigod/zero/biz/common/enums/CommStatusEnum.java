package com.minigod.zero.biz.common.enums;

/**
 *
 * 通用状态 类型
 * @author bpmx
 */
public enum CommStatusEnum {
    NORMAL(1, "生效"), STOP(2, "失效"),INIT(0,"筹备中");

    private final Integer code;
    private final String info;

    CommStatusEnum(Integer code, String info) {
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
