package com.minigod.zero.data.statistics.service;

import java.util.List;
import java.util.Map;

/**
 * PI 申请统计
 *
 * @author eric
 * @since 2024-10-31 15:02:01
 */
public interface ProfessionalInvestorPIApplicationService {
	/**
	 * 统计 PI 申请数
	 *
	 * @return
	 */
	List<Map<String, Object>> countCustomerPIApply();
}
