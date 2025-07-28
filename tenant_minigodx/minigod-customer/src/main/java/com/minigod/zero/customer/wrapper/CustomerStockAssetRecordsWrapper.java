/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerStockAssetRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockAssetRecordsVO;
import java.util.Objects;

/**
 * 客户资产流水汇总表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerStockAssetRecordsWrapper extends BaseEntityWrapper<CustomerStockAssetRecordsEntity, CustomerStockAssetRecordsVO>  {

	public static CustomerStockAssetRecordsWrapper build() {
		return new CustomerStockAssetRecordsWrapper();
 	}

	@Override
	public CustomerStockAssetRecordsVO entityVO(CustomerStockAssetRecordsEntity stockAssetRecords) {
		CustomerStockAssetRecordsVO stockAssetRecordsVO = Objects.requireNonNull(BeanUtil.copy(stockAssetRecords, CustomerStockAssetRecordsVO.class));

		//User createUser = UserCache.getUser(stockAssetRecords.getCreateUser());
		//User updateUser = UserCache.getUser(stockAssetRecords.getUpdateUser());
		//stockAssetRecordsVO.setCreateUserName(createUser.getName());
		//stockAssetRecordsVO.setUpdateUserName(updateUser.getName());

		return stockAssetRecordsVO;
	}


}
