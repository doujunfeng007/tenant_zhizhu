package com.minigod.zero.cust.feign;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.apivo.AccountBalanceVO;
import com.minigod.zero.cust.apivo.CustomerDetailVO;
import com.minigod.zero.cust.apivo.req.AmountVO;
import com.minigod.zero.cust.apivo.req.CheckClientPwdReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * minigod-customer账户中心
 *
 * @author eric
 * @since 2024-05-22 22:02:09
 */
@FeignClient(value = "minigod-customer")
public interface ICustAccountClient {

	/**
	 * 获取账户余额
	 */
	String CUST_ACCOUNT_BALANCE = "/account_balance/detail";

	/**
	 * 校验交易密码
	 */
	String CHECK_TRAD_PWD = "/check_trade_pwd_for_inside";

	/**
	 * 获取客户详情
	 */
	String CUST_ACCOUNT_DETAIL = "/detail";
	/**
	 * 划扣金额
	 */
	String SCRATCH_BUTTON = "/fund/scratch_button";

	/**
	 * 根据用户交易账号和币种获取账户余额
	 *
	 * @param currency
	 * @param accountId
	 * @return
	 */
	@GetMapping(CUST_ACCOUNT_BALANCE)
	R<AccountBalanceVO> selectAccountBalance(@RequestParam("currency") String currency, @RequestParam("accountId") String accountId);

	/**
	 * 校验交易密码
	 *
	 * @param checkClientPwdReq
	 * @return
	 */
	@PostMapping(CHECK_TRAD_PWD)
	R checkTradPwd(@RequestBody CheckClientPwdReq checkClientPwdReq);

	/**
	 * 获取客户详情
	 *
	 * @param custId
	 * @return
	 */
	@GetMapping(CUST_ACCOUNT_DETAIL)
	R<CustomerDetailVO> selectCustomerDetail(@RequestParam("custId") Long custId);

	/**
	 * 划扣金额
	 *
	 * @param amountVO
	 * @return
	 */
	@PostMapping(SCRATCH_BUTTON)
	public R scratchButton(@RequestBody AmountVO amountVO);
}
