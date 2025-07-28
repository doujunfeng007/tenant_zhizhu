package com.minigod.zero.data.statistics.service.impl;

import com.minigod.zero.data.mapper.CustomerInfoMapper;
import com.minigod.zero.data.statistics.service.CustomerInfoStatisticsService;
import com.minigod.zero.data.vo.CustomerInfoForPICountVO;
import org.springframework.stereotype.Service;

/**
 * 客户信息服务实现
 *
 * @author eric
 * @date 2024-10-26 13:42:18
 */
@Service
public class CustomerInfoStatisticsServiceImpl implements CustomerInfoStatisticsService {

	private final CustomerInfoMapper customerInfoMapper;

	public CustomerInfoStatisticsServiceImpl(CustomerInfoMapper customerInfoMapper) {
		this.customerInfoMapper = customerInfoMapper;
	}

	/**
	 * 根据PI等级统计客户信息
	 *
	 * @return
	 */
	@Override
	public CustomerInfoForPICountVO statisticsByPiLevel() {
		return customerInfoMapper.statisticsByPiLevel();
	}

	/**
	 * 统计通过PI认证的用户数
	 *
	 * @return
	 */
	@Override
	public Integer statisticsPiUserCount() {
		return customerInfoMapper.statisticsPiUserCount();
	}
}
