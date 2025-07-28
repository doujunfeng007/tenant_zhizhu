package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 证券冻结
 */
public class EF01110010Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientId;
    private String fundAccount;
    private String exchangeType;
    private String stockAccount;
    private String stockCode;
    private String occurAmount;
    private String validDate;
    private String remark;
    private String localeRemark;
    private String overdraftForcedFlag;


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

    public String getStockAccount() {
        return stockAccount;
    }

    public void setStockAccount(String stockAccount) {
        this.stockAccount = stockAccount;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getOccurAmount() {
        return occurAmount;
    }

    public void setOccurAmount(String occurAmount) {
        this.occurAmount = occurAmount;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocaleRemark() {
        return localeRemark;
    }

    public void setLocaleRemark(String localeRemark) {
        this.localeRemark = localeRemark;
    }

    public String getOverdraftForcedFlag() {
        return overdraftForcedFlag;
    }

    public void setOverdraftForcedFlag(String overdraftForcedFlag) {
        this.overdraftForcedFlag = overdraftForcedFlag;
    }
}
