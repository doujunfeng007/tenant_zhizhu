package com.minigod.zero.trade.sjmb.resp.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName MaxmumBuyResp.java
 * @Description TODO
 * @createTime 2024年01月30日 14:37:00
 */
@Data
public class MaxmumBuyResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 现金最大可做多，存在做空持仓时，此值为空
     */
    private Integer maxByCash;
    /**
     * 融资最大可做多，存在做空持仓时，此值为空
     */
    private Integer maxLong;
    /**
     * 融资最大可做空，存在做多持仓时，此值为空
     */
    private Integer maxShort;
    /**
     * 持仓可卖
     */
    private Integer positionForClose;


}
