/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerStockTradingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockTradingRecordsVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户交易流水汇总表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerStockTransactionRecordsService extends BaseService<CustomerStockTradingRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param stockTransactionRecords
	 * @return
	 */
	IPage<CustomerStockTradingRecordsVO> selectCustomerStockTransactionRecordsPage(IPage<CustomerStockTradingRecordsVO> page, CustomerStockTradingRecordsVO stockTransactionRecords);


}
