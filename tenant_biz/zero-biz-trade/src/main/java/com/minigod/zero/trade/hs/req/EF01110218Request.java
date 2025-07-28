package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 恒生功能号：10218
 * 客户基金协议取消
 */
public class EF01110218Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String clientId; //char[18] 客户编号
    private String fundAccount; //int[10] 资金账号
    private String moneyType; //char[1] 币种
    private String exchangeType; //char[4] 基金市场
    private String stockCode; //char[8] 基金代码

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
