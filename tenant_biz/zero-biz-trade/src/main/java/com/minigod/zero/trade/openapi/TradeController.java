package com.minigod.zero.trade.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.vo.request.BaseRequest;
import com.minigod.zero.trade.order.vo.request.CancelOrderRequest;
import com.minigod.zero.trade.order.vo.request.PlaceOrderRequest;
import com.minigod.zero.trade.order.vo.request.UpdateOrderRequest;
import com.minigod.zero.trade.service.ITradeService;
import com.minigod.zero.trade.vo.sjmb.resp.OrderFeeVO;
import com.minigod.zero.trade.vo.strategy.CommonOrderVO;
import com.minigod.zero.trade.vo.strategy.CustStrategyOrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/trade/trade_api")
@Api(value = "交易相关接口", tags = "交易相关接口")
public class TradeController {
	private final ITradeService tradeService;

	@PostMapping(value = "/place_order")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "交易下单", notes = "传入request")
    public R placeOrder(@Valid @RequestBody PlaceOrderRequest placeOrderRequest) {
        return tradeService.placeOrder(placeOrderRequest);
    }

	@PostMapping(value = "/update_order")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "交易改单", notes = "传入request")
    public R updateOrder(@Valid @RequestBody UpdateOrderRequest request) {
        return tradeService.updateOrder(request);
    }

	@PostMapping(value = "/cancel_order")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "交易撤单", notes = "传入request")
    public R cancelOrder(@Valid @RequestBody CancelOrderRequest request) {
        return tradeService.cancelOrder(request);
    }

	@PostMapping(value = "/place_strategy_order")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "条件单下单", notes = "传入request")
    public R placeStrategyOrder(@Valid @RequestBody CustStrategyOrderVO request) {
        return tradeService.placeStrategyOrder(request);
    }

	@PostMapping(value = "/update_strategy_order")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "条件单改单", notes = "传入request")
    public R updateStrategyOrder(@Valid @RequestBody CustStrategyOrderVO request) {
        return tradeService.updateStrategyOrder(request);
    }

	@PostMapping(value = "/cancel_strategy_order")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "条件单撤单", notes = "传入request")
    public R cancelStrategyOrder(@Valid @RequestBody CustStrategyOrderVO request) {
        return tradeService.cancelStrategyOrder(request,true);
    }

	@PostMapping(value = "/update_common_order")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "普通单和条件单改单撤单", notes = "传入request")
	public R updateCommonOrder(@Valid @RequestBody CommonOrderVO request) {
		return tradeService.updateCommonOrder(request,true);
	}

	@PostMapping(value = "/get_order_fee")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "查询委托费用", notes = "传入request")
	public R getOrderFee(@Valid @RequestBody OrderFeeVO request) {
		return tradeService.getOrderFee(request,true);
	}

	@PostMapping(value = "/get_commission_info")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "查询客户佣金", notes = "传入request")
	public R getCommissionInfo(@Valid @RequestBody BaseRequest request) {
		return tradeService.getCommissionInfo(request);
	}





}
