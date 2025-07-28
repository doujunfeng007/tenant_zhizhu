
package com.minigod.zero.system.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.system.entity.Tenant;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
@InterceptorIgnore(tenantLine = "true")
public interface TenantMapper extends BaseMapper<Tenant> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tenant
	 * @return
	 */
	List<Tenant> selectTenantPage(IPage page, Tenant tenant);

	/**
	 * 租户id查询租户信息
	 * @param tenantId
	 * @return
	 */
	Tenant selectByTenantId(String tenantId);

}
