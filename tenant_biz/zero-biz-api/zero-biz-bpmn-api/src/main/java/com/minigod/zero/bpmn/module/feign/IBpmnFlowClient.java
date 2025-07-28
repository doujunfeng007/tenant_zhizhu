package com.minigod.zero.bpmn.module.feign;

import com.minigod.zero.flow.core.entity.FlowNotify;
import com.minigod.zero.flow.core.entity.FlowTable;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName: IFlowClient
 * @Description: 流程回调
 * @Author chenyu
 * @Date 2024/2/2
 * @Version 1.0
 */
@FeignClient(
        value = AppConstant.SERVICE_BIZ_BPMN_NAME
)
public interface IBpmnFlowClient {

    String API_PREFIX = AppConstant.FEIGN_API_PREFIX;
    String FLOW_CALLBACK = API_PREFIX + "/flow_callback";
    String FLOW_ASSIGN_CALLBACK = API_PREFIX + "/flow_assign_callback";
    String FLOW_NOTIFY_CALLBACK = API_PREFIX + "/flow_notify_callback/{businessKey}";


    @PostMapping(FLOW_CALLBACK)
    R flowCallBack(@RequestBody FlowTable flowTable);

    @PostMapping(FLOW_ASSIGN_CALLBACK)
    R flowAssignCallback(@RequestBody FlowTable flowTable);

    @PostMapping(FLOW_NOTIFY_CALLBACK)
    R flowNotifyCallback(@PathVariable("businessKey")String businessKey, @RequestBody FlowNotify flowNotify);


}
