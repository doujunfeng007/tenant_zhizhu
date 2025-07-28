package com.minigod.zero.trade.fund.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 查询汇率
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class CurrencyMasterResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 汇率
     */
    private String exchangeRate;
}
