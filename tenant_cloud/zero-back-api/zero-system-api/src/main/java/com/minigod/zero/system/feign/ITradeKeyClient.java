
package com.minigod.zero.system.feign;

import com.minigod.zero.system.entity.TradeKey;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME
)
public interface ITradeKeyClient {

	String GET_BY_TENANT_ID = AppConstant.FEIGN_API_PREFIX + "/getTradeKeyByTenantId";


	/**
	 * 通过租户id查询交易配置详情
	 * @param tenantId
	 * @return
	 */
	@GetMapping(GET_BY_TENANT_ID)
	TradeKey getTradeKeyByTenantId(@RequestParam String tenantId,@RequestParam String counterType);

}
