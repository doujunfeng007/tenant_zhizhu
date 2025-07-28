package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.vo.AccountPropertyTypeVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountPropertyTypeEntity;

import java.util.List;

/**
 *  服务类
 *
 * @author Chill
 */
public interface IAccountPropertyTypeService extends BaseService<AccountPropertyTypeEntity> {
    List<AccountPropertyTypeVO> queryListByApplicationId(String applicationId);

}
