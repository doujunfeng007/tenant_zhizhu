package com.minigod.zero.trade.order.vo.request;

import lombok.Data;

/**
 * @description: 条件单改单
 * @author: Larry Lai
 * @date: 2020/4/13 15:19
 * @version: v1.0
 */

@Data
public class UpdateStrategyOrderRequest extends UpdateOrderRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 条件单类型[0-OQ 1-MIT 2-LIT]
     */
    private String conditionType;

    /**
     * 策略类型[0-当日有效 1-GTD 2-GTC]
     */
    private String strategyType;

    /**
     * 策略失效日期
     */
    private String strategyEndDate;

    /**
     * 触发价格
     */
    private String triggerPrice;

    /**
     * 触发价格上限
     */
    private String triggerPriceUp;

    /**
     * 触发价格下限
     */
    private String triggerPriceDown;
}
