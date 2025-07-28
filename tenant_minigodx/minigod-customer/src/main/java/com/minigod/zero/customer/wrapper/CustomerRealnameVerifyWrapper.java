/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerRealnameVerifyEntity;
import com.minigod.zero.customer.vo.CustomerRealnameVerifyVO;
import java.util.Objects;

/**
 * 客户实名认证表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerRealnameVerifyWrapper extends BaseEntityWrapper<CustomerRealnameVerifyEntity, CustomerRealnameVerifyVO>  {

	public static CustomerRealnameVerifyWrapper build() {
		return new CustomerRealnameVerifyWrapper();
 	}

	@Override
	public CustomerRealnameVerifyVO entityVO(CustomerRealnameVerifyEntity realnameVerify) {
		CustomerRealnameVerifyVO realnameVerifyVO = Objects.requireNonNull(BeanUtil.copy(realnameVerify, CustomerRealnameVerifyVO.class));

		//User createUser = UserCache.getUser(realnameVerify.getCreateUser());
		//User updateUser = UserCache.getUser(realnameVerify.getUpdateUser());
		//realnameVerifyVO.setCreateUserName(createUser.getName());
		//realnameVerifyVO.setUpdateUserName(updateUser.getName());

		return realnameVerifyVO;
	}


}
