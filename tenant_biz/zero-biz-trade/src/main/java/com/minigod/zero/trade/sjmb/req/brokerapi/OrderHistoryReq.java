package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName OrderHistoryReq.java
 * @Description TODO
 * @createTime 2024年01月29日 19:47:00
 */
@Data
public class OrderHistoryReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accountId;

    private Long begin;

    private Long end;

    private Integer page;

    private Integer limit;
}
