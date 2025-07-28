package com.minigod.zero.trade.tiger.service.impl;

import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.enums.TradeEnum;
import com.minigod.zero.biz.common.mkt.vo.PositionQuotationLVVO;
import com.minigod.zero.biz.common.mkt.vo.StrategyOrderVO;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.enums.EMarket;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.trade.order.vo.request.CancelOrderRequest;
import com.minigod.zero.trade.order.vo.request.PlaceOrderRequest;
import com.minigod.zero.trade.order.vo.request.UpdateOrderRequest;
import com.minigod.zero.trade.service.ITradeService;
import com.minigod.zero.trade.tiger.req.CancelOrderReq;
import com.minigod.zero.trade.tiger.req.PlaceOrderReq;
import com.minigod.zero.trade.tiger.req.UpdateOrderReq;
import com.minigod.zero.trade.tiger.service.TigerCommonService;
import com.minigod.zero.trade.vo.afe.AfeEntrustPropEnum;
import com.minigod.zero.trade.vo.request.BaseRequest;
import com.minigod.zero.trade.vo.sjmb.ExchangeTypeEnum;
import com.minigod.zero.trade.vo.sjmb.resp.OrderFeeVO;
import com.minigod.zero.trade.vo.strategy.CommonOrderVO;
import com.minigod.zero.trade.vo.strategy.CustStrategyOrderVO;
import com.minigod.zero.trade.vo.strategy.FeeVO;
import com.minigod.zero.trade.vo.tiger.TigerEntrustBsEnum;
import com.minigod.zero.trade.vo.tiger.TigerEntrustPropEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_TIGER;

/**
 * @author chen
 * @date 2025/5/19 14:54
 * @description
 */
@Service
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_TIGER)
@Slf4j
public class TigerTradeServiceImpl implements ITradeService {

	@Autowired
	private TigerCommonService tigerCommonService;

	@Autowired
	private ZeroRedis zeroRedis;

	@Override
	public R placeOrder(PlaceOrderRequest placeOrderRequest) {

		PlaceOrderReq placeOrderReq =new PlaceOrderReq();
		if (ExchangeTypeEnum.HK.getExchangeType().equals(placeOrderRequest.getExchangeType())) {
			placeOrderReq.setSymbol(StringUtils.leftPad(placeOrderRequest.getStockCode(), 5, "0"));
		} else {
			placeOrderReq.setSymbol(placeOrderRequest.getStockCode());
		}
		placeOrderReq.setQuantity(new BigDecimal(placeOrderRequest.getQty()));
		placeOrderReq.setAction(TigerEntrustBsEnum.getTigerEntrustBs(placeOrderRequest.getBsFlag()));
		placeOrderReq.setOrderType(TigerEntrustPropEnum.getTigerEntrustProp(placeOrderRequest.getEntrustProp()));
		placeOrderReq.setPrice(new BigDecimal(placeOrderRequest.getPrice()));
		placeOrderReq.setTimeInForce("DAY"); //当天有效

		R result = tigerCommonService.placeOrder(placeOrderRequest.getTradeAccount(), placeOrderReq);
		return result;
	}

	@Override
	public R placeStrategyOrder(CustStrategyOrderVO request) {
		return null;
	}

	@Override
	public R updateOrder(UpdateOrderRequest request) {
		UpdateOrderReq updateOrderReq = new UpdateOrderReq();
		updateOrderReq.setOrderId(Long.valueOf(request.getOrderNo()));
		updateOrderReq.setQuantity(new BigDecimal(request.getQty()));
		updateOrderReq.setPrice(new BigDecimal(request.getPrice()));
		return tigerCommonService.updateOrder(request.getTradeAccount(), updateOrderReq);
	}

	@Override
	public R updateStrategyOrder(CustStrategyOrderVO request) {
		return null;
	}

	@Override
	public R cancelOrder(CancelOrderRequest request) {
		CancelOrderReq cancelOrderReq = new CancelOrderReq();
		cancelOrderReq.setAccountId(request.getTradeAccount());
		cancelOrderReq.setOrderId(Long.valueOf(request.getOrderNo()));
		return tigerCommonService.cancelOrder(cancelOrderReq);
	}

	@Override
	public R cancelStrategyOrder(CustStrategyOrderVO request, boolean checkTradeAccount) {
		return null;
	}

	@Override
	public R getCommissionInfo(BaseRequest request) {
		return null;
	}

	@Override
	public R getOrderFee(OrderFeeVO request, boolean b) {
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
//			Map<String, Object> quotationMap = (Map<String, Object>) restTemplateUtil.postSend(OpenApiConstant.GET_QUOTATION, HashMap.class, pqlv);
//			if (quotationMap.get(request.getAssetId()) != null) {
//				PositionQuotationVO quotation = JSON.parseObject(JSON.toJSONString(quotationMap.get(request.getAssetId())), PositionQuotationVO.class);
//				request.setEntrustPrice(quotation.getPrice().toString());
//			} else {
//				request.setEntrustPrice("0");
//			}
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
		return null;
	}
}
