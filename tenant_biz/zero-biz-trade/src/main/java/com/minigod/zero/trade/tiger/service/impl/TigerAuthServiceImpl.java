package com.minigod.zero.trade.tiger.service.impl;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.IAuthService;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;
import com.minigod.zero.trade.vo.req.ValidPwdVO;
import com.minigod.zero.trade.vo.request.BaseRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_TIGER;

@Service
@ConditionalOnProperty(value="trade.type",havingValue = MULTI_SERVER_TYPE_TIGER)
public class TigerAuthServiceImpl implements IAuthService {
	@Override
	public R getServerType(String request) {
		return null;
	}

	@Override
	public R<Object> login(BaseRequest request) {
		return R.success();
	}

	@Override
	public R switchToken(String request) {
		return null;
	}

	@Override
	public R validPwd(ValidPwdVO request) {
		return null;
	}

	@Override
	public R modifyPwd(ModifyPwdVO request) {
		return null;
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
