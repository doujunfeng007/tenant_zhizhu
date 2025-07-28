package com.minigod.zero.data.mapper;


import com.minigod.zero.data.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 交易数据访问层接口
 *
 * @author eric
 * @date 2024-03-19
 */
@Mapper
public interface TransactionMapper {
	/**
	 * 根据主键查询交易信息
	 *
	 * @param id 交易ID
	 * @return 交易信息
	 */
	Transaction selectByPrimaryKey(Integer id);

	/**
	 * 插入完整的交易信息
	 *
	 * @param transaction 交易信息
	 * @return 影响行数
	 */
	int insert(Transaction transaction);

	/**
	 * 选择性插入交易信息（只插入非null字段）
	 *
	 * @param transaction 交易信息
	 * @return 影响行数
	 */
	int insertSelective(Transaction transaction);

	/**
	 * 根据主键选择性更新交易信息（只更新非null字段）
	 *
	 * @param transaction 交易信息
	 * @return 影响行数
	 */
	int updateByPrimaryKeySelective(Transaction transaction);

	/**
	 * 根据主键更新交易全部信息
	 *
	 * @param transaction 交易信息
	 * @return 影响行数
	 */
	int updateByPrimaryKey(Transaction transaction);

	/**
	 * 根据条件查询交易列表
	 *
	 * @param query 查询条件
	 * @return 交易列表
	 */
	List<Transaction> selectByCondition(Transaction query);

	/**
	 * 根据主键删除交易信息
	 *
	 * @param id 交易ID
	 * @return 影响行数
	 */
	int deleteByPrimaryKey(Integer id);
}
