package com.minigod.zero.bpmn.module.account.service.impl;

import com.minigod.zero.bpmn.module.account.vo.AccountBankVerityInfoVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.AccountBankVerityInfoEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountBankVerityInfoMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountBankVerityInfoService;
import com.minigod.zero.core.secure.utils.AuthUtil;
import org.springframework.stereotype.Service;

/**
 *  银行卡四要素验证信息服务实现类
 *
 * @author Chill
 */
@Service
public class AccountBankVerityInfoServiceImpl extends BaseServiceImpl<AccountBankVerityInfoMapper, AccountBankVerityInfoEntity> implements IAccountBankVerityInfoService {

    @Override
    public AccountBankVerityInfoVO queryByApplicationId(String applicationId) {
        return baseMapper.queryByApplicationId(applicationId);
    }

	@Override
	public AccountBankVerityInfoEntity queryByBankFourInfo(String bankCard, String idNo, String name, String bankMobileArea, String bankMobile) {
		return baseMapper.queryByBankFourInfo(bankCard, idNo, name, bankMobileArea, bankMobile);
	}

	@Override
	public AccountBankVerityInfoEntity queryByBankCard(String bankCard) {
		return baseMapper.queryByBankCard(bankCard, AuthUtil.getTenantCustId());
	}

	@Override
	public AccountBankVerityInfoEntity queryByIdNo(String idNo) {
		return baseMapper.queryByIdNo(idNo, AuthUtil.getTenantCustId());
	}

	@Override
	public AccountBankVerityInfoEntity queryByPhoneNumber(String bankMobile) {
		return baseMapper.queryByPhoneNumber(bankMobile, AuthUtil.getTenantCustId());
	}

	@Override
    public void deleteByApplicationId(String applicationId) {
        baseMapper.deleteByApplicationId(applicationId);
    }
}
