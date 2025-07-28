package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 股票存
 */
public class EF01110013VO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String serialNo;
    private String auditSerialNo;
    private String currentAmount;
    private String enableAmount;
    private String opRemark;
    private String fundBusinessFlag;
    private String transmitAmount;

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

    public String getTransmitAmount() {
        return transmitAmount;
    }

    public void setTransmitAmount(String transmitAmount) {
        this.transmitAmount = transmitAmount;
    }

    public String getFundBusinessFlag() {
        return fundBusinessFlag;
    }

    public void setFundBusinessFlag(String fundBusinessFlag) {
        this.fundBusinessFlag = fundBusinessFlag;
    }
}
