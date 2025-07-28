package com.minigod.zero.trade.tiger.resp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName TigerPositionResp.java
 * @Description TODO
 * @createTime 2025年02月25日 16:51:00
 */
@Data
public class TigerPositionResp {

    private Contract contract;

    @Data
    public static class Contract {

        private String secType;

        private String symbol;

        private String expiry;

        private String strike;

        private String right;

        private String underlyingSymbol;

        private String market;

        private String currency;
    }

    /**
     * 合约名称
     */
    private String contractMajorDescription;

    /**
     * 持仓数量
     */
    private BigDecimal positionQty;

    /**
     * 可平仓量
     */
    private String closablePosition;

    private BigDecimal position;


    /**
     * 合约最新报价
     */
    private BigDecimal latestPrice;

    /**
     * 应计利息
     */
    private BigDecimal accruedInterest;


    /**
     * 持仓价值
     */
    private BigDecimal marketValue;


    /**
     *  FIFO模式成本
     */
    private BigDecimal averageCostByFifo;

    /**
     * 摊薄模式成本
     */
    private BigDecimal averageCostOfCarry;

    /**
     * 均价模式成本
     */
    private BigDecimal averageCostByAverage;

    /**
     *  持仓盈亏
     */
    private BigDecimal positionPnl;

    /**
     * FIFO /FIFO模式已实现盈亏
     */
    private BigDecimal realizedPnlByFifo;

    /**
     * 平均成本模式已实现盈亏
     */
    private BigDecimal realizedPnlByAverage;

    /**
     * FIFO模式未实现盈亏
     */
    private BigDecimal unrealizedPnlByFifo;

    /**
     * 平均成本模式未实现盈亏
     */
    private BigDecimal unrealizedPnlByAverage;

    /**
     * 摊薄模式未实现盈亏
     */
    private BigDecimal unrealizedPnlByCostOfCarry;

    /**
     *  FIFO模式未实现盈亏比例
     */
    private BigDecimal unrealizedPnlPercentByFifo;

    /**
     * 平均成本模式未实现盈亏比例
     */
    private BigDecimal unrealizedPnlPercentByAverage;

    /**
     * 摊薄模式未实现盈亏比例
     */
    private BigDecimal unrealizedPnlPercentByCostOfCarry;
}
