package com.minigod.zero.trade.tiger.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author chen
 * @ClassName AssetsResp.java
 * @Description TODO
 * @createTime 2025年02月19日 16:08:00
 */
@Data
public class AssetsResp {

    /**
     * {
     *   "errorCode": "SUCCESS",
     *   "serverTime": 1743044233847,
     *   "errorMessage": "ok",
     *   "data": {
     *     "accountId": 70156980,
     *     "assets": [
     *       {
     *         "currency": "USD",
     *         "segType": "SEC",
     *         "equityWithLoanValue": 100010.0,
     *         "netLiquidationValue": 100010.0,
     *         "cashBalance": 100010.0,
     *         "uncollected": 0.0,
     *         "grossPositionValue": 0.0,
     *         "stockMarketValue": 0.0,
     *         "optionMarketValue": 0.0,
     *         "settledCash": 100010.0,
     *         "initialMarginRequirements": 0.0,
     *         "maintenanceMarginRequirements": 0.0,
     *         "excessEquity": 100010.0,
     *         "excessLiquidity": 100010.0,
     *         "availableExcessEquity": 100010.0,
     *         "lockedExcessEquity": 0.0,
     *         "creditLineAmount": 0.0,
     *         "accruedInterest": 0.0,
     *         "intradayRiskRatio": 0.0,
     *         "overnightRiskRatio": 0.0,
     *         "leverage": 0.0,
     *         "shortCollateral": 0.0,
     *         "assetsByCurrency": [
     *           {
     *             "currency": "USD",
     *             "forexRateToBase": 1.0,
     *             "cashBalance": 100010.0,
     *             "settledCash": 100010.0,
     *             "lockedCash": 0.0,
     *             "availableCash": 100010.0,
     *             "availableSettledCash": 100010.0,
     *             "availableExcessEquity": 100010.0,
     *             "accruedInterest": 0.0
     *           }
     *         ]
     *       }
     *     ]
     *   }
     * }
     */

    private Long accountId;

    private List<Assets> assets;

    @Data
    public static class Assets {

        private String currency;

        /**
         * 账户类型
         */
        private String segType;

        /**
         * 具有借贷价值的资产值，指在考虑到借款或杠杆的情况下，投资者所持有资产的权益价值。这个值是基于投资者的总资产减去负债（如借款）后的净值
         */
        private BigDecimal equityWithLoanValue;

        /**
         * 净清算价值(总资产)，指在考虑到所有资产和负债的情况下，投资者的投资组合如果被立即清算所得到的净值
         */
        private BigDecimal netLiquidationValue;

        /**
         * 现金余额
         */
        private BigDecimal cashBalance;

        /**
         * 在途资产，指的是尚未到账但已经在支付或转移过程中的资金。
         */
        private BigDecimal uncollected;

        /**
         * 总持仓价值，是指投资者在某个特定时间点上，所有持有证券（如股票、期货、债券等）的市场价值的总和
         */
        private BigDecimal grossPositionValue;

        /**
         * 已结算现金, 指的是在金融交易中，资金或现金清算的金额。
         */
        private BigDecimal settledCash;

        /**
         * 初始保证金要求,指在进行杠杆交易时，投资者必须为开设一个新仓位而存入的最低资金金额
         */
        private BigDecimal initialMarginRequirements;

        /**
         * 维持保证金要求，是指在杠杆交易中，投资者账户中必须保持的最低保证金水平，以维持当前持仓的有效性。
         */
        private BigDecimal maintenanceMarginRequirements;

        /**
         * 超额权益(剩余具有借贷价值资产)，指的是投资者账户中的总权益（即账户资产的价值减去负债）超过了维持保证金要求的部分
         */
        private BigDecimal excessEquity;

        /**
         * 超额流动性(剩余流动性)，指的是投资者账户中，除了满足维持保证金要求外，剩余的可用于其他投资或应对市场波动的现金或可立即变现的资产部分。它表示账户中超出最低流动性要求的资金
         */
        private BigDecimal excessLiquidity;

        /**
         * 可用超额权益(剩余可用的具有借贷价值资产), 指的是投资者账户中，扣除维持保证金要求后，剩余的超额权益部分，且这些超额权益可以用于新的投资或应对其他市场波动的资金。
         */
        private BigDecimal availableExcessEquity;

        /**
         * 锁定超额权益(锁定的具有借贷价值资产), 指的是投资者账户中，虽然属于超额权益的一部分，但由于某些原因（如已投入某些未完成的交易、冻结资金或其他限制性操作），这些超额权益暂时不能用于其他投资或操作的资金
         */
        private BigDecimal lockedExcessEquity;

        /**
         * 信用额度，指的是金融机构或券商为投资者提供的可用信用额度的总金额。
         */
        private BigDecimal creditLineAmount;

        private List<AssetsByCurrency> assetsByCurrency;

    }
}
