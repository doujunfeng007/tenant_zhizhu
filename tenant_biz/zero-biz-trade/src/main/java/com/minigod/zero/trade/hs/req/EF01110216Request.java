package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 恒生功能号：10216
 * 客户基金协议设置
 */
public class EF01110216Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientId; //char[18] 客户编号
    private String fundAccount; //int[10] 资金账号
    private String moneyType; //char[1] 币种
    private String exchangeType; //char[4] 基金市场
    private String stockCode; //char[8] 基金代码
//    private String idleStartBalance; //double[19,2] 闲置启动金额
    private String reserveBalance; //double[19,2] 预留金额
    private String enableStatus; //char[1] 0-未激活,1-激活(签约成功=1)
    private String signFlag; //char[1] 0-签订,1-修改
    private String validDate; //int[8] 基金有效到期日期(不填默认20991231)

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

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
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

    public String getReserveBalance() {
        return reserveBalance;
    }

    public void setReserveBalance(String reserveBalance) {
        this.reserveBalance = reserveBalance;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }

    public String getSignFlag() {
        return signFlag;
    }

    public void setSignFlag(String signFlag) {
        this.signFlag = signFlag;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }
}
