package com.minigod.zero.data.statistics.service.impl;

import java.util.List;

import com.minigod.zero.data.vo.CustomerTotalCountVO;
import org.springframework.stereotype.Service;

import com.minigod.zero.data.mapper.CustomerFinancingAccountMapper;
import com.minigod.zero.data.statistics.service.CustomerFinancingAccountService;
import com.minigod.zero.data.vo.CustomerAccountStatisticsVO;

/**
 * @author eric
 * @desc 客户理财账户表服务实现
 * @date 2024-10-28 13:40:05
 */
@Service
public class CustomerFinancingAccountServiceImpl implements CustomerFinancingAccountService {

	private final CustomerFinancingAccountMapper customerFinancingAccountMapper;

	public CustomerFinancingAccountServiceImpl(CustomerFinancingAccountMapper customerFinancingAccountMapper) {
		this.customerFinancingAccountMapper = customerFinancingAccountMapper;
	}

	/**
	 * 统计用户数转化率
	 *
	 * @return
	 */
	@Override
	public List<CustomerAccountStatisticsVO> getAccountStatistics() {
		return customerFinancingAccountMapper.getAccountStatistics();
	}

	/**
	 * 统计用户数概览
	 *
	 * @return
	 */
	@Override
	public CustomerTotalCountVO getCustomerTotalCount() {
		return customerFinancingAccountMapper.getCustomerTotalCount();
	}
}
