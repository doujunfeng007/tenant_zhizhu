package com.minigod.zero.bpmn.module.account.service.impl;

import com.minigod.zero.bpmn.module.account.vo.AccountTaxationInfoVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.AccountTaxationInfoEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountTaxationInfoMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountTaxationInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Chill
 */
@Service
public class AccountTaxationInfoServiceImpl extends BaseServiceImpl<AccountTaxationInfoMapper, AccountTaxationInfoEntity> implements IAccountTaxationInfoService {

    @Override
    public List<AccountTaxationInfoVO> queryListByApplicationId(String applicationId) {
        return baseMapper.queryListByApplicationId(applicationId);
    }

    @Override
    public void deleteByApplicationId(String applicationId) {
        baseMapper.deleteByApplicationId(applicationId);
    }
}
