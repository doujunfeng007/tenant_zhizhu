package com.minigod.zero.customer.api.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.ReqUpdateCustDTO;
import com.minigod.zero.customer.vo.CustAccountInfoVO;
import com.minigod.zero.customer.vo.CustCapitalInfoVO;

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
	List<CustAccountInfoVO> getAccountList();

	/**
	 * 获取客户资金账户列表
	 * @return
	 */
	List<CustCapitalInfoVO> getCapitalList(String moneyType);

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
	R switchCapitalAcct(ReqUpdateCustDTO req);


}
