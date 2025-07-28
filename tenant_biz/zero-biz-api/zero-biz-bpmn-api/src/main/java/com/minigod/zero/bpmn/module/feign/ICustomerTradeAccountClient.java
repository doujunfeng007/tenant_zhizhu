package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.bpmn.module.feign.dto.CustomerOpenAccountDTO;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author：eric
 * @Date：2024/4/26 11:00
 * @description：专业投资者PI认者通过后同步客户级别
 */
@FeignClient(value = "minigod-customer")
public interface ICustomerTradeAccountClient {

	@PostMapping("/customer/open_account")
	R openAccount(@RequestBody CustomerOpenAccountDTO customerOpenAccount);

}
