/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerCardInfoEntity;
import com.minigod.zero.customer.vo.CustomerCardInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户银行卡记录 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerCardInfoMapper extends BaseMapper<CustomerCardInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param customercardInfo
	 * @return
	 */
	List<CustomerCardInfoVO> selectCustomerCardInfoPage(IPage page, CustomerCardInfoVO customercardInfo);


}
