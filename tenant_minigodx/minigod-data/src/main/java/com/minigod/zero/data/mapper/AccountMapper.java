package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 账户数据访问层接口
 *
 * @author eric
 * @date 2024-10-28 16:07:08
 */
@Mapper
public interface AccountMapper {
	/**
	 * 根据主键查询账户信息
	 *
	 * @param accountId 账户ID
	 * @return 账户信息
	 */
	Account selectByPrimaryKey(String accountId);

	/**
	 * 插入完整的账户信息
	 *
	 * @param account 账户信息
	 * @return 影响行数
	 */
	int insert(Account account);

	/**
	 * 选择性插入账户信息（只插入非null字段）
	 *
	 * @param account 账户信息
	 * @return 影响行数
	 */
	int insertSelective(Account account);

	/**
	 * 根据主键选择性更新账户信息（只更新非null字段）
	 *
	 * @param account 账户信息
	 * @return 影响行数
	 */
	int updateByPrimaryKeySelective(Account account);

	/**
	 * 根据主键更新账户全部信息
	 *
	 * @param account 账户信息
	 * @return 影响行数
	 */
	int updateByPrimaryKey(Account account);

	/**
	 * 根据条件查询账户列表
	 *
	 * @param query 查询条件
	 * @return 账户列表
	 */
	List<Account> selectByCondition(Account query);

	/**
	 * 根据主键删除账户信息
	 *
	 * @param accountId 账户ID
	 * @return 影响行数
	 */
	int deleteByPrimaryKey(String accountId);
}
