package com.minigod.zero.platform.feign;

import com.minigod.zero.platform.feign.IPlatformMsgReadRecordClient;
import com.minigod.zero.platform.service.IPlatformMsgReadRecordService;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.platform.entity.PlatformMsgReadRecordEntity;
import com.minigod.zero.platform.feign.IPlatformMsgReadRecordClient;
import com.minigod.zero.platform.utils.PlatformUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@NonDS
@RestController
@RequiredArgsConstructor
@Slf4j
public class PlatformMsgReadRecordClient implements IPlatformMsgReadRecordClient {

	@Resource
	private IPlatformMsgReadRecordService platformMsgReadRecordService;

	@Override
	public PlatformMsgReadRecordEntity findMsgReadRecord(Long userId, Integer msgCode) {
		Integer clientType = PlatformUtil.getClientType();
		return platformMsgReadRecordService.findMsgReadRecord(userId, msgCode, clientType);
	}
}
