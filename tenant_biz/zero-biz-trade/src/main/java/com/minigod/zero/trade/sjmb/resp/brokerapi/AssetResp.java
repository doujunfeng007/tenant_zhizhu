package com.minigod.zero.trade.sjmb.resp.brokerapi;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName AssetResp.java
 * @Description TODO
 * @createTime 2024年01月11日 17:33:00
 */
@Data
public class AssetResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 币种
     */
    private String currency;

    /**
     * 已实现盈亏
     */
    private BigDecimal realizedPnl;

    /**
     * 未实现盈亏
     */
    private BigDecimal unrealizedPnl;

    /**
     * 当日盈亏
     */
    private BigDecimal todayPnl;

    /**
     * 净流动性
     */
    private BigDecimal netLiquidation;


    /**
     * 持仓标的总值
     */
    private BigDecimal gpv;

    /**
     * 初始保证金
     */
    private BigDecimal initialMarginRequirement;

    /**
     * 维持保证金
     */
    private BigDecimal maintenanceMarginRequirement;

    /**
     * 可用资金
     */
    private BigDecimal availableFunds;

    /**
     * 剩余流动性
     */
    private BigDecimal excessLiquidity;

    /**
     * 风险度
     */
    private BigDecimal riskDegree;

    private Object segments;









}
