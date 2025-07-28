/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerRealnameVerifyEntity;
import com.minigod.zero.customer.vo.CustomerRealnameVerifyVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户实名认证表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerRealnameVerifyService extends BaseService<CustomerRealnameVerifyEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param realnameVerify
	 * @return
	 */
	IPage<CustomerRealnameVerifyVO> selectCustomerRealnameVerifyPage(IPage<CustomerRealnameVerifyVO> page, CustomerRealnameVerifyVO realnameVerify);


}
