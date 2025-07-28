package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 7434 取前台当天基金委托
 */
public class EF01107434Request extends GrmRequestVO implements Serializable {
    private String clientId; //int 柜台交易账号
    private String fundAccount; //int 柜台资金账号
    private String password; //char[32] 交易密码
    private String exchangeType; //char[4] 交易类别(不传忽略该查询条件)

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

}
