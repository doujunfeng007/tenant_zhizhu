package com.minigod.zero.data.statistics.service;

import com.minigod.zero.data.entity.Account;

import java.util.List;

/**
 * 账户服务接口
 */
public interface AccountService {
	/**
	 * 根据账户ID获取账户信息
	 * @param accountId 账户ID
	 * @return 账户信息
	 */
	Account getAccountById(String accountId);

	/**
	 * 创建新账户
	 * @param account 账户信息
	 * @return 创建是否成功
	 */
	boolean createAccount(Account account);

	/**
	 * 更新账户信息
	 * @param account 账户信息
	 * @return 更新是否成功
	 */
	boolean updateAccount(Account account);

	/**
	 * 根据条件查询账户列表
	 * @param query 查询条件
	 * @return 账户列表
	 */
	List<Account> queryAccounts(Account query);

	/**
	 * 更新账户状态
	 * @param accountId 账户ID
	 * @param status 新状态
	 * @return 更新是否成功
	 */
	boolean updateAccountStatus(String accountId, String status);

	/**
	 * 更新账户风险信息
	 * @param accountId 账户ID
	 * @param riskScore 风险评分
	 * @param riskLevel 风险等级
	 * @return 更新是否成功
	 */
	boolean updateRiskInfo(String accountId, Integer riskScore, Integer riskLevel);
}
