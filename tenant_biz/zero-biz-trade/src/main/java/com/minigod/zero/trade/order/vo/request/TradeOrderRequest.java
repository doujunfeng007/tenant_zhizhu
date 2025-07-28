package com.minigod.zero.trade.order.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;

public class TradeOrderRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    private String exchangeType;//市场类型

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }
}
