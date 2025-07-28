package com.minigod.zero.bpmn.module.flow.service.impl;

import com.minigod.zero.bpmn.module.flow.mapper.FlowMapper;
import com.minigod.zero.bpmn.module.flow.service.IFlowService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.SpringUtil;
import com.minigod.zero.flow.core.entity.FlowNotify;
import com.minigod.zero.flow.core.entity.FlowTable;
import com.minigod.zero.flow.core.enums.FLowBusinessEnum;
import com.minigod.zero.flow.core.service.FlowNotifyService;
import com.minigod.zero.system.feign.IDictClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: FlowServiceImpl
 * @Description:
 * @Author chenyu
 * @Date 2024/2/2
 * @Version 1.0
 */
@Service
public class FlowServiceImpl implements IFlowService {
    @Autowired
    FlowMapper flowMapper;
    @Autowired
    IDictClient dictClient;

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public void updateUserApplicationId(FlowTable flowTable) {
        FlowTable oldFlowTable = this.selectByApplicationId(flowTable);
        if (oldFlowTable != null && oldFlowTable.getCurrentNode() != null && flowTable.getCurrentNode() != null) {
            //判断是否循环节点，避免流程审核记录太长
            if (!flowTable.getCurrentNode().equals(oldFlowTable.getCurrentNode())) {
                flowTable.setFlowPath(oldFlowTable.getFlowPath() + "-" + flowTable.getCurrentNode());
            }
        } else {
            flowTable.setFlowPath(flowTable.getCurrentNode());
        }
        flowMapper.updateUserApplicationId(flowTable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public void updateAssign(FlowTable flowTable) {
        flowMapper.updateAssign(flowTable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public FlowTable selectByApplicationId(FlowTable flowTable) {
        return flowMapper.selectByApplicationId(flowTable);
    }

    @Override
    public void notifyCallback(String businessKey, FlowNotify flowNotify) {
        FLowBusinessEnum businessEnum =  FLowBusinessEnum.valueOf(businessKey);
        if(!ObjectUtil.isEmpty(businessEnum)){
            R<String> r = dictClient.getValue("notify_business_bean", businessKey);
            if (r.isSuccess()) {
                FlowNotifyService flowNotifyService = SpringUtil.getBean(r.getData(), FlowNotifyService.class);
                flowNotifyService.notify(flowNotify);
            }
        }

    }
}
