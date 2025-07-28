package com.minigod.zero.cust.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.apivo.req.ReqUpdateCustVO;
import com.minigod.zero.cust.cache.AcctInfo;
import com.minigod.zero.cust.vo.response.CustAccountInfoResp;
import com.minigod.zero.cust.vo.response.CustCapitalInfoResp;

import java.util.List;

/**
 * 客户资金账号信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
public interface ICustAccountInfoService {


	/**
	 * 获取客户交易账户列表
	 * @return
	 */
	List<CustAccountInfoResp> getAccountList();

	/**
	 * 获取客户资金账户列表
	 * @return
	 */
	List<CustCapitalInfoResp> getCapitalList(Integer moneyType);

	/**
	 * 切换交易账号
	 * @param tradeAccount
	 * @return
	 */
	R switchTradeAcct(String tradeAccount);

	/**
	 * 切换资金账号
	 * @param req
	 * @return
	 */
	R switchCapitalAcct(ReqUpdateCustVO req);

	/**
	 * 授权人负责公司户列表信息查询
	 * @return List<AcctInfo>
	 */
    List<AcctInfo> getAcctList();

	/**
	 * 切换个人/ESOP户
	 * @param acctType
	 * @return
	 */
	Long switchEsopCust(int acctType);

}
