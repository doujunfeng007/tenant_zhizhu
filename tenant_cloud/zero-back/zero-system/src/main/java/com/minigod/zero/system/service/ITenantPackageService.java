
package com.minigod.zero.system.service;

import com.minigod.zero.system.entity.TenantPackage;
import com.minigod.zero.core.mp.base.BaseService;

/**
 * 租户产品表 服务类
 *
 * @author minigod
 */
public interface ITenantPackageService extends BaseService<TenantPackage> {

    TenantPackage getByTenantId(String tenantId);
}
