package com.minigod.zero.trade.fund.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;

import lombok.Data;

/**
 * @description: 查询按币种查询资金
 * @author: Larry Lai
 * @date: 2020/4/13 15:19
 * @version: v1.0
 */

@Data
public class SingleAccountRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 币种[HKD-港币 USD-美元 CNY-人民币]
     */
    private String currency;
}
