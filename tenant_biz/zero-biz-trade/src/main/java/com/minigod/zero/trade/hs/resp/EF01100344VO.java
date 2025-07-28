package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 344 查询OQ上下限价格
 */
public class EF01100344VO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String upPrice;
    private String downPrice;

    public String getUpPrice() {
        return upPrice;
    }

    public void setUpPrice(String upPrice) {
        this.upPrice = upPrice;
    }

    public String getDownPrice() {
        return downPrice;
    }

    public void setDownPrice(String downPrice) {
        this.downPrice = downPrice;
    }
}
