package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.vo.AccountBankVerityInfoVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountBankVerityInfoEntity;

/**
 * 银行卡四要素验证信息服务类
 *
 * @author Chill
 */
public interface IAccountBankVerityInfoService extends BaseService<AccountBankVerityInfoEntity> {

	AccountBankVerityInfoVO queryByApplicationId(String applicationId);

	AccountBankVerityInfoEntity queryByBankFourInfo(String bankCard, String idNo, String name, String bankMobileArea, String bankMobile);

	AccountBankVerityInfoEntity queryByBankCard(String bankCard);

	AccountBankVerityInfoEntity queryByIdNo(String idNo);

	AccountBankVerityInfoEntity queryByPhoneNumber(String bankMobile);

	void deleteByApplicationId(String applicationId);

}
