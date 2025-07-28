/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerRealnameVerifyService;
import com.minigod.zero.customer.entity.CustomerRealnameVerifyEntity;
import com.minigod.zero.customer.vo.CustomerRealnameVerifyVO;
import com.minigod.zero.customer.mapper.CustomerRealnameVerifyMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户实名认证表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerRealnameVerifyServiceImpl extends BaseServiceImpl<CustomerRealnameVerifyMapper, CustomerRealnameVerifyEntity> implements ICustomerRealnameVerifyService {

	@Override
	public IPage<CustomerRealnameVerifyVO> selectCustomerRealnameVerifyPage(IPage<CustomerRealnameVerifyVO> page, CustomerRealnameVerifyVO realnameVerify) {
		return page.setRecords(baseMapper.selectCustomerRealnameVerifyPage(page, realnameVerify));
	}


}
