package com.minigod.zero.trade.sjmb.req.openapi;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author chen
 * @ClassName OrderPlaceReq.java
 * @Description TODO
 * @createTime 2024年01月29日 10:47:00
 */
@Data
public class OrderPlaceReq implements Serializable {

    private static final long serialVersionUID = 1L;


    private String accountId;

    /**
     * 交易所
     */
    private String exchange;
    /**
     * 数量
     */
    private Map<String, Object> limitOffset;
    /**
     * 到期日，securityType为OPT时，必填；格式：yyyyMMdd；如：20210723
     */
    private String maturityDate;
    /**
     * 是否只在盘中成交，不填写时，默认为False，表示在盘前盘后也可成交
     */
    private Boolean onlyRth;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 数量
     */
    private Map<String, Object> pegDifferent;
    /**
     * 数量
     */
    private Map<String, Object> price;
    /**
     * 数量
     */
    private Map<String, Object> quantity;
    /**
     * 期权看涨看跌，securityType为OPT时，必填
     */
    private String right;
    /**
     * 证券类型
     */
    private String securityType;
    /**
     * 买卖方向
     */
    private String side;
    /**
     * 底层合约代码，securityType为OPT时，必填；如：00700
     */
    private String stockSymbol;
    /**
     * 数量
     */
    private Map<String, Object> stopPrice;
    /**
     * 行权价，securityType为OPT时，必填；如：220
     */
    private Double strike;
    /**
     * 合约代码，securityType为EQTY或FUT时，必填；如：00700
     */
    private String symbol;
    /**
     * 有效期，订单有效期
     */
    private String timeInForce;
    /**
     * 追踪止损类型
     */
    private Map<String, Object> trailingAmtUnit;



}
