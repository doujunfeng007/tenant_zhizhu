package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

public class EF01100412VO extends FundJourRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    //证券帐号
    private String stockName;
    //证券代码
    protected String stockCode;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
