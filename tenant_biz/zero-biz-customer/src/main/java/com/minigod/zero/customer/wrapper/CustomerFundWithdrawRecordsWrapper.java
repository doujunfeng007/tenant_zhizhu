/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerFundWithdrawRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundWithdrawRecordsVO;
import java.util.Objects;

/**
 * 客户出金申请信息表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerFundWithdrawRecordsWrapper extends BaseEntityWrapper<CustomerFundWithdrawRecordsEntity, CustomerFundWithdrawRecordsVO>  {

	public static CustomerFundWithdrawRecordsWrapper build() {
		return new CustomerFundWithdrawRecordsWrapper();
 	}

	@Override
	public CustomerFundWithdrawRecordsVO entityVO(CustomerFundWithdrawRecordsEntity fundWithdrawRecords) {
		CustomerFundWithdrawRecordsVO fundWithdrawRecordsVO = Objects.requireNonNull(BeanUtil.copy(fundWithdrawRecords, CustomerFundWithdrawRecordsVO.class));

		//User createUser = UserCache.getUser(fundWithdrawRecords.getCreateUser());
		//User updateUser = UserCache.getUser(fundWithdrawRecords.getUpdateUser());
		//fundWithdrawRecordsVO.setCreateUserName(createUser.getName());
		//fundWithdrawRecordsVO.setUpdateUserName(updateUser.getName());

		return fundWithdrawRecordsVO;
	}


}
