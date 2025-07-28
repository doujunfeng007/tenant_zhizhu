package com.minigod.zero.customer.api.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.customer.vo.Cust2faVO;
import com.minigod.zero.customer.vo.TradeUnlockReq;
import com.minigod.zero.customer.vo.TradeUnlockRes;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/21 10:05
 * @description：
 */
public interface ITradeUblockService {
	/**
	 * 交易解锁（校验交易账号密码）
	 * @param tradeUnlockReq
	 * @return
	 */
	R<TradeUnlockRes> tradeUnlock(TradeUnlockReq tradeUnlockReq);

	R<TradeUnlockRes> tradeUnlock(Long custId,String pwd);

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
	 * 发送重置交易密码邮件验证码
	 * @param cust2faVO
	 * @return
	 */
	R sendEmailCaptcha(Cust2faVO cust2faVO);
}
