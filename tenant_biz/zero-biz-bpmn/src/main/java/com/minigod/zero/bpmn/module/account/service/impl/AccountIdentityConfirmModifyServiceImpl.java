package com.minigod.zero.bpmn.module.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.account.entity.AccountIdentityConfirmModifyEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountIdentityConfirmModifyMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountIdentityConfirmModifyService;
import com.minigod.zero.bpmn.module.account.vo.AccountIdentityConfirmModifyVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 身份确认信息修改服务实现类
 *
 * @author eric
 * @since 2024-08-05 11:19:52
 */
@Slf4j
@Service
public class AccountIdentityConfirmModifyServiceImpl extends BaseServiceImpl<AccountIdentityConfirmModifyMapper, AccountIdentityConfirmModifyEntity> implements IAccountIdentityConfirmModifyService {
	/**
	 * 持久化身份确认信息修改记录
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean insert(AccountIdentityConfirmModifyEntity entity) {
		return this.save(entity);
	}

	/**
	 * 根据修改申请ID查询客户身份确认信息修改记录
	 *
	 * @param applyId
	 * @return
	 */
	@Override
	public AccountIdentityConfirmModifyVO selectByApplyId(Long applyId) {
		LambdaQueryWrapper<AccountIdentityConfirmModifyEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(AccountIdentityConfirmModifyEntity::getApplyId, applyId);
		queryWrapper.orderByDesc(AccountIdentityConfirmModifyEntity::getCreateTime);

		return BeanUtil.copyProperties(this.getOne(queryWrapper), AccountIdentityConfirmModifyVO.class);
	}

	/**
	 * 根据开户申请ID查询客户身份确认信息修改记录
	 *
	 * @param applicationId
	 * @return
	 */
	@Override
	public List<AccountIdentityConfirmModifyVO> selectByApplicationId(String applicationId) {
		LambdaQueryWrapper<AccountIdentityConfirmModifyEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(AccountIdentityConfirmModifyEntity::getApplicationId, applicationId);
		queryWrapper.orderByDesc(AccountIdentityConfirmModifyEntity::getCreateTime);
		List<AccountIdentityConfirmModifyEntity> entities = this.list(queryWrapper);
		List<AccountIdentityConfirmModifyVO> modifyVOS = BeanUtil.copyProperties(entities, AccountIdentityConfirmModifyVO.class);
		return modifyVOS;
	}

	/**
	 * 根据用户ID查询客户身份确认信息修改记录
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List<AccountIdentityConfirmModifyVO> selectByUserId(Long userId) {
		LambdaQueryWrapper<AccountIdentityConfirmModifyEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(AccountIdentityConfirmModifyEntity::getUserId, userId);
		queryWrapper.orderByDesc(AccountIdentityConfirmModifyEntity::getCreateTime);
		List<AccountIdentityConfirmModifyEntity> entities = this.list(queryWrapper);
		List<AccountIdentityConfirmModifyVO> modifyVOS = BeanUtil.copyProperties(entities, AccountIdentityConfirmModifyVO.class);
		return modifyVOS;
	}
}
