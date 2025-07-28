package com.minigod.zero.platform.feign;

import com.minigod.zero.platform.feign.IPlatformMobileContentClient;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.feign.IPlatformMobileContentClient;
import com.minigod.zero.platform.service.IPlatformMobileContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@NonDS
@RestController
@RequiredArgsConstructor
@Slf4j
public class PlatformMobileContentClient implements IPlatformMobileContentClient {

	private final IPlatformMobileContentService platformMobileContentService;

	@Override
	public R pushUnsendMobileMsg() {
		platformMobileContentService.pushUnsendMobileMsg();
		return R.success();
	}
}
