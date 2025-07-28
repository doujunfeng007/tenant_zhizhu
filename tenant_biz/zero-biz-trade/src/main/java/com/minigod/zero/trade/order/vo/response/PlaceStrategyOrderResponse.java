package com.minigod.zero.trade.order.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 条件单下单
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class PlaceStrategyOrderResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 委托单号
     */
    private String orderNo;
}
