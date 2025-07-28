package com.minigod.zero.trade.sjmb.service.impl;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_SJMB;

import javax.annotation.Resource;

import com.minigod.zero.biz.common.mkt.vo.StrategyOrderVO;
import com.minigod.zero.trade.vo.sjmb.resp.OrderFeeVO;
import com.minigod.zero.trade.vo.strategy.CommonOrderVO;
import com.minigod.zero.trade.vo.strategy.CustStrategyOrderVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.minigod.zero.biz.common.constant.TradeCommonConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.order.vo.request.CancelOrderRequest;
import com.minigod.zero.trade.order.vo.request.PlaceOrderRequest;
import com.minigod.zero.trade.order.vo.request.UpdateOrderRequest;
import com.minigod.zero.trade.service.ITradeService;
import com.minigod.zero.trade.sjmb.common.MessageCode;
import com.minigod.zero.trade.sjmb.common.SjmbFunctionsUrlEnum;
import com.minigod.zero.trade.sjmb.req.openapi.OrderCancelReq;
import com.minigod.zero.trade.sjmb.req.openapi.OrderPlaceReq;
import com.minigod.zero.trade.sjmb.req.openapi.OrderReplaceReq;
import com.minigod.zero.trade.sjmb.resp.SjmbResponse;
import com.minigod.zero.trade.sjmb.service.ICounterService;
import com.minigod.zero.trade.utils.SjmbUtil;
import com.minigod.zero.trade.vo.request.BaseRequest;
import com.minigod.zero.trade.vo.sjmb.EntrustBsEnum;
import com.minigod.zero.trade.vo.sjmb.EntrustPropEnum;
import com.minigod.zero.trade.vo.sjmb.ExchangeTypeEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @ClassName SjmbTradeServiceImpl.java
 * @Description TODO
 * @createTime 2024年04月13日 17:37:00
 */

@Slf4j
@Service
@ConditionalOnProperty(value="trade.type",havingValue = MULTI_SERVER_TYPE_SJMB)
@RequiredArgsConstructor
public class SjmbTradeServiceImpl implements ITradeService {

	@Resource
	private ICounterService counterService;

	@Override
	public R placeOrder(PlaceOrderRequest placeOrderRequest) {
		OrderPlaceReq orderPlaceReq = new OrderPlaceReq();
		orderPlaceReq.setAccountId(placeOrderRequest.getTradeAccount());
		orderPlaceReq.setSecurityType("EQTY"); //股票
		orderPlaceReq.setExchange(ExchangeTypeEnum.getCounterExchangeType(placeOrderRequest.getExchangeType()));
		if (ExchangeTypeEnum.HK.getExchangeType().equals(placeOrderRequest.getExchangeType())) {
			orderPlaceReq.setSymbol(StringUtils.leftPad(placeOrderRequest.getStockCode(), 5, "0"));
		} else {
			orderPlaceReq.setSymbol(placeOrderRequest.getStockCode());
		}
		orderPlaceReq.setOrderType(EntrustPropEnum.getCounterEntrustProp(placeOrderRequest.getEntrustProp()));
		orderPlaceReq.setSide(EntrustBsEnum.getCounterEntrustBs(placeOrderRequest.getBsFlag()));
		orderPlaceReq.setTimeInForce("DAY");
		orderPlaceReq.setQuantity(SjmbUtil.getMapParamsByStr(placeOrderRequest.getQty()));
		orderPlaceReq.setPrice(SjmbUtil.getMapParamsByStr(placeOrderRequest.getPrice()));

		SjmbResponse response = counterService.openApiSend(orderPlaceReq, SjmbFunctionsUrlEnum.ORDER_PLACE);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			if (MessageCode.NEED_LOGIN.getCode().equals(response.getCode())) {
				return R.fail(TradeCommonConstant.TRADE_NOT_LOGIN, response.getMsg());
			} else {
				return R.fail(response.getMsg());
			}
		}
		return R.success();
	}

	@Override
	public R placeStrategyOrder(CustStrategyOrderVO request) {
		return null;
	}

	@Override
	public R updateOrder(UpdateOrderRequest request) {
		OrderReplaceReq orderReplaceReq = new OrderReplaceReq();
		orderReplaceReq.setOrderId(request.getOrderNo());
		orderReplaceReq.setQuantity(SjmbUtil.getMapParamsByStr(request.getQty()));
		orderReplaceReq.setPrice(SjmbUtil.getMapParamsByStr(request.getPrice()));
		orderReplaceReq.setAccountId(request.getTradeAccount());
		SjmbResponse response = counterService.openApiSend(orderReplaceReq, SjmbFunctionsUrlEnum.ORDER_REPLACE);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			if (MessageCode.NEED_LOGIN.getCode().equals(response.getCode())) {
				return R.fail(TradeCommonConstant.TRADE_NOT_LOGIN, response.getMsg());
			} else {
				return R.fail(response.getMsg());
			}
		}
		return R.success();
	}

	@Override
	public R updateStrategyOrder(CustStrategyOrderVO request) {
		return null;
	}

	@Override
	public R cancelOrder(CancelOrderRequest request) {
		OrderCancelReq orderCancelReq = new OrderCancelReq();
		orderCancelReq.setOrderId(request.getOrderNo());
		orderCancelReq.setAccountId(request.getTradeAccount());
		SjmbResponse response = counterService.openApiSend(orderCancelReq, SjmbFunctionsUrlEnum.ORDER_CANCEL);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			if (MessageCode.NEED_LOGIN.getCode().equals(response.getCode())) {
				return R.fail(TradeCommonConstant.TRADE_NOT_LOGIN, response.getMsg());
			} else {
				return R.fail(response.getMsg());
			}
		}
		return R.success();
	}

	@Override
	public R cancelStrategyOrder(CustStrategyOrderVO request,boolean checkTradeAccount) {
		return null;
	}

	@Override
	public R getCommissionInfo(BaseRequest request) {
		return R.success();
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
