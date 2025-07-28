package com.minigod.zero.cust.service;

import com.minigod.zero.cust.apivo.LockVO;

public interface ICommUnLockUnLockService <T extends LockVO>{

	/**
	 * 解锁
	 */
	void unlock(T lockVO) throws Exception;
}
