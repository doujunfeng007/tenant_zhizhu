package com.minigod.zero.cust.feign;

import com.minigod.zero.cust.apivo.CustAccountVO;
import com.minigod.zero.cust.apivo.CustTradePushInfoVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 交易服务接口
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_CUST_NAME
)
public interface ICustTradeClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/trade";
	String CUST_CURRENT_ACCT = API_PREFIX + "/cust_current_account";
	String GET_CUST_TRADE_PUSH_INFO = API_PREFIX + "/get_cust_trade_push_info";
	String CHECK_OLD_TRADE_PWD = API_PREFIX + "/check_old_trade_pwd";
	String ACTIVE_TRADE_PWD = API_PREFIX + "/active_trade_pwd";
	String VALID_PWD = API_PREFIX + "/valid_pwd";
	String CHECK_OLD_PWD = API_PREFIX + "/check_old_pwd";

	@PostMapping(CUST_CURRENT_ACCT)
	R<CustAccountVO> custCurrentAccount(@RequestParam("custId") Long custId);

	@PostMapping(GET_CUST_TRADE_PUSH_INFO)
	R<CustTradePushInfoVO> getCustTradePushInfo(@RequestParam("capitalAccount") String capitalAccount);

	@GetMapping(CHECK_OLD_TRADE_PWD)
	R checkOldTradePwd(@RequestParam("tradeAccount") String tradeAccount, @RequestParam("password") String password);

	/**
	 * 旧密码验证码通过后激活
	 */
	@PostMapping(ACTIVE_TRADE_PWD)
	R activeTradePwd(@RequestParam("custId") Long custId, @RequestParam("tradeAccount") String tradeAccount);

	/**
	 * 校验交易密码
	 */
	@PostMapping(VALID_PWD)
	R validPwd(@RequestParam("password") String password, @RequestParam("tradeAccount") String tradeAccount);

	@PostMapping(CHECK_OLD_PWD)
	R checkOldPwd(@RequestParam("tradeAccount") String tradeAccount, @RequestParam("password") String password);


}
