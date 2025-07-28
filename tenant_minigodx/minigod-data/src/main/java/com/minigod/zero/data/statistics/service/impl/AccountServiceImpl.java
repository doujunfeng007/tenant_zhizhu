package com.minigod.zero.data.statistics.service.impl;

import com.minigod.zero.data.entity.Account;
import com.minigod.zero.data.mapper.AccountMapper;
import com.minigod.zero.data.statistics.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 账户服务实现类
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

	private final AccountMapper accountMapper;

	public AccountServiceImpl(AccountMapper accountMapper) {
		this.accountMapper = accountMapper;
	}

	@Override
	public Account getAccountById(String accountId) {
		log.info("查询账户信息, accountId: {}", accountId);
		return accountMapper.selectByPrimaryKey(accountId);
	}

	@Override
	public boolean createAccount(Account account) {
		log.info("创建新账户, account: {}", account);
		// 设置创建时间和更新时间
		account.setCreateTime(new Date());
		account.setUpdateTime(new Date());
		return accountMapper.insertSelective(account) > 0;
	}

	@Override
	public boolean updateAccount(Account account) {
		log.info("更新账户信息, account: {}", account);
		// 更新修改时间
		account.setUpdateTime(new Date());
		return accountMapper.updateByPrimaryKeySelective(account) > 0;
	}

	@Override
	public List<Account> queryAccounts(Account query) {
		log.info("查询账户列表, query: {}", query);
		return accountMapper.selectByCondition(query);
	}

	@Override
	public boolean updateAccountStatus(String accountId, String status) {
		log.info("更新账户状态, accountId: {}, status: {}", accountId, status);
		Account account = new Account();
		account.setAccountId(accountId);
		account.setStatus(status);
		account.setUpdateTime(new Date());
		return accountMapper.updateByPrimaryKeySelective(account) > 0;
	}

	@Override
	public boolean updateRiskInfo(String accountId, Integer riskScore, Integer riskLevel) {
		log.info("更新账户风险信息, accountId: {}, riskScore: {}, riskLevel: {}",
			accountId, riskScore, riskLevel);
		Account account = new Account();
		account.setAccountId(accountId);
		account.setRiskScore(riskScore);
		account.setRiskLevel(riskLevel);
		account.setRiskUpdateTime(new Date());
		account.setUpdateTime(new Date());
		return accountMapper.updateByPrimaryKeySelective(account) > 0;
	}
}
