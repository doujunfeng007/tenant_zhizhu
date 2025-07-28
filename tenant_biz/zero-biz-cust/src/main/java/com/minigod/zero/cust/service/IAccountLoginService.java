package com.minigod.zero.cust.service;


import com.minigod.zero.cust.vo.icbc.*;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.cust.cache.CustInfo;
import com.minigod.zero.cust.entity.IpAddress;
import com.minigod.zero.cust.vo.TradeUnlockReq;
import com.minigod.zero.trade.vo.req.ModifyPwdVO;
import com.minigod.zero.trade.vo.req.ResetTradePwdVO;

import java.util.List;

/**
 * @author:yanghu.luo
 * @create: 2023-03-07 13:46
 * @Description: 客户登录接口
 */
public interface IAccountLoginService {


	/**
	 * 交易解锁
	 */
	R<CustInfo> tradeUnlock(TradeUnlockReq req);

	/**
	 * 账号信息查询
	 */
	R<ClientAccount> accountInfo();

	/**
	 * 用户信息查询
	 */
	R<UserInfo> userInfo();

	/**
	 * 未绑定结算账户查询<br/>
	 * 工银接口：/account/query/client/settlement/list<br/>
	 * @param req
	 * @return
	 */
	R<List<Settleaccts>> icbcaUnboundSettleAcct(IcbcaUnboundSettleAcctReqVO req);

	/**
	 * 绑定结算账户<br/>
	 * 工银接口：/account/query/client/settlement/list<br/>
	 * @param req
	 * @return
	 */
	R icbcaSettleAcct(IcbcaSettleAcctReqVO req);

	/**
	 * W8客户信息查询<br/>
	 * 工银接口：/account/query/client/info<br/>
	 * 工银接口: /account/query/client/addr<br/>
	 * @return
	 */
	R<W8InfoVO> icbcaW8Info();

	/**
	 * W8签署<br/>
	 * 同步到交易前置：http://123.33.49.131:5959/v1/icbcainner/UpdClnAccDoc<br/>
	 * @param req
	 * @return
	 */
	R icbcaW8Create(W8InfoVO req);

	/**
	 * 登陆成功后发送提醒
	 * @param phone
	 */
	void sendMessage(String phone, String email, String deviceName, IpAddress ipAddress);

	/**
	 * 交易密码重置
	 */
	R<Kv> resetTradePwd(ResetTradePwdVO vo);

	/**
	 * 校验交易密码
	 */
	R validTradePwd(TradeUnlockReq req);

	/**
	 * 重置交易密码
	 */
	R modifyTradePwd(ModifyPwdVO request);
}
