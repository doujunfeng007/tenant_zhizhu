/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerStockOpenAccountInfoEntity;
import com.minigod.zero.customer.vo.CustomerStockOpenAcocuntInfoVO;
import java.util.Objects;

/**
 * 客户股票开户资料 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerStockOpenAcocuntInfoWrapper extends BaseEntityWrapper<CustomerStockOpenAccountInfoEntity, CustomerStockOpenAcocuntInfoVO>  {

	public static CustomerStockOpenAcocuntInfoWrapper build() {
		return new CustomerStockOpenAcocuntInfoWrapper();
 	}

	@Override
	public CustomerStockOpenAcocuntInfoVO entityVO(CustomerStockOpenAccountInfoEntity stockOpenAcocuntInfo) {
		CustomerStockOpenAcocuntInfoVO stockOpenAcocuntInfoVO = Objects.requireNonNull(BeanUtil.copy(stockOpenAcocuntInfo, CustomerStockOpenAcocuntInfoVO.class));

		//User createUser = UserCache.getUser(stockOpenAcocuntInfo.getCreateUser());
		//User updateUser = UserCache.getUser(stockOpenAcocuntInfo.getUpdateUser());
		//stockOpenAcocuntInfoVO.setCreateUserName(createUser.getName());
		//stockOpenAcocuntInfoVO.setUpdateUserName(updateUser.getName());

		return stockOpenAcocuntInfoVO;
	}


}
