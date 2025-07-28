package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 服务_海外_查询客户费用
 */
public class EF01110136VO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String branchNo;
    private String clientId;
    private String fundAccount;
    /**
     *费用类型字典
     */
    private String fareDict;
    /**
     *付费方式
     */
    private String feeType;
    /**
     *付费数值
     */
    private String feeCount;
    /**
     *费用类别
     */
    private String fareType;
    /**
     *费用描述
     */
    private String fareStr;
    /**
     *交易类别
     */
    private String exchangeType;
    /**
     *证券类别
     */
    private String stockType;
    /**
     *证券代码
     */
    private String stockCode;
    /**
     *买卖方向
     */
    private String entrustBs;
    /**
     *委托方式
     */
    private String entrustWay;
    /**
     *币种类别
     */
    private String moneyType;
    /**
     *最低费用
     */
    private String minFare;
    /**
     *最高费用
     */
    private String maxFare;
    /**
     *截位方式
     */
    private String precisionFlag;
    /**
     *精度
     */
    private String precision;

    private String feeCountFix;

    private String beginDate;

    private String endDate;

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
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

    public String getFeeCount() {
        return feeCount;
    }

    public void setFeeCount(String feeCount) {
        this.feeCount = feeCount;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public String getFareStr() {
        return fareStr;
    }

    public void setFareStr(String fareStr) {
        this.fareStr = fareStr;
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

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
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
}
