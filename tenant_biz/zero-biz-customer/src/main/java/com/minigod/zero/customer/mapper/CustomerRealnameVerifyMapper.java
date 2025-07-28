/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerRealnameVerifyEntity;
import com.minigod.zero.customer.vo.CustomerRealnameVerifyVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户实名认证表 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerRealnameVerifyMapper extends BaseMapper<CustomerRealnameVerifyEntity> {
	int deleteByPrimaryKey(Long id);

	int insert(CustomerRealnameVerifyEntity record);

	int insertSelective(CustomerRealnameVerifyEntity record);

	CustomerRealnameVerifyEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(CustomerRealnameVerifyEntity record);

	int updateByPrimaryKey(CustomerRealnameVerifyEntity record);
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param realnameVerify
	 * @return
	 */
	List<CustomerRealnameVerifyVO> selectCustomerRealnameVerifyPage(IPage page, CustomerRealnameVerifyVO realnameVerify);


	CustomerRealnameVerifyEntity selectByCustIdAndIdCard(@Param("custId") Long custId, @Param("idCard") String idCard);

	CustomerRealnameVerifyEntity selectByCustId(@Param("custId") Long custId);
}
