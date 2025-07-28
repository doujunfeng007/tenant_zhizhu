/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerFundHoldingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundHoldingRecordsVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户基金持仓表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerFundHoldingRecordsService {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundHoldingRecords
	 * @return
	 */
	IPage<CustomerFundHoldingRecordsVO> selectCustomerFundHoldingRecordsPage(IPage<CustomerFundHoldingRecordsVO> page, CustomerFundHoldingRecordsVO fundHoldingRecords);


}
