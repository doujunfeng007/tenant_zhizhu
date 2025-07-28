package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;
import java.util.List;

/**
 * 查询历史成交总汇
 * 10149 服务_海外_取历史成交总汇
 */
public class EF01110149VO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String assetId;
    private String exchangeType;
    private String crossing;// 交叉盘
    private String entrustBs;
    private String stockCode;
    private String moneyType;
    private String clientId;
    private String fundAccount;
    private String tradeDate;
    private String dateBack;
    private String totalAmount;
    private String averagePrice;
    private String glossBalance;
    private String profitFlag;
    private String clearBalance;
    private String shortSell;
    private String remark;
    private String disposalSale;
    private String businessTime;
    private String stockType;
    private String stockName;
    private String cancelFlag;// 交易取消状态，是否已取消
    private String bargainStatus;// 买卖状态
    private String entrustWay;// 委托方式
    private String fare0;
    private String fare1;
    private String fare2;
    private String fare3;
    private String fare4;
    private String fare5;
    private String fare6;
    private String fare7;
    private String fare8;
    private String farex;

    private List<EF01110149Fare> fareList;

    public List<EF01110149Fare>  getFareList() {
        return fareList;
    }

    public void setFareList(List<EF01110149Fare>  fareList) {
        this.fareList = fareList;
    }

    private String sequenceNo;
    private String allocationId;
    private String platformFee;


    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getCrossing() {
        return crossing;
    }

    public void setCrossing(String crossing) {
        this.crossing = crossing;
    }

    public String getEntrustBs() {
        return entrustBs;
    }

    public void setEntrustBs(String entrustBs) {
        this.entrustBs = entrustBs;
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

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getDateBack() {
        return dateBack;
    }

    public void setDateBack(String dateBack) {
        this.dateBack = dateBack;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getGlossBalance() {
        return glossBalance;
    }

    public void setGlossBalance(String glossBalance) {
        this.glossBalance = glossBalance;
    }

    public String getProfitFlag() {
        return profitFlag;
    }

    public void setProfitFlag(String profitFlag) {
        this.profitFlag = profitFlag;
    }

    public String getClearBalance() {
        return clearBalance;
    }

    public void setClearBalance(String clearBalance) {
        this.clearBalance = clearBalance;
    }

    public String getShortSell() {
        return shortSell;
    }

    public void setShortSell(String shortSell) {
        this.shortSell = shortSell;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDisposalSale() {
        return disposalSale;
    }

    public void setDisposalSale(String disposalSale) {
        this.disposalSale = disposalSale;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(String cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public String getBargainStatus() {
        return bargainStatus;
    }

    public void setBargainStatus(String bargainStatus) {
        this.bargainStatus = bargainStatus;
    }

    public String getEntrustWay() {
        return entrustWay;
    }

    public void setEntrustWay(String entrustWay) {
        this.entrustWay = entrustWay;
    }

    public String getFare0() {
        return fare0;
    }

    public void setFare0(String fare0) {
        this.fare0 = fare0;
    }

    public String getFare1() {
        return fare1;
    }

    public void setFare1(String fare1) {
        this.fare1 = fare1;
    }

    public String getFare2() {
        return fare2;
    }

    public void setFare2(String fare2) {
        this.fare2 = fare2;
    }

    public String getFare3() {
        return fare3;
    }

    public void setFare3(String fare3) {
        this.fare3 = fare3;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(String allocationId) {
        this.allocationId = allocationId;
    }

    public String getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(String platformFee) {
        this.platformFee = platformFee;
    }

    public String getFarex() {
        return farex;
    }

    public void setFarex(String farex) {
        this.farex = farex;
    }

    public String getFare6() {
        return fare6;
    }

    public void setFare6(String fare6) {
        this.fare6 = fare6;
    }

    public String getFare8() {
        return fare8;
    }

    public void setFare8(String fare8) {
        this.fare8 = fare8;
    }

    public String getFare4() {
        return fare4;
    }

    public void setFare4(String fare4) {
        this.fare4 = fare4;
    }

    public String getFare5() {
        return fare5;
    }

    public void setFare5(String fare5) {
        this.fare5 = fare5;
    }

    public String getFare7() {
        return fare7;
    }

    public void setFare7(String fare7) {
        this.fare7 = fare7;
    }

}
