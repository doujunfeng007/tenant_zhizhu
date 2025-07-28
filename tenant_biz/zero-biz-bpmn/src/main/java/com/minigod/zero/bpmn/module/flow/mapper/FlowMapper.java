package com.minigod.zero.bpmn.module.flow.mapper;

import com.minigod.zero.flow.core.entity.FlowTable;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: FlowMapper
 * @Description:
 * @Author chenyu
 * @Date 2024/2/2
 * @Version 1.0
 */
@Mapper
public interface FlowMapper {

    int updateUserApplicationId(FlowTable flowTable);

    int updateAssign(FlowTable flowTable);

    FlowTable selectByApplicationId(FlowTable flowTable);
}
