package com.minigod.zero.data.statistics.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.statistics.service.CustomerAssetsStatisticsService;
import com.minigod.zero.data.vo.AccumulatedProfitTotalVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户资产数据统计
 *
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/28 19:55
 * @description：用户资产数据统计
 */
@RestController
@RequestMapping("/statistics/customer-asset")
@Api(tags = "用户资产数据统计", value = "用户资产数据统计")
public class CustomerAssetsStatisticsController {

	@Autowired
	private CustomerAssetsStatisticsService customerAssetsStatisticsService;

	/**
	 * 大客户top 10
	 *
	 * @return
	 */
	@ApiOperation("大客户top10")
	@GetMapping("/customer_assets_top10")
	public R majorAccountTop10() {
		return customerAssetsStatisticsService.majorAccountTop10();
	}

	/**
	 * 累计利息统计
	 *
	 * @return
	 */
	@ApiOperation("累计利息统计")
	@GetMapping("/accumulated/profit")
	public R<AccumulatedProfitTotalVO> getAccumulatedProfitByCurrency() {
		return R.data(customerAssetsStatisticsService.getAccumulatedProfitByCurrency());
	}
}
