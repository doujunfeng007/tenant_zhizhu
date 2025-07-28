package com.minigod.zero.trade.order.vo.request;

import com.minigod.zero.trade.vo.request.BaseRequest;

import lombok.Data;

/**
 * @description: 下单
 * @author: Larry Lai
 * @date: 2020/4/13 15:19
 * @version: v1.0
 */

@Data
public class PlaceOrderRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 买卖方向[B-买入 S-卖出]
     */
    private String bsFlag;

    /**
     * 股票代码 00700
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
     * I：竞价限价盘；A：竞价盘；L：限价盘；E：增强限价盘；S：特别限价盘；U:碎股盘
     */
    private String orderType;

    /**
     * 股票市场[K-港股 P-美股]
     */
    private String exchangeType;

    /**
     * 交易阶段标识 0-正常订单交易（默认），1-盘前交易，2-暗盘交易,3-盘后交易
     */
    private String sessionType = "0";

    /**
     * 委托属性 默认：e，碎股盘：u
     */
    private String entrustProp = "e";

    private String bcan;

}
