/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerGoldDepositRecordsEntity;
import com.minigod.zero.customer.vo.CustomerGoldDepositRecordsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户入金申请信息表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerGoldDepositRecordsMapper extends BaseMapper<CustomerGoldDepositRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param goldDepositRecords
	 * @return
	 */
	List<CustomerGoldDepositRecordsVO> selectCustomerGoldDepositRecordsPage(IPage page, CustomerGoldDepositRecordsVO goldDepositRecords);


}
