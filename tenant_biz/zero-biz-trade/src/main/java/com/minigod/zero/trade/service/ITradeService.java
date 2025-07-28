package com.minigod.zero.trade.service;

import com.minigod.zero.biz.common.mkt.vo.StrategyOrderVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.order.vo.request.CancelOrderRequest;
import com.minigod.zero.trade.order.vo.request.PlaceOrderRequest;
import com.minigod.zero.trade.order.vo.request.UpdateOrderRequest;
import com.minigod.zero.trade.vo.request.BaseRequest;
import com.minigod.zero.trade.vo.sjmb.resp.OrderFeeVO;
import com.minigod.zero.trade.vo.strategy.CommonOrderVO;
import com.minigod.zero.trade.vo.strategy.CustStrategyOrderVO;

/**
 * 交易服务
 */
public interface ITradeService {
    /**
     * 普通下单
     */
    R placeOrder(PlaceOrderRequest placeOrderRequest);

    /**
     * 条件单下单
     */
    R placeStrategyOrder(CustStrategyOrderVO request);

    /**
     * 普通改单
     */
    R updateOrder(UpdateOrderRequest request);

    /**
     * 条件单改单
     */
    R updateStrategyOrder(CustStrategyOrderVO request);

    /**
     * 普通撤单
     */
    R cancelOrder(CancelOrderRequest request);

    /**
     * 条件单撤单
     */
    R cancelStrategyOrder(CustStrategyOrderVO request,boolean checkTradeAccount);

	/**
	 * 查询佣金
	 * @param request
	 * @return
	 */
	R getCommissionInfo(BaseRequest request);

	/**
	 * 查询委托费用
	 * checkTradeAccount true-通过token校验传入的交易账号是否跟token获取到的是同一个，false-不校验
	 */
	R getOrderFee(OrderFeeVO request, boolean b);

	/**
	 * 普通单和条件单改单撤单
	 */
	R updateCommonOrder(CommonOrderVO request,boolean checkTradeAccount);

	R triggerStrategyOrder(StrategyOrderVO request);
}
