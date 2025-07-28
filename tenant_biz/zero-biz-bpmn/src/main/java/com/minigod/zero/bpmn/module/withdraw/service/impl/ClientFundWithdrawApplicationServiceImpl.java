package com.minigod.zero.bpmn.module.withdraw.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.common.constant.TenantConstant;
import com.minigod.zero.biz.common.enums.LanguageEnum;
import com.minigod.zero.biz.common.enums.WithdrawKeyConstants;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.biz.common.utils.ProtocolUtils;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpmn.module.account.constants.DictTypeConstant;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.constant.CashConstant;
import com.minigod.zero.bpmn.module.constant.WithdrawalsFundMessageConstant;
import com.minigod.zero.bpmn.module.deposit.mapper.FundDepositInfoMapper;
import com.minigod.zero.bpmn.module.deposit.vo.FundDepositInfoVO;
import com.minigod.zero.bpmn.module.feign.ICashBankClient;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.AmountDTO;
import com.minigod.zero.bpmn.module.feign.enums.ThawingType;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountDetailVO;
import com.minigod.zero.bpmn.module.feign.vo.FinancingAccountAmount;
import com.minigod.zero.bpmn.module.feign.vo.ReceivingBankVO;
import com.minigod.zero.bpmn.module.withdraw.bo.*;
import com.minigod.zero.bpmn.module.withdraw.constant.RedisKeyConstants;
import com.minigod.zero.bpmn.module.withdraw.entity.BankPaying;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawApplication;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawInfo;
import com.minigod.zero.bpmn.module.withdraw.enums.BpmCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.entity.DbsRemitReqLog;
import com.minigod.zero.bpmn.module.withdraw.enums.RefundType;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.mapper.ClientFundWithdrawApplicationMapper;
import com.minigod.zero.bpmn.module.withdraw.mapper.ClientFundWithdrawInfoMapper;
import com.minigod.zero.bpmn.module.withdraw.mapper.DbsRemitReqLogMapper;
import com.minigod.zero.bpmn.module.withdraw.service.DbsFundBusinessService;
import com.minigod.zero.bpmn.module.withdraw.service.IBankPayingService;
import com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawApplicationService;
import com.minigod.zero.bpmn.module.withdraw.service.IClientTeltransferInfoService;
import com.minigod.zero.bpmn.module.withdraw.util.WithdrawBusinessUtlis;
import com.minigod.zero.bpmn.module.withdraw.vo.*;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.bpmn.utils.DateUtils;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.dbs.bo.FundTransferEntity;
import com.minigod.zero.dbs.enums.BankApiFuncTypeEnum;
import com.minigod.zero.dbs.protocol.DbsApiProtocol;
import com.minigod.zero.dbs.protocol.TxnEnqResponse;
import com.minigod.zero.dbs.util.DecimalUtils;
import com.minigod.zero.dbs.vo.TseTransactionEnquiryRequestVO;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.constant.ProcessConstant;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.flow.core.utils.FlowUtil;
import com.minigod.zero.flow.core.utils.TaskUtil;
import com.minigod.zero.platform.enums.*;
import com.minigod.zero.platform.utils.EmailUtil;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户出金申请流程信息Service业务层处理
 *
 * @author chenyu
 * @title ClientFundWithdrawApplicationServiceImpl
 * @date 2023-04-04 20:25
 * @description
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClientFundWithdrawApplicationServiceImpl implements IClientFundWithdrawApplicationService {

	private final IFlowClient flowClient;
	private final IDictBizClient idictBizClient;
	private final RedisLockClient redisLockClient;
	private final IBankPayingService bankPayingService;
	private final ICustomerInfoClient customerInfoClient;
	private final ClientFundWithdrawInfoMapper infoMapper;
	private final ICustomerInfoClient iCustomerInfoClient;
	private final AccountOpenInfoMapper accountOpenInfoMapper;
	private final DbsFundBusinessService dbsFundBusinessService;
	private final ClientFundWithdrawApplicationMapper baseMapper;
	private final IClientTeltransferInfoService clientTeltransferInfoService;
	@Autowired
	private ICashBankClient cashBankClient;
	@Autowired
	private IDictBizClient dictBizClient;
	@Autowired
	private FundDepositInfoMapper fundDepositInfoMapper;

	@Autowired
	private DbsRemitReqLogMapper dbsRemitReqLogMapper;


	@Value("${tenant.business.type}")
	private String  businessType;
	/**
	 * 客户取款申请
	 *
	 * @param bo ClientBankcardApplicationBo
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String commitApply(ClientFundWithdrawInfoBo bo) {
		log.info("客户取款申请开始 ClientFundWithdrawInfoBo: {}", JSON.toJSONString(bo));
		// 生成流水号
		String applicationId = ApplicationIdUtil.generateWithdrawApplicationId(StringUtil.isNotBlank(bo.getTenantId()) ? bo.getTenantId() : AuthUtil.getTenantId());
		String fundAccount = bo.getFundAccount();
		String clientId = bo.getClientId();

		BpmTradeAcctRespDto dto = new BpmTradeAcctRespDto();
		dto.setTradeAccount(clientId);
		dto.setCapitalAccount(fundAccount);
		// 设置收款人账户名称 取自客户英文名称 非第三者收款
		if (ObjectUtil.isNull(bo.getThirdRecvFlag()) || SystemCommonEnum.YesNo.YES.getIndex() != bo.getThirdRecvFlag()) {
			bo.setRecvBankAcctName(bo.getClientNameSpell());
		}
		// 校验基本信息
		validEntityBeforeSave(bo);
		// 添加客户取款信息
		ClientFundWithdrawInfo addInfo = BeanUtil.toBean(bo, ClientFundWithdrawInfo.class);
		addInfo.setApplicationId(applicationId);
		addInfo.setIsDeleted(SystemCommonEnum.YesNo.NO.getIndex());
		addInfo.setStatus(BpmCommonEnum.FundWithdrawStatus.AUDIT.getStatus());
		addInfo.setMobile(bo.getPhoneAreaCode() + "-" + bo.getPhoneNumber());
		addInfo.setTenantId(StringUtil.isNotBlank(bo.getTenantId()) ? bo.getTenantId() : AuthUtil.getTenantId());
		// 设置证券账户类型
		addInfo.setRecvAccountType(1);
		// 手续费
		BigDecimal chargeFee = BigDecimal.ZERO;
		if (bo.getChargeFee() != null) {
			chargeFee = bo.getChargeFee();
		}
		// 计算手续费 1-从提取金额中扣除,2-从账户余额中扣除
		// 计算实际到账金额
		BigDecimal withdrawAmount = bo.getWithdrawAmount();
		BigDecimal frozenBalance = null;
		BigDecimal actualAmount = null;
		Integer deductWay = bo.getDeductWay();
		if (deductWay != null && deductWay.equals(SystemCommonEnum.DeductWay.BALANCE.getType())) {
			// 2-从余额中扣除
			frozenBalance = withdrawAmount.add(chargeFee);
			actualAmount = withdrawAmount;
		} else {
			// 1-从提取金额中扣除
			frozenBalance = withdrawAmount;
			actualAmount = withdrawAmount.subtract(chargeFee);
		}
		addInfo.setActualAmount(actualAmount);
		addInfo.setFrozenBalance(frozenBalance);
		addInfo.setChargeFee(chargeFee);
		addInfo.setOldChargeFee(chargeFee);
		addInfo.setDeductWay(deductWay);
		// 取款金额大于手续费
		if (actualAmount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_AMOUNT_ERROR_NOTICE));
		}
		// 取款方式名称
		String transferTypeName = null;
		List<String> businessTypeList = Arrays.asList(businessType.split(","));
		if (businessTypeList.contains(TenantConstant.FUND_TYPE)) {
			// 资金账户 必须冻结
			R<FinancingAccountAmount> result = customerInfoClient.accountAmount(fundAccount, bo.getCcy());
			if (!result.isSuccess()) {
				log.error("调用账户中心接口查询账户可提金额出错了，账户：{}，错误信息：{}", fundAccount, result.getMsg());
				throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_CAN_BE_AMOUNT_ERROR_NOTICE));
			}
			BigDecimal fetchBalance = result.getData().getAvailableAmount();
			//冻结金额不能大于账户余额
			if (fetchBalance.compareTo(frozenBalance) < 0) {
				throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_INSUFFICIENT_AMOUNT_ERROR_NOTICE));
			}

			R<List<DictBiz>> dictWithdrawTransferType = dictBizClient.getList("WITHDRAW_TRANSFER_TYPE");
			if (dictWithdrawTransferType.isSuccess()) {
				List<DictBiz> dictBizData = dictWithdrawTransferType.getData();
				if (dictBizData != null) {
					for (DictBiz d : dictBizData) {
						if (d.getDictKey() != null && bo.getTransferType() != null && d.getDictKey().trim().toString().equals(bo.getTransferType().toString())) {
							transferTypeName = d.getDictValue();
						}
					}
				}
			}
			// 设置可提余额
			addInfo.setDrawableBalance(fetchBalance);
			log.info("客户取款申请请求参数：{}", bo);
			log.info("取款金额：{}，手续费：{}，实际到账金额：{}", withdrawAmount, chargeFee, actualAmount);
			log.info("查询付款银行信息：出金银行ID(withdrawalsId):{},币种：{}, 取款方式：{}, 取款方式名称:{}", bo.getWithdrawalsId(), bo.getCcy(), bo.getTransferType(), transferTypeName);
		}

		String ccy = CashConstant.MoneyTypeDescEnum.getValue(bo.getCcy());
		if (StringUtils.isBlank(ccy)) {
			log.warn("币种未设置：{}", bo.getCcy());
			throw new ServiceException(String.format(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_CURRENCY_TYPE_ERROR_NOTICE), bo.getCcy()));
		}
		// 查询付款银行信息
		R<ReceivingBankVO> receivingBankResult = cashBankClient.findPaymentBankByWithdrawalsBankById(bo.getWithdrawalsId(), ccy, bo.getTransferType().toString());
		if (!receivingBankResult.isSuccess()) {
			throw new ServiceException(String.format(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_SUBMIT_QUERY_RECEIVE_ACCOUNT_ERROR_NOTICE), bo.getCcy(), transferTypeName));
		}
		ReceivingBankVO receivingBank = receivingBankResult.getData();
		if (receivingBank == null) {
			throw new ServiceException(String.format(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_SUBMIT_RECEIVE_ACCOUNT_ERROR_NOTICE), bo.getCcy(), transferTypeName));
		}

		log.info("付款銀行信息：{}", JSONObject.toJSONString(receivingBank));
		if (ObjectUtil.isNotNull(receivingBank)) {
			Long bankId = Long.getLong(receivingBank.getBankId());
			if (bankId == null) {
				log.warn("付款银行ID:{}转换为Long类型失败,返回为空值!", receivingBank.getBankId());
			}
			addInfo.setPayBankId(bankId);
			addInfo.setPayBankCode(receivingBank.getBankCode());
			addInfo.setPayBankName(receivingBank.getBankName());
			addInfo.setPayBankAcct(receivingBank.getAccount());
			addInfo.setPayAccountName(receivingBank.getAccountName());
		}
		addInfo.setCallbackStatus(SystemCommonEnum.CommonProcessResultStatus.COMMON_PROCESS_STATUS_WAIT_VALUE);
		addInfo.setGtDealStatus(SystemCommonEnum.CommonProcessResultStatus.COMMON_PROCESS_STATUS_WAIT_VALUE);
		addInfo.setBankState(SystemCommonEnum.BankStateTypeEnum.UN_COMMNIT.getValue());
		addInfo.setEntrustTime(new Date()); // 委托时间
		// 收付款信息
		log.info("取款申请, data:{}", addInfo);
		int flag = infoMapper.insert(addInfo);
		log.info("取款申请结果, applicationId:{} flag:{}", applicationId, flag);
		if (flag < 1) {
			log.error("取款申请保存失败 : {}", bo);
			throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_APPLICATION_PROCESSING_ERROR_NOTICE));
		}
		// 如果为海外汇款
		if (bo.getTransferType().equals(SystemCommonEnum.TransferTypeEnum.OVERSEAS.getType())) {
			// 添加海外汇款信息
			ClientTeltransferInfoBo addTelBo = null;
			if (null != bo.getTelegram()) {
				addTelBo = BeanUtil.toBean(bo.getTelegram(), ClientTeltransferInfoBo.class);
			} else {
				addTelBo = new ClientTeltransferInfoBo();
			}
			addTelBo.setApplicationId(applicationId);
			addTelBo.setTenantId(StringUtil.isNotBlank(bo.getTenantId()) ? bo.getTenantId() : AuthUtil.getTenantId());
			addTelBo.setClientId(addInfo.getClientId());
			addTelBo.setFundAccount(addInfo.getFundAccount());
			addTelBo.setBankName(addInfo.getRecvBankName());
			addTelBo.setBankCode(addInfo.getRecvBankCode());
			addTelBo.setBankAcct(addInfo.getRecvBankAcct());

			ClientTeltransferInfoBo teltransferInfoBo = bo.getTelegram();
			if (null != teltransferInfoBo) {
				addTelBo.setCityId(teltransferInfoBo.getCityId());
				addTelBo.setProvinceId(teltransferInfoBo.getProvinceId());
				addTelBo.setCountry(teltransferInfoBo.getCountry());
				addTelBo.setCityName(teltransferInfoBo.getCityName());
				addTelBo.setProvinceName(teltransferInfoBo.getProvinceName());
				StringBuffer buffer = new StringBuffer();
				buffer.append(StringUtil.isBlank(teltransferInfoBo.getCountry()) ? "" : teltransferInfoBo.getCountry())
					.append(StringUtil.isBlank(teltransferInfoBo.getProvinceName()) ? "" : teltransferInfoBo.getProvinceName())
					.append(StringUtil.isBlank(teltransferInfoBo.getCityName()) ? "" : teltransferInfoBo.getCityName());
				addTelBo.setAddress(buffer.toString());
			}
			addTelBo.setTenantId(bo.getTenantId());
			// 保存海外汇款信息
			boolean isSuccess = clientTeltransferInfoService.insertByBo(addTelBo);
			if (!isSuccess) {
				log.error("取款申请保存失败: {}", bo);
				throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_APPLICATION_PROCESSING_ERROR_NOTICE));
			}
		}
		// 审批信息
		String name = addInfo.getClientNameSpell();
		if (StringUtils.isEmpty(name)) {
			name = addInfo.getClientName();
		}
		ClientFundWithdrawApplication addApplicaiton = new ClientFundWithdrawApplication();
		addApplicaiton.setApplicationId(applicationId);
		addApplicaiton.setStatus(BpmCommonEnum.FundWithdrawStatus.AUDIT.getStatus());
		addApplicaiton.setApplicationTitle(String.format("[%s取款申请]", name));
		addApplicaiton.setCreateUser(AuthUtil.getUserId());
		addApplicaiton.setTenantId(StringUtil.isNotBlank(bo.getTenantId()) ? bo.getTenantId() : AuthUtil.getTenantId());
		addApplicaiton.setCreateTime(new Date());
		flag = baseMapper.insert(addApplicaiton);
		if (flag < 1) {
			log.error("客户取款申请流程定义信息保存失败 : {}", bo);
			throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_APPLICATION_PROCESSING_ERROR_NOTICE));
		}
		//冻结账户信息
		AmountDTO amountVO = new AmountDTO();
		amountVO.setCustId(AuthUtil.getTenantCustId());
		amountVO.setAmount(frozenBalance);
		amountVO.setCurrency(bo.getCcy());
		amountVO.setAccountId(fundAccount);
		amountVO.setBusinessId(applicationId);
		if (bo.getTransferType().equals(SystemCommonEnum.TransferTypeEnum.OVERSEAS.getType())) {
			amountVO.setThawingType(ThawingType.WIRE_TRANSFER_WITHDRAWAL.getCode());
		}
		if (bo.getTransferType().equals(SystemCommonEnum.TransferTypeEnum.HK.getType())) {
			amountVO.setThawingType(ThawingType.ORDINARY_TRANSFER_WITHDRAWAL.getCode());
		}
		if (bo.getTransferType().equals(SystemCommonEnum.TransferTypeEnum.HK_LOCAL.getType())) {
			amountVO.setThawingType(ThawingType.LOCAL_TRANSFER_WITHDRAWAL.getCode());
		}
		R freezeResult = customerInfoClient.freezeAmount(amountVO);
		if (!freezeResult.isSuccess()){
			log.error("调用账户中心冻结金额失败，账号：{}，错误信息：{}",fundAccount,freezeResult.getMsg());
			throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_WITHDRAWAL_FROZEN_AMOUNT_FAILED_NOTICE));
		}
		return addApplicaiton.getApplicationId();
	}

	@Override
	public String backCommitApply(ClientWithdrawDTO clientWithdraw) {
		paramCheck(clientWithdraw);
		// 暂不使用ClientWithdrawDTO参数中的withdrawalsId
		// 提交出金申请根据bankCode和bankId查询用户绑定的银行卡对应的出金银行主键ID。
		R<Long> withdrawBankIdResult = cashBankClient.withdrawBankId(clientWithdraw.getRecvBankCode(), clientWithdraw.getRecvBankId());
		if (!withdrawBankIdResult.isSuccess()) {
			throw new ZeroException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_QUERY_WITHDRAWAL_BANK_FAILED_NOTICE));
		}
		Long withdrawBankId = withdrawBankIdResult.getData();
		if (withdrawBankId == null) {
			throw new ZeroException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_QUERY_WITHDRAWAL_BANK_CONFIG_FAILED_NOTICE));
		}
		ClientFundWithdrawInfoBo bo = new ClientFundWithdrawInfoBo();
		//个人信息
		bo.setTenantId(AuthUtil.getTenantId());
		bo.setMobile(clientWithdraw.getMobile());
		bo.setClientId(clientWithdraw.getClientId());
		bo.setWithdrawalsId(withdrawBankId);
		bo.setFundAccount(clientWithdraw.getClientId());
		bo.setClientName(clientWithdraw.getClientName());
		bo.setClientNameSpell(clientWithdraw.getClientNameSpell());
		bo.setUserId(clientWithdraw.getCustId());
		String phoneNumber = clientWithdraw.getMobile();
		if (!StringUtil.isBlank(phoneNumber) && phoneNumber.indexOf("-") >= 0) {
			String[] phone = phoneNumber.split("-");
			bo.setPhoneNumber(phone[1]);
			bo.setPhoneAreaCode(phone[0]);
		}
		//提现信息
		bo.setCcy(clientWithdraw.getCurrency());
		bo.setChargeFee(clientWithdraw.getChargeFee());
		bo.setPayWay(clientWithdraw.getTransferType());
		bo.setDeductWay(clientWithdraw.getDeductWay());
		Date entrustTime = null;
		try {
			entrustTime = DateUtils.parseDate(clientWithdraw.getEntrustTime(), DateUtils.YYYY_MM_DD);
		} catch (ParseException e) {
			log.error("出金申请信息提交失败，日期解析异常：{}", e.getMessage());
			throw new ZeroException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_WITHDRAWAL_DATE_EXCEPTION_NOTICE));
		}
		bo.setExportDate(entrustTime);
		bo.setTransferType(clientWithdraw.getTransferType());
		bo.setBankAcctType(clientWithdraw.getBankAcctType());
		bo.setWithdrawAmount(clientWithdraw.getWithdrawAmount());
		bo.setApplySource(SystemCommonEnum.ApplySource.BACK_ADMIN.getType());
		//收款银行账户信息
		bo.setRecvBankName(clientWithdraw.getRecvBankName());
		bo.setRecvBankCode(clientWithdraw.getRecvBankCode());
		bo.setRecvBankAcct(clientWithdraw.getRecvBankAcct());
		bo.setRecvBankId(clientWithdraw.getRecvBankId());
		bo.setRecvBankAcctName(clientWithdraw.getRecvBankAcctName());
		bo.setRecvSwiftCode(clientWithdraw.getRecvSwiftCode());
		bo.setRecvBankType(clientWithdraw.getRecvBankType());
		if (StringUtil.isBlank(clientWithdraw.getRelationshipWithThirdParties())) {
			bo.setRecvBankAcctName(clientWithdraw.getClientNameSpell());
		} else {
			bo.setThirdRecvFlag(1);
			bo.setRecvBankAcctName(clientWithdraw.getRecvBankAcctName());
			bo.setThirdRecvReal(clientWithdraw.getRelationshipWithThirdParties());
			bo.setThirdRecvReason(clientWithdraw.getReasonForReceivingPayment());
		}
		ClientTeltransferInfoBo teltransferInfoBo = new ClientTeltransferInfoBo();
		teltransferInfoBo.setIsVisible("1");
		teltransferInfoBo.setCityId(clientWithdraw.getCityId());
		teltransferInfoBo.setBankCode(clientWithdraw.getRecvBankCode());
		teltransferInfoBo.setBankName(clientWithdraw.getRecvBankName());
		teltransferInfoBo.setSwiftCode(clientWithdraw.getRecvSwiftCode());
		teltransferInfoBo.setProvinceId(clientWithdraw.getProvinceId());
		teltransferInfoBo.setFundAccount(clientWithdraw.getClientId());
		teltransferInfoBo.setBankAcct(clientWithdraw.getRecvBankAcct());
		teltransferInfoBo.setCountry(clientWithdraw.getCountry());
		teltransferInfoBo.setCityName(clientWithdraw.getCityName());
		teltransferInfoBo.setProvinceName(clientWithdraw.getProvinceName());
		teltransferInfoBo.setCityId(clientWithdraw.getCityId());
		teltransferInfoBo.setProvinceId(clientWithdraw.getProvinceId());
		teltransferInfoBo.setCountry(clientWithdraw.getNationality());
		bo.setTelegram(teltransferInfoBo);
		return this.commitApply(bo);
	}


	private void paramCheck(ClientWithdrawDTO clientWithdraw) {
		if (clientWithdraw.getCustId() == null) {
			throw new ZeroException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_WITHDRAWAL_CUSTOMER_ID_ERROR_NOTICE));
		}
		if (StringUtil.isBlank(clientWithdraw.getClientId())) {
			throw new ZeroException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_MANAGE_ACCOUNT_EMPTY_NOTICE));
		}
		if (StringUtil.isBlank(clientWithdraw.getClientName())) {
			throw new ZeroException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_CUSTOMER_NAME_EMPTY_NOTICE));
		}
		if (StringUtil.isBlank(clientWithdraw.getMobile())) {
			throw new ZeroException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_CUSTOMER_MOBILE_EMPTY_NOTICE));
		}
		if (StringUtil.isBlank(clientWithdraw.getCurrency())) {
			throw new ZeroException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_CURRENCY_EMPTY_NOTICE));
		}
		String entrustTime = clientWithdraw.getEntrustTime();
		if (StringUtil.isBlank(entrustTime)) {
			throw new ZeroException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_COMMISSION_TIME_EMPTY_NOTICE));
		}
		if (clientWithdraw.getTransferType() == null) {
			throw new ZeroException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_WITHDRAWAL_METHOD_EMPTY_NOTICE));
		}
		if (clientWithdraw.getWithdrawAmount() == null) {
			throw new ZeroException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_WITHDRAWAL_AMOUNT_EMPTY_NOTICE));
		}
		if (StringUtil.isBlank(clientWithdraw.getRecvBankName())) {
			throw new ZeroException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_RECEIVING_BANK_EMPTY_NOTICE));
		}
		if (StringUtil.isBlank(clientWithdraw.getRecvBankAcct())) {
			throw new ZeroException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_RECEIVING_BANK_ACCOUNT_EMPTY_NOTICE));
		}
	}

	/**
	 * 启动工作流
	 *
	 * @param applicationId
	 */
	@Override
	public void startWithdrawFlow(String applicationId, Integer transferType, int applySource) {
		// 设置流程变量
		Map<String, Object> variables = new HashMap<>();
		variables.put(TaskConstants.TENANT_ID, AuthUtil.getTenantId());
		variables.put(TaskConstants.APPLICATION_ID, applicationId);
		variables.put(TaskConstants.APPLICATION_TABLE, FlowUtil.getBusinessTable(ProcessConstant.WITHDRAW_KEY));
		variables.put(TaskConstants.APPLICATION_DICT_KEY, FlowUtil.getBusinessDictKey(ProcessConstant.WITHDRAW_KEY));
		variables.put(TaskConstants.PROCESS_INITIATOR, TaskUtil.getTaskUser());
		variables.put("transferType", transferType);
		variables.put("applySource", applySource);
		variables.put("isRefund", false);
		R r = flowClient.startProcessInstanceByKey(ProcessConstant.WITHDRAW_KEY, variables);
		if (!r.isSuccess()) {
			throw new ServiceException(r.getMsg());
		}

		try {
			ClientFundWithdrawInfo clientFundWithdrawInfo = infoMapper.queryByApplicationId(applicationId);
			R<CustomerAccountDetailVO> accountDetail = iCustomerInfoClient.selectCustomerDetailByAccountId(clientFundWithdrawInfo.getClientId());
			if (accountDetail.isSuccess()) {
				CustomerAccountDetailVO customerAccountDetailVO = accountDetail.getData();
				PushUtil.builder()
					.msgGroup("P")
					.lang(WebUtil.getLanguage())
					.custId(customerAccountDetailVO.getCustId())
					.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
					.templateCode(PushTemplate.WITHDRAWAL_RECEIVED.getCode())
					.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
					.pushAsync();
				sendWithdrawEmail(applicationId);
			}
		} catch (Exception e) {
			log.error("推送消息失败", e);
		}

	}

	private void sendWithdrawEmail(String applicationId) {

		try {
			ClientFundWithdrawApplicationBo bo = new ClientFundWithdrawApplicationBo();
			bo.setApplicationId(applicationId);
			ClientFundWithdrawInfo clientFundWithdrawInfo = infoMapper.queryByApplicationId(applicationId);
			R<CustomerAccountDetailVO> accountDetail = iCustomerInfoClient.selectCustomerDetailByAccountId(clientFundWithdrawInfo.getClientId());
			if (accountDetail.isSuccess()) {
				CustomerAccountDetailVO accountOpenInfoVO = accountDetail.getData();
				String now = DateUtil.now();
				String name = accountOpenInfoVO.getClientName();
				if (StringUtils.isEmpty(name)) {
					name = accountOpenInfoVO.getGivenNameSpell();
				}
				R<List<DictBiz>> emailAddress = idictBizClient.getListByTenantId(AuthUtil.getTenantId()
					, DictTypeConstant.SYSTEM_EMAIL_ADDRESS);
				//ops发送审核邮件
				List<DictBiz> data = emailAddress.getData();
				List<String> emailList = data.stream().map(DictBiz::getDictKey).collect(Collectors.toList());
				List<String> paramList = new ArrayList<>();
				paramList.add(now);
				paramList.add(name);
				paramList.add(clientFundWithdrawInfo.getRecvBankName());
				paramList.add(ProtocolUtils.bankCardFormatter(clientFundWithdrawInfo.getRecvBankAcct()));
				paramList.add(DecimalUtils.formatDecimal(clientFundWithdrawInfo.getWithdrawAmount()));
				paramList.add(clientFundWithdrawInfo.getCcy());

				List<String> titleList = new ArrayList<>();
				titleList.add(now);
				titleList.add(name);
				String language = WebUtil.getLanguage();
				EmailUtil.builder()
					.titleParams(titleList)
					.paramList(paramList)
					.accepts(emailList)
					.lang(language == null ? LanguageEnum.ZH_CN.getCode() : language)
					.templateCode(EmailTemplate.WITHDRAWAL_REQUEST_NOTIFICATION.getCode())
					.sendAsync();
			}


		} catch (Exception e) {
			log.error("发送邮件失败 {}", e.getMessage());
		}
	}

	/**
	 * 银行付款成功
	 *
	 * @param approvalOption
	 */
	@Override
	public void doCommitNodeByPaySuccess(String applicationId, String approvalOption) {
		// 查询流程应用
		Map<String, Object> variables = new HashMap<>();
		variables.put("isRefund", Boolean.FALSE);
		ClientFundWithdrawApplicationVo applicationVo = queryByApplicationId(applicationId);
		if (applicationVo == null) {
			throw new ServiceException("申请单不存在");
		}
		R r = flowClient.completeTask(applicationVo.getTaskId(), approvalOption, variables);
		if (!r.isSuccess()) {
			throw new ServiceException(r.getMsg());
		}
		updateApplicationStatus(BpmCommonEnum.FundWithdrawStatus.BANK_SUCCESS, applicationId, approvalOption);
		updateInfoStatus(BpmCommonEnum.FundWithdrawStatus.BANK_SUCCESS, applicationId);

		try {
			FundDepositInfoVO fundDepositInfoVO = fundDepositInfoMapper.queryByApplicationId(applicationId);

			List<String> params = new ArrayList<>();
			params.add(applicationId);
			params.add(fundDepositInfoVO.getReceivingAccount());

			PushUtil.builder()
				.msgGroup("P")
				.custId(fundDepositInfoVO.getUserId())
				.params(params)
				.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
				.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
				.templateCode(PushTemplate.WITHDRAWAL_COMPLETE.getCode())
				.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
				.pushAsync();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("推送消息失败", e);
		}
	}

	/**
	 * 更新提款信息状态
	 *
	 * @param status
	 * @param applicationId
	 */
	private void updateInfoStatus(BpmCommonEnum.FundWithdrawStatus status, String applicationId) {
		LambdaUpdateWrapper<ClientFundWithdrawInfo> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.set(ClientFundWithdrawInfo::getStatus, status.getStatus());
		updateWrapper.set(ClientFundWithdrawInfo::getUpdateTime, new Date());
		updateWrapper.set(ClientFundWithdrawInfo::getUpdateUser, AuthUtil.getUserId());
		updateWrapper.eq(ClientFundWithdrawInfo::getApplicationId, applicationId);
		infoMapper.update(null, updateWrapper);
	}

	/**
	 * 更新申请单状态
	 *
	 * @param status
	 * @param applicationId
	 * @param reason
	 */
	private void updateApplicationStatus(BpmCommonEnum.FundWithdrawStatus status, String applicationId, String reason) {
		LambdaUpdateWrapper<ClientFundWithdrawApplication> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.set(ClientFundWithdrawApplication::getStatus, status.getStatus());
		updateWrapper.set(ClientFundWithdrawApplication::getUpdateTime, new Date());
		updateWrapper.set(ClientFundWithdrawApplication::getUpdateUser, AuthUtil.getUserId());
		updateWrapper.set(ClientFundWithdrawApplication::getLastApprovalUser, AuthUtil.getUserName());
		updateWrapper.set(StringUtil.isNotBlank(reason), ClientFundWithdrawApplication::getApprovalOpinion, reason);
		updateWrapper.set(StringUtil.isNotBlank(reason), ClientFundWithdrawApplication::getApprovalTime, new Date());
		updateWrapper.eq(ClientFundWithdrawApplication::getApplicationId, applicationId);
		baseMapper.update(null, updateWrapper);
	}

	/**
	 * 银行付款失败
	 *
	 * @param approvalOption
	 */
	@Override
	public void doCommitNodeByPayFailed(String applicationId, String approvalOption) {
		// 查询流程应用
		Map<String, Object> variables = new HashMap<String, Object>(4);
		variables.put("isRefund", Boolean.TRUE);
		ClientFundWithdrawApplicationVo applicationVo = queryByApplicationId(applicationId);
		if (applicationVo == null) {
			throw new ServiceException("申请单不存在");
		}
		R r = flowClient.completeTask(applicationVo.getTaskId(), approvalOption, variables);
		if (!r.isSuccess()) {
			throw new ServiceException(r.getMsg());
		}
		updateApplicationStatus(BpmCommonEnum.FundWithdrawStatus.BANK_FAIL, applicationId, approvalOption);
		updateInfoStatus(BpmCommonEnum.FundWithdrawStatus.BANK_FAIL, applicationId);
	}

	/**
	 * 提交节点(FPS ID打款失败 拒绝)
	 *
	 * @param approvalOption
	 */
	@Override
	public void doRejectByPayFailed(String applicationId, String approvalOption) {
		ClientFundWithdrawApplicationVo applicationVo = queryByApplicationId(applicationId);
		rejectFlow(applicationId, applicationVo.getTaskId(), approvalOption);
		Map<String, Object> variables = new HashMap<String, Object>(4);
		variables.put("isRefund", Boolean.TRUE);
		R r1 = flowClient.completeTask(applicationVo.getTaskId(), approvalOption, variables);
		if (!r1.isSuccess()) {
			log.error("{} 执行付款节点完成节点失败: {}", applicationId, r1.getMsg());
		} else {
			//更改状态为待退款
			updateApplicationStatus(BpmCommonEnum.FundWithdrawStatus.REFUND, applicationId, approvalOption);
			updateInfoStatus(BpmCommonEnum.FundWithdrawStatus.REFUND, applicationId);
		}

	}

	/**
	 * 业务处理(汇款失败)
	 *
	 * @param fundWithdrawInfoVo
	 * @return
	 */
	@Override
	public void doBusinessAfterPayFailed(ClientFundWithdrawInfo fundWithdrawInfoVo) {
		String applicationId = fundWithdrawInfoVo.getApplicationId();
//         DBS 打款失败 更改付款银行为 SCB
		// 银行提交失败 暂时停止变更付款银行为渣打(20240105)
		String bankCode = "SCB";
		if (StringUtil.isNotBlank(bankCode) && !bankCode.equals(fundWithdrawInfoVo.getPayBankCode())) {
			// 查询默认付款银行信息  默认 SCB
			BankPaying bankPayingVo = WithdrawBusinessUtlis.queryBankPayingEntity(bankCode, fundWithdrawInfoVo.getFundAccount(), fundWithdrawInfoVo.getCcy());
			if (null != bankPayingVo) {
				LambdaUpdateWrapper<ClientFundWithdrawInfo> updateWrapper = new LambdaUpdateWrapper<>();
				updateWrapper.set(ClientFundWithdrawInfo::getPayBankId, bankPayingVo.getId());
				updateWrapper.set(ClientFundWithdrawInfo::getPayBankCode, bankPayingVo.getBankCode());
				updateWrapper.set(ClientFundWithdrawInfo::getPayBankName, bankPayingVo.getBankName());
				updateWrapper.set(ClientFundWithdrawInfo::getPayBankAcct, bankPayingVo.getBankAcct());
				updateWrapper.set(ClientFundWithdrawInfo::getPayAccountName, bankPayingVo.getBankAcctName());
				updateWrapper.eq(ClientFundWithdrawInfo::getApplicationId, applicationId);
				infoMapper.update(null, updateWrapper);
				log.info("DBS打款失败，查询付款银行, 流水号：{} 付款银行:{}", fundWithdrawInfoVo.getApplicationId(), bankPayingVo);
			}
			updateApplicationStatus(BpmCommonEnum.FundWithdrawStatus.BANK_FAIL, applicationId, "DBS打款失败，请手工汇款");
			updateInfoStatus(BpmCommonEnum.FundWithdrawStatus.BANK_FAIL, applicationId);
		}

		// 若为 FPS ID(GPP/PPP/GCP) 付款方式 则拒绝付款
		if (fundWithdrawInfoVo.getPayWay().equals(SystemCommonEnum.TransferTypeEnum.FPSID.getType())) {
			//走退款
			doRejectByPayFailed(applicationId, "DBS打款失败,自动退款");
		}
	}


	/**
	 * 批量审批通过
	 *
	 * @param applicationIds
	 * @param reason
	 * @return
	 */
	@Override
	public String passNode(String[] applicationIds, String[] taskIds, String reason) {
		if (null == applicationIds || applicationIds.length == 0) {
			throw new ServiceException("请选择记录");
		}
		if (null == taskIds || taskIds.length == 0) {
			throw new ServiceException("请选择记录");
		}
		if (applicationIds.length != taskIds.length) {
			throw new ServiceException("流水ID和任务ID长度不对应");
		}
		List<String> applicationIdList = Arrays.asList(applicationIds);
		// 查询应用信息
		ClientFundWithdrawApplicationBo queryEntity = new ClientFundWithdrawApplicationBo();
		queryEntity.setApplicationIdList(applicationIdList);
		List<ClientFundWithdrawApplication> applicationVOs = this.queryList(queryEntity);
		if (null == applicationVOs || applicationVOs.size() == 0) {
			throw new ServiceException("所选任务未找到记录");
		}

		List<String> failList = new ArrayList<String>(applicationVOs.size());
		Integer iSuccessCount = 0;
		Integer iFailCount = 0;
		int index = 0;
		for (ClientFundWithdrawApplication application : applicationVOs) {
			try {
				if (!application.getTaskId().equals(taskIds[index])) {
					failList.add(application.getApplicationId() + "-不在当前节点");
					iFailCount++;
					continue;
				}
				doPassNode(taskIds[index], reason);
				if (application.getApplicationStatus() == 4) {
					ClientFundWithdrawApplication clientFundWithdrawApplication = new ClientFundWithdrawApplication();
					clientFundWithdrawApplication.setId(application.getId());
					clientFundWithdrawApplication.setUpdateTime(new Date());
					clientFundWithdrawApplication.setUpdateUser(AuthUtil.getUserId());
					clientFundWithdrawApplication.setLastApprovalUser(AuthUtil.getUserName());
					clientFundWithdrawApplication.setStatus(BpmCommonEnum.FundWithdrawStatus.COUNTER_WITHDRAW.getStatus());
					baseMapper.updateById(clientFundWithdrawApplication);

					LambdaUpdateWrapper<ClientFundWithdrawInfo> updateWrapper = new LambdaUpdateWrapper<>();
					updateWrapper.set(ClientFundWithdrawInfo::getStatus, BpmCommonEnum.FundWithdrawStatus.COUNTER_WITHDRAW.getStatus());
					updateWrapper.set(ClientFundWithdrawInfo::getUpdateTime, new Date());
					updateWrapper.set(ClientFundWithdrawInfo::getUpdateUser, AuthUtil.getUserId());
					updateWrapper.eq(ClientFundWithdrawInfo::getApplicationId, application.getApplicationId());
					infoMapper.update(null, updateWrapper);

					try {
						ClientFundWithdrawInfo clientFundWithdrawInfo = infoMapper.queryByApplicationId(application.getApplicationId());
						List<String> params = new ArrayList<>();
						params.add(clientFundWithdrawInfo.getPayBankName());
						params.add(application.getApplicationId());

						PushUtil.builder()
							.msgGroup("P")
							.custId(clientFundWithdrawInfo.getUserId())
							.params(params)
							.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
							.templateCode(PushTemplate.BATCH_WITHDRAWAL_REQUEST_SUCCESS.getCode())
							.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
							.pushAsync();
					} catch (Exception e) {
						log.error("推送消息失败", e);
					}
				}
				iSuccessCount++;
				index++;
			} catch (Exception e) {
				log.error("审批失败:{}", application.getApplicationId(), e);
				failList.add(application.getApplicationId());
				iFailCount++;
			}
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("成功:" + iSuccessCount + "条<br/>");
		stringBuilder.append("失败:" + iFailCount + "条<br/>");
		if (null != failList && failList.size() > 0) {
			failList.stream().forEach(mapper -> {
				stringBuilder.append(mapper).append("<br/>");
			});
		}

		return stringBuilder.toString();
	}

	/**
	 * 通过节点
	 *
	 * @param taskId
	 * @param reason
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void doPassNode(String taskId, String reason) {
		// 执行流程
		Map<String, Object> variables = new HashMap<String, Object>(3);
		R r = flowClient.completeTask(taskId, reason, variables);
		if (!r.isSuccess()) {
			throw new ServiceException(r.getMsg());
		}
	}

	/**
	 * 取消取款申请
	 *
	 * @param fundWithdrawApplicationVo
	 * @param reason
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cancelFlow(ClientFundWithdrawApplication fundWithdrawApplicationVo, ClientFundWithdrawInfo fundWithdrawInfoVo, String reason) {

		if (ObjectUtil.isNull(fundWithdrawApplicationVo)) {
			log.error("取消取款申请 记录不存在, data:{}", fundWithdrawApplicationVo.getApplicationId());
			throw new ServiceException("取款申请不存在");
		}

		if (!fundWithdrawApplicationVo.getStatus().equals(BpmCommonEnum.FundWithdrawStatus.AUDIT)) {
			log.error("取款申请已审批完成, data:{}", fundWithdrawApplicationVo.getApplicationId());
			throw new ServiceException("取款申请已审批完成无法取消");
		}

		R r = flowClient.taskFinish(fundWithdrawApplicationVo.getInstanceId(), FlowComment.STOP.getType(), reason);
		if (!r.isSuccess()) {
			throw new ServiceException(r.getMsg());
		}
	}

	/**
	 * 拒绝（单条拒绝）
	 *
	 * @param applicationId
	 * @param reason
	 * @return
	 */
	@Override
	public void rejectFlow(String applicationId, String taskId, String reason) {
		if (StringUtil.isBlank(applicationId)) {
			log.error("取消审批 流水号不能为空, data:{}", applicationId);
			throw new ServiceException("流水号不能为空");
		}
		if (StringUtil.isBlank(taskId)) {
			log.error("取消审批 任务编号不能为空, data:{}", applicationId);
			throw new ServiceException("任务编号不能为空");
		}
		ClientFundWithdrawApplicationVo applicationVo = queryByApplicationId(applicationId);
		if (applicationVo == null) {
			log.error("取消审批申请单不存在, data:{}", applicationId);
			throw new ServiceException("申请单不存在");
		} else {
			if (applicationVo.getApplicationStatus() > BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_AUDIT_VALUE) {
				throw new ServiceException("该取款申请已审批完成");
			}
		}


		// 执行流程终止
		R r = flowClient.taskFinish(applicationVo.getInstanceId(), FlowComment.REJECT.getType(), reason);
		if (!r.isSuccess()) {
			log.error("取消审批流程终止失败, taskId:{} - msg:{}", taskId, r.getMsg());
			throw new ServiceException("取消审批流程终止失败");
		}
		updateApplicationStatus(BpmCommonEnum.FundWithdrawStatus.REJECT, applicationVo.getApplicationId(), reason);
		//updateInfoStatus(BpmCommonEnum.FundWithdrawStatus.REJECT, applicationVo.getApplicationId());
		ClientFundWithdrawInfo info = infoMapper.queryByApplicationId(applicationId);

		//解冻资金
		AmountDTO outAmount = new AmountDTO();
		outAmount.setAmount(info.getFrozenBalance());
		outAmount.setCurrency(info.getCcy());
		outAmount.setAccountId(info.getClientId());
		outAmount.setBusinessId(info.getApplicationId());
		outAmount.setThawingType(ThawingType.WITHDRAWALS_REFUSED_RETURN.getCode());
		outAmount.setType(1);
		outAmount.setCustId(info.getUserId());
		log.info("iCustomerInfoClient.thawingAmount query:{}", JSON.toJSONString(outAmount));
		R result = iCustomerInfoClient.thawingAmount(outAmount);
		log.info("iCustomerInfoClient.thawingAmount result:{}", JSON.toJSONString(outAmount));
		if (!result.isSuccess()) {
			log.error("调用账户中心解冻金额失败，错误信息：" + result.getMsg());
			throw new ServiceException("解冻失败！");
		}

		ClientFundWithdrawInfo withdrawInfo = new ClientFundWithdrawInfo();
		withdrawInfo.setId(info.getId());
		withdrawInfo.setStatus(BpmCommonEnum.FundWithdrawStatus.REJECT.getStatus());
		withdrawInfo.setRefundedDate(new Date());
		withdrawInfo.setRefundedAmount(info.getFrozenBalance());
		withdrawInfo.setUpdateTime(new Date());
		infoMapper.updateById(withdrawInfo);

		try {
			ClientFundWithdrawInfo clientFundWithdrawInfo = infoMapper.queryByApplicationId(applicationId);
			List<String> params = new ArrayList<>();
			params.add(applicationId);
			params.add(reason);
			params.add(dictBizClient.getValue("company", "CO_Phone_HK").getData());

			PushUtil.builder()
				.msgGroup("P")
				.custId(clientFundWithdrawInfo.getUserId())
				.params(params)
				.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
				.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
				.templateCode(PushTemplate.WITHDRAWAL_FAIL.getCode())
				.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
				.pushAsync();
		} catch (Exception e) {
			log.error("推送消息失败", e);
		}

}


	/**
	 * 设置打印标记
	 *
	 * @param applicationIds
	 * @return
	 */
	@Override
	public String setPrintFlag(String[] applicationIds) {
		if (null == applicationIds || applicationIds.length == 0) {
			throw new ServiceException("请选择记录");
		}
		List<String> applicationIdList = Arrays.asList(applicationIds);
		// 查询应用信息
		ClientFundWithdrawInfoBo queryEntity = new ClientFundWithdrawInfoBo();
		queryEntity.setApplicationIdList(applicationIdList);
		LambdaQueryWrapper<ClientFundWithdrawInfo> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(ClientFundWithdrawInfo::getApplicationId, applicationIdList);
		List<ClientFundWithdrawInfo> applicationVOs = infoMapper.selectList(queryWrapper);
		if (null == applicationVOs || applicationVOs.size() == 0) {
			throw new ServiceException("所选任务未找到记录");
		}
		List<String> failList = new ArrayList<String>(applicationVOs.size());
		Integer iSuccessCount = 0;
		Integer iFailCount = 0;
		R result = null;
		for (ClientFundWithdrawInfo fundWithdrawInfoVo : applicationVOs) {
			try {
				// 已设置标记
				if (fundWithdrawInfoVo.getPrintDate() != null
					&& fundWithdrawInfoVo.getPrintStatus() != null
					&& fundWithdrawInfoVo.getPrintStatus() == SystemCommonEnum.YesNo.YES.getIndex()) {
					failList.add(fundWithdrawInfoVo.getApplicationId() + "-已被标记");
					iFailCount++;
					continue;
				}
				// 执行打印
				try {
					doSetPrintFlag(fundWithdrawInfoVo);
					iSuccessCount++;
				} catch (ServiceException e) {
					failList.add(fundWithdrawInfoVo.getApplicationId() + "-" + result.getMsg());
					iFailCount++;
				}
			} catch (Exception e) {
				log.error("设置打印标记失败:{}", fundWithdrawInfoVo.getApplicationId(), e);
				failList.add(fundWithdrawInfoVo.getApplicationId() + "-设置打印标记失败");
				iFailCount++;
			}
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("成功:" + iSuccessCount + "条<br/>");
		stringBuilder.append("失败:" + iFailCount + "条<br/>");
		if (null != failList && failList.size() > 0) {
			failList.stream().forEach(mapper -> {
				stringBuilder.append(mapper).append("<br/>");
			});
		}
		return stringBuilder.toString();
	}

	/**
	 * 执行设置打印标记
	 *
	 * @param fundWithdrawInfoVo
	 * @return
	 */
	//@Transactional(rollbackFor = Exception.class)
	public void doSetPrintFlag(ClientFundWithdrawInfo fundWithdrawInfoVo) {

		// 查询应用流程
		ClientFundWithdrawApplicationVo fundWithdrawApplicationVo = queryByApplicationId(fundWithdrawInfoVo.getApplicationId());
		// 判断流程状态
		if (fundWithdrawApplicationVo.getApplicationStatus() != BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_REFUND_VALUE) {
			log.error("流程状态非DBS打款失败，操作异常 data:{}", fundWithdrawInfoVo.getApplicationId());
			throw new ServiceException("非退款节点，不能设置打印标记");
		}

		// 查看银行执行状态
		if (fundWithdrawInfoVo.getBankState() == null || fundWithdrawInfoVo.getBankState() == SystemCommonEnum.BankStateTypeEnum.UN_COMMNIT.getValue()) {
			log.error("DBS未打款，操作异常 data:{}", fundWithdrawInfoVo.getApplicationId());
			throw new ServiceException("DBS未打款，不能设置打印标记");
		}


		Map<String, Object> params = new HashMap<>();
		params.put("isRefund", false);
		R r = flowClient.completeTask(fundWithdrawApplicationVo.getTaskId(), FlowComment.NORMAL.getType(), params);
		if (r.isSuccess()) {
			// 更新标记
			LambdaUpdateWrapper<ClientFundWithdrawInfo> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.set(ClientFundWithdrawInfo::getPrintStatus, SystemCommonEnum.YesNo.YES.getIndex());
			updateWrapper.set(ClientFundWithdrawInfo::getPrintDate, new Date());
			updateWrapper.eq(ClientFundWithdrawInfo::getApplicationId, fundWithdrawInfoVo.getApplicationId());
			infoMapper.update(null, updateWrapper);
			updateInfoStatus(BpmCommonEnum.FundWithdrawStatus.BANK_SUCCESS, fundWithdrawInfoVo.getApplicationId());
			updateApplicationStatus(BpmCommonEnum.FundWithdrawStatus.BANK_SUCCESS, fundWithdrawInfoVo.getApplicationId(), "线下汇款成功");
		}
	}

	/**
	 * 刷新银行交易状态
	 *
	 * @param applicationId
	 * @return
	 */
	//@Transactional(rollbackFor = Exception.class)
	@Override
	public void refreshBankState(String applicationId) {
		// 查询取款信息
		ClientFundWithdrawInfo withdrawInfoVo = infoMapper.queryByApplicationId(applicationId);

		// TSE 只需要支持 ACT RTGS TT 汇款支付查询
		// 取自付款方式
		Integer payType = withdrawInfoVo.getPayType();
		if (payType != null) {
			if (payType != SystemCommonEnum.PayTypeEnum.DBS_ACT.getIndex()
				&& payType != SystemCommonEnum.PayTypeEnum.DBS_RTGS.getIndex()
				&& payType != SystemCommonEnum.PayTypeEnum.DBS_TT.getIndex()) {
				log.info("刷新取款付款状态，非RTGS/TT支付通道无需更新, 预约号: {}, payType: {}", withdrawInfoVo.getApplicationId(), payType);
				throw new ServiceException(SystemCommonEnum.CodeType.DBS_REMIT_NONE_REMITTANCE.getMessage());
			}
		} else {
			// 付款未提交
			throw new ServiceException(SystemCommonEnum.CodeType.DBS_REMIT_UNCOMMIT.getMessage());
		}

		// 校验是否已发送银行请求 银行已提交或处理中
		if (withdrawInfoVo.getBankState() != null &&
			(withdrawInfoVo.getBankState() == SystemCommonEnum.BankStateTypeEnum.COMMITTED.getValue() ||
				withdrawInfoVo.getBankState() == SystemCommonEnum.BankStateTypeEnum.PROCESSING.getValue())) {
			// 锁相关
			RLock rLock = null;
			String redisLockKey = RedisKeyConstants.LOCK_DBS_REMIT_ACK_KEY_PREFIX + withdrawInfoVo.getApplicationId();
			boolean isLock = false;

			try {
				isLock = redisLockClient.tryLock(redisLockKey, LockType.REENTRANT, 1, 1, TimeUnit.MILLISECONDS);
				log.info("RefreshBankState获取交易锁: {} ,预约号: {}, Result: {}", redisLockKey, withdrawInfoVo.getApplicationId(), isLock);
				if (isLock) {
					// 发送银行请求
					TseTransactionEnquiryRequestVO request = new TseTransactionEnquiryRequestVO();
					request.setEnqAccountNo(withdrawInfoVo.getPayBankAcct());
					request.setCustomerReference(withdrawInfoVo.getBankReference());
					request.setTxnRefId(withdrawInfoVo.getBankRefId());
					request.setEnqType("REM");// 汇款查询
					request.setTenantId(withdrawInfoVo.getTenantId());
					request.setTxnAmount(withdrawInfoVo.getActualAmount());
					request.setTxnCcy(withdrawInfoVo.getCcy());
					R<TxnEnqResponse> result = dbsFundBusinessService.sendTseQuery(request);
					if (result.isSuccess()) {
						// 校验
						TxnEnqResponse txnEnqResponse = result.getData();
						String txnStatus = txnEnqResponse.getTxnStatus();
						String txnSettlementAmt = txnEnqResponse.getTxnSettlementAmt();
						String txnSettlementDt = txnEnqResponse.getTxnSettlementDt();
						String txnRefId = txnEnqResponse.getTxnRefId();
						String bankReference = txnEnqResponse.getBankReference();

						if (SystemCommonEnum.BankRespStatus.ACSP.getType().equals(txnStatus)
							|| SystemCommonEnum.BankRespStatus.ACWC.getType().equals(txnStatus)) {
							// 已结算
							if (StringUtil.isNotBlank(txnSettlementAmt) && StringUtil.isNotBlank(txnSettlementDt)) {
								// 交易成功
								int bankState = SystemCommonEnum.BankStateTypeEnum.SUCCESS.getValue();
								// 成功业务处理
								LambdaUpdateWrapper<ClientFundWithdrawInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper();
								lambdaUpdateWrapper.eq(ClientFundWithdrawInfo::getApplicationId, withdrawInfoVo.getApplicationId());
								lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getBankRefId, txnRefId);
								lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getBankReference, bankReference);
								lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getBankState, bankState);
								lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getUpdateTime, new Date());

								infoMapper.update(null, lambdaUpdateWrapper);
								log.info("RefreshBankState银行执行成功,更新银行状态, 流水号：{} bankStata:{} ", withdrawInfoVo.getApplicationId(), bankState);

								// 执行下一步
								String approvalOption = "DBS付款成功";
								doCommitNodeByPaySuccess(withdrawInfoVo.getApplicationId(), approvalOption);
								log.info("RefreshBankState银行执行成功, 流程审批完成。流水号:{}", withdrawInfoVo.getApplicationId());
							} else {
								// 交易失败
								if (StringUtil.isBlank(txnEnqResponse.getTxnRejectCode())) {
									txnEnqResponse.setTxnRejectCode(String.valueOf(SystemCommonEnum.CodeType.DBS_REMIT_TSE_FAILED_NO_SETTLEMENT.getCode()));
								}
								if (StringUtil.isBlank(txnEnqResponse.getTxnStatusDescription())) {
									txnEnqResponse.setTxnStatusDescription(SystemCommonEnum.CodeType.DBS_REMIT_TSE_FAILED_NO_SETTLEMENT.getMessage());
								}
								dbsRemitFailed(txnEnqResponse, withdrawInfoVo, txnRefId, bankReference);
								log.info("RefreshBankState银行执行付款失败, 未完成结算结算日期或结算金额为空, 流水号：{}", withdrawInfoVo.getApplicationId());
							}

						} else if (SystemCommonEnum.BankRespStatus.ACCP.getType().equals(txnStatus)) {
							// 前提条件为: 已提交
							if (withdrawInfoVo.getBankState() == SystemCommonEnum.BankStateTypeEnum.COMMITTED.getValue()) {
								// 处理中
								int bankState = SystemCommonEnum.BankStateTypeEnum.PROCESSING.getValue();
								// 成功业务处理
								LambdaUpdateWrapper<ClientFundWithdrawInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper();
								lambdaUpdateWrapper.eq(ClientFundWithdrawInfo::getApplicationId, withdrawInfoVo.getApplicationId());
								lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getBankRefId, txnRefId);
								lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getBankReference, bankReference);
								lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getBankState, bankState);
								lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getUpdateTime, new Date());
								infoMapper.update(null, lambdaUpdateWrapper);
								log.info("RefreshBankState银行执行成功,更新银行状态, 流水号：{} bankStata:{} ", withdrawInfoVo.getApplicationId(), bankState);
							}
						} else if (SystemCommonEnum.BankRespStatus.RJCT.getType().equals(txnStatus)) {
							// 交易失败
							dbsRemitFailed(txnEnqResponse, withdrawInfoVo, txnRefId, bankReference);
						}
					} else {
						throw new ServiceException(result.getMsg());
					}
				}
			} catch (Exception e) {
				log.error("RefreshBankState异常", e);
			} finally {
				if (null != rLock && isLock) {
					if (rLock.isHeldByCurrentThread()) {
						log.info("RefreshBankState解锁: {} ,预约号: {}, Result: {}", redisLockKey, withdrawInfoVo.getApplicationId(), isLock);
						rLock.unlock();
					}
				}
			}
		} else if (withdrawInfoVo.getBankState() != null && withdrawInfoVo.getBankState() == SystemCommonEnum.BankStateTypeEnum.UN_COMMNIT.getValue()) {
			// 银行未提交
			throw new RuntimeException(SystemCommonEnum.CodeType.DBS_REMIT_UNCOMMIT.getMessage());
		} else if (withdrawInfoVo.getBankState() != null && (withdrawInfoVo.getBankState() == SystemCommonEnum.BankStateTypeEnum.FAIL.getValue()
			|| withdrawInfoVo.getBankState() == SystemCommonEnum.BankStateTypeEnum.SUCCESS.getValue())) {
			// 银行未提交
			throw new RuntimeException(SystemCommonEnum.CodeType.DBS_REMIT_PROCESSED.getMessage());
		}
	}

	private void dbsRemitFailed(TxnEnqResponse txnEnqResponse, ClientFundWithdrawInfo withdrawInfoVo, String txnRefId, String bankReference) {
		// 交易失败
		int bankState = SystemCommonEnum.BankStateTypeEnum.FAIL.getValue();
		String txnRejectCode = txnEnqResponse.getTxnRejectCode();
		String txnStatusDescription = txnEnqResponse.getTxnStatusDescription();
		LambdaUpdateWrapper<ClientFundWithdrawInfo> lambdaUpdateWrapper = new LambdaUpdateWrapper();
		lambdaUpdateWrapper.eq(ClientFundWithdrawInfo::getApplicationId, withdrawInfoVo.getApplicationId());
		lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getBankRefId, txnRefId);
		lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getBankReference, bankReference);
		lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getBankState, bankState);
		lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getBankRtCode, txnRejectCode);
		lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getBankRtMsg, txnStatusDescription);
		lambdaUpdateWrapper.set(ClientFundWithdrawInfo::getUpdateTime, new Date());

		infoMapper.update(null, lambdaUpdateWrapper);
		log.info("RefreshBankState银行执行失败,更新银行状态, 流水号：{} bankStata:{} ankRtCode:{} BankMsg：{}", withdrawInfoVo.getApplicationId(), bankState,
			txnRejectCode, txnStatusDescription);

		// 失败更新业务状态
		if (bankState == SystemCommonEnum.BankStateTypeEnum.FAIL.getValue()) {
			ClientFundWithdrawApplicationBo fundWithdrawApplicationBo = new ClientFundWithdrawApplicationBo();
			fundWithdrawApplicationBo.setApplicationId(withdrawInfoVo.getApplicationId());
			fundWithdrawApplicationBo.setApplicationStatus(BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_REFUND_VALUE);
			fundWithdrawApplicationBo.setUpdateUser(AuthUtil.getUserId());
			fundWithdrawApplicationBo.setUpdateTime(new Date());
			log.info("RefreshBankState银行执行失败,更新流程状态, 流水号：{} bankStata:{} applicationStatus:{}", withdrawInfoVo.getApplicationId(), bankState,
				fundWithdrawApplicationBo.getApplicationStatus());
			updateClientFundWithdrawApplication(fundWithdrawApplicationBo);
		}

		// Dbs付款失败 业务处理 完成汇款节点，后续 人工打印或退款申请处理
		String approvalOption = "DBS付款失败";
		doCommitNodeByPayFailed(withdrawInfoVo.getApplicationId(), approvalOption);

		// 发送邮件通知
