package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 获取客户限制
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 */
public class EF01110119VO implements Serializable {
    private static final long serialVersionUID = 1L;
    public String audit_serial_no;
    public String op_remark;
    public String fund_account;
    public String serial_no;
    public String business_flag;

    public String getAudit_serial_no() {
        return audit_serial_no;
    }

    public void setAudit_serial_no(String audit_serial_no) {
        this.audit_serial_no = audit_serial_no;
    }

    public String getOp_remark() {
        return op_remark;
    }

    public void setOp_remark(String op_remark) {
        this.op_remark = op_remark;
    }

    public String getFund_account() {
        return fund_account;
    }

    public void setFund_account(String fund_account) {
        this.fund_account = fund_account;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getBusiness_flag() {
        return business_flag;
    }

    public void setBusiness_flag(String business_flag) {
        this.business_flag = business_flag;
    }
}
