package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 转帐查询
 */
public class EF01100830VO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String initDate;
    private String serialNo;
    private String bankNo;
    private String moneyType;
    private String currDate;
    private String sourceFlag;
    private String bktransStatus;
    private String transType;
    private String bankAccount;
    private String clientAccount;
    private String occurBalance;
    private String currTime;
    private String reportTime;
    private String positionStr;
    private String exchangeType;
    private String businType;
    private String debitCuy;
    private String creditCuy;
    private String tradeSign;
    private String remark;
    private String entrustWay;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getCurrDate() {
        return currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    public String getSourceFlag() {
        return sourceFlag;
    }

    public void setSourceFlag(String sourceFlag) {
        this.sourceFlag = sourceFlag;
    }

    public String getBktransStatus() {
        return bktransStatus;
    }

    public void setBktransStatus(String bktransStatus) {
        this.bktransStatus = bktransStatus;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
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

    public String getOccurBalance() {
        return occurBalance;
    }

    public void setOccurBalance(String occurBalance) {
        this.occurBalance = occurBalance;
    }

    public String getCurrTime() {
        return currTime;
    }

    public void setCurrTime(String currrTime) {
        this.currTime = currrTime;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getPositionStr() {
        return positionStr;
    }

    public void setPositionStr(String positionStr) {
        this.positionStr = positionStr;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getBusinType() {
        return businType;
    }

    public void setBusinType(String businType) {
        this.businType = businType;
    }

    public String getDebitCuy() {
        return debitCuy;
    }

    public void setDebitCuy(String debitCuy) {
        this.debitCuy = debitCuy;
    }

    public String getCreditCuy() {
        return creditCuy;
    }

    public void setCreditCuy(String creditCuy) {
        this.creditCuy = creditCuy;
    }

    public String getTradeSign() {
        return tradeSign;
    }

    public void setTradeSign(String tradeSign) {
        this.tradeSign = tradeSign;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEntrustWay() {
        return entrustWay;
    }

    public void setEntrustWay(String entrustWay) {
        this.entrustWay = entrustWay;
    }
}
