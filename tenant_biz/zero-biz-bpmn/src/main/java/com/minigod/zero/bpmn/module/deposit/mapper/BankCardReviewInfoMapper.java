package com.minigod.zero.bpmn.module.deposit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardReviewInfo;
import java.util.List;

import com.minigod.zero.bpmn.module.deposit.vo.BankCardVO;
import org.apache.ibatis.annotations.Param;

/**
*@ClassName: BankCardReviewInfoMapper
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/13
*@Version 1.0
*
*/
public interface BankCardReviewInfoMapper extends BaseMapper<BankCardReviewInfo> {
    int batchInsert(@Param("list") List<BankCardReviewInfo> list);

}
