/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerFundWithdrawRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundWithdrawRecordsVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户出金申请信息表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerFundWithdrawRecordsService extends BaseService<CustomerFundWithdrawRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundWithdrawRecords
	 * @return
	 */
	IPage<CustomerFundWithdrawRecordsVO> selectCustomerFundWithdrawRecordsPage(IPage<CustomerFundWithdrawRecordsVO> page, CustomerFundWithdrawRecordsVO fundWithdrawRecords);


}
