package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 恒生功能号：949
 * 新增基金委托
 */
public class EF01100949Request extends GrmRequestVO implements Serializable {
    private String clientId; //char[18] 客户号
    private String fundAccount; //int[10] 资金账户
    private String exchangeType; //char[4] 市场 (m)
    private String stockCode; //char[12] 基金代码
    private String entrustBs; //char[1] 买卖方向 (1:申购,2:赎回)
    private String businessAmount; //double[19,2] 委托数量
    private String businessBalance; //double[19,2] 委托金额
    private String tradeDate; //int 交易日期 (yyyymmdd)
    private String settDate; //int 交收日期 (yyyymmdd)
    private String orderType; //char[1] 交易类型 (0:Direct,1:Indirect)
    private String feeType; //char[1] 计费方法 (1:正常,2:费用包含在价格中)
//    private String fare0; //double[19,2] 佣金
//    private String fare1; //double[19,2] 印花税
    private String fare2; //double[19,2] 交易费
//    private String fare3; //double[19,2] 交易征费
//    private String farex; //double[19,2] 结算费
    private String fxMoneyType; //char[1] 实际交收币种 (0:CNY,1:USD,2:HKD)
    private String fxRate; //double[9,8] 外币交收利率 (1.0)
    private String clearBalance; //double[19,2] 实际交收金额
    private String settMethod; //char[1] 交收方式 (0:自动交收,1:手工交收)
    private String brokerNo; //char[18] 对手券商 (清算管理-券商管理-券商信息管理)
    private String custodian; //char[8] 托管商编号 (清算管理-券商管理-券商交收指令管理)
    private String custodianId; //int 子仓编号 (清算管理-券商管理-券商交收指令管理)
//    private String withdrawlFlag; //char[1] 赎回是否取款(Y:取款,N:不取款)


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

    public String getEntrustBs() {
        return entrustBs;
    }

    public void setEntrustBs(String entrustBs) {
        this.entrustBs = entrustBs;
    }

    public String getBusinessAmount() {
        return businessAmount;
    }

    public void setBusinessAmount(String businessAmount) {
        this.businessAmount = businessAmount;
    }

    public String getBusinessBalance() {
        return businessBalance;
    }

    public void setBusinessBalance(String businessBalance) {
        this.businessBalance = businessBalance;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getSettDate() {
        return settDate;
    }

    public void setSettDate(String settDate) {
        this.settDate = settDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFare2() {
        return fare2;
    }

    public void setFare2(String fare2) {
        this.fare2 = fare2;
    }

    public String getFxMoneyType() {
        return fxMoneyType;
    }

    public void setFxMoneyType(String fxMoneyType) {
        this.fxMoneyType = fxMoneyType;
    }

    public String getFxRate() {
        return fxRate;
    }

    public void setFxRate(String fxRate) {
        this.fxRate = fxRate;
    }

    public String getClearBalance() {
        return clearBalance;
    }

    public void setClearBalance(String clearBalance) {
        this.clearBalance = clearBalance;
    }

    public String getSettMethod() {
        return settMethod;
    }

    public void setSettMethod(String settMethod) {
        this.settMethod = settMethod;
    }

    public String getBrokerNo() {
        return brokerNo;
    }

    public void setBrokerNo(String brokerNo) {
        this.brokerNo = brokerNo;
    }

    public String getCustodian() {
        return custodian;
    }

    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    public String getCustodianId() {
        return custodianId;
    }

    public void setCustodianId(String custodianId) {
        this.custodianId = custodianId;
    }
}
