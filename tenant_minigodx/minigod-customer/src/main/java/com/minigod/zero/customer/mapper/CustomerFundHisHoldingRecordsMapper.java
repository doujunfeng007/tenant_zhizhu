/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerBondHoldingRecords;
import com.minigod.zero.customer.entity.CustomerFundHisHoldingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundHisHoldingRecordsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户基金历史持仓表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerFundHisHoldingRecordsMapper extends BaseMapper<CustomerFundHisHoldingRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundHisHoldingRecords
	 * @return
	 */
	List<CustomerFundHisHoldingRecordsVO> selectCustomerFundHisHoldingRecordsPage(IPage page, CustomerFundHisHoldingRecordsVO fundHisHoldingRecords);

	List<CustomerBondHoldingRecords> selectBySubAccountList(@Param("subAccountList") List<String> subAccountList);
}
