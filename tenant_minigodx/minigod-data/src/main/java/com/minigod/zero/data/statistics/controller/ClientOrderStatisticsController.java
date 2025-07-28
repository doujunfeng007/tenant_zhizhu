package com.minigod.zero.data.statistics.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.statistics.service.OrderStatisticsService;
import com.minigod.zero.data.vo.OrderIPOSummaryVO;
import com.minigod.zero.data.vo.OrderTotalAmountVO;
import com.minigod.zero.data.vo.OrderTotalCountVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics/order")
@Api(tags = "用户订单资产数据统计", value = "用户订单资产数据统计")
public class ClientOrderStatisticsController {
	private final OrderStatisticsService orderStatisticsService;

	public ClientOrderStatisticsController(OrderStatisticsService orderStatisticsService) {
		this.orderStatisticsService = orderStatisticsService;
	}

	/**
	 * 已购买总资产
	 *
	 * @return
	 */
	@ApiOperation(value = "已购买总资产")
	@RequestMapping(value = "/total-amount", method = {RequestMethod.GET})
	public R<OrderTotalAmountVO> statistics() {
		return R.data(orderStatisticsService.getTotalAmountByCurrency());
	}

	/**
	 * 交易笔数按产品类型统计
	 *
	 * @return
	 */
	@ApiOperation(value = "交易笔数按产品类型统计")
	@RequestMapping(value = "/total-count", method = {RequestMethod.GET})
	public R<List<OrderTotalCountVO>> statisticsByBusiType() {
		return R.data(orderStatisticsService.getTotalCountByBusiType());
	}

	/**
	 * 统计IPO订单情况
	 *
	 * @return
	 */
	@ApiOperation(value = "统计IPO订单情况")
	@RequestMapping(value = "/ipo-statistics", method = {RequestMethod.GET})
	public R<List<OrderIPOSummaryVO>> selectOrderStatistics() {
		return R.data(orderStatisticsService.selectOrderStatistics());
	}
}
