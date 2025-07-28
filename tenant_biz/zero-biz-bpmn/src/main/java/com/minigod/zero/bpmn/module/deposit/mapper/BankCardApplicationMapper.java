package com.minigod.zero.bpmn.module.deposit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.deposit.bo.BankCardApplicationQuery;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardApplication;
import java.util.List;

import com.minigod.zero.bpmn.module.deposit.vo.BankCardApplicationVO;
import org.apache.ibatis.annotations.Param;

/**
*@ClassName: BankCardApplicationMapper
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/11
*@Version 1.0
*
*/
public interface BankCardApplicationMapper extends BaseMapper<BankCardApplication> {
    int batchInsert(@Param("list") List<BankCardApplication> list);

    BankCardApplicationVO queryApplicationId(String applicationId);

    IPage<BankCardApplicationVO> selectBankCardApplicationPage(IPage<BankCardApplicationVO> page,@Param("bo") BankCardApplicationQuery applicationQuery);

    List<BankCardApplication> queryListByNode(String node);
}
