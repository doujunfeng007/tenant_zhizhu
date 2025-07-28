package com.minigod.zero.customer.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.minigod.zero.biz.common.constant.AccountMessageConstant;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.biz.common.constant.TenantConstant;
import com.minigod.zero.biz.common.exception.BusinessException;
import com.minigod.zero.biz.common.utils.ValidateUtil;
import com.minigod.zero.bpm.feign.IBpmAccountClient;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.customer.api.service.AppCustomerInfoService;
import com.minigod.zero.customer.api.service.ISaasCustomerService;
import com.minigod.zero.customer.back.service.ICustomerTradeAccountService;
import com.minigod.zero.customer.back.service.ICustomerTradeSubAccountService;
import com.minigod.zero.customer.config.GoldProperties;
import com.minigod.zero.customer.dto.AmountDTO;
import com.minigod.zero.customer.dto.CustomerBasicInfoDTO;
import com.minigod.zero.customer.dto.CustomerInfoDTO;
import com.minigod.zero.customer.dto.CustomerOpenAccountInfoDTO;
import com.minigod.zero.customer.emuns.BusinessTypeEnums;
import com.minigod.zero.customer.emuns.CustomerStatus;
import com.minigod.zero.customer.emuns.MarketTypeEnums;
import com.minigod.zero.customer.entity.*;
import com.minigod.zero.customer.enums.*;
import com.minigod.zero.customer.events.EventType;
import com.minigod.zero.customer.events.MiniGodEventSource;
import com.minigod.zero.customer.factory.EventPublisherFactory;
import com.minigod.zero.customer.factory.ExchangeRateFactory;
import com.minigod.zero.customer.mapper.*;
import com.minigod.zero.customer.proxy.ProxyCustomerDetailVO;
import com.minigod.zero.customer.utils.IdGenerateUtils;
import com.minigod.zero.customer.utils.RSANewUtil;
import com.minigod.zero.customer.vo.*;
import com.minigod.zero.customer.vo.stock.CustomerStockInfo;
import com.minigod.zero.platform.dto.OpenAccountDTO;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.feign.IAccountClient;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.platform.vo.FundAccountVO;
import com.minigod.zero.platform.vo.SubAccountVO;
import com.minigod.zero.system.feign.IDictBizClient;
import com.minigod.zero.trade.feign.ICustAccountClient;
import com.minigod.zero.trade.vo.sjmb.resp.AssetInfoVO;
import com.minigod.zero.trade.vo.sjmb.resp.FundQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.minigod.zero.core.tool.api.R.data;

/**
 * @Author: guangjie.liao
 * @Date: 2024/4/14 14:47
 * @Description:
 */
@Slf4j
@Service
public class AppCustomerInfoServiceImpl implements AppCustomerInfoService {

	@Autowired
	private IAccountClient iAccountClient;
	@Autowired
	private CustomerInfoMapper customerInfoMapper;

	@Autowired
	private CustomerFundTradingAccountMapper customerFundTradingAccountMapper;

	@Autowired
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;

	@Autowired
	private CustomerFundTradingAccountMapper fundTradingAccountMapper;

	@Autowired
	private CustomerFundCapitalAccountMapper fundCapitalAccountMapper;

	@Autowired
	private CustomerBasicInfoMapper customerBasicInfoMapper;

	@Autowired
	private FinancingAccountAmountMapper financingAccountAmountMapper;

	@Autowired
	private CustomerAppSettingsMapper customerAppSettingsMapper;

	@Autowired
	private ExchangeRateFactory exchangeRateFactory;

	@Autowired
	private CustomerHldTradingAccountMapper customerHldTradingAccountMapper;

	@Autowired
	private CustomerHldCapitalAccountMapper customerHldCapitalAccountMapper;

	@Autowired
	private CustomerBondCapitalAccountMapper customerBondCapitalAccountMapper;
	@Autowired
	private CustomerBondTradingAccountMapper customerBondTradingAccountMapper;
	@Autowired
	private CustomerDebentureTradingAccountMapper customerDebentureTradingAccountMapper;
	@Autowired
	private CustomerDebentureCapitalAccountMapper customerDebentureCapitalAccountMapper;

	@Autowired
	private CustomerDoubleVerifyMapper customerDoubleVerifyMapper;

	@Autowired
	private IdGenerateUtils idGenerateUtils;

	@Autowired
	private CustomerCashAssetsHistoryMapper customerCashAssetsHistoryMapper;

	@Autowired
	private CustomerFundAssetsHistoryMapper customerFundAssetsHistoryMapper;

	@Autowired
	private CustomerHldAssetsHistoryMapper customerHldAssetsHistoryMapper;

	@Autowired
	private CustomerBondAssetsHistoryMapper customerBondAssetsHistoryMapper;

	@Autowired
	private CustomerTotalAssetsHistoryMapper customerTotalAssetsHistoryMapper;

	@Autowired
	private CustomerRealnameVerifyMapper customerRealnameVerifyMapper;

	@Autowired
	private GoldProperties goldProperties;

	@Value("${open_account.deposit_amount.min}")
	private BigDecimal minDepositAmount;

	@Autowired
	private ISaasCustomerService saasCustomerService;

	@Autowired
	private AccountBusinessFlowMapper accountBusinessFlowMapper;

	@Autowired
	private CustomerWhiteListMapper customerWhiteListMapper;

	@Autowired
	private FinancingAccountAmountFlowsMapper financingAccountAmountFlowsMapper;

	@Resource
	private IDictBizClient dictBizClient;
	@Autowired
	private CustomerIdentityInfoMapper customerPcRoleMapper;

	@Autowired
	private CustomerFinancingSubAccountMapper customerFinancingSubAccountMapper;

	@Autowired
	private CustomerDebentureCapitalAccountMapper debentureCapitalAccountMapper;

	@Autowired
	private CustomerDebentureTradingAccountMapper debentureTradingAccountMapper;

	@Resource
	private IBpmAccountClient bpmAccountClient;

	@Resource
	private ICustAccountClient custAccountClient;

	@Value("${tenant.business.type}")
	private String businessType;

	@Value("${tenant.stock.market}")
	private String stockMarket;

	@Autowired
	private ICustomerTradeAccountService customerTradeAccountService;

	@Autowired
	private ICustomerTradeSubAccountService customerTradeSubAccountService;

	@Override
	public R customerRegister(CustomerInfoDTO customerInfoDTO) {
		CustomerInfoVO customerInfo = this.defaultRegister(customerInfoDTO.getCellPhone(), customerInfoDTO.getAreaCode(), customerInfoDTO.getTenantId());
		/**
		 * 注册SAAS端
		 */
		R result = saasCustomerService.register(customerInfo);
		if (!result.isSuccess()) {
			return result;
		}
		return data(customerInfo);
	}

	@Override
	@Transactional
	public R<FundAccountVO> fundOpenAccount(OpenAccountDTO fundOpenAccount) {
		//开通基金
		FundAccountVO fundAccountVO = openFundAccount(fundOpenAccount);
		//开通活力得
		hldOpenAccount(fundOpenAccount);
		//开通债券易
		bondOpenAccount(fundOpenAccount);
		//更新债券账户
		updateOtcAccount(fundOpenAccount);
		return R.data(fundAccountVO);
	}


	private void updateOtcAccount(OpenAccountDTO openAccount){
		openAccount.setBusiType(TradeAccountType.DEBENTURE.getCode());

		CustomerDebentureTradingAccount debentureTradingAccount =
			debentureTradingAccountMapper.selectByCustId(openAccount.getCustId());

		if (debentureTradingAccount != null){
			openAccount.setAccountId(debentureTradingAccount.getTradeAccount());
			iAccountClient.updateFundAccount(openAccount);
		}
	}

	@Override
	public FundAccountVO openOtcAccount(OpenAccountDTO openAccount){
		openAccount.setBusiType(TradeAccountType.DEBENTURE.getCode());

		CustomerDebentureTradingAccount debentureTradingAccount =
			debentureTradingAccountMapper.selectByCustId(openAccount.getCustId());

		if (debentureTradingAccount != null){
			openAccount.setAccountId(debentureTradingAccount.getTradeAccount());
			R<FundAccountVO> result = iAccountClient.updateFundAccount(openAccount);
			return result.getData();
		}
		else{
			//柜台开户
			R<FundAccountVO> result = iAccountClient.fundOpenAccount(openAccount);
			if (result.getCode() != ResultCode.SUCCESS.getCode()) {
				throw new ZeroException(
					String.format(
						I18nUtil.getMessage(
							AccountMessageConstant.ACCOUNT_OPEN_FUND_OPEN_FAIL_NOTICE), openAccount.getCustId(), result.getMsg()));
			}
			FundAccountVO fundAccountVO = result.getData();
			String tradeAccount = fundAccountVO.getAccountId();

			debentureTradingAccount = new CustomerDebentureTradingAccount();
			debentureTradingAccount.setAccountId(openAccount.getExtAccountId());
			debentureTradingAccount.setTradeAccount(tradeAccount);
			debentureTradingAccount.setCustId(openAccount.getCustId());
			debentureTradingAccount.setCreateTime(new Date());
			debentureTradingAccount.setIsDeleted(0);
			debentureTradingAccountMapper.insertSelective(debentureTradingAccount);

			List<SubAccountVO> subAccounts = fundAccountVO.getSubAccounts();
			if (CollectionUtil.isEmpty(subAccounts)) {
				throw new ZeroException(
					String.format(
						I18nUtil.getMessage(
							AccountMessageConstant.ACCOUNT_OPEN_FUND_OPEN_FAIL_NO_SUB_ACCOUNT_NOTICE), openAccount.getCustId()));
			}

			for (SubAccountVO subAccount : subAccounts){
				CustomerDebentureCapitalAccount debentureCapitalAccount = new CustomerDebentureCapitalAccount();
				debentureCapitalAccount.setTradeAccount(tradeAccount);
				debentureCapitalAccount.setFundAccount(subAccount.getSubAccountId());
				debentureCapitalAccount.setCreateTime(new Date());
				debentureCapitalAccount.setIsDeleted(0);
				debentureCapitalAccountMapper.insertSelective(debentureCapitalAccount);
			}
			return fundAccountVO;
		}
	}

