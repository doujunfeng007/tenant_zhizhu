package com.minigod.zero.flow.flowable.flowable.service;

import com.minigod.zero.flow.core.entity.FlowTable;

import java.util.Map;

/**
 * @ClassName: FlowTableService
 * @Description: 更新流程节点
 * @Author chenyu
 * @Date 2022/7/26
 * @Version 1.0
 */
public interface FlowTableService {
    /**
     * 更新流程表
     * @param flowTable
     */
    void updateByApplicationId(FlowTable flowTable);

    void updateAssign(FlowTable flowTable);
}
