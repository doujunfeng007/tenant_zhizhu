/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerStockHoldingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockHoldingRecordsVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户股票持仓表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerStockHoldingRecordsService extends BaseService<CustomerStockHoldingRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param stockHoldingRecords
	 * @return
	 */
	IPage<CustomerStockHoldingRecordsVO> selectCustomerStockHoldingRecordsPage(IPage<CustomerStockHoldingRecordsVO> page, CustomerStockHoldingRecordsVO stockHoldingRecords);


}
