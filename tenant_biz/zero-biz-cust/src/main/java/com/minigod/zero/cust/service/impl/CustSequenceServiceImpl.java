package com.minigod.zero.cust.service.impl;

import com.minigod.zero.cust.mapper.CustSequenceMapper;
import com.minigod.zero.cust.service.ICustSequenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.service.impl.CustSequenceServiceImpl
 * @Date: 2023年05月05日 14:23
 * @Description:
 */
@Service
public class CustSequenceServiceImpl implements ICustSequenceService {

	@Resource
	private CustSequenceMapper custSequenceMapper;



	@Override
	public Long queryNextSequenceIdByName(String custIdSequenceName) {
		return custSequenceMapper.queryNextSequenceIdByName(custIdSequenceName);
	}
}
