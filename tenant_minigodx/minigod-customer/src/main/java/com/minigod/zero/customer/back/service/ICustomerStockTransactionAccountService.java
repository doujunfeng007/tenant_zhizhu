/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerStockTradingAccountEntity;
import com.minigod.zero.customer.vo.CustomerStockTradingAccountVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 交易账户信息表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerStockTransactionAccountService extends BaseService<CustomerStockTradingAccountEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param stockTransactionAccount
	 * @return
	 */
	IPage<CustomerStockTradingAccountVO> selectCustomerStockTransactionAccountPage(IPage<CustomerStockTradingAccountVO> page, CustomerStockTradingAccountVO stockTransactionAccount);


}
