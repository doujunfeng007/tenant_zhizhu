/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerFundHisHoldingRecordsService;
import com.minigod.zero.customer.entity.CustomerFundHisHoldingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundHisHoldingRecordsVO;
import com.minigod.zero.customer.mapper.CustomerFundHisHoldingRecordsMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户基金历史持仓表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerFundHisHoldingRecordsServiceImpl extends BaseServiceImpl<CustomerFundHisHoldingRecordsMapper, CustomerFundHisHoldingRecordsEntity> implements ICustomerFundHisHoldingRecordsService {

	@Override
	public IPage<CustomerFundHisHoldingRecordsVO> selectCustomerFundHisHoldingRecordsPage(IPage<CustomerFundHisHoldingRecordsVO> page, CustomerFundHisHoldingRecordsVO fundHisHoldingRecords) {
		return page.setRecords(baseMapper.selectCustomerFundHisHoldingRecordsPage(page, fundHisHoldingRecords));
	}


}
