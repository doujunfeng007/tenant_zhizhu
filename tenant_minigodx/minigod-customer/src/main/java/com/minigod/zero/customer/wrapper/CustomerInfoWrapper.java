/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import com.minigod.zero.customer.vo.CustomerInfoVO;
import java.util.Objects;

/**
 * 客户信息表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerInfoWrapper extends BaseEntityWrapper<CustomerInfoEntity, CustomerInfoVO>  {

	public static CustomerInfoWrapper build() {
		return new CustomerInfoWrapper();
 	}

	@Override
	public CustomerInfoVO entityVO(CustomerInfoEntity customerInfo) {
		CustomerInfoVO customerInfoVO = Objects.requireNonNull(BeanUtil.copy(customerInfo, CustomerInfoVO.class));

		//User createUser = UserCache.getUser(customerInfo.getCreateUser());
		//User updateUser = UserCache.getUser(customerInfo.getUpdateUser());
		//customerInfoVO.setCreateUserName(createUser.getName());
		//customerInfoVO.setUpdateUserName(updateUser.getName());

		return customerInfoVO;
	}


}
