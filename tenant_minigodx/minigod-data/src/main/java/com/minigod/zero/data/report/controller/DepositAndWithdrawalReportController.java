package com.minigod.zero.data.report.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.report.service.DepositAndWithdrawalReportService;
import com.minigod.zero.data.dto.DepositAndWithdrawalReportDTO;
import com.minigod.zero.data.vo.ReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Title: 客戶出入金报表
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/26 16:49
 * @description：出入金报表
 */
@RestController
@RequestMapping("/report")
@Api(tags = "客戶出、入金报表", value = "客戶出、入金报表")
public class DepositAndWithdrawalReportController {

	private final DepositAndWithdrawalReportService depositAndWithdrawalReportService;

	public DepositAndWithdrawalReportController(DepositAndWithdrawalReportService depositAndWithdrawalReportService) {
		this.depositAndWithdrawalReportService = depositAndWithdrawalReportService;
	}

	/**
	 * 出入金报表
	 *
	 * @param depositAndWithdrawalReportDTO
	 * @return
	 */
	@ApiOperation("查询客户出入金报表")
	@PostMapping("/deposit_withdrawal")
	public R<ReportVO> queryPage(@RequestBody DepositAndWithdrawalReportDTO depositAndWithdrawalReportDTO) {
		return R.data(depositAndWithdrawalReportService.depositAndWithdrawalReport(depositAndWithdrawalReportDTO));
	}
}
