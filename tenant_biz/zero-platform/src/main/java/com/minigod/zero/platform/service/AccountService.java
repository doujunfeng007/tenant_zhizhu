package com.minigod.zero.platform.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.dto.OpenAccountDTO;
import com.minigod.zero.platform.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/15 19:35
 * @description：账户相关业务处理
 */
public interface AccountService {

	/**
	 * 基金开户
	 * @param fundOpenAccount
	 * @return
	 */
	R fundOpenAccount(OpenAccountDTO fundOpenAccount);

	/**
	 * 基金账户修改
	 * @param fundOpenAccount
	 * @return
	 */
	R updateFundAccount(OpenAccountDTO fundOpenAccount);

	/**
	 * 获取基金子账号余额
	 * @param subAccountId
	 * @return
	 */
	R<AccountBalanceVO> selectFundSubAccountBalance(String subAccountId);

	/**
	 * 批量获取基金账号余额
	 * @param subAccountIds
	 * @return
	 */
	R<List<AccountBalanceVO>> selectFundSubAccountBalance(List<String> subAccountIds);

	/**
	 * 查询汇率
	 * @return
	 */
	R selectExchangeRate();

	/**
	 * 查询市值
	 * @param accountId
	 * @return
	 */
	R selectMarketValue(String accountId);

	/**
	 * 查询各币种利息
	 * @param accountId
	 * @return
	 */
	R accumulatedInterest(String accountId);

	/**
	 * 根据理财账号获取所有子账号账户金额
	 * @param accountId
	 * @return
	 */
	R  accountCapital(String accountId);

	/**
	 * 持仓资产信息
	 * @param accountId
	 * @param outCurrency
	 * @return
	 */
	R accountAssetAll(String accountId,String outCurrency);


	PageVO customerPositionList(String extacctid, Integer start, Integer count, Integer busiType);

	PageVO orderList(String extAccountId, Integer start, Integer count, Integer busiType, Integer status);

	PageVO customerHistoryOrder(String extacctid, Integer start, Integer count, Integer busiType);

	PageVO distributionRecords(String extAccountId, Integer start, Integer count, Integer busiType);

	List<Map<String,String>> selectConfirmation(String orderId);

	R<JSONObject> selectOrderInfo(String orderId);

}
