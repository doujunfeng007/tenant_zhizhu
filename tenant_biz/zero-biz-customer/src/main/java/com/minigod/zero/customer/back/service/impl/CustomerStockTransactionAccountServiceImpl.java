/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerStockTransactionAccountService;
import com.minigod.zero.customer.entity.CustomerStockTradingAccountEntity;
import com.minigod.zero.customer.vo.CustomerStockTradingAccountVO;
import com.minigod.zero.customer.mapper.CustomerStockTradingAccountMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 交易账户信息表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerStockTransactionAccountServiceImpl extends BaseServiceImpl<CustomerStockTradingAccountMapper, CustomerStockTradingAccountEntity> implements ICustomerStockTransactionAccountService {

	@Override
	public IPage<CustomerStockTradingAccountVO> selectCustomerStockTransactionAccountPage(IPage<CustomerStockTradingAccountVO> page, CustomerStockTradingAccountVO stockTransactionAccount) {
		return page.setRecords(baseMapper.selectCustomerStockTransactionAccountPage(page, stockTransactionAccount));
	}


}
