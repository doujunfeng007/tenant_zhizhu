package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline on 2016/11/21 16:58.
 * sunline
 */
public class BankInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    protected String bankNo;
    protected String bankType;
    protected String bankName;
    protected String branchNo;
    protected String amOpen;
    protected String amClose;
    protected String pmOpen;
    protected String pmClose;
    protected String initDate;
    protected String bankStatus;
    protected String businessDate;
    protected String bankargFlag;

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getAmOpen() {
        return amOpen;
    }

    public void setAmOpen(String amOpen) {
        this.amOpen = amOpen;
    }

    public String getAmClose() {
        return amClose;
    }

    public void setAmClose(String amClose) {
        this.amClose = amClose;
    }

    public String getPmOpen() {
        return pmOpen;
    }

    public void setPmOpen(String pmOpen) {
        this.pmOpen = pmOpen;
    }

    public String getPmClose() {
        return pmClose;
    }

    public void setPmClose(String pmClose) {
        this.pmClose = pmClose;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(String bankStatus) {
        this.bankStatus = bankStatus;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getBankargFlag() {
        return bankargFlag;
    }

    public void setBankargFlag(String bankargFlag) {
        this.bankargFlag = bankargFlag;
    }
}
