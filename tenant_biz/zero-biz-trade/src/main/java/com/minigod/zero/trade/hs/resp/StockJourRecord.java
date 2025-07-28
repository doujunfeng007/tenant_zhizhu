package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline on 2016/6/30 19:53.
 * sunline
 */
public class StockJourRecord implements Serializable{
    private static final long serialVersionUID = 1L;
    protected String serialNo;
    protected String businessDate;
    protected String businessFlag;
    protected String businessName;
    protected String stockCode;
    protected String assetId;
    protected String stockName;
    protected String exchangeType;
    protected String occurAmount;
    protected String postAmount;
    protected String moneyType;
    protected String localeRemark;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getOccurAmount() {
        return occurAmount;
    }

    public void setOccurAmount(String occurAmount) {
        this.occurAmount = occurAmount;
    }

    public String getPostAmount() {
        return postAmount;
    }

    public void setPostAmount(String postAmount) {
        this.postAmount = postAmount;
    }

    public String getLocaleRemark() {
        return localeRemark;
    }

    public void setLocaleRemark(String localeRemark) {
        this.localeRemark = localeRemark;
    }

    protected String positionStr;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBusinessFlag() {
        return businessFlag;
    }

    public void setBusinessFlag(String businessFlag) {
        this.businessFlag = businessFlag;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getPositionStr() {
        return positionStr;
    }

    public void setPositionStr(String positionStr) {
        this.positionStr = positionStr;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }
    public String getStockName() {
        return stockName;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
