/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerFundTransactionAccountService;
import com.minigod.zero.customer.entity.CustomerFundTradingAccountEntity;
import com.minigod.zero.customer.vo.CustomerFundTradingAccountVO;
import com.minigod.zero.customer.mapper.CustomerFundTradingAccountMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 基金交易账户信息表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerFundTransactionAccountServiceImpl extends BaseServiceImpl<CustomerFundTradingAccountMapper, CustomerFundTradingAccountEntity> implements ICustomerFundTransactionAccountService {

	@Override
	public IPage<CustomerFundTradingAccountVO> selectCustomerFundTransactionAccountPage(IPage<CustomerFundTradingAccountVO> page, CustomerFundTradingAccountVO fundTransactionAccount) {
		return page.setRecords(baseMapper.selectCustomerFundTransactionAccountPage(page, fundTransactionAccount));
	}


}
