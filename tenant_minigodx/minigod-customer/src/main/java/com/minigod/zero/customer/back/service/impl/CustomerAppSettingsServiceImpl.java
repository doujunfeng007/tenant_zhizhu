/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerAppSettingsService;
import com.minigod.zero.customer.entity.CustomerAppSettingsEntity;
import com.minigod.zero.customer.vo.CustomerAppSettingsVO;
import com.minigod.zero.customer.mapper.CustomerAppSettingsMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户app设置信息表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerAppSettingsServiceImpl extends BaseServiceImpl<CustomerAppSettingsMapper, CustomerAppSettingsEntity> implements ICustomerAppSettingsService {

	@Override
	public IPage<CustomerAppSettingsVO> selectCustomerAppSettingsPage(IPage<CustomerAppSettingsVO> page, CustomerAppSettingsVO appSettings) {
		return page.setRecords(baseMapper.selectCustomerAppSettingsPage(page, appSettings));
	}


}
