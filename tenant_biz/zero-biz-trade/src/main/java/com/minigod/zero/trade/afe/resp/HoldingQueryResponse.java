package com.minigod.zero.trade.afe.resp;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author chen
 * @ClassName HoldingQueryResponse.java
 * @Description TODO
 * @createTime 2024年04月24日 15:25:00
 */
@Data
public class HoldingQueryResponse {

    /**
     * 持仓列表
     */
    @JSONField(name = "STOCK_HOLDING")
    private List<StockHolding> stockHoldingList;

    /**
     * 资金汇总列表
     */
    @JSONField(name = "CASH_HOLDING")
    private List<CashHolding> cashHoldingList;

    @Data
    public static class StockHolding {

        @JSONField(name = "TODAY_SOLD_QTY")
        private String todaySoldQty;

        @JSONField(name = "AVAILABLE_MKT_VAL")
        private String availableMktVal;

        @JSONField(name = "MARGIN_PCT")
        private String marginPct;

        @JSONField(name = "MARGIN_VAL")
        private String marginVal;

        @JSONField(name = "CURRENCY_CODE")
        private String currencyCode;

        @JSONField(name = "UNDER_DUE_PAY_QTY")
        private String underDuePayQty;

        @JSONField(name = "HOLD_QUANTITY")
        private String holdQuantity;

        @JSONField(name = "LAST_CLOSE_PRICE")
        private String lastClosePrice;

        @JSONField(name = "Msg_ID")
        private String msgId;

        @JSONField(name = "STATUS")
        private String status;

        @JSONField(name = "INSTRUMENT_NAME")
        private String instrumentName;

        @JSONField(name = "TODAY_BOUGHT_QTY")
        private String todayBoughtQty;

        @JSONField(name = "TNAME")
        private String tname;

        @JSONField(name = "UNDER_DUE_REC_QTY")
        private String underDueRecQty;

        @JSONField(name = "LEDGAR_BAL")
        private String ledgagBal;

        @JSONField(name = "ENAME")
        private String ename;

        @JSONField(name = "AVAILABLE_QUANTITY")
        private String availableQuantity;

        @JSONField(name = "MARKET")
        private String market;

        @JSONField(name = "SNAME")
        private String sname;

        @JSONField(name = "INSTRUMENT_CODE")
        private String instrumentCode;

        @JSONField(name = "UNDER_DUE_QTY")
        private String underDueQty;

        @JSONField(name = "EXCHG_CD")
        private String exchgCd;

        @JSONField(name = "EX_RATE")
        private String exRate;

        @JSONField(name = "TODAY_SELL_QTY")
        private String todaySellQty;

        @JSONField(name = "DUE_AND_OVERDUE_QTY")
        private String dueAndOverdueQty;

        @JSONField(name = "AVG_COST")
        private String avgCost;

        @JSONField(name = "CURRENT_AVG_COST")
        private String currentAvgCost;

    }

    @Data
    public static class CashHolding {

        @JSONField(name = "ACCRUED_INTEREST")
        private String accruedInterest;

        @JSONField(name = "AVAILABLE_BAL")
        private String availableBal;

        @JSONField(name = "CASH_ON_HOLD")
        private String cashOnHold;

        @JSONField(name = "AVAILABLE_MARGIN_VAL")
        private String availableMarginVal;

        @JSONField(name = "BUYING_POWER")
        private String buyingPower;

        @JSONField(name = "UNDER_DUE_AMT")
        private String underDueAmt;

        @JSONField(name = "AVAILABLE_MKT_VAL")
        private String availableMktVal;

        @JSONField(name = "BUY_SOLD_CONSIDERATION")
        private String buySoldConsideration;

        @JSONField(name = "DUE_AND_OVERDUE_AMT")
        private String dueAndOverdueAmt;

        @JSONField(name = "NET_DEPOSIT")
        private String netDeposit;

        @JSONField(name = "TODAY_INTRADAY_PNL")
        private String todayIntradayPnl;

        @JSONField(name = "CURRENCY_CODE")
        private String currencyCode;

        @JSONField(name = "TOTAL_PNL")
        private String totalPnl;

        @JSONField(name = "Msg_ID")
        private String msgId;

        @JSONField(name = "SOLD_CONSIDERATION")
        private String soldConsideration;

        @JSONField(name = "CASH_BALANCE")
        private String cashBalance;

        @JSONField(name = "STATUS")
        private String status;

        @JSONField(name = "EX_RATE")
        private String exRate;

        @JSONField(name = "BUY_CONSIDERATION")
        private String buyConsideration;

        @JSONField(name = "UNSETTLED_CASH")
        private String unsettledCash;

    }
}
