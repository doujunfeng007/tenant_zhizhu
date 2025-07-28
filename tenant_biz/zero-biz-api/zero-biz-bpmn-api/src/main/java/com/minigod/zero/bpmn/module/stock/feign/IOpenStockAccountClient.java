package com.minigod.zero.bpmn.module.stock.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_BPMN_NAME
)
public interface IOpenStockAccountClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/stock";

	String OPEN_STOCK_ACCOUNT_JOB = API_PREFIX + "/openAccount";

	/**
	 * 增开股票
	 *
	 * @param map
	 * @return
	 */
	@PostMapping(OPEN_STOCK_ACCOUNT_JOB)
	R openStockAccountJob(Map<String, Object> map);

}
