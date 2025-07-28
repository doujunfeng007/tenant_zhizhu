package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @ClassName: IBpmnAccountClient
 * @Description: 开户业务定时器接口
 * @Author chenyu
 * @Date 2024/2/23
 * @Version 1.0
 */
@FeignClient(
        value = AppConstant.SERVICE_BIZ_BPMN_NAME
)
public interface IAddrClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/addr";
	String GET_ADDRESS = AppConstant.FEIGN_API_PREFIX + "/getAddressName";

	@GetMapping(GET_ADDRESS)
	R getAddressName(@RequestParam("value") String value);


}
