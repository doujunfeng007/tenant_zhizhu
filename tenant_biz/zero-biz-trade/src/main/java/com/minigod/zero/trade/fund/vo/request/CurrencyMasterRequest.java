package com.minigod.zero.trade.fund.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;
import lombok.Data;

/**
 * @description: 查询汇率
 * @author: Larry Lai
 * @date: 2020/4/13 15:19
 * @version: v1.0
 */

@Data
public class CurrencyMasterRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 源币种
     */
    private String fromCurrency;

    /**
     * 目标币种
     */
    private String toCurrency;
}
