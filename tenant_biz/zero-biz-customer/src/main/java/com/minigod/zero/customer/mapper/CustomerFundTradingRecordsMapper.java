/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.dto.CustomerFundTradingAppListDTO;
import com.minigod.zero.customer.dto.CustomerFundTradingBackListDTO;
import com.minigod.zero.customer.entity.CustomerFundHoldingRecordsEntity;
import com.minigod.zero.customer.entity.CustomerFundTradingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerBondTradingRecordsListVO;
import com.minigod.zero.customer.vo.CustomerFundHoldingListVO;
import com.minigod.zero.customer.vo.CustomerFundTradingRecordsListVO;
import com.minigod.zero.customer.vo.CustomerFundTradingRecordsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 客户交易流水汇总表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerFundTradingRecordsMapper extends BaseMapper<CustomerFundTradingRecordsEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param fundTransactionRecords
	 * @return
	 */
	List<CustomerFundTradingRecordsVO> selectCustomerFundTransactionRecordsPage(IPage page, CustomerFundTradingRecordsVO fundTransactionRecords);

	/**
	 * 后台查询基金流水
	 * @param vo
	 * @return
	 */
	List<CustomerFundTradingBackListDTO> selectTradingBackList(CustomerFundTradingRecordsListVO vo);

	List<CustomerFundTradingAppListDTO> selectTradingAppList(@Param("userId") Long userId, @Param("type") String type, @Param("time") Date time, @Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize);

}
