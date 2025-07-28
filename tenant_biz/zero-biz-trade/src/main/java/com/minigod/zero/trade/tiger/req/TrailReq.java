package com.minigod.zero.trade.tiger.req;

import lombok.Data;

/**
 * @author chen
 * @ClassName TrailReq.java
 * @Description TODO
 * @createTime 2025年02月19日 18:03:00
 */
@Data
public class TrailReq {

    /**
     *当市场价格达到或超过设置的触发价时，止损限价单被激活。触发价通常基于市场当前价格计算一个百分比
     */
    private Number trailPercent;

    /**
     * 置一个固定的价格差，当市场价格触及触发价时，止损单被激活
     */
    private Number trailSpread;
}
