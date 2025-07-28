package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 7437 取基金基本信息
 */
public class EF01107437Request extends GrmRequestVO implements Serializable {
    //char[4] 交易类别
    private String exchangeType;
    //char[12] 证券代码
    private String stockCode;

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
}
