package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 345 查询策略单
 */
public class EF01100345VO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String conditionType;// 策略类型（数据字典641016 0-OQ订单;1-市价到价单;2-限价到价单）
    private String exchangeType;
    private String entrustNo;//
    private String initDate;// 交易日期
    private String touchPriceUp;// 触发上限价格
    private String touchPriceDown;// 触发下限价格
    private String touchPrice;// 触发价格

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getEntrustNo() {
        return entrustNo;
    }

    public void setEntrustNo(String entrustNo) {
        this.entrustNo = entrustNo;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getTouchPriceUp() {
        return touchPriceUp;
    }

    public void setTouchPriceUp(String touchPriceUp) {
        this.touchPriceUp = touchPriceUp;
    }

    public String getTouchPriceDown() {
        return touchPriceDown;
    }

    public void setTouchPriceDown(String touchPriceDown) {
        this.touchPriceDown = touchPriceDown;
    }

    public String getTouchPrice() {
        return touchPrice;
    }

    public void setTouchPrice(String touchPrice) {
        this.touchPrice = touchPrice;
    }
}
