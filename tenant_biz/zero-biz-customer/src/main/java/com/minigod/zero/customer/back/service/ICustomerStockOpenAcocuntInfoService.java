/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerStockOpenAccountInfoEntity;
import com.minigod.zero.customer.vo.CustomerStockOpenAcocuntInfoVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户股票开户资料 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerStockOpenAcocuntInfoService extends BaseService<CustomerStockOpenAccountInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param stockOpenAcocuntInfo
	 * @return
	 */
	IPage<CustomerStockOpenAcocuntInfoVO> selectCustomerStockOpenAcocuntInfoPage(IPage<CustomerStockOpenAcocuntInfoVO> page, CustomerStockOpenAcocuntInfoVO stockOpenAcocuntInfo);


}
