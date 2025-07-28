/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerFundWithdrawRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundWithdrawRecordsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户出金申请信息表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerFundWithdrawRecordsMapper extends BaseMapper<CustomerFundWithdrawRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundWithdrawRecords
	 * @return
	 */
	List<CustomerFundWithdrawRecordsVO> selectCustomerFundWithdrawRecordsPage(IPage page, CustomerFundWithdrawRecordsVO fundWithdrawRecords);


}
