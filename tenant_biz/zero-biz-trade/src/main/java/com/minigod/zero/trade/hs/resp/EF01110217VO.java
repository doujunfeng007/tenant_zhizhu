package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 恒生功能号：10217
 * 客户基金协议查询
 */
public class EF01110217VO implements Serializable {
    private String clientId; //char[18] 客户编号
    private String fundAccount; //int[10] 资金账号
    private String moneyType; //char[1] 币种
    private String moneyTypeName; //char[10] 币种名称
    private String idleStartBalance; //double[19,2] 闲置启动金额
    private String reserveBalance; //double[19,2] 预留金额
    private String enableStatus; //char[1] 0-未激活,1-激活(签约成功=1), 2-取消签约
    private String stockCode; //char[12] 基金代码
    private String stockName; //char[255] 基金名称
    private String exchangeType; //char[4] 基金市场
    private String signDate; //int[8] 基金签约日期
    private String validDate; //int[8] 基金有效到期日期
    /*private String stockFullname; //char[255] 英文名称
    private String stockNamebig5; //char[255] 繁体名称
    private String lastPrice; //double[15,8] 最新价
    private String isinCode; //char[20] 国际标识
    private String issuserNo; //char[20] 基金发行商
    private String issuserName; //char[255] 基金发行商名称
    private String brokerNo; //char[18] 基金公司
    private String brokerName; //char[200] 基金公司名称*/

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

    public String getMoneyTypeName() {
        return moneyTypeName;
    }

    public void setMoneyTypeName(String moneyTypeName) {
        this.moneyTypeName = moneyTypeName;
    }

    public String getIdleStartBalance() {
        return idleStartBalance;
    }

    public void setIdleStartBalance(String idleStartBalance) {
        this.idleStartBalance = idleStartBalance;
    }

    public String getReserveBalance() {
        return reserveBalance;
    }

    public void setReserveBalance(String reserveBalance) {
        this.reserveBalance = reserveBalance;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
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

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

}
