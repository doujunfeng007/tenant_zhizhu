package com.minigod.zero.flow.flowable.flowable.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.bpmn.module.feign.IBpmnFlowClient;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.entity.FlowTable;
import com.minigod.zero.flow.flowable.flowable.service.FlowTableService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName: FlowTableServiceImpl
 * @Description:
 * @Author chenyu
 * @Date 2022/7/26
 * @Version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class FlowTableServiceImpl implements FlowTableService {

    private final IBpmnFlowClient bpmnFlowClient;

    @Override
    public void updateByApplicationId(FlowTable flowTable) {
        log.info("FlowTable:" + JSONObject.toJSONString(flowTable));
        R r = bpmnFlowClient.flowCallBack(flowTable);
        if (!r.isSuccess()) {
            throw new ServiceException("业务处理异常:"+r.getMsg());
        }
    }

    @Override
    public void updateAssign(FlowTable flowTable) {
        log.info("FlowTable:" + JSONObject.toJSONString(flowTable));
        R r = bpmnFlowClient.flowAssignCallback(flowTable);
        if (!r.isSuccess()) {
            throw new ServiceException("业务处理异常:"+r.getMsg());
        }
    }
}
