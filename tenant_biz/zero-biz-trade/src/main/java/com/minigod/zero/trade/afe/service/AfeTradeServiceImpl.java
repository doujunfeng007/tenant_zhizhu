package com.minigod.zero.trade.afe.service;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.cache.PositionQuotationVO;
import com.minigod.zero.biz.common.constant.OpenApiConstant;
import com.minigod.zero.biz.common.enums.TradeEnum;
import com.minigod.zero.biz.common.mkt.vo.PositionQuotationLVVO;
import com.minigod.zero.biz.common.mkt.vo.StrategyOrderVO;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.core.tool.enums.EMarket;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.req.OrderVO;
import com.minigod.zero.trade.afe.common.AfeInterfaceTypeEnum;
import com.minigod.zero.trade.afe.config.WebSocketClientService;
import com.minigod.zero.trade.afe.req.OrderAmendmentRequest;
import com.minigod.zero.trade.afe.req.OrderCancellationRequest;
import com.minigod.zero.trade.afe.req.OrderPlacementRequest;
import com.minigod.zero.trade.common.ServerConstant;
import com.minigod.zero.trade.entity.CustStrategyOrderEntity;
import com.minigod.zero.trade.order.vo.request.CancelOrderRequest;
import com.minigod.zero.trade.order.vo.request.PlaceOrderRequest;
import com.minigod.zero.trade.order.vo.request.UpdateOrderRequest;
import com.minigod.zero.trade.service.IAuthService;
import com.minigod.zero.trade.service.ICustStrategyOrderService;
import com.minigod.zero.trade.service.ITradeService;
import com.minigod.zero.trade.utils.HSUtil;
import com.minigod.zero.trade.utils.MarketUtils;
import com.minigod.zero.trade.vo.afe.AfeEntrustBsEnum;
import com.minigod.zero.trade.vo.afe.AfeEntrustPropEnum;
import com.minigod.zero.trade.vo.afe.AfeExchangeTypeEnum;
import com.minigod.zero.trade.vo.request.BaseRequest;
import com.minigod.zero.trade.vo.sjmb.resp.OrderFeeVO;
import com.minigod.zero.trade.vo.strategy.CommonOrderVO;
import com.minigod.zero.trade.vo.strategy.CustStrategyOrderVO;
import com.minigod.zero.trade.vo.strategy.FeeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_AFE;

/**
 * @author chen
 * @ClassName AfeTradeServiceImpl.java
 * @Description TODO
 * @createTime 2024年04月13日 17:44:00
 */
@Service
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_AFE)
@Slf4j
public class AfeTradeServiceImpl implements ITradeService {

	@Autowired
	private WebSocketClientService webSocketClientService;

	@Autowired
	private RestTemplateUtil restTemplateUtil;

	@Resource
	private ZeroRedis zeroRedis;
	@Resource
	private ICustStrategyOrderService custStrategyOrderService;

	@Autowired
	private RedisLockClient redisLockClient;

	@Autowired
	private IAuthService authService;

