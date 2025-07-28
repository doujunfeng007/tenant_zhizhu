package com.minigod.zero.bpmn.module.flow.feign;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.bpmn.module.flow.service.IFlowService;
import com.minigod.zero.bpmn.module.feign.IBpmnFlowClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.entity.FlowNotify;
import com.minigod.zero.flow.core.entity.FlowTable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: FlowClient
 * @Description:
 * @Author chenyu
 * @Date 2024/2/2
 * @Version 1.0
 */
@Slf4j
@RestController
@AllArgsConstructor
public class BpmnFlowClient implements IBpmnFlowClient {

    private final IFlowService flowService;

    @Override
    @PostMapping(FLOW_CALLBACK)
    public R flowCallBack(FlowTable flowTable) {
        log.info("流程回调更新:"+ JSONObject.toJSONString(flowTable));
		if (flowTable.getApplicationId()!=null) {
			flowService.updateUserApplicationId(flowTable);
		}else {
			log.info("流程回调失败: 无预约号");
		}
        return R.success();
    }

    @Override
    @PostMapping(FLOW_ASSIGN_CALLBACK)
    public R flowAssignCallback(FlowTable flowTable) {
        log.info("受理人更新:"+ JSONObject.toJSONString(flowTable));
        flowService.updateAssign(flowTable);
        return R.success();
    }

    @Override
    public R flowNotifyCallback(String businessKey, FlowNotify flowNotify) {
        flowService.notifyCallback(businessKey,flowNotify);
        return R.success();
    }

}
