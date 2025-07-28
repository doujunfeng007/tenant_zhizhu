/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerFundCapitalAccountEntity;
import com.minigod.zero.customer.vo.CustomerFundCapitalAccountVO;
import java.util.Objects;

/**
 * 基金账户信息表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerFundCapitalAccountWrapper extends BaseEntityWrapper<CustomerFundCapitalAccountEntity, CustomerFundCapitalAccountVO>  {

	public static CustomerFundCapitalAccountWrapper build() {
		return new CustomerFundCapitalAccountWrapper();
 	}

	@Override
	public CustomerFundCapitalAccountVO entityVO(CustomerFundCapitalAccountEntity fundCapitalAccount) {
		CustomerFundCapitalAccountVO fundCapitalAccountVO = Objects.requireNonNull(BeanUtil.copy(fundCapitalAccount, CustomerFundCapitalAccountVO.class));

		//User createUser = UserCache.getUser(fundCapitalAccount.getCreateUser());
		//User updateUser = UserCache.getUser(fundCapitalAccount.getUpdateUser());
		//fundCapitalAccountVO.setCreateUserName(createUser.getName());
		//fundCapitalAccountVO.setUpdateUserName(updateUser.getName());

		return fundCapitalAccountVO;
	}


}
