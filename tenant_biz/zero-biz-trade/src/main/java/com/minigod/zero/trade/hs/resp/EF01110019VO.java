package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * AE、操作员退出
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 */
public class EF01110019VO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String auditSerialNo;
    private String opRemark;
    private String serialNo;

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
