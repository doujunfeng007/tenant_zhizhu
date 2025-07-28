package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 949 新增基金委托
 */
public class EF01100949VO implements Serializable {
    private String errorInfo; //char[255] 错误信息
    private String errorId; //int[10] 错误流水号
    private String oinitDate; //int 交易日期
    private String osequenceNo; //char[8] 合约号

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getOinitDate() {
        return oinitDate;
    }

    public void setOinitDate(String oinitDate) {
        this.oinitDate = oinitDate;
    }

    public String getOsequenceNo() {
        return osequenceNo;
    }

    public void setOsequenceNo(String osequenceNo) {
        this.osequenceNo = osequenceNo;
    }
}
