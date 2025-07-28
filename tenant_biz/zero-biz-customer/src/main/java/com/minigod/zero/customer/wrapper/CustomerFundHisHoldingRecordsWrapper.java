/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerFundHisHoldingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundHisHoldingRecordsVO;
import java.util.Objects;

/**
 * 客户基金历史持仓表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerFundHisHoldingRecordsWrapper extends BaseEntityWrapper<CustomerFundHisHoldingRecordsEntity, CustomerFundHisHoldingRecordsVO>  {

	public static CustomerFundHisHoldingRecordsWrapper build() {
		return new CustomerFundHisHoldingRecordsWrapper();
 	}

	@Override
	public CustomerFundHisHoldingRecordsVO entityVO(CustomerFundHisHoldingRecordsEntity fundHisHoldingRecords) {
		CustomerFundHisHoldingRecordsVO fundHisHoldingRecordsVO = Objects.requireNonNull(BeanUtil.copy(fundHisHoldingRecords, CustomerFundHisHoldingRecordsVO.class));

		//User createUser = UserCache.getUser(fundHisHoldingRecords.getCreateUser());
		//User updateUser = UserCache.getUser(fundHisHoldingRecords.getUpdateUser());
		//fundHisHoldingRecordsVO.setCreateUserName(createUser.getName());
		//fundHisHoldingRecordsVO.setUpdateUserName(updateUser.getName());

		return fundHisHoldingRecordsVO;
	}


}
