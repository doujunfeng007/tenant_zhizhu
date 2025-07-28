package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName MaxmumBuyReq.java
 * @Description TODO
 * @createTime 2024年01月30日 11:25:00
 */
@Data
public class MaxmumBuyReq implements Serializable {

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
     * 到期日，securityType为OPT或WRNT时，必填；格式：yyyyMMdd
     */
    private String expiryDay;
    /**
     * 期权看涨看跌，securityType为OPT或WRNT时，必填
     */
    private String right;
    /**
     * 证券类型
     */
    private String securityType;
    /**
     * 股票代码，securityType为OPT或WRNT时，必填
     */
    private String stockSymbol;
    /**
     * 行权价，securityType为OPT或WRNT时，必填
     */
    private Double strike;
    /**
     * 合约代码，securityType为EQTY或FUT时，必填
     */
    private String symbol;


}
