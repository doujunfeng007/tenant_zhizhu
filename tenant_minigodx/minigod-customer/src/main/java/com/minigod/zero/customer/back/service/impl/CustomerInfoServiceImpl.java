/*
 *  Copyright (c) 2018-2028, MiniGod All rights reserved.
 */
package com.minigod.zero.customer.back.service.impl;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.common.exceptions.BusinessException;
import com.minigod.common.exceptions.ResultCode;
import com.minigod.zero.core.http.HttpRequest;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.customer.back.client.BpmnProxyClient;
import com.minigod.zero.customer.back.service.ICustomerInfoService;
import com.minigod.zero.customer.dto.CustRiskInfoDTO;
import com.minigod.zero.customer.dto.OpenAccountLogQueryDTO;
import com.minigod.zero.customer.dto.StatementListDTO;
import com.minigod.zero.customer.dto.SubAccountDTO;
import com.minigod.zero.customer.emuns.CustomerStatus;
import com.minigod.zero.customer.entity.*;
import com.minigod.zero.customer.enums.*;
import com.minigod.zero.customer.factory.ExchangeRateFactory;
import com.minigod.zero.customer.mapper.*;
import com.minigod.zero.customer.utils.IdGenerateUtils;
import com.minigod.zero.customer.vo.*;
import com.minigod.zero.platform.enums.EmailTemplate;
import com.minigod.zero.platform.feign.IAccountClient;
import com.minigod.zero.platform.utils.EmailUtil;
import com.minigod.zero.platform.vo.PageVO;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 客户信息表 服务实现类
 *
 * @author ken
 * @since 2024-04-11
 */
@Slf4j
@Service
public class CustomerInfoServiceImpl extends BaseServiceImpl<CustomerInfoMapper, CustomerInfoEntity> implements ICustomerInfoService {
	@Autowired
	private IAccountClient iAccountClient;
	@Autowired
	private IDictBizClient iDictBizClient;
	@Autowired
	private IdGenerateUtils idGenerateUtils;
	@Autowired
	private BpmnProxyClient accountOpenClient;
	@Autowired
	private CustomerInfoMapper customerInfoMapper;
	@Autowired
	private ExchangeRateFactory exchangeRateFactory;
	@Autowired
	private CustomerIdentityInfoMapper customerPcRoleMapper;
	@Autowired
	private CustomerFundTradingAccountMapper customerFundTradingAccountMapper;

	@Autowired
	private CustomerBasicInfoMapper customerBasicInfoMapper;
	@Autowired
	private CustomerRealnameVerifyMapper customerRealnameVerifyMapper;
	@Autowired
	private FinancingAccountAmountMapper financingAccountAmountMapper;
	@Autowired
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;
	@Autowired
	private CustomerHldTradingAccountMapper customerHldTradingAccountMapper;
	@Autowired
	private CustomerDebentureTradingAccountMapper debentureTradingAccountMapper;
	@Autowired
	private CustomerBondTradingAccountMapper customerBondTradingAccountMapper;
	@Autowired
	private CustomerFinancingSubAccountMapper customerFinancingSubAccountMapper;
	@Autowired
	private FinancingAccountAmountFlowsMapper financingAccountAmountFlowsMapper;

	// PC端角色业务字典
	private final String CUSTOMER_PC_ROLE = "customer_pc_role";

	private final static String SUB_ACCOUNT_LIMIT_QUANTITY = "sub_account_limit_quantity";

	@Override
	public IPage<CustomerInfoVO> selectCustomerInfoPage(IPage<CustomerInfoVO> page, CustomerInfoVO customerInfo) {
		return page.setRecords(baseMapper.selectCustomerInfoPage(page, customerInfo));
	}

	@Override
	public IPage<CustRiskInfoVO> custRiskPage(IPage<CustRiskInfoVO> page, CustRiskInfoDTO custRiskInfoDTO) {
		List<CustRiskInfoVO> custRiskInfoVOList = baseMapper.custRiskList(page, custRiskInfoDTO);
		custRiskInfoVOList.forEach(custRiskInfoVO -> {
			custRiskInfoVO.setBondOpen(custRiskInfoVO.getBondCustId() != null);
			custRiskInfoVO.setFundOpen(custRiskInfoVO.getFundCustId() != null);
			custRiskInfoVO.setHldOpen(custRiskInfoVO.getHldCustId() != null);
		});
		return page.setRecords(custRiskInfoVOList);
	}


	@Override
	public IPage<CustomerOpenAccountVO> openAccountList(IPage<CustomerOpenAccountVO> page, OpenAccountLogQueryDTO queryParams) {
		List<CustomerOpenAccountVO> list = new ArrayList<>();
		//用户
		if (queryParams.getOpenStatus() == null || queryParams.getOpenStatus() == 2) {
			page = this.openAccountUserList(queryParams.getKeyword(), queryParams.getStartTime(), queryParams.getEndTime(), page.getCurrent(), page.getSize());
			list = page.getRecords();
		}
		//游客
		else if (queryParams.getOpenStatus() == 1) {
			list = baseMapper.visitorList(page, queryParams.getKeyword(), queryParams.getStartTime(), queryParams.getEndTime());
			return page.setRecords(list);
		}
		//客户
		else if (queryParams.getOpenStatus() == 3) {
			list = baseMapper.customerList(page, queryParams.getKeyword(),
				queryParams.getRiskLevel(), queryParams.getPiLevel(),
				queryParams.getStartTime(), queryParams.getEndTime());
		} else if (queryParams.getOpenStatus() == 0) {
			//所有客户
			list = baseMapper.allCustomerList(page, queryParams.getKeyword(),
				queryParams.getStartTime(), queryParams.getEndTime());
		}
		if (CollectionUtil.isEmpty(list)) {
			return page.setRecords(list);
		}
		list.stream().forEach(customer -> {
			if (queryParams.getOpenStatus() == 2) {
				this.settingCustomerOpenStatus(customer);
				this.settingCustomerLoginPhone(customer);
			}
			this.settingRoleName(customer);
			this.settingCustomerTradeAccount(customer);
			customer.setRiskLevelName(FundRiskLevel.getByCode(customer.getRiskLevel()).getDesc());
			customer.setStatusName(CustomerStatus.getByCode(customer.getStatus()).getDesc());
		});
		return page.setRecords(list);
	}

