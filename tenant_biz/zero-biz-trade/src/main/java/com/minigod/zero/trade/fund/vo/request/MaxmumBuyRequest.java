package com.minigod.zero.trade.fund.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;

import lombok.Data;

/**
 * @description: 查询最大可买
 * @author: Larry Lai
 * @date: 2020/4/13 15:19
 * @version: v1.0
 */

@Data
public class MaxmumBuyRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 股票价格
     */
    private String price;

    /**
     * 股票市场[K-港股 P-美股]
     */
    private String exchangeType;
}