	@Override
	public R placeOrder(PlaceOrderRequest placeOrderRequest) {

		OrderPlacementRequest request = new OrderPlacementRequest();
		request.setClientId(placeOrderRequest.getTradeAccount());
		request.setMarket(AfeExchangeTypeEnum.getCounterExchangeType(placeOrderRequest.getExchangeType()));
		request.setMatchingType(AfeEntrustPropEnum.getCounterEntrustProp(placeOrderRequest.getEntrustProp()));
		request.setBidAskType(AfeEntrustBsEnum.getCounterEntrustBs(placeOrderRequest.getBsFlag()));
		request.setInstrumentNo(placeOrderRequest.getStockCode());
		request.setPrice(placeOrderRequest.getPrice());
		request.setQuantity(placeOrderRequest.getQty());
		if(AfeEntrustPropEnum.MO.getEntrustProp().equals(placeOrderRequest.getEntrustProp())){
			request.setPrice(null);
		}
		R result = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.ORDER_PLACEMENT.getRequestType(), request,
			placeOrderRequest.getTradeAccount());
		return result;
	}

	@Override
	public R placeStrategyOrder(CustStrategyOrderVO request) {
		// 缓存订单确认记录，只有确认过订单，下单接口才能下单(普通下单和条件单)，下单成功后缓存失效，即下单接口必须是确认好的，并且不能确认一次重复下单
		Boolean ack = zeroRedis.get(CacheNames.TRADE_PLACE_ORDER_ACK.concat(AuthUtil.getTenantCustId().toString()));
		if (ack == null) {
			return R.fail("订单未确认或时间超时失效");
		} else {
			zeroRedis.del(CacheNames.TRADE_PLACE_ORDER_ACK.concat(AuthUtil.getTenantCustId().toString()));
		}

		List<String> list = new ArrayList<>();
		list.add(request.getAssetId());
		PositionQuotationLVVO pqlv = new PositionQuotationLVVO();
		pqlv.setCustId(AuthUtil.getTenantCustId());
		pqlv.setTenantId("place_order");
		pqlv.setTerminalId(2);
		pqlv.setClientId(AuthUtil.getClientId());
		pqlv.setIp(WebUtil.getIP());
		pqlv.setAssetIds(list);
		Map<String, Object> quotationMap = (Map<String, Object>) restTemplateUtil.postSend(OpenApiConstant.GET_QUOTATION, HashMap.class, pqlv);
		if (quotationMap.get(request.getAssetId()) != null) {
			PositionQuotationVO quotation = JSON.parseObject(JSON.toJSONString(quotationMap.get(request.getAssetId())), PositionQuotationVO.class);
			if(request.getStrategyPrice().compareTo(quotation.getPrice()) < 0){
				request.setStrategyAction(2);
			}else{
				request.setStrategyAction(1);
			}
		} else {
			request.setStrategyAction(1);
		}
		StkInfo stkInfo = zeroRedis.protoGet(request.getAssetId(), StkInfo.class);
		if (stkInfo != null) {
			request.setStockName(HSUtil.getStockName(stkInfo, WebUtil.getLanguage(), stkInfo.getStkName()));
		}
		request.setCreateTime(new Date());
		request.setTradeAccount(request.getTradeAccount());

		// 正常订单0，允许盘前盘后1或3
		if (request.getSessionType() != null && request.getSessionType() == 0) {
			request.setDiscType(0);
		} else {
			request.setDiscType(1);
		}

		R<String> r = custStrategyOrderService.placeStrategyOrder(request);
		if (r.isSuccess()) {
			request.setEntrustNo(r.getData());
			StrategyOrderVO mktVO = new StrategyOrderVO();
			mktVO.setAssetId(request.getAssetId());
			mktVO.setStrategyPrice(request.getStrategyPrice().toString());
			mktVO.setStrategyAction(request.getStrategyAction().toString());
			mktVO.setDeadlineDate(request.getDeadlineDate().toString());
			mktVO.setEntrustNo(request.getEntrustNo());
			mktVO.setExpSeconds(0L);
			mktVO.setEntrustStatus("C0");
			mktVO.setCreateTime(DateUtil.format(request.getCreateTime(), "yyyyMMddHHmmss"));
			mktVO.setDiscType(request.getDiscType().toString());
			mktVO.setTenantId(AuthUtil.getTenantId());
			mktVO.setOrderNo(r.getData());
			Long expSeconds = restTemplateUtil.postSend(OpenApiConstant.STRATEGY_ORDER_PLACE, Long.class, mktVO);
			// 条件单提交监控失败
			if (expSeconds == null) {
				custStrategyOrderService.errorStrategyOrder(Long.valueOf(r.getData()), "条件单提交监控失败", true);
				return R.fail("条件单提交监控失败");
			}
			custStrategyOrderService.updateStrategyOrderExpirationTime(Long.valueOf(r.getData()), expSeconds);

			return R.data(Kv.create().set("entrustNo", r.getData()));
		}
		return R.fail("条件单下单失败");
	}

	@Override
	public R updateOrder(UpdateOrderRequest updateOrderRequest) {
		OrderAmendmentRequest request = new OrderAmendmentRequest();
		request.setClientId(updateOrderRequest.getTradeAccount());
		request.setOrderNo(updateOrderRequest.getOrderNo());
		request.setPrice(updateOrderRequest.getPrice());
		request.setQuantity(updateOrderRequest.getQty());
		R result = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.ORDER_AMENDMENT.getRequestType(), request,
			updateOrderRequest.getTradeAccount());
		return result;
	}

	@Override
	public R updateStrategyOrder(CustStrategyOrderVO request) {
		request.setTradeAccount(request.getTradeAccount());

		request.setEntrustTime(null);
		R r = custStrategyOrderService.updateStrategyOrder(request);
		if (r.isSuccess()) {
			// 下单的时候不能拿到柜台订单编号,由用户触发改单
			if (r.getData() != null && "C3".equals(r.getData())) {
				return R.fail("条件单已触发，请前往今日订单中修改订单信息");
			} else {
				Map<String, String> parame = new HashMap<>();
				parame.put("assetId", request.getAssetId());
				parame.put("entrustNo", request.getOrderNo());
				parame.put("strategyPrice", request.getStrategyPrice().toString());
				restTemplateUtil.postSend(OpenApiConstant.STRATEGY_ORDER_UPDATE, Boolean.class, parame);
			}
		}

		return R.data(Kv.create().set("entrustNo", request.getOrderNo()));
	}

	@Override
	public R cancelOrder(CancelOrderRequest cancelOrderRequest) {
		OrderCancellationRequest request = new OrderCancellationRequest();
		request.setClientId(cancelOrderRequest.getTradeAccount());
		request.setOrderNo(cancelOrderRequest.getOrderNo());
		R result = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.ORDER_CANCELLATION.getRequestType(), request,
			cancelOrderRequest.getTradeAccount());
		return result;
	}

	@Override
	public R cancelStrategyOrder(CustStrategyOrderVO request, boolean checkTradeAccount) {
		request.setTradeAccount(request.getTradeAccount());

		R r = custStrategyOrderService.cancelStrategyOrder(request);
		if (r.isSuccess()) {
			// 条件单已触发，调用恒生撤单
			if (r.getData() != null && "C3".equals(r.getData())) {
				log.info("条件单已触发，调用柜台撤单");
				return R.fail("条件单已触发，请前往今日订单中撤单");
			} else {
				StrategyOrderVO mktVO = new StrategyOrderVO();
				mktVO.setEntrustNo(request.getOrderNo());
				mktVO.setAssetId(request.getAssetId());
				restTemplateUtil.postSend(OpenApiConstant.STRATEGY_ORDER_CANCEL, Boolean.class, mktVO);
			}
		}
		return R.data(Kv.create().set("entrustNo", request.getOrderNo()));
	}

	@Override
	public R getCommissionInfo(BaseRequest request) {
		return null;
	}

	@Override
	public R getOrderFee(OrderFeeVO request, boolean checkTradeAccount) {
		FeeVO feeVO = new FeeVO();
		// 设置默认值
		feeVO.setFee(BigDecimal.ZERO);
		feeVO.setFeeRatio("0");
		feeVO.setFeeType(0);
		feeVO.setMinFare("0");

		// 港股 w-市价单，d-竞价单；美股 w-市价单，由柜台控制价格,前端不传值，获取最新价计算费用
		if ((request.getAssetId().endsWith(EMarket.HK.toString()) &&
			(request.getEntrustProp().equals(AfeEntrustPropEnum.MO.getEntrustProp()) ||
				request.getEntrustProp().equals(AfeEntrustPropEnum.AO.getEntrustProp())))
			|| (request.getAssetId().endsWith(EMarket.US.toString())
			&& (request.getEntrustProp().equals(TradeEnum.EntrustProp.USW.getCode())))) {
			List<String> list = new ArrayList<>();
			list.add(request.getAssetId());
			// 获取行情数据
			PositionQuotationLVVO pqlv = new PositionQuotationLVVO();
			pqlv.setCustId(AuthUtil.getCustId());
			pqlv.setTenantId("place_order");
			pqlv.setTerminalId(2);
			pqlv.setClientId(AuthUtil.getClientId());
			pqlv.setIp(WebUtil.getIP());
			pqlv.setAssetIds(list);
			Map<String, Object> quotationMap = (Map<String, Object>) restTemplateUtil.postSend(OpenApiConstant.GET_QUOTATION, HashMap.class, pqlv);
			if (quotationMap.get(request.getAssetId()) != null) {
				PositionQuotationVO quotation = JSON.parseObject(JSON.toJSONString(quotationMap.get(request.getAssetId())), PositionQuotationVO.class);
				request.setEntrustPrice(quotation.getPrice().toString());
			} else {
				request.setEntrustPrice("0");
			}
		}
		// 缓存订单确认记录，只有确认过订单，下单接口才能下单(普通下单和条件单)，下单成功后缓存失效，即下单接口必须是确认好的，并且不能确认一次重复下单
		zeroRedis.setEx(CacheNames.TRADE_PLACE_ORDER_ACK.concat(AuthUtil.getTenantCustId().toString()), true, CacheNames.TRADE_PLACE_ORDER_ACK_EXPIRE_TIME);
		return R.data(feeVO);
	}

	@Override
	public R updateCommonOrder(CommonOrderVO request, boolean checkTradeAccount) {
		return null;
	}

	@Override
	public R triggerStrategyOrder(StrategyOrderVO request) {
		String key = ServerConstant.TRADE_TRIGGER_STRATEGY_LOCK.concat(request.getOrderNo());
		try {
			if (redisLockClient.tryLock(key, LockType.REENTRANT, ServerConstant.LOCK_MILLS, ServerConstant.LOCK_MILLS, TimeUnit.MILLISECONDS)) {
				String entrustNo = zeroRedis.get(CacheNames.TRADE_TRIGGER_STRATEGY_ORDER.concat(request.getOrderNo()));
				if (StringUtils.isNotBlank(entrustNo)) {
					log.warn("条件单已被触发过：{}", request);
					return R.fail("条件单已被触发过");
				}
				zeroRedis.setEx(CacheNames.TRADE_TRIGGER_STRATEGY_ORDER.concat(request.getOrderNo()), request.getOrderNo(), CacheNames.TRADE_TRIGGER_STRATEGY_ORDER_EXPIRE_TIME);

				CustStrategyOrderVO cso = new CustStrategyOrderVO();
				cso.setOrderNo(request.getOrderNo());
				cso.setEntrustStatus(request.getEntrustStatus());
				R<CustStrategyOrderEntity> r = custStrategyOrderService.triggerStrategyOrder(cso);
				if (r.isSuccess()) {
					// 条件单触发
					CustStrategyOrderEntity custStrategyOrder = r.getData();

//					R result =authService.agentLogin();
					// 下单
					OrderPlacementRequest orderPlacementRequest = new OrderPlacementRequest();
					orderPlacementRequest.setClientId(custStrategyOrder.getTradeAccount());
					orderPlacementRequest.setMarket(AfeExchangeTypeEnum.getCounterExchangeType(custStrategyOrder.getExchangeType()));
					orderPlacementRequest.setMatchingType(AfeEntrustPropEnum.getCounterEntrustProp(custStrategyOrder.getEntrustProp()));
					orderPlacementRequest.setBidAskType(AfeEntrustBsEnum.getCounterEntrustBs(custStrategyOrder.getEntrustBs()));
					orderPlacementRequest.setInstrumentNo(MarketUtils.extractStockCode(custStrategyOrder.getAssetId()));
					orderPlacementRequest.setPrice(custStrategyOrder.getStrategyPrice().toString());
					orderPlacementRequest.setQuantity(custStrategyOrder.getEntrustAmount().toString());
					if(AfeEntrustPropEnum.MO.getEntrustProp().equals(custStrategyOrder.getEntrustProp())){
						orderPlacementRequest.setPrice(null);
					}
					R res = webSocketClientService.sendAgentSyncMsg(AfeInterfaceTypeEnum.ORDER_PLACEMENT.getRequestType(), orderPlacementRequest,
						custStrategyOrder.getTradeAccount());
					log.info("条件单触发下单：{}", custStrategyOrder);
					log.info("条件单触发下单结果：{}", res);
					if (!res.isSuccess()) {
						log.error("条件单下单错误:{}", res);
						custStrategyOrderService.errorStrategyOrder(custStrategyOrder.getId(), res.getMsg(), false);
						return R.fail();
					}
					cso.setId(custStrategyOrder.getId());
					cso.setEntrustTime(new Date());
//							cso.setEntrustNo(vo.getEntrustNo());
					// 更新委托编号和下单时间
					if (r.isSuccess()) {
						R result = custStrategyOrderService.updateStrategyOrder(cso);
						if (!result.isSuccess()) {
							log.error("条件单更新委托编号失败，条件单内容：{}", request);
						}
					}
					return R.data(true);
				}
			}
		} catch (Exception e) {
			log.error("条件单触发处理错误", e);
		} finally {
			redisLockClient.unLock(key, LockType.REENTRANT);
		}
		return R.fail();
	}

	public R updateOrder(OrderVO request, boolean checkTradeAccount) {
		request.setTradeAccount(request.getTradeAccount());

//		EF01110104Request req = new EF01110104Request();
//		req.setFunctionId(GrmFunctions.BROKER_ORDER_MODIFY);
//		req.setFundAccount(request.getCapitalAccount());
//		req.setExchangeType(HSUtil.getMarketToHS(request.getAssetId()));
//		req.setStockCode(HSUtil.getStockCode(request.getAssetId()));
//		req.setEntrustAmount(request.getEntrustAmount());
//		req.setEntrustPrice(request.getEntrustPrice());
//		req.setEntrustNo(request.getEntrustNo());
//		req.setEntrustType("B");
//		GrmResponseVO response = eF01110104.requestData(req);
//		if (!response.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
//			log.error("placeOrder EF01110103, error=" + JSON.toJSONString(response));
//			return R.fail(response.getErrorMsg());
//		}
		/**
		 * 柜台改单
		 */
//		return R.data(response.getResult().get("data"));
		log.info("触发柜台改单");
		return R.success();
	}

}
