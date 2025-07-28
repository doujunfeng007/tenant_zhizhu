package com.minigod.zero.trade.feign;

import com.minigod.zero.trade.entity.CustOperationLogEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author:yanghu.luo
 * @create: 2023-07-14 14:14
 * @Description: 日志记录
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_TRADE_NAME
)
public interface ICustOperationLogClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/cust_log";
	String OPERATION_LOG = API_PREFIX + "/operation_log";

	/**
	 *  操作日志记录
	 */
	@PostMapping(OPERATION_LOG)
	R operationLog(@RequestBody CustOperationLogEntity eustOperationLog);
}
