package com.minigod.zero.trade.openapi;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.options.IOptionsService;
import com.minigod.zero.trade.vo.req.options.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/options")
@Api(value = "美股期权相关接口", tags = "美股期权相关接口")
public class OptionsTradeController {


	@Autowired
	private IOptionsService optionsService;

    @PostMapping(value = "/inquiry")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "询价", notes = "询价")
    public R queryInquiry(@Valid @RequestBody InquiryReq inquiryReq) {
        return optionsService.queryInquiry(inquiryReq);
    }


	@PostMapping(value = "/place/order")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "下单", notes = "下单")
	public R placeOrder(@Valid @RequestBody InquiryPlaceOrderReq inquiryPlaceOrderReq) {
		return optionsService.placeOrder(inquiryPlaceOrderReq);
	}

	@PostMapping(value = "/cancel/order")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "撤单", notes = "撤单")
	public R cancelOrder(@Valid @RequestBody InquiryCancelOrderReq inquiryCancelOrderReq) {
		return optionsService.cancelOrder(inquiryCancelOrderReq);
	}


	@PostMapping(value = "/update/order")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "改单", notes = "改单")
	public R updateOrder(@Valid @RequestBody InquiryUpdateOrderReq inquiryUpdateOrderReq) {
		return optionsService.updateOrder(inquiryUpdateOrderReq);
	}

	@PostMapping(value = "/get_user_portfolio")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "持仓", notes = "持仓")
	public R getUserPortfolio( @RequestBody PortfolioReq portfolioReq) {
		return optionsService.getUserPortfolio(portfolioReq);
	}


	@PostMapping(value = "/get_journal_orders")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "今日委托", notes = "今日委托")
	public R getJournalOrders( @RequestBody JournalOrdersReq journalOrdersReq) {
		return optionsService.getJournalOrders(journalOrdersReq);
	}

	@PostMapping(value = "/get_history_orders")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "历史委托", notes = "历史委托")
	public R getHistoryOrders( @RequestBody JournalOrdersReq journalOrdersReq) {
		return optionsService.getHistoryOrders(journalOrdersReq);
	}


	@PostMapping(value = "/completed")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "成交", notes = "成交")
	public R completed(@Valid @RequestBody InquiryCancelOrderReq inquiryCancelOrderReq) {
		return optionsService.completed(inquiryCancelOrderReq);
	}





}