//        WithdrawNoticeHelper.sendDbsPaySystemEmail(withdrawInfoVo, txnRejectCode, txnStatusDescription);
	}

	/**
	 * 修改客户取款申请信息
	 *
	 * @param bo 客户取款申请信息
	 * @return 结果
	 */
	@Override
	public Boolean updateClientFundWithdrawApplication(ClientFundWithdrawApplicationBo bo) {
		if (StringUtil.isBlank(bo.getApplicationId()) && bo.getId() == null) {
			return false;
		}
		ClientFundWithdrawApplication update = BeanUtil.toBean(bo, ClientFundWithdrawApplication.class);
		return baseMapper.updateClientFundWithdrawApplication(update) > 0;
	}

	/**
	 * 查询客户取款申请
	 *
	 * @param applicationId 申请流水号
	 * @return 客户取款申请
	 */
	@Override
	public ClientFundWithdrawApplicationVo queryByApplicationId(String applicationId) {
		ClientFundWithdrawApplicationBo bo = new ClientFundWithdrawApplicationBo();
		bo.setApplicationId(applicationId);
		ClientFundWithdrawApplicationVo clientFundWithdrawApplicationVo = baseMapper.queryByApplicationId(bo);
		if (Objects.equals(clientFundWithdrawApplicationVo.getStatus(), BpmCommonEnum.FundWithdrawStatus.BANK_FAIL.getStatus())) {
			//查找失败原因 日志表
			DbsRemitReqLog dbsRemitReqLog = dbsRemitReqLogMapper.selectOne(
				new QueryWrapper<DbsRemitReqLog>().eq("application_id", applicationId)
					.orderByDesc("create_time")
					.last("limit 1"));
			if (null != dbsRemitReqLog) {
				ClientFundWithdrawInfoVo info = clientFundWithdrawApplicationVo.getInfo();
				info.setBankMsg(dbsRemitReqLog.getRejDescription());
				info.setBankRtCode(dbsRemitReqLog.getRejCode());
				clientFundWithdrawApplicationVo.setInfo(info);
			}
		}
		return clientFundWithdrawApplicationVo;
	}

	/**
	 * 查询客户出金申请详细信息
	 *
	 * @param bo
	 * @return 客户出金申请
	 */
	@Override
	public IPage<ClientFundWithdrawApplicationVo> queryDetailInfoList(ClientFundWithdrawApplicationBo bo, IPage pageQuery) {
		IPage<ClientFundWithdrawApplicationVo> result = baseMapper.queryDetailPageList(pageQuery, bo);
		return result;
	}

	@Override
	public List<ClientFundWithdrawApplicationVo> queryDetailInfoList(ClientFundWithdrawApplicationBo bo) {
		List<ClientFundWithdrawApplicationVo> result = baseMapper.queryDetailList(bo);
		return result;
	}

	/**
	 * 查询记录数
	 *
	 * @param bo
	 * @return
	 */
