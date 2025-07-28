package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * @description: 服务_海外_今日持仓盈亏
 * @author: sunline
 * @date: 2019/7/4 10:52
 * @version: v1.0
 */

public class EF01118000Request extends GrmRequestVO implements Serializable {

    private static final long serialVersionUID = -4985391944433739714L;

    private String branchNo;

    /**
     * 客户帐号
     */
    private String clientId;

    /**
     * 资金帐号
     */
    private String fundAccount;

    /**
     * 证券市场
     */
    private String exchangeType;

    /**
     * 证券代码
     */
    private String stockCode;

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

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }
}
