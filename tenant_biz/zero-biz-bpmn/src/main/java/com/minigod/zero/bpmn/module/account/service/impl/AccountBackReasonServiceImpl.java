package com.minigod.zero.bpmn.module.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.minigod.zero.bpmn.module.account.vo.AccountBackReasonVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.AccountBackReasonEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountBackReasonMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountBackReasonService;
import com.minigod.zero.core.secure.utils.AuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
public class AccountBackReasonServiceImpl extends BaseServiceImpl<AccountBackReasonMapper, AccountBackReasonEntity> implements IAccountBackReasonService {

	@Override
	public List<AccountBackReasonVO> selectByApplicationId(String applicationId) {
		return baseMapper.selectByApplicationId(applicationId, AuthUtil.getTenantUser() == null ? AuthUtil.getTenantId() : AuthUtil.getTenantUser().getTenantId());
	}

	@Override
	public List<String> selectCodeListByApplicationId(String applicationId) {
		return baseMapper.selectCodeListByApplicationId(applicationId, AuthUtil.getTenantUser() == null ? AuthUtil.getTenantId() : AuthUtil.getTenantUser().getTenantId());
	}

	@Override
	public int deleteWithCode(String applicationId, String code) {
		return baseMapper.deleteWithCode(applicationId, code, AuthUtil.getTenantUser() == null ? AuthUtil.getTenantId() : AuthUtil.getTenantUser().getTenantId());
	}

	@Override
	public void deleteByApplicationId(String applicationId) {
		baseMapper.deleteByApplicationId(applicationId, AuthUtil.getTenantUser() == null ? AuthUtil.getTenantId() : AuthUtil.getTenantUser().getTenantId());
	}

	@Override
	public AccountBackReasonVO selectByApplicationIdAndCode(String applicationId, String code) {
		return baseMapper.selectByApplicationIdAndCode(applicationId, code, AuthUtil.getTenantUser() == null ? AuthUtil.getTenantId() : AuthUtil.getTenantUser().getTenantId());
	}

	@Override
	public int updateByApplicationId(AccountBackReasonEntity bo) {
		LambdaUpdateWrapper<AccountBackReasonEntity> lambdaUpdate = new LambdaUpdateWrapper<>();
		lambdaUpdate.set(AccountBackReasonEntity::getContent, bo.getContent())
			.eq(AccountBackReasonEntity::getApplicationId, bo.getApplicationId())
			.eq(AccountBackReasonEntity::getCode, bo.getCode())
			.eq(AccountBackReasonEntity::getTenantId, AuthUtil.getTenantUser() == null ? AuthUtil.getTenantId() : AuthUtil.getTenantUser().getTenantId());
		return baseMapper.update(null, lambdaUpdate);
	}
}
