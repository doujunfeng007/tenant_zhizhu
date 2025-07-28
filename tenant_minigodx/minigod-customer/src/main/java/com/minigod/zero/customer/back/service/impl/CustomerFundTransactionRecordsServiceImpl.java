/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerFundTransactionRecordsService;
import com.minigod.zero.customer.entity.CustomerFundTradingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundTradingRecordsVO;
import com.minigod.zero.customer.mapper.CustomerFundTradingRecordsMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户交易流水汇总表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerFundTransactionRecordsServiceImpl extends BaseServiceImpl<CustomerFundTradingRecordsMapper, CustomerFundTradingRecordsEntity> implements ICustomerFundTransactionRecordsService {

	@Override
	public IPage<CustomerFundTradingRecordsVO> selectCustomerFundTransactionRecordsPage(IPage<CustomerFundTradingRecordsVO> page, CustomerFundTradingRecordsVO fundTransactionRecords) {
		return page.setRecords(baseMapper.selectCustomerFundTransactionRecordsPage(page, fundTransactionRecords));
	}


}
