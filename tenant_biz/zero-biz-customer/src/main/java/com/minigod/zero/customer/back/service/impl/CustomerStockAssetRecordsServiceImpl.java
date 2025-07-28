/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerStockAssetRecordsService;
import com.minigod.zero.customer.entity.CustomerStockAssetRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockAssetRecordsVO;
import com.minigod.zero.customer.mapper.CustomerStockAssetRecordsMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户资产流水汇总表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerStockAssetRecordsServiceImpl extends BaseServiceImpl<CustomerStockAssetRecordsMapper, CustomerStockAssetRecordsEntity> implements ICustomerStockAssetRecordsService {

	@Override
	public IPage<CustomerStockAssetRecordsVO> selectCustomerStockAssetRecordsPage(IPage<CustomerStockAssetRecordsVO> page, CustomerStockAssetRecordsVO stockAssetRecords) {
		return page.setRecords(baseMapper.selectCustomerStockAssetRecordsPage(page, stockAssetRecords));
	}


}
