package com.minigod.zero.bpmn.module.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.bpmn.module.account.entity.AccountTaxationInfoModifyEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountTaxationInfoModifyMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountTaxationInfoModifyService;
import com.minigod.zero.bpmn.module.account.vo.AccountTaxationInfoModifyVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客税务信息修改服务
 *
 * @author eric
 * @since 2024-08-05 11:03:12
 */
@Slf4j
@Service
public class AccountTaxationInfoModifyServiceImpl extends BaseServiceImpl<AccountTaxationInfoModifyMapper, AccountTaxationInfoModifyEntity> implements IAccountTaxationInfoModifyService {
	/**
	 * 持久化税务信息修改记录
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean insert(AccountTaxationInfoModifyEntity entity) {
		return this.insert(entity);
	}

	/**
	 * 根据修改申请ID查询税务信息记录
	 *
	 * @param applyId
	 * @return
	 */
	@Override
	public List<AccountTaxationInfoModifyVO> selectByApplyId(Long applyId) {
		LambdaQueryWrapper<AccountTaxationInfoModifyEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AccountTaxationInfoModifyEntity::getApplyId, applyId);
		queryWrapper.orderByDesc(AccountTaxationInfoModifyEntity::getCreateTime);
		return BeanUtil.copyProperties(this.list(queryWrapper), AccountTaxationInfoModifyVO.class);
	}

	/**
	 * 根据开户申请ID查询税务信息修改记录
	 *
	 * @param applicationId
	 * @return
	 */
	@Override
	public List<AccountTaxationInfoModifyVO> selectByApplicationId(String applicationId) {
		LambdaQueryWrapper<AccountTaxationInfoModifyEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(AccountTaxationInfoModifyEntity::getApplicationId, applicationId);
		queryWrapper.orderByDesc(AccountTaxationInfoModifyEntity::getCreateTime);
		List<AccountTaxationInfoModifyEntity> entities = this.list(queryWrapper);
		return BeanUtil.copyProperties(entities, AccountTaxationInfoModifyVO.class);
	}

	/**
	 * 根据用户ID查询税务信息修改记录
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List<AccountTaxationInfoModifyVO> selectByUserId(Long userId) {
		LambdaQueryWrapper<AccountTaxationInfoModifyEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(AccountTaxationInfoModifyEntity::getUserId, userId);
		queryWrapper.orderByDesc(AccountTaxationInfoModifyEntity::getCreateTime);
		List<AccountTaxationInfoModifyEntity> entities = this.list(queryWrapper);
		List<AccountTaxationInfoModifyVO> modifyVOS = BeanUtil.copyProperties(entities, AccountTaxationInfoModifyVO.class);
		return modifyVOS;
	}
}
