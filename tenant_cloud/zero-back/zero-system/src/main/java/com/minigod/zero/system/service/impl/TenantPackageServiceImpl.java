
package com.minigod.zero.system.service.impl;

import com.minigod.zero.system.entity.Tenant;
import com.minigod.zero.system.entity.TenantPackage;
import com.minigod.zero.system.mapper.TenantPackageMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.system.service.ITenantPackageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * 租户产品表 服务实现类
 *
 * @author minigod
 */
@Service
@AllArgsConstructor
public class TenantPackageServiceImpl extends BaseServiceImpl<TenantPackageMapper, TenantPackage> implements ITenantPackageService {

//	ITenantService tenantService;

	@Override
	public TenantPackage getByTenantId(String tenantId) {
//		Tenant tenant = tenantService.getByTenantId(tenantId);
		Tenant tenant = new Tenant();
		return Optional.of(tenant)
			.filter(e -> Objects.nonNull(tenant.getPackageId()))
			.map(e -> this.getById(e.getPackageId()))
			.orElse(null);
	}
}
