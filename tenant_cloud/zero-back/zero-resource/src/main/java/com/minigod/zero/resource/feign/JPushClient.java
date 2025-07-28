package com.minigod.zero.resource.feign;

import com.minigod.zero.resource.dto.JPushDTO;
import com.minigod.zero.resource.service.IJpushService;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 极光推送 远程调用服务
 *
 * @author Chill
 */
@NonDS
@RestController
public class JPushClient implements IJPushClient {

	@Resource
	private IJpushService jPushService;

	@Override
	@PostMapping(PUSH_MSG_TO_LIST)
	public R saveAndPushMsgToList(JPushDTO jPushDTO) {
		return jPushService.saveAndPushMsgToList(jPushDTO);
	}

	@Override
	@PostMapping(PUSH_MSG_TO_APP)
	public R saveAndPushMsgToApp(JPushDTO jPushDTO) {
		return jPushService.saveAndPushMsgToApp(jPushDTO);
	}

}
