package com.minigod.zero.trade.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.minigod.zero.core.cloud.feign.ZeroFeignRequestInterceptor;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;
import com.minigod.zero.trade.vo.req.ValidPwdVO;
import com.minigod.zero.trade.vo.request.BaseRequest;

/**
 * @author:yanghu.luo
 * @create: 2023-04-26 10:14
 * @Description: 恒生登陆相关接口
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_TRADE_NAME,
	configuration = ZeroFeignRequestInterceptor.class
)
public interface ICounterAuthClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/hs_auth";
	String ACCOUNT_LOGIN = API_PREFIX + "/account_login";
	String VALID_PWD = API_PREFIX + "/valid_pwd";
	String RESET_TRADE_PWD = API_PREFIX + "/reset_trade_pwd";
	String MODIFY_TRADE_PWD = API_PREFIX + "/modify_trade_pwd";



	/**
	 *  交易账号登录校验
	 */
	@PostMapping(ACCOUNT_LOGIN)
	R<Object> accountLogin(@RequestBody BaseRequest request);

	/**
	 *  交易账号登录校验
	 */
	@PostMapping(VALID_PWD)
	R<Object> validPwd(@RequestBody ValidPwdVO request);

	/**
	 *  交易密码重置
	 */
	@PostMapping(RESET_TRADE_PWD)
	R resetTradePwd(@RequestBody ResetTradePwdVO request);

	/**
	 *  交易密码修改
	 */
	@PostMapping(MODIFY_TRADE_PWD)
	R modifyPwd(@RequestBody ModifyPwdVO request);
}
