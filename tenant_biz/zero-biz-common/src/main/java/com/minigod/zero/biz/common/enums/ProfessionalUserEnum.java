package com.minigod.zero.biz.common.enums;

/**
 *
 * 通用状态 类型
 * @author bpmx
 */
public enum ProfessionalUserEnum {
    YES(1, "是"), NO(0, "否");
    private final int code;
    private final String info;

    ProfessionalUserEnum(int code, String info) {
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
