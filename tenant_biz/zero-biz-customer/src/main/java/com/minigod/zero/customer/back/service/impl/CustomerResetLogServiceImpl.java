/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerResetLogService;
import com.minigod.zero.customer.entity.CustomerResetLogEntity;
import com.minigod.zero.customer.vo.CustomerResetLogVO;
import com.minigod.zero.customer.mapper.CustomerResetLogMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户密码重置日志 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerResetLogServiceImpl extends BaseServiceImpl<CustomerResetLogMapper, CustomerResetLogEntity> implements ICustomerResetLogService {

	@Override
	public IPage<CustomerResetLogVO> selectCustomerResetLogPage(IPage<CustomerResetLogVO> page, CustomerResetLogVO resetLog) {
		return page.setRecords(baseMapper.selectCustomerResetLogPage(page, resetLog));
	}


}
