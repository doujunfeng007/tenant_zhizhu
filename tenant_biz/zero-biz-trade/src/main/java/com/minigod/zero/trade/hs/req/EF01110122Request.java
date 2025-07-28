package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 恒生功能号：10122
 * 在指定客户资金帐户下开设证券交易帐户
 */
public class EF01110122Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String clientId; //char[18] 客户编号
    private String fundAccount; //int[10] 资金账号
    private String exchangeType; //char[4] 交易类别（对应字典项编号：1301 ）

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
}
