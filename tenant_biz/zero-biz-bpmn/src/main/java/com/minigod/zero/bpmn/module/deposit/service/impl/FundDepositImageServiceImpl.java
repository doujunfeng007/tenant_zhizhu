package com.minigod.zero.bpmn.module.deposit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.deposit.vo.FundDepositImageVO;
import com.minigod.zero.core.secure.utils.AuthUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.deposit.entity.FundDepositImage;
import com.minigod.zero.bpmn.module.deposit.mapper.FundDepositImageMapper;
import com.minigod.zero.bpmn.module.deposit.service.FundDepositImageService;
/**
*@ClassName: FundDepositImageServiceImpl
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/6
*@Version 1.0
*
*/
@Service
public class FundDepositImageServiceImpl extends ServiceImpl<FundDepositImageMapper, FundDepositImage> implements FundDepositImageService{

    @Override
    public List<FundDepositImageVO> queryByApplicationId(String applicationId) {
        LambdaQueryWrapper<FundDepositImage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FundDepositImage::getApplicationId,applicationId);
        queryWrapper.eq(FundDepositImage::getTenantId, AuthUtil.getTenantId());
        return baseMapper.selectList(queryWrapper).stream().map(entity -> {
            FundDepositImageVO vo = new FundDepositImageVO();
             BeanUtils.copyProperties(entity,vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
