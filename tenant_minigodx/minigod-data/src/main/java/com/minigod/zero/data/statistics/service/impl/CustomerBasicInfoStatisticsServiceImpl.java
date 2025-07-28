package com.minigod.zero.data.statistics.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.minigod.zero.data.mapper.CustomerBasicInfoMapper;
import com.minigod.zero.data.statistics.service.CustomerBasicInfoStatisticsService;
import com.minigod.zero.data.vo.CustomerBasicInfoIdKindCountVO;

/**
 * 客户基础信息服务实现
 *
 * @author eric
 * @date 2024-10-26 17:29:18
 */
@Service
public class CustomerBasicInfoStatisticsServiceImpl implements CustomerBasicInfoStatisticsService {
	private final CustomerBasicInfoMapper customerBasicInfoMapper;

	public CustomerBasicInfoStatisticsServiceImpl(CustomerBasicInfoMapper customerBasicInfoMapper) {
		this.customerBasicInfoMapper = customerBasicInfoMapper;
	}

	/**
	 * 统计客户ID种类
	 *
	 * @return
	 */
	@Override
	public List<CustomerBasicInfoIdKindCountVO> statisticsByIdKind() {
		return customerBasicInfoMapper.statisticsByIdKind();
	}

}
