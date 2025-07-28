package com.minigod.zero.trade.tiger.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName PlaceOrderReq.java
 * @Description TODO
 * @createTime 2025年02月19日 17:55:00
 */
@Data
public class PlaceOrderReq {

    private String externalOrderId;

    /**
     * STOCK:股票
     * OPTION:期权
     * WARRANT:权证
     * FOREX:foreign exchange/外汇
     * CBBC:callable bull/bear contract/牛熊证
     */
    private String secType;

    /**
     * 股票代码
     */
    private String symbol;

    /**
     * 期权到期日 (格式：yyyyMMdd)
     */
    private String expiry;

    /**
     * 期权的执行价格，行使期权时，执行价格是持有人有权买入（看涨期权）或卖出（看跌期权）标的股票的价格。
     */
    private String strike;

    /**
     *  期权权利类型, 期权合约赋予持有人在特定时间段内，按照事先约定的价格买入或卖出标的资产的权利
     * CALL:持有者在特定时间内按约定价格购买标的资产的权利
     * PUT:持有者在特定时间内按约定价格卖出标的资产的权利
     */
    private String right;

    /**
     * 老虎内部股票、期货等证券唯一标识，类似股票代码,你可以通过ID 进行下单，同时也支持使用传入参数(secType + symbol + option + strike+ expiry)方式下单
     */
    private Long contractId;

    private Number quantity;

    /**
     *  BUY or SELL / 订单买卖方向(SELL:卖 BUY:买
     */
    private String action;

    /**
     * LMT:limit order
     * MKT: market order
     * STP: stop order
     * STP_LMT: stop limit order
     * TRAIL:trailing loss order
     * AM: Auction market order
     * AL:Auction Limit order
     * / 订单类型
     * LMT:限价单
     * MKT: 市价单
     * STP: 止损单
     * STP_LMT: 止损限价单
     * TRAIL:追踪止损单
     * AM: 竞价市价单
     * AL:竞价限价单
     */
    private String  orderType;

    private Number price;

    private Number stopPrice;

    private String timeInForce;

    /**
     * 交易时段是指允许股票交易所接受买卖订单的时间段。它通常分为开盘前、正常交易、盘后等不同阶段 RTH : 正常交易时段 OVERNIGHT : 隔夜交易时段 PRE_RTH_POST : 盘前盘后交易时段
     */
    private String tradingSessionType;

    private TrailReq trailReq;

    /**
     * 按金额下单意味着你可以买多少钱的股票 (Required if quantity is null)
     */
    private BigDecimal cashAmount;


    private String remark;

    /**
     * 订单过期时间 TimeInForce为GTD时需要设置
     */
    private Long expireTime;

    private Long requestTimestamp;


}
