package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.entity.AccountIdentityConfirmModifyEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountIdentityConfirmModifyVO;
import com.minigod.zero.core.mp.base.BaseService;

import java.util.List;

/**
 * 身份确认信息修改 服务类
 *
 * @author eric
 * @since 2024-08-05 11:17:25
 */
public interface IAccountIdentityConfirmModifyService extends BaseService<AccountIdentityConfirmModifyEntity> {
	/**
	 * 持久化客户身份确认信息修改记录
	 *
	 * @param entity
	 * @return
	 */
	boolean insert(AccountIdentityConfirmModifyEntity entity);

	/**
	 * 根据修改申请ID查询客户身份确认信息修改记录
	 *
	 * @param applyId
	 * @return
	 */
	AccountIdentityConfirmModifyVO selectByApplyId(Long applyId);

	/**
	 * 根据开户申请ID查询客户身份确认信息修改记录
	 *
	 * @param applicationId
	 * @return
	 */
	List<AccountIdentityConfirmModifyVO> selectByApplicationId(String applicationId);

	/**
	 * 根据用户ID查询客户身份确认信息修改记录
	 *
	 * @param userId
	 * @return
	 */
	List<AccountIdentityConfirmModifyVO> selectByUserId(Long userId);
}
