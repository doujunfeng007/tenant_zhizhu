package com.minigod.zero.customer.fegin;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.common.UserLoginLogClientConstants;
import com.minigod.zero.customer.entity.CustomerIpAddress;
import com.minigod.zero.customer.entity.CustomerLoginLogEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 游客用户登录日志记录
 */
@FeignClient(value = "minigod-customer")
public interface UserLoginLogClient {
	/**
	 * 登陆日志表 新增
	 */
	@PostMapping(UserLoginLogClientConstants.USER_LOGIN_LOG_INSERT)
	R save(@RequestBody CustomerLoginLogEntity loginLog);

	/**
	 * 登陆日志表 详情
	 *
	 * @param ip
	 * @return
	 */
	@GetMapping(UserLoginLogClientConstants.USER_LOGIN_IP_DETAIL)
	R<CustomerIpAddress> findIpAddress(@RequestParam("ip") String ip);
}
