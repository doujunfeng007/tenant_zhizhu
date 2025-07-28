/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import com.minigod.zero.customer.back.service.ICustomerFundAssetRecordsService;
import com.minigod.zero.customer.entity.CustomerFundAssetRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundAssetRecordsVO;
import com.minigod.zero.customer.mapper.CustomerFundAssetRecordsMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户资产流水汇总表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Service
public class CustomerFundAssetRecordsServiceImpl extends BaseServiceImpl<CustomerFundAssetRecordsMapper, CustomerFundAssetRecordsEntity> implements ICustomerFundAssetRecordsService {

	@Override
	public IPage<CustomerFundAssetRecordsVO> selectCustomerFundAssetRecordsPage(IPage<CustomerFundAssetRecordsVO> page, CustomerFundAssetRecordsVO fundAssetRecords) {
		return page.setRecords(baseMapper.selectCustomerFundAssetRecordsPage(page, fundAssetRecords));
	}


}
