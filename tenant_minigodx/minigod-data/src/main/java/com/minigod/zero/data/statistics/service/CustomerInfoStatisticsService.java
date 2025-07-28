package com.minigod.zero.data.statistics.service;

import com.minigod.zero.data.vo.CustomerInfoForPICountVO;

/**
 * 客户信息服务
 *
 * @author eric
 * @date 2024-10-26 13:42:18
 */
public interface CustomerInfoStatisticsService {
	/**
	 * 统计PI用户数
	 *
	 * @return
	 */
	CustomerInfoForPICountVO statisticsByPiLevel();

	/**
	 * 统计通过PI认证的用户数
	 *
	 * @return
	 */
	Integer statisticsPiUserCount();
}
