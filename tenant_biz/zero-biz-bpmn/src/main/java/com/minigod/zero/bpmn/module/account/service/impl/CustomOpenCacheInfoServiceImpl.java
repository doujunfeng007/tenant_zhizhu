package com.minigod.zero.bpmn.module.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.core.mp.base.AppServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenCacheInfoEntity;
import com.minigod.zero.bpmn.module.account.mapper.CustomOpenCacheInfoMapper;
import com.minigod.zero.bpmn.module.account.service.ICustomOpenCacheInfoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  服务实现类
 *
 * @author Chill
 */
@Service
public class CustomOpenCacheInfoServiceImpl extends AppServiceImpl<CustomOpenCacheInfoMapper, CustomOpenCacheInfoEntity> implements ICustomOpenCacheInfoService {

    @Override
    public List<CustomOpenCacheInfoEntity> selectByUserId(Long custId) {
        LambdaQueryWrapper<CustomOpenCacheInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomOpenCacheInfoEntity::getUserId,custId);
        return  baseMapper.selectList(queryWrapper);
    }

    @Override
    public CustomOpenCacheInfoEntity selectOneByUserIdAndStep(Long custId, Integer step) {
        LambdaQueryWrapper<CustomOpenCacheInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomOpenCacheInfoEntity::getUserId,custId);
        queryWrapper.eq(CustomOpenCacheInfoEntity::getStep,step);
        queryWrapper.last("limit 1");
        queryWrapper.orderByDesc(CustomOpenCacheInfoEntity::getCreateTime);
        return  baseMapper.selectOne(queryWrapper);
    }

    @Override
    public void saveOrUpdateStepInfo(Long userId,Integer step, String info) {
        // 查询当前步骤缓存数据
        CustomOpenCacheInfoEntity cacheInfo = selectOneByUserIdAndStep(userId, step);
        if (cacheInfo != null) {
            cacheInfo.setUpdateTime(new Date());
            cacheInfo.setJsonInfo(info);
            baseMapper.updateById(cacheInfo);
        } else {
            cacheInfo = new CustomOpenCacheInfoEntity();
            cacheInfo.setUserId(userId);
            cacheInfo.setJsonInfo(info);
            cacheInfo.setStep(step);
            cacheInfo.setCreateTime(new Date());
            baseMapper.insert(cacheInfo);
        }
    }

    @Override
    public void saveOrUpdateLastStepInfo(Long userId, Integer step) {
        Integer baseStep = -1;
        Map<String, Integer> lastStepInfo = new HashMap<>();
        lastStepInfo.put("lastStep", step);
        saveOrUpdateStepInfo(userId, baseStep, JSON.toJSONString(lastStepInfo));
    }
}
