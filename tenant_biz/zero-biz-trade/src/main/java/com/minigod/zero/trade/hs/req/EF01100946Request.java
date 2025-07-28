package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 恒生功能号：946
 * 获取签约基金历史收益
 */
public class EF01100946Request extends GrmRequestVO implements Serializable {
    private String opStation; //HsStation 站点/电话
    private String beginDate; //int[8] 开始日期
    private String endDate; //int[8] 结束日期
    private String clientId; //char[18] 客户号
    private String fundAccount; //int[10] 柜台资金账号
    private String moneyType; //char[1] 币种 必填（所有币种传 !）
    private String exchangeType; //char[4] 市场 必填（所有市场传 !）
    private String stockCode; //char[12] 证券代码 必填（所有股票代码传 !）

    public String getOpStation() {
        return opStation;
    }

    public void setOpStation(String opStation) {
        this.opStation = opStation;
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
}
