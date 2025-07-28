package com.minigod.zero.trade.sjmb.resp.brokerapi;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName AssetDetailResp.java
 * @Description 账号资产明细
 * @createTime 2024年01月12日 11:14:00
 */
@Data
public class AssetDetailResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总资产
     */
    private BigDecimal asset;
    /**
     * 委托冻结(买入未成交冻结金额)
     */
    private BigDecimal cashOnHold;
    /**
     * 基础币种
     */
    private String currency;
    /**
     * 账面金额
     */
    private BigDecimal currentBalance;
    /**
     * 可用金额(购买力)
     */
    private BigDecimal enableBalance;
    /**
     * 可取金额
     */
    private BigDecimal fetchBalance;
    /**
     * 冻结金额
     */
    private BigDecimal frozenBalance;
    /**
     * 港元总资产
     */
    private BigDecimal hkAsset;
    /**
     * 港元委托冻结(买入未成交冻结金额)
     */
    private BigDecimal hkCashOnHold;
    /**
     * 港元现金余额
     */
    private BigDecimal hkCurrentCash;
    /**
     * 港元可用资金
     */
    private BigDecimal hkEnableBalance;
    /**
     * 港元可提资金
     */
    private BigDecimal hkFetchBalance;
    /**
     * 港元冻结金额
     */
    private BigDecimal hkFrozenBalance;
    /**
     * 港股持仓盈亏金额(总盈亏)
     */
    private BigDecimal hkIncomeBalance;
    /**
     * 港股做多持仓
     */
    private BigDecimal hkLongHoldValue;
    /**
     * 港元持仓市值
     */
    private BigDecimal hkMarketValue;
    /**
     * 港元其它冻结
     */
    private BigDecimal hkOtherFrozenBalance;
    /**
     * 港元在途资金
     */
    private BigDecimal hkUnsettledBalance;
    /**
     * 持仓盈亏金额(总盈亏)
     */
    private BigDecimal incomeBalance;
    /**
     * 计息金额
     */
    private BigDecimal interestBearingBalance;
    /**
     * IPO扣款金额
     */
    private BigDecimal ipoDeductBalance;
    /**
     * 做多持仓市值
     */
    private BigDecimal longHoldValue;
    /**
     * 追缴保证金
     */
    private BigDecimal marginCall;
    /**
     * 初始保证金
     */
    private BigDecimal marginInitial;
    /**
     * 维持保证金
     */
    private BigDecimal marginMaintenance;
    /**
     * 证券市值
     */
    private BigDecimal marketValue;
    /**
     * 当月累计利息
     */
    private BigDecimal monthlyAccumulatedInterest;
    /**
     * 其他冻结
     */
    private BigDecimal otherFrozenBalance;
    /**
     * 资金余额
     */
    private BigDecimal settledBalance;
    /**
     * 做空持仓市值
     */
    private BigDecimal shortHoldValue;
    /**
     * 今日盈亏(今日总盈亏)
     */
    private BigDecimal todayIncomeBalance;
    /**
     * 待交收金额
     */
    private BigDecimal unsettledBalance;
    /**
     * 美元总资产
     */
    private BigDecimal usAsset;
    /**
     * 美元委托冻结(买入未成交冻结金额)
     */
    private BigDecimal usCashOnHold;
    /**
     * 美元现金余额
     */
    private BigDecimal usCurrentCash;
    /**
     * 美元可用资金
     */
    private BigDecimal usEnableBalance;
    /**
     * 美元可提资金
     */
    private BigDecimal usFetchBalance;
    /**
     * 美元冻结金额
     */
    private BigDecimal usFrozenBalance;
    /**
     * 美股持仓盈亏金额(总盈亏)
     */
    private BigDecimal usIncomeBalance;
    /**
     * 美股做多持仓美股做多持仓
     */
    private BigDecimal usLongHoldValue;
    /**
     * 美元持仓市值
     */
    private BigDecimal usMarketValue;
    /**
     * 美元其它冻结
     */
    private BigDecimal usOtherFrozenBalance;
    /**
     * 美股做空持仓
     */
    private BigDecimal usShortHoldValue;
    /**
     * 美元在途资金
     */
    private BigDecimal usUnsettledBalance;


}
