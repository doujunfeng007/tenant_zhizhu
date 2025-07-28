/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerStockOpenAccountInfoEntity;
import com.minigod.zero.customer.vo.CustomerStockOpenAcocuntInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户股票开户资料 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerStockOpenAcocuntInfoMapper extends BaseMapper<CustomerStockOpenAccountInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param stockOpenAcocuntInfo
	 * @return
	 */
	List<CustomerStockOpenAcocuntInfoVO> selectCustomerStockOpenAcocuntInfoPage(IPage page, CustomerStockOpenAcocuntInfoVO stockOpenAcocuntInfo);


}
