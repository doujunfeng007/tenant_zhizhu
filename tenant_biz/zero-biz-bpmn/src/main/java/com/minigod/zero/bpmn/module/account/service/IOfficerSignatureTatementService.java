package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.vo.OfficerSignatureTatementVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.OfficerSignatureTatementEntity;

/**
 *  服务类
 *
 * @author Chill
 */
public interface IOfficerSignatureTatementService extends BaseService<OfficerSignatureTatementEntity> {

    OfficerSignatureTatementVO queryByApplicationId(String applicationId);
}
