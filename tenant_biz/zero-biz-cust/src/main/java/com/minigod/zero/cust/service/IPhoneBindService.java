package com.minigod.zero.cust.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.cust.vo.CustPhoneBindOpenVO;
import com.minigod.zero.cust.vo.CustPhoneBindVO;

/**
 * @author:yanghu.luo
 * @create: 2023-03-29 11:05
 * @Description: 手机号绑定
 */
public interface IPhoneBindService {

	/**
	 * 登录交易账号后，进行手机号绑定
	 */
	String checkPhoneBind(CustPhoneBindVO vo);

	/**
	 * 登录交易账号后，进行手机号绑定
	 */
	R<Kv> phoneBind(CustPhoneBindVO vo);

	/**
	 * 绑定开户手机号到登陆手机号
	 */
	R<Kv> phoneBindOpen(CustPhoneBindOpenVO vo);

	/**
	 * 交易账号/用户号/邮箱登陆绑定注册手机号
	 */
	R<Kv> checkPhoneLogin(CustPhoneBindVO vo);

	/**
	 * 交易账号/用户号/邮箱登陆绑定注册手机号
	 */
	R<Kv> phoneBindLogin(CustPhoneBindVO vo);
}
