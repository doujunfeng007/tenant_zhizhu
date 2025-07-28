
package com.minigod.zero.system.feign;


import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.system.entity.I18nConfigEntity;
import com.minigod.zero.system.entity.I18nLanguageEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME,
	fallback = IDictBizClientFallback.class
)
public interface IDictBizClient {

	String GET_BY_ID = AppConstant.FEIGN_API_PREFIX + "/dict-biz/get-by-id";
	String GET_VALUE = AppConstant.FEIGN_API_PREFIX + "/dict-biz/get-value";
	String GET_VALUE_LANG = AppConstant.FEIGN_API_PREFIX + "/dict-biz/get-value-lang";
	String GET_LABEL = AppConstant.FEIGN_API_PREFIX + "/dict-biz/get-label";
	String GET_LIST = AppConstant.FEIGN_API_PREFIX + "/dict-biz/get-list";
	String GET_LIST_TENANT_ID = AppConstant.FEIGN_API_PREFIX + "/dict-biz/get-list-tenant-id";
	String GET_ALL = AppConstant.FEIGN_API_PREFIX + "/dict-biz/get_all";
	String GET_LANGUAGE = AppConstant.FEIGN_API_PREFIX + "/dict-biz/language";

	/**
	 * 获取字典实体
	 *
	 * @param id 主键
	 * @return
	 */
	@GetMapping(GET_BY_ID)
	R<DictBiz> getById(@RequestParam("id") Long id);

	/**
	 * 获取字典表对应值
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return
	 */
	@GetMapping(GET_VALUE)
	R<String> getValue(@RequestParam("code") String code, @RequestParam("dictKey") String dictKey);
	/**
	 * 获取字典表对应值
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @param lang    语言
	 * @return
	 */
	@GetMapping(GET_VALUE_LANG)
	R<String> getValue(@RequestParam("code") String code, @RequestParam("dictKey") String dictKey, @RequestParam("lang") String lang);

	/**
	 * 获取字典表对应标签
	 *
	 * @param code
	 * @param dictLabel
	 * @return
	 */
	@GetMapping(GET_LABEL)
	R<String> getLabel(@RequestParam("code") String code, @RequestParam("dictLabel") String dictLabel);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return
	 */
	@GetMapping(GET_LIST)
	R<List<DictBiz>> getList(@RequestParam("code") String code);

	/**
	 * 获取所有可用的
	 *
	 * @return
	 */
	@GetMapping(GET_ALL)
	R<List<DictBiz>> getAll();

	/**
	 * 获取字典表根据租户ID
	 *
	 * @param tenantId
	 * @param code
	 * @return
	 */
	@GetMapping(GET_LIST_TENANT_ID)
	R<List<DictBiz>> getListByTenantId(@RequestParam("tenantId") String tenantId, @RequestParam("code") String code);

	/**
	 * 获取国际化语言
	 * @param modelName
	 * @return
	 */
	@GetMapping(GET_LANGUAGE)
	R<List<I18nConfigEntity>> getLanguageByModelName(@RequestParam("modelName") String modelName);
}
