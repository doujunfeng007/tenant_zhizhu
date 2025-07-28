package com.minigod.zero.trade.tiger.resp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName AssetsByCurrency.java
 * @Description TODO
 * @createTime 2025年02月25日 11:05:00
 */
@Data
public class AssetsByCurrency {

    private String currency;

    /**
     * 基于基础币种的汇率
     */
    private Number forexRateToBase;

    /**
     * 现金余额
     */
    private BigDecimal cashBalance;

    /**
     * 已结算现金, 指的是在金融交易中，资金或现金清算的金额。
     */
    private BigDecimal settledCash;

    /**
     * 锁定现金，指的是在投资者账户中，虽然属于可用现金的一部分，但由于某些原因，暂时不能被提取、转移或用于其他投资操作的资金。
     */
    private BigDecimal lockedCash;

    /**
     * 可用现金 availableCash = cashBalance - lockedCash
     */
    private BigDecimal availableCash;

    /**
     * 可用的已结算现金 availableSettledCash=settledCash - lockedCash
     */
    private BigDecimal availableSettledCash;

    /**
     * 可用超额权益(剩余可用的具有借贷价值资产), 指的是投资者账户中，扣除维持保证金要求后，剩余的超额权益部分，且这些超额权益可以用于新的投资或应对其他市场波动的资金。
     */
    private BigDecimal availableExcessEquity;
}
