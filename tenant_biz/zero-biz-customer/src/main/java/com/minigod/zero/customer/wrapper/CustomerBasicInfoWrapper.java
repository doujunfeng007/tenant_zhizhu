/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerBasicInfoEntity;
import com.minigod.zero.customer.vo.CustomerBasicInfoVO;
import java.util.Objects;

/**
 * 客户基础资料信息表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerBasicInfoWrapper extends BaseEntityWrapper<CustomerBasicInfoEntity, CustomerBasicInfoVO>  {

	public static CustomerBasicInfoWrapper build() {
		return new CustomerBasicInfoWrapper();
 	}

	@Override
	public CustomerBasicInfoVO entityVO(CustomerBasicInfoEntity customerbasicInfo) {
		CustomerBasicInfoVO customerbasicInfoVO = Objects.requireNonNull(BeanUtil.copy(customerbasicInfo, CustomerBasicInfoVO.class));

		//User createUser = UserCache.getUser(customerbasicInfo.getCreateUser());
		//User updateUser = UserCache.getUser(customerbasicInfo.getUpdateUser());
		//customerbasicInfoVO.setCreateUserName(createUser.getName());
		//customerbasicInfoVO.setUpdateUserName(updateUser.getName());

		return customerbasicInfoVO;
	}


}
