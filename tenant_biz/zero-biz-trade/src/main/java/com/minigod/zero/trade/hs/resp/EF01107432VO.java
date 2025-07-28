package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 7432 基金委托确认
 */
public class EF01107432VO implements Serializable {
    private String errorNo; //int 错误序号
    private String errorInfo; //char[255] 错误提示
    private String entrustNo; //int 委托编号

    public String getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getEntrustNo() {
        return entrustNo;
    }

    public void setEntrustNo(String entrustNo) {
        this.entrustNo = entrustNo;
    }
}
