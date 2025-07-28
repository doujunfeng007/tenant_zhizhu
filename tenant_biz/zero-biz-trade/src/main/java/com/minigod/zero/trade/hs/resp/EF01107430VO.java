package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 7430 获取最大可赎回数量
 */
public class EF01107430VO implements Serializable {
    private String errorNo; //int 错误序号
    private String errorInfo; //char[255] 错误提示
    private String enableAmount; //double[19,6] 可用数量

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

    public String getEnableAmount() {
        return enableAmount;
    }

    public void setEnableAmount(String enableAmount) {
        this.enableAmount = enableAmount;
    }
}
