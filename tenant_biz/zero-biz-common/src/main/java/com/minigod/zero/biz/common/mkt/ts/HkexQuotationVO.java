package com.minigod.zero.biz.common.mkt.ts;

import com.minigod.zero.biz.common.enums.ETradeType;
import com.minigod.zero.core.tool.beans.QuotationVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class HkexQuotationVO extends QuotationVO {
    private static final long serialVersionUID = 7184505879193249169L;
    protected long b6;
    protected long b7;
    protected long b8;
    protected long b9;
    protected long b10;
    protected BigDecimal b6Price = BigDecimal.ZERO;
    protected BigDecimal b7Price = BigDecimal.ZERO;
    protected BigDecimal b8Price = BigDecimal.ZERO;
    protected BigDecimal b9Price = BigDecimal.ZERO;
    protected BigDecimal b10Price = BigDecimal.ZERO;
    protected long s6;
    protected long s7;
    protected long s8;
    protected long s9;
    protected long s10;
    protected BigDecimal s6Price=BigDecimal.ZERO;
    protected BigDecimal s7Price=BigDecimal.ZERO;
    protected BigDecimal s8Price=BigDecimal.ZERO;
    protected BigDecimal s9Price=BigDecimal.ZERO;
    protected BigDecimal s10Price=BigDecimal.ZERO;
    //十档 买入排队数量
    protected long b1Ordcount;
    protected long b2Ordcount;
    protected long b3Ordcount;
    protected long b4Ordcount;
    protected long b5Ordcount;
    protected long b6Ordcount;
    protected long b7Ordcount;
    protected long b8Ordcount;
    protected long b9Ordcount;
    protected long b10Ordcount;

    //十档 卖出排队数量
    protected long s1Ordcount;
    protected long s2Ordcount;
    protected long s3Ordcount;
    protected long s4Ordcount;
    protected long s5Ordcount;
    protected long s6Ordcount;
    protected long s7Ordcount;
    protected long s8Ordcount;
    protected long s9Ordcount;
    protected long s10Ordcount;
    /**
     * 港股的成交类型，
     */
    private String tradeType;

/*    // order imbalance
    String			szOrderImbDirection;	// IEP时，买卖盘差额方向 'N'=(买=卖) 'B'=(买>卖) 'S'=(买<卖)
    String			szOrderImbQuantity;	// IEP时，买卖盘差值的绝对值*/

    // Reference Price
    protected BigDecimal casReferPrice = BigDecimal.ZERO;    // 收盘竞价(CAS)时的参考价
    protected BigDecimal casReferLowerPrice = BigDecimal.ZERO;    // CAS允许的最低价
    protected BigDecimal casReferUpperPrice = BigDecimal.ZERO;    // CAS允许的最高价

    // VCM Trigger
    protected Date vcmStartTime;        // 熔断的开始时间 time_t 的%u值
    protected Date vcmEndTime;        // 熔断的结束时间 time_t 的%u值
    protected BigDecimal vcmReferPrice=BigDecimal.ZERO;    // VCM时的参考价
    protected BigDecimal vcmLowerPrice=BigDecimal.ZERO;        // VCM允许最低价
    protected BigDecimal vcmUpperPrice=BigDecimal.ZERO; ;        // VCM允许最高价

    // shortsell
    protected BigDecimal shortSellTraded=BigDecimal.ZERO;        //
    protected BigDecimal shortSellTurnover=BigDecimal.ZERO;    //

	private Boolean opriceSet = false; // 开盘价已设置


    public void resetQuote() {
        super.resetQuote();
        resetHkexQuote();
        this.setTradeType(ETradeType.NONE.toString());
    }

    public void resetHkexQuote() {
    	setOpriceSet(false);
        this.setB6(0);
        this.setB6Price(BigDecimal.ZERO);
        this.setB7(0);
        this.setB7Price(BigDecimal.ZERO);
        this.setB8(0);
        this.setB8Price(BigDecimal.ZERO);
        this.setB9(0);
        this.setB9Price(BigDecimal.ZERO);
        this.setB10(0);
        this.setB10Price(BigDecimal.ZERO);

        this.setS6(0);
        this.setS6Price(BigDecimal.ZERO);
        this.setS7(0);
        this.setS7Price(BigDecimal.ZERO);
        this.setS8(0);
        this.setS8Price(BigDecimal.ZERO);
        this.setS9(0);
        this.setS9Price(BigDecimal.ZERO);
        this.setS10(0);
        this.setS10Price(BigDecimal.ZERO);

        this.setB1Ordcount(0);
        this.setB2Ordcount(0);
        this.setB3Ordcount(0);
        this.setB4Ordcount(0);
        this.setB5Ordcount(0);
        this.setB6Ordcount(0);
        this.setB7Ordcount(0);
        this.setB8Ordcount(0);
        this.setB9Ordcount(0);
        this.setB10Ordcount(0);

        this.setS1Ordcount(0);
        this.setS2Ordcount(0);
        this.setS3Ordcount(0);
        this.setS4Ordcount(0);
        this.setS5Ordcount(0);
        this.setS6Ordcount(0);
        this.setS7Ordcount(0);
        this.setS8Ordcount(0);
        this.setS9Ordcount(0);
        this.setS10Ordcount(0);

        this.setCasReferLowerPrice(BigDecimal.ZERO);
        this.setCasReferLowerPrice(BigDecimal.ZERO);
        this.setCasReferUpperPrice(BigDecimal.ZERO);
        this.setVcmStartTime(null);
        this.setVcmEndTime(null);
        this.setVcmReferPrice(BigDecimal.ZERO);
        this.setVcmLowerPrice(BigDecimal.ZERO);
        this.setVcmUpperPrice(BigDecimal.ZERO);
        this.setShortSellTraded(BigDecimal.ZERO);
        this.setShortSellTurnover(BigDecimal.ZERO);
    }
}
