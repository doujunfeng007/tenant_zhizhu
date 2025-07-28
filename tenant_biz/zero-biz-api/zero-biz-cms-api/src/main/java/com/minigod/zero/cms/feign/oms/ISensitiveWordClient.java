package com.minigod.zero.cms.feign.oms;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 掌上智珠
 * @since 2023/7/17 17:02
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface ISensitiveWordClient {

	String CHECK = AppConstant.FEIGN_API_PREFIX + "/check_sensitive_word";

	String SENSITIVEWORDLIST = AppConstant.FEIGN_API_PREFIX + "/sensitive_word_list";

	String FILTERSENSITIVEWORD = AppConstant.FEIGN_API_PREFIX + "/filter";

	/**
	 * 检验该词语是不是敏感词
	 * @param checkWord
	 * @param scope
	 * @return
	 */
	@PostMapping(CHECK)
	R<Boolean> check(@RequestParam String checkWord,@RequestParam Integer scope);


	/**
	 * 返回文本中包含的敏感词列表
	 * @param checkWord
	 * @param scope
	 * @return
	 */
	@PostMapping(SENSITIVEWORDLIST)
	R<List<String>> sensitiveWordList(@RequestParam String checkWord, @RequestParam Integer scope);

	/**
	 * 对敏感词进行替换
	 * @param checkWord
	 * @param scope
	 * @param replace
	 * @return
	 */
	@PostMapping(FILTERSENSITIVEWORD)
	R<String> filterWord(@RequestParam String checkWord, @RequestParam Integer scope,@RequestParam Character replace);

}
