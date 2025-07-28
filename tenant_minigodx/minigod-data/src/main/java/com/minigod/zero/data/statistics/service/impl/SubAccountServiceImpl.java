package com.minigod.zero.data.statistics.service.impl;


import com.minigod.zero.data.entity.SubAccount;
import com.minigod.zero.data.mapper.SubAccountMapper;
import com.minigod.zero.data.statistics.service.SubAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 子账户服务实现类
 *
 * @author eric
 * @date 2024-10-28 16:07:08
 */
@Service
public class SubAccountServiceImpl implements SubAccountService {

	private final SubAccountMapper subAccountMapper;

	public SubAccountServiceImpl(SubAccountMapper subAccountMapper) {
		this.subAccountMapper = subAccountMapper;
	}

	@Override
	@Transactional
	public SubAccount createSubAccount(SubAccount subAccount) {
		subAccountMapper.insertSelective(subAccount);
		return subAccount;
	}

	@Override
	@Transactional
	public SubAccount updateSubAccount(SubAccount subAccount) {
		subAccountMapper.updateByPrimaryKeySelective(subAccount);
		return subAccountMapper.selectByPrimaryKey(subAccount.getSubAccountId());
	}

	@Override
	public SubAccount getSubAccount(String subAccountId) {
		return subAccountMapper.selectByPrimaryKey(subAccountId);
	}

	@Override
	public List<SubAccount> getSubAccountsByAccountId(String accountId) {
		return subAccountMapper.selectByAccountId(accountId);
	}

	@Override
	@Transactional
	public boolean deleteSubAccount(String subAccountId) {
		return subAccountMapper.deleteByPrimaryKey(subAccountId) > 0;
	}
}
