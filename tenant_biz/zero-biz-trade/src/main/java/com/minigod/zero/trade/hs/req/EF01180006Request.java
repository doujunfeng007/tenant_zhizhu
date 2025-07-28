package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 查询基金市场基本信息
 */
public class EF01180006Request extends GrmRequestVO implements Serializable {
    /**char[4] 交易类别*/
    private String exchangeType;

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

}
