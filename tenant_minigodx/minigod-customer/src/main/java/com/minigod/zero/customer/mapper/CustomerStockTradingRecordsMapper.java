/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerStockTradingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockTradingRecordsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户交易流水汇总表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerStockTradingRecordsMapper extends BaseMapper<CustomerStockTradingRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param stockTransactionRecords
	 * @return
	 */
	List<CustomerStockTradingRecordsVO> selectCustomerStockTransactionRecordsPage(IPage page, CustomerStockTradingRecordsVO stockTransactionRecords);


}
