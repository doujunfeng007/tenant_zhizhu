package com.minigod.zero.trade.tiger.resp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName TrailParameter.java
 * @Description TODO
 * @createTime 2025年02月25日 14:33:00
 */
@Data
public class TrailParameter {

    /**
     * 当市场价格达到或超过设置的触发价时，止损限价单被激活。触发价通常基于市场当前价格计算一个百分比
     */
    private BigDecimal trailPercent;

    /**
     * 设置一个固定的价格差，当市场价格触及触发价时，止损单被激活
     */
    private BigDecimal trailSpread;
}
