package com.minigod.zero.trade.common.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 汇率
 * @author: Larry Lai
 * @date: 2020/4/22 11:01
 * @version: v1.0
 */

@Data
public class CcyExchangeRate implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fromCurrency;
    private String toCurrency;
    private String exchangeRate;

}
