package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 恒生功能号：948
 * 获取昨日基金收益
 */
public class EF01100948VO implements Serializable {
    private String initDate; //int[10] 发生日期
    private String clientId; //char[18] 客户号
    private String fundAccount; //int[10] 柜台资金账号
    private String moneyType; //char[1] 币种
    private String exchangeType; //char[4] 市场
    private String stockCode; //int[10] 证券代码
    private String netAmount; //double 计算收益净持仓
    private String closePrice; //double 收市价
    private String income; //double 收益

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
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

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }
}
