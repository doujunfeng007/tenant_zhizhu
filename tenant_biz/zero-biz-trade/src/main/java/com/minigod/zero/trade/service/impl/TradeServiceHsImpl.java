package com.minigod.zero.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.biz.common.constant.OpenApiConstant;
import com.minigod.zero.biz.common.constant.TradeCommonConstant;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.mkt.vo.StrategyOrderVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.cust.apivo.CustAccountVO;
import com.minigod.zero.cust.apivo.req.OrderVO;
import com.minigod.zero.trade.entity.CustOperationLogEntity;
import com.minigod.zero.trade.vo.request.BaseRequest;
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
import com.minigod.zero.trade.vo.sjmb.EntrustBsEnum;
import com.minigod.zero.trade.vo.sjmb.EntrustPropEnum;
import com.minigod.zero.trade.vo.sjmb.ExchangeTypeEnum;
import com.minigod.zero.trade.vo.sjmb.resp.OrderFeeVO;
import com.minigod.zero.trade.vo.strategy.CommonOrderVO;
import com.minigod.zero.trade.vo.strategy.CustStrategyOrderVO;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_HS;

/**
 * 交易服务
 */
@Slf4j
@Service
@AllArgsConstructor
@ConditionalOnProperty(prefix = "trade", name = "type", havingValue = MULTI_SERVER_TYPE_HS)
public class TradeServiceHsImpl implements ITradeService {

	@Resource
	private ICounterService counterService;

	@SneakyThrows
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
//		if (StringUtils.isBlank(request.getTradeAccount())) {
//			R result = custTradeClient.custCurrentAccount(AuthUtil.getCustId());
//			if (!result.isSuccess()) {
//				log.error("获取交易账号失败，custId：{}", AuthUtil.getCustId());
//				return R.fail("获取交易账号失败");
//			}
//			CustAccountVO custAccount = (CustAccountVO) result.getData();
//			request.setTradeAccount(custAccount.getTradeAccount());
//			request.setCapitalAccount(custAccount.getCapitalAccount());
//		}
//
//		Map<String, CommissionInfoVO> requestMap = zeroRedis.get(CacheNames.TRADE_COMMISSION.concat(request.getCapitalAccount()));
//		if (requestMap != null) {
//			return R.data(requestMap);
//		}
//		requestMap = new HashMap<>();
//		EF01100432Request req = new EF01100432Request();
//		req.setFunctionId(GrmFunctions.GET_ORDER_FEE);
//		req.setFundAccount(request.getCapitalAccount());
//		req.setExchangeType(HsConstants.HSExchageType.HK.getCode());
//		req.setStockType("0"); // 股票
//		req.setEntrustBs("1"); // 买入
//		req.setMoneyType("2"); // 港币
//		GrmResponseVO response = eF01100432.requestData(req);
//		if (!response.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
//			log.error("placeOrder getFeeList hk, error=" + JSON.toJSONString(response));
//		} else {
//			List<EF01100432VO> vos = (List<EF01100432VO>) response.getResult().get("data");
//			if (vos != null && vos.size() > 0) {
//				for (EF01100432VO vo : vos) {
//					// 佣金
//					if (vo.getFareType().equals("0")) {
//						CommissionInfoVO cvo = new CommissionInfoVO();
//						cvo.setBalanceRatio(vo.getFeeCount().toString());
//						cvo.setMinFare(vo.getMinFare().toString());
//						cvo.setMaxFare(vo.getMaxFare().toString());
//						cvo.setFeeCountFix(vo.getFeeCountFix().toString());
//						cvo.setFeeType(vo.getFeeType());
//						requestMap.put("hk", cvo);
//					}
//				}
//			}
//		}
//
//		req.setExchangeType(HsConstants.HSExchageType.US.getCode());
//		req.setMoneyType("1"); // 美元
//		response = eF01100432.requestData(req);
//		if (!response.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
//			log.error("placeOrder getFeeList us, error=" + JSON.toJSONString(response));
//		} else {
//			List<EF01100432VO> vos = (List<EF01100432VO>) response.getResult().get("data");
//			if (vos != null && vos.size() > 0) {
//				for (EF01100432VO vo : vos) {
//					// 佣金
//					if (vo.getFareType().equals("0")) {
//						CommissionInfoVO cvo = new CommissionInfoVO();
//						cvo.setBalanceRatio(vo.getFeeCount().toString());
//						cvo.setMinFare(vo.getMinFare().toString());
//						cvo.setMaxFare(vo.getMaxFare().toString());
//						cvo.setFeeCountFix(vo.getFeeCountFix().toString());
//						cvo.setFeeType(vo.getFeeType());
//						requestMap.put("us", cvo);
//					}
//				}
//			}
//		}
//		zeroRedis.setEx(CacheNames.TRADE_COMMISSION.concat(request.getCapitalAccount()), requestMap, CacheNames.TRADE_COMMISSION_EXPIRE_TIME);
//		return R.data(requestMap);
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
