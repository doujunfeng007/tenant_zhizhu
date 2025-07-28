/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.dto.CustomerLoginLogDTO;
import com.minigod.zero.customer.entity.CustomerLoginLogEntity;
import com.minigod.zero.customer.vo.CustomerLoginLogVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 登陆日志表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerLoginLogService extends BaseService<CustomerLoginLogEntity> {

	/**
	 * 查询所有登录日志
	 *
	 * @param page
	 * @param loginLog
	 * @return
	 */
	IPage<CustomerLoginLogVO> selectCustomerLoginLogPage(IPage<CustomerLoginLogVO> page, CustomerLoginLogDTO loginLog);

	/**
	 * 查询游客登录日志
	 *
	 * @param page
	 * @param loginLog
	 * @return
	 */
	IPage<CustomerLoginLogVO> selectVisitorLoginLogPage(IPage<CustomerLoginLogVO> page, CustomerLoginLogDTO loginLog);

	/**
	 * 查询开户用户登录日志
	 *
	 * @param page
	 * @param loginLog
	 * @return
	 */
	IPage<CustomerLoginLogVO> selectAccountUserLoginLogPage(IPage<CustomerLoginLogVO> page, CustomerLoginLogDTO loginLog);
}
