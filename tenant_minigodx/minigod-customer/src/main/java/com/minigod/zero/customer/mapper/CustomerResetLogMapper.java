/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerResetLogEntity;
import com.minigod.zero.customer.vo.CustomerResetLogVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户密码重置日志 Mapper 接口
 *
 * @author ken
 * @since 2024-04-11
 */
@DS("customer")
public interface CustomerResetLogMapper extends BaseMapper<CustomerResetLogEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param resetLog
	 * @return
	 */
	List<CustomerResetLogVO> selectCustomerResetLogPage(IPage page, CustomerResetLogVO resetLog);


}
