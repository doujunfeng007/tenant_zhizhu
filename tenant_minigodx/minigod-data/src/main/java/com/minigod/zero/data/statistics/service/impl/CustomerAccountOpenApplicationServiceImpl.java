package com.minigod.zero.data.statistics.service.impl;

import com.minigod.zero.data.mapper.CustomerAccountOpenApplicationMapper;
import com.minigod.zero.data.statistics.service.CustomerAccountOpenApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 开户申请记录统计
 *
 * @author: eric
 * @since 2024-10-31 19:39:04
 */
@Service
public class CustomerAccountOpenApplicationServiceImpl implements CustomerAccountOpenApplicationService {
	private final CustomerAccountOpenApplicationMapper customerAccountOpenApplicationMapper;

	public CustomerAccountOpenApplicationServiceImpl(CustomerAccountOpenApplicationMapper customerAccountOpenApplicationMapper) {
		this.customerAccountOpenApplicationMapper = customerAccountOpenApplicationMapper;
	}

	/**
	 * 统计客户开户申请笔数
	 */
	@Override
	public List<Map<String, Object>> countCustomerAccountOpenApply() {
		return customerAccountOpenApplicationMapper.countCustomerAccountOpenApply();
	}
}
