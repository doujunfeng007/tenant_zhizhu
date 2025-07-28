package com.minigod.zero.data.report.service;


import com.minigod.zero.data.dto.CustomerFundDetailsReportDTO;
import com.minigod.zero.data.vo.CustomerFinancialDetailsReportVO;

/**
 * 客户资金明细报表
 *
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/23 9:43
 * @description：
 */
public interface CustomerFundDetailsReportService {
	/**
	 * 客户资金明细报表
	 *
	 * @param dto
	 * @return
	 */
	CustomerFinancialDetailsReportVO financialDetailsReport(CustomerFundDetailsReportDTO dto);
}
