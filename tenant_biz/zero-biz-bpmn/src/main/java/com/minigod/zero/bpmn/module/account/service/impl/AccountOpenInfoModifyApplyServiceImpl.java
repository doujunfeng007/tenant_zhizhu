package com.minigod.zero.bpmn.module.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.bpm.enums.OpenAccountEnum;
import com.minigod.zero.bpmn.module.account.dto.AccountOpenInfoModifyApplyPageDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenInfoModifyApplyEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoModifyApplyMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoModifyApplyService;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoModifyApplyVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户开户资料修改申请记录服务实现类
 *
 * @author eric
 * @since 2024-08-05 11:23:25
 */
@Slf4j
@Service
public class AccountOpenInfoModifyApplyServiceImpl extends BaseServiceImpl<AccountOpenInfoModifyApplyMapper, AccountOpenInfoModifyApplyEntity> implements IAccountOpenInfoModifyApplyService {
	/**
	 * 持久化客户开户资料修改申请记录
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean insert(AccountOpenInfoModifyApplyEntity entity) {
		return this.save(entity);
	}

	/**
	 * 根据修改申请ID查询客户开户资料修改申请记录
	 *
	 * @param applyId
	 * @return
	 */
	@Override
	public AccountOpenInfoModifyApplyVO selectByApplyId(Long applyId) {
		LambdaQueryWrapper<AccountOpenInfoModifyApplyEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AccountOpenInfoModifyApplyEntity::getId, applyId);
		queryWrapper.orderByDesc(AccountOpenInfoModifyApplyEntity::getCreateTime);

		return BeanUtil.copyProperties(this.getOne(queryWrapper), AccountOpenInfoModifyApplyVO.class);
	}

	/**
	 * 根据开户申请ID查询客户开户资料修改申请记录
	 *
	 * @param applicationId
	 * @return
	 */
	@Override
	public List<AccountOpenInfoModifyApplyVO> selectByApplicationId(String applicationId) {
		LambdaQueryWrapper<AccountOpenInfoModifyApplyEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AccountOpenInfoModifyApplyEntity::getApplicationId, applicationId);
		queryWrapper.orderByDesc(AccountOpenInfoModifyApplyEntity::getCreateTime);
		List<AccountOpenInfoModifyApplyEntity> entities = this.list(queryWrapper);
		return BeanUtil.copyProperties(entities, AccountOpenInfoModifyApplyVO.class);
	}

	/**
	 * 根据用户ID查询客户开户资料修改申请记录
	 *
	 * @param applyPageDTO
	 * @return
	 */
	@Override
	public Page<AccountOpenInfoModifyApplyVO> pageByUserId(AccountOpenInfoModifyApplyPageDTO applyPageDTO) {
		Page page = new Page<>(applyPageDTO.getCurrent(), applyPageDTO.getSize());
		List<AccountOpenInfoModifyApplyVO> modifyApplyVOs = this.baseMapper.getOpenInfoModifyPge(page, applyPageDTO);
		page.setRecords(modifyApplyVOs);
		return page;
	}

	/**
	 * 根据userid、状态查询开户资料修改申请记录
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public AccountOpenInfoModifyApplyEntity getInfoByUserIdStatus(Long userId) {
		LambdaQueryWrapper<AccountOpenInfoModifyApplyEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AccountOpenInfoModifyApplyEntity::getUserId, userId);
		queryWrapper.eq(AccountOpenInfoModifyApplyEntity::getApproveStatus, OpenAccountEnum.OpenAccountModifyApproveStatus.APPROVE_PENDING.getCode());
		return getOne(queryWrapper);
	}

	/**
	 * 根据用户ID查询客户开户资料修改申请记录
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public AccountOpenInfoModifyApplyVO getApproveInfoByUserId(Long userId) {
		LambdaQueryWrapper<AccountOpenInfoModifyApplyEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AccountOpenInfoModifyApplyEntity::getUserId, userId);
		queryWrapper.orderByDesc(AccountOpenInfoModifyApplyEntity::getCreateTime);
		queryWrapper.last("limit 1");
		AccountOpenInfoModifyApplyEntity accountOpenInfoModifyApply = getOne(queryWrapper);
		AccountOpenInfoModifyApplyVO accountOpenInfoModifyApplyVO = new AccountOpenInfoModifyApplyVO();
		if (accountOpenInfoModifyApply != null) {
			BeanUtil.copyProperties(accountOpenInfoModifyApply, accountOpenInfoModifyApplyVO);
		}
		return accountOpenInfoModifyApplyVO;
	}
}