	private FundAccountVO openFundAccount(OpenAccountDTO fundOpenAccount) {
		Long custId = fundOpenAccount.getCustId();
		if (custId == null) {
			custId = AuthUtil.getTenantUser().getUserId();
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			throw new BusinessException(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_FAIL_NOT_OPEN_FINANCING));
		}
		CustomerBasicInfoEntity customerBasicInfo = customerBasicInfoMapper.selectByCustId(custId);
		if (customerBasicInfo != null) {
			fundOpenAccount.setChineseName(customerBasicInfo.getClientName());
			fundOpenAccount.setFirstName(customerBasicInfo.getGivenNameSpell());
			fundOpenAccount.setLastName(customerBasicInfo.getFamilyNameSpell());
		}
		//更新风险等级
		if (fundOpenAccount.getRiskLevel() != null && fundOpenAccount.getExpiryDate() != null) {
			log.info("账号={}更新风险等级：{}", custId, fundOpenAccount.getRiskLevel());
			Date expiryDate = DateUtil.parseDate(DateUtil.format(fundOpenAccount.getExpiryDate(), DateUtil.PATTERN_DATE));
			customerInfoMapper.updateRiskLevel(custId, fundOpenAccount.getRiskLevel(), expiryDate);
		}
		//账户已开通则修改账户信息
		fundOpenAccount.setBusiType(TradeAccountType.FUND.getCode());
		/**
		 * 基金账号
		 */
		CustomerTradeAccountEntity fundTradingAccountEntity =
			customerTradeAccountService.selectTradeByAccountAndType(financingAccount.getAccountId(), BusinessTypeEnums.FUND.getBusinessType());
		if (fundTradingAccountEntity != null) {
			//更新基金账号信息
			fundOpenAccount.setAccountId(fundTradingAccountEntity.getTradeAccount());
			R<FundAccountVO> result = iAccountClient.updateFundAccount(fundOpenAccount);
			log.info("更新基金账号请求返回结果,{}", JsonUtil.toJson(result));
			return result.getData();
		}
		//基金柜台开户
		fundOpenAccount.setExtAccountId(financingAccount.getAccountId());
		log.info("用户{}提交基金开户", custId);
		R<FundAccountVO> result = iAccountClient.fundOpenAccount(fundOpenAccount);
		if (result.getCode() != ResultCode.SUCCESS.getCode()) {
			log.error("用户:{}基金开户失败,失败原因:{}", custId, result.getMsg());
			throw new BusinessException(String.format(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_FUND_OPEN_FAIL_NOTICE), custId, result.getMsg()));
		}
		FundAccountVO fundAccountVO = result.getData();
		fundAccountVO.setRiskLevel(fundOpenAccount.getRiskLevel());
		//交易账号保存
		String tradeAccount = fundAccountVO.getAccountId();
		log.info("用户{}基金开户成功:{}", custId, tradeAccount);
		fundTradingAccountEntity = new CustomerTradeAccountEntity();
		fundTradingAccountEntity.setBusinessType(BusinessTypeEnums.FUND.getBusinessType());

		fundTradingAccountEntity.setTradeAccount(tradeAccount);

		fundTradingAccountEntity.setAccountId(financingAccount.getAccountId());
		fundTradingAccountEntity.setAccountType(fundOpenAccount.getAccountType().toString());
		fundTradingAccountEntity.setAccountStatus(0);
		customerTradeAccountService.addTradeAccount(fundTradingAccountEntity);

		List<SubAccountVO> subAccounts = fundAccountVO.getSubAccounts();
		if (CollectionUtil.isEmpty(subAccounts)) {
			throw new BusinessException(String.format(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_FUND_OPEN_FAIL_NO_SUB_ACCOUNT_NOTICE), custId));
		}
		Long tradeAccountId = fundTradingAccountEntity.getId();
		//基金子账号保存
		for (SubAccountVO subAccount : subAccounts) {
			CustomerTradeSubAccountEntity fundCapitalAccountEntity = new CustomerTradeSubAccountEntity();
			fundCapitalAccountEntity.setTradeAccountId(tradeAccountId);
			fundCapitalAccountEntity.setAccountId(financingAccount.getAccountId());
			fundCapitalAccountEntity.setTradeAccount(tradeAccount);
			fundCapitalAccountEntity.setSubAccount(subAccount.getSubAccountId());
			fundCapitalAccountEntity.setMarketType(MarketTypeEnums.FUND.getMarketType());
			fundCapitalAccountEntity.setCreateTime(new Date());
			customerTradeSubAccountService.addSubAccount(fundCapitalAccountEntity);
		}
		PushUtil.builder().custId(custId).group(MsgStaticType.DisplayGroup.SERVICE_MSG)
			.templateCode(PushTemplate.FUND_ACCOUNT_OPEN.getCode()).pushAsync();
		return fundAccountVO;
	}

	@Override
	@Transactional
	public CustomerTradeAccountEntity hldOpenAccount(OpenAccountDTO fundOpenAccount) {
		Long custId = fundOpenAccount.getCustId();
		if (custId == null) {
			custId = AuthUtil.getTenantUser().getUserId();
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			throw new BusinessException(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_FAIL_NOT_OPEN_FINANCING));
		}
		CustomerBasicInfoEntity customerBasicInfo = customerBasicInfoMapper.selectByCustId(custId);
		if (customerBasicInfo != null) {
			fundOpenAccount.setChineseName(customerBasicInfo.getClientName());
			fundOpenAccount.setFirstName(customerBasicInfo.getGivenNameSpell());
			fundOpenAccount.setLastName(customerBasicInfo.getFamilyNameSpell());
		}
		fundOpenAccount.setBusiType(TradeAccountType.HLD.getCode());
		//更新账户，如风险等级

		/**
		 * 活利得交易账号
		 */
		CustomerTradeAccountEntity customerTradeAccountEntity =
			customerTradeAccountService.selectTradeByAccountAndType(financingAccount.getAccountId(), BusinessTypeEnums.HLD.getBusinessType());

		if (customerTradeAccountEntity != null) {
			fundOpenAccount.setAccountId(customerTradeAccountEntity.getTradeAccount());
			R<FundAccountVO> result = iAccountClient.updateFundAccount(fundOpenAccount);
			if (result != null && result.getCode() == ResultCode.SUCCESS.getCode()){
				//更新本地账户风险等级
				customerTradeAccountEntity.setUpdateTime(new Date());
				customerTradeAccountService.updateById(customerTradeAccountEntity);
			}
			return customerTradeAccountEntity;
		}
		fundOpenAccount.setExtAccountId(financingAccount.getAccountId());
		R<FundAccountVO> result = iAccountClient.fundOpenAccount(fundOpenAccount);
		if (result.getCode() != ResultCode.SUCCESS.getCode()) {
			throw new BusinessException(String.format(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_FUND_OPEN_FAIL_NO_SUB_ACCOUNT_NOTICE), custId));
		}
		FundAccountVO fundAccountVO = result.getData();
		customerTradeAccountEntity = new CustomerTradeAccountEntity();

		customerTradeAccountEntity.setBusinessType(BusinessTypeEnums.HLD.getBusinessType());
		customerTradeAccountEntity.setCreateTime(new Date());
		customerTradeAccountEntity.setTradeAccount(fundAccountVO.getAccountId());
		customerTradeAccountEntity.setAccountId(financingAccount.getAccountId());
		customerTradeAccountEntity.setAccountType(fundOpenAccount.getAccountType().toString());
		customerTradeAccountEntity.setAccountStatus(0);
		customerTradeAccountService.addTradeAccount(customerTradeAccountEntity);
		List<SubAccountVO> subAccounts = fundAccountVO.getSubAccounts();
		if (CollectionUtil.isEmpty(subAccounts)) {
			throw new BusinessException(String.format(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_FUND_OPEN_FAIL_NO_SUB_ACCOUNT_NOTICE), custId));
		}
		Long tradeAccountId = customerTradeAccountEntity.getId();
		subAccounts.stream().forEach(subAccount -> {
			CustomerTradeSubAccountEntity hldCapitalAccount = new CustomerTradeSubAccountEntity();
			hldCapitalAccount.setTradeAccountId(tradeAccountId);
			hldCapitalAccount.setAccountId(financingAccount.getAccountId());

			hldCapitalAccount.setTradeAccount(fundAccountVO.getAccountId());
			hldCapitalAccount.setSubAccount(subAccount.getSubAccountId());
			hldCapitalAccount.setMarketType(MarketTypeEnums.HLD.getMarketType());
//			hldCapitalAccount.setAccountStatus(0);
			customerTradeSubAccountService.addSubAccount(hldCapitalAccount);
		});
		PushUtil.builder().custId(custId).group(MsgStaticType.DisplayGroup.SERVICE_MSG)
			.templateCode(PushTemplate.HUOLIDE_ACCOUNT_OPEN.getCode()).pushAsync();
		return customerTradeAccountEntity;
	}

	@Override
	public CustomerTradeAccountEntity bondOpenAccount(OpenAccountDTO fundOpenAccount) {
		Long custId = fundOpenAccount.getCustId();
		if (custId == null) {
			custId = AuthUtil.getTenantUser().getUserId();
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			throw new BusinessException(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_FAIL_NOT_OPEN_FINANCING));
		}
		CustomerBasicInfoEntity customerBasicInfo = customerBasicInfoMapper.selectByCustId(custId);
		if (customerBasicInfo != null) {
			fundOpenAccount.setChineseName(customerBasicInfo.getClientName());
			fundOpenAccount.setFirstName(customerBasicInfo.getGivenNameSpell());
			fundOpenAccount.setLastName(customerBasicInfo.getFamilyNameSpell());
		}
		fundOpenAccount.setBusiType(TradeAccountType.ZQY.getCode());
		/**
		 * 债券交易账号
		 */
		CustomerTradeAccountEntity bondTradingAccount =
			customerTradeAccountService.selectTradeByAccountAndType(financingAccount.getAccountId(), BusinessTypeEnums.BOND.getBusinessType());
		if (bondTradingAccount != null) {
			fundOpenAccount.setAccountId(bondTradingAccount.getTradeAccount());
			R<FundAccountVO> result = iAccountClient.updateFundAccount(fundOpenAccount);
//			if (result != null && result.getCode() == ResultCode.SUCCESS.getCode()) {
//				//更新本地账户风险等级
//				bondTradingAccount.setRiskLevel(fundOpenAccount.getRiskLevel());
//				customerBondTradingAccountMapper.updateByPrimaryKeySelective(bondTradingAccount);
//			}
			return bondTradingAccount;
		}
		fundOpenAccount.setExtAccountId(financingAccount.getAccountId());
		R<FundAccountVO> result = iAccountClient.fundOpenAccount(fundOpenAccount);
		if (result.getCode() != ResultCode.SUCCESS.getCode()) {
			throw new BusinessException(String.format(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_FAIL_NOTICE), result.getMsg()));
		}
		FundAccountVO fundAccountVO = result.getData();
		bondTradingAccount = new CustomerTradeAccountEntity();
		bondTradingAccount.setBusinessType(BusinessTypeEnums.BOND.getBusinessType());
		bondTradingAccount.setTradeAccount(fundAccountVO.getAccountId());
		bondTradingAccount.setAccountId(financingAccount.getAccountId());
		bondTradingAccount.setAccountType(fundOpenAccount.getAccountType().toString());
		bondTradingAccount.setAccountStatus(0);
		customerTradeAccountService.addTradeAccount(bondTradingAccount);
		List<SubAccountVO> subAccounts = fundAccountVO.getSubAccounts();
		if (CollectionUtil.isEmpty(subAccounts)) {
			throw new BusinessException(String.format(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_FUND_OPEN_FAIL_NO_SUB_ACCOUNT_NOTICE), custId));
		}
		Long tradeAccountId = bondTradingAccount.getId();
		subAccounts.stream().forEach(subAccount -> {
			CustomerTradeSubAccountEntity bondCapitalAccount = new CustomerTradeSubAccountEntity();
			bondCapitalAccount.setTradeAccountId(tradeAccountId);
			bondCapitalAccount.setAccountId(financingAccount.getAccountId());

			bondCapitalAccount.setTradeAccount(fundAccountVO.getAccountId());
			bondCapitalAccount.setSubAccount(subAccount.getSubAccountId());
			bondCapitalAccount.setMarketType(MarketTypeEnums.BOND.getMarketType());
//			hldCapitalAccount.setAccountStatus(0);
			customerTradeSubAccountService.addSubAccount(bondCapitalAccount);
		});

		PushUtil.builder().custId(custId).group(MsgStaticType.DisplayGroup.SERVICE_MSG)
			.templateCode(PushTemplate.BONDEASE_ACCOUNT_OPEN.getCode()).pushAsync();
		return bondTradingAccount;
	}

	/**
	 * 按汇率转换成统一货币展示
	 *
	 * @return
	 */
	@Override
	public R accountTotalBalance(String currency, String accountId) {
		CustomerAccountBalanceVO accountBalanceVO = new CustomerAccountBalanceVO();
		if (StringUtils.isEmpty(accountId)) {
			accountId = AuthUtil.getTenantUser().getAccount();
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByAccountId(accountId);
		if (financingAccount == null) {
			return R.data(accountBalanceVO);
		}
		accountBalanceVO.setAccountId(financingAccount.getAccountId());
		accountBalanceVO.setCustId(financingAccount.getCustId());
		accountBalanceVO.setTenantId(financingAccount.getTenantId());
		this.selectPurchasingPower(accountBalanceVO, accountId, currency);
		return R.data(accountBalanceVO);
	}

	@Override
	public CustomerInfoVO loadCustomerByUsername(String userName, String areaCode, String tenantId, String param) {
		CustomerInfoEntity entity = customerInfoMapper.loadCustomerByUsername(userName, areaCode, tenantId);
		if (entity == null) {
			CustomerInfoVO customerInfoVO = this.defaultRegister(userName, areaCode, tenantId);
			//注册成功处理
			JSONObject handParam = new JSONObject();
			handParam.put("param", param);
			handParam.put("custInfo", customerInfoVO);
			EventPublisherFactory.publishEvent(new MiniGodEventSource(handParam, EventType.REGISTER_SUCCESS));
			return customerInfoVO;
		}
		return getCustomerInfoVO(entity.getId());
	}

	@Override
	public CustomerInfoVO loadCustomerByAccount(String userName, String tenantId) {
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByAccountIdAndTenantId(userName, tenantId);
		if (financingAccount == null) {
			return null;
		}
		CustomerInfoVO customerInfoVO = getCustomerInfoVO(financingAccount.getCustId());
		//使用理财账号+密码登录是，构造用户对象，将理财账号密码赋值给对象
		//兼容手机号+密码登录
		customerInfoVO.setPassword(financingAccount.getPassword());
		customerInfoVO.setPwdUpdTime(financingAccount.getUpdatePwdTime());
		return customerInfoVO;
	}

	@Override
	public CustomerInfoVO loadCustomerBySubAccount(String accountId, String subAccount) {
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByAccountId(accountId);
		if (financingAccount == null) {
			return null;
		}
		CustomerInfoVO customerInfoVO = getCustomerInfoVO(financingAccount.getCustId());

		CustomerFinancingSubAccount financingSubAccount =
			customerFinancingSubAccountMapper.selectByAccountIdAndSubAccount(accountId,subAccount);
		if (financingAccount == null){
			return null;
		}
		customerInfoVO.setRoleId(financingSubAccount.getRoleId());
		customerInfoVO.setPassword(financingSubAccount.getPassword());
		customerInfoVO.setAccount(financingSubAccount.getSubAccount());
		return customerInfoVO;
	}

	private CustomerInfoVO getCustomerInfoVO(Long custId) {
		CustomerInfoEntity entity = customerInfoMapper.selectByCustId(custId);
		CustomerInfoVO customerInfoVO = new CustomerInfoVO();
		BeanUtils.copyProperties(entity, customerInfoVO);
		CustomerFinancingAccountEntity financingAccountEntity = customerFinancingAccountMapper.selectByCustId(entity.getId());
		if (financingAccountEntity != null) {
			customerInfoVO.setAccount(financingAccountEntity.getAccountId());
			if (!financingAccountEntity.getStatus().equals(FinancingAccountStatus.NOT_ACTIVE.getCode())) {
				CustomerBasicInfoEntity basicInfoEntity = customerBasicInfoMapper.selectByCustId(entity.getId());
				if (basicInfoEntity != null) {
					customerInfoVO.setEmail(basicInfoEntity.getEmail());
					customerInfoVO.setRealName(basicInfoEntity.getClientName());
				}
			}
		}
		CustomerIdentityInfo pcRole = customerPcRoleMapper.selectDefaultRoleIdByCustId(custId);
		if (pcRole != null) {
			customerInfoVO.setRoleId(pcRole.getRoleId());
		}
		//查询白名单
		CustomerWhiteList customerWhiteList = customerWhiteListMapper.selectByCustId(entity.getId(), entity.getTenantId());
		customerInfoVO.setIsWhiteList(customerWhiteList != null);
		return customerInfoVO;
	}

	@Override
	@Transactional
	public CustomerInfoVO defaultRegister(String phone, String areaCode, String tenantId) {
		//区号加手机号一起校验
		boolean flag = ValidateUtil.validatePhone(areaCode, phone);
		if (!flag) {
			throw new BusinessException(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_FAIL_PHONE_ERROR_NOTICE));
		}
		CustomerInfoEntity customerInfoEntity = customerInfoMapper.loadCustomerByUsername(phone, areaCode, tenantId);
		if (customerInfoEntity != null) {
			throw new BusinessException(I18nUtil.getMessage(CommonConstant.ACCOUNT_ALREADY_EXISTS));
		}
		CustomerInfoEntity entity = new CustomerInfoEntity();
		entity.setCellPhone(phone);
		entity.setAreaCode(areaCode);
		entity.setCreateTime(new Date());
		entity.setTenantId(tenantId);
		entity.setNickName(IdGenerateUtils.getNickName() + phone.substring(phone.length() - 4));
		entity.setCustType(CustEnums.CustType.GENERAL.getCode());
		entity.setStatus(CustomerStatus.NORMAL.getCode());
		entity.setIsDeleted(0);
		//entity.setPassword(new BCryptPasswordEncoder().encode(GoldDictFactory.builder().code(DictConstant.DEFAULT_PASSWORD).getKey()));

		customerInfoMapper.insertSelective(entity);

		//本地理财账户开通
		CustomerFinancingAccountEntity financingAccount = new CustomerFinancingAccountEntity();
		financingAccount.setCustId(entity.getId());
		financingAccount.setTenantId(tenantId);
		financingAccount.setAccountId(idGenerateUtils.generateId());
		financingAccount.setStatus(FinancingAccountStatus.NOT_ACTIVE.getCode());
		financingAccount.setIsDeleted(0);
		customerFinancingAccountMapper.insert(financingAccount);

		CustomerInfoEntity newCustomer = customerInfoMapper.loadCustomerByUsername(phone, areaCode, tenantId);
		CustomerInfoVO customerInfoVO = new CustomerInfoVO();
		BeanUtils.copyProperties(newCustomer, customerInfoVO);
		customerInfoVO.setAccount(financingAccount.getAccountId());

		return customerInfoVO;
	}

	private CustomerAccountBalanceVO selectPurchasingPower(CustomerAccountBalanceVO accountBalanceVO, String accountId, String currency) {
		List<FinancingAccountAmount> accountAmountList = financingAccountAmountMapper.selectByAccountId(accountId, null);

		// 判空处理
		if (CollectionUtil.isEmpty(accountAmountList)) {
			return accountBalanceVO;
		}

		// 初始化金额列表
		List<BigDecimal> availableAmountList = new ArrayList<>();
		List<BigDecimal> freezeAmountList = new ArrayList<>();
		List<BigDecimal> transitedAmountList = new ArrayList<>();

		// 计算可用、冻结和在途金额
		accountAmountList.forEach(accountAmount -> {
			String sourceCurrency = accountAmount.getCurrency();
			availableAmountList.add(exchangeRateFactory.exchange(currency, sourceCurrency, accountAmount.getAvailableAmount()));
			freezeAmountList.add(exchangeRateFactory.exchange(currency, sourceCurrency, accountAmount.getFreezeAmount()));
			transitedAmountList.add(exchangeRateFactory.exchange(currency, sourceCurrency, accountAmount.getTransitedAmount()));
		});

		// 设置货币类型
		accountBalanceVO.setCurrency(currency);

		// 计算总金额
		accountBalanceVO.setAvailableAmount(calculateTotal(availableAmountList));
		accountBalanceVO.setFreezeAmount(calculateTotal(freezeAmountList));
		accountBalanceVO.setTransitedAmount(calculateTotal(transitedAmountList));

		// 查询市值
		R result = iAccountClient.accountAssetAll(accountId, currency);
		if (!result.isSuccess()) {
			throw new BusinessException(result.getMsg());
		}

		// 处理市值数据
		JSONObject marketValue = (JSONObject) result.getData();
		BigDecimal positionMarketValue = calculateMarketValue(marketValue);
		BigDecimal totalGainLoss = calculateTotalGainLoss(marketValue);
		BigDecimal positionIncome = calculatePositionIncome(marketValue);
		BigDecimal hldPositionIncome = getPositionIncome(marketValue, TradeAccountType.HLD.getValue());

		// 计算总资产
		BigDecimal totalFreezeAmount = accountBalanceVO.getFreezeAmount();
		exchangeRateFactory.cleanCache();
		BigDecimal totalAsset = accountBalanceVO.getAvailableAmount()
			.add(positionMarketValue)
			.add(hldPositionIncome)
			.add(totalFreezeAmount)
			.add(accountBalanceVO.getTransitedAmount());

		// 设置账户余额信息
		accountBalanceVO.setTotalAsset(totalAsset);
		accountBalanceVO.setTotalGainLoss(positionIncome);
		accountBalanceVO.setHldMarketValue(getMarketValue(marketValue,  TradeAccountType.HLD.getValue()));
		accountBalanceVO.setFundMarketValue(getMarketValue(marketValue, TradeAccountType.FUND.getValue()));
		accountBalanceVO.setBondMarketValue(getMarketValue(marketValue, TradeAccountType.ZQY.getValue()));
		accountBalanceVO.setOtcMarketValue(getMarketValue(marketValue, TradeAccountType.DEBENTURE.getValue()));
		accountBalanceVO.setTotalFreezeAmount(totalFreezeAmount);
		accountBalanceVO.setAccumulatedRevenue(totalGainLoss);
		accountBalanceVO.setPositionMarketValue(positionMarketValue);

		return accountBalanceVO;
	}

	private BigDecimal calculateTotal(List<BigDecimal> amounts) {
		return amounts.stream()
			.filter(Objects::nonNull) // 过滤掉 null 值
			.reduce(BigDecimal.ZERO, BigDecimal::add)
			.setScale(4, RoundingMode.HALF_UP);
	}

	private BigDecimal calculateMarketValue(JSONObject marketValue) {
		BigDecimal fundMarketValue = getMarketValue(marketValue, TradeAccountType.FUND.getValue());
		BigDecimal hldMarketValue = getMarketValue(marketValue, TradeAccountType.HLD.getValue());
		BigDecimal bondMarketValue = getMarketValue(marketValue, TradeAccountType.ZQY.getValue());
		BigDecimal otcMarketValue = getMarketValue(marketValue, TradeAccountType.DEBENTURE.getValue());
		return fundMarketValue.add(hldMarketValue).add(bondMarketValue).add(otcMarketValue).setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal calculateTotalGainLoss(JSONObject marketValue) {
		BigDecimal hldGainLoss = getTotalGainLoss(marketValue, TradeAccountType.HLD.getValue());
		BigDecimal bondGainLoss = getTotalGainLoss(marketValue, TradeAccountType.ZQY.getValue());
		BigDecimal fundGainLoss = getTotalGainLoss(marketValue,  TradeAccountType.FUND.getValue());
		BigDecimal otcGainLoss = getTotalGainLoss(marketValue,  TradeAccountType.DEBENTURE.getValue());
		return hldGainLoss.add(bondGainLoss).add(fundGainLoss).add(otcGainLoss);
	}

	private BigDecimal calculatePositionIncome(JSONObject marketValue) {
		BigDecimal hldPositionIncome = getPositionIncome(marketValue, TradeAccountType.HLD.getValue());
		BigDecimal bondPositionIncome = getPositionIncome(marketValue, TradeAccountType.ZQY.getValue());
		BigDecimal fundPositionIncome = getPositionIncome(marketValue, TradeAccountType.FUND.getValue());
		BigDecimal otcPositionIncome = getPositionIncome(marketValue, TradeAccountType.DEBENTURE.getValue());
		return hldPositionIncome.add(bondPositionIncome).add(fundPositionIncome).add(otcPositionIncome);
	}

	private BigDecimal getMarketValue(JSONObject marketValue, String key) {
		JSONObject jsonObject = marketValue.getJSONObject(key);
		return jsonObject != null ? jsonObject.getBigDecimal("marketValue") : BigDecimal.ZERO;
	}

	private BigDecimal getTotalGainLoss(JSONObject marketValue, String key) {
		JSONObject jsonObject = marketValue.getJSONObject(key);
		return jsonObject != null ? jsonObject.getBigDecimal("totalGainLoss") : BigDecimal.ZERO;
	}

	private BigDecimal getPositionIncome(JSONObject marketValue, String key) {
		JSONObject jsonObject = marketValue.getJSONObject(key);
		return jsonObject != null ? jsonObject.getBigDecimal("positionEarnings") : BigDecimal.ZERO;
	}
	/**
	 * 持仓收益
	 *
	 * @param object
	 * @return
	 */
	private BigDecimal getTotalGainLoss(JSONObject object) {
		if (object == null) {
			return BigDecimal.ZERO;
		}
		Object totalGainLoss = object.get("positionEarnings");
		if (totalGainLoss == null) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(String.valueOf(totalGainLoss));
	}

	/**
	 * 区分不同货币展示
	 *
	 * @param currency 币种
	 * @return
	 */
	@Override
	public R selectAccountBalance(String currency, String accountId) {
		if (StringUtils.isEmpty(accountId)) {
			accountId = AuthUtil.getTenantUser().getAccount();
		}
		AccountBalanceVO accountBalanceVO = new AccountBalanceVO();
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByAccountId(accountId);
		if (financingAccount == null) {
			return R.data(accountBalanceVO);
		}

		List<String> businessTypeList = Arrays.asList(businessType.split(","));
		if (businessTypeList.contains(TenantConstant.STOCK_TYPE)) {
			// 证券
			List<FinancingAccountAmount> financingAccountAmounts = new ArrayList<>();
			CustomerStockInfo customerStockInfo = selectStockAccount(financingAccount.getAccountId());
			R<FundQueryVO> result = custAccountClient.getDetailAccount(financingAccount.getCustId(), customerStockInfo.getCapitalAccounts().get(0).getCapitalAccount(), null);
			if (result.isSuccess()) {
				List<AssetInfoVO> fundStats = result.getData().getFundStats();
				for (AssetInfoVO fundStat : fundStats) {
					FinancingAccountAmount financingAccountAmount = new FinancingAccountAmount();
					financingAccountAmount.setAvailableAmount(fundStat.getFetchBalance());
					financingAccountAmount.setCurrency(fundStat.getCurrency());
					financingAccountAmounts.add(financingAccountAmount);
				}

			} else {
				return result;
			}
			accountBalanceVO.setFinancingAccountBalance(financingAccountAmounts);
		} else {

			List<FinancingAccountAmount> financingAccountAmounts = financingAccountAmountMapper.selectByAccountId(accountId, currency);
			if (!CollectionUtil.isEmpty(financingAccountAmounts)) {
				financingAccountAmounts.stream().forEach(accountAmount -> {
					accountAmount.setTotalAmount(accountAmount.getAvailableAmount()
						.add(accountAmount.getFreezeAmount()).add(accountAmount.getTransitedAmount()));
				});
			}
			accountBalanceVO.setFinancingAccountBalance(financingAccountAmounts);
		}
		return R.data(accountBalanceVO);
	}

	@Override
	public R selectCustomerDetail(Long custId) {
		if (custId == null) {
			custId = AuthUtil.getTenantUser().getUserId();
		}
		AppCustomerDetailVO customerDetail = new AppCustomerDetailVO();
		//注册信息
		CustomerInfoEntity customerInfoEntity = customerInfoMapper.selectByCustId(custId);
		if (customerInfoEntity == null) {
			return R.data(customerDetail);
		}
		customerDetail.setCustId(customerInfoEntity.getId());
		customerDetail.setStatus(customerInfoEntity.getStatus());
		customerDetail.setPiLevel(customerInfoEntity.getPiLevel());
		customerDetail.setNickName(customerInfoEntity.getNickName());
		customerDetail.setUserIcon(customerInfoEntity.getCustIcon());
		customerDetail.setAreaCode(customerInfoEntity.getAreaCode());
		customerDetail.setPhoneNum(customerInfoEntity.getCellPhone());
		customerDetail.setRiskType(customerInfoEntity.getRiskLevel());
		customerDetail.setInvCustId(customerInfoEntity.getInvCustId());
		customerDetail.setDerivative(customerInfoEntity.getDerivative());
		customerDetail.setCreateTime(customerInfoEntity.getCreateTime());
		customerDetail.setCustChannel(customerInfoEntity.getCustChannel());
		customerDetail.setHldRiskType(customerInfoEntity.getPiRiskLevel());
		customerDetail.setBondRiskType(customerInfoEntity.getPiRiskLevel());
		customerDetail.setRiskExpiryDate(customerInfoEntity.getRiskExpiryDate());
		customerDetail.setHasLoginPass(!StringUtils.isEmpty(customerInfoEntity.getPassword()));
		customerDetail.setHldPermission(goldProperties.getHasHldPermission());
		//开户信息
		CustomerBasicInfoEntity basicInfoEntity = customerBasicInfoMapper.selectByCustId(custId);
		if (basicInfoEntity != null) {
			customerDetail.setCustEmail(basicInfoEntity.getEmail());
			customerDetail.setOpenAreaCode(basicInfoEntity.getPhoneArea());
			customerDetail.setOpenPhoneNum(basicInfoEntity.getPhoneNumber());
			customerDetail.setAccountType(basicInfoEntity.getAccountType());
			customerDetail.setAccountLevel(basicInfoEntity.getAccountLevel());
			customerDetail.setOpenAccountTime(basicInfoEntity.getCreateTime());
			String customerEnName = null;
			if (!StringUtils.isEmpty(basicInfoEntity.getFamilyNameSpell()) && !StringUtils.isEmpty(basicInfoEntity.getGivenNameSpell())) {
				customerEnName = basicInfoEntity.getFamilyNameSpell() + " " + basicInfoEntity.getGivenNameSpell();
			}
			String customerName = basicInfoEntity.getClientName();
			if (StringUtils.isEmpty(customerName)) {
				customerName = customerEnName;
			}
			customerDetail.setCustomerName(customerName);
			customerDetail.setCustomerEnName(customerEnName);
		}
		//实名信息
		CustomerRealnameVerifyEntity realnameVerify = customerRealnameVerifyMapper.selectByCustId(custId);
		if (realnameVerify != null) {
			customerDetail.setGender(realnameVerify.getGender());
			customerDetail.setIdCard(realnameVerify.getIdCard());
			customerDetail.setIdKind(realnameVerify.getIdKind());
		}
		//app设置
		CustomerAppSettingsEntity appSettings = customerAppSettingsMapper.selectByCustId(custId);
		if (appSettings != null) {
			customerDetail.setPrivacy(appSettings.getPrivacy());
		}
		//统一账号
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null ||
			FinancingAccountStatus.NOT_ACTIVE.getCode().equals(financingAccount.getStatus())) {
			return R.data(customerDetail);
		}
		//已開戶
		customerDetail.setOpenAccount(true);
		Integer flag = financingAccount.getUpdatePwdTime() == null ? 0 : 1;
		customerDetail.setIsUpdateTradePwd(flag);
		customerDetail.setAccountId(financingAccount.getAccountId());
		customerDetail.setCustomerType(financingAccount.getAccountType());
		customerDetail.setFinancingAccountStatus(financingAccount.getStatus());

		/**
		 * 查询交易账号
		 */
		List<CustomerTradeAccountEntity> tradeList = customerTradeAccountService.selectTradeByAccount(financingAccount.getAccountId());

		List<CustomerTradeSubAccountEntity> subAccountList = customerTradeSubAccountService.selectByAccountId(financingAccount.getAccountId());
		//基金交易账号

		CustomerTradeAccountEntity fundTradingAccountEntity =
			tradeList.stream().filter(trade -> trade.getBusinessType().equals(BusinessTypeEnums.FUND.getBusinessType())).findFirst().orElse(null);
		if (fundTradingAccountEntity != null) {
			customerDetail.setTradeAccount(fundTradingAccountEntity.getTradeAccount());
		}
		//基金资金账号
		List<CustomerTradeSubAccountEntity> capitalAccountEntityList =
			subAccountList.stream().filter(subAccount -> subAccount.getMarketType().equals(MarketTypeEnums.FUND.getMarketType())).collect(Collectors.toList());
		if (!CollectionUtil.isEmpty(capitalAccountEntityList)) {
			List<CapitalAccountVO> capitalAccounts = new ArrayList<>();
			customerDetail.setCapitalAccounts(capitalAccounts);
			capitalAccountEntityList.stream().forEach(capitalAccount -> {
				CapitalAccountVO account = new CapitalAccountVO();
				account.setCapitalAccount(capitalAccount.getSubAccount());
				capitalAccounts.add(account);
			});
		}
		//活利得交易账号
		CustomerTradeAccountEntity hldTradingAccount =
			tradeList.stream().filter(trade -> trade.getBusinessType().
				equals(BusinessTypeEnums.HLD.getBusinessType())).findFirst().orElse(null);
		if (hldTradingAccount != null) {
//			if (customerDetail.getHldRiskType() == null) {
//				customerDetail.setHldRiskType(hldTradingAccount.getRiskLevel());
//			}
			customerDetail.setHldTradeAccount(hldTradingAccount.getTradeAccount());
		}
		//活利得资金账号
		List<CustomerTradeSubAccountEntity> hldCapitalAccounts =
			subAccountList.stream().filter(subAccount -> subAccount.getMarketType().equals(MarketTypeEnums.HLD.getMarketType())).collect(Collectors.toList());
		if (!CollectionUtil.isEmpty(hldCapitalAccounts)) {
			List<CapitalAccountVO> capitalAccounts = new ArrayList<>();
			customerDetail.setHldCapitalAccounts(capitalAccounts);
			hldCapitalAccounts.stream().forEach(capitalAccount -> {
				CapitalAccountVO account = new CapitalAccountVO();
//				account.setAccountType(capitalAccount.getAccountType());
				account.setCapitalAccount(capitalAccount.getSubAccount());
				capitalAccounts.add(account);
			});
		}
		//债券易账号
		CustomerTradeAccountEntity bondTradingAccount =
			tradeList.stream().filter(trade -> trade.getBusinessType().
				equals(BusinessTypeEnums.BOND.getBusinessType())).findFirst().orElse(null);

		if (bondTradingAccount != null) {
//			if (customerDetail.getBondRiskType() == null) {
//				customerDetail.setBondRiskType(bondTradingAccount.getRiskLevel());
//			}
			customerDetail.setBondTradeAccount(bondTradingAccount.getTradeAccount());
		}
		//债券易资金账号

		List<CustomerTradeSubAccountEntity> BondCapitalAccounts =
			subAccountList.stream().filter(subAccount -> subAccount.getMarketType().equals(MarketTypeEnums.BOND.getMarketType())).collect(Collectors.toList());
		if (!CollectionUtil.isEmpty(BondCapitalAccounts)) {
			List<CapitalAccountVO> capitalAccounts = new ArrayList<>();
			customerDetail.setBondCapitalAccounts(capitalAccounts);
			BondCapitalAccounts.stream().forEach(capitalAccount -> {
				CapitalAccountVO account = new CapitalAccountVO();
//				account.setAccountType(capitalAccount.getAccountType());
				account.setCapitalAccount(capitalAccount.getSubAccount());
				capitalAccounts.add(account);
			});
		}
		CustomerDebentureTradingAccount debentureTradingAccount = customerDebentureTradingAccountMapper.selectByCustId(custId);
		if (debentureTradingAccount != null){
			customerDetail.setDebentureTradingAccount(debentureTradingAccount.getTradeAccount());
		}

		CustomerStockInfo customerStockInfo =selectStockAccount(financingAccount.getAccountId());
		customerDetail.setCustomerStockInfo(customerStockInfo);

		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		customerDetail.setNeed2fa(need2fa(custId, request.getHeader("DeviceCode")));
		return R.data(customerDetail);
	}

	private boolean need2fa(Long custId, String deviceCode) {
		CustomerDoubleVerify custDoubleVerifyEntity = customerDoubleVerifyMapper.findUserDoubleVerifyRecord(custId, deviceCode);
		if (custDoubleVerifyEntity == null) {
			// 首次需要做2fa
			return true;
		}
		// 非首次
		custDoubleVerifyEntity = customerDoubleVerifyMapper.verifyWtForward(custId, deviceCode);
		if (custDoubleVerifyEntity == null) {
			return true;
		}
		Integer selectedType = custDoubleVerifyEntity.getSelectedType();
		if (selectedType == 0) {
			return true;
		}
		return false;
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public R updateCustomerPiLevel(Integer piLevel, Long custId) {
		if (piLevel == null) {
			throw new BusinessException(ResultCode.PARAM_MISS.getMessage());
		}
		if (custId == null) {
			throw new BusinessException(ResultCode.PARAM_MISS.getMessage());
		}

		CustomerInfoEntity customer = customerInfoMapper.selectById(custId);
		if (customer == null) {
			throw new BusinessException(I18nUtil.getMessage(CommonConstant.ABNORMAL_USER_INFORMATION));
		}
		CustomerInfoEntity updateCustomer = new CustomerInfoEntity();
		updateCustomer.setId(customer.getId());
		updateCustomer.setPiLevel(piLevel);
		updateCustomer.setUpdateTime(new Date());
		customerInfoMapper.updateByPrimaryKeySelective(updateCustomer);

		log.info("用户{}pi等级更新成功", custId);
		//活利得开户
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			return R.success(ResultCode.SUCCESS);
		}
		OpenAccountDTO openAccountDTO = new OpenAccountDTO();
		openAccountDTO.setPiFlag(1);
		openAccountDTO.setCustId(custId);
		openAccountDTO.setAccountType(1);
		openAccountDTO.setRiskLevel(customer.getPiRiskLevel());
		openAccountDTO.setExtAccountId(financingAccount.getAccountId());
		//基金开户
		this.openFundAccount(openAccountDTO);
		log.info("用户{}基金开户成功", custId);
		//开债券易账户
		CustomerTradeAccountEntity bondTradingAccount = this.bondOpenAccount(openAccountDTO);
		log.info("用户{}债券易开户成功", custId);
		CustomerTradeAccountEntity hldTradingAccount = this.hldOpenAccount(openAccountDTO);
		log.info("用户{}活利得开户成功", custId);
		Map<String, Object> data = new HashMap<>();
		data.put("bondAccount", bondTradingAccount);
		data.put("hldAccount", hldTradingAccount);
		return R.data(data);
	}

	@Override
	public R updateCustomerDerivative(Long custId) {
		if (custId == null) {
			throw new BusinessException(ResultCode.PARAM_MISS.getMessage());
		}
		CustomerInfoEntity customer = customerInfoMapper.selectById(custId);
		if (customer == null) {
			throw new BusinessException(I18nUtil.getMessage(CommonConstant.ABNORMAL_USER_INFORMATION));
		}
		CustomerInfoEntity updateCustomer = new CustomerInfoEntity();
		updateCustomer.setId(customer.getId());
		updateCustomer.setDerivative(StatusEnum.YES.getCode());
		updateCustomer.setUpdateTime(new Date());
		customerInfoMapper.updateByPrimaryKeySelective(updateCustomer);
		return R.success();
	}

	private void saveBusinessFlow(AmountDTO amountDTO, List<FinancingAccountAmountFlows> accountAmountFlows, Integer operationType, CustomerFinancingAccountEntity financingAccount) {
		String flowId = null;
		if (!CollectionUtil.isEmpty(accountAmountFlows)) {
			flowId = accountAmountFlows.stream().map(flow -> {
				return String.valueOf(flow.getId());
			}).collect(Collectors.joining());
		}
		AccountBusinessFlow accountBusiness = new AccountBusinessFlow();
		accountBusiness.setFlowId(flowId);
		accountBusiness.setCreateTime(new Date());
		accountBusiness.setOperationType(operationType);
		accountBusiness.setAmount(amountDTO.getAmount());
		accountBusiness.setCurrency(amountDTO.getCurrency());
		accountBusiness.setCustId(financingAccount.getCustId());
		accountBusiness.setBusinessId(amountDTO.getBusinessId());
		accountBusiness.setBusinessType(amountDTO.getThawingType());
		accountBusiness.setAccountId(financingAccount.getAccountId());
		accountBusiness.setRemark(ThawingType.getByCode(amountDTO.getThawingType()).getDesc());
		accountBusinessFlowMapper.insertSelective(accountBusiness);
	}

	private BigDecimal doScratchButton(FinancingAccountAmount accountAmount,
									   BigDecimal scratchButtonAmount,
									   String targetCurrency, List<FinancingAccountAmountFlows> result, Integer thawingType) {
		Long amountId = accountAmount.getId();
		//这里对划扣金额进行一次汇率转换
		scratchButtonAmount = exchangeRateFactory.exchange(accountAmount.getCurrency(), targetCurrency, scratchButtonAmount);
		BigDecimal availableAmount = accountAmount.getAvailableAmount();
		//划扣可用金额
		BigDecimal lastSurplus = BigDecimal.ZERO;
		if (availableAmount.compareTo(scratchButtonAmount) >= 0) {
			financingAccountAmountMapper.scratchButton(amountId, scratchButtonAmount);
		} else {
			financingAccountAmountMapper.scratchButton(amountId, availableAmount);
			lastSurplus = scratchButtonAmount.subtract(availableAmount);
		}
		BigDecimal amountLog = lastSurplus.compareTo(BigDecimal.ZERO) > 0 ? availableAmount : scratchButtonAmount;
		if (amountLog.compareTo(BigDecimal.ZERO) > 0) {
			result.addAll(saveFinancingAccountAmountFlows(amountLog, thawingType, accountAmount, accountAmount.getCurrency(), AmountOperationType.WITHDRAWAL.getCode()));
		}
		//转回传入币种
		return exchangeRateFactory.exchange(targetCurrency, accountAmount.getCurrency(), lastSurplus);

	}

	/**
	 * 理财账户金额流水
	 *
	 * @param amount
	 * @param thawingType
	 * @param financingAccountAmount
	 * @param currency
	 */
	private List<FinancingAccountAmountFlows> saveFinancingAccountAmountFlows(BigDecimal amount, Integer thawingType,
																			  FinancingAccountAmount financingAccountAmount, String currency, Integer type) {

		List<FinancingAccountAmountFlows> accountAmountFlows = new ArrayList<>();

		FinancingAccountAmountFlows financingAccountAmountFlow = new FinancingAccountAmountFlows();
		financingAccountAmountFlow.setAmount(amount);
		financingAccountAmountFlow.setCurrency(currency);
		financingAccountAmountFlow.setCreateTime(new Date());
		financingAccountAmountFlow.setAccountId(financingAccountAmount.getAccountId());
		financingAccountAmountFlow.setRemark(ThawingType.getByCode(thawingType).getDesc());
		//获取操作后剩余金额
		FinancingAccountAmount afterAccountAmount = financingAccountAmountMapper.selectByPrimaryKey(financingAccountAmount.getId());
		if (AmountOperationType.DEPOSIT.getCode() == type.intValue() || AmountOperationType.WITHDRAWAL.getCode() == type.intValue()) {
			//可用加
			financingAccountAmountFlow.setRemark(financingAccountAmountFlow.getRemark() + "(现金)");
			financingAccountAmountFlow.setType(AmountType.AVAILABLE_AMOUNT.getCode());
			financingAccountAmountFlow.setBeforeAmount(financingAccountAmount.getAvailableAmount());
			financingAccountAmountFlow.setAfterAmount(afterAccountAmount.getAvailableAmount());
			financingAccountAmountFlowsMapper.insertSelective(financingAccountAmountFlow);

			accountAmountFlows.add(financingAccountAmountFlow);
		}
		if (AmountOperationType.FREEZE.getCode().intValue() == type || AmountOperationType.UNFREEZE_RETURN.getCode().intValue() == type) {
			String remark = financingAccountAmountFlow.getRemark();
			//可用减，冻结加
			FinancingAccountAmountFlows freezeAmount = financingAccountAmountFlow;
			freezeAmount.setBeforeAmount(financingAccountAmount.getFreezeAmount());
			freezeAmount.setAfterAmount(afterAccountAmount.getFreezeAmount());
			freezeAmount.setType(AmountType.FREEZE_AMOUNT.getCode());
			freezeAmount.setRemark(remark + "(冻结)");
			financingAccountAmountFlowsMapper.insertSelective(freezeAmount);
			accountAmountFlows.add(freezeAmount);

			FinancingAccountAmountFlows availableAmount = financingAccountAmountFlow;
			availableAmount.setType(AmountType.AVAILABLE_AMOUNT.getCode());
			availableAmount.setBeforeAmount(financingAccountAmount.getAvailableAmount());
			availableAmount.setAfterAmount(afterAccountAmount.getAvailableAmount());
			availableAmount.setRemark(remark + "(现金)");
			financingAccountAmountFlowsMapper.insertSelective(availableAmount);
			accountAmountFlows.add(availableAmount);
		}

		if (AmountOperationType.UNFREEZE.getCode() == type) {
			//冻结减
			financingAccountAmountFlow.setRemark(financingAccountAmountFlow.getRemark() + "(冻结)");
			financingAccountAmountFlow.setType(AmountType.FREEZE_AMOUNT.getCode());
			financingAccountAmountFlow.setBeforeAmount(financingAccountAmount.getFreezeAmount());
			financingAccountAmountFlow.setAfterAmount(afterAccountAmount.getFreezeAmount());
			financingAccountAmountFlowsMapper.insertSelective(financingAccountAmountFlow);
			accountAmountFlows.add(financingAccountAmountFlow);
		}
		return accountAmountFlows;
	}

	/**
	 * 理财账户
	 *
	 * @param accountId
	 * @return
	 */
	private CustomerFinancingAccountEntity checkFinancingAccount(String accountId) {
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByAccountId(accountId);
		if (financingAccount == null) {
			throw new BusinessException("操作失败，理财账号不存在！");
		}
//		if (!financingAccount.getStatus().equals(FinancingAccountStatus.NORMAL.getCode())){
//			throw new BusinessException("操作失败，统一账户未激活！");
//		}
		return financingAccount;
	}

	@Override
	public R updateHldAccountRiskLevel(Integer riskLevel) {
		if (riskLevel == null) {
			return R.fail(ResultCode.PARAM_MISS);
		}
		Long custId = AuthUtil.getTenantUser().getUserId();
		CustomerInfoEntity customerInfo = customerInfoMapper.selectById(custId);
		if (customerInfo == null) {
			return R.fail(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_UPDATE_HLD_RISK_LEVEL_FAIL_NOTICE));
		}
		log.info("用户{}更新风险等级到本地", custId);
		customerInfo.setPiRiskLevel(riskLevel);
		customerInfo.setUpdateTime(new Date());
		customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

