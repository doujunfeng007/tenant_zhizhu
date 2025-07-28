package com.minigod.zero.data.report.controller;

import com.minigod.zero.data.report.service.CustomerFundDetailsReportService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.dto.CustomerFundDetailsReportDTO;
import com.minigod.zero.data.vo.CustomerFinancialDetailsReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 客户资金明细汇总
 *
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/22 18:48
 * @description：客户资金明细汇总
 */
@RestController
@RequestMapping("/report")
@Api(tags = "客户账户资产明细报表", value = "客户账户资产明细报表")
public class CustomerFundDetailsReportController {

	private final CustomerFundDetailsReportService customerFundDetailsService;

	public CustomerFundDetailsReportController(CustomerFundDetailsReportService customerFundDetailsService) {
		this.customerFundDetailsService = customerFundDetailsService;
	}

	/**
	 * 客户资金明细汇总报表
	 *
	 * @param customerFundDetailsReportDTO
	 * @return
	 */
	@ApiOperation("查询客户资金明细汇总报表")
	@PostMapping("/financial_details_report")
	public R<CustomerFinancialDetailsReportVO> financialDetailsReport(@RequestBody CustomerFundDetailsReportDTO customerFundDetailsReportDTO) {
		return R.data(customerFundDetailsService.financialDetailsReport(customerFundDetailsReportDTO));
	}
}
