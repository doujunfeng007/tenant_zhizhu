/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerStockHoldingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockHoldingRecordsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户股票持仓表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerStockHoldingRecordsMapper extends BaseMapper<CustomerStockHoldingRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param stockHoldingRecords
	 * @return
	 */
	List<CustomerStockHoldingRecordsVO> selectCustomerStockHoldingRecordsPage(IPage page, CustomerStockHoldingRecordsVO stockHoldingRecords);


}
