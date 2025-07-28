/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerFundCapitalAccountService;
import com.minigod.zero.customer.entity.CustomerFundCapitalAccountEntity;
import com.minigod.zero.customer.vo.CustomerFundCapitalAccountVO;
import com.minigod.zero.customer.mapper.CustomerFundCapitalAccountMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 基金账户信息表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerFundCapitalAccountServiceImpl extends BaseServiceImpl<CustomerFundCapitalAccountMapper, CustomerFundCapitalAccountEntity> implements ICustomerFundCapitalAccountService {

	@Override
	public IPage<CustomerFundCapitalAccountVO> selectCustomerFundCapitalAccountPage(IPage<CustomerFundCapitalAccountVO> page, CustomerFundCapitalAccountVO fundCapitalAccount) {
		return page.setRecords(baseMapper.selectCustomerFundCapitalAccountPage(page, fundCapitalAccount));
	}


}
