package com.minigod.zero.data.report.service;


import com.minigod.zero.data.dto.DepositAndWithdrawalReportDTO;
import com.minigod.zero.data.vo.ReportVO;

/**
 * @Title: 出入金报表
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/26 17:00
 * @description：出入金报表
 */
public interface DepositAndWithdrawalReportService {
	/**
	 * 出入金报表
	 *
	 * @param depositAndWithdrawalReportDTO
	 * @return
	 */
	ReportVO depositAndWithdrawalReport(DepositAndWithdrawalReportDTO depositAndWithdrawalReportDTO);
}
