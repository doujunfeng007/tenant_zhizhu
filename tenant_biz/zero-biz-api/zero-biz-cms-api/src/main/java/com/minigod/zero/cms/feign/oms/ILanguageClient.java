package com.minigod.zero.cms.feign.oms;

import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface ILanguageClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/i18n";
	String GET_TEXT = API_PREFIX + "/get_text";
	String GET_TEXT2 = API_PREFIX + "/get_text2";
	String GET_TRADITIONAL = API_PREFIX + "/get_traditional";

	/**
	 * 获取配置文件中key所对应的国际化语言
	 * @param msgKey
	 * @return
	 */
	@GetMapping(GET_TEXT)
	String getTextByLang(@RequestHeader("Accept-Language") String lang, @RequestParam String msgKey);

	/**
	 * 获取配置文件中key所对应的国际化信息,存在占位符通过args数组进行数据填充
	 * @param msgKey
	 * @param args
	 * @return
	 */
	@GetMapping(GET_TEXT2)
	String getTextByLang2(@RequestHeader("Accept-Language") String lang, @RequestParam String msgKey, @RequestParam Object[] args);


	/**
	 * 获取繁体文本
	 * @param simplified
	 * @return
	 */
	@PostMapping(GET_TRADITIONAL)
	String getTraditional(@RequestParam String simplified);

}
