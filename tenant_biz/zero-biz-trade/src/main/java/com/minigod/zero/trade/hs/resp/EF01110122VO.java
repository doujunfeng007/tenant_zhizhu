package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 恒生功能号：10122
 * 在指定客户资金帐户下开设证券交易帐户
 */
public class EF01110122VO implements Serializable {
    private String auditSerialNo; //int[10] 复核流水号
    private String opRemark; //char[2000] 操作备注
    private String serialNo; //int[10] 流水号

    public String getAuditSerialNo() {
        return auditSerialNo;
    }

    public void setAuditSerialNo(String auditSerialNo) {
        this.auditSerialNo = auditSerialNo;
    }

    public String getOpRemark() {
        return opRemark;
    }

    public void setOpRemark(String opRemark) {
        this.opRemark = opRemark;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
