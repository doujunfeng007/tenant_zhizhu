/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerStockHoldingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockHoldingRecordsVO;
import java.util.Objects;

/**
 * 客户股票持仓表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerStockHoldingRecordsWrapper extends BaseEntityWrapper<CustomerStockHoldingRecordsEntity, CustomerStockHoldingRecordsVO>  {

	public static CustomerStockHoldingRecordsWrapper build() {
		return new CustomerStockHoldingRecordsWrapper();
 	}

	@Override
	public CustomerStockHoldingRecordsVO entityVO(CustomerStockHoldingRecordsEntity stockHoldingRecords) {
		CustomerStockHoldingRecordsVO stockHoldingRecordsVO = Objects.requireNonNull(BeanUtil.copy(stockHoldingRecords, CustomerStockHoldingRecordsVO.class));

		//User createUser = UserCache.getUser(stockHoldingRecords.getCreateUser());
		//User updateUser = UserCache.getUser(stockHoldingRecords.getUpdateUser());
		//stockHoldingRecordsVO.setCreateUserName(createUser.getName());
		//stockHoldingRecordsVO.setUpdateUserName(updateUser.getName());

		return stockHoldingRecordsVO;
	}


}
