package com.minigod.zero.manage.feign;

import com.minigod.zero.manage.vo.request.SensitiveWordLogSaveVo;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;

/**
 * @author 掌上智珠
 * @since 2023/7/25 1:47
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_OMS_NAME
)
public interface ISensitiveWordLogClient {
	String SAVEBATCH = AppConstant.FEIGN_API_PREFIX + "/save_batch";

	@PostMapping(SAVEBATCH)
	R saveBatch(@RequestBody ArrayList<SensitiveWordLogSaveVo> sensitiveWordLogSaveVo);

}
