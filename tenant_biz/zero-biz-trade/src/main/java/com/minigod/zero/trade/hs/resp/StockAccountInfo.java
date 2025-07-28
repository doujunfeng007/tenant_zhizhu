package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline on 2016/5/7 15:57.
 * sunline
 */
public class StockAccountInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String exchangeType; //char[4] 交易类别
    private String exchangeName; //char[16] 交易名称
    private String stockAccount; //char[15] 证券账号
    private String holderStatus; //char 股东状态
    private String holderRights; //char[16] 股东权限
    private String mainFlag; //char 主副标志
    private String register; //char 是否指定
    private String enableAmount; //double 可用数量
    private String seatNo; //char[8] 席位编号

    public String getExchangeType() {
        return exchangeType;
    }

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
