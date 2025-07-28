
package com.minigod.zero.system.feign;

import com.minigod.zero.system.entity.Tenant;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public interface ITenantClient {

	String GET_LIST = AppConstant.FEIGN_API_PREFIX + "/getList";
	String GET_LIST_BY_TENANT_IDS = AppConstant.FEIGN_API_PREFIX + "/getListByTenantIds";
	String GET_BY_TENANT_ID = AppConstant.FEIGN_API_PREFIX + "/getByTenantId";
	String GET_BY_TENANT_NAME = AppConstant.FEIGN_API_PREFIX + "/getByTenantName";
	String GET_BY_TENANT_DOMAIN = AppConstant.FEIGN_API_PREFIX + "/getByTenantDomain";
	String GET_LIKE_TENANT_NAME = AppConstant.FEIGN_API_PREFIX + "/getLikeTenantName";

	@GetMapping(GET_LIST)
	List<Tenant> List();

	@GetMapping(GET_LIST_BY_TENANT_IDS)
	List<Tenant> getListByTenantIds(@RequestParam List<String> tenantIds);

	@GetMapping(GET_BY_TENANT_ID)
	Tenant getByTenantId(@RequestParam String tenantId);

	@GetMapping(GET_BY_TENANT_NAME)
	Tenant getByTenantName(@RequestParam String tenantName);

	@GetMapping(GET_BY_TENANT_DOMAIN)
	Tenant getByTenantDomain(@RequestParam("domain") String domain);

	@GetMapping(GET_LIKE_TENANT_NAME)
	List<Tenant> getLikeTenantName(@RequestParam String tenantName);
}
