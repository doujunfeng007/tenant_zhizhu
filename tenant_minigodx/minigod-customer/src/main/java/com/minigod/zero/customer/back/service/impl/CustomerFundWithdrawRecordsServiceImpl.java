/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerFundWithdrawRecordsService;
import com.minigod.zero.customer.entity.CustomerFundWithdrawRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundWithdrawRecordsVO;
import com.minigod.zero.customer.mapper.CustomerFundWithdrawRecordsMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户出金申请信息表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerFundWithdrawRecordsServiceImpl extends BaseServiceImpl<CustomerFundWithdrawRecordsMapper, CustomerFundWithdrawRecordsEntity> implements ICustomerFundWithdrawRecordsService {

	@Override
	public IPage<CustomerFundWithdrawRecordsVO> selectCustomerFundWithdrawRecordsPage(IPage<CustomerFundWithdrawRecordsVO> page, CustomerFundWithdrawRecordsVO fundWithdrawRecords) {
		return page.setRecords(baseMapper.selectCustomerFundWithdrawRecordsPage(page, fundWithdrawRecords));
	}


}
