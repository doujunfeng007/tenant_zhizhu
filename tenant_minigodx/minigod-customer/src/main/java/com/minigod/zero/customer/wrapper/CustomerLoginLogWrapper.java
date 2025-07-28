/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerLoginLogEntity;
import com.minigod.zero.customer.vo.CustomerLoginLogVO;
import java.util.Objects;

/**
 * 登陆日志表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerLoginLogWrapper extends BaseEntityWrapper<CustomerLoginLogEntity, CustomerLoginLogVO>  {

	public static CustomerLoginLogWrapper build() {
		return new CustomerLoginLogWrapper();
 	}

	@Override
	public CustomerLoginLogVO entityVO(CustomerLoginLogEntity loginLog) {
		CustomerLoginLogVO loginLogVO = Objects.requireNonNull(BeanUtil.copy(loginLog, CustomerLoginLogVO.class));

		//User createUser = UserCache.getUser(loginLog.getCreateUser());
		//User updateUser = UserCache.getUser(loginLog.getUpdateUser());
		//loginLogVO.setCreateUserName(createUser.getName());
		//loginLogVO.setUpdateUserName(updateUser.getName());

		return loginLogVO;
	}


}
