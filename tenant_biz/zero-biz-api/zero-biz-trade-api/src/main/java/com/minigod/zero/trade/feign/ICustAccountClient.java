package com.minigod.zero.trade.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.entity.FundAccountInfo;
import com.minigod.zero.trade.vo.sjmb.resp.FundQueryVO;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_TRADE_NAME
)
public interface ICustAccountClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/cust_account";
	String GET_DETAIL_ACCOUNT = API_PREFIX + "/get_detail_account";
	String GET_EXTRACTABLE_MONEY = API_PREFIX + "/get_extractable_money";

	String GET_CAPITALACCOUNT = API_PREFIX + "/get_capitalAccount";

	/**
	 *  获取资金账户信息
	 */
	@GetMapping(GET_DETAIL_ACCOUNT)
	R<FundQueryVO> getDetailAccount(@RequestParam Long custId, @RequestParam String fundAccount, @RequestParam(required = false) String moneyType);

	/**
	 * 获取用户资金信息
	 */
	@GetMapping(GET_EXTRACTABLE_MONEY)
	R<String> getExtractableMoney(@RequestParam String fundAccount, @RequestParam String moneyType);

	/**
	 * 获取资金账号信息
	 */
	@GetMapping(GET_CAPITALACCOUNT)
	R<List<FundAccountInfo>> getCapitalAccount(@RequestParam String tradeAccount);
}
