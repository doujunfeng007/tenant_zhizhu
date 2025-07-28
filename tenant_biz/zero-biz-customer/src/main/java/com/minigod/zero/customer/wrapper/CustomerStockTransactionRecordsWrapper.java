/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerStockTradingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerStockTradingRecordsVO;
import java.util.Objects;

/**
 * 客户交易流水汇总表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerStockTransactionRecordsWrapper extends BaseEntityWrapper<CustomerStockTradingRecordsEntity, CustomerStockTradingRecordsVO>  {

	public static CustomerStockTransactionRecordsWrapper build() {
		return new CustomerStockTransactionRecordsWrapper();
 	}

	@Override
	public CustomerStockTradingRecordsVO entityVO(CustomerStockTradingRecordsEntity stockTransactionRecords) {
		CustomerStockTradingRecordsVO stockTransactionRecordsVO = Objects.requireNonNull(BeanUtil.copy(stockTransactionRecords, CustomerStockTradingRecordsVO.class));

		//User createUser = UserCache.getUser(stockTransactionRecords.getCreateUser());
		//User updateUser = UserCache.getUser(stockTransactionRecords.getUpdateUser());
		//stockTransactionRecordsVO.setCreateUserName(createUser.getName());
		//stockTransactionRecordsVO.setUpdateUserName(updateUser.getName());

		return stockTransactionRecordsVO;
	}


}
