package com.minigod.zero.cust.service;

public interface ICustSequenceService {

	/**
	 * 根据序列名称返回下一个序列号
	 * @param custIdSequenceName
	 * @return
	 */
	Long queryNextSequenceIdByName(String custIdSequenceName);
}
