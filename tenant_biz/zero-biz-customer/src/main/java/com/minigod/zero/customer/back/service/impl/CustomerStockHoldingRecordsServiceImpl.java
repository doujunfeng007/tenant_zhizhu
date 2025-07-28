/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerStockHoldingRecordsService;
import com.minigod.zero.customer.entity.CustomerStockHoldingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockHoldingRecordsVO;
import com.minigod.zero.customer.mapper.CustomerStockHoldingRecordsMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户股票持仓表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerStockHoldingRecordsServiceImpl extends BaseServiceImpl<CustomerStockHoldingRecordsMapper, CustomerStockHoldingRecordsEntity> implements ICustomerStockHoldingRecordsService {

	@Override
	public IPage<CustomerStockHoldingRecordsVO> selectCustomerStockHoldingRecordsPage(IPage<CustomerStockHoldingRecordsVO> page, CustomerStockHoldingRecordsVO stockHoldingRecords) {
		return page.setRecords(baseMapper.selectCustomerStockHoldingRecordsPage(page, stockHoldingRecords));
	}


}
