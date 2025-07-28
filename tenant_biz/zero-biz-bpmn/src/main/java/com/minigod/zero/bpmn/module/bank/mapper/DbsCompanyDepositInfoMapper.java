package com.minigod.zero.bpmn.module.bank.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.bank.entity.DbsCompanyDepositInfo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
*@ClassName: DbsCompanyDepositInfoMapper
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/7
*@Version 1.0
*
*/
public interface DbsCompanyDepositInfoMapper extends BaseMapper<DbsCompanyDepositInfo> {
    int batchInsert(@Param("list") List<DbsCompanyDepositInfo> list);
}
