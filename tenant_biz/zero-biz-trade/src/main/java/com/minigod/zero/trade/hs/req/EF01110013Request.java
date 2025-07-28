package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 证券取
 */
public class EF01110013Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String opPassword;
    private String clientId;
    private String fundAccount;
    private String password;
    private String exchangeType;
    private String stockAccount;
    private String stockCode;
    private String stockPrice;
    private String occurAmount;
    private String moneyType;
    private String occurBalance;
    private String remark;
    private String localId;
    private String bankNo;
    private String stockMoneyType;
    private String custodianId;
    private String custodian;
    private String valueDate;
    private String localeRemark;
    private String remarkFund;
    private String remarkFundLocale;
    private String bankId;
    private String bankAccount;
    private String occurAmountP1;
    private String occurAmountP2;
    private String occurAmountP3;
    private String fareMoneyType;
    private String overdraftForcedFlag;


    public String getOpPassword() {
        return opPassword;
    }

    public void setOpPassword(String opPassword) {
        this.opPassword = opPassword;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getStockAccount() {
        return stockAccount;
    }

    public void setStockAccount(String stockAccount) {
        this.stockAccount = stockAccount;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(String stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getOccurAmount() {
        return occurAmount;
    }

    public void setOccurAmount(String occurAmount) {
        this.occurAmount = occurAmount;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getStockMoneyType() {
        return stockMoneyType;
    }

    public void setStockMoneyType(String stockMoneyType) {
        this.stockMoneyType = stockMoneyType;
    }

    public String getCustodianId() {
        return custodianId;
    }

    public void setCustodianId(String custodianId) {
        this.custodianId = custodianId;
    }

    public String getCustodian() {
        return custodian;
    }

    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getLocaleRemark() {
        return localeRemark;
    }

    public void setLocaleRemark(String localeRemark) {
        this.localeRemark = localeRemark;
    }

    public String getRemarkFund() {
        return remarkFund;
    }

    public void setRemarkFund(String remarkFund) {
        this.remarkFund = remarkFund;
    }

    public String getRemarkFundLocale() {
        return remarkFundLocale;
    }

    public void setRemarkFundLocale(String remarkFundLocale) {
        this.remarkFundLocale = remarkFundLocale;
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

    public String getOccurAmountP1() {
        return occurAmountP1;
    }

    public void setOccurAmountP1(String occurAmountP1) {
        this.occurAmountP1 = occurAmountP1;
    }

    public String getOccurAmountP2() {
        return occurAmountP2;
    }

    public void setOccurAmountP2(String occurAmountP2) {
        this.occurAmountP2 = occurAmountP2;
    }

    public String getOccurAmountP3() {
        return occurAmountP3;
    }

    public void setOccurAmountP3(String occurAmountP3) {
        this.occurAmountP3 = occurAmountP3;
    }

    public String getFareMoneyType() {
        return fareMoneyType;
    }

    public void setFareMoneyType(String fareMoneyType) {
        this.fareMoneyType = fareMoneyType;
    }

    public String getOverdraftForcedFlag() {
        return overdraftForcedFlag;
    }

    public void setOverdraftForcedFlag(String overdraftForcedFlag) {
        this.overdraftForcedFlag = overdraftForcedFlag;
    }
}
