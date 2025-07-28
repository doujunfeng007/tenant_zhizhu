package com.minigod.zero.trade.tiger.req;

import lombok.Data;

/**
 * @author chen
 * @ClassName UpdateOrderReq.java
 * @Description TODO
 * @createTime 2025年02月19日 17:20:00
 */
@Data
public class UpdateOrderReq {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 客户指定的订单标识, 一般用于订单关联外部系统
     */
    private String externalOrderId;

    /**
     * 数量
     */
    private Number quantity;

    /**
     * 价格
     */
    private Number price;

    /**
     * 设置的一个触发价格。当市场价格达到或突破这个止损价时，止损单将被激活，并转变为市价单或限价单进行执行，旨在限制亏损或锁定利润
     */
    private Number stopPrice;


    private TrailReq trailReq;


    /**
     * 按金额下单意味着你可以买多少钱的股票
     */
    private Number cashAmount;

    /**
     * 发起请求的时间戳
     */
    private Long requestTimestamp;
}
