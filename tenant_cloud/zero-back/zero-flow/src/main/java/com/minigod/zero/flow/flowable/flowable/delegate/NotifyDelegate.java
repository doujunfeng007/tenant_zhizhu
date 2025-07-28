package com.minigod.zero.flow.flowable.flowable.delegate;

import com.minigod.zero.bpmn.module.feign.IBpmnFlowClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.entity.FlowNotify;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: NotifyDelegate
 * @Description: 通知委托
 * @Author chenyu
 * @Date 2024/3/5
 * @Version 1.0
 */
@Slf4j
@Component("notifyService")
@AllArgsConstructor
public class NotifyDelegate implements JavaDelegate {
    private final IBpmnFlowClient bpmnFlowClient;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        log.info("-------------通知委托方法租户----------------" + delegateExecution.getTenantId());
        log.info("-------------通知委托方法当前节点-------------" + delegateExecution.getCurrentFlowElement().getName());
        log.info("-------------通知委托方法变量----------------" + delegateExecution.getVariables());
        FlowNotify flowNotify = new FlowNotify();
        flowNotify.setTenantId(delegateExecution.getTenantId());
        flowNotify.setBusinessKey(delegateExecution.getProcessDefinitionId().split(":")[0]);
        flowNotify.setNode(delegateExecution.getCurrentFlowElement().getName());
        flowNotify.setApplicationId((String) delegateExecution.getVariable(TaskConstants.APPLICATION_ID));
        flowNotify.setVariables(delegateExecution.getVariables());
        R r = bpmnFlowClient.flowNotifyCallback(flowNotify.getBusinessKey(), flowNotify);
        if(!r.isSuccess()){
            log.error("回调通知失败:"+r.getMsg());
        }
    }
}
