package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 恒生功能号：10217
 * 客户基金协议查询
 */
public class EF01110217Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String clientId; //char[18] 客户编号
    private String fundAccount; //int[10] 资金账号

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
}
