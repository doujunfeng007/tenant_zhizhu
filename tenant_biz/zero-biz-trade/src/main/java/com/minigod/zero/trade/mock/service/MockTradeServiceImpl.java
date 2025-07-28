package com.minigod.zero.trade.mock.service;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_MOCK;

import com.minigod.zero.biz.common.mkt.vo.StrategyOrderVO;
import com.minigod.zero.trade.vo.sjmb.resp.OrderFeeVO;
import com.minigod.zero.trade.vo.strategy.CommonOrderVO;
import com.minigod.zero.trade.vo.strategy.CustStrategyOrderVO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.vo.request.BaseRequest;
import com.minigod.zero.trade.order.vo.request.CancelOrderRequest;
import com.minigod.zero.trade.order.vo.request.PlaceOrderRequest;
import com.minigod.zero.trade.order.vo.request.UpdateOrderRequest;
import com.minigod.zero.trade.service.ITradeService;

/**
 * @author chen
 * @ClassName MockTradeServiceImpl.java
 * @Description TODO
 * @createTime 2024年09月29日 10:42:00
 */
@Service
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_MOCK)
public class MockTradeServiceImpl implements ITradeService {
	@Override
	public R placeOrder(PlaceOrderRequest placeOrderRequest) {
		return null;
	}

	@Override
	public R placeStrategyOrder(CustStrategyOrderVO request) {
		return null;
	}

	@Override
	public R updateOrder(UpdateOrderRequest request) {
		return null;
	}

	@Override
	public R updateStrategyOrder(CustStrategyOrderVO request) {
		return null;
	}

	@Override
	public R cancelOrder(CancelOrderRequest request) {
		return null;
	}

	@Override
	public R cancelStrategyOrder(CustStrategyOrderVO request,boolean checkTradeAccount) {
		return null;
	}

	@Override
	public R getCommissionInfo(BaseRequest request) {
		return null;
	}

	@Override
	public R getOrderFee(OrderFeeVO request, boolean b) {
		return null;
	}

	@Override
	public R updateCommonOrder(CommonOrderVO request, boolean checkTradeAccount) {
		return null;
	}

	@Override
	public R triggerStrategyOrder(StrategyOrderVO request) {
		return null;
	}
}
