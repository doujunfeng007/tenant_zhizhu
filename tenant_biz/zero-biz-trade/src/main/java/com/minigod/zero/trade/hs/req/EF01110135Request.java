package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 服务_海外_管理客户费用
 */
public class EF01110135Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String branchNo;
    private String actionIn;
    private String clientId;
    private String fundAccount;
    private String fareDict;
    private String feeType;
    private String fareType;
    private String exchangeType;
    private String stockType;
    private String entrustBs;
    private String entrustWay;
    private String moneyType;
    private String minFare;
    private String maxFare;
    private String precisionFlag;
    private String precision;
    private String feeCountFix;
    //费用描述
    private String fareStr;
    private String feeCount;
    private String stockCode;
    private String beginDate;
    private String endDate;
    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }



    public String getActionIn() {
        return actionIn;
    }

    public void setActionIn(String actionIn) {
        this.actionIn = actionIn;
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

    public String getFareDict() {
        return fareDict;
    }

    public void setFareDict(String fareDict) {
        this.fareDict = fareDict;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getEntrustBs() {
        return entrustBs;
    }

    public void setEntrustBs(String entrustBs) {
        this.entrustBs = entrustBs;
    }

    public String getEntrustWay() {
        return entrustWay;
    }

    public void setEntrustWay(String entrustWay) {
        this.entrustWay = entrustWay;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public String getMinFare() {
        return minFare;
    }

    public void setMinFare(String minFare) {
        this.minFare = minFare;
    }

    public String getMaxFare() {
        return maxFare;
    }

    public void setMaxFare(String maxFare) {
        this.maxFare = maxFare;
    }

    public String getPrecisionFlag() {
        return precisionFlag;
    }

    public void setPrecisionFlag(String precisionFlag) {
        this.precisionFlag = precisionFlag;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getFeeCountFix() {
        return feeCountFix;
    }

    public void setFeeCountFix(String feeCountFix) {
        this.feeCountFix = feeCountFix;
    }

    public String getFareStr() {
        return fareStr;
    }

    public void setFareStr(String fareStr) {
        this.fareStr = fareStr;
    }

    public String getFeeCount() {
        return feeCount;
    }

    public void setFeeCount(String feeCount) {
        this.feeCount = feeCount;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }
}
