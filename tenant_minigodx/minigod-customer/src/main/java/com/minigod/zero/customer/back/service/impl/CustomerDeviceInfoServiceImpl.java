/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerDeviceInfoService;
import com.minigod.zero.customer.entity.CustomerDeviceInfoEntity;
import com.minigod.zero.customer.vo.CustomerDeviceInfoVO;
import com.minigod.zero.customer.mapper.CustomerDeviceInfoMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户设备信息 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerDeviceInfoServiceImpl extends BaseServiceImpl<CustomerDeviceInfoMapper, CustomerDeviceInfoEntity> implements ICustomerDeviceInfoService {

	@Override
	public IPage<CustomerDeviceInfoVO> selectCustomerDeviceInfoPage(IPage<CustomerDeviceInfoVO> page, CustomerDeviceInfoVO customerdeviceInfo) {
		return page.setRecords(baseMapper.selectCustomerDeviceInfoPage(page, customerdeviceInfo));
	}


}
