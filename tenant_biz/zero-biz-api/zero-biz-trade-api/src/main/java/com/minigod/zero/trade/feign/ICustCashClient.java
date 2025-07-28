package com.minigod.zero.trade.feign;

import com.minigod.zero.trade.vo.req.CashFrozenCancelVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.vo.req.CashDepositVO;
import com.minigod.zero.trade.vo.req.CashFrozenVO;
import com.minigod.zero.trade.vo.req.CashWithdrawVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author: Zhe.Xiao
 * @Description: 恒生出入金相关接口
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_TRADE_NAME
)
public interface ICustCashClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/cust_cash";
	String CASH_DEPOSIT = API_PREFIX + "/deposit_cash";
	String CASH_WITHDRAW = API_PREFIX + "/withdraw_cash";
	String CASH_FROZEN = API_PREFIX + "/cash_frozen";
	String CASH_FROZEN_CANCEL = API_PREFIX + "/cash_frozen_cancel";

	/**
	 * 存入资金
	 */
	@PostMapping(CASH_DEPOSIT)
	R<String> depositCash(@RequestBody CashDepositVO cashDepositVO);

	/**
	 * 取出资金
	 */
	@PostMapping(CASH_WITHDRAW)
	R<String> withdrawCash(@RequestBody CashWithdrawVO cashWithdrawVO);

	/**
	 * 资金冻结
	 */
	@PostMapping(CASH_FROZEN)
	R<Map<String, String>> cashFrozen(@RequestBody CashFrozenVO cashFrozenVO);

	/**
	 * 资金解冻
	 */
	@PostMapping(CASH_FROZEN_CANCEL)
	R<Map<String, String>> cashFrozenCancel(@RequestBody CashFrozenCancelVO cashFrozenCancelVO);

}
