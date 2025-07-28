package com.minigod.zero.trade.fund.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 查询条件单
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class StrategyOrdersResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 委托单号
     */
    private String orderNo;

    /**
     * 委托时间
     */
    private String orderTime;

    /**
     * 资产ID
     */
    private String assetId;

    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 股票名称
     */
    private String stockName;

    /**
     * 股票二级子类
     */
    private String secSType;

    /**
     * 买卖标识
     */
    private String bsFlag;

    /**
     * 股票市场[K-港股 P-美股]
     */
    private String exchangeType;

    /**
     * 委托价格
     */
    private String price;

    /**
     * 当前数量
     */
    private String qty;

    /**
     * 成交价格
     */
    private String businessPrice;

    /**
     * 成交数量
     */
    private String businessQty;

    /**
     * 成交时间
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
     * 是否可撤
     */
    private String cancelable;

    /**
     * 是否可改
     */
    private String modifiable;

    /**
     * 币种[HKD-港币 USD-美元 CNY-人民币]
     */
    private String currency;

    /**
     * 条件单状态[1-未执行 2-执行中 3-执行完成 4-暂停 5-强制释放]
     */
    private String strategyStatus;

    /**
     * 条件单类型[0-OQ 1-MIT 2-LIT]
     */
    private String conditionType;

    /**
     * 策略类型[0-当日有效 1-GTD 2-GTC]
     */
    private String strategyType;

    /**
     * 策略失效日期
     */
    private String strategyEndDate;

    /**
     * 触发价格
     */
    private String triggerPrice;

    /**
     * 触发价格上限
     */
    private String triggerPriceUp;

    /**
     * 触发价格下限
     */
    private String triggerPriceDown;
}
