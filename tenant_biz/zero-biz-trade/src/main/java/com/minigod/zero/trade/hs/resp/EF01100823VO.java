package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 查客户银行帐号
 */
public class EF01100823VO  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bankNo;
    private String fundAccount;
    private String moneyType;
    private String bankAccount;
    private String clientAccount;
    private String bkaccountStatus;
    private String holderName;
    private String foreignFlag;
    private String idKind;
    private String idNo;
    private String bkaccountKind;

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(String clientAccount) {
        this.clientAccount = clientAccount;
    }

    public String getBkaccountStatus() {
        return bkaccountStatus;
    }

    public void setBkaccountStatus(String bkaccountStatus) {
        this.bkaccountStatus = bkaccountStatus;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getForeignFlag() {
        return foreignFlag;
    }

    public void setForeignFlag(String foreignFlag) {
        this.foreignFlag = foreignFlag;
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

    public String getBkaccountKind() {
        return bkaccountKind;
    }

    public void setBkaccountKind(String bkaccountKind) {
        this.bkaccountKind = bkaccountKind;
    }
}
