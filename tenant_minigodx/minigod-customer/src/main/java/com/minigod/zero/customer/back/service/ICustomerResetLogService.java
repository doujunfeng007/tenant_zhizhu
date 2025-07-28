/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerResetLogEntity;
import com.minigod.zero.customer.vo.CustomerResetLogVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户密码重置日志 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerResetLogService extends BaseService<CustomerResetLogEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param resetLog
	 * @return
	 */
	IPage<CustomerResetLogVO> selectCustomerResetLogPage(IPage<CustomerResetLogVO> page, CustomerResetLogVO resetLog);


}
