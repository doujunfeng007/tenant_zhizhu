/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerStockAssetRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockAssetRecordsVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户资产流水汇总表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerStockAssetRecordsService extends BaseService<CustomerStockAssetRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param stockAssetRecords
	 * @return
	 */
	IPage<CustomerStockAssetRecordsVO> selectCustomerStockAssetRecordsPage(IPage<CustomerStockAssetRecordsVO> page, CustomerStockAssetRecordsVO stockAssetRecords);


}
