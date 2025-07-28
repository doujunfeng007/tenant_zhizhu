/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerStockTradingAccountEntity;
import com.minigod.zero.customer.vo.CustomerStockTradingAccountVO;
import java.util.Objects;

/**
 * 交易账户信息表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerStockTransactionAccountWrapper extends BaseEntityWrapper<CustomerStockTradingAccountEntity, CustomerStockTradingAccountVO>  {

	public static CustomerStockTransactionAccountWrapper build() {
		return new CustomerStockTransactionAccountWrapper();
 	}

	@Override
	public CustomerStockTradingAccountVO entityVO(CustomerStockTradingAccountEntity stockTransactionAccount) {
		CustomerStockTradingAccountVO stockTransactionAccountVO = Objects.requireNonNull(BeanUtil.copy(stockTransactionAccount, CustomerStockTradingAccountVO.class));

		//User createUser = UserCache.getUser(stockTransactionAccount.getCreateUser());
		//User updateUser = UserCache.getUser(stockTransactionAccount.getUpdateUser());
		//stockTransactionAccountVO.setCreateUserName(createUser.getName());
		//stockTransactionAccountVO.setUpdateUserName(updateUser.getName());

		return stockTransactionAccountVO;
	}


}
