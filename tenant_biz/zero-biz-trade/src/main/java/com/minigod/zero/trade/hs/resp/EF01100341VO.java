package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 341 策略下单
 */
public class EF01100341VO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String entrustNo;
    private String entrustType;

    public String getEntrustNo() {
        return entrustNo;
    }

    public void setEntrustNo(String entrustNo) {
        this.entrustNo = entrustNo;
    }

    public String getEntrustType() {
        return entrustType;
    }

    public void setEntrustType(String entrustType) {
        this.entrustType = entrustType;
    }
}
