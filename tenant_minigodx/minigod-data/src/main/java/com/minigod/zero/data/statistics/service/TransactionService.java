package com.minigod.zero.data.statistics.service;


import com.minigod.zero.data.entity.Transaction;

import java.util.List;

/**
 * 交易服务接口
 *
 * @author eric
 * @date 2024-03-19
 */
public interface TransactionService {
	/**
	 * 根据ID获取交易信息
	 *
	 * @param id 交易ID
	 * @return 交易信息
	 */
	Transaction getById(Integer id);

	/**
	 * 创建交易记录
	 *
	 * @param transaction 交易信息
	 * @return 影响行数
	 */
	int create(Transaction transaction);

	/**
	 * 更新交易信息
	 *
	 * @param transaction 交易信息
	 * @return 影响行数
	 */
	int update(Transaction transaction);

	/**
	 * 根据ID删除交易记录
	 *
	 * @param id 交易ID
	 * @return 影响行数
	 */
	int deleteById(Integer id);

	/**
	 * 根据条件查询交易列表
	 *
	 * @param query 查询条件
	 * @return 交易列表
	 */
	List<Transaction> queryByCondition(Transaction query);
}
