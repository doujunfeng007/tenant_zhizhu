/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerCardInfoService;
import com.minigod.zero.customer.entity.CustomerCardInfoEntity;
import com.minigod.zero.customer.vo.CustomerCardInfoVO;
import com.minigod.zero.customer.mapper.CustomerCardInfoMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户银行卡记录 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerCardInfoServiceImpl extends BaseServiceImpl<CustomerCardInfoMapper, CustomerCardInfoEntity> implements ICustomerCardInfoService {

	@Override
	public IPage<CustomerCardInfoVO> selectCustomerCardInfoPage(IPage<CustomerCardInfoVO> page, CustomerCardInfoVO customercardInfo) {
		return page.setRecords(baseMapper.selectCustomerCardInfoPage(page, customercardInfo));
	}


}
