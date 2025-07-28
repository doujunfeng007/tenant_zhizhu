
package com.minigod.zero.system.feign;

import com.minigod.zero.system.entity.*;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.system.entity.*;
import com.minigod.zero.system.vo.FuncConfigVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author Chill
 */
@Component
public class ISysClientFallback implements ISysClient {

	@Override
	public R<Menu> getMenu(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Dept> getDept(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getDeptIds(String tenantId, String deptNames) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getDeptIdsByFuzzy(String tenantId, String deptNames) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getDeptName(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<String>> getDeptNames(String deptIds) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<Dept>> getDeptChild(Long deptId) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Post> getPost(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getPostIds(String tenantId, String postNames) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getPostIdsByFuzzy(String tenantId, String postNames) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getPostName(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<String>> getPostNames(String postIds) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Role> getRole(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getRoleIds(String tenantId, String roleNames) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getRoleName(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getRoleAlias(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<String>> getRoleNames(String roleIds) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<String>> getRoleAliases(String roleIds) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Tenant> getTenant(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Tenant> getTenant(String tenantId) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Tenant> getTenant(String tenantId, String domain) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<TenantPackage> getTenantPackage(String tenantId) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<FuncConfigVO>> getFuncConfig(String tenantId) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Param> getParam(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getParamValue(String paramKey) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<Region> getRegion(String code) {
		return R.fail("获取数据失败");
	}


}