	/**
	 * 设置用户角色
	 *
	 * @param customer
	 */
	private void settingRoleName(CustomerOpenAccountVO customer) {
		List<CustomerIdentityInfo> list = customerPcRoleMapper.selectByCustId(customer.getCustId());
		String roleName = "";
		if (!CollectionUtil.isEmpty(list)) {
			roleName = list.stream().map(CustomerIdentityInfo::getRoleName).collect(Collectors.joining(","));
		} else {
			roleName = CustomerRole.ORDINARY.getDesc();
		}
		customer.setRoleName(roleName);
	}

	/**
	 * 设置交易账号
	 *
	 * @param customer
	 */
	private void settingCustomerTradeAccount(CustomerOpenAccountVO customer) {
		CustomerFundTradingAccountEntity fundTradingAccount = customerFundTradingAccountMapper.selectByCustId(customer.getCustId());
		if (fundTradingAccount != null) {
			customer.setFundTradeAccount(fundTradingAccount.getTradeAccount());
		}
		CustomerBondTradingAccount bondTradingAccount = customerBondTradingAccountMapper.selectByCustId(customer.getCustId());
		if (bondTradingAccount != null) {
			customer.setBondTradeAccount(bondTradingAccount.getTradeAccount());
		}
		CustomerHldTradingAccount hldTradingAccount = customerHldTradingAccountMapper.selectByCustId(customer.getCustId());
		if (hldTradingAccount != null) {
			customer.setHldTradeAccount(hldTradingAccount.getTradeAccount());
		}
	}

	/**
	 * 设置用户登录手机号
	 *
	 * @param customer
	 */
	private void settingCustomerLoginPhone(CustomerOpenAccountVO customer) {
		CustomerInfoEntity customerInfo = customerInfoMapper.selectById(customer.getCustId());
		if (customerInfo != null) {
			customer.setStatus(customerInfo.getStatus());
			customer.setPiLevel(customerInfo.getPiLevel());
			customer.setRiskLevel(customerInfo.getRiskLevel());
			customer.setPhone(customerInfo.getAreaCode() + "+" + customerInfo.getCellPhone());
		}
	}

	/**
	 * 设置用户账户状态
	 *
	 * @param customer
	 */
	private void settingCustomerOpenStatus(CustomerOpenAccountVO customer) {
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(customer.getCustId());
		if (financingAccount == null) {
			return;
		}
		Integer accountStatus = financingAccount.getStatus();
		if (financingAccount == null || FinancingAccountStatus.NOT_ACTIVE.getCode().equals(accountStatus)) {
			customer.setOpenStatus("未开户");
			return;
		} else if (FinancingAccountStatus.PRE_APPROVED.getCode().equals(accountStatus)) {
			customer.setOpenStatus("预批户");
		} else if (FinancingAccountStatus.NORMAL.getCode().equals(accountStatus)) {
			customer.setOpenStatus("已开户");
		}
		customer.setAccountId(financingAccount.getAccountId());
	}

