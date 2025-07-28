package com.minigod.zero.bpmn.module.exchange.service;

import com.minigod.zero.bpm.vo.MoneyExchangeInfoVO;
import com.minigod.zero.bpmn.module.exchange.dto.ExchangeQueryReq;
import com.minigod.zero.bpmn.module.exchange.dto.MoneyExchangeReqDTO;
import com.minigod.zero.bpmn.module.exchange.vo.CurrencyExchangeRateInfoVO;
import com.minigod.zero.bpmn.module.exchange.vo.CustomerCurrencyExchangeInfoVO;
import com.minigod.zero.core.tool.api.R;

import java.util.List;
import java.util.Map;

public interface MoneyExchangeRateInfoService {

	/**
	 * 查询各币种兑换汇率
	 *
	 * @return List<MoneyExchangeRateInfoVO>
	 */
	List<CurrencyExchangeRateInfoVO> getMoneyExchangeRateInfo();

	/**
	 * 换汇提交
	 *
	 * @param moneyExchangeReqDTO 提交入参
	 * @return Object
	 */
	R moneyExchange(MoneyExchangeReqDTO moneyExchangeReqDTO);

	/**
	 * 查询用户换汇兑换记录
	 *
	 * @return List<MoneyExchangeInfoVO>
	 */
	List<CustomerCurrencyExchangeInfoVO> getPersonalMoneyExchangeInfo(ExchangeQueryReq exchangeQueryReq);

	/**
	 * 换汇权限
	 * @return Map<String, Integer>
	 */
	Map<String, Integer> moneyExchangeAuth();

	/**
	 * 各币种可兑换余额
	 * @return R
	 */
	R moneyExchangeValue();

	/**
	 * 换汇记录-存在未审批的数据时返回最新一条未审批的数据
	 * @return MoneyExchangeInfoVO
	 */
	MoneyExchangeInfoVO moneyExchangeWarn();

	R cancel(ExchangeQueryReq exchangeQueryReq);

    R queryInfoByTradeAccount(String tradeAccount);

	/**
	 * 人工货币兑换提交
	 * @param moneyExchangeReqDTO
	 * @return
	 */
	R manualMoneyExchange(MoneyExchangeReqDTO moneyExchangeReqDTO);

	/**
	 * 后台获取余额
	 * @param tradeAccount
	 * @param capitalAccount
	 * @return
	 */
	R moneyExchangeValue(String tradeAccount, String capitalAccount);
}
