/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerAppSettingsEntity;
import com.minigod.zero.customer.vo.CustomerAppSettingsVO;
import java.util.Objects;

/**
 * 客户app设置信息表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerAppSettingsWrapper extends BaseEntityWrapper<CustomerAppSettingsEntity, CustomerAppSettingsVO>  {

	public static CustomerAppSettingsWrapper build() {
		return new CustomerAppSettingsWrapper();
 	}

	@Override
	public CustomerAppSettingsVO entityVO(CustomerAppSettingsEntity appSettings) {
		CustomerAppSettingsVO appSettingsVO = Objects.requireNonNull(BeanUtil.copy(appSettings, CustomerAppSettingsVO.class));

		//User createUser = UserCache.getUser(appSettings.getCreateUser());
		//User updateUser = UserCache.getUser(appSettings.getUpdateUser());
		//appSettingsVO.setCreateUserName(createUser.getName());
		//appSettingsVO.setUpdateUserName(updateUser.getName());

		return appSettingsVO;
	}


}
