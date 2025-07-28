package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 342 策略改单
 */
public class EF01100342VO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String entrustPrice2;// 委托价格2
    private String touchPrice2;// 触发价格2
    private String entrustPriceUp2;// 触发上限价格2
    private String entrustPriceDown2;// 触发下限价格2

    public String getEntrustPrice2() {
        return entrustPrice2;
    }

    public void setEntrustPrice2(String entrustPrice2) {
        this.entrustPrice2 = entrustPrice2;
    }

    public String getTouchPrice2() {
        return touchPrice2;
    }

    public void setTouchPrice2(String touchPrice2) {
        this.touchPrice2 = touchPrice2;
    }

    public String getEntrustPriceUp2() {
        return entrustPriceUp2;
    }

    public void setEntrustPriceUp2(String entrustPriceUp2) {
        this.entrustPriceUp2 = entrustPriceUp2;
    }

    public String getEntrustPriceDown2() {
        return entrustPriceDown2;
    }

    public void setEntrustPriceDown2(String entrustPriceDown2) {
        this.entrustPriceDown2 = entrustPriceDown2;
    }
}
