package com.minigod.zero.flow.core.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName: IWorkFlowClient
 * @Description:
 * @Author chenyu
 * @Date 2024/1/25
 * @Version 1.0
 */
@FeignClient(
        value = AppConstant.APPLICATION_FLOW_NAME,
        fallback = IWorkFlowFallback.class
)
public interface IWorkFlowClient {
}
