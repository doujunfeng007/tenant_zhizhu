package com.minigod.zero.cust.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.cust.vo.TradeAccountBindVO;
import com.minigod.zero.cust.vo.TradeAccountBind2faVO;

/**
 * @author:yanghu.luo
 * @create: 2023-03-29 11:07
 * @Description: 交易账号绑定
 */
public interface ITradeAccountBindService {

	/**
	 * 交易账号绑定
	 */
	String checkAcct(String tradeAccount);

	/**
	 * 交易账号绑定并解锁，数据合并
	 */
	R<Kv> checkAcc2fa(TradeAccountBind2faVO req);

	/**
	 * 2fa校验
	 */
	R<String> acctBind(TradeAccountBindVO req);
}

