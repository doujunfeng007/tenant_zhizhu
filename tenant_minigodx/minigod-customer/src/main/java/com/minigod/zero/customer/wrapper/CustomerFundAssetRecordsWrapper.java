/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.customer.entity.CustomerFundAssetRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundAssetRecordsVO;
import java.util.Objects;

/**
 * 客户资产流水汇总表 包装类,返回视图层所需的字段
 *
 * @author ken
 * @since 2024-04-11
 */
public class CustomerFundAssetRecordsWrapper extends BaseEntityWrapper<CustomerFundAssetRecordsEntity, CustomerFundAssetRecordsVO>  {

	public static CustomerFundAssetRecordsWrapper build() {
		return new CustomerFundAssetRecordsWrapper();
 	}

	@Override
	public CustomerFundAssetRecordsVO entityVO(CustomerFundAssetRecordsEntity fundAssetRecords) {
		CustomerFundAssetRecordsVO fundAssetRecordsVO = Objects.requireNonNull(BeanUtil.copy(fundAssetRecords, CustomerFundAssetRecordsVO.class));

		//User createUser = UserCache.getUser(fundAssetRecords.getCreateUser());
		//User updateUser = UserCache.getUser(fundAssetRecords.getUpdateUser());
		//fundAssetRecordsVO.setCreateUserName(createUser.getName());
		//fundAssetRecordsVO.setUpdateUserName(updateUser.getName());

		return fundAssetRecordsVO;
	}


}
