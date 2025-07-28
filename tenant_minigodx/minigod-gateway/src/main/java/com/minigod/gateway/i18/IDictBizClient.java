package com.minigod.gateway.i18;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/12 16:47
 * @description：
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME
)
public interface IDictBizClient {
	String GET_LANGUAGE = AppConstant.FEIGN_API_PREFIX + "/dict-biz/language";
	/**
	 * 获取国际化语言
	 * @param modelName
	 * @return
	 */
	@GetMapping(GET_LANGUAGE)
	R<List<I18nConfig>> getLanguageByModelName(@RequestParam("modelName") String modelName);
}
