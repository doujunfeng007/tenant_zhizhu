package com.minigod.zero.trade.service.impl;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.ITradeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 委托订单服务
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "trade", name = "type", havingValue = "hs")
public class TradeOrderServiceHsImpl implements ITradeOrderService {

	@Override
	public R saveTradeOrder(String request) {
		return null;
	}

	@Override
	public R updateTradeOrder(String request) {
		return null;
	}

	@Override
	public R updateTradeStrategyOrder(String request) {
		return null;
	}

	@Override
	public R updateCancelTradeOrder(String request) {
		return null;
	}

	@Override
	public R findPage(String request) {
		return null;
	}

	@Override
	public R findList(String request) {
		return null;
	}

	@Override
	public R getLatestTradeOrderInfo(String request) {
		return null;
	}
}
