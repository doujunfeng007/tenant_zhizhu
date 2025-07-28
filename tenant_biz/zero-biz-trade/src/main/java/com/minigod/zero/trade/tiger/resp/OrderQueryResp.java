package com.minigod.zero.trade.tiger.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author chen
 * @ClassName OrderQueryResp.java
 * @Description TODO
 * @createTime 2025年02月25日 14:06:00
 */
@Data
public class OrderQueryResp {

    /**
     * 下一次查询的id
     */
    private Long nextToId;

    private List<Orders> orders;

    @Data
    public static class Orders {

        private String secType;

        private String symbol;

        private String expiry;

        private String strike;

        private String right;

        private String underlyingSymbol;

        private String market;

        private String currency;

        private Long id;

        private String secSubType;

        private BigDecimal multiplier;

        private String action;

        private String timeInForce;

        private BigDecimal totalQty;

        private BigDecimal filledQty;

        private BigDecimal totalCashAmount;

        private BigDecimal filledCashAmount;

        private String orderType;

        private BigDecimal price;

        private BigDecimal stopPrice;

        private ModifyParameterResp modifyParameter;

        private BigDecimal latestPrice;

        private BigDecimal avgFillPrice;

        private String tradingSessionType;

        /**
         * 订单在交易系统中的当前状态 PendingNew：已提交。 New：已接收。 Filled：已成交 Canceled：已取消。 Rejected：已拒绝 Expired：已过期
         */
        private String status;

        /**
         * 改单状态
         */
        private String replaceStatus;

        /**
         * 撤单状态
         */
        private String cancelStatus;

        private BigDecimal proceeds;

        private BigDecimal commissionAndFees;

        private BigDecimal netAmount;

        private String message;

        private String messageCode;

        private Boolean modifiable;

        private Boolean cancelable;

        private TrailParameter trailParameter;

        /**
         * 客户指定的订单标识, 一般用于订单关联外部系统
         */
        private String externalOrderId;

        /**
         * 错误码
         */
        private String errorCode;

        /**
         * 备注
         */
        private String remark;

        /**
         * 订单过期时间 TimeInForce为GTD时需要设置
         */
        private  Long expireTime;

        /**
         * 订单状态更新时间
         */
        private Long statusUpdatedAt;

        /**
         * 订单更新时间
         */
        private Long updatedAt;

        /**
         * 订单创建时间
         */
        private Long createdAt;


    }
}
