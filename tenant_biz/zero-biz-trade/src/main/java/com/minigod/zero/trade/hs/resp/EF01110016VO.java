package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 查证券帐号
 */
public class EF01110016VO extends StockHoldingVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String exchangeType;
    private String exchangeName;
    private String stockAccount;
    private String holderStatus;
    private String holderRights;
    private String mainFlag;
    private String register;
    private String enableAmount;
    private String seatNo;

    @Override
    public String getExchangeType() {
        return exchangeType;
    }

    @Override
    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getStockAccount() {
        return stockAccount;
    }

    public void setStockAccount(String stockAccount) {
        this.stockAccount = stockAccount;
    }

    public String getHolderStatus() {
        return holderStatus;
    }

    public void setHolderStatus(String holderStatus) {
        this.holderStatus = holderStatus;
    }

    public String getHolderRights() {
        return holderRights;
    }

    public void setHolderRights(String holderRights) {
        this.holderRights = holderRights;
    }

    public String getMainFlag() {
        return mainFlag;
    }

    public void setMainFlag(String mainFlag) {
        this.mainFlag = mainFlag;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }


    public String getEnableAmount() {
        return enableAmount;
    }


    public void setEnableAmount(String enableAmount) {
        this.enableAmount = enableAmount;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }
}
