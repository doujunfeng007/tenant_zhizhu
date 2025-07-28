package com.minigod.zero.resource.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.resource.dto.JPushDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Zhe.Xiao
 */
@FeignClient(
	value = AppConstant.APPLICATION_RESOURCE_NAME,
	fallback = IJPushClientFallback.class
)
public interface IJPushClient {

	String PUSH_MSG_TO_LIST = AppConstant.FEIGN_API_PREFIX + "/pushMsgToList";
	String PUSH_MSG_TO_APP = AppConstant.FEIGN_API_PREFIX + "/pushMsgToApp";

	/**
	 * 极光推送 给多个设备推送
	 */
	@PostMapping(PUSH_MSG_TO_LIST)
	R saveAndPushMsgToList(@RequestBody JPushDTO jPushDTO);

	/**
	 * 极光推送 推送消息给所有设备
	 */
	@PostMapping(PUSH_MSG_TO_APP)
	R saveAndPushMsgToApp(@RequestBody JPushDTO jPushDTO);

}
