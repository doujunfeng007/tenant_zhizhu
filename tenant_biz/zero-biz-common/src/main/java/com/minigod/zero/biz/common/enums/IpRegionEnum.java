package com.minigod.zero.biz.common.enums;

/**
 *
 * 通用状态 类型
 * @author bpmx
 */
public enum IpRegionEnum {
    HK("HK", "香港"), TWN("TWN", "台湾"),MO("MO","澳门");

    private final String code;
    private final String info;

    IpRegionEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
