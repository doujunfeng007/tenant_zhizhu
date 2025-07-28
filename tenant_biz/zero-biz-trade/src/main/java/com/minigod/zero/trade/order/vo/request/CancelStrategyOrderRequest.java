package com.minigod.zero.trade.order.vo.request;

import lombok.Data;

/**
 * @description: 条件单撤单
 * @author: Larry Lai
 * @date: 2020/4/13 15:19
 * @version: v1.0
 */

@Data
public class CancelStrategyOrderRequest extends CancelOrderRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 条件单类型[0-OQ 1-MIT 2-LIT]
     */
    private String conditionType;
}
