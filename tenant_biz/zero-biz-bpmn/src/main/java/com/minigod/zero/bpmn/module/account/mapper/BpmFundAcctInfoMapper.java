package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.BpmnFundAcctInfoEntity;

/**
 * 基金账户信息表 Mapper 接口
 *
 * @author eric
 * @since 2024-08-20 15:42:05
 */
@DS("cust")
public interface BpmFundAcctInfoMapper extends BaseMapper<BpmnFundAcctInfoEntity> {

}