	/**
	 * 获取开户用户数据
	 *
	 * @param keyword
	 * @param startTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	private Page openAccountUserList(String keyword, String startTime, String endTime, Long pageIndex, Long pageSize) {
		Integer index = Long.valueOf(pageIndex).intValue();
		Integer size = Long.valueOf(pageSize).intValue();
		R<Page<CustomerOpenAccountVO>> result = accountOpenClient.openAccountUserList(keyword, startTime, endTime, index, size);
		if (!result.isSuccess()) {
			throw new BusinessException(result.getMsg());
		}
		Page page = result.getData();
		return page;
	}


	@Override
	public BackCustomerDetailVO detail(Long custId) {
		if (custId == null) {
			throw new BusinessException("custId 不能为空");
		}
		CustomerInfoEntity customerInfo = customerInfoMapper.selectById(custId);
		if (customerInfo == null) {
			throw new BusinessException("用户信息不存在");
		}
		BackCustomerDetailVO customerDetail = new BackCustomerDetailVO();
		customerDetail.setCustId(customerInfo.getId());
		customerDetail.setPhoneArea(customerInfo.getAreaCode());
		customerDetail.setPhoneNumber(customerInfo.getCellPhone());
		customerDetail.setRegisterTime(DateUtil.format(customerInfo.getCreateTime(), DateUtil.PATTERN_DATETIME));
		customerDetail.setCertificationPi(customerInfo.getPiLevel() == null ? "未认证" : "已认证");
		customerDetail.setUpdateTime(DateUtil.format(customerInfo.getUpdateTime(), DateUtil.PATTERN_DATETIME));
		customerDetail.setStatusName(CustomerStatus.getByCode(customerInfo.getStatus()).getDesc());
		customerDetail.setCustomerTypeName(CustomerType.getByCode(customerInfo.getCustType()).getDesc());
		customerDetail.setRiskLevel(FundRiskLevel.getByCode(customerInfo.getRiskLevel()).getDesc());
		CustomerBasicInfoEntity customerBasicInfo = customerBasicInfoMapper.selectByCustId(custId);
		if (customerBasicInfo != null) {
			customerDetail.setFamilyName(customerBasicInfo.getFamilyName());
			customerDetail.setGivenName(customerBasicInfo.getGivenName());
			customerDetail.setClientNameSpell(customerBasicInfo.getClientNameSpell());
			customerDetail.setCustomerName(customerDetail.getFamilyName() + customerDetail.getGivenName());
			customerDetail.setFamilyNameSpell(customerBasicInfo.getFamilyNameSpell());
			customerDetail.setGivenNameSpell(customerBasicInfo.getGivenNameSpell());
			customerDetail.setCustomerEName(customerDetail.getFamilyNameSpell() + customerDetail.getGivenNameSpell());
			customerDetail.setAddress(customerBasicInfo.getIdCardDetailAddress());
			customerDetail.setOpenAccountTime(DateUtil.formatDate(customerBasicInfo.getCreateTime()));
			customerDetail.setOpenAccountEmail(customerBasicInfo.getEmail());
			customerDetail.setOpenAccountType(OpenAccountAccessWay.getByCode(customerBasicInfo.getOpenAccountAccessWay()).getDesc());
			customerDetail.setAccountType(FundAccountType.getByCode(customerBasicInfo.getFundAccountType()).getDesc());
			customerDetail.setIdKindType(IdKindType.getByIdKind(Integer.valueOf(customerBasicInfo.getIdKind())).getDesc());
			customerDetail.setNationality(customerBasicInfo.getNationality());
			CustomerRealnameVerifyEntity realnameVerify = customerRealnameVerifyMapper.selectByCustId(custId);
			if (realnameVerify != null) {
				customerDetail.setGender(Gender.getByCode(realnameVerify.getGender()).getDesc());
				customerDetail.setIdCardNo(realnameVerify.getIdCard());
			}

		} else {
			R<BackCustomerDetailVO> result = accountOpenClient.openAccountDetail(custId);
			if (result.isSuccess()) {
				BackCustomerDetailVO detail = result.getData();
				customerDetail.setFamilyName(detail.getFamilyName());
				customerDetail.setGivenName(detail.getGivenName());
				customerDetail.setClientNameSpell(detail.getClientNameSpell());
				customerDetail.setCustomerName(detail.getFamilyName() + customerDetail.getGivenName());
				customerDetail.setFamilyNameSpell(detail.getFamilyNameSpell());
				customerDetail.setGivenNameSpell(detail.getGivenNameSpell());
				customerDetail.setCustomerEName(detail.getFamilyNameSpell() + customerDetail.getGivenNameSpell());
				customerDetail.setAddress(detail.getAddress());
				customerDetail.setOpenAccountTime(detail.getOpenAccountTime());
				customerDetail.setOpenAccountEmail(detail.getOpenAccountEmail());
				customerDetail.setOpenAccountType(OpenAccountAccessWay.getByCode(detail.getOpenAccountType()).getDesc());
				customerDetail.setAccountType(FundAccountType.getByCode(detail.getAccountType()).getDesc());
				customerDetail.setIdKindType(IdKindType.getByIdKind(detail.getIdKindType()).getDesc());
				customerDetail.setGender(Gender.getByCode(detail.getGender()).getDesc());
				customerDetail.setIdCardNo(detail.getIdCardNo());
				customerDetail.setNationality(detail.getNationality());
			}
		}
		try {
			R<String> result = iDictBizClient.getValue("nationality", customerDetail.getNationality());
			if (result.isSuccess()) {
				customerDetail.setNationality(result.getData());
			}
		} catch (Exception e) {
			log.error("获取客户国籍出错：{}", e.getMessage());
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount != null) {
			customerDetail.setAccountId(financingAccount.getAccountId());
		}
		return customerDetail;
	}

	@Override
	public List<CashAccountVO> cashAccountInfo(Long custId) {
		if (custId == null) {
			throw new BusinessException("custId 不能为空");
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			return new ArrayList<>();
		}
		List<FinancingAccountAmount> accountAmountList =
			financingAccountAmountMapper.selectByAccountId(financingAccount.getAccountId(), null);
		if (CollectionUtil.isEmpty(accountAmountList)) {
			return new ArrayList<>();
		}

		List<CashAccountVO> cashAccountList = new ArrayList<>();
		accountAmountList.stream().forEach(accountAmount -> {
			CashAccountVO cashAccount = new CashAccountVO();
			cashAccount.setCurrency(CurrencyType.getByCode(accountAmount.getCurrency()).getDesc());
			cashAccount.setAvailableAmount(accountAmount.getAvailableAmount());
			cashAccount.setTransitedAmount(accountAmount.getTransitedAmount());
			cashAccount.setWithdrawalAmount(accountAmount.getAvailableAmount());
			cashAccount.setFreezeAmount(accountAmount.getFreezeAmount());
			cashAccountList.add(cashAccount);
		});
		return cashAccountList;
	}

	@Override
	public BigDecimal cashAccountBalance(Long custId, String currency) {
		if (custId == null) {
			throw new BusinessException("custId 不能为空");
		}
		if (StringUtil.isBlank(currency)) {
			throw new BusinessException("currency 不能为空");
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			return BigDecimal.ZERO;
		}

		List<FinancingAccountAmount> accountAmountList =
			financingAccountAmountMapper.selectByAccountId(financingAccount.getAccountId(), null);
		if (CollectionUtil.isEmpty(accountAmountList)) {
			return BigDecimal.ZERO;
		}
		List<BigDecimal> availableAmountList = new ArrayList<>();
		List<BigDecimal> freezeAmountList = new ArrayList<>();
		List<BigDecimal> transitedAmountList = new ArrayList<>();

		accountAmountList.stream().forEach(accountAmount -> {
			String sourceCurrency = accountAmount.getCurrency();
			availableAmountList.add(exchangeRateFactory.exchange(currency, sourceCurrency, accountAmount.getAvailableAmount()));
			freezeAmountList.add(exchangeRateFactory.exchange(currency, sourceCurrency, accountAmount.getFreezeAmount()));
			transitedAmountList.add(exchangeRateFactory.exchange(currency, sourceCurrency, accountAmount.getTransitedAmount()));
		});
		exchangeRateFactory.cleanCache();
		//可用
		BigDecimal availableAmount = availableAmountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		//冻结
		BigDecimal freezeAmount = freezeAmountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		//在途
		BigDecimal transitedAmount = transitedAmountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

		return availableAmount.add(freezeAmount).add(transitedAmount);
	}

	@Override
	public IPage<CustomerBankCardVO> bankCardInfo(IPage<CustomerBankCardVO> page, Long custId) {

		return null;
	}

	@Override
	public IPage<CapitalFlowVO> capitalFlow(IPage<CapitalFlowVO> page, Long custId) {
		if (custId == null) {
			throw new BusinessException("custId 不能为空");
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			return new Page<>();
		}
		List<FinancingAccountAmountFlows> accountAmountFlows =
			financingAccountAmountFlowsMapper.queryList(page, financingAccount.getAccountId());
		List<CapitalFlowVO> accountAmountFlowVOList = new ArrayList<>();
		if (!CollectionUtil.isEmpty(accountAmountFlows)) {
			accountAmountFlows.stream().forEach(amountFlows -> {
				CapitalFlowVO amountFlowVO = new CapitalFlowVO();
				amountFlowVO.setAmount(amountFlows.getAmount());
				amountFlowVO.setTypeName(amountFlows.getRemark());
				amountFlowVO.setCreateTime(DateUtil.format(amountFlows.getCreateTime(), DateUtil.PATTERN_DATETIME));
				amountFlowVO.setCurrency(CurrencyType.getByCode(amountFlows.getCurrency()).getDesc());
				Boolean flag = amountFlows.getBeforeAmount() == null || amountFlows.getBeforeAmount().compareTo(amountFlows.getAfterAmount()) < 0;
				amountFlowVO.setFlag(flag ? "+" : "-");
				accountAmountFlowVOList.add(amountFlowVO);
			});
		}
		return page.setRecords(accountAmountFlowVOList);
	}

	@Override
	public R<IPage<CustomerPositionsDetailVO>> customerPositionList(Long custId, Integer pageNumber, Integer pageSize, Integer productType) {
		if (custId == null) {
			throw new BusinessException("custId 不能为空");
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			return R.success();
		}

		R<PageVO> result = iAccountClient.customerPositionList(financingAccount.getAccountId(), pageNumber, pageSize, productType);
		if (!result.isSuccess()) {
			throw new BusinessException(result.getMsg());
		}
		PageVO page = result.getData();
		IPage<CustomerPositionsDetailVO> iPage = new Page<>();
		iPage.setTotal(Long.valueOf(page.getTotal()));
		iPage.setPages(Long.valueOf(page.getPages()));
		iPage.setCurrent(Long.valueOf(page.getCurrent()));
		iPage.setSize(Long.valueOf(page.getSize()));
		iPage.setRecords((List<CustomerPositionsDetailVO>) page.getRecords());
		return R.data(iPage);
	}

	@Override
	public R<IPage<CustomerOrderVO>> orderList(Long custId, Integer pageNumber, Integer pageSize, Integer productType, Integer status) {
		if (custId == null) {
			throw new BusinessException("custId 不能为空");
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			return R.success();
		}
		R result = iAccountClient.orderList(financingAccount.getAccountId(), pageNumber, pageSize, productType, status);
		if (!result.isSuccess()) {
			throw new BusinessException(result.getMsg());
		}
		PageVO page = (PageVO) result.getData();
		IPage<CustomerOrderVO> iPage = new Page<>();
		iPage.setTotal(Long.valueOf(page.getTotal()));
		iPage.setPages(Long.valueOf(page.getPages()));
		iPage.setCurrent(Long.valueOf(page.getCurrent()));
		iPage.setSize(Long.valueOf(page.getSize()));
		iPage.setRecords((List<CustomerOrderVO>) page.getRecords());
		return R.data(iPage);
	}

	@Override
	public R<IPage<CustomerHistoryOrderVO>> customerHistoryOrder(Long custId, Integer pageNumber, Integer pageSize, Integer productType) {
		if (custId == null) {
			throw new BusinessException("custId 不能为空");
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			return R.success();
		}
		R result = iAccountClient.customerHistoryOrder(financingAccount.getAccountId(), pageNumber, pageSize, productType);
		if (!result.isSuccess()) {
			throw new BusinessException(result.getMsg());
		}
		PageVO page = (PageVO) result.getData();
		IPage<CustomerHistoryOrderVO> iPage = new Page<>();
		iPage.setTotal(Long.valueOf(page.getTotal()));
		iPage.setPages(Long.valueOf(page.getPages()));
		iPage.setCurrent(Long.valueOf(page.getCurrent()));
		iPage.setSize(Long.valueOf(page.getSize()));
		iPage.setRecords((List<CustomerHistoryOrderVO>) page.getRecords());
		return R.data(iPage);
	}

	@Override
	public R<IPage<DividendDistributionRecords>> distributionRecords(Long custId, Integer pageNumber, Integer pageSize, Integer productType) {
		if (custId == null) {
			throw new BusinessException("custId 不能为空");
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			return R.success();
		}
		R result = iAccountClient.distributionRecords(financingAccount.getAccountId(), pageNumber, pageSize, productType);
		if (!result.isSuccess()) {
			throw new BusinessException(result.getMsg());
		}
		PageVO page = (PageVO) result.getData();
		IPage<DividendDistributionRecords> iPage = new Page<>();
		iPage.setTotal(Long.valueOf(page.getTotal()));
		iPage.setPages(Long.valueOf(page.getPages()));
		iPage.setCurrent(Long.valueOf(page.getCurrent()));
		iPage.setSize(Long.valueOf(page.getSize()));
		iPage.setRecords((List<DividendDistributionRecords>) page.getRecords());
		return R.data(iPage);
	}

	@Override
	public R positionMarketValue(Long custId, Integer productType, String currency) {
		if (custId == null || productType == null) {
			return R.fail("查询失败，参数不能为空");
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			return R.fail("查询失败，请先开通理财账户");
		}
		CustomerPositionsVO positions = new CustomerPositionsVO();
		//查询市值算
		R result = iAccountClient.selectMarketValue(financingAccount.getAccountId());
		if (!result.isSuccess()) {
			throw new ZeroException(result.getMsg());
		}
		JSONObject marketValue = (JSONObject) result.getData();

		//累计收益
		result = iAccountClient.accumulatedInterest(financingAccount.getAccountId());
		if (!result.isSuccess()) {
			throw new ZeroException(result.getMsg());
		}
		JSONObject data = (JSONObject) result.getData();

		// Handle different product types
		handleProductType(positions, custId, productType, marketValue, data, currency);

		BigDecimal targetHKD = exchangeRateFactory.exchange(currency, CurrencyType.HKD.getCode(), positions.getHDKMarketValue());
		BigDecimal targetCNY = exchangeRateFactory.exchange(currency, CurrencyType.CNY.getCode(), positions.getCNYMarketValue());
		BigDecimal targetUSD = exchangeRateFactory.exchange(currency, CurrencyType.USD.getCode(), positions.getUSDMarketValue());
		positions.setMarketValue(targetUSD.add(targetCNY).add(targetHKD));
		exchangeRateFactory.cleanCache();
		return R.data(positions);
	}

	// New helper method to handle product types
	private void handleProductType(CustomerPositionsVO positions, Long custId, Integer productType, JSONObject marketValue, JSONObject data, String currency) {
		String tradeAccount = null;
		String openAccountTime = null;

		switch (productType) {
			case 64:
				CustomerHldTradingAccount hldTradingAccount = customerHldTradingAccountMapper.selectByCustId(custId);
				if (hldTradingAccount != null) {
					tradeAccount = hldTradingAccount.getTradeAccount();
					openAccountTime = DateUtil.formatDate(hldTradingAccount.getCreateTime());
				}
				//活利得市值
				JSONObject hld = marketValue.getJSONObject("hld");
				setMarketValues(positions, hld, data.getJSONObject("hld"), currency);
				break;
			case 65:
				CustomerBondTradingAccount bondTradingAccount = customerBondTradingAccountMapper.selectByCustId(custId);
				if (bondTradingAccount != null) {
					tradeAccount = bondTradingAccount.getTradeAccount();
					openAccountTime = DateUtil.formatDate(bondTradingAccount.getCreateTime());
				}
				//债券易市值
				JSONObject bond = marketValue.getJSONObject("bond");
				setMarketValues(positions, bond, data.getJSONObject("bond"), currency);
				break;
			case 128:
				CustomerDebentureTradingAccount debentureTradingAccount = debentureTradingAccountMapper.selectByCustId(custId);
				if (debentureTradingAccount != null) {
					tradeAccount = debentureTradingAccount.getTradeAccount();
					openAccountTime = DateUtil.formatDate(debentureTradingAccount.getCreateTime());
				}
				// OTC市值
				JSONObject otc = marketValue.getJSONObject("otc");
				setMarketValues(positions, otc, null, currency);
				break;
			default:
				throw new BusinessException("未知的产品类型");
		}

		positions.setAccount(tradeAccount);
		positions.setOpenAccountTime(openAccountTime);
	}

	// New helper method to set market values
	private void setMarketValues(CustomerPositionsVO positions, JSONObject marketData, JSONObject accumulatedData, String currency) {
		BigDecimal USD = this.getValue(marketData, CurrencyType.USD.getCode());
		BigDecimal HKD = this.getValue(marketData, CurrencyType.HKD.getCode());
		BigDecimal CNY = this.getValue(marketData, CurrencyType.CNY.getCode());

		positions.setHDKMarketValue(HKD);
		positions.setCNYMarketValue(CNY);
		positions.setUSDMarketValue(USD);

		if (accumulatedData != null) {
			BigDecimal accumulatedRevenue = getAccumulatedRevenue(accumulatedData, currency);
			positions.setTotalIncome(accumulatedRevenue);
		}
	}

	@Override
	public R selectConfirmation(String orderId) {
		return iAccountClient.selectConfirmation(orderId);
	}

	@Override
	public void downloadConfirmation(HttpServletResponse response, HttpServletRequest request, String path) throws IOException {
		if (StringUtil.isBlank(path)) {
			throw new BusinessException("下载失败，path不能为空");
		}
		HttpRequest httpRequest = HttpRequest.get(path);
		httpRequest.addHeader("Content-Type", "application/pdf");
		byte[] stream = httpRequest.execute().asBytes();
		response.getOutputStream().write(stream);
	}

	@Override
	public R customerStatement(IPage<CustomerOpenAccountVO> page, StatementListDTO statementListDTO) {

		List<CustomerOpenAccountVO> list = getCustomerStatementList(page, statementListDTO);
		page.setRecords(list);
		return R.data(page);
	}

	@Override
	public List<CustomerOpenAccountVO> getCustomerStatementList(IPage<CustomerOpenAccountVO> page, StatementListDTO statementListDTO) {
		if (StringUtil.isBlank(statementListDTO.getStartTime()) && StringUtil.isBlank(statementListDTO.getEndTime())) {

			String format = "";
			if (statementListDTO.getStatementType() == StatementEnum.DAY.getCode()) {
				DateTime yesterday = cn.hutool.core.date.DateUtil.yesterday();
				format = cn.hutool.core.date.DateUtil.format(yesterday, "yyyy-MM-dd");
			} else if (statementListDTO.getStatementType() == StatementEnum.MONTH.getCode()) {
				DateTime yesterday = cn.hutool.core.date.DateUtil.lastMonth();
				format = cn.hutool.core.date.DateUtil.format(yesterday, "yyyy-MM");
			}
			statementListDTO.setStartTime(format);
			statementListDTO.setEndTime(format);
		}
		List<CustomerOpenAccountVO> list = baseMapper.customerStatementList(page, statementListDTO);
		return list;
	}

	@Override
	public R freezeCustomers(Long custId, Integer status) {
		if (custId == null || status == null) {
			throw new BusinessException(ResultCode.PARAM_MISS.getMessage());
		}
		CustomerInfoEntity customerInfo = customerInfoMapper.selectByCustId(custId);
		if (customerInfo == null) {
			throw new BusinessException("用户信息异常，无法冻结");
		}
		customerInfo.setStatus(status);
		customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

		//注销账号踢下线
		if (CustomerStatus.LOGOUT.getCode() == status){
			Jwt2Util.removeAccessToken(String.valueOf(customerInfo.getId()), null);
		}
		return R.success();
	}

	@Override
	public R queryFreezeCustomers(IPage<CustomerWhiteListVO> page, String keyword) {
		List<CustomerWhiteListVO> list = baseMapper.queryFreezeCustomers(page, keyword);
		page.setRecords(list);
		return R.data(page);
	}

	@Override
	public R customerWithdrawalInfo(String accountId) {
		if (StringUtil.isBlank(accountId)) {
			throw new ZeroException("查询失败，理财账号不能为空");
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByAccountId(accountId);
		if (financingAccount == null) {
			throw new ZeroException("查询失败，理财账号不存在!");
		}
		Integer accountStatus = financingAccount.getStatus();
		if (accountStatus != FinancingAccountStatus.NORMAL.getCode()) {
			throw new ZeroException("查询失败，理财账号未激活!");
		}
		Long custId = financingAccount.getCustId();
		CustomerWithdrawalVO customerWithdrawal = new CustomerWithdrawalVO();
		customerWithdrawal.setClientId(accountId);
		customerWithdrawal.setCustId(financingAccount.getCustId());

		CustomerBasicInfoEntity basicInfo = customerBasicInfoMapper.selectByCustId(custId);
		if (basicInfo != null) {
			customerWithdrawal.setClientName(basicInfo.getClientName());
			customerWithdrawal.setMobile(basicInfo.getPhoneArea() + "-" + basicInfo.getPhoneNumber());
			if (StringUtil.isNotBlank(basicInfo.getGivenNameSpell()) && StringUtil.isNotBlank(basicInfo.getFamilyNameSpell())) {
				customerWithdrawal.setClientNameSpell(basicInfo.getGivenNameSpell() + " " + basicInfo.getFamilyNameSpell());
			} else if (StringUtil.isNotBlank(basicInfo.getGivenNameSpell())) {
				customerWithdrawal.setClientNameSpell(basicInfo.getGivenNameSpell());
			} else if (StringUtil.isNotBlank(basicInfo.getFamilyNameSpell())) {
				customerWithdrawal.setClientNameSpell(basicInfo.getFamilyNameSpell());
			}
		}
		List<FinancingAccountAmount> accountAmountList = financingAccountAmountMapper.selectByAccountId(accountId, null);
		if (!CollectionUtil.isEmpty(accountAmountList)) {
			Optional<FinancingAccountAmount> optionalObj = accountAmountList.stream()
				.filter(obj -> obj.getCurrency().equals(CurrencyType.HKD.getCode()))
				.findFirst();
			if (optionalObj.isPresent()) {
				customerWithdrawal.setHkdFreeze(optionalObj.get().getFreezeAmount());
				customerWithdrawal.setHkdAvailable(optionalObj.get().getAvailableAmount());
				customerWithdrawal.setHkdIntransit(optionalObj.get().getTransitedAmount());
			}
			optionalObj = accountAmountList.stream()
				.filter(obj -> obj.getCurrency().equals(CurrencyType.USD.getCode()))
				.findFirst();
			if (optionalObj.isPresent()) {
				customerWithdrawal.setUsdFreeze(optionalObj.get().getFreezeAmount());
				customerWithdrawal.setUsdAvailable(optionalObj.get().getAvailableAmount());
				customerWithdrawal.setUsdIntransit(optionalObj.get().getTransitedAmount());
			}

			optionalObj = accountAmountList.stream()
				.filter(obj -> obj.getCurrency().equals(CurrencyType.CNY.getCode()))
				.findFirst();
			if (optionalObj.isPresent()) {
				customerWithdrawal.setCnyFreeze(optionalObj.get().getFreezeAmount());
				customerWithdrawal.setCnyAvailable(optionalObj.get().getAvailableAmount());
				customerWithdrawal.setCnyIntransit(optionalObj.get().getTransitedAmount());
			}
		}
		return R.data(customerWithdrawal);
	}

	@Override
	public R getCustomerRole(Long custId) {
		if (custId == null) {
			throw new ZeroException("custId不能为空!");
		}

		List<CustomerIdentityInfo> customerPcRoles = customerPcRoleMapper.selectByCustId(custId);
		CustomerRoleVO customerRoleVO = new CustomerRoleVO();
		customerRoleVO.setCustId(custId);
		customerRoleVO.setRoleIds(customerPcRoles.stream().map(CustomerIdentityInfo::getRoleId).collect(Collectors.toList()));
		return R.data(customerRoleVO);
	}

	@Override
	public R settingCustomerRole(Long custId, List<Integer> roles) {
		if (custId == null) {
			throw new ZeroException("设置失败，custId不能为空!");
		}
		CustomerInfoEntity customerInfo = customerInfoMapper.selectById(custId);
		if (customerInfo == null) {
			throw new ZeroException("设置失败，用户不存在!");
		}
		R<List<DictBiz>> pcRoleResult = iDictBizClient.getList(CUSTOMER_PC_ROLE);
		if (!pcRoleResult.isSuccess()) {
			throw new ZeroException("获取角色列表失败，请稍后再试!");
		}
		List<DictBiz> pcRoleList = pcRoleResult.getData();
		if (CollectionUtil.isEmpty(pcRoleList)) {
			throw new ZeroException("获取角色列表失败,请确认是否有配置业务字典【CUSTOMER_PC_ROLE】!");
		}

		// 如果角色集合为空则不做任何处理
		if (roles == null || roles.size() == 0) {
			log.info("设置角色集合为空，不进行任何处理");
			return R.success();
		}

		//判断roles集合中的角色是否在业务字典中
		for (Integer role : roles) {
			Optional<DictBiz> optionalObj = pcRoleList.stream().filter(obj -> obj.getDictValue().equals(role.toString())).findFirst();
			if (!optionalObj.isPresent()) {
				throw new ZeroException("设置失败，传入的角色不存在，请确认是否有正确配置业务字典【CUSTOMER_PC_ROLE】!");
			}
		}

		// 删除之前已经配置的角色记录
		int rows = customerPcRoleMapper.deleteRoleByCustId(custId);
		log.info("设置之前先删除已经配置的角色记录,影响行数：" + rows);

		for (Integer role : roles) {
			CustomerIdentityInfo customerPcRole = customerPcRoleMapper.selectByCustIdAndRoleId(custId, role);
			if (customerPcRole != null) {
				throw new ZeroException("设置失败，该用户已设置该角色!");
			}
			customerPcRole = new CustomerIdentityInfo();
			customerPcRole.setRoleId(role);
			customerPcRole.setCustId(custId);
			customerPcRole.setCreateTime(new Date());
			customerPcRole.setCreaterName(AuthUtil.getUserName());
			Optional<DictBiz> optionalObj = pcRoleList.stream().filter(obj -> obj.getDictValue().equals(role.toString())).findFirst();
			if (optionalObj.isPresent()) {
				customerPcRole.setRoleName(optionalObj.get().getDictKey());
			}
			customerPcRoleMapper.insertSelective(customerPcRole);
		}
		return R.success();
	}

	@Override
	public R deleteCustomerRole(Long custId, List<Integer> roles) {
		if (custId == null) {
			throw new ZeroException("删除失败，custId不能为空!");
		}
		if (roles == null || roles.size() == 0) {
			throw new ZeroException("删除失败，role集合不能为空!");
		}
		CustomerInfoEntity customerInfo = customerInfoMapper.selectById(custId);
		if (customerInfo == null) {
			throw new ZeroException("删除失败，用户不存在!");
		}
		for (Integer role : roles) {
			CustomerIdentityInfo customerPcRole = customerPcRoleMapper.selectByCustIdAndRoleId(custId, role);
			if (customerPcRole == null) {
				throw new ZeroException("删除失败，用户角色不存在!");
			}
			customerPcRole.setUpdateTime(new Date());
			customerPcRole.setStatus(StatusEnum.YES.getCode());
			customerPcRole.setUpdaterName(AuthUtil.getUserName());
			customerPcRoleMapper.updateByPrimaryKeySelective(customerPcRole);
		}
		return R.success();
	}


	@Override
	public R generateSubAccount(SubAccountDTO dto) {

		//参数校验
		CustomerFinancingAccountEntity financingAccount = validateParam(dto);

		//校验子账号个数限制
		validateSubAccount(dto);

		//创建子账号
		List<CustomerFinancingSubAccount> subAccount = createSubAccount(dto);

		//创建子账号通知
		createSubAccountNotice(financingAccount.getCustId(),subAccount);

		return R.success();
	}

	@Override
	public R subAccountList(String accountId, String subAccount, Integer roleId) {
		if (StringUtil.isBlank(accountId)){
			throw new ZeroException("查询失败，accountId 不能为空");
		}
		List<CustomerFinancingSubAccount> list =
			customerFinancingSubAccountMapper.selectSubAccount(accountId,subAccount,roleId);
		if (CollectionUtil.isEmpty(list)){
			return R.success();
		}
		List<FinancingSubAccountVO> result = list.stream().map(financingSubAccount->{
			FinancingSubAccountVO subAccountVO = new FinancingSubAccountVO();
			subAccountVO.setSubAccount(financingSubAccount.getSubAccount());
			subAccountVO.setCreateTime(financingSubAccount.getCreateTime());
			subAccountVO.setRoleName(CustomerRole.getDesc(financingSubAccount.getRoleId()));
			return subAccountVO;
		}).collect(Collectors.toList());
		return R.data(result);
	}


	private void createSubAccountNotice(Long custId,List<CustomerFinancingSubAccount> subAccountList){
		CustomerBasicInfoEntity customerBasicInfo = customerBasicInfoMapper.selectByCustId(custId);
		if (customerBasicInfo == null){
			log.info("账号：{}开户信息为空，子账号开通通知失败",custId);
			return;
		}
		String email = customerBasicInfo.getEmail();
		subAccountList.stream().forEach(financingSubAccount->{
			EmailUtil.builder()
				.param(CustomerRole.getDesc(financingSubAccount.getRoleId()))
				.param(financingSubAccount.getSubAccount())
				.param(financingSubAccount.getPassword())
				.accepts(Arrays.asList(email))
				.templateCode(EmailTemplate.OPEN_SUB_ACCOUNT.getCode())
				.sendAsync();
		});
	}


	private List<CustomerFinancingSubAccount> createSubAccount(SubAccountDTO dto){

		List<CustomerFinancingSubAccount> list = new ArrayList<>();

		dto.getRoleIdList().stream().forEach(roleId->{
			String initPassword = idGenerateUtils.generatePassword();
			CustomerFinancingSubAccount subAccount = CustomerFinancingSubAccount.builder()
				.accountId(dto.getAccountId())
				.roleId(roleId)
				.createTime(new Date())
				.password(new BCryptPasswordEncoder().encode(initPassword))
				.creatorId(String.valueOf(AuthUtil.getUserId()))
				.creatorName(AuthUtil.getUserName())
				.subAccount(generateSubAccount(dto.getAccountId(),roleId))
				.build();

			customerFinancingSubAccountMapper.insertSelective(subAccount);
			//重新设置一下，发邮件用
			subAccount.setPassword(initPassword);
			list.add(subAccount);
		});

		return list;
	}

	private String generateSubAccount(String accountId,Integer roleId){
		StringBuffer subAccount = new StringBuffer(accountId);
		subAccount.append("00");
		String prefix = CustomerRole.getPrefix(roleId);
		subAccount.append(idGenerateUtils.generateSubAccount(accountId,prefix));
		subAccount.append(prefix);
		return subAccount.toString();
	}


	private void validateSubAccount(SubAccountDTO dto){

		dto.getRoleIdList().stream().forEach(roleId->{
			List<CustomerFinancingSubAccount> subAccountList =
				customerFinancingSubAccountMapper.selectByAccountIdAndRoleId(dto.getAccountId(),roleId);

			Integer subAccountLimitQuantity = getSubAccountLimitQuantity(SUB_ACCOUNT_LIMIT_QUANTITY);
			if (subAccountLimitQuantity == null){
				subAccountLimitQuantity = 5;
			}
			if (subAccountLimitQuantity != null && subAccountList.size() >= subAccountLimitQuantity){
				throw new ZeroException("理财账号："+dto.getAccountId()+"创建"+
					CustomerRole.getDesc(subAccountLimitQuantity)+"子账号已达最大个数");
			}
		});

	}


	private CustomerFinancingAccountEntity validateParam(SubAccountDTO dto){
		if (CollectionUtil.isEmpty(dto.getRoleIdList())){
			throw new ZeroException("子账号类型不能为空！");
		}
		if (StringUtil.isBlank(dto.getAccountId())){
			throw new ZeroException("理财账号不能为空");
		}

		List<Integer> roleIdList = Arrays.asList(CustomerRole.values()).stream().map(role->{
			return role.getCode();
		}).collect(Collectors.toList());

		Boolean flag = roleIdList.containsAll(dto.getRoleIdList());
		if (!flag){
			throw new ZeroException("不支持创建其他类型子账号");
		}

		CustomerFinancingAccountEntity financingAccount =
			customerFinancingAccountMapper.selectByAccountId(dto.getAccountId());

		if (financingAccount == null){
			throw new ZeroException("理财账号不存在");
		}

		return financingAccount;
	}


	private Integer getSubAccountLimitQuantity(String code) {
		R<List<DictBiz>> result = iDictBizClient.getList(code);
		if (!result.isSuccess()) {
			throw new ZeroException("获取角色列表失败，请稍后再试!");
		}
		List<DictBiz> subAccountLimitQuantity = result.getData();
		if (CollectionUtil.isEmpty(subAccountLimitQuantity)){
			return null;
		}

		DictBiz dictBiz = subAccountLimitQuantity.get(0);
		return Integer.valueOf(dictBiz.getDictValue());

	}

	@Override
	public BigDecimal getAccumulatedRevenue(JSONObject data, String currency) {
		if (data == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal revenue = getTargetCurrencyAmount(currency, data);
		return revenue;
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
		return targetUSD.add(targetCNY).add(targetHKD);
	}


	private BigDecimal getValue(JSONObject hld, String currency) {
		if (hld == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal obj = hld.getBigDecimal(currency);
		if (obj == null) {
			return BigDecimal.ZERO;
		}
		return obj;
	}
}
