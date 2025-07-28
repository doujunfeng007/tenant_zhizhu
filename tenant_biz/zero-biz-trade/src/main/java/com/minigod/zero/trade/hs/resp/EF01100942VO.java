package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;
import java.util.List;

/**
 * 942 取签约基金基本信息
 */
public class EF01100942VO implements Serializable {
    private String initDate; //int 日期
    private String stockCode; //char[12] 基金代码
    private String exchangeType; //char[4] 基金市场
    private String stockName; //char[255] 基金名称
    private String stockNamegb; //char[255] 简体名称
    private String stockNamebig5; //char[255] 繁体名称
    private String lastPrice; //double[15,8] 最新价
    private String moneyType; //char[1] 币种ID
    private String moneyTypeName; //char[10] 币种名称
    private String isinCode; //char[20] 国际标识
    private String issuserNo; //char[20] 基金发行商ID
    private String issuserName; //char[255] 基金发行商中文名称
    private String brokerNo; //char[18] 基金公司ID
    private String brokerName; //char[200] 基金公司中文名称
    private String interestRateYear; //double[14,13] 七日年化利率
    private String redemptionLimitDay; //double[19,2] 公司赎回每日总上限额度
    private String subscriptionLimitClient; //double[19,2] 单个客户每日申购限额
    private String totalLimit; //double[19,2] 基金持仓总额度
    private String redemptionLimitClient; //double[19,2] 单个客户每日赎回上限
    private String refPriceDate; //int[8] 基金价格参考日期
    private List<EF01100942SubVO> subList;// 历史收益列表

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

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

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getMoneyTypeName() {
        return moneyTypeName;
    }

    public void setMoneyTypeName(String moneyTypeName) {
        this.moneyTypeName = moneyTypeName;
    }

    public String getIsinCode() {
        return isinCode;
    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    public String getIssuserNo() {
        return issuserNo;
    }

    public void setIssuserNo(String issuserNo) {
        this.issuserNo = issuserNo;
    }

    public String getIssuserName() {
        return issuserName;
    }

    public void setIssuserName(String issuserName) {
        this.issuserName = issuserName;
    }

    public String getBrokerNo() {
        return brokerNo;
    }

    public void setBrokerNo(String brokerNo) {
        this.brokerNo = brokerNo;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getInterestRateYear() {
        return interestRateYear;
    }

    public void setInterestRateYear(String interestRateYear) {
        this.interestRateYear = interestRateYear;
    }

    public String getRedemptionLimitDay() {
        return redemptionLimitDay;
    }

    public void setRedemptionLimitDay(String redemptionLimitDay) {
        this.redemptionLimitDay = redemptionLimitDay;
    }

    public String getSubscriptionLimitClient() {
        return subscriptionLimitClient;
    }

    public void setSubscriptionLimitClient(String subscriptionLimitClient) {
        this.subscriptionLimitClient = subscriptionLimitClient;
    }

    public String getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(String totalLimit) {
        this.totalLimit = totalLimit;
    }

    public String getRedemptionLimitClient() {
        return redemptionLimitClient;
    }

    public void setRedemptionLimitClient(String redemptionLimitClient) {
        this.redemptionLimitClient = redemptionLimitClient;
    }

    public String getRefPriceDate() {
        return refPriceDate;
    }

    public void setRefPriceDate(String refPriceDate) {
        this.refPriceDate = refPriceDate;
    }

    public List<EF01100942SubVO> getSubList() {
        return subList;
    }

    public void setSubList(List<EF01100942SubVO> subList) {
        this.subList = subList;
    }

    public String getStockNamegb() {
        return stockNamegb;
    }

    public void setStockNamegb(String stockNamegb) {
        this.stockNamegb = stockNamegb;
    }

    public String getStockNamebig5() {
        return stockNamebig5;
    }

    public void setStockNamebig5(String stockNamebig5) {
        this.stockNamebig5 = stockNamebig5;
    }
}
