/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerDeviceInfoEntity;
import com.minigod.zero.customer.vo.CustomerDeviceInfoVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户设备信息 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerDeviceInfoService extends BaseService<CustomerDeviceInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param customerdeviceInfo
	 * @return
	 */
	IPage<CustomerDeviceInfoVO> selectCustomerDeviceInfoPage(IPage<CustomerDeviceInfoVO> page, CustomerDeviceInfoVO customerdeviceInfo);


}
