package com.minigod.zero.cms.feign.oms;

import com.minigod.zero.cms.entity.oms.OmsParamsEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface IOmsParamsClient {

	String FIND_KEY_VAL_NO_LOCAL_CACHE = AppConstant.FEIGN_API_PREFIX + "/findKeyValNoLocalCache";
	String FIND_KEY_VAL = AppConstant.FEIGN_API_PREFIX + "/findKeyVal";
	String FIND_ALL_KEY_VAL = AppConstant.FEIGN_API_PREFIX + "/findAllKeyVal";

	/**
	 * 根据模块名称和键名,得到键值，不使用本地VM缓存
	 * @param moduleName
	 * @param keyName
	 * @return
	 */
	@GetMapping(FIND_KEY_VAL_NO_LOCAL_CACHE)
	OmsParamsEntity findKeyValNoLocalCache(@RequestParam String moduleName, @RequestParam String keyName);

	/**
	 * 根据模块名称和键名,得到键值
	 * @param moduleName
	 * @param keyName
	 * @return
	 */
	@GetMapping(FIND_KEY_VAL)
	OmsParamsEntity findKeyVal(@RequestParam String moduleName, @RequestParam String keyName);

	/**
	 * 根据模块名称
	 * @param moduleName
	 * @return
	 */
	@GetMapping(FIND_ALL_KEY_VAL)
	Map<String, OmsParamsEntity> findAllKeyVal(@RequestParam String moduleName);
}
