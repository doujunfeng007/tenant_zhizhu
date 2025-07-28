/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerFundHoldingRecordsService;
import com.minigod.zero.customer.vo.CustomerFundHoldingRecordsVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户基金持仓表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerFundHoldingRecordsServiceImpl implements ICustomerFundHoldingRecordsService {

	@Override
	public IPage<CustomerFundHoldingRecordsVO> selectCustomerFundHoldingRecordsPage(IPage<CustomerFundHoldingRecordsVO> page, CustomerFundHoldingRecordsVO fundHoldingRecords) {
		return null;
	}
}