//		CustomerHldTradingAccount hldTradingAccount = customerHldTradingAccountMapper.selectByCustId(custId);
//		if (hldTradingAccount == null) {
//			return R.success(ResultCode.SUCCESS);
//		}
//		OpenAccountDTO openAccountDTO = new OpenAccountDTO();
//		openAccountDTO.setCustId(custId);
//		openAccountDTO.setRiskLevel(riskLevel);
//		this.hldOpenAccount(openAccountDTO);
//		log.info("用户{}活利得风险等级更新成功", custId);
//
//		CustomerHldTradingAccount tradingAccount = new CustomerHldTradingAccount();
//		tradingAccount.setId(hldTradingAccount.getId());
//		tradingAccount.setRiskLevel(riskLevel);
//		tradingAccount.setUpdateTime(new Date());
//		customerHldTradingAccountMapper.updateByPrimaryKeySelective(tradingAccount);
//
//		this.bondOpenAccount(openAccountDTO);
//		log.info("用户{}债券易风险等级更新成功", custId);
//		CustomerBondTradingAccount bondTradingAccount = customerBondTradingAccountMapper.selectByCustId(custId);
//		if (bondTradingAccount == null) {
//			return R.fail("更新失败，未开通债券易账户！");
//		}
//		CustomerBondTradingAccount bondAccount = new CustomerBondTradingAccount();
//		bondAccount.setId(bondTradingAccount.getId());
//		bondAccount.setRiskLevel(riskLevel);
//		bondAccount.setUpdateTime(new Date());
//		customerBondTradingAccountMapper.updateByPrimaryKeySelective(bondAccount);
		return R.success(ResultCode.SUCCESS);
	}

	@Override
	public R archiveUserAssets() {
		List<CustomerFinancingAccountEntity> financingAccountList = customerFinancingAccountMapper.selectAllAccount();
		if (CollectionUtil.isEmpty(financingAccountList)) {
			log.info("还没有理财账户，不统计用户资产");
			return R.success();
		}
		String date = DateUtil.formatDate(new Date());
		for (CustomerFinancingAccountEntity account : financingAccountList) {
			try {
				Long custId = account.getCustId();
				String accountId = account.getAccountId();
				//现金
				CustomerCashAssetsHistory cashAssetsHistory = new CustomerCashAssetsHistory(custId, accountId, date);
				try {
					statisticsCashAssets(cashAssetsHistory);
					customerCashAssetsHistoryMapper.insertSelective(cashAssetsHistory);
				} catch (Exception e) {
					log.error("账号{}统计现金资产出错：{}", accountId, e.getMessage());
				}
				//查询市值算
				R result = iAccountClient.selectMarketValue(accountId);
				if (!result.isSuccess()) {
					log.error("账号{}统计资产查询持仓市值出错：{}", accountId, result.getMsg());
					continue;
				}
				JSONObject marketValue = (JSONObject) result.getData();
				log.info("账号{}市值数据：{}", accountId, marketValue);
				//基金
				JSONObject fund = marketValue.getJSONObject("fund");
				CustomerFundAssetsHistory fundAssetsHistory = new CustomerFundAssetsHistory(custId, accountId, date);
				try {
					//基金总市值计算
					statisticsFundAssets(fund, fundAssetsHistory);
					customerFundAssetsHistoryMapper.insertSelective(fundAssetsHistory);
				} catch (Exception e) {
					log.error("账号{}统计基金资产出错：{}", accountId, e.getMessage());
				}
				//活利得
				JSONObject hld = marketValue.getJSONObject("hld");
				CustomerHldAssetsHistory hldAssetsHistory = new CustomerHldAssetsHistory(custId, accountId, date);
				try {
					//活利得总市值计算
					statisticsHldAssets(hld, hldAssetsHistory);
					customerHldAssetsHistoryMapper.insertSelective(hldAssetsHistory);
				} catch (Exception e) {
					log.error("账号{}统计活利得资产出错：{}", accountId, e.getMessage());
				}
				//债券易
				JSONObject bond = marketValue.getJSONObject("bond");
				CustomerBondAssetsHistory bondAssetsHistory = new CustomerBondAssetsHistory(custId, accountId, date);
				try {
					//债券易总市值计算
					statisticsBondAssets(bond, bondAssetsHistory);
					customerBondAssetsHistoryMapper.insertSelective(bondAssetsHistory);
				} catch (Exception e) {
					log.error("账号{}统计债券易资产出错：{}", accountId, e.getMessage());
				}
				try {
					statisticsPositionIncome(accountId, hldAssetsHistory, bondAssetsHistory);
				} catch (Exception e) {
					log.error("账号{}统计持仓收益出错：{}", accountId, e.getMessage());
				}
				//总计
				try {
					CustomerTotalAssetsHistory totalAssetsHistory =
						statisticsTotalAssets(cashAssetsHistory, fundAssetsHistory, hldAssetsHistory, bondAssetsHistory);
					totalAssetsHistory.setCustId(custId);
					totalAssetsHistory.setAccountId(accountId);
					totalAssetsHistory.setStatisticalTime(date);
					totalAssetsHistory.setCreateTime(new Date());
					customerTotalAssetsHistoryMapper.insertSelective(totalAssetsHistory);
				} catch (Exception e) {
					log.error("账号{}资产总表出错：{}", accountId, e.getMessage());
				}
				log.info("=========================================分割线===========================================");
			} catch (Exception e) {
				log.error("账号{}统计资产出错：{}", account.getAccountId(), e.getMessage());
			} finally {
				exchangeRateFactory.cleanCache();
			}
		}
		return R.success();
	}

	@Override
	public R accountAmount(String accountId, String currency) {
		if (StringUtils.isEmpty(accountId) || StringUtils.isEmpty(currency)) {
			return R.fail(ResultCode.PARAM_MISS);
		}
		return R.data(financingAccountAmountMapper.selectByAccountAndCurrency(accountId, currency));
	}

	@Override
	public CustomerFinancingAccountEntity selectAccountByCustId(Long custId) {
		return customerFinancingAccountMapper.selectByCustId(custId);
	}

	@Override
	public R updateOpenAccountInfo(CustomerOpenAccountInfoDTO customerOpenAccount) {
		Long custId = customerOpenAccount.getCustId();
		if (custId == null) {
			throw new ZeroException(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_UPDATE_CUSTOMER_INFO_FAIL_CUST_ID_NULL_NOTICE));
		}

		CustomerBasicInfoEntity basicInfoEntity = customerBasicInfoMapper.selectByCustId(custId);
		if (basicInfoEntity == null) {
			throw new ZeroException(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_UPDATE_CUSTOMER_INFO_FAIL_NOT_EXIST_NOTICE));
		}

		CustomerBasicInfoEntity updateInfo = new CustomerBasicInfoDTO();
		BeanUtils.copyProperties(customerOpenAccount, updateInfo);
		updateInfo.setId(basicInfoEntity.getId());
		customerBasicInfoMapper.updateByPrimaryKeySelective(updateInfo);
		return R.success();
	}

	@Override
	public R<CustomerAccountDetailVO> selectCustomerDetailByAccountId(String accountId) {
		CustomerFinancingAccountEntity customerFinancingAccountEntity = customerFinancingAccountMapper.selectByAccountId(accountId);
		CustomerAccountDetailVO customerAccountDetailVO = new CustomerAccountDetailVO();

		if (customerFinancingAccountEntity != null) {
			CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoMapper.selectByCustId(customerFinancingAccountEntity.getCustId());
			if (customerBasicInfoEntity == null) {
				return R.fail(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_NOT_FIND_CUSTOMER_INFO_NOTICE));
			}
			BeanUtils.copyProperties(customerBasicInfoEntity, customerAccountDetailVO);
			customerAccountDetailVO.setGivenNameSpell(customerBasicInfoEntity.getGivenNameSpell() + " " + customerBasicInfoEntity.getFamilyNameSpell());
			customerAccountDetailVO.setAccountType(customerFinancingAccountEntity.getAccountType());
			return R.data(customerAccountDetailVO);
		}
		return R.fail(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_NOT_FIND_CUSTOMER_INFO_NOTICE));
	}

	@Override
	public R<CustomerAccountDetailVO> selectCustomerDetailByCustId(Long custId) {
		if (custId == null) {
			throw new ZeroException(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_SELECT_CUSTOMER_INFO_FAIL_CUST_ID_NULL_NOTICE));
		}
		CustomerInfoEntity customerInfo = customerInfoMapper.selectById(custId);
		if (customerInfo == null) {
			throw new ZeroException(I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_SELECT_CUSTOMER_INFO_FAIL_NOT_EXIST_NOTICE));
		}
		CustomerAccountDetailVO accountDetail = new CustomerAccountDetailVO();
		accountDetail.setCustId(custId);
		accountDetail.setTenantId(customerInfo.getTenantId());

		CustomerBasicInfoEntity customerBasicInfo = customerBasicInfoMapper.selectByCustId(custId);
		if (customerBasicInfo != null) {
			accountDetail.setEmail(customerBasicInfo.getEmail());
			accountDetail.setClientName(customerBasicInfo.getClientName());
			accountDetail.setPhoneArea(customerBasicInfo.getPhoneArea());
			accountDetail.setPhoneNumber(customerBasicInfo.getPhoneNumber());
			accountDetail.setGivenNameSpell(customerBasicInfo.getGivenNameSpell() + " " + customerBasicInfo.getFamilyNameSpell());
		}
		CustomerFinancingAccountEntity customerFinancingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (customerFinancingAccount != null && customerFinancingAccount.getStatus() != FinancingAccountStatus.NOT_ACTIVE.getCode()) {
			accountDetail.setAccountId(customerFinancingAccount.getAccountId());
			accountDetail.setAccountType(customerFinancingAccount.getAccountType());
		}
		return R.data(accountDetail);
	}

	@Override
	public R tenantCustomerRegister(CustomerInfoDTO customerInfoDTO) {
		CustomerInfoVO customerInfo = this.defaultTenantRegister(customerInfoDTO.getCellPhone(),
			customerInfoDTO.getAreaCode(), customerInfoDTO.getTenantId(), null);
		return data(customerInfo);
	}

	private CustomerInfoVO defaultTenantRegister(String phone, String areaCode, String tenantId, Long tenantCustId) {

		//区号加手机号一起校验
		boolean flag = ValidateUtil.validatePhone(areaCode, phone);
		if (!flag) {
			throw new BusinessException("请输入正确手机号");
		}
		CustomerInfoEntity customerInfoEntity = customerInfoMapper.loadCustomerByUsername(phone, areaCode, tenantId);
		if (customerInfoEntity != null) {
			throw new BusinessException("手机号已存在");
		}
		CustomerInfoEntity entity = new CustomerInfoEntity();
		entity.setCellPhone(phone);
		entity.setAreaCode(areaCode);
		entity.setCreateTime(new Date());
		entity.setTenantId(tenantId);
		entity.setNickName(IdGenerateUtils.getNickName() + phone.substring(phone.length() - 4));
		entity.setCustType(CustEnums.CustType.GENERAL.getCode());
		entity.setStatus(CustomerStatus.NORMAL.getCode());
		entity.setIsDeleted(0);
		customerInfoMapper.insertSelective(entity);

		CustomerInfoEntity newCustomer = customerInfoMapper.loadCustomerByUsername(phone, areaCode, tenantId);
		CustomerInfoVO customerInfoVO = new CustomerInfoVO();
		BeanUtils.copyProperties(newCustomer, customerInfoVO);
		return customerInfoVO;
	}

	@Override
	public R<List<CustomerAssetDetailVO>> selectCustomerDetailByTime(Date startTime, Date endTime) {
		ArrayList<CustomerAssetDetailVO> arrayList = new ArrayList<>();
		if (startTime != null && endTime != null) {
			List<CustomerBasicInfoEntity> list = customerBasicInfoMapper.selectList(
				new QueryWrapper<CustomerBasicInfoEntity>().ge("create_time", startTime)
					.le("create_time", endTime)
			);
			for (CustomerBasicInfoEntity customerBasicInfoEntity : list) {
				CustomerAssetDetailVO accountDetail = new CustomerAssetDetailVO();
				accountDetail.setCustId(customerBasicInfoEntity.getCustId());
				accountDetail.setCreateTime(customerBasicInfoEntity.getCreateTime());
				accountDetail.setTenantId(customerBasicInfoEntity.getTenantId());
				accountDetail.setEmail(customerBasicInfoEntity.getEmail());
				accountDetail.setClientName(customerBasicInfoEntity.getClientName());
				accountDetail.setPhoneArea(customerBasicInfoEntity.getPhoneArea());
				accountDetail.setPhoneNumber(customerBasicInfoEntity.getPhoneNumber());
				accountDetail.setGivenNameSpell(customerBasicInfoEntity.getGivenNameSpell() + " " + customerBasicInfoEntity.getFamilyNameSpell());
				CustomerFinancingAccountEntity customerFinancingAccount = customerFinancingAccountMapper.selectByCustId(customerBasicInfoEntity.getCustId());
				if (customerFinancingAccount != null && customerFinancingAccount.getStatus() != FinancingAccountStatus.NOT_ACTIVE.getCode()) {
					accountDetail.setAccountId(customerFinancingAccount.getAccountId());
					accountDetail.setAccountType(customerFinancingAccount.getAccountType());
				}
				//平均资产
				String day = dictBizClient.getValue("broker_risk_balance_rule", "迎新奖励时间条件(天)").getData();
				Date custEndTime = cn.hutool.core.date.DateUtil.offsetDay(accountDetail.getCreateTime(), Integer.parseInt(day));
				BigDecimal averageAssets1 = customerFundAssetsHistoryMapper.selSumAssets(accountDetail.getCustId(), accountDetail.getCreateTime(), custEndTime) == null ?
					BigDecimal.ZERO : customerFundAssetsHistoryMapper.selSumAssets(accountDetail.getCustId(), accountDetail.getCreateTime(), custEndTime);
				averageAssets1 = averageAssets1.divide(new BigDecimal(day), 2, BigDecimal.ROUND_HALF_UP);

				BigDecimal averageAssets2 = customerHldAssetsHistoryMapper.selSumAssets(accountDetail.getCustId(), accountDetail.getCreateTime(), custEndTime) == null ?
					BigDecimal.ZERO : customerHldAssetsHistoryMapper.selSumAssets(accountDetail.getCustId(), accountDetail.getCreateTime(), custEndTime);
				averageAssets2 = averageAssets2.divide(new BigDecimal(day), 2, BigDecimal.ROUND_HALF_UP);
				BigDecimal averageAssets = averageAssets1.add(averageAssets2);
				accountDetail.setAverageAssets(averageAssets);

				arrayList.add(accountDetail);
			}
		}
		return R.data(arrayList);
	}

	@Override
	public R selectProxyCustomerDetail(Long custId) {
		if (custId == null) {
			return R.fail("用户号不能为空");
		}
		ProxyCustomerDetailVO proxyCustomerDetailVO = new ProxyCustomerDetailVO();
		//注册信息
		CustomerInfoEntity customerInfoEntity = customerInfoMapper.selectByCustId(custId);
		if (customerInfoEntity == null) {
			return data(proxyCustomerDetailVO);
		}

		proxyCustomerDetailVO.setCustomerInfoEntity(customerInfoEntity);

		//开户信息
		CustomerBasicInfoEntity basicInfoEntity = customerBasicInfoMapper.selectByCustId(custId);
		if (basicInfoEntity != null) {
			proxyCustomerDetailVO.setCustomerBasicInfoEntity(basicInfoEntity);
		}
		//实名信息
		CustomerRealnameVerifyEntity realnameVerify = customerRealnameVerifyMapper.selectByCustId(custId);
		if (realnameVerify != null) {
			proxyCustomerDetailVO.setCustomerRealnameVerifyEntity(realnameVerify);
		}
		//统一账号
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);

		proxyCustomerDetailVO.setCustomerFinancingAccountEntity(financingAccount);

		List<CustomerTradeAccountEntity> tradeList=customerTradeAccountService.selectTradeByAccount(financingAccount.getAccountId());

		List<CustomerTradeSubAccountEntity> subAccounts = customerTradeSubAccountService.getSubAccountsByAccountId(financingAccount.getAccountId());

		proxyCustomerDetailVO.setCustomerTradeAccountEntityList(tradeList);
		proxyCustomerDetailVO.setCustomerTradeSubAccountEntityList(subAccounts);

		CustomerStockInfo customerStockInfo =selectStockAccount(financingAccount.getAccountId());
		proxyCustomerDetailVO.setCustomerStockInfo(customerStockInfo);
		return data(proxyCustomerDetailVO);
	}

	private void statisticsPositionIncome(String account, CustomerHldAssetsHistory hldAssetsHistory,
										  CustomerBondAssetsHistory bondAssetsHistory) {
		R incomeHkdResult = iAccountClient.accountAssetAll(account, CurrencyType.HKD.getCode());
		if (!incomeHkdResult.isSuccess()) {
			log.error("账号{}查询持仓收益港币出错：{}", account, incomeHkdResult.getMsg());
		}

		R incomeUsdResult = iAccountClient.accountAssetAll(account, CurrencyType.USD.getCode());
		if (!incomeUsdResult.isSuccess()) {
			log.error("账号{}查询持仓收益美元出错：{}", account, incomeUsdResult.getMsg());
		}

		R incomeCnyResult = iAccountClient.accountAssetAll(account, CurrencyType.CNY.getCode());
		if (!incomeCnyResult.isSuccess()) {
			log.error("账号{}查询持仓收益人民币出错：{}", account, incomeCnyResult.getMsg());
		}
		JSONObject hdkDate = (JSONObject) incomeHkdResult.getData();
		JSONObject usdDate = (JSONObject) incomeUsdResult.getData();
		JSONObject cnyDate = (JSONObject) incomeCnyResult.getData();

		hldAssetsHistory.setTargetCnyIncome(getTotalGainLoss(hdkDate.getJSONObject("hld")));
		hldAssetsHistory.setTargetHkdIncome(getTotalGainLoss(usdDate.getJSONObject("hld")));
		hldAssetsHistory.setTargetUsdIncome(getTotalGainLoss(cnyDate.getJSONObject("hld")));

		bondAssetsHistory.setTargetCnyIncome(getTotalGainLoss(hdkDate.getJSONObject("bond")));
		bondAssetsHistory.setTargetHkdIncome(getTotalGainLoss(usdDate.getJSONObject("bond")));
		bondAssetsHistory.setTargetUsdIncome(getTotalGainLoss(cnyDate.getJSONObject("bond")));
	}

	private CustomerTotalAssetsHistory statisticsTotalAssets(CustomerCashAssetsHistory cashAssetsHistory,
															 CustomerFundAssetsHistory fundAssetsHistory,
															 CustomerHldAssetsHistory hldAssetsHistory,
															 CustomerBondAssetsHistory bondAssetsHistory) {
		CustomerTotalAssetsHistory totalAssetsHistory = new CustomerTotalAssetsHistory();

		totalAssetsHistory.setCnyCashAssets(cashAssetsHistory.getTargetCnyAssets());
		totalAssetsHistory.setHkdCashAssets(cashAssetsHistory.getTargetHkdAssets());
		totalAssetsHistory.setUsdCashAssets(cashAssetsHistory.getTargetUsdAssets());

		BigDecimal cnyPositionAssets = fundAssetsHistory.getTargetCnyMarketValue()
			.add(hldAssetsHistory.getTargetCnyMarketValue())
			.add(bondAssetsHistory.getTargetCnyMarketValue());
		totalAssetsHistory.setCnyPositionAssets(cnyPositionAssets);

		BigDecimal hkdPositionAssets = fundAssetsHistory.getTargetHkdMarketValue()
			.add(hldAssetsHistory.getTargetHkdMarketValue())
			.add(bondAssetsHistory.getTargetHkdMarketValue());
		totalAssetsHistory.setHkdPositionAssets(hkdPositionAssets);

		BigDecimal usdPositionAssets = fundAssetsHistory.getTargetUsdMarketValue()
			.add(hldAssetsHistory.getTargetUsdMarketValue())
			.add(bondAssetsHistory.getTargetUsdMarketValue());
		totalAssetsHistory.setUsdPositionAssets(usdPositionAssets);

		BigDecimal cnyIncomeAssets = fundAssetsHistory.getTargetCnyIncome()
			.add(hldAssetsHistory.getTargetCnyIncome())
			.add(bondAssetsHistory.getTargetCnyIncome());
		totalAssetsHistory.setCnyIncomeAssets(cnyIncomeAssets);

		BigDecimal usdIncomeAssets = fundAssetsHistory.getTargetUsdIncome()
			.add(hldAssetsHistory.getTargetUsdIncome())
			.add(bondAssetsHistory.getTargetUsdIncome());
		totalAssetsHistory.setUsdIncomeAssets(usdIncomeAssets);
		BigDecimal hkdIncomeAssets = fundAssetsHistory.getTargetHkdIncome()
			.add(hldAssetsHistory.getTargetHkdIncome())
			.add(bondAssetsHistory.getTargetHkdIncome());
		totalAssetsHistory.setHkdIncomeAssets(hkdIncomeAssets);

		BigDecimal hkdAssets = cashAssetsHistory.getTargetHkdAssets().add(hkdIncomeAssets).add(hkdPositionAssets);
		BigDecimal cnyAssets = cashAssetsHistory.getTargetCnyAssets().add(cnyPositionAssets).add(cnyIncomeAssets);
		BigDecimal usdAssets = cashAssetsHistory.getTargetUsdAssets().add(usdPositionAssets).add(usdIncomeAssets);

		totalAssetsHistory.setHkdAssets(hkdAssets);
		totalAssetsHistory.setCnyAssets(cnyAssets);
		totalAssetsHistory.setUsdAssets(usdAssets);
		return totalAssetsHistory;
	}

	/**
	 * 计算现金资产
	 *
	 * @return
	 */
	private CustomerCashAssetsHistory statisticsCashAssets(CustomerCashAssetsHistory cashAssetsHistory) throws Exception {
		List<FinancingAccountAmount> accountAmountList = financingAccountAmountMapper.selectByAccountId(cashAssetsHistory.getAccountId(), null);
		if (CollectionUtil.isEmpty(accountAmountList)) {
			return cashAssetsHistory;
		}
		BigDecimal hkdAvailableAmount = BigDecimal.ZERO;
		BigDecimal usdAvailableAmount = BigDecimal.ZERO;
		BigDecimal cnyAvailableAmount = BigDecimal.ZERO;

		Optional<FinancingAccountAmount> hkdAccountAmount =
			accountAmountList.stream().filter(accountAmount -> accountAmount.getCurrency().equals(CurrencyType.HKD.getCode())).findFirst();
		if (hkdAccountAmount.isPresent() && hkdAccountAmount.get().getAvailableAmount() != null) {
			hkdAvailableAmount = hkdAccountAmount.get().getAvailableAmount()
				.add(hkdAccountAmount.get().getFreezeAmount()).add(hkdAccountAmount.get().getTransitedAmount());

			cashAssetsHistory.setHkdFreeze(hkdAccountAmount.get().getFreezeAmount());
			cashAssetsHistory.setHkdAvailable(hkdAccountAmount.get().getAvailableAmount());
			cashAssetsHistory.setHkdIntransit(hkdAccountAmount.get().getTransitedAmount());
		}
		Optional<FinancingAccountAmount> cnyAccountAmount =
			accountAmountList.stream().filter(accountAmount -> accountAmount.getCurrency().equals(CurrencyType.CNY.getCode())).findFirst();
		if (cnyAccountAmount.isPresent() && cnyAccountAmount.get().getAvailableAmount() != null) {
			cnyAvailableAmount = cnyAccountAmount.get().getAvailableAmount()
				.add(cnyAccountAmount.get().getFreezeAmount()).add(cnyAccountAmount.get().getTransitedAmount());

			cashAssetsHistory.setCnyFreeze(cnyAccountAmount.get().getFreezeAmount());
			cashAssetsHistory.setCnyAvailable(cnyAccountAmount.get().getAvailableAmount());
			cashAssetsHistory.setCnyIntransit(cnyAccountAmount.get().getTransitedAmount());
		}
		Optional<FinancingAccountAmount> usdAccountAmount =
			accountAmountList.stream().filter(accountAmount -> accountAmount.getCurrency().equals(CurrencyType.USD.getCode())).findFirst();
		if (usdAccountAmount.isPresent() && usdAccountAmount.get().getAvailableAmount() != null) {
			usdAvailableAmount = usdAccountAmount.get().getAvailableAmount()
				.add(usdAccountAmount.get().getFreezeAmount()).add(usdAccountAmount.get().getTransitedAmount());

			cashAssetsHistory.setUsdFreeze(usdAccountAmount.get().getFreezeAmount());
			cashAssetsHistory.setUsdAvailable(usdAccountAmount.get().getAvailableAmount());
			cashAssetsHistory.setUsdIntransit(usdAccountAmount.get().getTransitedAmount());
		}

		String targetCurrency = CurrencyType.HKD.getCode();
		BigDecimal CNY_HKD = exchangeRateFactory.exchange(targetCurrency, CurrencyType.CNY.getCode(), cnyAvailableAmount);
		BigDecimal USD_HKD = exchangeRateFactory.exchange(targetCurrency, CurrencyType.USD.getCode(), usdAvailableAmount);
		BigDecimal targetHKD = hkdAvailableAmount.add(CNY_HKD).add(USD_HKD);

		targetCurrency = CurrencyType.CNY.getCode();
		BigDecimal HKD_CNY = exchangeRateFactory.exchange(targetCurrency, CurrencyType.HKD.getCode(), hkdAvailableAmount);
		BigDecimal USD_CNY = exchangeRateFactory.exchange(targetCurrency, CurrencyType.USD.getCode(), usdAvailableAmount);
		BigDecimal targetCNY = cnyAvailableAmount.add(HKD_CNY).add(USD_CNY);

		targetCurrency = CurrencyType.USD.getCode();
		BigDecimal CNY_USD = exchangeRateFactory.exchange(targetCurrency, CurrencyType.CNY.getCode(), cnyAvailableAmount);
		BigDecimal HKD_USD = exchangeRateFactory.exchange(targetCurrency, CurrencyType.HKD.getCode(), hkdAvailableAmount);
		BigDecimal targetUSD = usdAvailableAmount.add(HKD_USD).add(CNY_USD);
		//冻结部分
//		R result = iAccountClient.accountCapital(cashAssetsHistory.getAccountId());
//		if (!result.isSuccess()){
//			throw new BusinessException(result.getMsg());
//		}
//		JSONObject data = (JSONObject) result.getData();
//		BigDecimal forzenHKD = this.getForzenValue(data,CurrencyType.HKD.getCode());
//		BigDecimal forzenCNY = this.getForzenValue(data,CurrencyType.CNY.getCode());
//		BigDecimal forzenUSD = this.getForzenValue(data,CurrencyType.USD.getCode());

//		BigDecimal targetForzenHKD = getTargetCurrencyForzenAmount(CurrencyType.HKD.getCode(),data);
//		BigDecimal targetForzenCNY = getTargetCurrencyForzenAmount(CurrencyType.CNY.getCode(),data);
//		BigDecimal targetForzenUSD = getTargetCurrencyForzenAmount(CurrencyType.USD.getCode(),data);

		cashAssetsHistory.setCreateTime(new Date());
		cashAssetsHistory.setCnyAssets(cnyAvailableAmount);
		cashAssetsHistory.setHkdAssets(hkdAvailableAmount);
		cashAssetsHistory.setUsdAssets(usdAvailableAmount);
		cashAssetsHistory.setTargetCnyAssets(targetCNY);
		cashAssetsHistory.setTargetHkdAssets(targetHKD);
		cashAssetsHistory.setTargetUsdAssets(targetUSD);
		log.info("现金资产统计：{}", JSONObject.toJSONString(cashAssetsHistory));
		return cashAssetsHistory;
	}

	/**
	 * 基金资产
	 *
	 * @param fund
	 * @param fundAssetsHistory
	 * @return
	 * @throws Exception
	 */
	private CustomerFundAssetsHistory statisticsFundAssets(JSONObject fund, CustomerFundAssetsHistory fundAssetsHistory) throws Exception {
		BigDecimal fundUSD = BigDecimal.ZERO;
		BigDecimal fundHKD = BigDecimal.ZERO;
		BigDecimal fundCNY = BigDecimal.ZERO;

		BigDecimal targetUSD = BigDecimal.ZERO;
		BigDecimal targetHKD = BigDecimal.ZERO;
		BigDecimal targetCNY = BigDecimal.ZERO;

		if (fund != null) {
			fundUSD = fund.getBigDecimal(CurrencyType.USD.getCode());
			fundHKD = fund.getBigDecimal(CurrencyType.HKD.getCode());
			fundCNY = fund.getBigDecimal(CurrencyType.CNY.getCode());

			String targetCurrency = CurrencyType.USD.getCode();
			targetUSD = getTargetCurrencyAmount(targetCurrency, fund);

			targetCurrency = CurrencyType.HKD.getCode();
			targetHKD = getTargetCurrencyAmount(targetCurrency, fund);

			targetCurrency = CurrencyType.CNY.getCode();
			targetCNY = getTargetCurrencyAmount(targetCurrency, fund);
		}
		fundAssetsHistory.setCnyMarketValue(fundCNY);
		fundAssetsHistory.setHkdMarketValue(fundHKD);
		fundAssetsHistory.setUsdMarketValue(fundUSD);
		fundAssetsHistory.setTargetCnyMarketValue(targetCNY);
		fundAssetsHistory.setTargetHkdMarketValue(targetHKD);
		fundAssetsHistory.setTargetUsdMarketValue(targetUSD);
		log.info("基金资产统计：{}", JSONObject.toJSONString(fundAssetsHistory));
		return fundAssetsHistory;
	}

	private BigDecimal getValue(JSONObject hld, String currency) {
		BigDecimal obj = hld.getBigDecimal(currency);
		if (obj == null) {
			return BigDecimal.ZERO;
		}
		return obj;
	}

	/**
	 * 活利得资产
	 *
	 * @param hld
	 * @param hlddAssetsHistory
	 * @return
	 * @throws Exception
	 */
	private CustomerHldAssetsHistory statisticsHldAssets(JSONObject hld, CustomerHldAssetsHistory hlddAssetsHistory) throws Exception {
		BigDecimal hldUSD = BigDecimal.ZERO;
		BigDecimal hldHKD = BigDecimal.ZERO;
		BigDecimal hldCNY = BigDecimal.ZERO;

		BigDecimal targetUSD = BigDecimal.ZERO;
		BigDecimal targetHKD = BigDecimal.ZERO;
		BigDecimal targetCNY = BigDecimal.ZERO;
		if (hld != null && !hld.isEmpty()) {
			hldUSD = this.getValue(hld, CurrencyType.USD.getCode());
			hldHKD = this.getValue(hld, CurrencyType.HKD.getCode());
			hldCNY = this.getValue(hld, CurrencyType.CNY.getCode());

			String targetCurrency = CurrencyType.USD.getCode();
			targetUSD = getTargetCurrencyAmount(targetCurrency, hld);

			targetCurrency = CurrencyType.HKD.getCode();
			targetHKD = getTargetCurrencyAmount(targetCurrency, hld);

			targetCurrency = CurrencyType.CNY.getCode();
			targetCNY = getTargetCurrencyAmount(targetCurrency, hld);
		}
		hlddAssetsHistory.setCnyMarketValue(hldCNY);
		hlddAssetsHistory.setHkdMarketValue(hldHKD);
		hlddAssetsHistory.setUsdMarketValue(hldUSD);
		hlddAssetsHistory.setTargetCnyMarketValue(targetCNY);
		hlddAssetsHistory.setTargetHkdMarketValue(targetHKD);
		hlddAssetsHistory.setTargetUsdMarketValue(targetUSD);
		log.info("活利得产统计：{}", JSONObject.toJSONString(hlddAssetsHistory));
		return hlddAssetsHistory;
	}

	/**
	 * 债券易资产
	 *
	 * @param bond
	 * @param bondAssetsHistory
	 * @return
	 * @throws Exception
	 */
	private CustomerBondAssetsHistory statisticsBondAssets(JSONObject bond, CustomerBondAssetsHistory bondAssetsHistory) throws Exception {

		BigDecimal bondUSD = BigDecimal.ZERO;
		BigDecimal bondHKD = BigDecimal.ZERO;
		BigDecimal bondCNY = BigDecimal.ZERO;

		BigDecimal targetUSD = BigDecimal.ZERO;
		BigDecimal targetHKD = BigDecimal.ZERO;
		BigDecimal targetCNY = BigDecimal.ZERO;

		if (bond != null && !bond.isEmpty()) {
			bondUSD = this.getValue(bond, CurrencyType.USD.getCode());
			bondHKD = this.getValue(bond, CurrencyType.HKD.getCode());
			bondCNY = this.getValue(bond, CurrencyType.CNY.getCode());

			String targetCurrency = CurrencyType.USD.getCode();
			targetUSD = getTargetCurrencyAmount(targetCurrency, bond);

			targetCurrency = CurrencyType.HKD.getCode();
			targetHKD = getTargetCurrencyAmount(targetCurrency, bond);

			targetCurrency = CurrencyType.CNY.getCode();
			targetCNY = getTargetCurrencyAmount(targetCurrency, bond);
		}
		bondAssetsHistory.setCnyMarketValue(bondCNY);
		bondAssetsHistory.setHkdMarketValue(bondHKD);
		bondAssetsHistory.setUsdMarketValue(bondUSD);
		bondAssetsHistory.setTargetCnyMarketValue(targetCNY);
		bondAssetsHistory.setTargetHkdMarketValue(targetHKD);
		bondAssetsHistory.setTargetUsdMarketValue(targetUSD);
		log.info("债券易产统计：{}", JSONObject.toJSONString(bondAssetsHistory));
		return bondAssetsHistory;
	}

	private BigDecimal getTargetCurrencyAmount(String currency, JSONObject income) {
		BigDecimal targetHKD = BigDecimal.ZERO;
		BigDecimal targetCNY = BigDecimal.ZERO;
		BigDecimal targetUSD = BigDecimal.ZERO;
		if (income != null && !income.isEmpty()) {
			BigDecimal USD = this.getValue(income, CurrencyType.USD.getCode());
			BigDecimal HKD = this.getValue(income, CurrencyType.HKD.getCode());
			BigDecimal CNY = this.getValue(income, CurrencyType.CNY.getCode());

			targetHKD = exchangeRateFactory.exchange(currency, CurrencyType.HKD.getCode(), HKD);
			targetCNY = exchangeRateFactory.exchange(currency, CurrencyType.CNY.getCode(), CNY);
			targetUSD = exchangeRateFactory.exchange(currency, CurrencyType.USD.getCode(), USD);
		}
		return targetUSD.add(targetCNY).add(targetHKD).setScale(4, RoundingMode.HALF_UP);
	}

	private BigDecimal getTargetCurrencyForzenAmount(String currency, JSONObject income) {
		BigDecimal targetHKD = BigDecimal.ZERO;
		BigDecimal targetCNY = BigDecimal.ZERO;
		BigDecimal targetUSD = BigDecimal.ZERO;
		if (income != null && !income.isEmpty()) {
			BigDecimal USD = this.getForzenValue(income, CurrencyType.USD.getCode());
			BigDecimal HKD = this.getForzenValue(income, CurrencyType.HKD.getCode());
			BigDecimal CNY = this.getForzenValue(income, CurrencyType.CNY.getCode());

			targetHKD = exchangeRateFactory.exchange(currency, CurrencyType.HKD.getCode(), HKD);
			targetCNY = exchangeRateFactory.exchange(currency, CurrencyType.CNY.getCode(), CNY);
			targetUSD = exchangeRateFactory.exchange(currency, CurrencyType.USD.getCode(), USD);
		}
		return targetUSD.add(targetCNY).add(targetHKD);
	}

	private BigDecimal getForzenValue(JSONObject obj, String currency) {
		JSONObject data = obj.getJSONObject(currency);
		if (data == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal forzeValue = data.getBigDecimal("frozen");
		if (forzeValue == null) {
			return BigDecimal.ZERO;
		}
		return forzeValue;
	}

	@Override
	public R acctCheckPwd(String tradeAccount, String password) {

		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByAccountId(tradeAccount);
		if (financingAccount == null) {
			throw new BusinessException("账号/密码错误");
		}
		String oldPassword = RSANewUtil.decrypt(password);
		Boolean flag = checkOldPassword(oldPassword, financingAccount.getPassword());
		if (!flag) {
			throw new BusinessException("账号/密码错误");
		}
		return R.data(financingAccount.getCustId());
	}

	private boolean checkOldPassword(String rsaPwd, String shaPwd) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		if (bCryptPasswordEncoder.matches(rsaPwd, shaPwd)) {
			//if (true){

			return true;
		}
		return false;
	}

	@Override
	public CustomerStockInfo selectStockAccount(String accountId) {
		CustomerStockInfo customerStockInfo = new CustomerStockInfo();
		customerStockInfo.setSupportType(stockMarket);
		CustomerTradeAccountEntity tradeInfo = customerTradeAccountService.selectTradeByAccountAndType(accountId, BusinessTypeEnums.SEC.getBusinessType());
		if (null != tradeInfo) {
			customerStockInfo.setTradeAccount(tradeInfo.getReletionTradeAccount());
			List<CapitalAccountVO> list = new ArrayList<>();
			CapitalAccountVO capitalAccount = new CapitalAccountVO();
			capitalAccount.setCapitalAccount(tradeInfo.getTradeAccount());
			capitalAccount.setAccountType(tradeInfo.getAccountType());
			list.add(capitalAccount);
			customerStockInfo.setCapitalAccounts(list);

			List<CustomerTradeSubAccountEntity> subAccountEntityList = customerTradeSubAccountService.selecctSubAccountByTradeId(tradeInfo.getId());
			for (CustomerTradeSubAccountEntity subAccountEntity : subAccountEntityList) {
				if (MarketTypeEnums.ML.getMarketType().equals(subAccountEntity.getMarketType())) {
					customerStockInfo.setCnStatus("1");
				}
				if (MarketTypeEnums.HK.getMarketType().equals(subAccountEntity.getMarketType())) {
					customerStockInfo.setHkStatus("1");
				}
				if (MarketTypeEnums.US.getMarketType().equals(subAccountEntity.getMarketType())) {
					customerStockInfo.setUsStatus("1");
				}
			}
		}
		return customerStockInfo;

	}

	@Override
	public CustomerInfoEntity updateCustomerInfo(CustomerInfoDTO customerInfoDTO) {
		CustomerInfoEntity customerInfo = new CustomerInfoEntity();
		BeanUtils.copyProperties(customerInfoDTO, customerInfo);
		return this.customerInfoMapper.updateCustomerInfo(customerInfo);
	}
}
