package com.minigod.zero.trade.tiger.resp;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AccountPnlResp {

    private Long accountId;

    private List<Pnls> pnls;

    @Data
    public static  class Pnls {

        private String currency;

        /**
         * 账户类型
         */
        private String segType;


        /**
         * The realized profit and loss of FIFO /FIFO模式已实现盈亏
         */
        private BigDecimal realizedPnlByFifo;

        /**
         * The realized profit and loss of average /平均成本模式已实现盈亏
         */
        private BigDecimal realizedPnlByAverage;


        /**
         * Unrealized profit and loss of FIFO /FIFO模式未实现盈亏
         */
        private BigDecimal unrealizedPnlByFifo;

        /**
         * Unrealized profit and loss of average /均价成本模式未实现盈亏
         */
        private BigDecimal unrealizedPnlByAverage;

        /**
         * Unrealized profit and loss of average /均价成本模式未实现盈亏
         */
        private BigDecimal unrealizedPnlByCostOfCarry;

        /**
         * 持仓盈亏
         */
        private BigDecimal positionPnl;
    }
}
