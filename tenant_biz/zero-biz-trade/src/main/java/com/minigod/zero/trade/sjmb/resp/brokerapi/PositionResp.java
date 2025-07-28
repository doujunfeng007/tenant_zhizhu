package com.minigod.zero.trade.sjmb.resp.brokerapi;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author chen
 * @ClassName PositionResp.java
 * @Description TODO
 * @createTime 2024年01月26日 15:00:00
 */
@Data
public class PositionResp implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Position> positions;

    @Data
    public class Position implements Serializable{
        private static final long serialVersionUID = 1L;

        /**
         * 资金组
         */
        private String segmentId;

        /**
         * 证券类型
         */
        private String securityType;

        /**
         * 交易所
         */
        private String exchange;

        /**
         * 股票代码
         */
        private String symbol;

        /**
         * 币种
         */
        private String currency;

        private Quantity quantity;

        private LatestPrice latestPrice;

        private BigDecimal marketValue;

        private AveragePrice averagePrice;

        private AverageCost averageCost;

        private BigDecimal unrealizedPnL; //未实现盈亏



        @Data
        public class Quantity implements Serializable{
            private static final long serialVersionUID = 1L;
            private Double value;  //整数部分
            private Integer offset;//小数点偏移位数

        }

        @Data
        public class LatestPrice implements Serializable{
            private static final long serialVersionUID = 1L;
            private Double value;  //整数部分
            private Integer offset;//小数点偏移位数

        }

        @Data
        public class AveragePrice implements Serializable{
            private static final long serialVersionUID = 1L;
            private Double value;  //整数部分
            private Integer offset;//小数点偏移位数

        }

        @Data
        public class AverageCost implements Serializable{
            private static final long serialVersionUID = 1L;
            private Double value;  //整数部分
            private Integer offset;//小数点偏移位数

        }


    }
}
