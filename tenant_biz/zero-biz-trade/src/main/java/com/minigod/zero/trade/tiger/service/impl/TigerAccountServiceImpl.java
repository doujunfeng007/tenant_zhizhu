package com.minigod.zero.trade.tiger.service.impl;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.IAccountService;
import com.minigod.zero.trade.vo.sjmb.req.ModifyAccountReq;
import com.minigod.zero.trade.vo.sjmb.req.OpenAccountReq;
import com.minigod.zero.trade.vo.sjmb.resp.OpenAccountVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_TIGER;


/**
 * @author chen
 * @date 2025/5/20 14:31
 * @description
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_TIGER)
public class TigerAccountServiceImpl implements IAccountService {
	@Override
	public R<OpenAccountVO> openAccount(OpenAccountReq openAccountReq, boolean isRepeat) {
		return null;
	}

	@Override
	public R updateAccount(ModifyAccountReq modifyAccountReq) {
		return null;
	}
}
