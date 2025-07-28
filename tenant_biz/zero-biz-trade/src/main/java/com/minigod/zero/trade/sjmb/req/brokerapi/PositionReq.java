package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName PositionReq.java
 * @Description TODO
 * @createTime 2024年01月26日 14:11:00
 */
@Data
public class PositionReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 泰坦系统使用的账号ID
     */
    private String accountId;
    /**
     * 交易所
     */
    private String exchange;
    /**
     * 市场
     */
    private String market;
    /**
     * 证券类型
     */
    private String securityType;
    /**
     * 资金组
     */
    private String segmentId;
}
