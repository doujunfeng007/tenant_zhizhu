package com.minigod.zero.data.statistics.service;

import java.util.List;
import java.util.Map;

/**
 * 开户申请记录统计
 *
 * @author eric
 * @since 2024-10-31 15:38:01
 */
public interface CustomerAccountOpenApplicationService {
	/**
	 * 统计客户开户申请笔数
	 */
	List<Map<String, Object>> countCustomerAccountOpenApply();
}
