package com.minigod.zero.trade.service.impl;

import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.constant.TradeCommonConstant;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.utils.RSANewUtil;
import com.minigod.zero.trade.vo.request.BaseRequest;
import com.minigod.zero.trade.hs.service.EF01100200;
import com.minigod.zero.trade.hs.service.EF01100201;
import com.minigod.zero.trade.hs.service.EF01110018;
import com.minigod.zero.trade.hs.service.EF01281018;
import com.minigod.zero.trade.service.IAuthService;
import com.minigod.zero.trade.sjmb.common.MessageCode;
import com.minigod.zero.trade.sjmb.common.SjmbFunctionsUrlEnum;
import com.minigod.zero.trade.sjmb.req.brokerapi.ResetPasswordReq;
import com.minigod.zero.trade.sjmb.req.openapi.LoginPasswordModReq;
import com.minigod.zero.trade.sjmb.req.openapi.LoginReq;
import com.minigod.zero.trade.sjmb.resp.LoginResp;
import com.minigod.zero.trade.sjmb.resp.SjmbResponse;
import com.minigod.zero.trade.sjmb.service.ICounterService;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;
import com.minigod.zero.trade.vo.req.ValidPwdVO;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_HS;

/**
 * 登录权限服务
 */
@Slf4j
@Service
@AllArgsConstructor
@ConditionalOnProperty(prefix = "trade", name = "type", havingValue = MULTI_SERVER_TYPE_HS)
public class AuthServiceHsImpl extends CommonService implements IAuthService {
	private final EF01100200 eF01100200;
	private final EF01281018 eF01281018;
	private final EF01110018 eF01110018;
	private final EF01100201 ef01100201;


	@Resource
	private ICounterService counterService;

	@Resource
	private ZeroRedis zeroRedis;
	@Override
	public R getServerType(String request) {
		return null;
	}

	@Override
	@SneakyThrows
	public R<Object> login(BaseRequest request) {
		LoginReq loginReq =new LoginReq();
		loginReq.setAccountId(request.getTradeAccount());
		loginReq.setPassword(RSANewUtil.decrypt(request.getPassword()));
//		loginReq.setPassword(request.getPassword());
		loginReq.setTfaType("NONE");
		SjmbResponse response =counterService.openApiSend(loginReq, SjmbFunctionsUrlEnum.CLIENT_LOGIN);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {

            if(MessageCode.NEED_LOGIN.getCode().equals(response.getCode())){
				return R.fail(TradeCommonConstant.TRADE_NOT_LOGIN, response.getMsg());
			}else{
				return R.fail(response.getMsg());
			}
		}
		/**
		 * 成功后将token 存redis
		 */
		LoginResp lginResp = JSONUtil.fromJson(response.getData().toString(), LoginResp.class);
		zeroRedis.hSet(CacheNames.TRADE_TOKEN, lginResp.getAccountId(),lginResp.getToken());
		return R.success();
	}

	@Override
	public R switchToken(String request) {
		return null;
	}

	@Override
	public R validPwd(ValidPwdVO request) {
		ResetPasswordReq req =new ResetPasswordReq();
		req.setAccountId(request.getTradeAccount());
		req.setNewPassword(RSANewUtil.decrypt(request.getPassword()));

		SjmbResponse response = counterService.brokerApiSend(request, SjmbFunctionsUrlEnum.ACCOUNT_PASSWORD_RESET, HttpMethod.POST);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		}
		return R.success();
	}

	@Override
	public R modifyPwd(ModifyPwdVO request) {
		LoginPasswordModReq loginPasswordModReq =new LoginPasswordModReq();

		loginPasswordModReq.setAccountId(request.getTradeAccount());
		loginPasswordModReq.setOldPassword(RSANewUtil.decrypt(request.getPassword()));
		loginPasswordModReq.setNewPassword(RSANewUtil.decrypt(request.getNewPassword()));

		SjmbResponse response =counterService.openApiSend(loginPasswordModReq, SjmbFunctionsUrlEnum.LOGIN_PAWWWORD_MOD);

		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			if(MessageCode.NEED_LOGIN.getCode().equals(response.getCode())){
				return R.fail(TradeCommonConstant.TRADE_NOT_LOGIN, response.getMsg());
			}else{
				return R.fail(response.getMsg());
			}
		}
		return R.success();
	}

	@Override
	public R resetPwd(ResetTradePwdVO request) {
		return null;
	}

	@Override
	public R getRiskLevel(String request) {
		return null;
	}
}
