package com.minigod.zero.data.statistics.service.impl;


import com.minigod.zero.data.entity.Transaction;
import com.minigod.zero.data.mapper.TransactionMapper;
import com.minigod.zero.data.statistics.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 交易服务实现类
 *
 * @author eric
 * @date 2024-03-19
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionMapper transactionMapper;

	public TransactionServiceImpl(TransactionMapper transactionMapper) {
		this.transactionMapper = transactionMapper;
	}

	@Override
	public Transaction getById(Integer id) {
		return transactionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int create(Transaction transaction) {
		return transactionMapper.insertSelective(transaction);
	}

	@Override
	public int update(Transaction transaction) {
		return transactionMapper.updateByPrimaryKeySelective(transaction);
	}

	@Override
	public int deleteById(Integer id) {
		return transactionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Transaction> queryByCondition(Transaction query) {
		return transactionMapper.selectByCondition(query);
	}
}
