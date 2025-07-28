package com.minigod.zero.trade.tiger.req;

import lombok.Data;

/**
 * @author chen
 * @ClassName CancelOrderReq.java
 * @Description TODO
 * @createTime 2025年02月19日 18:30:00
 */
@Data
public class CancelOrderReq extends TigerBaseRequest{

    private String externalOrderId;

    private Long orderId;

    private Long requestTimestamp;

    private String accountId;

}
