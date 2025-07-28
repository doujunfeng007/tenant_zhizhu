
package com.minigod.zero.system.feign;

import com.minigod.zero.core.datascope.model.DataScopeModel;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 数据权限Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME,
	fallback = IDataScopeClientFallback.class
)
public interface IDataScopeClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/data-scope";
	String GET_DATA_SCOPE_BY_MAPPER = API_PREFIX + "/by-mapper";
	String GET_DATA_SCOPE_BY_CODE = API_PREFIX + "/by-code";
	String GET_DEPT_ANCESTORS = API_PREFIX + "/dept-ancestors";

	/**
	 * 获取数据权限
	 *
	 * @param mapperId 数据权限mapperId
	 * @param roleId   用户角色集合
	 * @return DataScopeModel
	 */
	@GetMapping(GET_DATA_SCOPE_BY_MAPPER)
	DataScopeModel getDataScopeByMapper(@RequestParam("mapperId") String mapperId, @RequestParam("roleId") String roleId);

	/**
	 * 获取数据权限
	 *
	 * @param code 数据权限资源编号
	 * @return DataScopeModel
	 */
	@GetMapping(GET_DATA_SCOPE_BY_CODE)
	DataScopeModel getDataScopeByCode(@RequestParam("code") String code);

	/**
	 * 获取部门子级
	 *
	 * @param deptId 部门id
	 * @return deptIds
	 */
	@GetMapping(GET_DEPT_ANCESTORS)
	List<Long> getDeptAncestors(@RequestParam("deptId") Long deptId);


}
