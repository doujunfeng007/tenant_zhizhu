
package com.minigod.zero.system.feign;


import com.minigod.zero.system.entity.Dict;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
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
	fallback = IDictClientFallback.class
)
public interface IDictClient {

	String GET_BY_ID = AppConstant.FEIGN_API_PREFIX  + "/dict/get-by-id";
	String GET_VALUE = AppConstant.FEIGN_API_PREFIX  + "/dict/get-value";
	String GET_LABEL = AppConstant.FEIGN_API_PREFIX  + "/dict/get-label";
	String GET_LIST = AppConstant.FEIGN_API_PREFIX  + "/dict/get-list";

	/**
	 * 获取字典实体
	 *
	 * @param id 主键
	 * @return
	 */
	@GetMapping(GET_BY_ID)
	R<Dict> getById(@RequestParam("id") Long id);

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
	 * 获取字典表对应标签
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
	R<List<Dict>> getList(@RequestParam("code") String code);

}
