package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenCacheInfoEntity;

import java.util.List;

/**
 *  服务类
 *
 * @author Chill
 */
public interface ICustomOpenCacheInfoService extends BaseService<CustomOpenCacheInfoEntity> {

    List<CustomOpenCacheInfoEntity> selectByUserId(Long custId);

    CustomOpenCacheInfoEntity selectOneByUserIdAndStep(Long custId, Integer step);

    void saveOrUpdateStepInfo(Long userId, Integer step, String info);

    void saveOrUpdateLastStepInfo(Long userId ,Integer step);
}
