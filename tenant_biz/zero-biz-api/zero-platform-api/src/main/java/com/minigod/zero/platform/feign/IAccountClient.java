package com.minigod.zero.platform.feign;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.dto.OpenAccountDTO;
import com.minigod.zero.platform.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/15 19:19
 * @description：账号相关接口
 */
@FeignClient(value = AppConstant.APPLICATION_PLATFORM_NAME)
public interface IAccountClient {

	String FUND_OPEN_ACCOUNT = "/third_party/fund/open_account";

	String FUND_ACCOUNT_BALANCE = "/third_party/fund/account_balance";

	String FUND_ACCOUNT_BALANCE_DETAIL = "/third_party/fund/account_balance/{subAccountId}";

	String EXCHANGE_RATE = "/third_party/exchange_rate";

	String MARKET_VALUE = "/third_party/market_value";

	String ACCUMULATED_INTEREST = "/third_party/accumulated_interest";

	String ACCOUNT_CAPITAL = "/third_party/account/capital";

	String ACCOUNT_ASSET_ALL = "/third_party/account/asset_all";

	String CUSTOMER_POSITION_LIST = "/customer_position_list";

	String ORDER_LIST = "/order_list";

	String CUSTOMER_HISTORY_ORDER = "/customer_history_order";

	String DISTRIBUTION_RECORDS = "/distribution_records";

	String SELECT_CONFIRMATION = "/select_confirmation";
	String OMS_ORDER_INFO = "/oms/orderInfo";


	/**
	 * 基金开户
	 * @return
	 */
	@PostMapping(FUND_OPEN_ACCOUNT)
	R<FundAccountVO> fundOpenAccount(@RequestBody OpenAccountDTO fundOpenAccount);

	/**
	 * 更新基金账户信息AccountController
	 * @param fundOpenAccount
	 * @return
	 */
	@PutMapping(FUND_OPEN_ACCOUNT)
	R<FundAccountVO> updateFundAccount(@RequestBody OpenAccountDTO fundOpenAccount);
	/**
	 * 查询子账号余额
	 * @param subAccountId
	 * @return
	 */
	@GetMapping(FUND_ACCOUNT_BALANCE_DETAIL)
	R<AccountBalanceVO> selectFundSubAccountBalance(@PathVariable("subAccountId") String subAccountId);

	/**
	 * 批量查询子账号余额
	 * @param subAccountIds
	 * @return
	 */
	@GetMapping(FUND_ACCOUNT_BALANCE)
	R<List<AccountBalanceVO>> selectFundSubAccountBalance(@RequestParam List<String> subAccountIds);

	/**
	 *
	 * @return
	 */
	@GetMapping(EXCHANGE_RATE)
	R<JSONObject> selectExchangeRate();


	/**
	 * 查市值
	 * @param accountId
	 * @return
	 */
	@GetMapping(MARKET_VALUE)
	R<JSONObject> selectMarketValue(@RequestParam("accountId") String accountId);

	/**
	 * 查询各币种利息
	 * @param accountId
	 * @return
	 */
	@GetMapping(ACCUMULATED_INTEREST)
	R<JSONObject> accumulatedInterest(@RequestParam("accountId") String accountId);

	/**
	 * 根据理财账号查询下面所有交易账号金额
	 * @param accountId
	 * @return
	 */
	@GetMapping(ACCOUNT_CAPITAL)
	R<JSONObject> accountCapital(@RequestParam("accountId") String accountId);

	/**
	 * 持仓资产信息
	 * @param accountId
	 * @param outCurrency
	 * @return
	 */
	@GetMapping(ACCOUNT_ASSET_ALL)
	R<JSONObject> accountAssetAll(@RequestParam("accountId") String accountId,@RequestParam("outCurrency")String outCurrency);


	@GetMapping(CUSTOMER_POSITION_LIST)
	R<PageVO> customerPositionList(@RequestParam("accountId")String accountId, @RequestParam("pageNumber")Integer pageNumber,
															 @RequestParam("pageSize")Integer pageSize, @RequestParam("productType")Integer productType);

	@GetMapping(ORDER_LIST)
	R<PageVO> orderList(@RequestParam("accountId")String accountId, @RequestParam("pageNumber")Integer pageNumber, @RequestParam("pageSize")Integer pageSize,
										@RequestParam("productType")Integer productType, @RequestParam("status")Integer status);

	@GetMapping(CUSTOMER_HISTORY_ORDER)
	R<PageVO> customerHistoryOrder(@RequestParam("accountId")String accountId, @RequestParam("pageNumber")Integer pageNumber,
														  @RequestParam("pageSize")Integer pageSize, @RequestParam("productType")Integer productType);

	@GetMapping(DISTRIBUTION_RECORDS)
	R<PageVO> distributionRecords(@RequestParam("accountId")String accountId, @RequestParam("pageNumber")Integer pageNumber,
															  @RequestParam("pageSize")Integer pageSize, @RequestParam("productType")Integer productType);

	@GetMapping(SELECT_CONFIRMATION)
	R<List<Map<String,String>>> selectConfirmation(@RequestParam("orderId")String orderId);


	/**
	 * 根据订单号查询订单详情
	 * @param orderId
	 * @return
	 */
	@GetMapping(OMS_ORDER_INFO)
	R<JSONObject> selectOrderInfo(@RequestParam("orderId")String orderId);
}
