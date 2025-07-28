package com.minigod.zero.bpmn.module.account.service.impl;

import com.minigod.zero.bpmn.module.account.vo.AccountPropertyTypeVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.AccountPropertyTypeEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountPropertyTypeMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountPropertyTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  服务实现类
 *
 * @author Chill
 */
@Service
public class AccountPropertyTypeServiceImpl extends BaseServiceImpl<AccountPropertyTypeMapper, AccountPropertyTypeEntity> implements IAccountPropertyTypeService {


    @Override
    public List<AccountPropertyTypeVO> queryListByApplicationId(String applicationId) {
        return baseMapper.queryListByApplicationId(applicationId);
    }
}
