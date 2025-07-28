/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerFundTradingAccountEntity;
import com.minigod.zero.customer.vo.CustomerFundTradingAccountVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 基金交易账户信息表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerFundTransactionAccountService extends BaseService<CustomerFundTradingAccountEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundTransactionAccount
	 * @return
	 */
	IPage<CustomerFundTradingAccountVO> selectCustomerFundTransactionAccountPage(IPage<CustomerFundTradingAccountVO> page, CustomerFundTradingAccountVO fundTransactionAccount);


}
