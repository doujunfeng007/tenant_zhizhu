package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.vo.AccountTaxationInfoVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountTaxationInfoEntity;

import java.util.List;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IAccountTaxationInfoService extends BaseService<AccountTaxationInfoEntity> {
	/**
	 * 根据流水号获取税务信息
	 *
	 * @param applicationId
	 * @return
	 */
	List<AccountTaxationInfoVO> queryListByApplicationId(String applicationId);

	/**
	 * 根据流水号删除税务信息
	 *
	 * @param applicationId
	 */
	void deleteByApplicationId(String applicationId);
}
