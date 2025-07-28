package com.minigod.zero.bpmn.module.deposit.service;

import com.minigod.zero.bpmn.module.deposit.entity.FundDepositImage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.deposit.vo.FundDepositImageVO;

import java.util.List;

/**
 * @ClassName: FundDepositImageService
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/6
 * @Version 1.0
 */
public interface FundDepositImageService extends IService<FundDepositImage> {


    List<FundDepositImageVO> queryByApplicationId(String applicationId);
}
