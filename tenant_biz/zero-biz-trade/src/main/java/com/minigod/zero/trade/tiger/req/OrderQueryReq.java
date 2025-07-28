package com.minigod.zero.trade.tiger.req;

import lombok.Data;

/**
 * @author chen
 * @ClassName OrderQueryReq.java
 * @Description TODO
 * @createTime 2025年02月25日 11:37:00
 */
@Data
public class OrderQueryReq {

    /**
     * 期权到期日
     */
    private String expiry;

    /**
     *  限制 limit <= 100
     */
    private Long limit;

    /**
     * 市场
     */
    private String market;

    /**
     * 订单id列表
     */
    private Long[] orderIds;

    private String right;

    private String secType;

    private String strike;

    private String symbol;

    /**
     * to id / 结束id
     */
    private Long toId;

    private String accountId;

    /**
     *  订单创建开始时间
     */
    private String sinceTime;

    /**
     * 订单创建结束时间
     */
    private String toTime;
}
