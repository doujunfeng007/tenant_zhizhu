package com.minigod.zero.trade.mock.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.vo.request.BaseRequest;
import com.minigod.zero.trade.service.IAuthService;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;
import com.minigod.zero.trade.vo.req.ValidPwdVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_MOCK;

/**
 * @author chen
 * @ClassName MockAuthServiceImpl.java
 * @Description TODO
 * @createTime 2024年09月27日 16:31:00
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_MOCK)
public class MockAuthServiceImpl implements IAuthService {
	@Override
	public R getServerType(String request) {
		return null;
	}

	@Override
	public R<Object> login(BaseRequest request) {
		return null;
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
