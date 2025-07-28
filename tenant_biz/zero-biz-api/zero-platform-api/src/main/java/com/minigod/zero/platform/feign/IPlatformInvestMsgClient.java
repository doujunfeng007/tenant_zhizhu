package com.minigod.zero.platform.feign;

import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
	value = AppConstant.APPLICATION_PLATFORM_NAME
)
public interface IPlatformInvestMsgClient {

	String FIND_INVEST_MSG_FOR_LAST_ONE = AppConstant.FEIGN_API_PREFIX + "/findInvestMsgForLastOne";
	String PUSH_UNSEND_IVEST_MSG = AppConstant.FEIGN_API_PREFIX + "/pushUnsendInvestMsg";

	/**
	 * 查询用户推送消息记录
	 * @param userId
	 * @param msgCode
	 * @return
	 */
	@GetMapping(FIND_INVEST_MSG_FOR_LAST_ONE)
    PlatformInvestMsgEntity findInvestMsgForLastOne(@RequestParam Long userId, @RequestParam Integer msgCode);

	/**
	 * 扫描消息通知表，将定时发送类型中没有发送的数据发送出去
	 * @return
	 */
	@GetMapping(PUSH_UNSEND_IVEST_MSG)
	R pushUnsendInvestMsg();

}
