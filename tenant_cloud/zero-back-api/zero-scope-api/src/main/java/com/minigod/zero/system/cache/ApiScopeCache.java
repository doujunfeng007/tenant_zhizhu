
package com.minigod.zero.system.cache;

import com.minigod.zero.system.feign.IApiScopeClient;
import com.minigod.zero.core.cache.utils.CacheUtil;
import com.minigod.zero.core.tool.utils.SpringUtil;
import com.minigod.zero.core.tool.utils.StringPool;

import java.util.List;

import static com.minigod.zero.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 接口权限缓存
 *
 * @author Chill
 */
public class ApiScopeCache {

	private static final String SCOPE_CACHE_CODE = "apiScope:code:";

	private static IApiScopeClient apiScopeClient;

	private static IApiScopeClient getApiScopeClient() {
		if (apiScopeClient == null) {
			apiScopeClient = SpringUtil.getBean(IApiScopeClient.class);
		}
		return apiScopeClient;
	}

	/**
	 * 获取接口权限地址
	 *
	 * @param roleId 角色id
	 * @return permissions
	 */
	public static List<String> permissionPath(String roleId) {
		List<String> permissions = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CODE, roleId, List.class, Boolean.FALSE);
		if (permissions == null) {
			permissions = getApiScopeClient().permissionPath(roleId);
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CODE, roleId, permissions);
		}
		return permissions;
	}

	/**
	 * 获取接口权限信息
	 *
	 * @param permission 权限编号
	 * @param roleId     角色id
	 * @return permissions
	 */
	public static List<String> permissionCode(String permission, String roleId) {
		List<String> permissions = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CODE, permission + StringPool.COLON + roleId, List.class, Boolean.FALSE);
		if (permissions == null) {
			permissions = getApiScopeClient().permissionCode(permission, roleId);
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CODE, permission + StringPool.COLON + roleId, permissions);
		}
		return permissions;
	}

}
