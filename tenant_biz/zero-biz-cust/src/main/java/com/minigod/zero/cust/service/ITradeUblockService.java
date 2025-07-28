package com.minigod.zero.cust.service;

import com.minigod.zero.cust.vo.Cust2faVO;
import com.minigod.zero.cust.vo.TradeUnlockReq;
import com.minigod.zero.cust.vo.TradeUnlockRes;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.cust.vo.*;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;

public interface ITradeUblockService {

	/**
	 * 交易解锁（校验交易账号密码）
	 * @param tradeUnlockReq
	 * @return
	 */
	R<TradeUnlockRes> tradeUnlock(TradeUnlockReq tradeUnlockReq);

	/**
	 * 交易2FA验证（短信校验银行卡开户手机号）
	 * @param cust2faVO
	 * @return
	 */
	R<Kv> cust2fa(Cust2faVO cust2faVO);

	/**
	 * 获取2FA验证码
	 * @param cust2faVO
	 * @return
	 */
	R get2faCaptcha(Cust2faVO cust2faVO);

	/**
	 * 交易登录注销
	 * @param tradeToken
	 * @return
	 */
	boolean tradeLogout(String tradeToken);

	/**
	 * 交易密码重置
	 */
	R resetTradePwd(ResetTradePwdVO vo);

	/**
	 * 校验交易密码
	 */
	R validTradePwd(TradeUnlockReq req);

	/**
	 * 修改交易密码
	 */
	R modifyTradePwd(ModifyPwdVO vo);

}
