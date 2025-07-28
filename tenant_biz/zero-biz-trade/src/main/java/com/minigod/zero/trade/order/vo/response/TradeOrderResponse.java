package com.minigod.zero.trade.order.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 委托信息
 */
@Data
public class TradeOrderResponse implements Serializable {

    private static final long serialVersionUID = -2260388125919493487L;
    private String id;//日期时分秒+order_no
    private Integer userId;//犇犇号
    private String ciNo;//银行客户号
    private String mobile;//手机号
    private String tradeAccount;//交易账号
    private String fundAccount;//资金账号
    private String orderNo;//委托单号
    private String bsFlag;//买卖方向[B-买入 S-卖出]
    private String assetId;//资产id
    private String stockCode;//股票代码
    private String stkName;//股票名称
    private BigDecimal price;//股票价格
    private Integer qty;//股票数量
    private String orderType;//I：竞价限价盘；A：竞价盘；L：限价盘；E：增强限价盘；S：特别限价盘；U:碎股盘
    private String exchangeType;//股票市场[K-港股 P-美股]
    private String sessionType = "0";//交易阶段标识 0-正常订单交易（默认），1-盘前交易，2-暗盘交易,3-盘后交易
    private String entrustProp = "e";//委托属性 默认：e，碎股盘：u
    private Integer orderDate;//委托日期 20200707
    private Integer opType;//操作类型 1下单，2改单，3撤单
    private Boolean isStrategy = false;//是否条件单，0否1是
    private String conditionType;//条件单类型[0-OQ 1-MIT 2-LIT]
    private String strategyType;//策略类型[0-当日有效 1-GTD 2-GTC]
    private String strategyEndDate;//策略失效日期 2020-07-07
    private Date createTime;//创建时间
    private Date updateTime;//更新时间

}
