/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerStockAssetRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockAssetRecordsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户资产流水汇总表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerStockAssetRecordsMapper extends BaseMapper<CustomerStockAssetRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param stockAssetRecords
	 * @return
	 */
	List<CustomerStockAssetRecordsVO> selectCustomerStockAssetRecordsPage(IPage page, CustomerStockAssetRecordsVO stockAssetRecords);


}
