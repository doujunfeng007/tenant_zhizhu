package com.minigod.zero.platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.dto.OpenAccountDTO;
import com.minigod.zero.platform.engine.FundFactory;
import com.minigod.zero.platform.service.AccountService;
import com.minigod.zero.platform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/15 19:36
 * @description：账户相关处理实现
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private FundFactory fundFactory;

	@Override
	public R<FundAccountVO> fundOpenAccount(OpenAccountDTO fundOpenAccount) {
		FundAccountVO fundAccount = fundFactory.fundOpenAccount(fundOpenAccount,0);
		if (fundAccount == null){
			throw new ServiceException("开户失败！");
		}
		return R.data(fundAccount);
	}

	@Override
	public R updateFundAccount(OpenAccountDTO fundOpenAccount) {
		FundAccountVO fundAccount = fundFactory.fundOpenAccount(fundOpenAccount,1);
		if (fundAccount == null){
			throw new ServiceException("修改账户信息失败！");
		}
		return R.data(fundAccount);
	}

	@Override
	public R<AccountBalanceVO> selectFundSubAccountBalance(String subAccountId) {
		return R.data(fundFactory.getSubAccountBalance(subAccountId));
	}

	@Override
	public R<List<AccountBalanceVO>> selectFundSubAccountBalance(List<String> subAccountIds) {
		return R.data(fundFactory.getSubAccountBalance(subAccountIds));
	}

	@Override
	public R selectExchangeRate() {
		return R.data(fundFactory.selectExchangeRate());
	}

	@Override
	public R selectMarketValue(String accountId) {
		return R.data(fundFactory.selectMarketValue(accountId));
	}

	@Override
	public R accumulatedInterest(String accountId) {
		return R.data(fundFactory.accumulatedInterest(accountId));
	}

	@Override
	public R accountCapital(String accountId) {
		return R.data(fundFactory.accountCapital(accountId));
	}

	@Override
	public R accountAssetAll(String accountId, String outCurrency) {
		return R.data(fundFactory.accountAssetAll(accountId,outCurrency));
	}

	@Override
	public PageVO customerPositionList(String extacctid, Integer start, Integer count, Integer busiType) {
		return fundFactory.customerPositionList(extacctid, start, count, busiType);
	}

	@Override
	public PageVO orderList(String extAccountId, Integer start, Integer count, Integer busiType, Integer status) {
		return fundFactory.orderList(extAccountId, start, count, busiType, status);
	}

	@Override
	public PageVO customerHistoryOrder(String extacctid, Integer start, Integer count, Integer busiType) {
		return fundFactory.customerHistoryOrder(extacctid, start, count, busiType);
	}

	@Override
	public PageVO distributionRecords(String extAccountId, Integer start, Integer count, Integer busiType) {
		return fundFactory.distributionRecords(extAccountId, start, count, busiType);
	}

	@Override
	public List<Map<String, String>> selectConfirmation(String orderId) {
		return fundFactory.selectConfirmation(orderId);
	}

	@Override
	public R<JSONObject> selectOrderInfo(String orderId) {
		return fundFactory.selectOrderInfo(orderId);
	}
}
