/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerBasicInfoEntity;
import com.minigod.zero.customer.vo.CustomerBasicInfoVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户基础资料信息表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerBasicInfoService extends BaseService<CustomerBasicInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param customerbasicInfo
	 * @return
	 */
	IPage<CustomerBasicInfoVO> selectCustomerBasicInfoPage(IPage<CustomerBasicInfoVO> page, CustomerBasicInfoVO customerbasicInfo);


}
