package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

public class EF01110184VO implements Serializable{

    private String initDate;
    /**
     * char[12] 证券代码
     */
    private String stockCode;
    /**
     * char[4] 交易类别
     */
    private String exchangeType;
    /**
     * char[18] 客户编号
     */
    private String clientId;
    /**
     * int 资产账号
     */
    private String fundAccount;
    /**
     * char[200] 客户姓名
     */
    private String clientName;
    /**
     * int 申购数量
     */
    private String quantityApply;
    /**
     * double 总申购金额
     */
    private String applyAmount;
    /**
     * double 申购资金比例
     */
    private String depositRate;
    /**
     * double 客户申购资金
     */
    private String depositAmount;
    /**
     * double IPO利率
     */
    private String ipoIntrate;
    /**
     * int 中签数量
     */
    private String quantityAllotted;
    /**
     * double 手续费
     */
    private String handlingFee;
    /**
     * double 最终申购金额
     */
    private String finalAmount;
    /**
     * double 融资利息
     */
    private String financingAmount;
    /**
     * char 申购类型
     */
    private String type;
    /**
     * char 冻结标志
     */
    private String frozenFlag;
    /**
     * char 委托方式
     */
    private String entrustWay;
    /**
     * char 申购批核状态(O,Original|A,Accept|R,Reject)
     */
    private String statusCheck;
    /**
     * char[255] 申购状态描述
     */
    private String statusDetail;
    /**
     * char 默认处理方式
     */
    private String process;
    /**
     * char[255] 备注
     */
    private String remark;
    /**
     * char 提供融资的银行
     */
    private String financingBy;
    /**
     * int 申购日期
     */
    private String currDate;
    /**
     * int 申购时间
     */
    private String currTime;

    private String stockName;

    private String stockNamegb;

    private String stockNamebig5;

    private String stockFullname;

    /**
     * ‘0’：booking新股申購；‘1’：checking審核通過或拒絕；‘2’：applying已扣款；‘3’：Refunded已返款；‘4’：Over新股入賬。
     */
    private String status;

    private String assetId;

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getQuantityApply() {
        return quantityApply;
    }

    public void setQuantityApply(String quantityApply) {
        this.quantityApply = quantityApply;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getDepositRate() {
        return depositRate;
    }

    public void setDepositRate(String depositRate) {
        this.depositRate = depositRate;
    }

    public String getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getIpoIntrate() {
        return ipoIntrate;
    }

    public void setIpoIntrate(String ipoIntrate) {
        this.ipoIntrate = ipoIntrate;
    }

    public String getQuantityAllotted() {
        return quantityAllotted;
    }

    public void setQuantityAllotted(String quantityAllotted) {
        this.quantityAllotted = quantityAllotted;
    }

    public String getHandlingFee() {
        return handlingFee;
    }

    public void setHandlingFee(String handlingFee) {
        this.handlingFee = handlingFee;
    }

    public String getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(String finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getFinancingAmount() {
        return financingAmount;
    }

    public void setFinancingAmount(String financingAmount) {
        this.financingAmount = financingAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrozenFlag() {
        return frozenFlag;
    }

    public void setFrozenFlag(String frozenFlag) {
        this.frozenFlag = frozenFlag;
    }

    public String getEntrustWay() {
        return entrustWay;
    }

    public void setEntrustWay(String entrustWay) {
        this.entrustWay = entrustWay;
    }

    public String getStatusCheck() {
        return statusCheck;
    }

    public void setStatusCheck(String statusCheck) {
        this.statusCheck = statusCheck;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFinancingBy() {
        return financingBy;
    }

    public void setFinancingBy(String financingBy) {
        this.financingBy = financingBy;
    }

    public String getCurrDate() {
        return currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    public String getCurrTime() {
        return currTime;
    }

    public void setCurrTime(String currTime) {
        this.currTime = currTime;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockNamegb() {
        return stockNamegb;
    }

    public void setStockNamegb(String stockNamegb) {
        this.stockNamegb = stockNamegb;
    }

    public String getStockNamebig5() {
        return stockNamebig5;
    }

    public void setStockNamebig5(String stockNamebig5) {
        this.stockNamebig5 = stockNamebig5;
    }

    public String getStockFullname() {
        return stockFullname;
    }

    public void setStockFullname(String stockFullname) {
        this.stockFullname = stockFullname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
}
