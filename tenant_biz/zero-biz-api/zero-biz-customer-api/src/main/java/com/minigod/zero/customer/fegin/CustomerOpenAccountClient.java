package com.minigod.zero.customer.fegin;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.common.CustomerClientConstants;
import com.minigod.zero.customer.dto.CustomerOpenAccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/8 9:35
 * @description：
 */

@FeignClient(value = "minigod-customer")
public interface CustomerOpenAccountClient {

	@GetMapping(CustomerClientConstants.CUSTOMER_OPEN_ACCOUNT)
	R openAccount(@RequestBody CustomerOpenAccountDTO customerOpenAccount);
}
