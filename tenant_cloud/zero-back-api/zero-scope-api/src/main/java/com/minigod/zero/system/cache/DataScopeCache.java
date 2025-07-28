
package com.minigod.zero.system.cache;

import com.minigod.zero.system.feign.IDataScopeClient;
import com.minigod.zero.core.cache.utils.CacheUtil;
import com.minigod.zero.core.datascope.model.DataScopeModel;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.SpringUtil;
import com.minigod.zero.core.tool.utils.StringPool;
import com.minigod.zero.core.tool.utils.StringUtil;

import java.util.List;

import static com.minigod.zero.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 数据权限缓存
 *
 * @author Chill
 */
public class DataScopeCache {

	private static final String SCOPE_CACHE_CODE = "dataScope:code:";
	private static final String SCOPE_CACHE_CLASS = "dataScope:class:";
	private static final String DEPT_CACHE_ANCESTORS = "dept:ancestors:";

	private static IDataScopeClient dataScopeClient;

	private static IDataScopeClient getDataScopeClient() {
		if (dataScopeClient == null) {
			dataScopeClient = SpringUtil.getBean(IDataScopeClient.class);
		}
		return dataScopeClient;
	}

	/**
	 * 获取数据权限
	 *
	 * @param mapperId 数据权限mapperId
	 * @param roleId   用户角色集合
	 * @return DataScopeModel
	 */
	public static DataScopeModel getDataScopeByMapper(String mapperId, String roleId) {
		DataScopeModel dataScope = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CLASS, mapperId + StringPool.COLON + roleId, DataScopeModel.class, Boolean.FALSE);
		if (dataScope == null || !dataScope.getSearched()) {
			dataScope = getDataScopeClient().getDataScopeByMapper(mapperId, roleId);
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CLASS, mapperId + StringPool.COLON + roleId, dataScope);
		}
		return dataScope != null && StringUtil.isNotBlank(dataScope.getResourceCode()) ? dataScope : null;
	}

	/**
	 * 获取数据权限
	 *
	 * @param code 数据权限资源编号
	 * @return DataScopeModel
	 */
	public static DataScopeModel getDataScopeByCode(String code) {
		DataScopeModel dataScope = CacheUtil.get(SYS_CACHE, SCOPE_CACHE_CODE, code, DataScopeModel.class, Boolean.FALSE);
		if (dataScope == null || !dataScope.getSearched()) {
			dataScope = getDataScopeClient().getDataScopeByCode(code);
			CacheUtil.put(SYS_CACHE, SCOPE_CACHE_CODE, code, dataScope);
		}
		return StringUtil.isNotBlank(dataScope.getResourceCode()) ? dataScope : null;
	}

	/**
	 * 获取部门子级
	 *
	 * @param deptId 部门id
	 * @return deptIds
	 */
	public static List<Long> getDeptAncestors(Long deptId) {
		List ancestors = CacheUtil.get(SYS_CACHE, DEPT_CACHE_ANCESTORS, deptId, List.class);
		if (CollectionUtil.isEmpty(ancestors)) {
			ancestors = getDataScopeClient().getDeptAncestors(deptId);
			CacheUtil.put(SYS_CACHE, DEPT_CACHE_ANCESTORS, deptId, ancestors);
		}
		return ancestors;
	}
}
