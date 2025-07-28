/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerFundTradingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundTradingRecordsVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户交易流水汇总表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerFundTransactionRecordsService extends BaseService<CustomerFundTradingRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundTransactionRecords
	 * @return
	 */
	IPage<CustomerFundTradingRecordsVO> selectCustomerFundTransactionRecordsPage(IPage<CustomerFundTradingRecordsVO> page, CustomerFundTradingRecordsVO fundTransactionRecords);


}
