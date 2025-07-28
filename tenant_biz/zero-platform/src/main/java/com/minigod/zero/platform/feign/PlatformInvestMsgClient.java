package com.minigod.zero.platform.feign;

import com.minigod.zero.platform.feign.IPlatformInvestMsgClient;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;
import com.minigod.zero.platform.feign.IPlatformInvestMsgClient;
import com.minigod.zero.platform.service.IPlatformInvestMsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@NonDS
@RestController
@RequiredArgsConstructor
@Slf4j
public class PlatformInvestMsgClient  implements IPlatformInvestMsgClient {

	private final IPlatformInvestMsgService platformInvestMsgService;

	@Override
	public PlatformInvestMsgEntity findInvestMsgForLastOne(Long userId, Integer msgCode) {
		return platformInvestMsgService.findInvestMsgForLastOne(userId, msgCode);
	}

	@Override
	public R pushUnsendInvestMsg() {
		platformInvestMsgService.pushUnsendInvestMsg();
		return R.success();
	}
}
