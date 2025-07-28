package com.minigod.zero.bpmn.module.account.service.impl;

import com.minigod.zero.bpmn.module.account.vo.AccountOtherDisclosureVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.AccountOtherDisclosureEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountOtherDisclosureMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountOtherDisclosureService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Chill
 */
@Service
public class AccountOtherDisclosureServiceImpl extends BaseServiceImpl<AccountOtherDisclosureMapper, AccountOtherDisclosureEntity> implements IAccountOtherDisclosureService {

    @Override
    public List<AccountOtherDisclosureVO> queryListByApplicationId(String applicationId) {
        return baseMapper.queryListByApplicationId(applicationId);
    }
}
