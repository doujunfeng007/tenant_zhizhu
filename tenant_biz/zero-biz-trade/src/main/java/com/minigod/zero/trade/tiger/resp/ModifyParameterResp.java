package com.minigod.zero.trade.tiger.resp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName ModifyParameterResp.java
 * @Description TODO
 * @createTime 2025年02月25日 14:26:00
 */
@Data
public class ModifyParameterResp {

    /**
     * 改单后的总数量
     */
    private BigDecimal totalQuantity;

    /**
     * 改单后的总金额
     */
    private BigDecimal totalCashAmount;

    /**
     * 改单后的价格
     */
    private BigDecimal price;

    /**
     *  改单后的止损价格
     */
    private BigDecimal stopPrice;
}
