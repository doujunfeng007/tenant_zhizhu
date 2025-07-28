package com.minigod.zero.cust.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.cust.entity.CustOldPasswordEntity;

/**
 * 存量客户密码表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-20
 */
public interface ICustOldPasswordService extends IService<CustOldPasswordEntity> {


	/**
	 * 校验智珠存量用户登录密码，且通过后重新激活登录密码
	 * @param custId 客户ID UIN
	 * @param password 登录密码
	 */
	boolean checkOldLoginPwd(Long custId, String password);

	/**
	 * 仅激活智珠存量用户登录密码
	 * @param custId 客户ID UIN
	 */
	void activeLoginPwd(Long custId);

	/**
	 * 仅校验智珠存量用户交易密码
	 * @param custId 客户ID UIN
	 * @param tradeAccount 交易账号 ClientId
	 * @param password 交易密码
	 */
	boolean checkOldTradePwd(Long custId, String tradeAccount, String password);

	/**
	 * 仅激活智珠存量用户交易密码
	 * @param custId 客户ID UIN
	 * @param tradeAccount 交易账号 ClientId
	 */
	void activeTradePwd(Long custId, String tradeAccount);


}
