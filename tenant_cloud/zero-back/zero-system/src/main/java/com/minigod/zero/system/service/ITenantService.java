
package com.minigod.zero.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.system.entity.Tenant;
import com.minigod.zero.system.entity.TenantPackage;
import com.minigod.zero.core.mp.base.BaseService;

import java.util.Date;
import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface ITenantService extends BaseService<Tenant> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tenant
	 * @return
	 */
	IPage<Tenant> selectTenantPage(IPage<Tenant> page, Tenant tenant);

	/**
	 * 根据租户编号获取实体
	 *
	 * @param tenantId
	 * @return
	 */
	Tenant getByTenantId(String tenantId);

	/**
	 * 根据租户编号获取实体
	 *
	 * @param tenantId
	 * @param domain
	 * @return
	 */
	Tenant getByTenantDomain(String tenantId, String domain);
	/**
	 * 根据租户编号获取实体
	 *
	 * @param domain
	 * @param domain
	 * @return
	 */
	Tenant getByTenantDomain(String domain);
	/**
	 * 新增
	 *
	 * @param tenant
	 * @return
	 */
	boolean submitTenant(Tenant tenant);

	/**
	 * 删除
	 *
	 * @param ids
	 * @return
	 */
	boolean removeTenant(List<Long> ids);

	/**
	 * 配置租户授权
	 *
	 * @param accountNumber
	 * @param expireTime
	 * @param ids
	 * @return
	 */
	boolean setting(Integer accountNumber, Date expireTime, String ids);

	/**
	 * 获取tenantPackage
	 * @param tenantId
	 * @return
	 */
	TenantPackage getPackage(String tenantId);

	/**
	 * 根据tenantIds查询
	 * @param tenantIds
	 * @return
	 */
    List<Tenant> getListByTenantIds(List<String> tenantIds);

	/**
	 * 根据tenantName查询
	 * @param tenantName
	 * @return
	 */
	Tenant getByTenantName(String tenantName);

	/**
	 * 根据tenantName模糊查询
	 * @param tenantName
	 * @return
	 */
	List<Tenant> getLikeTenantName(String tenantName);
}
