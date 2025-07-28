package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

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
public interface IBpmnAccountClient {

    String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/task";
    String CA_AUTH = API_PREFIX + "/caAuth";
    String AML_CHECK = API_PREFIX + "/amlCheck";
    String PLACE = API_PREFIX + "/place";
    String OPEN = API_PREFIX + "/open";

	String API_PREFIX_W8 = API_PREFIX + "/w8";
	String W8_CONFIRM = API_PREFIX_W8 + "/confirm";

    @PostMapping(CA_AUTH)
    R caAuthTask(Map<String, Object> map);

    @PostMapping(AML_CHECK)
    R amlCheckTask(Map<String, Object> map);

    @PostMapping(PLACE)
    R placeTask(Map<String, Object> map);

    @PostMapping(OPEN)
    R openTask(Map<String, Object> map);


	/**
	 * W8协议确认
	 */
	@PostMapping(W8_CONFIRM)
	R w8ConfirmTask(Map<String, Object> map);


}
