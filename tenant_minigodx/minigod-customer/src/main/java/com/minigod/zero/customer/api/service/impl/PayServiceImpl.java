package com.minigod.zero.customer.api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minigod.common.constant.PayMessageConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.redis.lock.RedisLock;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.customer.api.service.PayService;
import com.minigod.zero.customer.config.GoldProperties;
import com.minigod.zero.customer.dto.AmountDTO;
import com.minigod.zero.customer.dto.PayDetailDTO;
import com.minigod.zero.customer.dto.PayOrderDTO;
import com.minigod.zero.customer.entity.*;
import com.minigod.zero.customer.enums.*;
import com.minigod.zero.customer.factory.ExchangeRateFactory;
import com.minigod.zero.customer.mapper.*;
import com.minigod.zero.customer.vo.AccountAmountFlowVO;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.utils.PushUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/26 10:48
 * @description：
 */
@Slf4j
@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private GoldProperties goldProperties;
	@Autowired
	private ExchangeRateFactory exchangeRateFactory;
	@Autowired
	private CustomerBasicInfoMapper customerBasicInfoMapper;
	@Autowired
	private AccountBusinessFlowMapper accountBusinessFlowMapper;
	@Autowired
	private FinancingAccountAmountMapper financingAccountAmountMapper;
	@Autowired
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;
	@Autowired
	private FinancingAccountAmountFlowsMapper financingAccountAmountFlowsMapper;

	@Override
	@RedisLock(value = "lock:fund:freeze_amount")
	@Transactional(rollbackFor = Exception.class)
	public R freezeAmount(AmountDTO amountDTO) {
		BigDecimal amount = amountDTO.getAmount();
		String currency = amountDTO.getCurrency();
		String accountId = amountDTO.getAccountId();
		Integer thawingType = amountDTO.getThawingType();

		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_FREEZE_AMOUNT_ERROR_NOTICE));
		}
		if (StringUtils.isEmpty(currency)) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_FREEZE_CURRENCY_ERROR_NOTICE));
		}
		//理财账户验证
		if (StringUtils.isEmpty(accountId)) {
			accountId = AuthUtil.getTenantUser().getAccount();
		}
		CustomerFinancingAccountEntity financingAccount = this.checkFinancingAccount(accountId);
		FinancingAccountAmount financingAccountAmount =
			financingAccountAmountMapper.selectByAccountAndCurrency(financingAccount.getAccountId(), currency);
		if (financingAccountAmount == null || financingAccountAmount.getAvailableAmount().compareTo(amount) < 0) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_FREEZE_AMOUNT_NOT_ENOUGH_NOTICE));
		}
		//记录业务类型
		Integer accountBusinessFlowId = this.saveBusinessFlow(amountDTO, financingAccount.getCustId());
		//理财账户
		financingAccountAmountMapper.freezeAvailableAmount(financingAccountAmount.getId(), amount);
		//记录账户流水
		this.saveFinancingAccountAmountFlows(amount, thawingType, financingAccountAmount, FlowBusinessType.AVAILABLE_TO_FREEZE.getCode(), accountBusinessFlowId);
		return R.data(financingAccountAmount);
	}

	@Override
	@RedisLock(value = "lock:fund:lock:thawing_amount")
	@Transactional(rollbackFor = Exception.class)
	public R thawingAmount(AmountDTO amountDTO) {

		Integer type = amountDTO.getType();
		BigDecimal amount = amountDTO.getAmount();
		String currency = amountDTO.getCurrency();
		String accountId = amountDTO.getAccountId();
		Integer thawingType = amountDTO.getThawingType();

		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_UNFREEZING_FAILED_AMOUNT_ERROR_NOTICE));
		}
		if (StringUtils.isEmpty(currency)) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_UNFREEZING_FAILED_CURRENCY_ERROR_NOTICE));
		}
		if (thawingType == null) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_UNFREEZING_FAILED_OPERATION_TYPE_ERROR_NOTICE));
		}
		//理财账户验证
		if (StringUtils.isEmpty(accountId)){
			accountId = AuthUtil.getTenantUser().getAccount();
		}
		CustomerFinancingAccountEntity financingAccount = this.checkFinancingAccount(accountId);
		FinancingAccountAmount financingAccountAmount =
			financingAccountAmountMapper.selectByAccountAndCurrency(financingAccount.getAccountId(), currency);
		if (financingAccountAmount == null || financingAccountAmount.getFreezeAmount().compareTo(amount) < 0) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_UNFREEZING_FAILED_AMOUNT_NOT_ENOUGH_NOTICE));
		}
		Integer accountBusinessFlowId = this.saveBusinessFlow(amountDTO, financingAccount.getCustId());
		//扣减冻结金额
		if (type == 0) {
			financingAccountAmountMapper.reduceFreezeAmount(financingAccountAmount.getId(), amount);
			saveFinancingAccountAmountFlows(amount, thawingType, financingAccountAmount, FlowBusinessType.FREEZE_DEDUCTIONS.getCode(), accountBusinessFlowId);
		}
		//冻结转可用
		if (type == 1) {
			financingAccountAmountMapper.freezeToAvailableAmount(financingAccountAmount.getId(), amount);
			saveFinancingAccountAmountFlows(amount, thawingType, financingAccountAmount, FlowBusinessType.FREEZE_TO_AVAILABLE.getCode(), accountBusinessFlowId);
		}
		return R.success(ResultCode.SUCCESS);
	}

	@Override
	@RedisLock(value = "lock:fund:gold_deposit")
	@Transactional(rollbackFor = Exception.class)
	public R goldDeposit(AmountDTO amountDTO) {

		BigDecimal amount = amountDTO.getAmount();
		String currency = amountDTO.getCurrency();
		String accountId = amountDTO.getAccountId();
		Integer thawingType = amountDTO.getThawingType();

		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_DEPOSIT_AMOUNT_ERROR_NOTICE));
		}
		if (StringUtils.isEmpty(currency)) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_DEPOSIT_CURRENCY_ERROR_NOTICE));
		}
		//理财账户更新
		if (StringUtils.isEmpty(accountId)) {
			accountId = AuthUtil.getTenantUser().getAccount();
		}
		if (StringUtils.isEmpty(accountId)) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_DEPOSIT_UNIFIED_ACCOUNT_ERROR_NOTICE));
		}
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByAccountId(accountId);

		FinancingAccountAmount financingAccountAmount =
			financingAccountAmountMapper.selectByAccountAndCurrency(financingAccount.getAccountId(), currency);
		if (financingAccountAmount == null) {
			financingAccountAmount = new FinancingAccountAmount();
			financingAccountAmount.setCurrency(currency);
			financingAccountAmount.setCreateTime(new Date());
			financingAccountAmount.setAvailableAmount(amount);
			financingAccountAmount.setAccountId(financingAccount.getAccountId());
			financingAccountAmountMapper.insertSelective(financingAccountAmount);
			//初始化记录插入完成后置空，方便后面流水写入before_amount
			financingAccountAmount.setAvailableAmount(BigDecimal.ZERO);
		} else {
			financingAccountAmountMapper.goldDeposit(financingAccountAmount.getId(), amount);
		}
		//预批账号激活
		if (financingAccount.getStatus().equals(FinancingAccountStatus.PRE_APPROVED.getCode())) {
			BigDecimal totalAmount = countAmount(accountId);
			log.info("accountId={} 累计入金金额：{}，目标金额：{}", accountId, totalAmount, goldProperties.getMinDepositAmount());
			if (totalAmount.compareTo(goldProperties.getMinDepositAmount()) >= 0) {
				log.info("修改预批账户为正常账户 accountId={}", accountId);
				financingAccount.setUpdateTime(new Date());
				financingAccount.setActivationTime(new Date());
				financingAccount.setStatus(FinancingAccountStatus.NORMAL.getCode());
				customerFinancingAccountMapper.updateByPrimaryKeySelective(financingAccount);

				CustomerBasicInfoEntity basicInfoEntity = customerBasicInfoMapper.selectByCustId(financingAccount.getCustId());

				String name = basicInfoEntity.getClientName();
				if (org.apache.commons.lang3.StringUtils.isEmpty(name)) {
					name = basicInfoEntity.getFamilyNameSpell() + basicInfoEntity.getGivenNameSpell();
				}
				PushUtil.builder()
					.custId(basicInfoEntity.getCustId())
					.msgGroup("P")
					.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
					.param(name)
					.templateCode(PushTemplate.FUND_IN_ACTIVATE_ACCOUNT.getCode())
					.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
					.pushAsync();
			}
		}
		//业务相关表
		Integer accountBusinessFlowId = this.saveBusinessFlow(amountDTO, financingAccount.getCustId());
		//账户流水
		List<FinancingAccountAmountFlows> flows =
			saveFinancingAccountAmountFlows(amount, thawingType, financingAccountAmount, FlowBusinessType.AVAILABLE_DEPOSIT.getCode(), accountBusinessFlowId);
		return R.data(flows);
	}

	@Override
	@RedisLock(value = "lock:fund:scratch_button")
	@Transactional(rollbackFor = Exception.class)
	public R scratchButton(AmountDTO amountDTO) {
		BigDecimal amount = amountDTO.getAmount();
		String currency = amountDTO.getCurrency();
		String accountId = amountDTO.getAccountId();
		Integer thawingType = amountDTO.getThawingType();
		//理财账号
		CustomerFinancingAccountEntity financingAccount = checkFinancingAccount(accountId);
		if (financingAccount == null) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_DEDUCTION_FAILED_ACCOUNT_NOT_EXIST_NOTICE));
		}
		//保存业务数据
		Integer accountBusinessFlowId = saveBusinessFlow(amountDTO, financingAccount.getCustId());

		FinancingAccountAmount financingAccountAmount = financingAccountAmountMapper.selectByAccountAndCurrency(accountId, currency);
		BigDecimal localCurrencyAvailableAmount = getLocalCurrencyAvailableAmount(financingAccountAmount, accountId, currency);
		//本币种是否足够,true足够，false 不够
		Boolean isEnough = localCurrencyAvailableAmount.compareTo(amount) >= 0;
		if (!isEnough) {
			//自动划扣其他账户开关
			if (StatusEnum.NO.getCode() == goldProperties.getAutomaticDeduction()) {
				log.info("自动换汇控制【关闭】");
				throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_DEDUCTION_FAILED_AMOUNT_NOT_ENOUGH_NOTICE));
			}
			List<FinancingAccountAmount> accountAmountList = financingAccountAmountMapper.selectByAccountId(accountId, null);
			//其他币种总金额够不够
			BigDecimal otherCurrencyAvailableAmount = getOtherCurrencyAvailableAmount(accountAmountList, currency);
			if (localCurrencyAvailableAmount.add(otherCurrencyAvailableAmount).compareTo(amount) < 0) {
				log.info("所有币种账户金额不够");
				throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_DEDUCTION_FAILED_AMOUNT_NOT_ENOUGH_NOTICE));
			}
			//其他币种转过来
			LinkedList<FinancingAccountAmount> financingAccountAmountList = allCurrencyAccount(accountAmountList);
			BigDecimal remainingAmount = amount.subtract(localCurrencyAvailableAmount);
			for (FinancingAccountAmount sourceAccount : financingAccountAmountList) {
				if (!sourceAccount.getCurrency().equals(currency)) {
					//其他账户需要转过来的金额
					remainingAmount = currencyExchange(sourceAccount, currency, remainingAmount, accountBusinessFlowId);
				}
			}
		}
		financingAccountAmountMapper.scratchButton(financingAccountAmount.getId(), amount);
		saveFinancingAccountAmountFlows(amount, thawingType, financingAccountAmount, FlowBusinessType.AVAILABLE_DEDUCTIONS.getCode(), accountBusinessFlowId);
		return R.data(financingAccountAmountFlowsMapper.selectByAccountBusinessFlowId(accountBusinessFlowId));
	}


	@Override
	@RedisLock(value = "lock:fund:advance_payment")
	@Transactional(rollbackFor = Exception.class)
	public R advancePayment(PayOrderDTO payOrder) {
		String accountId = payOrder.getAccountId();
		//参数校验
		paymentParamCheck(payOrder);
		//理财账号
		CustomerFinancingAccountEntity financingAccount = checkFinancingAccount(payOrder.getAccountId());
		if (financingAccount == null) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_DEDUCTION_FAILED_ACCOUNT_NOT_EXIST_NOTICE));
		}
		Long custId = financingAccount.getCustId();
		String currency = payOrder.getCurrency();
		BigDecimal orderAmount = getOrderAmount(payOrder.getPayDetails());
		//保存业务数据
		Integer accountBusinessFlowId = saveBusinessOrder(payOrder, orderAmount, custId);

		FinancingAccountAmount financingAccountAmount = financingAccountAmountMapper.selectByAccountAndCurrency(accountId, currency);
		BigDecimal localCurrencyAvailableAmount = getLocalCurrencyAvailableAmount(financingAccountAmount, accountId, currency);
		//本币种是否足够,true足够，false 不够
		Boolean isEnough = localCurrencyAvailableAmount.compareTo(orderAmount) >= 0;
		if (!isEnough) {
			//自动划扣其他账户开关
			if (StatusEnum.NO.getCode() == goldProperties.getAutomaticDeduction()) {
				log.info("自动换汇控制【关闭】");
				throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_DEDUCTION_FAILED_AMOUNT_NOT_ENOUGH_NOTICE));
			}
			List<FinancingAccountAmount> accountAmountList = financingAccountAmountMapper.selectByAccountId(accountId, null);
			//其他币种总金额够不够
			BigDecimal otherCurrencyAvailableAmount = getOtherCurrencyAvailableAmount(accountAmountList, currency);
			if (localCurrencyAvailableAmount.add(otherCurrencyAvailableAmount).compareTo(orderAmount) < 0) {
				log.info("所有币种账户金额不够");
				throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_DEDUCTION_FAILED_AMOUNT_NOT_ENOUGH_NOTICE));
			}
			//其他币种转过来
			LinkedList<FinancingAccountAmount> financingAccountAmountList = allCurrencyAccount(accountAmountList);
			BigDecimal remainingAmount = orderAmount.subtract(localCurrencyAvailableAmount);
			for (FinancingAccountAmount sourceAccount : financingAccountAmountList) {
				if (!sourceAccount.getCurrency().equals(currency)) {
					//其他账户需要转过来的金额
					remainingAmount = currencyExchange(sourceAccount, currency, remainingAmount, accountBusinessFlowId);
				}
			}
		}
		//付款明细
		List<PayDetailDTO> payDetailList = payOrder.getPayDetails();
		for (PayDetailDTO payDetail : payDetailList) {
			BigDecimal amount = payDetail.getAmount();
			Integer thawingType = payDetail.getThawingType();
			financingAccountAmount = financingAccountAmountMapper.selectByAccountAndCurrency(accountId, currency);
			financingAccountAmountMapper.freezeAvailableAmount(financingAccountAmount.getId(), amount);
			saveFinancingAccountAmountFlows(amount, thawingType, financingAccountAmount, FlowBusinessType.AVAILABLE_TO_FREEZE.getCode(), accountBusinessFlowId);
		}
		return R.success();
	}

	@Override
	@RedisLock(value = "lock:fund:confirm_payment")
	@Transactional(rollbackFor = Exception.class)
	public R confirmPayment(PayOrderDTO payOrder) {
		String accountId = payOrder.getAccountId();
		String businessId = payOrder.getBusinessId();
		//参数校验
		paymentParamCheck(payOrder);
		AccountBusinessFlow businessFlow =
			accountBusinessFlowMapper.selectByBusinessIdAndSource(businessId, payOrder.getSource(), payOrder.getBusinessType());
		if (businessFlow == null) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_CONFIRM_PAYMENT_FAILED_BUSINESS_ID_NOT_EXIST_NOTICE));
		}
		if (businessFlow.getStatus() == PayStatus.PAYMENT_SUCCESSFUL.getCode()
			|| businessFlow.getStatus() == PayStatus.CANCEL_PAYMENT.getCode()) {
			throw new ZeroException(String.format(I18nUtil.getMessage(PayMessageConstant.PAY_CONFIRM_PAYMENT_FAILED_BUSINESS_ID_HANDLED_NOTICE), businessId));
		}
		String currency = payOrder.getCurrency();
		if (!businessFlow.getCurrency().equals(currency)) {
			throw new ZeroException(String.format(I18nUtil.getMessage(PayMessageConstant.PAY_CONFIRM_PAYMENT_FAILED_CURRENCY_NOT_EQUAL_NOTICE), businessId));
		}
		//不能超过订单原先总金额
		List<PayDetailDTO> payDetailList = payOrder.getPayDetails();
		BigDecimal orderAmount = getOrderAmount(payDetailList).add(businessFlow.getPayAmount()).add(businessFlow.getRefundingAmount());
		if (orderAmount.compareTo(businessFlow.getAmount()) > 0) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_CONFIRM_PAYMENT_FAILED_AMOUNT_ERROR_NOTICE));
		}
		for (PayDetailDTO payDetail : payDetailList) {
			BigDecimal amount = payDetail.getAmount();
			Integer thawingType = payDetail.getThawingType();
			Integer payType = payDetail.getPayType();
			FinancingAccountAmount accountAmount = financingAccountAmountMapper.selectByAccountAndCurrency(accountId, currency);
			if (payType == FlowBusinessType.FREEZE_DEDUCTIONS.getCode()) {
				financingAccountAmountMapper.reduceFreezeAmount(accountAmount.getId(), amount);
			}
			if (payType == FlowBusinessType.FREEZE_TO_AVAILABLE.getCode()) {
				financingAccountAmountMapper.freezeToAvailableAmount(accountAmount.getId(), amount);
			}
			//保存流水
			saveFinancingAccountAmountFlows(amount, thawingType, accountAmount, payType, businessFlow.getId());
		}
		businessFlowStatus(businessFlow, payDetailList);
		accountBusinessFlowMapper.updateByPrimaryKeySelective(businessFlow);
		return R.success();
	}

	@Override
	@RedisLock(value = "lock:fund:transited_deposit")
	@Transactional(rollbackFor = Exception.class)
	public R transitedDeposit(PayOrderDTO payOrder) {
		//参数校验
		paymentParamCheck(payOrder);
		//理财账号
		CustomerFinancingAccountEntity financingAccount = checkFinancingAccount(payOrder.getAccountId());
		//业务相关表
		List<PayDetailDTO> payDetailList = payOrder.getPayDetails();
		BigDecimal orderAmount = getOrderAmount(payDetailList);
		Integer accountBusinessFlowId = this.saveBusinessOrder(payOrder, orderAmount, financingAccount.getCustId());
		String currency = payOrder.getCurrency();
		String accountId = payOrder.getAccountId();
		//付款明细
		for (PayDetailDTO payDetail : payDetailList) {
			BigDecimal amount = payDetail.getAmount();
			Integer thawingType = payDetail.getThawingType();
			FinancingAccountAmount financingAccountAmount = financingAccountAmountMapper.selectByAccountAndCurrency(accountId, currency);
			financingAccountAmountMapper.transitedDeposit(financingAccountAmount.getId(), amount);
			saveFinancingAccountAmountFlows(amount, thawingType, financingAccountAmount, FlowBusinessType.TRANSITED_DEPOSIT.getCode(), accountBusinessFlowId);
		}
		return R.success();
	}

	@Override
	public R transitedToAvailable(PayOrderDTO payOrder) {
		String accountId = payOrder.getAccountId();
		String businessId = payOrder.getBusinessId();
		//参数校验
		paymentParamCheck(payOrder);
		AccountBusinessFlow businessFlow =
			accountBusinessFlowMapper.selectByBusinessIdAndSource(businessId, payOrder.getSource(), payOrder.getBusinessType());
		if (businessFlow == null) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_TRANSITED_TO_AVAILABLE_FAILED_BUSINESS_ID_NOT_EXIST_NOTICE));
		}
		if (businessFlow.getStatus() == PayStatus.PAYMENT_SUCCESSFUL.getCode()
			|| businessFlow.getStatus() == PayStatus.CANCEL_PAYMENT.getCode()) {
			throw new ZeroException(String.format(I18nUtil.getMessage(PayMessageConstant.PAY_TRANSITED_TO_AVAILABLE_FAILED_BUSINESS_ID_HANDLED_NOTICE), businessId));
		}
		String currency = payOrder.getCurrency();
		if (!businessFlow.getCurrency().equals(currency)) {
			throw new ZeroException(String.format(I18nUtil.getMessage(PayMessageConstant.PAY_TRANSITED_TO_AVAILABLE_FAILED_CURRENCY_NOT_EQUAL_NOTICE), businessId));
		}
		//不能超过订单原先总金额
		List<PayDetailDTO> payDetailList = payOrder.getPayDetails();
		BigDecimal orderAmount = getOrderAmount(payDetailList).add(businessFlow.getPayAmount()).add(businessFlow.getRefundingAmount());
		if (orderAmount.compareTo(businessFlow.getAmount()) > 0) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_TRANSITED_TO_AVAILABLE_FAILED_AMOUNT_ERROR_NOTICE));
		}
		for (PayDetailDTO payDetail : payDetailList) {
			BigDecimal amount = payDetail.getAmount();
			Integer thawingType = payDetail.getThawingType();
			FinancingAccountAmount accountAmount = financingAccountAmountMapper.selectByAccountAndCurrency(accountId, currency);
			financingAccountAmountMapper.transitedToAvailable(accountAmount.getId(), amount);
			//保存流水
			saveFinancingAccountAmountFlows(amount, thawingType, accountAmount, FlowBusinessType.TRANSITED_TO_AVAILABLE.getCode(), businessFlow.getId());
		}
		businessFlowStatus(businessFlow, payDetailList);
		accountBusinessFlowMapper.updateByPrimaryKeySelective(businessFlow);
		return R.success();
	}


	@Override
	public R amountFlows(String currency, String starTime, String endTme, Integer pageSize, Integer pageIndex) {
		String accountId = AuthUtil.getTenantUser().getAccount();
		if (!StringUtils.isEmpty(starTime)) {
			starTime = starTime + " 00:00:00";
		}
		if (!StringUtils.isEmpty(endTme)) {
			endTme = endTme + " 23:59:59";
		}
		PageHelper.startPage(pageIndex, pageSize);
		List<FinancingAccountAmountFlows> accountAmountFlows = financingAccountAmountFlowsMapper.querypage(accountId, currency, starTime, endTme);
		PageInfo pageInfo = new PageInfo(accountAmountFlows);
		List<AccountAmountFlowVO> accountAmountFlowVOList = new ArrayList<>();
		if (!CollectionUtil.isEmpty(accountAmountFlows)) {
			accountAmountFlows.stream().forEach(amountFlows -> {
				AccountAmountFlowVO amountFlowVO = new AccountAmountFlowVO();
				amountFlowVO.setBusinessAmount(amountFlows.getAmount());
				amountFlowVO.setBusinessBalance(amountFlows.getAfterAmount());
				amountFlowVO.setCurrency(amountFlows.getCurrency());
				amountFlowVO.setBusinessDate(DateUtil.format(amountFlows.getCreateTime(), DateUtil.PATTERN_DATETIME));
				amountFlowVO.setBusinessName(amountFlows.getRemark());
				Boolean flag = amountFlows.getBeforeAmount() == null || amountFlows.getBeforeAmount().compareTo(amountFlows.getAfterAmount()) < 0;
				amountFlowVO.setBusinessFlag(flag ? "+" : "-");
				accountAmountFlowVOList.add(amountFlowVO);
			});
		}
		pageInfo.setList(accountAmountFlowVOList);
		return R.data(pageInfo);
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
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_OPERATION_FAILED_ACCOUNT_NOT_EXIST_NOTICE));
		}
		return financingAccount;
	}

	/**
	 * 保存业务订单信息
	 *
	 * @param amountDTO
	 * @param custId
	 * @return
	 */
	private Integer saveBusinessFlow(AmountDTO amountDTO, Long custId) {
		AccountBusinessFlow accountBusiness = new AccountBusinessFlow();
		accountBusiness.setCustId(custId);
		accountBusiness.setCreateTime(new Date());
		accountBusiness.setSource(amountDTO.getSource());
		accountBusiness.setAmount(amountDTO.getAmount());
		accountBusiness.setCurrency(amountDTO.getCurrency());
		accountBusiness.setAccountId(amountDTO.getAccountId());
		accountBusiness.setBusinessId(amountDTO.getBusinessId());
		accountBusinessFlowMapper.insertSelective(accountBusiness);
		return accountBusiness.getId();
	}

	/**
	 * 交易保存业务订单信息
	 *
	 * @param payOrder
	 * @param orderAmount
	 * @param custId
	 * @return
	 */
	public Integer saveBusinessOrder(PayOrderDTO payOrder, BigDecimal orderAmount, Long custId) {
		AccountBusinessFlow accountBusiness = new AccountBusinessFlow();
		accountBusiness.setCustId(custId);
		accountBusiness.setAmount(orderAmount);
		accountBusiness.setCreateTime(new Date());
		accountBusiness.setSource(payOrder.getSource());
		accountBusiness.setCurrency(payOrder.getCurrency());
		accountBusiness.setAccountId(payOrder.getAccountId());
		accountBusiness.setBusinessId(payOrder.getBusinessId());
		accountBusiness.setBusinessType(payOrder.getBusinessType());
		accountBusiness.setStatus(PayStatus.ADVANCE_CHARGE.getCode());
		accountBusinessFlowMapper.insertSelective(accountBusiness);
		return accountBusiness.getId();
	}

	/**
	 * 统计参数总金额
	 *
	 * @param payDetailList
	 * @return
	 */
	private BigDecimal getOrderAmount(List<PayDetailDTO> payDetailList) {
		return payDetailList.stream()
			.map(PayDetailDTO::getAmount)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}


	/**
	 * 统计账户总的入金金额
	 *
	 * @param accountId
	 * @return
	 */
	private BigDecimal countAmount(String accountId) {
		List<FinancingAccountAmount> accountAmountList = financingAccountAmountMapper.selectByAccountId(accountId, null);
		if (CollectionUtil.isEmpty(accountAmountList)) {
			return BigDecimal.ZERO;
		}
		List<BigDecimal> availableAmountList = new ArrayList<>();
		accountAmountList.stream().forEach(accountAmount -> {
			String sourceCurrency = accountAmount.getCurrency();
			availableAmountList.add(exchangeRateFactory.exchange(CurrencyType.HKD.getCode(), sourceCurrency, accountAmount.getAvailableAmount()));
		});
		BigDecimal availableAmount = availableAmountList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		return availableAmount;
	}

	/**
	 * 参数校验
	 */
	private void paymentParamCheck(PayOrderDTO payOrder) {
		if (StringUtils.isEmpty(payOrder.getAccountId())) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_OPERATION_ACCOUNT_ID_ERROR_NOTICE));
		}
		if (StringUtils.isEmpty(payOrder.getBusinessId())) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_OPERATION_BUSINESS_ID_ERROR_NOTICE));
		}
		List<PayDetailDTO> payDetailList = payOrder.getPayDetails();
		if (CollectionUtil.isEmpty(payDetailList)) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_OPERATION_PAY_DETAILS_ERROR_NOTICE));
		}
		if (StringUtils.isEmpty(payOrder.getCurrency())) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_OPERATION_CURRENCY_ERROR_NOTICE));
		}
		if (StringUtils.isEmpty(payOrder.getSource())) {
			throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_OPERATION_SOURCE_ERROR_NOTICE));
		}
		for (PayDetailDTO payDetail : payDetailList) {
			BigDecimal amount = payDetail.getAmount();
			if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
				throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_OPERATION_AMOUNT_ERROR_NOTICE));
			}
			if (payDetail.getThawingType() == null) {
				throw new ZeroException(I18nUtil.getMessage(PayMessageConstant.PAY_OPERATION_BUSINESS_TYPE_ERROR_NOTICE));
			}
		}

	}

	/**
	 * 处理业务状态
	 *
	 * @return
	 */
	private void businessFlowStatus(AccountBusinessFlow accountBusinessFlow, List<PayDetailDTO> payDetailList) {
		BigDecimal deductible = getAmountByAccountType(payDetailList, FlowBusinessType.FREEZE_DEDUCTIONS.getCode());
		BigDecimal refundingAmount = getAmountByAccountType(payDetailList, FlowBusinessType.FREEZE_TO_AVAILABLE.getCode());

		BigDecimal orderAmount = accountBusinessFlow.getAmount();
		BigDecimal totalDeductible = deductible.add(accountBusinessFlow.getPayAmount());
		BigDecimal totalRefundingAmount = refundingAmount.add(accountBusinessFlow.getRefundingAmount());

		if (totalDeductible.compareTo(orderAmount) == 0
			|| totalDeductible.add(totalRefundingAmount).compareTo(orderAmount) == 0) {
			accountBusinessFlow.setStatus(PayStatus.PAYMENT_SUCCESSFUL.getCode());
		} else if (totalRefundingAmount.compareTo(orderAmount) == 0) {
			accountBusinessFlow.setStatus(PayStatus.CANCEL_PAYMENT.getCode());
		} else if (totalDeductible.add(totalRefundingAmount).compareTo(orderAmount) < 0
			|| totalDeductible.compareTo(orderAmount) < 0) {
			accountBusinessFlow.setStatus(PayStatus.PARTIAL_PAYMENT.getCode());
		}
		accountBusinessFlow.setPayAmount(totalDeductible);
		accountBusinessFlow.setRefundingAmount(totalRefundingAmount);
	}

	/**
	 * 获取操作的总金额
	 *
	 * @param payDetailList
	 * @param accountType
	 * @return
	 */
	private BigDecimal getAmountByAccountType(List<PayDetailDTO> payDetailList, Integer accountType) {
		BigDecimal amount = BigDecimal.ZERO;
		for (PayDetailDTO payDetail : payDetailList) {
			if (accountType == payDetail.getPayType()) {
				amount = amount.add(payDetail.getAmount());
			}
		}
		return amount;
	}

	/**
	 * 获取所有币种现金账户
	 * 根据HKD USD CNY 排序
	 */
	private LinkedList<FinancingAccountAmount> allCurrencyAccount(List<FinancingAccountAmount> accountAmountList) {
		LinkedList<FinancingAccountAmount> linkedList = new LinkedList<>();

		//排序，港币，美元，人民币
		Optional<FinancingAccountAmount> optionalObj = accountAmountList.stream()
			.filter(obj -> obj.getCurrency().equals(CurrencyType.HKD.getCode()))
			.findFirst();
		if (optionalObj.isPresent()) {
			linkedList.add(optionalObj.get());
		}

		optionalObj = accountAmountList.stream()
			.filter(obj -> obj.getCurrency().equals(CurrencyType.CNY.getCode()))
			.findFirst();
		if (optionalObj.isPresent()) {
			linkedList.add(optionalObj.get());
		}

		optionalObj = accountAmountList.stream()
			.filter(obj -> obj.getCurrency().equals(CurrencyType.USD.getCode()))
			.findFirst();
		if (optionalObj.isPresent()) {
			linkedList.add(optionalObj.get());
		}
		return linkedList;
	}

	/**
	 * @param sourceCurrencyAccount 理财账号
	 * @param targetCurrency        目标币种
	 * @param amount                金额
	 * @param accountBusinessFlowId 业务类型
	 */
	private BigDecimal currencyExchange(FinancingAccountAmount sourceCurrencyAccount, String targetCurrency, BigDecimal amount, Integer accountBusinessFlowId) {
		//源币种账户
		if (sourceCurrencyAccount == null || sourceCurrencyAccount.getAvailableAmount().compareTo(BigDecimal.ZERO) <= 0) {
			return amount;
		}
		String accountId = sourceCurrencyAccount.getAccountId();
		String sourceCurrency = sourceCurrencyAccount.getCurrency();
		//将目标币种金额做一次转换
		BigDecimal sourceExchangeAmount = exchangeRateFactory.exchange(sourceCurrency, targetCurrency, amount);
		//源币种需要扣减的金额
		BigDecimal sourceDeductionAmount = BigDecimal.ZERO;
		BigDecimal targetIncreasedAmount = BigDecimal.ZERO;
		BigDecimal targetRemainingAmount = BigDecimal.ZERO;
		//源币种够转换
		if (sourceCurrencyAccount.getAvailableAmount().compareTo(sourceExchangeAmount) >= 0) {
			targetIncreasedAmount = amount;
			targetRemainingAmount = BigDecimal.ZERO;
			sourceDeductionAmount = sourceExchangeAmount;
		}
		//源币种不够转换,先将该币种全部转换，剩下的留给下一个币种账户转换
		else {
			sourceDeductionAmount = sourceCurrencyAccount.getAvailableAmount();
			//剩余未扣减的源币种金额
			BigDecimal remainingAmount = sourceExchangeAmount.subtract(sourceCurrencyAccount.getAvailableAmount());
			//转换成剩余的目标币种金额
			targetRemainingAmount = exchangeRateFactory.exchange(targetCurrency, sourceCurrency, remainingAmount);
			targetIncreasedAmount = amount.subtract(targetRemainingAmount);
		}
		//源币种账户减少
		financingAccountAmountMapper.scratchButton(sourceCurrencyAccount.getId(), sourceDeductionAmount);
		//源币种账户流水
		saveFinancingAccountAmountFlows(sourceDeductionAmount, ThawingType.TRADING_EXCHANGE.getCode(),
			sourceCurrencyAccount, FlowBusinessType.AVAILABLE_DEDUCTIONS.getCode(), accountBusinessFlowId);
		//目标币种账户增加
		FinancingAccountAmount targetCurrencyAccount =
			financingAccountAmountMapper.selectByAccountAndCurrency(accountId, targetCurrency);
		financingAccountAmountMapper.goldDeposit(targetCurrencyAccount.getId(), targetIncreasedAmount);
		//目标币种账户流水
		saveFinancingAccountAmountFlows(targetIncreasedAmount, ThawingType.TRADING_EXCHANGE.getCode(),
			targetCurrencyAccount, FlowBusinessType.AVAILABLE_DEPOSIT.getCode(), accountBusinessFlowId);
		//返回剩余金额
		return targetRemainingAmount;
	}

	/**
	 * 获取币种账户 可用金额
	 *
	 * @param localCurrencyAccount
	 * @param accountId
	 * @param currency
	 * @return
	 */
	private BigDecimal getLocalCurrencyAvailableAmount(FinancingAccountAmount localCurrencyAccount, String accountId, String currency) {
		if (localCurrencyAccount == null) {
			FinancingAccountAmount newAccountAmount = new FinancingAccountAmount();
			newAccountAmount.setAccountId(accountId);
			newAccountAmount.setCurrency(currency);
			newAccountAmount.setCreateTime(new Date());
			newAccountAmount.setAvailableAmount(BigDecimal.ZERO);
			financingAccountAmountMapper.insertSelective(newAccountAmount);
			localCurrencyAccount = newAccountAmount;
		}
		return localCurrencyAccount.getAvailableAmount();
	}

	/**
	 * 获取其他币种总金额（可用）
	 *
	 * @param otherCurrencyAccountList
	 * @param currency
	 * @return
	 */
	private BigDecimal getOtherCurrencyAvailableAmount(List<FinancingAccountAmount> otherCurrencyAccountList, String currency) {
		//统计其他币种总金额
		List<FinancingAccountAmount> otherCurrencyAccounts = otherCurrencyAccountList.stream()
			.filter(accountAmount -> !accountAmount.getCurrency().equals(currency))
			.collect(Collectors.toList());

		List<BigDecimal> otherAccountAmount = new ArrayList<>();
		otherCurrencyAccounts.stream().forEach(otherCurrencyAccount -> {
			String sourceCurrency = otherCurrencyAccount.getCurrency();
			BigDecimal otherAmount = otherCurrencyAccount.getAvailableAmount();
			//其他币种转成目标币种金额
			otherAccountAmount.add(exchangeRateFactory.exchange(currency, sourceCurrency, otherAmount));
		});
		BigDecimal otherCurrencyTotalAmount = otherAccountAmount.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		return otherCurrencyTotalAmount;
	}

	/**
	 * 理财账户金额流水
	 *
	 * @param amount                 金额
	 * @param thawingType            业务类型
	 * @param financingAccountAmount 账户
	 * @param accountType            操作类型
	 * @return
	 */
	private List<FinancingAccountAmountFlows> saveFinancingAccountAmountFlows(BigDecimal amount, Integer thawingType, FinancingAccountAmount financingAccountAmount,
																			  Integer accountType, Integer accountBusinessFlowId) {

		List<FinancingAccountAmountFlows> accountAmountFlows = new ArrayList<>();
		FinancingAccountAmountFlows financingAccountAmountFlow = new FinancingAccountAmountFlows();
		financingAccountAmountFlow.setAmount(amount);
		financingAccountAmountFlow.setCreateTime(new Date());
		financingAccountAmountFlow.setBusinessType(thawingType);
		financingAccountAmountFlow.setOperationType(accountType);
		financingAccountAmountFlow.setAccountBusinessFlowId(accountBusinessFlowId);
		financingAccountAmountFlow.setCurrency(financingAccountAmount.getCurrency());
		financingAccountAmountFlow.setAccountId(financingAccountAmount.getAccountId());
		financingAccountAmountFlow.setRemark(ThawingType.getByCode(thawingType).getDesc());
		//获取操作后剩余金额
		FinancingAccountAmount afterAccountAmount = financingAccountAmountMapper.selectByPrimaryKey(financingAccountAmount.getId());
		//可用入金
		if (FlowBusinessType.AVAILABLE_DEPOSIT.getCode() == accountType
			|| FlowBusinessType.AVAILABLE_DEDUCTIONS.getCode() == accountType) {
			financingAccountAmountFlow.setType(AmountType.AVAILABLE_AMOUNT.getCode());
			financingAccountAmountFlow.setAfterAmount(afterAccountAmount.getAvailableAmount());
			financingAccountAmountFlow.setRemark(financingAccountAmountFlow.getRemark() + "(现金)");
			financingAccountAmountFlow.setBeforeAmount(financingAccountAmount.getAvailableAmount());
			financingAccountAmountFlowsMapper.insertSelective(financingAccountAmountFlow);
			accountAmountFlows.add(financingAccountAmountFlow);
		}
		//冻结出金
		if (FlowBusinessType.FREEZE_DEDUCTIONS.getCode() == accountType) {
			financingAccountAmountFlow.setType(AmountType.FREEZE_AMOUNT.getCode());
			financingAccountAmountFlow.setAfterAmount(afterAccountAmount.getFreezeAmount());
			financingAccountAmountFlow.setBeforeAmount(financingAccountAmount.getFreezeAmount());
			financingAccountAmountFlow.setRemark(financingAccountAmountFlow.getRemark() + "(冻结)");
			financingAccountAmountFlowsMapper.insertSelective(financingAccountAmountFlow);
			accountAmountFlows.add(financingAccountAmountFlow);
		}
		//在途入金
		if (FlowBusinessType.TRANSITED_DEPOSIT.getCode() == accountType) {
			financingAccountAmountFlow.setType(AmountType.TRANSITED_AMOUNT.getCode());
			financingAccountAmountFlow.setAfterAmount(afterAccountAmount.getTransitedAmount());
			financingAccountAmountFlow.setBeforeAmount(financingAccountAmount.getTransitedAmount());
			financingAccountAmountFlow.setRemark(financingAccountAmountFlow.getRemark() + "(在途)");
			financingAccountAmountFlowsMapper.insertSelective(financingAccountAmountFlow);
			accountAmountFlows.add(financingAccountAmountFlow);
		}
		//可用转冻结
		if (FlowBusinessType.AVAILABLE_TO_FREEZE.getCode() == accountType
			|| FlowBusinessType.FREEZE_TO_AVAILABLE.getCode() == accountType) {
			String remark = financingAccountAmountFlow.getRemark();
			FinancingAccountAmountFlows freezeAmount = financingAccountAmountFlow;
			freezeAmount.setRemark(remark + "(冻结)");
			freezeAmount.setType(AmountType.FREEZE_AMOUNT.getCode());
			freezeAmount.setAfterAmount(afterAccountAmount.getFreezeAmount());
			freezeAmount.setBeforeAmount(financingAccountAmount.getFreezeAmount());
			financingAccountAmountFlowsMapper.insertSelective(freezeAmount);
			accountAmountFlows.add(freezeAmount);

			FinancingAccountAmountFlows availableAmount = financingAccountAmountFlow;
			availableAmount.setRemark(remark + "(现金)");
			availableAmount.setType(AmountType.AVAILABLE_AMOUNT.getCode());
			availableAmount.setAfterAmount(afterAccountAmount.getAvailableAmount());
			availableAmount.setBeforeAmount(financingAccountAmount.getAvailableAmount());
			financingAccountAmountFlowsMapper.insertSelective(availableAmount);
			accountAmountFlows.add(availableAmount);
		}
		//在途转可用
		if (FlowBusinessType.TRANSITED_TO_AVAILABLE.getCode() == accountType) {
			String remark = financingAccountAmountFlow.getRemark();
			FinancingAccountAmountFlows transitedAmount = financingAccountAmountFlow;
			transitedAmount.setRemark(remark + "(在途)");
			transitedAmount.setType(AmountType.TRANSITED_AMOUNT.getCode());
			transitedAmount.setAfterAmount(afterAccountAmount.getFreezeAmount());
			transitedAmount.setBeforeAmount(financingAccountAmount.getFreezeAmount());
			financingAccountAmountFlowsMapper.insertSelective(transitedAmount);
			accountAmountFlows.add(transitedAmount);

			FinancingAccountAmountFlows availableAmount = financingAccountAmountFlow;
			availableAmount.setRemark(remark + "(现金)");
			availableAmount.setType(AmountType.AVAILABLE_AMOUNT.getCode());
			availableAmount.setAfterAmount(afterAccountAmount.getAvailableAmount());
			availableAmount.setBeforeAmount(financingAccountAmount.getAvailableAmount());
			financingAccountAmountFlowsMapper.insertSelective(availableAmount);
			accountAmountFlows.add(availableAmount);
		}
		return accountAmountFlows;
	}
}
