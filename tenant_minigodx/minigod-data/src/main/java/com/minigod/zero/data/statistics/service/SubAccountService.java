package com.minigod.zero.data.statistics.service;


import com.minigod.zero.data.entity.SubAccount;

import java.util.List;

/**
 * 子账户服务接口
 *
 * @author eric
 * @date 2024-10-28 16:07:08
 */
public interface SubAccountService {
	/**
	 * 创建子账户
	 *
	 * @param subAccount 子账户信息
	 * @return 创建的子账户信息
	 */
	SubAccount createSubAccount(SubAccount subAccount);

	/**
	 * 更新子账户信息
	 *
	 * @param subAccount 子账户信息
	 * @return 更新后的子账户信息
	 */
	SubAccount updateSubAccount(SubAccount subAccount);

	/**
	 * 获取子账户信息
	 *
	 * @param subAccountId 子账户ID
	 * @return 子账户信息
	 */
	SubAccount getSubAccount(String subAccountId);

	/**
	 * 获取主账户下的所有子账户
	 *
	 * @param accountId 主账户ID
	 * @return 子账户列表
	 */
	List<SubAccount> getSubAccountsByAccountId(String accountId);

	/**
	 * 删除子账户
	 *
	 * @param subAccountId 子账户ID
	 * @return 是否删除成功
	 */
	boolean deleteSubAccount(String subAccountId);
}
