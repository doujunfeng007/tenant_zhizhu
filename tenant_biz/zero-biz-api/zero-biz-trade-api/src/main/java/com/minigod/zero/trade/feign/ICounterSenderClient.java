package com.minigod.zero.trade.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;

/**
 * @author:yanghu.luo
 * @create: 2023-04-26 10:14
 * @Description:  序号重置
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_TRADE_NAME
)
public interface ICounterSenderClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/sender";

	String SENDER_ID_RESET = API_PREFIX + "/reset";

	/**
	 * 重置senderId
	 * @return
	 */
	@GetMapping(value =SENDER_ID_RESET)
	R senderIdReset();
}
