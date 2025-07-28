package com.minigod.zero.customer.api.service;

import com.minigod.zero.core.tool.api.R;

/**
 * @author chen
 * @ClassName ITenantsCustomerService.java
 * @Description 租户端service
 * @createTime 2024年09月06日 18:51:00
 */
public interface ITenantsCustomerService {
	/**
	 * 查询用户信息
	 * @param custId
	 * @return
	 */
	R queryCustomerDetail(Long custId);
}
