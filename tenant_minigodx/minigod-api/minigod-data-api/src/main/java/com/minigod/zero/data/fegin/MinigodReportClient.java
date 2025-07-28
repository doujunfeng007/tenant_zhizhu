package com.minigod.zero.data.fegin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.dto.CustomerFundDetailsReportDTO;
import com.minigod.zero.data.dto.AccountAssetSummaryReportDTO;
import com.minigod.zero.data.dto.DepositAndWithdrawalReportDTO;
import com.minigod.zero.data.vo.CustomerFinancialDetailsReportVO;
import com.minigod.zero.data.vo.ReportVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/24 19:30
 * @description：
 */
@FeignClient(value = "minigod-data")
public interface MinigodReportClient {
	String DEPOSIT_AND_WITHDRAWAL_REPORT = "/report/deposit_withdrawal";

	String FINANCIAL_DETAILS_REPORT = "/report/financial_details_report";

	String ACCOUNT_ASSET_SUMMARY_REPORT = "/report/account_asset_summary_report";

	/**
	 * 出入金报表
	 * @param dto
	 * @return
	 */
	@PostMapping(DEPOSIT_AND_WITHDRAWAL_REPORT)
	R<ReportVO> depositAndWithdrawalReport(@RequestBody  DepositAndWithdrawalReportDTO dto);

	/**
	 * 账户资产汇总报表
	 * @param dto
	 * @return
	 */
	@PostMapping(ACCOUNT_ASSET_SUMMARY_REPORT)
	R<Page> accountAssetSummaryReport(@RequestBody  AccountAssetSummaryReportDTO dto);

	/**
	 * 客户资金明细报表
	 * @param dto
	 * @return
	 */
	@PostMapping(FINANCIAL_DETAILS_REPORT)
	R<CustomerFinancialDetailsReportVO> financialDetailsReport(@RequestBody  CustomerFundDetailsReportDTO dto);
}
