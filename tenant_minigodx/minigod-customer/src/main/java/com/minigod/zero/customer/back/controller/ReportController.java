package com.minigod.zero.customer.back.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.dto.AccountAssetSummaryReportDTO;
import com.minigod.zero.data.dto.CustomerFundDetailsReportDTO;
import com.minigod.zero.data.dto.DepositAndWithdrawalReportDTO;
import com.minigod.zero.data.fegin.MinigodReportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/23 14:12
 * @description：报表
 */
@RestController
@RequestMapping("/report")
public class ReportController {
	@Autowired
	private MinigodReportClient reportClient;

	@GetMapping("/depositAndWithdrawal")
	public R queryPage(Long pageIndex, Long pageSize, String startTime,
					   String endTime, String clientId, Integer currency,
					   Integer depositStatus, Integer withdrawalStatus,String applicationIds,Integer type){
		return reportClient.depositAndWithdrawalReport(new DepositAndWithdrawalReportDTO(pageIndex, pageSize,
			startTime, endTime, clientId, currency,
			depositStatus, withdrawalStatus, applicationIds, type));
	}

	@GetMapping("/accountAssetSummaryReport")
	public R accountAssetSummaryReport(Long pageIndex, Long pageSize, String startTime, String endTime,
									   String accountId,String currency, String ids){

		return reportClient.accountAssetSummaryReport(new AccountAssetSummaryReportDTO(pageIndex,pageSize,
			startTime,endTime,accountId,currency,ids));
	}

	@GetMapping("/financialDetailsReport")
	public R financialDetailsReport(Long pageIndex, Long pageSize, String startTime, String endTime,String accountId,String currency, String ids){
		return reportClient.financialDetailsReport(new CustomerFundDetailsReportDTO(pageIndex,pageSize,
			startTime,endTime,accountId,currency,ids));
	}
}
