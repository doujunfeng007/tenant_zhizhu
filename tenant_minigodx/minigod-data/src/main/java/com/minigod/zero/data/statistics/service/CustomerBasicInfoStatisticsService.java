package com.minigod.zero.data.statistics.service;

import java.util.List;

import com.minigod.zero.data.vo.CustomerBasicInfoIdKindCountVO;

/**
 * 客户基础信息服务
 *
 * @author eric
 * @date 2024-10-26 13:42:18
 */
public interface CustomerBasicInfoStatisticsService {

	/**
	 * 统计客户ID种类
	 *
	 * @return
	 */
	List<CustomerBasicInfoIdKindCountVO> statisticsByIdKind();

}
