/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerFundAssetRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundAssetRecordsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户资产流水汇总表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerFundAssetRecordsMapper extends BaseMapper<CustomerFundAssetRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundAssetRecords
	 * @return
	 */
	List<CustomerFundAssetRecordsVO> selectCustomerFundAssetRecordsPage(IPage page, CustomerFundAssetRecordsVO fundAssetRecords);


}
