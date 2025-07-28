package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.entity.AccountW8benInfoModifyEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountW8benInfoModifyVO;
import com.minigod.zero.core.mp.base.BaseService;

import java.util.List;

/**
 * W-8BEN表格美国税务局表修改信息服务类
 *
 * @author eric
 * @date 2024-08-05 11:10:01
 */
public interface IAccountW8benInfoModifyService extends BaseService<AccountW8benInfoModifyEntity> {
	/**
	 * 持久化客户身份确认信息修改记录
	 *
	 * @param entity
	 * @return
	 */
	boolean insert(AccountW8benInfoModifyEntity entity);

	/**
	 * 根据修改申请ID查询客户身份确认信息修改记录
	 *
	 * @param applyId
	 * @return
	 */
	AccountW8benInfoModifyVO selectByApplyId(Long applyId);

	/**
	 * 根据开户申请ID查询客户身份确认信息修改记录
	 *
	 * @param applicationId
	 * @return
	 */
	List<AccountW8benInfoModifyVO> selectByApplicationId(String applicationId);

	/**
	 * 根据用户ID查询客户身份确认信息修改记录
	 *
	 * @param userId
	 * @return
	 */
	List<AccountW8benInfoModifyVO> selectByUserId(Long userId);
}
