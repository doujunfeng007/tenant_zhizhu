package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.vo.AccountSupplementCertificateVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountSupplementCertificateEntity;

/**
 *  服务类
 *
 * @author Chill
 */
public interface IAccountSupplementCertificateService extends BaseService<AccountSupplementCertificateEntity> {

    AccountSupplementCertificateVO queryByApplicationId(String applicationId);

	AccountSupplementCertificateVO queryBySupIdCardNumber(String supIdCardNumber);
}
