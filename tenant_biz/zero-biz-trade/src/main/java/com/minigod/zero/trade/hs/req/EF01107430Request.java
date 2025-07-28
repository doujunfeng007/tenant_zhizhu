package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 恒生功能号：7430
 * 获取最大可赎回数量
 */
public class EF01107430Request extends GrmRequestVO implements Serializable {
    private String clientId; //int 柜台交易账号
    private String fundAccount; //int 柜台资金账号
    private String password; //char[32] 交易密码
    private String exchangeType; //char[4] 交易类别
    private String stockCode; //char[12] 证券代码

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
