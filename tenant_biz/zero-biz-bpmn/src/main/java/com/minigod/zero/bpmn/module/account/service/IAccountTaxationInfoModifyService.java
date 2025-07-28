package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.entity.AccountTaxationInfoModifyEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountTaxationInfoModifyVO;
import com.minigod.zero.core.mp.base.BaseService;

import java.util.List;

/**
 * 税务信息修改表 服务类
 *
 * @author eric
 * @since 2024-08-05 11:01:01
 */
public interface IAccountTaxationInfoModifyService extends BaseService<AccountTaxationInfoModifyEntity> {
	/**
	 * 持久化税务信息修改记录
	 *
	 * @param entity
	 * @return
	 */
	boolean insert(AccountTaxationInfoModifyEntity entity);

	/**
	 * 根据修改申请ID查询税务信息记录
	 *
	 * @param applyId
	 * @return
	 */
	List<AccountTaxationInfoModifyVO> selectByApplyId(Long applyId);

	/**
	 * 根据开户申请ID查询税务信息修改记录
	 *
	 * @param applicationId
	 * @return
	 */
	List<AccountTaxationInfoModifyVO> selectByApplicationId(String applicationId);

	/**
	 * 根据用户ID查询税务信息修改记录
	 *
	 * @param userId
	 * @return
	 */
	List<AccountTaxationInfoModifyVO> selectByUserId(Long userId);
}
