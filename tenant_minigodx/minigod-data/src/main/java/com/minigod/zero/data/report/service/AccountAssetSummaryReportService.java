package com.minigod.zero.data.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.data.dto.AccountAssetSummaryReportDTO;
import com.minigod.zero.data.entity.AccountAssetSummaryReport;

import java.util.List;

/**
 * 账户资产汇总报表服务接口
 *
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/22 13:32
 * @description：
 */
public interface AccountAssetSummaryReportService {
	/**
	 * 客户账户资产汇总报表
	 *
	 * @param accountAssetSummaryReportDTO
	 * @return
	 */
	Page<List<AccountAssetSummaryReport>> accountAssetSummaryReport(AccountAssetSummaryReportDTO accountAssetSummaryReportDTO);
}
