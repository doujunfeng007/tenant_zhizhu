/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.entity.CustomerFinancingAccountEntity;
import com.minigod.zero.customer.vo.CustomerFinancingAccountVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Date;

/**
 * 客户理财账户表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerFinancingAccountService extends BaseService<CustomerFinancingAccountEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param financingAccount
	 * @return
	 */
	IPage<CustomerFinancingAccountVO> selectCustomerFinancingAccountPage(IPage<CustomerFinancingAccountVO> page, CustomerFinancingAccountVO financingAccount);


}
