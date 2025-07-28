/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerFinancingAccountEntity;
import com.minigod.zero.customer.vo.CustomerFinancingAccountVO;
import java.util.Objects;

/**
 * 客户理财账户表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerFinancingAccountWrapper extends BaseEntityWrapper<CustomerFinancingAccountEntity, CustomerFinancingAccountVO>  {

	public static CustomerFinancingAccountWrapper build() {
		return new CustomerFinancingAccountWrapper();
 	}

	@Override
	public CustomerFinancingAccountVO entityVO(CustomerFinancingAccountEntity financingAccount) {
		CustomerFinancingAccountVO financingAccountVO = Objects.requireNonNull(BeanUtil.copy(financingAccount, CustomerFinancingAccountVO.class));

		//User createUser = UserCache.getUser(financingAccount.getCreateUser());
		//User updateUser = UserCache.getUser(financingAccount.getUpdateUser());
		//financingAccountVO.setCreateUserName(createUser.getName());
		//financingAccountVO.setUpdateUserName(updateUser.getName());

		return financingAccountVO;
	}


}
