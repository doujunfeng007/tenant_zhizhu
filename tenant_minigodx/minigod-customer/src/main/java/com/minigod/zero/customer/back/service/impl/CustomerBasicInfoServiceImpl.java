/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerBasicInfoService;
import com.minigod.zero.customer.entity.CustomerBasicInfoEntity;
import com.minigod.zero.customer.vo.CustomerBasicInfoVO;
import com.minigod.zero.customer.mapper.CustomerBasicInfoMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户基础资料信息表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerBasicInfoServiceImpl extends BaseServiceImpl<CustomerBasicInfoMapper, CustomerBasicInfoEntity> implements ICustomerBasicInfoService {

	@Override
	public IPage<CustomerBasicInfoVO> selectCustomerBasicInfoPage(IPage<CustomerBasicInfoVO> page, CustomerBasicInfoVO customerbasicInfo) {
		return page.setRecords(baseMapper.selectCustomerBasicInfoPage(page, customerbasicInfo));
	}


}
