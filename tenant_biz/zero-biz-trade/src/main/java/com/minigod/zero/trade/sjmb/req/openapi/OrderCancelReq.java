package com.minigod.zero.trade.sjmb.req.openapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName OrderCancelReq.java
 * @Description TODO
 * @createTime 2024年01月29日 16:07:00
 */
@Data
public class OrderCancelReq implements Serializable {

    private static final long serialVersionUID = 1L;


    private String orderId;

	private String accountId;


}
