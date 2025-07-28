/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerResetLogEntity;
import com.minigod.zero.customer.vo.CustomerResetLogVO;
import java.util.Objects;

/**
 * 客户密码重置日志 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerResetLogWrapper extends BaseEntityWrapper<CustomerResetLogEntity, CustomerResetLogVO>  {

	public static CustomerResetLogWrapper build() {
		return new CustomerResetLogWrapper();
 	}

	@Override
	public CustomerResetLogVO entityVO(CustomerResetLogEntity resetLog) {
		CustomerResetLogVO resetLogVO = Objects.requireNonNull(BeanUtil.copy(resetLog, CustomerResetLogVO.class));

		//User createUser = UserCache.getUser(resetLog.getCreateUser());
		//User updateUser = UserCache.getUser(resetLog.getUpdateUser());
		//resetLogVO.setCreateUserName(createUser.getName());
		//resetLogVO.setUpdateUserName(updateUser.getName());

		return resetLogVO;
	}


}
