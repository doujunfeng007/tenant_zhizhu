package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName OrderTodayReq.java
 * @Description TODO
 * @createTime 2024年01月29日 16:56:00
 */
@Data
public class OrderTodayReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accountId;

    private Integer page;

    private Integer limit;
}
