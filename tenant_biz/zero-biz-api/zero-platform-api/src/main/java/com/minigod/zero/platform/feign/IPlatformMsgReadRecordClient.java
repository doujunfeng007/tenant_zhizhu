package com.minigod.zero.platform.feign;

import com.minigod.zero.platform.entity.PlatformMsgReadRecordEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
	value = AppConstant.APPLICATION_PLATFORM_NAME
)
public interface IPlatformMsgReadRecordClient {

	String FIND_MSG_READ_RECORD = AppConstant.FEIGN_API_PREFIX + "/findMsgReadRecord";

	/**
	 * 查询用户消息读取记录表
	 * @param userId
	 * @param msgCode
	 * @return
	 */
	@GetMapping(FIND_MSG_READ_RECORD)
    PlatformMsgReadRecordEntity findMsgReadRecord(@RequestParam Long userId, @RequestParam Integer msgCode);
}
