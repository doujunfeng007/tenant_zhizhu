/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerFundTradingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundTradingRecordsVO;
import java.util.Objects;

/**
 * 客户交易流水汇总表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerFundTransactionRecordsWrapper extends BaseEntityWrapper<CustomerFundTradingRecordsEntity, CustomerFundTradingRecordsVO>  {

	public static CustomerFundTransactionRecordsWrapper build() {
		return new CustomerFundTransactionRecordsWrapper();
 	}

	@Override
	public CustomerFundTradingRecordsVO entityVO(CustomerFundTradingRecordsEntity fundTransactionRecords) {
		CustomerFundTradingRecordsVO fundTransactionRecordsVO = Objects.requireNonNull(BeanUtil.copy(fundTransactionRecords, CustomerFundTradingRecordsVO.class));

		//User createUser = UserCache.getUser(fundTransactionRecords.getCreateUser());
		//User updateUser = UserCache.getUser(fundTransactionRecords.getUpdateUser());
		//fundTransactionRecordsVO.setCreateUserName(createUser.getName());
		//fundTransactionRecordsVO.setUpdateUserName(updateUser.getName());

		return fundTransactionRecordsVO;
	}


}
