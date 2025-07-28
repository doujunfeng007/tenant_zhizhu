package com.minigod.zero.manage.feign;

import com.minigod.zero.manage.dto.ComPlexSimpleReplaceDto;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

/**
 * @author 掌上智珠
 * @since 2023/8/3 11:17
 */

@FeignClient(
	value = AppConstant.SERVICE_BIZ_OMS_NAME
)
public interface IComplexTransSimpleClient {

	String GETWORD = AppConstant.FEIGN_API_PREFIX + "/replace_words";

	/**
	 * 获取所有需要替换得词汇
	 * @return
	 */
	@GetMapping(GETWORD)
	R<List<ComPlexSimpleReplaceDto>> getReplaceWords();
}
