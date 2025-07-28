package com.minigod.zero.customer.back.service;

import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/30 17:23
 * @description：
 */
public interface ReportService {
	/**
	 * 用户出入金
	 * @return
	 */
	R queryPage(Long pageIndex, Long pageSize, String startTime, String endTime, String clientId, Integer currency,
				Integer depositStatus, Integer withdrawalStatus, String applicationIds, Integer type);

	/**
	 * 账户资产汇总
	 * @return
	 */
	R accountAssetSummaryReport(Long pageIndex, Long pageSize, String startTime, String endTime,String accountId,String currency, String ids);

	/**
	 * 资产明细报表 
	 * @return
	 */
	R financialDetailsReport(Long pageIndex, Long pageSize, String startTime, String endTime, String accountId, String currency, String ids);
}
