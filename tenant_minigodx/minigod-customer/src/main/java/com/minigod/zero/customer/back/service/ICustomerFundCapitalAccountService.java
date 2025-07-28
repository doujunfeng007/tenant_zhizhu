/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerFundCapitalAccountEntity;
import com.minigod.zero.customer.vo.CustomerFundCapitalAccountVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 基金账户信息表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerFundCapitalAccountService extends BaseService<CustomerFundCapitalAccountEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundCapitalAccount
	 * @return
	 */
	IPage<CustomerFundCapitalAccountVO> selectCustomerFundCapitalAccountPage(IPage<CustomerFundCapitalAccountVO> page, CustomerFundCapitalAccountVO fundCapitalAccount);


}
