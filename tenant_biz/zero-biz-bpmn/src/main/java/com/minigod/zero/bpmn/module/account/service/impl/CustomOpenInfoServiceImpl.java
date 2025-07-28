package com.minigod.zero.bpmn.module.account.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.enums.CustomOpenAccountEnum;
import com.minigod.zero.bpmn.module.account.mapper.CustomOpenInfoMapper;
import com.minigod.zero.bpmn.module.account.service.ICustomOpenInfoService;
import com.minigod.zero.core.mp.base.AppServiceImpl;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class CustomOpenInfoServiceImpl extends AppServiceImpl<CustomOpenInfoMapper, CustomOpenInfoEntity> implements ICustomOpenInfoService {
	/**
	 * 查询最新开户缓存数据
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public CustomOpenInfoEntity selectCustomOpenInfo(Long userId) {
		LambdaQueryWrapper<CustomOpenInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustomOpenInfoEntity::getUserId, userId);
		queryWrapper.last("limit 1");
		queryWrapper.orderByDesc(CustomOpenInfoEntity::getCreateTime);
		CustomOpenInfoEntity customOpenInfoEntity = baseMapper.selectOne(queryWrapper);
		return customOpenInfoEntity;
	}

	/**
	 * 是否可更新开户缓存数据
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public boolean isCanUpdateOpenInfo(Long userId) {
		CustomOpenInfoEntity customOpenInfoEntity = selectCustomOpenInfo(userId);
		if (customOpenInfoEntity != null) {
			CustomOpenAccountEnum.OpenStatus openStatus = CustomOpenAccountEnum.OpenStatus.getStatus(customOpenInfoEntity.getStatus());
			return openStatus.getIsSubmit();
		}
		return true;
	}

	/**
	 * 查询未推送的开户缓存数据
	 *
	 * @return
	 */
	@Override
	public List<CustomOpenInfoEntity> selectByIsPushedFalse() {
		LambdaQueryWrapper<CustomOpenInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustomOpenInfoEntity::getIsPushed, false);
		queryWrapper.orderByDesc(CustomOpenInfoEntity::getCreateTime);
		List<CustomOpenInfoEntity> list = baseMapper.selectList(queryWrapper);
		return list;
	}
}
