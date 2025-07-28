package com.minigod.zero.trade.fund.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 查询历史委托
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class HistoryOrdersResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易委托单号
     */
    private String orderNo;

    /**
     * 委托的时间
     */
    private String orderTime;

    /**
     * 资产ID
     */
    private String assetId;

    /**
     * 股票的代码
     */
    private String stockCode;

    /**
     * 股票名称
     */
    private String stockName;

    /**
     * 股票的二级子类
     */
    private String secSType;

    /**
     * 股票市场[K-港股 P-美股]
     */
    private String exchangeType;

    /**
     * 买卖的标识
     */
    private String bsFlag;

    /**
     * 委托的价格
     */
    private String price;

    /**
     * 当前的数量
     */
    private String qty;

    /**
     * 成交量
     */
    private String businessQty;

    /**
     * 成交价
     */
    private String businessPrice;

    /**
     * 成交的时间
     */
    private String businessTime;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单状态[NEW-未报 WA-待报 PRO–已报 Q–Queued(see order_sub_status for detail) REJ–废单 PEX–部成 FEX–已成 CAN–已取消]
     */
    private String orderStatus;

    /**
     * 是否可改
     */
    private String modifiable;

    /**
     * 是否可撤
     */
    private String cancelable;

    /**
     * 币种[HKD-港币 USD-美元 CNY-人民币]
     */
    private String currency;
}
