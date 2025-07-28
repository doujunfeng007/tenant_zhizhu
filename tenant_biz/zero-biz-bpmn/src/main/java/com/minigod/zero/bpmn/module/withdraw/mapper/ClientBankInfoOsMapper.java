package com.minigod.zero.bpmn.module.withdraw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientBankInfoOs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*@ClassName: ClientBankInfoOsMapper
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/28
*@Version 1.0
*
*/
public interface ClientBankInfoOsMapper extends BaseMapper<ClientBankInfoOs> {
    int batchInsert(@Param("list") List<ClientBankInfoOs> list);
}
