package com.minigod.zero.biz.common.enums;

/**
 * @description: TODO
 * @author: sunline
 * @date: 2018/8/3 14:58
 * @version: v1.0
 */

public enum IpoZqTypeEnum {

    IPO_ZQ(1 , "中签"),
    IPO_NOT_ZQ(2 , "未中签");

    private IpoZqTypeEnum(int code , String message) {
        this.code = code;
        this.mesasge = message;
    }

    private int code;
    private String mesasge;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMesasge() {
        return mesasge;
    }

    public void setMesasge(String mesasge) {
        this.mesasge = mesasge;
    }
}