//    @Override
//    public Long queryListCount(ClientFundWithdrawApplicationBo bo) {
//        LambdaQueryWrapper<ClientFundWithdrawApplication> lqw = buildQueryWrapper(bo);
//        return baseMapper.selectCount(lqw);
//    }

	/**
	 * 查询客户银行卡登记列表
	 *
	 * @param bo 客户银行卡登记
	 * @return 客户银行卡登记
	 */
	@Override
	public IPage<ClientFundWithdrawApplicationVo> queryPageList(ClientFundWithdrawApplicationBo bo, IPage pageQuery) {
		IPage<ClientFundWithdrawApplicationVo> result = baseMapper.queryPageList(pageQuery, bo);
		return result;
	}

	/**
	 * 查询客户银行卡登记列表（撤单）
	 *
	 * @param bo 客户银行卡登记
	 * @return 客户银行卡登记
	 */
	@Override
	public IPage<ClientFundWithdrawApplicationVo> queryRefundPageList(ClientFundWithdrawApplicationBo bo, IPage pageQuery) {
		IPage<ClientFundWithdrawApplicationVo> result = baseMapper.queryRefundPageList(pageQuery, bo);
		return result;
	}

	/**
	 * 查询客户出金申请流程信息
	 *
	 * @param id 客户出金申请流程信息主键
	 * @return 客户出金申请流程信息
	 */
	@Override
	public ClientFundWithdrawApplication queryById(Long id) {
		return baseMapper.selectById(id);
	}


	/**
	 * 查询客户出金申请流程信息列表
	 *
	 * @param bo 客户出金申请流程信息
	 * @return 客户出金申请流程信息
	 */
	@Override
	public List<ClientFundWithdrawApplication> queryList(ClientFundWithdrawApplicationBo bo) {
		LambdaQueryWrapper<ClientFundWithdrawApplication> lqw = buildQueryWrapper(bo);
		return baseMapper.selectList(lqw);
	}

	private LambdaQueryWrapper<ClientFundWithdrawApplication> buildQueryWrapper(ClientFundWithdrawApplicationBo bo) {
		LambdaQueryWrapper<ClientFundWithdrawApplication> lqw = Wrappers.lambdaQuery();
		lqw.eq(StringUtil.isNotBlank(bo.getApplicationId()), ClientFundWithdrawApplication::getApplicationId, bo.getApplicationId());
		lqw.eq(StringUtil.isNotBlank(bo.getApplicationTitle()), ClientFundWithdrawApplication::getApplicationTitle, bo.getApplicationTitle());
		lqw.eq(bo.getStatus() != null, ClientFundWithdrawApplication::getStatus, bo.getStatus());
		lqw.eq(StringUtil.isNotBlank(bo.getCurrentNode()), ClientFundWithdrawApplication::getCurrentNode, bo.getCurrentNode());
		lqw.eq(StringUtil.isNotBlank(bo.getLastApprovalUser()), ClientFundWithdrawApplication::getLastApprovalUser, bo.getLastApprovalUser());
		lqw.eq(StringUtil.isNotBlank(bo.getApprovalOpinion()), ClientFundWithdrawApplication::getApprovalOpinion, bo.getApprovalOpinion());
		lqw.eq(bo.getApprovalTime() != null, ClientFundWithdrawApplication::getApprovalTime, bo.getApprovalTime());
		lqw.eq(bo.getCallbackStatus() != null, ClientFundWithdrawApplication::getCallbackStatus, bo.getCallbackStatus());
		lqw.eq(bo.getApplicationStatus() != null, ClientFundWithdrawApplication::getApplicationStatus, bo.getApplicationStatus());
		lqw.eq(bo.getIsBack() != null, ClientFundWithdrawApplication::getIsBack, bo.getIsBack());
		lqw.eq(StringUtil.isNotBlank(bo.getAssignDrafter()), ClientFundWithdrawApplication::getAssignDrafter, bo.getAssignDrafter());
		lqw.eq(StringUtil.isNotBlank(bo.getFlowPath()), ClientFundWithdrawApplication::getFlowPath, bo.getFlowPath());
		lqw.eq(bo.getFireAid() != null, ClientFundWithdrawApplication::getFireAid, bo.getFireAid());
		lqw.eq(StringUtil.isNotBlank(bo.getInstanceId()), ClientFundWithdrawApplication::getInstanceId, bo.getInstanceId());
		lqw.eq(StringUtil.isNotBlank(bo.getDefinitionId()), ClientFundWithdrawApplication::getDefinitionId, bo.getDefinitionId());
		lqw.eq(StringUtil.isNotBlank(bo.getProcessInstanceId()), ClientFundWithdrawApplication::getProcessInstanceId, bo.getProcessInstanceId());
		lqw.eq(StringUtil.isNotBlank(bo.getTaskId()), ClientFundWithdrawApplication::getTaskId, bo.getTaskId());
		lqw.eq(StringUtil.isNotBlank(bo.getDeployId()), ClientFundWithdrawApplication::getDeployId, bo.getDeployId());
		lqw.in(ObjectUtil.isNotNull(bo.getApplicationStatusList()) && bo.getApplicationStatusList().size() > 0, ClientFundWithdrawApplication::getApplicationStatus, bo.getApplicationStatusList());
		lqw.in(ObjectUtil.isNotNull(bo.getApplicationIdList()) && bo.getApplicationIdList().size() > 0, ClientFundWithdrawApplication::getApplicationId, bo.getApplicationIdList());
		return lqw;
	}

	/**
	 * 统计银行取款报表
	 *
	 * @param bo
	 * @return
	 */
	@Override
	public List<WithdrawlBankSummaryVo> queryBankSummaryReportInfo(ClientFundWithdrawApplicationBo bo) {
		List<WithdrawlBankSummaryVo> lstData = baseMapper.queryBankSummaryReportInfo(bo);
		if (null != lstData && lstData.size() > 0) {
			lstData.stream().map(mappper -> {
				BankPayingBo queryBankPayingBo = new BankPayingBo();
				queryBankPayingBo.setBankCode(mappper.getPayBankCode());
				queryBankPayingBo.setActive(SystemCommonEnum.YesNo.YES.getIndex());
				BankPaying bankPayingVo = bankPayingService.queryEntity(queryBankPayingBo);
				if (null != bankPayingVo) {
					mappper.setPayBankName(bankPayingVo.getBankName());
				}
				return mappper;
			}).collect(Collectors.toList());
		}

		return lstData;
	}

	/**
	 * 新增客户出金申请流程信息
	 *
	 * @param bo 客户出金申请流程信息
	 * @return 结果
	 */
	@Override
	public Boolean insertByBo(ClientFundWithdrawApplicationBo bo) {
		ClientFundWithdrawApplication add = BeanUtil.toBean(bo, ClientFundWithdrawApplication.class);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * 修改客户出金申请流程信息
	 *
	 * @param bo 客户出金申请流程信息
	 * @return 结果
	 */
	@Override
	public Boolean updateByBo(ClientFundWithdrawApplicationBo bo) {
		ClientFundWithdrawApplication update = BeanUtil.toBean(bo, ClientFundWithdrawApplication.class);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * 保存前的数据校验
	 *
	 * @param bo 实体类数据
	 */
	private void validEntityBeforeSave(ClientFundWithdrawInfoBo bo) {
		// 若为 香港取款 bankId 必填 除美金外
		if ((SystemCommonEnum.TransferTypeEnum.HK.getType().equals(bo.getTransferType())
			|| SystemCommonEnum.TransferTypeEnum.HK_LOCAL.getType().equals(bo.getTransferType()))) {
			if (StringUtil.isNotBlank(bo.getCcy()) && !SystemCommonEnum.MoneyType.USD.getName().equals(bo.getCcy())) {
				if (StringUtil.isBlank(bo.getRecvBankId())) {
					throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_BANK_CODE_EMPTY_NOTICE));
				} else {
					String[] bankIds = bo.getRecvBankId().split(",");
					for (String bankId : bankIds) {
						if (!NumberUtil.isNumber(bankId.trim())) {
							log.info("->银行代码:{}格式错误, 限数字! ", bankId);
							throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_BANK_CODE_FORMAT_NOTICE));
						}
					}
				}
			}
		}

		// 是否第三者收藏
		if (null != bo.getThirdRecvFlag() && bo.getThirdRecvFlag() == SystemCommonEnum.YesNo.YES.getIndex()) {
			if (StringUtil.isBlank(bo.getThirdRecvReal())) {
				throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_RELATIONSHIP_THIRD_PARTY_NOTICE));
			}
			if (StringUtil.isBlank(bo.getThirdRecvReason())) {
				throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_REASON_THIRD_PARTY_NOTICE));
			}
		}

		// 若为香港FPSID
		if (SystemCommonEnum.TransferTypeEnum.FPSID.getType().equals(bo.getTransferType())) {
			// 查询DBS付款账号
			BankPaying bankPayingVo = bankPayingService.getPayingBank("SEC", "DBS", bo.getCcy(), bo.getTenantId(), bo.getTransferType());
			if (bankPayingVo == null) {
				log.error("查询DBS付款银行不存在, tradeAcct:{} ccy:{}", bo.getFundAccount(), bo.getCcy());
				throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_DBS_BANK_NOT_EXIST_NOTICE));

			}
			// 银行账户类型
			SystemCommonEnum.BankAcctType bankAcctType = SystemCommonEnum.BankAcctType.getEnum(bo.getBankAcctType());
			if (null == bankAcctType) {
				log.error("不支持的银行账户类型, tradeAcct:{} bankAcctType:{}", bo.getFundAccount(), bo.getBankAcctType());
				throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_UNSUPPORTED_BANK_ACCOUNT_TYPE_NOTICE));
			}

			// 银行账户类型
			if (SystemCommonEnum.MoneyType.USD.getName().equals(bo.getCcy())) {
				log.error("FPS ID不支持美金, tradeAcct:{} ccy:{}", bo.getFundAccount(), bo.getCcy());
				throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_FPS_NOT_SUPPORT_USD_NOTICE));
			}

			// 校验FPS ID 是否有效
			FundTransferEntity fpsIdQueryBo = new FundTransferEntity();
			fpsIdQueryBo.setDepositNo(bankPayingVo.getBankAcct());
			fpsIdQueryBo.setMoneyType(SystemCommonEnum.SecMoneyTypeEn.getName(bo.getCcy()));

			fpsIdQueryBo.setBankAccountType(bankAcctType.getIntCode());
			fpsIdQueryBo.setBankNo(bo.getRecvBankAcct());
			fpsIdQueryBo.setBankId(bo.getRecvBankId());
			fpsIdQueryBo.setClientNameSpell(bo.getRecvBankAcctName());
			fpsIdQueryBo.setTenantId(bo.getTenantId());
			R result = dbsFundBusinessService.sendFpsIdQuery(BankApiFuncTypeEnum.FPSID_ENQUIRY, fpsIdQueryBo);
			if (result.isSuccess()) {
				DbsApiProtocol.EnqResponse enqInfo = null;
				if (null != result.getData()) {
					enqInfo = (DbsApiProtocol.EnqResponse) result.getData();
					if (StringUtil.isBlank(enqInfo.getRespStatus()) || !(SystemCommonEnum.BankRespStatus.ACTC.getType().equals(enqInfo.getRespStatus())
						&& SystemCommonEnum.BankRespStatusDesc.Matched.getType().equals(enqInfo.getRespStatusDescription()))) {
						log.info("Fps Id 验证无效，tradeAcct:{} bankAcct:{} bankAcctName:{} ccy:{}", bo.getFundAccount(), bo.getRecvBankAcct(), bo.getRecvBankAcctName(), bo.getCcy());
						throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_FPS_ID_INVALID_NOTICE));
					}
				}
			} else {
				throw new ServiceException(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_FPS_ID_INVALID_NOTICE));
			}

			// FPSID 取款金额限制 港币 < 1000万 人民币 < 500万
			if (SystemCommonEnum.MoneyType.HKD.getName().equals(bo.getCcy()) || SystemCommonEnum.MoneyType.CNY.getName().equals(bo.getCcy())) {
				// 获取配置 取款金额限制
				String limitAmount = idictBizClient.getValue(WithdrawKeyConstants.DICT_KEY, WithdrawKeyConstants.FUND_WITHDRAW_LIMIT_PREFIX + bo.getCcy()).getData();
				BigDecimal withdrawMaxLimit = new BigDecimal(limitAmount);
				BigDecimal withdrawAmount = bo.getWithdrawAmount();
				if (withdrawAmount.compareTo(withdrawMaxLimit) >= 0) {
					log.info("取款金额超限，tradeAcct:{} amount:{} limitAmount:{} ccy:{}", bo.getFundAccount(), DecimalUtils.formatPlainDecimal(withdrawAmount), withdrawMaxLimit, bo.getCcy());
					throw new ServiceException(String.format(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_FPS_ID_INVALID_NOTICE), DecimalUtils.formatDecimal(withdrawAmount)));
				}
			}
		}
	}

	/**
	 * 批量删除客户出金申请流程信息
	 *
	 * @param ids 需要删除的客户出金申请流程信息主键
	 * @return 结果
	 */
	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if (isValid) {
			//TODO 做一些业务上的校验,判断是否需要校验
		}
		return baseMapper.deleteBatchIds(ids) > 0;
	}

	@Override
	public List<ClientFundWithdrawApplication> queryListByNode(String currentNode) {

		log.info("currentNode :{}", currentNode);
		LambdaQueryWrapper<ClientFundWithdrawApplication> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ClientFundWithdrawApplication::getIsDeleted, 0);
		queryWrapper.eq(ClientFundWithdrawApplication::getCurrentNode, currentNode.trim());
		//queryWrapper.last("and current_node = '"+currentNode.trim()+"'");
		queryWrapper.orderByDesc(ClientFundWithdrawApplication::getUpdateTime);
		List<ClientFundWithdrawApplication> list = baseMapper.selectList(queryWrapper);

		return list;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R cancel(String applicationId) {
		ClientFundWithdrawInfo info = infoMapper.queryByApplicationId(applicationId);
		ClientFundWithdrawApplicationBo bo = new ClientFundWithdrawApplicationBo();
		bo.setApplicationId(applicationId);
		ClientFundWithdrawApplicationVo fund = baseMapper.queryByApplicationId(bo);
		if (!(Objects.equals(info.getStatus(), BpmCommonEnum.FundWithdrawStatus.AUDIT.getStatus())
			&& Objects.equals(fund.getApplicationStatus(), BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_CONFIRM_VALUE))) {
			return R.fail("当前状态不可撤销");
		}

		//解冻资金
		AmountDTO outAmount = new AmountDTO();
		outAmount.setAmount(info.getFrozenBalance());
		outAmount.setCurrency(info.getCcy());
		outAmount.setAccountId(info.getClientId());
		outAmount.setBusinessId(info.getApplicationId());
		outAmount.setThawingType(ThawingType.WITHDRAWALS_REFUSED_RETURN.getCode());
		outAmount.setType(1);
		outAmount.setCustId(info.getUserId());
		log.info("iCustomerInfoClient.thawingAmount query:{}",JSON.toJSONString(outAmount));
		R result = iCustomerInfoClient.thawingAmount(outAmount);
		log.info("iCustomerInfoClient.thawingAmount result:{}", JSON.toJSONString(outAmount));
		if (!result.isSuccess()) {
			log.error("调用账户中心解冻金额失败，错误信息：" + result.getMsg());
			throw new ServiceException("解冻失败！");
		}

		ClientFundWithdrawInfo withdrawInfo = new ClientFundWithdrawInfo();
		withdrawInfo.setId(info.getId());
		withdrawInfo.setStatus(BpmCommonEnum.FundWithdrawStatus.CANCEL.getStatus());
		withdrawInfo.setRefundedDate(new Date());
		withdrawInfo.setRefundedAmount(info.getWithdrawAmount());
		withdrawInfo.setUpdateTime(new Date());
		infoMapper.updateById(withdrawInfo);

		LambdaUpdateWrapper<ClientFundWithdrawApplication> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(ClientFundWithdrawApplication::getApplicationId, applicationId);
		updateWrapper.set(ClientFundWithdrawApplication::getUpdateTime, new Date());
		updateWrapper.set(ClientFundWithdrawApplication::getStatus, BpmCommonEnum.FundWithdrawStatus.CANCEL.getStatus());
		baseMapper.update(null, updateWrapper);

		//extracted(fund.getInstanceId(),"用户手动撤销");
		return R.success();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cancelFlow(String applicationId, String approvalOption) {
		ClientFundWithdrawApplicationVo applicationVo = queryByApplicationId(applicationId);
		//还要走拒绝节点
		R r = flowClient.taskFinish(applicationVo.getInstanceId(), FlowComment.STOP.getType(), approvalOption);
		if (!r.isSuccess()) {
			throw new ServiceException(r.getMsg());
		}
	}

	@Override
	public R completeWithdraw(ClientFundWithdrawInfoBo clientFundWithdrawInfoBo) {
		String applicationId = clientFundWithdrawInfoBo.getApplicationId();
		if (StringUtils.isEmpty(applicationId)) {
			throw new ServiceException(ResultCode.PARAM_MISS);
		}
		if (StringUtils.isEmpty(clientFundWithdrawInfoBo.getRemittanceVoucher())) {
			throw new ServiceException("汇款凭证地址不能为空！");
		}
		ClientFundWithdrawInfo withdrawInfoVo = infoMapper.queryByApplicationId(applicationId);
		if (!Objects.equals(withdrawInfoVo.getStatus(), BpmCommonEnum.FundWithdrawStatus.COUNTER_SUCCESS.getStatus())) {
			return R.fail("当前状态不可完成汇款");
		}
		ClientFundWithdrawInfo withdrawInfo = new ClientFundWithdrawInfo();
		withdrawInfo.setId(withdrawInfoVo.getId());
		withdrawInfo.setStatus(BpmCommonEnum.FundWithdrawStatus.BANK_SUCCESS.getStatus());
		withdrawInfo.setUpdateTime(new Date());
		withdrawInfo.setRemittanceVoucher(clientFundWithdrawInfoBo.getRemittanceVoucher());
		infoMapper.updateById(withdrawInfo);

		LambdaUpdateWrapper<ClientFundWithdrawApplication> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(ClientFundWithdrawApplication::getApplicationId, applicationId);
		updateWrapper.set(ClientFundWithdrawApplication::getUpdateTime, new Date());
		updateWrapper.set(ClientFundWithdrawApplication::getStatus, BpmCommonEnum.FundWithdrawStatus.BANK_SUCCESS.getStatus());
		baseMapper.update(null, updateWrapper);
		return R.success();
	}

	@Override
	public R withdrawalPageList(WithdrawInfoDTO param, Query pageQuery) {
		IPage page = Condition.getPage(pageQuery);
		List<WithdrawInfoVO> list = baseMapper.withdrawalPageList(param, page);
		if (!CollectionUtil.isEmpty(list)) {
			list.stream().forEach(withdrawInfoVO -> {
				Integer status = withdrawInfoVO.getStatus();
				withdrawInfoVO.setStatusName(BpmCommonEnum.FundWithdrawStatus.valueOf(status).getDesc());

				Integer transferType = withdrawInfoVO.getTransferType();
				withdrawInfoVO.setTransferTypeName(SystemCommonEnum.TransferTypeEnum.getName(transferType));

				Integer deductWay = withdrawInfoVO.getDeductWay();
				withdrawInfoVO.setDeductWayName(SystemCommonEnum.DeductWay.getName(deductWay));

				if (!status.equals(BpmCommonEnum.FundWithdrawStatus.REFUND_SUCCESS.getStatus())) {
					withdrawInfoVO.setRefundedDate(null);
				}

			});
		}
		page.setRecords(list);
		return R.data(page);
	}

	@Override
	public R withdrawalFailPageList(WithdrawInfoDTO param, Query pageQuery) {
		IPage page = Condition.getPage(pageQuery);
		List<WithdrawRefundVO> list = baseMapper.withdrawalFailPageList(param, page);
		if (!CollectionUtil.isEmpty(list)) {
			list.stream().forEach(withdrawInfoVO -> {
				Integer status = withdrawInfoVO.getStatus();
				withdrawInfoVO.setStatusName(BpmCommonEnum.FundWithdrawStatus.valueOf(status).getDesc());

				Integer transferType = withdrawInfoVO.getTransferType();
				withdrawInfoVO.setTransferTypeName(SystemCommonEnum.TransferTypeEnum.getName(transferType));

				Integer deductWay = withdrawInfoVO.getDeductWay();
				withdrawInfoVO.setDeductWayName(SystemCommonEnum.DeductWay.getName(deductWay));

				Integer refundType = withdrawInfoVO.getRefundType();
				withdrawInfoVO.setRefundTypeName(RefundType.getName(refundType));

			});
		}
		page.setRecords(list);
		return R.data(page);
	}

	@Override
	public R withdrawalRefundPageList(WithdrawInfoDTO param, Query pageQuery) {
		IPage page = Condition.getPage(pageQuery);
		List<WithdrawRefundVO> list = baseMapper.withdrawalRefundPageList(param, page);
		if (!CollectionUtil.isEmpty(list)) {
			list.stream().forEach(withdrawInfoVO -> {
				Integer status = withdrawInfoVO.getStatus();
				withdrawInfoVO.setStatusName(BpmCommonEnum.FundWithdrawStatus.valueOf(status).getDesc());

				Integer transferType = withdrawInfoVO.getTransferType();
				withdrawInfoVO.setTransferTypeName(SystemCommonEnum.TransferTypeEnum.getName(transferType));

				Integer deductWay = withdrawInfoVO.getDeductWay();
				withdrawInfoVO.setDeductWayName(SystemCommonEnum.DeductWay.getName(deductWay));

				Integer refundType = withdrawInfoVO.getRefundType();
				withdrawInfoVO.setRefundTypeName(RefundType.getName(refundType));

			});
		}
		page.setRecords(list);
		return R.data(page);
	}
}
