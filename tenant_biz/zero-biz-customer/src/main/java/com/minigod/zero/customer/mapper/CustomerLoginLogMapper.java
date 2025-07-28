/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.dto.CustomerLoginLogDTO;
import com.minigod.zero.customer.entity.CustomerLoginLogEntity;
import com.minigod.zero.customer.vo.CustomerLoginLogVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 登陆日志表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerLoginLogMapper extends BaseMapper<CustomerLoginLogEntity> {

	/**
	 * 查询所有登录日志
	 *
	 * @param page
	 * @param loginLog
	 * @return
	 */
	List<CustomerLoginLogVO> selectCustomerLoginLogPage(IPage page, CustomerLoginLogDTO loginLog);

	/**
	 * 查询访客登录日志
	 *
	 * @param page
	 * @param loginLog
	 * @return
	 */
	List<CustomerLoginLogVO> selectVisitorLoginLogPage(IPage page, CustomerLoginLogDTO loginLog);

	/**
	 * 查询开户用户登录日志
	 *
	 * @param page
	 * @param loginLog
	 * @return
	 */
	List<CustomerLoginLogVO> selectAccountUserLoginLogPage(IPage page, CustomerLoginLogDTO loginLog);

	/**
	 * 插入登录日志
	 *
	 * @param customerLoginLogEntity
	 * @return
	 */
	int insertSelective(CustomerLoginLogEntity customerLoginLogEntity);

}
