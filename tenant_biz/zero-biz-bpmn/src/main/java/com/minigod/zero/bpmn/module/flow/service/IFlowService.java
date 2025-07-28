package com.minigod.zero.bpmn.module.flow.service;

import com.minigod.zero.flow.core.entity.FlowNotify;
import com.minigod.zero.flow.core.entity.FlowTable;

/**
 * @ClassName: IFlowService
 * @Description:
 * @Author chenyu
 * @Date 2024/2/2
 * @Version 1.0
 */
public interface IFlowService {
    /**
     * 修改流程业务表
     * @param flowTable
     */
    void updateUserApplicationId(FlowTable flowTable);

    /**
     * 修改受理人
     * @param flowTable
     */
    void updateAssign(FlowTable flowTable);

    /**
     * 获取业务流程信息
     * @param flowTable
     * @return
     */
    FlowTable selectByApplicationId(FlowTable flowTable);

    void notifyCallback(String businessKey, FlowNotify flowNotify);
}
