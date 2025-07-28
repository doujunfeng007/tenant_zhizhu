package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * @description: 服务_海外_今日持仓盈亏
 * @author: sunline
 * @date: 2019/7/4 11:04
 * @version: v1.0
 */

public class EF01118000VO implements Serializable {

    private static final long serialVersionUID = -2534352099798065283L;

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

    /**
     * 币种类别
     */
    private String moneyType;

    /**
     * 当前持仓数量
     */
    private Double currentAmount;

    /**
     * 最新价格
     */
    private Double assetPrice;

    /**
     * 昨日期末持仓
     */
    private Double beginAmount;

    /**
     * 昨日收市价
     */
    private Double closePrice;

    /**
     * 当日累计买入金额
     */
    private Double realBuyBalance;

    /**
     * 当日累计卖出金额
     */
    private Double realSellBalace;

    /**
     * 当日累计存入股份数量（SI/证券存）
     */
    private Double depositAmount;

    /**
     * 当日累计取出股份数量（SI/证券取）
     */
    private Double withdrawAmount;

    /**
     * 当日累计存入市值（SI/证券存）
     */
    private Double depositValue;

    /**
     * 当日累计取出市值（SI/证券取）
     */
    private Double withdrawValue;

    /**
     * 中签数量
     */
    private Double quantityAllotted;

    /**
     * 中签价格
     */
    private Double finalPrice;

    /**
     * 中签金额（含费用）
     */
    private Double reduceBalance;

    /**
     * 公司行动所有费用
     */
    private Double totalRightsFare;

    /**
     * 债券利息
     */
    private Double accruedInterest;


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

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Double getAssetPrice() {
        return assetPrice;
    }

    public void setAssetPrice(Double assetPrice) {
        this.assetPrice = assetPrice;
    }

    public Double getBeginAmount() {
        return beginAmount;
    }

    public void setBeginAmount(Double beginAmount) {
        this.beginAmount = beginAmount;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getRealBuyBalance() {
        return realBuyBalance;
    }

    public void setRealBuyBalance(Double realBuyBalance) {
        this.realBuyBalance = realBuyBalance;
    }

    public Double getRealSellBalace() {
        return realSellBalace;
    }

    public void setRealSellBalace(Double realSellBalace) {
        this.realSellBalace = realSellBalace;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Double getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(Double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public Double getDepositValue() {
        return depositValue;
    }

    public void setDepositValue(Double depositValue) {
        this.depositValue = depositValue;
    }

    public Double getWithdrawValue() {
        return withdrawValue;
    }

    public void setWithdrawValue(Double withdrawValue) {
        this.withdrawValue = withdrawValue;
    }

    public Double getQuantityAllotted() {
        return quantityAllotted;
    }

    public void setQuantityAllotted(Double quantityAllotted) {
        this.quantityAllotted = quantityAllotted;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Double getReduceBalance() {
        return reduceBalance;
    }

    public void setReduceBalance(Double reduceBalance) {
        this.reduceBalance = reduceBalance;
    }

    public Double getTotalRightsFare() {
        return totalRightsFare;
    }

    public void setTotalRightsFare(Double totalRightsFare) {
        this.totalRightsFare = totalRightsFare;
    }

    public Double getAccruedInterest() {
        return accruedInterest;
    }

    public void setAccruedInterest(Double accruedInterest) {
        this.accruedInterest = accruedInterest;
    }
}
