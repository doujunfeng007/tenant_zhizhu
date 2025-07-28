/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerFundHoldingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundHoldingRecordsVO;
import java.util.Objects;

/**
 * 客户基金持仓表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerFundHoldingRecordsWrapper extends BaseEntityWrapper<CustomerFundHoldingRecordsEntity, CustomerFundHoldingRecordsVO>  {

	public static CustomerFundHoldingRecordsWrapper build() {
		return new CustomerFundHoldingRecordsWrapper();
 	}

	@Override
	public CustomerFundHoldingRecordsVO entityVO(CustomerFundHoldingRecordsEntity fundHoldingRecords) {
		CustomerFundHoldingRecordsVO fundHoldingRecordsVO = Objects.requireNonNull(BeanUtil.copy(fundHoldingRecords, CustomerFundHoldingRecordsVO.class));

		//User createUser = UserCache.getUser(fundHoldingRecords.getCreateUser());
		//User updateUser = UserCache.getUser(fundHoldingRecords.getUpdateUser());
		//fundHoldingRecordsVO.setCreateUserName(createUser.getName());
		//fundHoldingRecordsVO.setUpdateUserName(updateUser.getName());

		return fundHoldingRecordsVO;
	}


}
