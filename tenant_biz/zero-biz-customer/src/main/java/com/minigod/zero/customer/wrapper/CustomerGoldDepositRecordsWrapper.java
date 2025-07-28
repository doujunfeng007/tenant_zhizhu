/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerGoldDepositRecordsEntity;
import com.minigod.zero.customer.vo.CustomerGoldDepositRecordsVO;
import java.util.Objects;

/**
 * 客户入金申请信息表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerGoldDepositRecordsWrapper extends BaseEntityWrapper<CustomerGoldDepositRecordsEntity, CustomerGoldDepositRecordsVO>  {

	public static CustomerGoldDepositRecordsWrapper build() {
		return new CustomerGoldDepositRecordsWrapper();
 	}

	@Override
	public CustomerGoldDepositRecordsVO entityVO(CustomerGoldDepositRecordsEntity goldDepositRecords) {
		CustomerGoldDepositRecordsVO goldDepositRecordsVO = Objects.requireNonNull(BeanUtil.copy(goldDepositRecords, CustomerGoldDepositRecordsVO.class));

		//User createUser = UserCache.getUser(goldDepositRecords.getCreateUser());
		//User updateUser = UserCache.getUser(goldDepositRecords.getUpdateUser());
		//goldDepositRecordsVO.setCreateUserName(createUser.getName());
		//goldDepositRecordsVO.setUpdateUserName(updateUser.getName());

		return goldDepositRecordsVO;
	}


}
