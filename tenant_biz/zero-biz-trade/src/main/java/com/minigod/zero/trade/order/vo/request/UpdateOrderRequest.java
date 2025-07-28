package com.minigod.zero.trade.order.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;

import lombok.Data;

/**
 * @description: 改单
 * @author: Larry Lai
 * @date: 2020/4/13 15:19
 * @version: v1.0
 */

@Data
public class UpdateOrderRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 委托单号
     */
    private String orderNo;

    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 股票价格
     */
    private String price;

    /**
     * 股票数量
     */
    private String qty;

    /**
     * 股票市场[K-港股 P-美股]
     */
    private String exchangeType;

    private String tradeOrderId;//落地到数据库的id

    /**
     * 买卖方向[B-买入 S-卖出]
     */
    private String bsFlag;
}
