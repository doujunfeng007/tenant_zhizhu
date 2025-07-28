
package com.minigod.zero.system.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.system.entity.AuthClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME
)
public interface IZeroClientClient {

	String GET_ZERO_CLIENT_List = AppConstant.FEIGN_API_PREFIX + "/getZeroClientList";
	String GET_LIST_BY_CLIENT_IDS = AppConstant.FEIGN_API_PREFIX + "/getListByClientIds";
	String GET_BY_CLIENT_ID = AppConstant.FEIGN_API_PREFIX + "/getByClientId";

	@GetMapping(GET_ZERO_CLIENT_List)
	List<AuthClient> getZeroClientList();

	@GetMapping(GET_LIST_BY_CLIENT_IDS)
	List<AuthClient> getListByClientIds(@RequestParam List<String> clientIds);

	@GetMapping(GET_BY_CLIENT_ID)
	AuthClient getByClientId(@RequestParam String clientId);
}
