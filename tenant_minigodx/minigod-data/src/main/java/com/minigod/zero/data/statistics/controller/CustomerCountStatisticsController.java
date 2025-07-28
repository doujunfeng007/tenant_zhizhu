package com.minigod.zero.data.statistics.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.statistics.service.CustomerAccountOpenApplicationService;
import com.minigod.zero.data.statistics.service.DepositAndWithdrawalStatisticsService;
import com.minigod.zero.data.statistics.service.ProfessionalInvestorPIApplicationService;
import com.minigod.zero.data.vo.CustomerTotalCountVO;
import com.minigod.zero.data.vo.DepositAndWithdrawalApplyCountVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minigod.zero.data.statistics.facade.CustomerInfoStatisticsFacadeService;
import com.minigod.zero.data.statistics.service.CustomerFinancingAccountService;
import com.minigod.zero.data.vo.CustomerAccountStatisticsVO;
import com.minigod.zero.data.vo.CustomerCountVO;

/**
 * 客户信息数据统计
 *
 * @author eric
 * @date 2024-10-26 13:56:25
 */
@RestController
@RequestMapping("/statistics/customer")
@Api(tags = "客户信息数据统计", value = "客户信息数据统计")
public class CustomerCountStatisticsController {

	private final CustomerInfoStatisticsFacadeService customerInfoFacadeService;
	private final CustomerFinancingAccountService customerFinancingAccountService;
	private final DepositAndWithdrawalStatisticsService depositAndWithdrawalStatisticsService;
	private final ProfessionalInvestorPIApplicationService professionalInvestorPIApplicationService;
	private final CustomerAccountOpenApplicationService customerAccountOpenApplicationService;

	public CustomerCountStatisticsController(CustomerInfoStatisticsFacadeService customerInfoFacadeService,
											 CustomerFinancingAccountService customerFinancingAccountService,
											 DepositAndWithdrawalStatisticsService depositAndWithdrawalStatisticsService,
											 ProfessionalInvestorPIApplicationService professionalInvestorPIApplicationService,
											 CustomerAccountOpenApplicationService customerAccountOpenApplicationService) {
		this.customerInfoFacadeService = customerInfoFacadeService;
		this.customerFinancingAccountService = customerFinancingAccountService;
		this.depositAndWithdrawalStatisticsService = depositAndWithdrawalStatisticsService;
		this.professionalInvestorPIApplicationService = professionalInvestorPIApplicationService;
		this.customerAccountOpenApplicationService = customerAccountOpenApplicationService;
	}

	/**
	 * 统计客户总数
	 *
	 * @return
	 */
	@ApiOperation("统计客户总数")
	@GetMapping("/count/total")
	public R<CustomerTotalCountVO> getCustomerTotalCount() {
		return R.data(customerInfoFacadeService.getCustomerTotalCount());
	}

	/**
	 * 统计用户数及占比情况
	 *
	 * @return
	 */
	@ApiOperation("统计用户数及占比情况")
	@GetMapping("/count/proportion")
	public R<CustomerCountVO> countCustomers() {
		return R.data(customerInfoFacadeService.countCustomers());
	}

	/**
	 * 统计用户数转化率
	 *
	 * @return
	 */
	@ApiOperation("统计用户数转化率")
	@GetMapping("/count/conversion-rate")
	public R<List<CustomerAccountStatisticsVO>> getAccountStatistics() {
		return R.data(customerFinancingAccountService.getAccountStatistics());
	}

	/**
	 * 统计用户统计出入金申请笔数
	 */
	@ApiOperation("统计用户统计出入金申请审批笔数")
	@GetMapping("/count/deposit-and-withdrawal-apply")
	public R<DepositAndWithdrawalApplyCountVO> countDepositAndWithdrawalApplyApproval() {
		return R.data(depositAndWithdrawalStatisticsService.countDepositAndWithdrawalApply());
	}

	/**
	 * 统计用户PI申请数
	 */
	@ApiOperation("统计用户PI申请审批笔数")
	@GetMapping("/count/pi-apply")
	public R<List<Map<String, Object>>> countPiApply() {
		return R.data(professionalInvestorPIApplicationService.countCustomerPIApply());
	}

	/**
	 * 统计客户开户申请笔数
	 */
	@ApiOperation("统计客户开户申请审批笔数")
	@GetMapping("/count/open-apply")
	public R<List<Map<String, Object>>> countPiApproval() {
		return R.data(customerAccountOpenApplicationService.countCustomerAccountOpenApply());
	}
}
