package com.minigod.zero.trade.mock.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.IAccountService;
import com.minigod.zero.trade.vo.sjmb.req.ModifyAccountReq;
import com.minigod.zero.trade.vo.sjmb.req.OpenAccountReq;
import com.minigod.zero.trade.vo.sjmb.resp.OpenAccountVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_MOCK;

/**
 * @author chen
 * @ClassName MockAccountServiceImpl.java
 * @Description TODO
 * @createTime 2024年09月27日 16:30:00
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_MOCK)
public class MockAccountServiceImpl implements IAccountService {
	@Override
	public R<OpenAccountVO> openAccount(OpenAccountReq openAccountReq,boolean isRepeat) {
		return null;
	}

	@Override
	public R updateAccount(ModifyAccountReq modifyAccountReq) {
		return null;
	}
}
