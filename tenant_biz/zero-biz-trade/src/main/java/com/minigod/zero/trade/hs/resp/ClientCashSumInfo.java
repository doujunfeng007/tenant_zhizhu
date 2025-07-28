package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline on 2016/5/7 16:08.
 * sunline
 */
public class ClientCashSumInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String moneyType;
    private String enableBalance;
    private String frozenBalance;
    private String marketValue;
    private String asset;
    private String transferBalance;
    private String currentBalance;
    private String incomeBalance;
    private String ipoBalance;
    private String incomeRatio;
    private String fetchBalance;
    private String gfFetchBalanceT;
    private String buyMoney;
    private String maxExposure;
    private String tradeDayBalance;

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getEnableBalance() {
        return enableBalance;
    }

    public void setEnableBalance(String enableBalance) {
        this.enableBalance = enableBalance;
    }

    public String getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(String frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getTransferBalance() {
        return transferBalance;
    }

    public void setTransferBalance(String transferBalance) {
        this.transferBalance = transferBalance;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {

        this.currentBalance = currentBalance;
    }

    public String getIncomeBalance() {
        return incomeBalance;
    }

    public void setIncomeBalance(String incomeBalance) {
        this.incomeBalance = incomeBalance;
    }

    public String getIpoBalance() {
        return ipoBalance;
    }

    public void setIpoBalance(String ipoBalance) {
        this.ipoBalance = ipoBalance;
    }

    public String getIncomeRatio() {
        return incomeRatio;
    }

    public void setIncomeRatio(String incomeRatio) {
        this.incomeRatio = incomeRatio;
    }

    public String getFetchBalance() {
        return fetchBalance;
    }

    public void setFetchBalance(String fetchBalance) {
        this.fetchBalance = fetchBalance;
    }

    public String getGfFetchBalanceT() {
        return gfFetchBalanceT;
    }

    public void setGfFetchBalanceT(String gfFetchBalanceT) {
        this.gfFetchBalanceT = gfFetchBalanceT;
    }

    public String getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(String buyMoney) {
        this.buyMoney = buyMoney;
    }

    public String getMaxExposure() {
        return maxExposure;
    }

    public void setMaxExposure(String maxExposure) {
        this.maxExposure = maxExposure;
    }

    public String getTradeDayBalance() {
        return tradeDayBalance;
    }

    public void setTradeDayBalance(String tradeDayBalance) {
        this.tradeDayBalance = tradeDayBalance;
    }
}
