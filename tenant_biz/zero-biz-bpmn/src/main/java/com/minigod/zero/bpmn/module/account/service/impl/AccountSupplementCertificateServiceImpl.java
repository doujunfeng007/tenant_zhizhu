package com.minigod.zero.bpmn.module.account.service.impl;

import com.minigod.zero.bpmn.module.account.vo.AccountSupplementCertificateVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.AccountSupplementCertificateEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountSupplementCertificateMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountSupplementCertificateService;
import com.minigod.zero.core.secure.utils.AuthUtil;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author Chill
 */
@Service
public class AccountSupplementCertificateServiceImpl extends BaseServiceImpl<AccountSupplementCertificateMapper, AccountSupplementCertificateEntity> implements IAccountSupplementCertificateService {

    @Override
    public AccountSupplementCertificateVO queryByApplicationId(String applicationId) {
        return baseMapper.queryByApplicationId(applicationId);
    }

	@Override
	public AccountSupplementCertificateVO queryBySupIdCardNumber(String supIdCardNumber) {
		return baseMapper.queryBySupIdCardNumber(supIdCardNumber, AuthUtil.getTenantCustId());
	}
}
