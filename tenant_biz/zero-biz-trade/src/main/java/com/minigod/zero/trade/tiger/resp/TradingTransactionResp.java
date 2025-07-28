package com.minigod.zero.trade.tiger.resp;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TradingTransactionResp {

    /**
     * 下一次查询的id
     */
    private Long nextToId;

    private List<Trades> trades;


    @Data
    public static class Trades {

        private String secType;

        private String symbol;

        private String expiry;

        private String strike;

        private String right;

        private String market;

        private String currency;

        private Long id;

        private Long accountId;

        private Long refId;

        private String segType;

        private String action;

        private BigDecimal multiplier;

        private BigDecimal filledQty;

        private BigDecimal filledPrice;

        private BigDecimal filledAmount;

        private BigDecimal commissionAndFees;

        private String transactedAt;

        /**
         * TRADE, // 交易
         * SUB_TRADE, // 做空召回
         * TRANSFER, // 转股
         * IPO, // IPO
         * INTERNAL_TRANSFER, // 内部转股
         * BLOCK_TRADE, // 大宗交易
         * GIFTED_TRANSFER, // 活动送股
         * BOND_MATURITY, // 债券到期
         * SN_KNOCK_OUT, // 结构产品提前敲出
         * SN_MATURITY, // 结构产品到期
         * OMNIBUS_GL_ADJ, // 上手大账号持仓调整
         * OPTION_EXERCISE, // 期权行权
         * OPTION_EXPIRE, // 期权过期
         * SPLIT, // 拆合股
         * SPIN_OFF, // 公司分拆
         * MERGE, // 公司合并
         * DELIST, // 退市
         * RIGHTS_ISSUE, // 供股
         * EXERCISE, // 供股权行权-供股权
         * SUBSCRIPTION, // 供股权行权-正股
         * STOCK_DIVIDEND, // 股票分红
         * TENDER_ISSUE, // 收购赎回
         * FUT_CASH_SETTLE, // 期货现金交割
         * FRACSHARE, // 碎股处理
         * ODD_TRANSFER, // 碎股回收
         * DIVIDEND_SUBSCRIPTION, // 权证派发
         * CONVERSION, // 持仓转换
         * DVP_TRANSFER, // 内部DVP转股
         * CANCELED_TRADE // 撤销交易
         */
        private String tradeType;
    }
}
