package com.minigod.zero.bpmn.module.margincredit.service;

import com.minigod.zero.bpmn.module.margincredit.entity.IncreaseCreditLimitEntity;
import com.minigod.zero.bpmn.module.margincredit.vo.IncreaseCreditLimitVO;
import com.minigod.zero.core.mp.base.BaseService;

/**
 * @ClassName IncreaseCreditLimitService.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月09日 17:24:00
 */
public interface IIncreaseCreditLimitService extends BaseService<IncreaseCreditLimitEntity> {

    IncreaseCreditLimitVO queryDetailByApplication(String applicationId);

    IncreaseCreditLimitEntity queryByApplicationId(String applicationId);
}
