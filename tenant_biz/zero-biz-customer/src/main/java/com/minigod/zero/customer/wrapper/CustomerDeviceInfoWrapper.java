/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerDeviceInfoEntity;
import com.minigod.zero.customer.vo.CustomerDeviceInfoVO;
import java.util.Objects;

/**
 * 客户设备信息 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerDeviceInfoWrapper extends BaseEntityWrapper<CustomerDeviceInfoEntity, CustomerDeviceInfoVO>  {

	public static CustomerDeviceInfoWrapper build() {
		return new CustomerDeviceInfoWrapper();
 	}

	@Override
	public CustomerDeviceInfoVO entityVO(CustomerDeviceInfoEntity customerdeviceInfo) {
		CustomerDeviceInfoVO customerdeviceInfoVO = Objects.requireNonNull(BeanUtil.copy(customerdeviceInfo, CustomerDeviceInfoVO.class));

		//User createUser = UserCache.getUser(customerdeviceInfo.getCreateUser());
		//User updateUser = UserCache.getUser(customerdeviceInfo.getUpdateUser());
		//customerdeviceInfoVO.setCreateUserName(createUser.getName());
		//customerdeviceInfoVO.setUpdateUserName(updateUser.getName());

		return customerdeviceInfoVO;
	}


}
