package com.minigod.zero.resource.proxy;

import com.minigod.zero.resource.dto.JPushDTO;
import com.minigod.zero.resource.service.IJpushService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 极光推送
 *
 * @author Zhe.Xiao
 */
@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX)
@Slf4j
public class JpushProxyController {

	@Resource
	private IJpushService jPushService;

	@SneakyThrows
	@PostMapping(value = "/jPushToList")
	public R jPushToList(@RequestBody JPushDTO jPushDTO) {
		return jPushService.saveAndPushMsgToList(jPushDTO);
	}

	@SneakyThrows
	@PostMapping(value = "/jPushToApp")
	public R jPushToApp(@RequestBody JPushDTO jPushDTO) {
		return jPushService.saveAndPushMsgToApp(jPushDTO);
	}

}
