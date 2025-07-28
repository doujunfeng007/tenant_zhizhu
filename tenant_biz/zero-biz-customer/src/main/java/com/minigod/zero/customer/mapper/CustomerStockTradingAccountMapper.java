/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerStockTradingAccountEntity;
import com.minigod.zero.customer.vo.CustomerStockTradingAccountVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 交易账户信息表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerStockTradingAccountMapper extends BaseMapper<CustomerStockTradingAccountEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param stockTransactionAccount
	 * @return
	 */
	List<CustomerStockTradingAccountVO> selectCustomerStockTransactionAccountPage(IPage page, CustomerStockTradingAccountVO stockTransactionAccount);


}
