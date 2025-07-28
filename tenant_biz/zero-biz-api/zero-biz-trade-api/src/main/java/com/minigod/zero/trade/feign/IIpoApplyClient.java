package com.minigod.zero.trade.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = AppConstant.SERVICE_BIZ_TRADE_NAME)
public interface IIpoApplyClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/ipo";
	String SUB_QUEUE_APPLY = API_PREFIX + "/subQueueApply";
	String CANCEL_QUEUE_APPLY = API_PREFIX + "/cancelQueueApply";

	@GetMapping(SUB_QUEUE_APPLY)
	void subQueueApply();

	@GetMapping(CANCEL_QUEUE_APPLY)
	void cancelQueueApply();
}
