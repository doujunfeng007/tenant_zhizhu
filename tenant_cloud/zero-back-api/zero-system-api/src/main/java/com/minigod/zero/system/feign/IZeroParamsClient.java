
package com.minigod.zero.system.feign;

import com.minigod.zero.system.entity.Param;
import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_SYSTEM_NAME
)
public interface IZeroParamsClient {

	String GET_DETAIL = AppConstant.FEIGN_API_PREFIX + "/getDetail";

	@GetMapping(GET_DETAIL)
    Param getDetail(@RequestParam String paramName, @RequestParam String paramKey);
}
