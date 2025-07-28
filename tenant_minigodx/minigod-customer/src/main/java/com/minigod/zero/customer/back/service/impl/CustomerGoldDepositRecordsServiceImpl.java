/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerGoldDepositRecordsService;
import com.minigod.zero.customer.entity.CustomerGoldDepositRecordsEntity;
import com.minigod.zero.customer.vo.CustomerGoldDepositRecordsVO;
import com.minigod.zero.customer.mapper.CustomerGoldDepositRecordsMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户入金申请信息表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerGoldDepositRecordsServiceImpl extends BaseServiceImpl<CustomerGoldDepositRecordsMapper, CustomerGoldDepositRecordsEntity> implements ICustomerGoldDepositRecordsService {

	@Override
	public IPage<CustomerGoldDepositRecordsVO> selectCustomerGoldDepositRecordsPage(IPage<CustomerGoldDepositRecordsVO> page, CustomerGoldDepositRecordsVO goldDepositRecords) {
		return page.setRecords(baseMapper.selectCustomerGoldDepositRecordsPage(page, goldDepositRecords));
	}


}
