package com.minigod.zero.platform.feign;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.dto.OpenAccountDTO;
import com.minigod.zero.platform.service.AccountService;
import com.minigod.zero.platform.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/15 19:33
 * @description：账户相关接口
 */
@RestController
public class AccountClient implements IAccountClient {

	@Autowired
	private AccountService accountService;

	/**
	 * 基金开户
	 * @param fundOpenAccount
	 * @return
	 */
	@Override
	@PostMapping(FUND_OPEN_ACCOUNT)
	public R<FundAccountVO> fundOpenAccount(@RequestBody OpenAccountDTO fundOpenAccount) {
		return accountService.fundOpenAccount(fundOpenAccount);
	}

	@Override
	@PutMapping(FUND_OPEN_ACCOUNT)
	public R<FundAccountVO> updateFundAccount(@RequestBody  OpenAccountDTO fundOpenAccount) {
		return accountService.updateFundAccount(fundOpenAccount);
	}

	/**
	 * 获取基金子账号余额
	 * @param subAccountId
	 * @return
	 */
	@Override
	@GetMapping(FUND_ACCOUNT_BALANCE_DETAIL)
	public R<AccountBalanceVO> selectFundSubAccountBalance(String subAccountId) {
		return accountService.selectFundSubAccountBalance(subAccountId);
	}
	/**
	 * 批量获取基金账号余额
	 * @param subAccountIds
	 * @return
	 */
	@Override
	@GetMapping(FUND_ACCOUNT_BALANCE)
	public R<List<AccountBalanceVO>> selectFundSubAccountBalance(List<String> subAccountIds) {
		return accountService.selectFundSubAccountBalance(subAccountIds);
	}

	/**
	 * 查询汇率
	 * @return
	 */
	@Override
	@GetMapping(EXCHANGE_RATE)
	public R selectExchangeRate() {
		return accountService.selectExchangeRate();
	}


	/**
	 * 查询持仓市值
	 * @param accountId
	 * @return
	 */
	@Override
	@GetMapping(MARKET_VALUE)
	public R selectMarketValue(@RequestParam("accountId")String accountId) {
		return accountService.selectMarketValue(accountId);
	}

	/**
	 * 查询各个币种累计利息
	 * @param accountId
	 * @return
	 */
	@Override
	public R<JSONObject> accumulatedInterest(String accountId) {
		return accountService.accumulatedInterest(accountId);
	}

	@Override
	public R<JSONObject> accountCapital(String accountId) {
		return accountService.accountCapital(accountId);
	}

	@Override
	public R<JSONObject> accountAssetAll(String accountId, String outCurrency) {
		return accountService.accountAssetAll(accountId, outCurrency);
	}

	@Override
	@GetMapping(CUSTOMER_POSITION_LIST)
	public R<PageVO> customerPositionList(String accountId, Integer pageNumber, Integer pageSize, Integer productType) {
		return R.data(accountService.customerPositionList(accountId, pageNumber, pageSize, productType));
	}

	@Override
	@GetMapping(ORDER_LIST)
	public R<PageVO> orderList(String accountId, Integer pageNumber, Integer pageSize, Integer productType, Integer status) {
		return R.data(accountService.orderList(accountId, pageNumber, pageSize, productType, status));
	}

	@Override
	@GetMapping(CUSTOMER_HISTORY_ORDER)
	public R<PageVO> customerHistoryOrder(String accountId, Integer pageNumber, Integer pageSize, Integer productType) {
		return R.data(accountService.customerHistoryOrder(accountId, pageNumber, pageSize, productType));
	}

	@Override
	@GetMapping(DISTRIBUTION_RECORDS)
	public R<PageVO> distributionRecords(String accountId, Integer pageNumber, Integer pageSize, Integer productType) {
		return R.data(accountService.distributionRecords(accountId, pageNumber, pageSize, productType));
	}

	@Override
	@GetMapping(SELECT_CONFIRMATION)
	public R<List<Map<String, String>>> selectConfirmation(String orderId) {
		return R.data(accountService.selectConfirmation(orderId));
	}

	@Override
	@GetMapping(OMS_ORDER_INFO)
	public R<JSONObject> selectOrderInfo(String orderId) {
		return accountService.selectOrderInfo(orderId);
	}

}
