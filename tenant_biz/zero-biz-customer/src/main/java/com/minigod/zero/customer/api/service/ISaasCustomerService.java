package com.minigod.zero.customer.api.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.vo.CustomerInfoVO;

/**
 * @author chen
 * @ClassName ISaasCustomerService.java
 * @Description TODO
 * @createTime 2024年09月13日 18:47:00
 */
public interface ISaasCustomerService {
	/**
	 * Saas端注册
	 * @param customerInfo
	 * @return
	 */
	R register(CustomerInfoVO customerInfo);
}
