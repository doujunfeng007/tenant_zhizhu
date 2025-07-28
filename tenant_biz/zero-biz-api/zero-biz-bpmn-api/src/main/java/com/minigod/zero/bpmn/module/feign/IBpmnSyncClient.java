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
public interface IBpmnSyncClient {

    String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/task";
    String ACCOUNT_PUSH = API_PREFIX + "/accountPush";
    String DEPOSIT_PUSH = API_PREFIX + "/depositPush";

    @PostMapping(ACCOUNT_PUSH)
    R accountPushTask(Map<String, Object> map);

    @PostMapping(DEPOSIT_PUSH)
    R depositPushTask(Map<String,Object> map);

}
