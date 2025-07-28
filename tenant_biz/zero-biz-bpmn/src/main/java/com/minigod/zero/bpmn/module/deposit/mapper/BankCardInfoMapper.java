package com.minigod.zero.bpmn.module.deposit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardInfo;
import java.util.List;

import com.minigod.zero.bpmn.module.deposit.vo.BankCardVO;
import org.apache.ibatis.annotations.Param;

/**
*@ClassName: BankCardInfoMapper
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/11
*@Version 1.0
*
*/
public interface BankCardInfoMapper extends BaseMapper<BankCardInfo> {
    int batchInsert(@Param("list") List<BankCardInfo> list);

	List<BankCardVO> selectCustomerBankCard(Long custId, String bankId, String bankCode);
}
