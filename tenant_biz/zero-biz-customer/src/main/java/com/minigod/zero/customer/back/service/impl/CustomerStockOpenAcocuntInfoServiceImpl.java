/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerStockOpenAcocuntInfoService;
import com.minigod.zero.customer.entity.CustomerStockOpenAccountInfoEntity;
import com.minigod.zero.customer.vo.CustomerStockOpenAcocuntInfoVO;
import com.minigod.zero.customer.mapper.CustomerStockOpenAcocuntInfoMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户股票开户资料 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerStockOpenAcocuntInfoServiceImpl extends BaseServiceImpl<CustomerStockOpenAcocuntInfoMapper, CustomerStockOpenAccountInfoEntity> implements ICustomerStockOpenAcocuntInfoService {

	@Override
	public IPage<CustomerStockOpenAcocuntInfoVO> selectCustomerStockOpenAcocuntInfoPage(IPage<CustomerStockOpenAcocuntInfoVO> page, CustomerStockOpenAcocuntInfoVO stockOpenAcocuntInfo) {
		return page.setRecords(baseMapper.selectCustomerStockOpenAcocuntInfoPage(page, stockOpenAcocuntInfo));
	}


}
