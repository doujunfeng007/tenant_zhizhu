package com.minigod.zero.data.task;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.dto.CustStatementDTO;
import com.minigod.zero.data.fegin.MinigodReportTaskClient;
import com.minigod.zero.data.task.service.AccountAssetSummaryReportTaskService;
import com.minigod.zero.data.task.service.CustomerStatementAccountService;
import com.minigod.zero.data.task.service.TradeStatementProcessBySambaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 客户账户资产汇总报表定时任务
 *
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/22 13:37
 * @description：报表类定时任务
 */
@Slf4j
@Api(tags = "客户账户资产汇总报表定时任务", value = "客户账户资产汇总报表定时任务")
@RestController
public class MinigodReportTask implements MinigodReportTaskClient {

	private final AccountAssetSummaryReportTaskService accountAssetSummaryReportTaskService;

	@Autowired
	private CustomerStatementAccountService customerStatementAccountService;

	@Autowired
	private TradeStatementProcessBySambaService tradeStatementProcessBySambaService;

	public MinigodReportTask(AccountAssetSummaryReportTaskService accountAssetSummaryReportTaskService) {
		this.accountAssetSummaryReportTaskService = accountAssetSummaryReportTaskService;
	}

	@Override
	@ApiOperation(value = "客户账户资产汇总报表")
	@PostMapping(ACCOUNT_ASSET_SUMMARY_REPORT_URL)
	public R<Void> accountAssetSummaryReport() {
		return accountAssetSummaryReportTaskService.accountAssetSummaryReport();
	}

	/**
	 * 日结单导出
	 * @param custStatementDTO
	 * @return
	 */
	@Override
	@PostMapping(EXPORT_STATEMENT_DAILY)
	public R exportStatementDaily(CustStatementDTO custStatementDTO) {
		customerStatementAccountService.exportHldBondDailyAccountList2(custStatementDTO);
		return R.success();
	}

	/**
	 * 月结单导出
	 * @param custStatementDTO
	 * @return
	 */
	@Override
	@PostMapping(EXPORT_STATEMENT_MONTH)
	public R exportStatementMonth(CustStatementDTO custStatementDTO) {
		customerStatementAccountService.exportHldBondMonthAccountList2(custStatementDTO);
		return R.success();
	}

	@Override
	public R stockStatementProcess(CustStatementDTO custStatementDTO) throws IOException {
		tradeStatementProcessBySambaService.processStatementFiles();
		return R.success();
	}
}
