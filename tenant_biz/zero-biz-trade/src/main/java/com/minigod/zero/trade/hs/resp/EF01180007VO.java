package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 代销基金历史收益率
 */
public class EF01180007VO implements Serializable {
    private String stkCode;//基金代码
    private BigDecimal interestRateYear;//七日年化利率
    private BigDecimal wanFenProfit;//万份收益
    private Integer refPriceDate;//参考价格日期20210303
    private String moneyType;//币种 0人民币 1美圆 2港币

    public String getStkCode() {
        return stkCode;
    }

    public void setStkCode(String stkCode) {
        this.stkCode = stkCode;
    }

    public BigDecimal getInterestRateYear() {
        return interestRateYear;
    }

    public void setInterestRateYear(BigDecimal interestRateYear) {
        this.interestRateYear = interestRateYear;
    }

    public BigDecimal getWanFenProfit() {
        return wanFenProfit;
    }

    public void setWanFenProfit(BigDecimal wanFenProfit) {
        this.wanFenProfit = wanFenProfit;
    }

    public Integer getRefPriceDate() {
        return refPriceDate;
    }

    public void setRefPriceDate(Integer refPriceDate) {
        this.refPriceDate = refPriceDate;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }
}
