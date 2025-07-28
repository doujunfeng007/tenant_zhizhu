package com.minigod.zero.trade.sjmb.resp.brokerapi;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName Segments.java
 * @Description TODO
 * @createTime 2024年01月11日 18:29:00
 */
@Data
public class Segments implements Serializable {

    private static final long serialVersionUID = 1L;

    private String segmentId;

    private String currency;

    private BigDecimal realizedPnl;

    private BigDecimal todayPnl;

    private BigDecimal netLiquidation;

    private BigDecimal gpv;

    private BigDecimal initialMarginRequirement;

    private BigDecimal maintenanceMarginRequirement;

    private BigDecimal availableFunds;

    private BigDecimal excessLiquidity;

    private BigDecimal riskDegree;

    private Cashes cashes;
}
