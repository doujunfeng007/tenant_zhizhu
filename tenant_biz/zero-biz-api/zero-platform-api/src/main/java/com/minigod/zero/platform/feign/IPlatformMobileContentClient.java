package com.minigod.zero.platform.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
	value = AppConstant.APPLICATION_PLATFORM_NAME
)
public interface IPlatformMobileContentClient {

	String PUSH_UNSEND_MOBILE_MSG = AppConstant.FEIGN_API_PREFIX + "/pushUnsendMobileMsg";

	/**
	 * 发送定时短信
	 * @return
	 */
	@GetMapping(PUSH_UNSEND_MOBILE_MSG)
	R pushUnsendMobileMsg();

}
