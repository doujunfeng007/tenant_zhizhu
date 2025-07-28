/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerFundHisHoldingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundHisHoldingRecordsVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户基金历史持仓表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerFundHisHoldingRecordsService extends BaseService<CustomerFundHisHoldingRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundHisHoldingRecords
	 * @return
	 */
	IPage<CustomerFundHisHoldingRecordsVO> selectCustomerFundHisHoldingRecordsPage(IPage<CustomerFundHisHoldingRecordsVO> page, CustomerFundHisHoldingRecordsVO fundHisHoldingRecords);


}
