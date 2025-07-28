/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerAppSettingsEntity;
import com.minigod.zero.customer.vo.CustomerAppSettingsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户app设置信息表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerAppSettingsMapper extends BaseMapper<CustomerAppSettingsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param appSettings
	 * @return
	 */
	List<CustomerAppSettingsVO> selectCustomerAppSettingsPage(IPage page, CustomerAppSettingsVO appSettings);

	CustomerAppSettingsEntity selectByCustId(Long custId);
}
