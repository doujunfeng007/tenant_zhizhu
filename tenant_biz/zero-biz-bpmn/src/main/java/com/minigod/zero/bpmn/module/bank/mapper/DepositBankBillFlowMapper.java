package com.minigod.zero.bpmn.module.bank.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.bank.entity.DepositBankBillFlow;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
*@ClassName: DepositBankBillFlowMapper
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/7
*@Version 1.0
*
*/
public interface DepositBankBillFlowMapper extends BaseMapper<DepositBankBillFlow> {
    int batchInsert(@Param("list") List<DepositBankBillFlow> list);

    DepositBankBillFlow selectBankBillByRefId(String refId);
}
