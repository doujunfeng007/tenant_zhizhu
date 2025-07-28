package com.minigod.zero.trade.tiger.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author chen
 * @ClassName EstimateQuantityResp.java
 * @Description TODO
 * @createTime 2025年02月25日 10:54:00
 */
@Data
public class EstimateQuantityResp {

    /**
     * 现金数量
     */
    private BigDecimal tradeQty;

    /**
     * 现金数量精度
     */
    private BigDecimal tradeQtyScale;

    private BigDecimal financingQty;

    private BigDecimal financingQtyScale;

    private BigDecimal positionQty;

    private BigDecimal positionQtyScale;

    private BigDecimal tradableQty;

    /**
     * 可平持仓数量(OPT、MLEG平仓时有值)
     */
    private BigDecimal closeableQuantity;

    private BigDecimal avgCost;

    private BigDecimal tradeCashAmount;

    private BigDecimal financingCashAmount;

    private BigDecimal availableCash;

    private BigDecimal buyOnCashAmountByCurrency;

    private List<AssetsByCurrency> assetsByCurrency;

    private BigDecimal availableBuyingPower;

}
