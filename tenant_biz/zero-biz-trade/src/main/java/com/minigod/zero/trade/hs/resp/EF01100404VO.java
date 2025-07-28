package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 查询当前流水
 */
public class EF01100404VO extends FundJourRecord implements Serializable {
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
