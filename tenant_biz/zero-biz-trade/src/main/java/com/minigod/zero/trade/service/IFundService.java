package com.minigod.zero.trade.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.fund.vo.request.*;
import com.minigod.zero.trade.vo.sjmb.req.*;
import com.minigod.zero.trade.vo.sjmb.resp.FundQueryVO;

/**
 * 帐户服务
 */

public interface IFundService {

    /**
     * 获取资产汇总信息和持仓列表
     */
	R<FundQueryVO> getFundInfo(FundInfoRequest request);

    /**
     * 查询客户资金汇总
     */
	R getTotalAccount(TotalAccountRequest request);

    /**
     * 查询客户资金明细
     */
	R getDetailAccount(TotalAccountRequest request);

	/**
	 * 查询客户资金明细
	 */
	R getDetailAccount(Long custId, String fundAccount, String moneyType);

    /**
     * 查询按币种查询资金
     */
	R getSingleAccount(SingleAccountRequest request);

    /**
     * 查询客户持仓
     */
	R getUserPortfolio(UserPortfolioRequest request);

    /**
     * 查询当日委托
     */
	R getJournalOrders(JournalOrdersRequest request);

    /**
     * 查询历史委托
     */
	R getHistoryOrders(HistoryOrdersRequest request);

    /**
     * 查询当日资金流水
     */
	R getJournalFundRecord(JournalFundRecordRequest request);

    /**
     * 查询历史资金流水
     */
	R getHistoryFundRecord(HistoryFundRecordRequest request);

    /**
     * 查询当日股票流水
     */
	R getJournalStockRecord(JournalStkRecordRequest request);

    /**
     * 查询历史股票流水
     */
	R getHistoryStockRecord(HistoryStkRecordRequest request);

    /**
     * 查询最大可买
     */
	R getMaxmumBuy(MaxmumBuyRequest request);

    /**
     * 查询最大可卖
     */
	R getMaxmumSell(MaxmumSellRequest request);

    /**
     * 查询汇率
     */
	R getCurrencyMaster(CurrencyMasterRequest request);

    /**
     * 查询当日盈亏
     */
	R getJournalProfit(UserPortfolioRequest request);

    /**
     * 查询条件单
     */
	R getStrategyOrders(String request);

    /**
     * 查询客户默认费用
     */
	R getOrderCharge(String request);



    /**
     * 查询公司行动
     */
    R getCompanyAction(String request);

	/**
	 * 查询用户资金信息
	 */
	R<String> getExtractableMoney(String fundAccount, String moneyType);

	/**
	 * 查询风险水平
	 */
	R getRiskLevel(String request);
	/**
	 * 查询订单费用
	 */
	R getOrdersFee(HistoryOrdersVO request);
	/**
	 * 查询股票成交明细
	 */
	R getStockRecordDetails(StockRecordDetailsVO request);

	/**
	 * 查询历史股票成交明细
	 */
	R getHistoryStockRecordDetails(StockRecordDetailsVO request);

	/**
	 * 可融资股票
	 * @return
	 */
	R getStockMarginRatio();

	/**
	 * 入金
	 * @param fundDepositReq
	 * @return
	 */
	R deposit(FundDepositReq fundDepositReq);

	/**
	 * 出金
	 * @param fundWithdrawalReq
	 * @return
	 */
	R withdrawal(FundWithdrawalReq fundWithdrawalReq);

	/**
	 * 冻结
	 * @param fundFreezeReq
	 * @return
	 */
	R freeze(FundFreezeReq fundFreezeReq);

	/**
	 * 解冻
	 * @param fundUnFreezeReq
	 * @return
	 */
	R unFreeze(FundUnFreezeReq fundUnFreezeReq);

	/**
	 * 解冻并扣款
	 * @param fundUnFreezeWithdrawReq
	 * @return
	 */
	R unFreezeWithdraw(FundUnFreezeWithdrawReq fundUnFreezeWithdrawReq);

	/**
	 * 根据交易账号或者资金账号查询资金数据
	 * @param tradeAccount
	 * @param capitalAccount
	 * @return
	 */
	R<FundQueryVO> queryFundByAccount(String tradeAccount, String capitalAccount);

	/**
	 * 设置可融资股票
	 * @param stockMarginSettingReq
	 * @return
	 */
	R stockMarginRatioSetting(StockMarginSettingReq stockMarginSettingReq);

	/**
	 * 条件单今日委托
	 * @param request
	 * @return
	 */
    R getJournalStrategyOrders(JournalOrdersRequest request);

	/**
	 * 条件单历史委托
	 * @param request
	 * @return
	 */
	R getHistoryStrategyOrders(HistoryOrdersRequest request);
}
