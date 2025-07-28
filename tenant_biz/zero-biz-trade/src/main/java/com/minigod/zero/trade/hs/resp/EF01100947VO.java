package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 恒生功能号：947
 * 获取签约基金收益汇总
 */
public class EF01100947VO implements Serializable {
    private String totalIncome; //double 总收益

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }
}
