package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 查询AE的信息
 */
public class EF01110001VO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String brokerAccount;
    private String branchNo;
    private String brokerName;
    private String brokerKind;
    private String idKind;
    private String idNo;
    private String brokerSex;
    private String address;
    private String phonecode;
    private String mobileTel;
    private String eMail;
    private String brokerStatus;

    public String getBrokerAccount() {
        return brokerAccount;
    }

    public void setBrokerAccount(String brokerAccount) {
        this.brokerAccount = brokerAccount;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBrokerKind() {
        return brokerKind;
    }

    public void setBrokerKind(String brokerKind) {
        this.brokerKind = brokerKind;
    }

    public String getIdKind() {
        return idKind;
    }

    public void setIdKind(String idKind) {
        this.idKind = idKind;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getBrokerSex() {
        return brokerSex;
    }

    public void setBrokerSex(String brokerSex) {
        this.brokerSex = brokerSex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }

    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getBrokerStatus() {
        return brokerStatus;
    }

    public void setBrokerStatus(String brokerStatus) {
        this.brokerStatus = brokerStatus;
    }
}
