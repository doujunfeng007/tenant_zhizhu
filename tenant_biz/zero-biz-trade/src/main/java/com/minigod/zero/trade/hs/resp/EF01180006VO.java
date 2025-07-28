package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 基金市场基本信息
 * @author sunline
 */
public class EF01180006VO implements Serializable {
    /**char[12] 证券代码*/
    private String stockCode;
    /**char[4] 交易类别*/
    private String exchangeType;
    /**char 币种类别*/
    private String moneyType;
    /**char[255] 代码名称*/
    private String stockName;
    /**基金类型*/
    private String fundType;
    /**历史收益率 乘以100*/
    private BigDecimal hisReturnRate;
    /**手续费*/
    private BigDecimal handlingFee;

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

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public BigDecimal getHisReturnRate() {
        return hisReturnRate;
    }

    public void setHisReturnRate(BigDecimal hisReturnRate) {
        this.hisReturnRate = hisReturnRate;
    }

    public BigDecimal getHandlingFee() {
        return handlingFee;
    }

    public void setHandlingFee(BigDecimal handlingFee) {
        this.handlingFee = handlingFee;
    }
}
