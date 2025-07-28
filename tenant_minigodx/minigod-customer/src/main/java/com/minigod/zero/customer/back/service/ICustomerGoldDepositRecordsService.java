/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerGoldDepositRecordsEntity;
import com.minigod.zero.customer.vo.CustomerGoldDepositRecordsVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户入金申请信息表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerGoldDepositRecordsService extends BaseService<CustomerGoldDepositRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param goldDepositRecords
	 * @return
	 */
	IPage<CustomerGoldDepositRecordsVO> selectCustomerGoldDepositRecordsPage(IPage<CustomerGoldDepositRecordsVO> page, CustomerGoldDepositRecordsVO goldDepositRecords);


}
