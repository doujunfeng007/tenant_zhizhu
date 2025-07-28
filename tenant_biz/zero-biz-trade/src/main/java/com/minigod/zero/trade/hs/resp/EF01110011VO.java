package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 股票解冻
 */
public class EF01110011VO   implements Serializable {
    private static final long serialVersionUID = 1L;
    private String serialNo;
    private String auditSerialNo;
    private String currentAmount;
    private String enableAmount;
    private String opRemark;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getAuditSerialNo() {
        return auditSerialNo;
    }

    public void setAuditSerialNo(String auditSerialNo) {
        this.auditSerialNo = auditSerialNo;
    }

    public String getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(String currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getEnableAmount() {
        return enableAmount;
    }

    public void setEnableAmount(String enableAmount) {
        this.enableAmount = enableAmount;
    }

    public String getOpRemark() {
        return opRemark;
    }

    public void setOpRemark(String opRemark) {
        this.opRemark = opRemark;
    }
}
