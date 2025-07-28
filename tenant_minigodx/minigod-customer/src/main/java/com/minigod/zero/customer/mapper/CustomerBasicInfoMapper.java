/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerBasicInfoEntity;
import com.minigod.zero.customer.vo.CustomerBasicInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户基础资料信息表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerBasicInfoMapper extends BaseMapper<CustomerBasicInfoEntity> {
	int deleteByPrimaryKey(Long id);

	int insert(CustomerBasicInfoEntity record);

	int insertSelective(CustomerBasicInfoEntity record);

	CustomerBasicInfoEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(CustomerBasicInfoEntity record);

	int updateByPrimaryKey(CustomerBasicInfoEntity record);
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param customerbasicInfo
	 * @return
	 */
	List<CustomerBasicInfoVO> selectCustomerBasicInfoPage(IPage page, CustomerBasicInfoVO customerbasicInfo);

	/**
	 * 查询开户信息
	 * @param cusId
	 * @return
	 */
	CustomerBasicInfoEntity selectByCustId(Long cusId);


	List<String> getUserEmails(List<Long> userIds);

}
