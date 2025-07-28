package com.minigod.zero.customer.api.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.vo.CustBondTradingListVO;
import com.minigod.zero.customer.vo.CustHldTradingListVO;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/6 21:19
 * @description： 我的投资业务实现
 */
public interface MyInvestmentService {
	/**
	 * 账户收益
	 * @param accountType link{@com.minigod.zero.customer.enums.AccountType}
	 * @param currency 币种
	 * @return
	 */
	R getAccountIncome(Integer accountType,String currency);

	/**
	 * 获取用户当前持仓
	 * @return
	 */
	R getCustomerFundHolding(Integer pageIndex,Integer pageSize);

	/**
	 * 活利得持仓
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	R hldHolding(Integer pageIndex,Integer pageSize);

	/**
	 * 总资产
	 * @return
	 */
	R totalAssets(String currency);

	R fundTradingList(String type, Integer pageIndex,Integer pageSize);

	R hldTradingList(CustHldTradingListVO custHldTradingListVO);

	R bondTradingList(CustBondTradingListVO custBondTradingListVO);

	R assertsHistory(String currency);

	R positionsAsserts(String currency);

	R checkTradePassword(String exAccountId,String token);
}
