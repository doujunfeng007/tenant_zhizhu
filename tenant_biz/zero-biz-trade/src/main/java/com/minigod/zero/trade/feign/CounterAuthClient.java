package com.minigod.zero.trade.feign;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.IAuthService;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;
import com.minigod.zero.trade.vo.req.ValidPwdVO;
import com.minigod.zero.trade.vo.request.BaseRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author:yanghu.luo
 * @create: 2023-04-26 10:19
 * @Description: 恒生登陆相关接口
 */
@Slf4j
@ApiIgnore()
@RestController
@AllArgsConstructor
public class CounterAuthClient implements ICounterAuthClient {

	private final IAuthService authService;


	@Override
	@PostMapping(ACCOUNT_LOGIN)
	public R<Object> accountLogin(BaseRequest baseRequest) {
		return authService.login(baseRequest);
	}

	@Override
	@PostMapping(VALID_PWD)
	public R<Object> validPwd( ValidPwdVO request) {
		return authService.validPwd(request);
	}

	@Override
	@PostMapping(RESET_TRADE_PWD)
	public R resetTradePwd(ResetTradePwdVO request) {
		return null;
	}

	@Override
	@PostMapping(MODIFY_TRADE_PWD)
	public R modifyPwd(ModifyPwdVO request) {
		return null;
	}
}
