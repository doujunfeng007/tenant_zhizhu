/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service;

import com.minigod.zero.customer.entity.CustomerFundAssetRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundAssetRecordsVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户资产流水汇总表 服务类
 *
 * @author ken
 * @since 2024-04-11
 */
public interface ICustomerFundAssetRecordsService extends BaseService<CustomerFundAssetRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundAssetRecords
	 * @return
	 */
	IPage<CustomerFundAssetRecordsVO> selectCustomerFundAssetRecordsPage(IPage<CustomerFundAssetRecordsVO> page, CustomerFundAssetRecordsVO fundAssetRecords);


}
