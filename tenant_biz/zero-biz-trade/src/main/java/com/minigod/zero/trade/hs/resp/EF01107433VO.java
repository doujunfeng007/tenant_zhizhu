package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 7433 基金委托撤单
 */
public class EF01107433VO implements Serializable {
    private String entrustNo; //int[10] 委托编号

    public String getEntrustNo() {
        return entrustNo;
    }

    public void setEntrustNo(String entrustNo) {
        this.entrustNo = entrustNo;
    }

}
