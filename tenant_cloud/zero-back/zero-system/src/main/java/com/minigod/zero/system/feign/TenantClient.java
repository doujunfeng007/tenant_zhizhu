package com.minigod.zero.system.feign;

import com.minigod.zero.system.entity.Tenant;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.system.service.ITenantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
public class TenantClient implements ITenantClient{

	@Resource
	ITenantService tenantService;

	@Override
	@GetMapping(GET_LIST)
	public List<Tenant> List() {
		return tenantService.list();
	}

	@Override
	public List<Tenant> getListByTenantIds(List<String> tenantIds) {
		return tenantService.getListByTenantIds(tenantIds);
	}

	@Override
	public Tenant getByTenantId(String tenantId) {
		return tenantService.getByTenantId(tenantId);
	}

	@Override
	public Tenant getByTenantName(String tenantName) {
		return tenantService.getByTenantName(tenantName);
	}

	@Override
	public Tenant getByTenantDomain(String domain) {
		return tenantService.getByTenantDomain(domain);
	}

	@Override
	public List<Tenant> getLikeTenantName(String tenantName) {
		return tenantService.getLikeTenantName(tenantName);
	}
}
