package com.minigod.zero.bpmn.module.deposit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.deposit.entity.FundDepositImage;
import com.minigod.zero.bpmn.module.deposit.vo.FundDepositImageVO;

import java.util.List;

/**
 * @ClassName: FundDepositImageMapper
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/6
 * @Version 1.0
 */
public interface FundDepositImageMapper extends BaseMapper<FundDepositImage> {
    int batchInsert(List<FundDepositImage> list);

    List<FundDepositImageVO> queryByApplicationId(String applicationId);
}
