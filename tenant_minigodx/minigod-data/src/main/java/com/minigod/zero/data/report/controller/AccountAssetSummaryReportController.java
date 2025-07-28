package com.minigod.zero.data.report.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.data.entity.AccountAssetSummaryReport;
import com.minigod.zero.data.report.service.AccountAssetSummaryReportService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.task.MinigodReportTask;
import com.minigod.zero.data.dto.AccountAssetSummaryReportDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客戶账户资产汇总报表
 *
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/22 13:30
 * @description：账户资产汇总
 */
@RestController
@RequestMapping("/report")
@Api(tags = "客户账户资产汇总报表", value = "客户账户资产汇总报表")
public class AccountAssetSummaryReportController {
	private final AccountAssetSummaryReportService accountAssetSummaryReportService;
	private final MinigodReportTask accountAssetSummaryReportTask;

	public AccountAssetSummaryReportController(AccountAssetSummaryReportService accountAssetSummaryReportService,
											   MinigodReportTask accountAssetSummaryReportTask) {
		this.accountAssetSummaryReportService = accountAssetSummaryReportService;
		this.accountAssetSummaryReportTask = accountAssetSummaryReportTask;
	}

	/**
	 * 客户账户资产汇总报表
	 *
	 * @param accountAssetSummaryReportDTO
	 * @return
	 */
	@ApiOperation("查询客户账户资产汇总表")
	@PostMapping("/account_asset_summary_report")
	public R<Page<List<AccountAssetSummaryReport>>> accountAssetSummaryReport(@RequestBody AccountAssetSummaryReportDTO accountAssetSummaryReportDTO) {
		return R.data(accountAssetSummaryReportService.accountAssetSummaryReport(accountAssetSummaryReportDTO));
	}

	/**
	 * 生成账户资产汇总报表数据
	 */
	@ApiOperation("生成账户资产汇总表")
	@GetMapping("/init")
	public void taskTest() {
		accountAssetSummaryReportTask.accountAssetSummaryReport();
	}
}
