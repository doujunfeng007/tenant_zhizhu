package com.minigod.zero.cust.service;

import com.minigod.zero.cust.apivo.LockVO;

public interface IUserCodeLockUnLockService<T extends LockVO> {


	void checkoutUserCodeLock(T lockVO);
}
