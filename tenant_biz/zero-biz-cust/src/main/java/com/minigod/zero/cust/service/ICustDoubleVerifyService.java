package com.minigod.zero.cust.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.cust.entity.CustDoubleVerifyEntity;

/**
 * 二重认证信息 服务类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
public interface ICustDoubleVerifyService extends BaseService<CustDoubleVerifyEntity> {


	/**
	 * 查询用户的二重认证信息
	 * @param custId
	 * @param deviceCode
	 * @return
	 */
    CustDoubleVerifyEntity findUserDoubleVerifyRecord(Long custId, String deviceCode);

	/**
	 * 判断当前七天之内是否有无交易解锁
	 * @param custId
	 * @param deviceCode
	 * @return
	 */
	CustDoubleVerifyEntity verifyWtForward(Long custId, String deviceCode);

	/**
	 * 更新2fa配置 ，没有则新增
	 * @param custId
	 * @param deviceCode
	 * @param cancel2fa
	 */
	CustDoubleVerifyEntity updateDoubleVerify(Long custId, String deviceCode, Integer cancel2fa);
}
