package com.minigod.zero.bpmn.module.exchange.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.bpm.vo.CustOpenAccountInfo;
import com.minigod.zero.bpm.vo.MoneyExchangeInfoVO;
import com.minigod.zero.bpmn.module.account.enums.CustomOpenAccountEnum;
import com.minigod.zero.bpmn.module.constant.CashConstant;
import com.minigod.zero.bpmn.module.constant.DepositFundMessageConstant;
import com.minigod.zero.bpmn.module.constant.ErrorMessageConstant;
import com.minigod.zero.bpmn.module.constant.ExchangeMessageConstant;
import com.minigod.zero.bpmn.module.exchange.dto.ExchangeQueryReq;
import com.minigod.zero.bpmn.module.exchange.dto.MoneyExchangeInfoReqDTO;
import com.minigod.zero.bpmn.module.exchange.dto.MoneyExchangeReqDTO;
import com.minigod.zero.bpmn.module.exchange.entity.CurrencyExchangeRateInfo;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeInfo;
import com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum;
import com.minigod.zero.bpmn.module.exchange.feign.ICurrencyExchangeClient;
import com.minigod.zero.bpmn.module.exchange.service.MoneyExchangeRateInfoService;
import com.minigod.zero.bpmn.module.exchange.vo.CurrencyExchangeRateInfoVO;
import com.minigod.zero.bpmn.module.exchange.vo.CustomerCurrencyExchangeInfoVO;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountDetailVO;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountVO;
import com.minigod.zero.bpmn.module.feign.vo.FinancingAccountAmount;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.feign.ICustInfoClient;
import com.minigod.zero.trade.entity.CustOperationLogEntity;
import com.minigod.zero.trade.feign.ICounterFundClient;
import com.minigod.zero.trade.feign.ICustOperationLogClient;
import com.minigod.zero.trade.vo.sjmb.resp.FundQueryVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MoneyExchangeRateInfoServiceImpl implements MoneyExchangeRateInfoService {

	@Value("${bpm.api.url:http://10.9.68.165:7777/bpm}")
	private String cubpBaseUrl;

	private String CUBP_LAST_MONEY_EXCHANGE_RATE_INFO = "";

	private String CUBP_SUBMIT_MONEY_EXCHANGE = "";

	private String CUBP_RUN_MONEY_EXCHANGE = "";

	private String CUBP_PERSONAL_MONEY_EXCHANGE_INFO = "";

	private String CUBP_MONEY_EXCHANGE_WARN = "";
	@Resource
	private ICustInfoClient custInfoClient;
	@Resource
	private ICustOperationLogClient custOperationLogClient;

	@Resource
	private ICurrencyExchangeClient currencyExchangeClient;

	@Resource
	private ICounterFundClient counterFundClient;

	@Resource
	private ICustomerInfoClient iCustomerInfoClient;


	@PostConstruct
	protected void initRestUrl() {
		CUBP_LAST_MONEY_EXCHANGE_RATE_INFO = cubpBaseUrl + "/proxy/moneyExchange/get_last_money_exchange_rate";
		CUBP_SUBMIT_MONEY_EXCHANGE = cubpBaseUrl + "/proxy/moneyExchange/submit_money_exchange";
		CUBP_RUN_MONEY_EXCHANGE = cubpBaseUrl + "/proxy/moneyExchange/runMoneyExchange";
		CUBP_PERSONAL_MONEY_EXCHANGE_INFO = cubpBaseUrl + "/proxy/moneyExchange/get_personal_money_exchange_info";
		CUBP_MONEY_EXCHANGE_WARN = cubpBaseUrl + "/proxy/moneyExchange/get_money_exchange_warn";
	}

	@Override
	public List<CurrencyExchangeRateInfoVO> getMoneyExchangeRateInfo() {
		List<CurrencyExchangeRateInfo> result = currencyExchangeClient.getMoneyRate();
		List<CurrencyExchangeRateInfoVO> list = BeanUtil.copy(result, CurrencyExchangeRateInfoVO.class);
		return list;
	}

	@Override
	public R moneyExchange(MoneyExchangeReqDTO dto) {
		Long custId = AuthUtil.getTenantCustId();
		// BPM获取交易账号
		R<CustomerAccountVO> accountResult = iCustomerInfoClient.customerAccountInfo(custId);
		if (!accountResult.isSuccess()) {
			throw new ServiceException(I18nUtil.getMessage(ErrorMessageConstant.ERROR_GET_ACCOUNT_INFO_FAILED_NOTICE));

			//return R.fail(ResultCode.DISPLAY_ERROR.getCode(), "获取账户信息失败");
		}
		CustomerAccountVO customerAccount = accountResult.getData();
		// 交易账号验证
		if (customerAccount == null) {
			log.warn("客户未绑定交易账号：" + custId);
			throw new ServiceException(I18nUtil.getMessage(ErrorMessageConstant.ERROR_CUSTOMER_NOT_BIND_ACCOUNT_NOTICE));
		}

		// 校验交易密码
//		R result = custTradeClient.checkOldTradePwd(custAcctInfo.getTradeAccount(), moneyExchangeReqDTO.getPwd());
//		if (!result.isSuccess()) {
//			return result;
//		}
		log.info("客户[" + custId + "]货币兑换");
		Integer exchangeDirection = CurrencyExcEnum.ExchangeType.valueOf(dto.getCurrencyOut(), dto.getCurrencyIn()).getExchangeDirection();
		CustomerCurrencyExchangeInfo customerCurrencyExchangeInfo =new CustomerCurrencyExchangeInfo();
		customerCurrencyExchangeInfo.setCustId(AuthUtil.getTenantCustId());
		customerCurrencyExchangeInfo.setFundAccount(customerAccount.getAcct().getCapitalAccount());
		Integer fundAccountType = customerAccount.getCust().getFundAccountType();
		if (fundAccountType == null) {
			fundAccountType = CustomOpenAccountEnum.FundAccountType.CASH.getCode();
		}
		customerCurrencyExchangeInfo.setFundAccountType(fundAccountType.toString());
		customerCurrencyExchangeInfo.setCurrencyOut(dto.getCurrencyOut());
		customerCurrencyExchangeInfo.setCurrencyIn(dto.getCurrencyIn());
		customerCurrencyExchangeInfo.setAmountIn(dto.getAmountIn());
		customerCurrencyExchangeInfo.setAmountOut(dto.getAmountOut());
		customerCurrencyExchangeInfo.setActualAmountOut(dto.getAmountOut());
		customerCurrencyExchangeInfo.setActualAmountIn(dto.getAmountIn());
		customerCurrencyExchangeInfo.setExchangeDirection(exchangeDirection);
		customerCurrencyExchangeInfo.setExchangeRate(dto.getExchangeRate());
		customerCurrencyExchangeInfo.setActualExchangeRate(dto.getExchangeRate());
		customerCurrencyExchangeInfo.setExchangeType(1);
		customerCurrencyExchangeInfo.setTenantId(AuthUtil.getTenantId());
		customerCurrencyExchangeInfo.setTradeAccount(customerAccount.getAcct().getTradeAccount());
		R<FinancingAccountAmount> financingAccountAmountR = iCustomerInfoClient.accountAmount(customerAccount.getAcct().getCapitalAccount(), CashConstant.MoneyTypeDescEnum.getDesc(String.valueOf(dto.getCurrencyOut())));
		if (financingAccountAmountR.isSuccess()) {
			FinancingAccountAmount financingAccountAmount = financingAccountAmountR.getData();
			customerCurrencyExchangeInfo.setAvailableBalance(financingAccountAmount.getAvailableAmount());
			if (dto.getAmountOut() != null && financingAccountAmount.getAvailableAmount() != null) {
				int i = dto.getAmountOut().compareTo(financingAccountAmount.getAvailableAmount());
				if (i > 0) {
					log.warn("客户可提余额不足 cust:{} 余额:{}", custId, financingAccountAmount.getAvailableAmount());
					throw new ServiceException(I18nUtil.getMessage(ExchangeMessageConstant.EXCHANGE_INSUFFICIENT_AVAILABLE_BALANCE_NOTICE));
				}
			} else {
				log.warn("客户申请兑出金额为空或可提余额为空 cust:{} 申请出金额:{},余额:{}", custId, dto.getAmountOut(), financingAccountAmount.getAvailableAmount());
				throw new ServiceException(I18nUtil.getMessage(ExchangeMessageConstant.EXCHANGE_AMOUNT_OR_AVAILABLE_BALANCE_EMPTY_NOTICE));
			}
		} else {
			log.warn("获取可提余额失败：" + custId);
			throw new ServiceException(I18nUtil.getMessage(ExchangeMessageConstant.EXCHANGE_BALANCE_RETRIEVAL_FAILED_NOTICE));
		}

		//可提余额
		//customerCurrencyExchangeInfo.setAvailableBalance(dto.getAvailableBalance());

		//BpmSecuritiesInfoEntity securitiesInfo = securitiesInfoService.securitiesInfoByCustId(AuthUtil.getCustId());
		customerCurrencyExchangeInfo.setClientName(customerAccount.getCust().getCustName());
		R result = currencyExchangeClient.submit(customerCurrencyExchangeInfo);
		if (result.isSuccess()) {
			// 用户操作记录
			try {
				CustOperationLogEntity eustOperationLog = new CustOperationLogEntity();
				eustOperationLog.setCapitalAccount(customerAccount.getAcct().getCapitalAccount());
				eustOperationLog.setTradeAccount(customerAccount.getAcct().getTradeAccount());
				eustOperationLog.setReqParams(com.alibaba.fastjson2.JSON.toJSONString(dto));
				eustOperationLog.setCustId(AuthUtil.getTenantCustId());
				eustOperationLog.setIp(WebUtil.getIP());
				eustOperationLog.setDeviceCode(WebUtil.getHeader(TokenConstant.DEVICE_CODE));
				eustOperationLog.setReqTime(new Date());
				eustOperationLog.setOperationType(CommonEnums.CustOperationType.FUND_APPLY_EXCHANGE.code);
				custOperationLogClient.operationLog(eustOperationLog);
			} catch (Exception e) {
				log.error("记录用户操作日志异常", e);
			}
			return R.success();
		}

		// 返回接口调用异常
		throw new ServiceException(I18nUtil.getMessage(ErrorMessageConstant.ERROR_REQUEST_EXCEPTION_RETRY_NOTICE));
	}

	@Override
	public List<CustomerCurrencyExchangeInfoVO> getPersonalMoneyExchangeInfo(ExchangeQueryReq exchangeQueryReq) {
		// 获取当前登录用户用户号
		Long custId = AuthUtil.getTenantCustId();
		CustomerCurrencyExchangeInfo customerCurrencyExchangeInfo =new CustomerCurrencyExchangeInfo();
		BeanUtil.copy(exchangeQueryReq, customerCurrencyExchangeInfo);
		customerCurrencyExchangeInfo.setCustId(custId);
		return currencyExchangeClient.queryExchangeList(customerCurrencyExchangeInfo);
	}

	/**
	 * 换汇权限
	 *
	 * @return
	 */
	@Override
	public Map<String, Integer> moneyExchangeAuth() {
		// 获取当前登录用户用户号
		Long custId = AuthUtil.getTenantCustId();
		Map<String, Integer> map = new HashMap<String, Integer>();
		/*map.put("moneyExchangeAuth", 1);
		BpmSecuritiesRespDto securitiesRespDto = bpmAccountService.getSecuritiesRespDto(custId);
		if(securitiesRespDto != null && securitiesRespDto.getClientType() != null && securitiesRespDto.getClientType().equals("s")){
			map.put("moneyExchangeAuth", 0);
		}*/
		map.put("moneyExchangeAuth", 0);
		return map;
	}

	@Override
	public R moneyExchangeValue() {

		Long custId = AuthUtil.getTenantCustId();
		// BPM获取交易账号
		/*BpmTradeAcctRespDto custAcctInfo = bpmAccountService.getCurrentAcctInfo(custId);
		R<FundQueryVO> result =counterFundClient.queryFund(custAcctInfo.getTradeAccount(),custAcctInfo.getCapitalAccount());*/
		return R.success();
	}

	/**
	 * 换汇记录-存在未审批的数据时返回最新一条未审批的数据
	 *
	 * @return R
	 */
	@Override
	public MoneyExchangeInfoVO moneyExchangeWarn() {
		// 获取当前登录用户用户号
		Long custId = AuthUtil.getTenantCustId();
		MoneyExchangeInfoReqDTO moneyExchangeInfo = new MoneyExchangeInfoReqDTO();
		moneyExchangeInfo.setUserId(custId);
		Map<String, Object> map = new HashMap<>();
		map.put("params", moneyExchangeInfo);
		MoneyExchangeInfoVO moneyExchangeInfoVO = null;
		// 查询用户换汇兑换记录
		/*String result = HttpClientUtils.postJson(CUBP_MONEY_EXCHANGE_WARN, JSONObject.toJSONString(map), true);
		if (StrUtil.isNotBlank(result)) {
			ResponseVO resultObj = JSONObject.parseObject(result, ResponseVO.class);
			if (resultObj.getCode() == 0 && resultObj.getResult() != null) {
				moneyExchangeInfoVO = JSONObject.parseObject(resultObj.getResult().toString(), MoneyExchangeInfoVO.class);
			}
		}*/
		return moneyExchangeInfoVO;
	}

	@Override
	public R cancel(ExchangeQueryReq exchangeQueryReq) {
		/**
		 * 先解冻金额
		 */
		return currencyExchangeClient.cancel(exchangeQueryReq.getApplicationId());
	}

	@Override
	public R queryInfoByTradeAccount(String tradeAccount) {

		CustOpenAccountInfo custOpenAccountInfo = new CustOpenAccountInfo();
		//CacheAcctInfoVO securitiesUserInfo = bpmSecuritiesInfoMapper.findByTradeAccountOrCustId(tradeAccount, null);
		R<CustomerAccountDetailVO> result = iCustomerInfoClient.selectCustomerDetailByAccountId(tradeAccount);
		if (!result.isSuccess()) {
			return R.fail(result.getMsg());
		}
		if (result.getData() == null) {
			return R.fail("查找客户资料失败");
		}
		if (result.getData().getClientName() == null) {
			custOpenAccountInfo.setCustName(result.getData().getGivenNameSpell());
		} else {
			custOpenAccountInfo.setCustName(result.getData().getClientName());
		}
		custOpenAccountInfo.setCustNameSpell(result.getData().getGivenNameSpell());
		custOpenAccountInfo.setCustId(result.getData().getCustId());
		custOpenAccountInfo.setTradeAccount(tradeAccount);
		custOpenAccountInfo.setCapitalAccount(tradeAccount);
		return R.data(custOpenAccountInfo);
	}

	@Override
	public R manualMoneyExchange(MoneyExchangeReqDTO dto) {
		Long custId = dto.getCustId();
		// BPM获取交易账号
		R<CustomerAccountVO> accountResult = iCustomerInfoClient.customerAccountInfo(custId);
		if (!accountResult.isSuccess()) {
			return R.fail(ResultCode.DISPLAY_ERROR.getCode(), "获取账户信息失败");
		}
		CustomerAccountVO customerAccount = accountResult.getData();
		Integer exchangeDirection = CurrencyExcEnum.ExchangeType.valueOf(dto.getCurrencyOut(), dto.getCurrencyIn()).getExchangeDirection();

		CustomerCurrencyExchangeInfo customerCurrencyExchangeInfo = new CustomerCurrencyExchangeInfo();
		customerCurrencyExchangeInfo.setCustId(custId);
		customerCurrencyExchangeInfo.setFundAccount(customerAccount.getAcct().getCapitalAccount());
		Integer fundAccountType = customerAccount.getCust().getFundAccountType();
		if (fundAccountType == null) {
			fundAccountType = CustomOpenAccountEnum.FundAccountType.CASH.getCode();
		}
		customerCurrencyExchangeInfo.setFundAccountType(fundAccountType.toString());
		customerCurrencyExchangeInfo.setCurrencyOut(dto.getCurrencyOut());
		customerCurrencyExchangeInfo.setCurrencyIn(dto.getCurrencyIn());
		customerCurrencyExchangeInfo.setAmountIn(dto.getAmountIn());
		customerCurrencyExchangeInfo.setAmountOut(dto.getAmountOut());
		customerCurrencyExchangeInfo.setActualAmountOut(dto.getAmountOut());
		customerCurrencyExchangeInfo.setActualAmountIn(dto.getAmountIn());
		customerCurrencyExchangeInfo.setExchangeDirection(exchangeDirection);
		customerCurrencyExchangeInfo.setExchangeRate(dto.getExchangeRate());
		customerCurrencyExchangeInfo.setActualExchangeRate(dto.getExchangeRate());
		customerCurrencyExchangeInfo.setExchangeType(2);
		customerCurrencyExchangeInfo.setTenantId(AuthUtil.getTenantId());
		customerCurrencyExchangeInfo.setTradeAccount(customerAccount.getAcct().getTradeAccount());
		customerCurrencyExchangeInfo.setAvailableBalance(dto.getAvailableBalance());

		//BpmSecuritiesInfoEntity securitiesInfo = securitiesInfoService.securitiesInfoByCustId(custId);
		customerCurrencyExchangeInfo.setClientName(customerAccount.getCust().getCustName());

		R result = currencyExchangeClient.submit(customerCurrencyExchangeInfo);
		if (result.isSuccess()) {
			// 用户操作记录
			try {
				CustOperationLogEntity eustOperationLog = new CustOperationLogEntity();
				eustOperationLog.setCapitalAccount(customerAccount.getAcct().getCapitalAccount());
				eustOperationLog.setTradeAccount(customerAccount.getAcct().getTradeAccount());
				eustOperationLog.setReqParams(com.alibaba.fastjson2.JSON.toJSONString(dto));
				eustOperationLog.setCustId(AuthUtil.getTenantCustId());
				eustOperationLog.setIp(WebUtil.getIP());
				eustOperationLog.setDeviceCode(WebUtil.getHeader(TokenConstant.DEVICE_CODE));
				eustOperationLog.setReqTime(new Date());
				eustOperationLog.setOperationType(CommonEnums.CustOperationType.FUND_APPLY_EXCHANGE.code);
				custOperationLogClient.operationLog(eustOperationLog);
			} catch (Exception e) {
				log.error("记录用户操作日志异常", e);
			}
			return R.success();
		}
		// 返回接口调用异常
		return R.fail(ResultCode.INTERNAL_ERROR);
	}

	@Override
	public R moneyExchangeValue(String tradeAccount, String capitalAccount) {
		R<FundQueryVO> result = counterFundClient.queryFund(tradeAccount, capitalAccount);
		return result;
	}

}
