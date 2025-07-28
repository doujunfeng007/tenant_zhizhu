package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 现金存
 */
public class EF01110008Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientId;
    private String fundAccount;
    private String moneyType;
    private String occurBalance;
    private String bankNo;
    private String bankAccountCli;
    private String ledgerId;
    private String checkNo;
    private String remark;
    private String bankId;
    private String bankAccount;
    private String valueDate;
    private String businessFlagReal;
    private String checkValidDate;
    private String localeRemark;
    private String feeMoneyType;
    private String feeOccurBalance;
    private String theThird;
    private String exStatus;
    private String fetchBalanceJust;
    private String remarkFee;
    private String localeRemarkFee;
    private String payee;
    private String autoPrint;


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

    public String getOccurBalance() {
        return occurBalance;
    }

    public void setOccurBalance(String occurBalance) {
        this.occurBalance = occurBalance;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankAccountCli() {
        return bankAccountCli;
    }

    public void setBankAccountCli(String bankAccountCli) {
        this.bankAccountCli = bankAccountCli;
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getBusinessFlagReal() {
        return businessFlagReal;
    }

    public void setBusinessFlagReal(String businessFlagReal) {
        this.businessFlagReal = businessFlagReal;
    }

    public String getCheckValidDate() {
        return checkValidDate;
    }

    public void setCheckValidDate(String checkValidDate) {
        this.checkValidDate = checkValidDate;
    }

    public String getLocaleRemark() {
        return localeRemark;
    }

    public void setLocaleRemark(String localeRemark) {
        this.localeRemark = localeRemark;
    }

    public String getFeeMoneyType() {
        return feeMoneyType;
    }

    public void setFeeMoneyType(String feeMoneyType) {
        this.feeMoneyType = feeMoneyType;
    }

    public String getFeeOccurBalance() {
        return feeOccurBalance;
    }

    public void setFeeOccurBalance(String feeOccurBalance) {
        this.feeOccurBalance = feeOccurBalance;
    }

    public String getTheThird() {
        return theThird;
    }

    public void setTheThird(String theThird) {
        this.theThird = theThird;
    }

    public String getExStatus() {
        return exStatus;
    }

    public void setExStatus(String exStatus) {
        this.exStatus = exStatus;
    }

    public String getFetchBalanceJust() {
        return fetchBalanceJust;
    }

    public void setFetchBalanceJust(String fetchBalanceJust) {
        this.fetchBalanceJust = fetchBalanceJust;
    }

    public String getRemarkFee() {
        return remarkFee;
    }

    public void setRemarkFee(String remarkFee) {
        this.remarkFee = remarkFee;
    }

    public String getLocaleRemarkFee() {
        return localeRemarkFee;
    }

    public void setLocaleRemarkFee(String localeRemarkFee) {
        this.localeRemarkFee = localeRemarkFee;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getAutoPrint() {
        return autoPrint;
    }

    public void setAutoPrint(String autoPrint) {
        this.autoPrint = autoPrint;
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
