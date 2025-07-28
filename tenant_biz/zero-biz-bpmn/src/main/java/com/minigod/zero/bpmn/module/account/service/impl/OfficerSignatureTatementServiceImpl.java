package com.minigod.zero.bpmn.module.account.service.impl;

import com.minigod.zero.bpmn.module.account.vo.OfficerSignatureTatementVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.OfficerSignatureTatementEntity;
import com.minigod.zero.bpmn.module.account.mapper.OfficerSignatureTatementMapper;
import com.minigod.zero.bpmn.module.account.service.IOfficerSignatureTatementService;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author Chill
 */
@Service
public class OfficerSignatureTatementServiceImpl extends BaseServiceImpl<OfficerSignatureTatementMapper, OfficerSignatureTatementEntity> implements IOfficerSignatureTatementService {

    @Override
    public OfficerSignatureTatementVO queryByApplicationId(String applicationId) {
        return baseMapper.queryByApplicationId(applicationId);
    }
}
