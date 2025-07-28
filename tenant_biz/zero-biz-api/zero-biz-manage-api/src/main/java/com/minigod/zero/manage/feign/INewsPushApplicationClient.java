package com.minigod.zero.manage.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_OMS_NAME
)
public interface INewsPushApplicationClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/pushUnSendMsg";

	@GetMapping(API_PREFIX)
	void pushUnSendMsg();
}
