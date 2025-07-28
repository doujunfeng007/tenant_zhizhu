package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 7435 取前台历史基金委托
 */
public class EF01107435VO implements Serializable {
    private String tradeDate; //int 委托日期
    private String initDate; //int 发生日期,当前交易日
    private String entrustNo; //int 委托编号
    private String currDate; //int 实际日期
    private String currTime; //int 发生时间
    private String fundAccount; //int 资产账号
    private String clientId; //char[18] 客户编号
    private String exchangeType; //char[4] 交易类别
//    private String stockAccount; //char[15] 证券账号
    private String seatNo; //char[8] 席位编号
    private String stockCode; //char[12] 证券代码
    private String stockType; //char[4] 证券类别
    private String entrustBs; //char 买卖方向
    private String entrustProp; //char 委托属性
    private String entrustAmount; //double[19,6] 委托数量
    private String entrustPrice; //double[15,8] 委托价格
    private String entrustStatus; //char 委托状态
//    private String positionStr; //char[32] 定位串
    private String stockNamegb; //char[255] 股票简体名称
    private String stockNamebig5; //char[255] 股票繁体名称
    private String moneyType; //char 币种类别
    private String entrustTime; //int 委托时间
    private String stockName; //char[255] 股票名称
    private String fareBalance; //double[19,2] 费用
    private String fareMode; //char 费用模式
    private String entrustDate; //int[8] 委托日期
  /*  private String orderfunddealref; //char[64] 基金公司的委托编号
    private String fundOrderno; //char[64] 上家的委托编号
    private String fundFare; //double[19,2] 上家的佣金
    private String confirmToken; //char[64] 确认的令牌
    private String confirmStatus; //char 确认状态
    private String errorMsg; //char[255] 错误信息*/
    private String enableAmount; //double[19,6] 可用数量（赎回时用于传给上家）
    private String originalAmount; //double[19,6] 原委托数量

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getEntrustNo() {
        return entrustNo;
    }

    public void setEntrustNo(String entrustNo) {
        this.entrustNo = entrustNo;
    }

    public String getCurrDate() {
        return currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    public String getCurrTime() {
        return currTime;
    }

    public void setCurrTime(String currTime) {
        this.currTime = currTime;
    }

    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
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

    public String getEntrustProp() {
        return entrustProp;
    }

    public void setEntrustProp(String entrustProp) {
        this.entrustProp = entrustProp;
    }

    public String getEntrustAmount() {
        return entrustAmount;
    }

    public void setEntrustAmount(String entrustAmount) {
        this.entrustAmount = entrustAmount;
    }

    public String getEntrustPrice() {
        return entrustPrice;
    }

    public void setEntrustPrice(String entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    public String getEntrustStatus() {
        return entrustStatus;
    }

    public void setEntrustStatus(String entrustStatus) {
        this.entrustStatus = entrustStatus;
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

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getEntrustTime() {
        return entrustTime;
    }

    public void setEntrustTime(String entrustTime) {
        this.entrustTime = entrustTime;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getFareBalance() {
        return fareBalance;
    }

    public void setFareBalance(String fareBalance) {
        this.fareBalance = fareBalance;
    }

    public String getFareMode() {
        return fareMode;
    }

    public void setFareMode(String fareMode) {
        this.fareMode = fareMode;
    }

    public String getEntrustDate() {
        return entrustDate;
    }

    public void setEntrustDate(String entrustDate) {
        this.entrustDate = entrustDate;
    }

    public String getEnableAmount() {
        return enableAmount;
    }

    public void setEnableAmount(String enableAmount) {
        this.enableAmount = enableAmount;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }
}
