package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 查成交历史
 */
public class EF01100814VO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private String exchRate;
    private String exchRateReverse;

    public String getExchRate() {
        return exchRate;
    }

    public void setExchRate(String exchRate) {
        this.exchRate = exchRate;
    }

    public String getExchRateReverse() {
        return exchRateReverse;
    }

    public void setExchRateReverse(String exchRateReverse) {
        this.exchRateReverse = exchRateReverse;
    }
}
