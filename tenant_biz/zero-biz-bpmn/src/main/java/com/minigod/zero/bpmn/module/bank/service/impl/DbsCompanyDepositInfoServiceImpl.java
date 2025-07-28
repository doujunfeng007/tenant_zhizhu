package com.minigod.zero.bpmn.module.bank.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.bank.mapper.DbsCompanyDepositInfoMapper;
import com.minigod.zero.bpmn.module.bank.entity.DbsCompanyDepositInfo;
import com.minigod.zero.bpmn.module.bank.service.DbsCompanyDepositInfoService;
/**
*@ClassName: DbsCompanyDepositInfoServiceImpl
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/7
*@Version 1.0
*
*/
@Service
public class DbsCompanyDepositInfoServiceImpl extends ServiceImpl<DbsCompanyDepositInfoMapper, DbsCompanyDepositInfo> implements DbsCompanyDepositInfoService{

    @Override
    public int batchInsert(List<DbsCompanyDepositInfo> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public List<DbsCompanyDepositInfo> queryInfoByTenantId(String tenantId) {
        LambdaQueryWrapper<DbsCompanyDepositInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DbsCompanyDepositInfo::getTenantId,tenantId);
        return baseMapper.selectList(queryWrapper);
    }
}
