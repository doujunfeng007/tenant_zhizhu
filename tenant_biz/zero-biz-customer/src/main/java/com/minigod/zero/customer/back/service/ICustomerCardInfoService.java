/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerCardInfoEntity;
import com.minigod.zero.customer.vo.CustomerCardInfoVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户银行卡记录 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerCardInfoService extends BaseService<CustomerCardInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param customercardInfo
	 * @return
	 */
	IPage<CustomerCardInfoVO> selectCustomerCardInfoPage(IPage<CustomerCardInfoVO> page, CustomerCardInfoVO customercardInfo);


}
