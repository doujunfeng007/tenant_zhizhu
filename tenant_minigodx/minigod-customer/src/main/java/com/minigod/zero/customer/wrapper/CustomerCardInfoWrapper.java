/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerCardInfoEntity;
import com.minigod.zero.customer.vo.CustomerCardInfoVO;
import java.util.Objects;

/**
 * 客户银行卡记录 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerCardInfoWrapper extends BaseEntityWrapper<CustomerCardInfoEntity, CustomerCardInfoVO>  {

	public static CustomerCardInfoWrapper build() {
		return new CustomerCardInfoWrapper();
 	}

	@Override
	public CustomerCardInfoVO entityVO(CustomerCardInfoEntity customercardInfo) {
		CustomerCardInfoVO customercardInfoVO = Objects.requireNonNull(BeanUtil.copy(customercardInfo, CustomerCardInfoVO.class));

		//User createUser = UserCache.getUser(customercardInfo.getCreateUser());
		//User updateUser = UserCache.getUser(customercardInfo.getUpdateUser());
		//customercardInfoVO.setCreateUserName(createUser.getName());
		//customercardInfoVO.setUpdateUserName(updateUser.getName());

		return customercardInfoVO;
	}


}
