package com.minigod.zero.data.mapper;


import com.minigod.zero.data.entity.SubAccount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 子账户数据访问层接口
 *
 * @author eric
 * @date 2024-10-28 16:07:08
 */
@Mapper
public interface SubAccountMapper {
	/**
	 * 根据主键查询子账户信息
	 *
	 * @param subAccountId 子账户ID
	 * @return 子账户信息
	 */
	SubAccount selectByPrimaryKey(String subAccountId);

	/**
	 * 根据主账户ID查询子账户列表
	 *
	 * @param accountId 主账户ID
	 * @return 子账户列表
	 */
	List<SubAccount> selectByAccountId(String accountId);

	/**
	 * 插入完整的子账户信息
	 *
	 * @param subAccount 子账户信息
	 * @return 影响行数
	 */
	int insert(SubAccount subAccount);

	/**
	 * 选择性插入子账户信息（只插入非null字段）
	 *
	 * @param subAccount 子账户信息
	 * @return 影响行数
	 */
	int insertSelective(SubAccount subAccount);

	/**
	 * 根据主键选择性更新子账户信息（只更新非null字段）
	 *
	 * @param subAccount 子账户信息
	 * @return 影响行数
	 */
	int updateByPrimaryKeySelective(SubAccount subAccount);

	/**
	 * 根据主键更新子账户全部信息
	 *
	 * @param subAccount 子账户信息
	 * @return 影响行数
	 */
	int updateByPrimaryKey(SubAccount subAccount);

	/**
	 * 根据条件查询子账户列表
	 *
	 * @param query 查询条件
	 * @return 子账户列表
	 */
	List<SubAccount> selectByCondition(SubAccount query);

	/**
	 * 根据主键删除子账户信息
	 *
	 * @param subAccountId 子账户ID
	 * @return 影响行数
	 */
	int deleteByPrimaryKey(String subAccountId);
}
