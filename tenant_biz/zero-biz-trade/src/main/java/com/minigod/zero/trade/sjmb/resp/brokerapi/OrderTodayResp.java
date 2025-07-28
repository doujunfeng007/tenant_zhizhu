package com.minigod.zero.trade.sjmb.resp.brokerapi;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chen
 * @ClassName OrderTodayResp.java
 * @Description TODO
 * @createTime 2024年01月29日 17:10:00
 */
@Data
public class OrderTodayResp implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Order> items;
    private Integer limit;
    private Integer page;
    private Integer total;


    @Data
    public static class Order implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 账号，泰坦系统使用的账号ID
         */
        private String accountId;
        /**
         * 平均成交价
         */
        private AveragePrice averagePrice;
        /**
         * 已成交数量
         */
        private CumulativeQuantity cumulativeQuantity;
        /**
         * 货币
         */
        private String currency;
        /**
         * 交易所
         */
        private String exchange;
        /**
         * 是否为暗盘
         */
        private boolean greyMarket;
        /**
         * 更新时间，毫秒
         */
        private Long lastUpdateTime;
        /**
         * 剩余数量
         */
        private LeavesQuantity leavesQuantity;
        /**
         * 追踪止损限价单限价差值
         */
        private LimitOffset limitOffset;
        /**
         * 是否只在盘中成交，标识是否只在盘中成交
         */
        private Boolean onlyRth;
        /**
         * 订单编号，全局唯一
         */
        private String orderId;
        /**
         * 拒绝原因
         */
        private String orderRejectReason;
        /**
         * 订单来源
         */
        private String orderSource;
        /**
         * 订单类型
         */
        private String orderType;
        /**
         * 追踪止损差值
         */
        private PegDifferent pegDifferent;
        /**
         * 下单时间，毫秒
         */
        private Long placeTime;
        /**
         * 限价
         */
        private Price price;
        /**
         * 数量
         */
        private Quantity quantity;
        /**
         * 证券类型
         */
        private String securityType;
        /**
         * 资金组
         */
        private String segmentId;
        /**
         * 买卖方向
         */
        private String side;
        /**
         * 订单状态
         */
        private String status;
        /**
         * 止损价
         */
        private StopPrice stopPrice;
        /**
         * 合约代码，如：00700
         */
        private String symbol;
        /**
         * 附加信息，订单拒绝时此字段为拒绝原因
         */
        private String text;
        /**
         * 有效期，订单有效期
         */
        private String timeInForce;
        /**
         * 追踪止损类型
         */
        private TrailingAmtUnit trailingAmtUnit;


        @Data
        public class Quantity implements Serializable {
            private static final long serialVersionUID = 1L;
            private Integer value;
            private Integer offset;
        }

        @Data
        public class CumulativeQuantity implements Serializable {
            private static final long serialVersionUID = 1L;
            private Integer value;
            private Integer offset;
        }

        @Data
        public class LeavesQuantity implements Serializable {
            private static final long serialVersionUID = 1L;
            private Integer value;
            private Integer offset;
        }

        @Data
        public class Price implements Serializable {
            private static final long serialVersionUID = 1L;
            private Integer value;
            private Integer offset;
        }

        @Data
        public class AveragePrice implements Serializable {
            private static final long serialVersionUID = 1L;
            private Integer value;
            private Integer offset;
        }
        @Data
        public class LimitOffset implements Serializable {
            private static final long serialVersionUID = 1L;
            private Integer value;
            private Integer offset;
        }

        @Data
        public class TrailingAmtUnit implements Serializable {
            private static final long serialVersionUID = 1L;
            private Integer value;
            private Integer offset;
        }
        @Data
        public class StopPrice implements Serializable {
            private static final long serialVersionUID = 1L;
            private Integer value;
            private Integer offset;
        }

        @Data
        public class PegDifferent implements Serializable {
            private static final long serialVersionUID = 1L;
            private Integer value;
            private Integer offset;
        }
    }
}
