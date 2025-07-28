package com.minigod.zero.bpmn.module.withdraw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientBankInfoOsBo;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientBankInfoOs;
import com.minigod.zero.bpmn.module.withdraw.mapper.ClientBankInfoOsMapper;
import com.minigod.zero.bpmn.module.withdraw.service.ClientBankInfoOsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
/**
*@ClassName: ClientBankInfoOsServiceImpl
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/28
*@Version 1.0
*
*/
@Service
public class ClientBankInfoOsServiceImpl extends ServiceImpl<ClientBankInfoOsMapper, ClientBankInfoOs> implements ClientBankInfoOsService{

    @Override
    public int batchInsert(List<ClientBankInfoOs> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public IPage<ClientBankInfoOs> queryPageList(IPage page, ClientBankInfoOsBo bo) {
        return baseMapper.selectPage(page, buildQueryWrapper(bo));
    }

    @Override
    public List<ClientBankInfoOs> queryList(ClientBankInfoOsBo bo) {
        return baseMapper.selectList(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<ClientBankInfoOs> buildQueryWrapper(ClientBankInfoOsBo bo) {
        LambdaQueryWrapper<ClientBankInfoOs> lqw = new LambdaQueryWrapper<>();
        lqw.eq(bo.getId() != null, ClientBankInfoOs::getId, bo.getId());
        lqw.like(StringUtils.isNotBlank(bo.getBankCode()), ClientBankInfoOs::getBankCode, bo.getBankCode());
        lqw.eq(bo.getStatus() != null, ClientBankInfoOs::getStatus, bo.getStatus());
        lqw.eq(bo.getDeposit() != null, ClientBankInfoOs::getDeposit, bo.getDeposit());
        lqw.like(StringUtils.isNotBlank(bo.getBankName()), ClientBankInfoOs::getBankName, bo.getBankName());
        lqw.like(StringUtils.isNotBlank(bo.getBankNameEn()), ClientBankInfoOs::getBankNameEn, bo.getBankNameEn());
        lqw.eq(bo.getStatus() != null, ClientBankInfoOs::getStatus, bo.getStatus());
        lqw.orderByDesc(ClientBankInfoOs::getId);
        return lqw;
    }
}
