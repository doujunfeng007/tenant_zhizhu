package com.minigod.zero.manage.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.vo.response.GpSensitiveWordDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 掌上智珠
 * @since 2023/7/17 17:02
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_OMS_NAME
)
public interface ISensitiveWordClient {

	String GPSENSITIVEWORDLIST = AppConstant.FEIGN_API_PREFIX + "/gp_sensitive_word_list";

	/**
	 * 获取智珠的敏感词库
	 * 返回格式 {”0005.HK“:List<"敏感词1","敏感词2">}
	 * @param scope 作用域
	 * @param source 新闻来源
	 * @return
	 */
	@PostMapping(GPSENSITIVEWORDLIST)
	R<List<GpSensitiveWordDto>> getGpSensitiveWordList(@RequestParam Integer scope, @RequestParam(required = false) String source);
}
