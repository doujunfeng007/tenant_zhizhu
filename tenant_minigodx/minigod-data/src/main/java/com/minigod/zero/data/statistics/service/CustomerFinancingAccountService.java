package com.minigod.zero.data.statistics.service;

import java.util.List;

import com.minigod.zero.data.vo.CustomerAccountStatisticsVO;
import com.minigod.zero.data.vo.CustomerTotalCountVO;

/**
 * @author eric
 * @desc 客户理财账户表服务接口
 * @date 2024-10-28 13:40:05
 */
public interface CustomerFinancingAccountService {
	/**
	 * 统计用户数转化率
	 *
	 * @return
	 */
	List<CustomerAccountStatisticsVO> getAccountStatistics();

	/**
	 * 统计用户数概览
	 *
	 * @return
	 */
	CustomerTotalCountVO getCustomerTotalCount();
}
