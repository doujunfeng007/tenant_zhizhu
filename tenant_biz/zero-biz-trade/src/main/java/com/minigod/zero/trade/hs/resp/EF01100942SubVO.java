package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 * 942 取签约基金历史收益
 */
public class EF01100942SubVO implements Serializable {
    private String initDate; //int 日期
    private String refPriceDate; //int[8] 基金价格参考日期
    private String lastPrice; //double[15,8] 最新价
    private String interestRateYear; //double[14,13] 七日年化利率
    private String wanFenProfit; // 万份收益

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getRefPriceDate() {
        return refPriceDate;
    }

    public void setRefPriceDate(String refPriceDate) {
        this.refPriceDate = refPriceDate;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getInterestRateYear() {
        return interestRateYear;
    }

    public void setInterestRateYear(String interestRateYear) {
        this.interestRateYear = interestRateYear;
    }

    public String getWanFenProfit() {
        return wanFenProfit;
    }

    public void setWanFenProfit(String wanFenProfit) {
        this.wanFenProfit = wanFenProfit;
    }
}
