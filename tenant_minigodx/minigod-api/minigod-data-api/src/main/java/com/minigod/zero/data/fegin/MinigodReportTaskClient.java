package com.minigod.zero.data.fegin;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.dto.CustStatementDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/28 10:32
 * @description：定时任务处理
 */
@FeignClient(value = "minigod-data")
public interface MinigodReportTaskClient {
	String ACCOUNT_ASSET_SUMMARY_REPORT_URL = "/report/task/account_asset_summary_report_task";
	/**
	 * 导出日结单
	 */
	 String EXPORT_STATEMENT_DAILY = "/report/task/statement/exportStatementDaily";
	/**
	 * 导出月结单
	 */
	String EXPORT_STATEMENT_MONTH = "/report/task/statement/exportStatementMonth";
	/**
	 * 导出月结单
	 */
	String STOCK_STATEMENT_PROCESS = "/report/task/statement/stockStatementProcess";

	@PostMapping(ACCOUNT_ASSET_SUMMARY_REPORT_URL)
	R<Void> accountAssetSummaryReport();


	@PostMapping(EXPORT_STATEMENT_DAILY)
	R exportStatementDaily(@RequestBody CustStatementDTO custStatementDTO);

	@PostMapping(EXPORT_STATEMENT_MONTH)
	R exportStatementMonth(@RequestBody CustStatementDTO custStatementDTO);

	@PostMapping(STOCK_STATEMENT_PROCESS)
	R stockStatementProcess(@RequestBody CustStatementDTO custStatementDTO) throws IOException;

}
