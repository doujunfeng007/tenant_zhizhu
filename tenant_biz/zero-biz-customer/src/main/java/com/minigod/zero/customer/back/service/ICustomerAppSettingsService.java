/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerAppSettingsEntity;
import com.minigod.zero.customer.vo.CustomerAppSettingsVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户app设置信息表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerAppSettingsService extends BaseService<CustomerAppSettingsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param appSettings
	 * @return
	 */
	IPage<CustomerAppSettingsVO> selectCustomerAppSettingsPage(IPage<CustomerAppSettingsVO> page, CustomerAppSettingsVO appSettings);


}
