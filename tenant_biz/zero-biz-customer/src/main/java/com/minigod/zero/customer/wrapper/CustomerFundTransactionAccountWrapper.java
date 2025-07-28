/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerFundTradingAccountEntity;
import com.minigod.zero.customer.vo.CustomerFundTradingAccountVO;
import java.util.Objects;

/**
 * 基金交易账户信息表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerFundTransactionAccountWrapper extends BaseEntityWrapper<CustomerFundTradingAccountEntity, CustomerFundTradingAccountVO>  {

	public static CustomerFundTransactionAccountWrapper build() {
		return new CustomerFundTransactionAccountWrapper();
 	}

	@Override
	public CustomerFundTradingAccountVO entityVO(CustomerFundTradingAccountEntity fundTransactionAccount) {
		CustomerFundTradingAccountVO fundTransactionAccountVO = Objects.requireNonNull(BeanUtil.copy(fundTransactionAccount, CustomerFundTradingAccountVO.class));

		//User createUser = UserCache.getUser(fundTransactionAccount.getCreateUser());
		//User updateUser = UserCache.getUser(fundTransactionAccount.getUpdateUser());
		//fundTransactionAccountVO.setCreateUserName(createUser.getName());
		//fundTransactionAccountVO.setUpdateUserName(updateUser.getName());

		return fundTransactionAccountVO;
	}


}
