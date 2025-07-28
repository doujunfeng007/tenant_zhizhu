package com.minigod.zero.bpmn.module.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.account.entity.AccountW8benInfoModifyEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountW8benInfoModifyMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountW8benInfoModifyService;
import com.minigod.zero.bpmn.module.account.vo.AccountW8benInfoModifyVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * W-8BEN表格美国税务局表修改信息 服务实现类
 *
 * @author eric
 * @since 2024-08-05 11:12:02
 */
@Slf4j
@Service
public class AccountW8benInfoModifyServiceImpl extends BaseServiceImpl<AccountW8benInfoModifyMapper, AccountW8benInfoModifyEntity> implements IAccountW8benInfoModifyService {
	/**
	 * 持久化W-8BEN表格美国税务局信息修改记录
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean insert(AccountW8benInfoModifyEntity entity) {
		return this.save(entity);
	}

	/**
	 * 根据修改申请ID查询W-8BEN表格美国税务局信息修改记录
	 *
	 * @param applyId
	 * @return
	 */
	@Override
	public AccountW8benInfoModifyVO selectByApplyId(Long applyId) {
		LambdaQueryWrapper<AccountW8benInfoModifyEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(AccountW8benInfoModifyEntity::getApplyId, applyId);
		queryWrapper.orderByDesc(AccountW8benInfoModifyEntity::getCreateTime);

		return BeanUtil.copyProperties(this.getOne(queryWrapper), AccountW8benInfoModifyVO.class);
	}

	/**
	 * 根据开户申请ID查询客户W-8BEN表格美国税务局信息修改记录
	 *
	 * @param applicationId
	 * @return
	 */
	@Override
	public List<AccountW8benInfoModifyVO> selectByApplicationId(String applicationId) {
		LambdaQueryWrapper<AccountW8benInfoModifyEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(AccountW8benInfoModifyEntity::getApplicationId, applicationId);
		queryWrapper.orderByDesc(AccountW8benInfoModifyEntity::getCreateTime);
		List<AccountW8benInfoModifyEntity> entities = this.list(queryWrapper);
		List<AccountW8benInfoModifyVO> modifyVOS = BeanUtil.copyProperties(entities, AccountW8benInfoModifyVO.class);
		return modifyVOS;
	}

	/**
	 * 根据用户ID查询W-8BEN表格美国税务局信息修改记录
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List<AccountW8benInfoModifyVO> selectByUserId(Long userId) {
		LambdaQueryWrapper<AccountW8benInfoModifyEntity> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(AccountW8benInfoModifyEntity::getUserId, userId);
		queryWrapper.orderByDesc(AccountW8benInfoModifyEntity::getCreateTime);
		List<AccountW8benInfoModifyEntity> entities = this.list(queryWrapper);
		List<AccountW8benInfoModifyVO> modifyVOS = BeanUtil.copyProperties(entities, AccountW8benInfoModifyVO.class);
		return modifyVOS;
	}
}
