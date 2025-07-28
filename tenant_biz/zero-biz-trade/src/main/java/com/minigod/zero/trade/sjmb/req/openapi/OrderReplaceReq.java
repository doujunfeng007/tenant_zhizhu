package com.minigod.zero.trade.sjmb.req.openapi;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author chen
 * @ClassName OrderReplaceReq.java
 * @Description TODO
 * @createTime 2024年01月29日 15:57:00
 */
@Data
public class OrderReplaceReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号，全局唯一
     */
    private String orderId;
    /**
     * 限价
     */
    private Map<String, Object> price;
    /**
     * 数量
     */
    private Map<String, Object> quantity;
    /**
     * 止损价
     */
    private Map<String, Object> stopPrice;

	private String accountId;


}
