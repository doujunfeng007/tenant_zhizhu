/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerStockTransactionRecordsService;
import com.minigod.zero.customer.entity.CustomerStockTradingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockTradingRecordsVO;
import com.minigod.zero.customer.mapper.CustomerStockTradingRecordsMapper;
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
public class CustomerStockTransactionRecordsServiceImpl extends BaseServiceImpl<CustomerStockTradingRecordsMapper, CustomerStockTradingRecordsEntity> implements ICustomerStockTransactionRecordsService {

	@Override
	public IPage<CustomerStockTradingRecordsVO> selectCustomerStockTransactionRecordsPage(IPage<CustomerStockTradingRecordsVO> page, CustomerStockTradingRecordsVO stockTransactionRecords) {
		return page.setRecords(baseMapper.selectCustomerStockTransactionRecordsPage(page, stockTransactionRecords));
	}


}
