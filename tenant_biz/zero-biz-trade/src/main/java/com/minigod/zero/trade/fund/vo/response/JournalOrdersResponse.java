package com.minigod.zero.trade.fund.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 查询当日委托
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class JournalOrdersResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易委托的单号
     */
    private String orderNo;

    /**
     * 委托下单时间
     */
    private String orderTime;

    /**
     * 资产的ID
     */
    private String assetId;

    /**
     * 股票 代码
     */
    private String stockCode;

    /**
     * 股票 名称
     */
    private String stockName;

    /**
     * 买卖 标识
     */
    private String bsFlag;

    /**
     * 股票市场 [K-港股 P-美股]
     */
    private String exchangeType;

    /**
     * 股票 二级子类
     */
    private String secSType;

    /**
     * 委托价
     */
    private String price;

    /**
     * 当前委托数量
     */
    private String qty;

    /**
     * 订单 类型
     */
    private String orderType;

    /**
     * 成交价
     */
    private String businessPrice;

    /**
     * 成交量
     */
    private String businessQty;

    /**
     * 成交 时间
     */
    private String businessTime;

    /**
     * 订单状态 [NEW-未报 WA-待报 PRO–已报 Q–Queued(see order_sub_status for detail) REJ–废单 PEX–部成 FEX–已成 CAN–已取消]
     */
    private String orderStatus;

    /**
     * 是否可撤单
     */
    private String cancelable;

    /**
     * 是否可改单
     */
    private String modifiable;

    /**
     * 每手股数量
     */
    private String lotSize;

    /**
     * 币种 [HKD-港币 USD-美元 CNY-人民币]
     */
    private String currency;

    /**
     * 拒单的原因
     */
    private String rejectReason;

    /**
     * 订单的其他费用
     */
    private BigDecimal otherFees;

    /**
     * 订单佣金
     */
    private BigDecimal commissionCharge;
}
