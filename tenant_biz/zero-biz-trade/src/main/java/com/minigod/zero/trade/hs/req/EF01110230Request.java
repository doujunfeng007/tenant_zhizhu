package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

public class EF01110230Request extends GrmRequestVO implements Serializable {

    private String stockCode;

    private String exchangeType;

    private String fundAccount;

    private String quantityApply;

    private String depositRate;

    private String type;

    private String actionIn;

    private String clientId;

    private String manualHandlingFee;

    private String handlingFeeAble;

    private String manualIpoIntrate;

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

    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getQuantityApply() {
        return quantityApply;
    }

    public void setQuantityApply(String quantityApply) {
        this.quantityApply = quantityApply;
    }

    public String getDepositRate() {
        return depositRate;
    }

    public void setDepositRate(String depositRate) {
        this.depositRate = depositRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActionIn() {
        return actionIn;
    }

    public void setActionIn(String actionIn) {
        this.actionIn = actionIn;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getManualHandlingFee() {
        return manualHandlingFee;
    }

    public void setManualHandlingFee(String manualHandlingFee) {
        this.manualHandlingFee = manualHandlingFee;
    }

    public String getHandlingFeeAble() {
        return handlingFeeAble;
    }

    public void setHandlingFeeAble(String handlingFeeAble) {
        this.handlingFeeAble = handlingFeeAble;
    }

    public String getManualIpoIntrate() {
        return manualIpoIntrate;
    }

    public void setManualIpoIntrate(String manualIpoIntrate) {
        this.manualIpoIntrate = manualIpoIntrate;
    }
}
