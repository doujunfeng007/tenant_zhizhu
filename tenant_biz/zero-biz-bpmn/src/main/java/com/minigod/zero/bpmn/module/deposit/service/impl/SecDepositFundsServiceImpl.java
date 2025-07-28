package com.minigod.zero.bpmn.module.deposit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.common.enums.BankAccountType;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.bpmn.module.constant.DepositFundMessageConstant;
import com.minigod.zero.bpmn.module.deposit.bo.HistoryRecordBo;
import com.minigod.zero.bpmn.module.deposit.bo.SubmitDepositBo;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositFundsEntity;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.deposit.mapper.SecDepositFundsMapper;
import com.minigod.zero.bpmn.module.deposit.service.BankCardInfoService;
import com.minigod.zero.bpmn.module.deposit.service.ISecAccImgService;
import com.minigod.zero.bpmn.module.deposit.service.ISecDepositFundsService;
import com.minigod.zero.bpmn.module.deposit.vo.DepositVO;
import com.minigod.zero.bpmn.module.deposit.vo.ManualDepositVO;
import com.minigod.zero.bpmn.module.deposit.vo.MoneySumVO;
import com.minigod.zero.bpmn.module.deposit.vo.SecDepositFundsVO;
import com.minigod.zero.bpmn.module.feign.ICashBankClient;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountVO;
import com.minigod.zero.bpmn.module.feign.vo.DepositBankVO;
import com.minigod.zero.bpmn.module.feign.vo.ReceivingBankVO;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.cms.enums.SupportTypeEnum;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.redis.lock.RedisLock;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.utils.PushUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 存入资金表 服务实现类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Service
public class SecDepositFundsServiceImpl extends ServiceImpl<SecDepositFundsMapper, SecDepositFundsEntity> implements ISecDepositFundsService {
	@Autowired
	private ISecAccImgService secAccImgService;
	@Autowired
	private BankCardInfoService bankCardInfoService;

	@Autowired
	private ICashBankClient cashBankClient;
	@Resource
	private RestTemplateUtil restTemplateUtil;

	/**
	 * 分页查询存入资金表
	 *
	 * @param page
	 * @param secDepositFundsVO
	 * @return
	 */
	@Override
	public IPage<SecDepositFundsVO> selectSecDepositFundsPage(IPage<SecDepositFundsVO> page, SecDepositFundsVO secDepositFundsVO) {
		return page.setRecords(baseMapper.selectSecDepositFundsPage(page, secDepositFundsVO));
	}

	/**
	 * 查询资金记录
	 *
	 * @param params
	 * @return
	 */
	@Override
	public List<SecDepositFundsEntity> findDepositFunds(HistoryRecordBo params) {
		LambdaQueryWrapper<SecDepositFundsEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SecDepositFundsEntity::getIsFind, Boolean.TRUE);
		queryWrapper.eq(AuthUtil.getTenantCustId() != null, SecDepositFundsEntity::getUserId, AuthUtil.getCustId());
		queryWrapper.eq(AuthUtil.getTenantId() != null, SecDepositFundsEntity::getTenantId, AuthUtil.getTenantId());
		queryWrapper.eq(params.getCurrency() != null && params.getCurrency() != 0, SecDepositFundsEntity::getCurrency, params.getCurrency());
		queryWrapper.eq(params.getState() != null && params.getState() != 99, SecDepositFundsEntity::getState, params.getState());
		queryWrapper.orderByDesc(SecDepositFundsEntity::getId);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public MoneySumVO allDepositFunds(Integer state) {
		return baseMapper.allDepositFunds(AuthUtil.getCustId(), AuthUtil.getTenantId());
	}

	@Autowired
	private ICustomerInfoClient customerInfoClient;

	@Override
	@RedisLock(value = "lock:deposit:submit")
	public R submitDepositFund(DepositVO params) {
		//参数校验
		depositParamCheck(params);
		//验证银行卡是否被其他人绑定
		bankCardInfoService.validateClientBankNo(params.getClientId(), params.getDepositAccount());
		//构建入金记录对象
		SecDepositFundsEntity entity = buildDepositFunds(params);
		//保存入金记录
		baseMapper.insert(entity);
		//保存凭证文件
		secAccImgService.batchUpdateImgTransactId(entity.getId(), params.getAccImgIds());
		//推送消息
		PushUtil.builder()
			.msgGroup("P")
			.custId(AuthUtil.getUserId())
			.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
			.templateCode(PushTemplate.DEPOSIT_RECEIVED.getCode())
			.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
			.pushAsync();
		return R.success();
	}

	@Override
	public R manualDeposit(ManualDepositVO manualDepositVO) {
		SecDepositFundsEntity secDeposit = new SecDepositFundsEntity();
		secDeposit.setUserId(manualDepositVO.getCustId());
		secDeposit.setClientId(manualDepositVO.getAccountId());
		secDeposit.setFundAccount(manualDepositVO.getAccountId());
		secDeposit.setFundAccountName(manualDepositVO.getCustName());
		//收款信息
		Integer currency = manualDepositVO.getCurrency();
		R<ReceivingBankVO> result = cashBankClient.findReceivingBankNew(currency, SupportTypeEnum.MANUAL.getType());
		if (!result.isSuccess()) {
			throw new ZeroException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_MANUAL_QUERY_RECEIVE_ACCOUNT_ERROR_NOTICE));
		}
		ReceivingBankVO receivingBank = result.getData();
		if (receivingBank == null) {
			throw new ZeroException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_MANUAL_RECEIVE_ACCOUNT_ERROR_NOTICE));
		}
		secDeposit.setSwiftCode(receivingBank.getSwiftCode());
		secDeposit.setReceivingAccount(receivingBank.getAccount());
		secDeposit.setReceivingBankCode(receivingBank.getBankCode());
		secDeposit.setReceivingBankNameCn(receivingBank.getBankName());
		secDeposit.setReceivingBankNameEn(receivingBank.getBankEnName());
		secDeposit.setReceivingBankAddress(receivingBank.getBankAddress());
		secDeposit.setReceivingAccountName(receivingBank.getAccountName());
		secDeposit.setSupportType(Integer.valueOf(SupportTypeEnum.MANUAL.getType()));
		//汇款信息
		R<DepositBankVO> response = cashBankClient.findDepositBankById(manualDepositVO.getDepositBankId());
		if (!response.isSuccess()) {
			throw new ZeroException("查询入金银行信息异常");
		}
		DepositBankVO depositBank =  response.getData();
		if (depositBank == null) {
			throw new ZeroException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_MANUAL_BANK_NOT_SUPPORT_NOTICE));
		}
		secDeposit.setCurrency(currency);
		secDeposit.setBankName(depositBank.getBankName());
		secDeposit.setBankCode(depositBank.getBankCode());
		secDeposit.setBankType(depositBank.getBankType());
		secDeposit.setRemittanceBankId(manualDepositVO.getBankId());
		secDeposit.setDepositMoney(manualDepositVO.getDepositAmount());
		secDeposit.setSettlementAmt(manualDepositVO.getDepositAmount());
		secDeposit.setSettlementDt(new Date());
		secDeposit.setChargeMoney(depositBank.getCommission());
		secDeposit.setRemittanceBankCode(depositBank.getBankCode());
		secDeposit.setRemittanceBankName(depositBank.getBankName());
		secDeposit.setRemittanceSwiftCode(depositBank.getSwiftCode());
		secDeposit.setBankAccountCategory(BankAccountType.getByCode(currency).getCode());
		secDeposit.setRemittanceBankAccount(manualDepositVO.getRemittanceAccount());
		secDeposit.setRemittanceAccountNameEn(manualDepositVO.getRemittanceAccountName());

		String applicationId = ApplicationIdUtil.generateDepositId(manualDepositVO.getTenantId());
		secDeposit.setIsCancel(0);
		secDeposit.setIsFind(1);
		secDeposit.setCreatedTime(new Date());
		secDeposit.setModifyTime(new Date());
		secDeposit.setApplicationId(applicationId);
		secDeposit.setEddaApplicationId(applicationId);
		secDeposit.setTenantId(manualDepositVO.getTenantId());
		secDeposit.setBackPerson(manualDepositVO.getCreateUserName());
		secDeposit.setState(DepositStatusEnum.SecDepositFundsStatus.FINISH.getCode());
		baseMapper.insert(secDeposit);
		return R.success();
	}

	private SecDepositFundsEntity buildDepositFunds(DepositVO params) {
		SecDepositFundsEntity secDeposit = new SecDepositFundsEntity();
		R<CustomerAccountVO> accountResult = customerInfoClient.customerAccountInfo(params.getCustId() != null ? params.getCustId() : AuthUtil.getTenantCustId());
		if (!accountResult.isSuccess()) {
			throw new ServiceException(accountResult.getMsg());
		}
		CustomerAccountVO customerAccount = accountResult.getData();
		if (customerAccount == null) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_SUBMIT_ACCOUNT_ERROR_NOTICE));
		}
		CustomerAccountVO.Securities customerInfo = customerAccount.getCust();
		String fundAccountName = StringUtil.isBlank(params.getAccountName()) ? customerInfo.getCustName() : params.getAccountName();
		String fundAccountNameEn = customerInfo.getCustNameSpell();
		//如果中文名为空，就取英文名
		if (StringUtil.isBlank(fundAccountName)) {
			fundAccountName = fundAccountNameEn;
		}
		if (StringUtil.isBlank(fundAccountNameEn)) {
			fundAccountNameEn = fundAccountName;
		}
		secDeposit.setUserId(params.getCustId() != null ? params.getCustId() : AuthUtil.getTenantCustId());
		secDeposit.setClientId(params.getClientId());
		secDeposit.setFundAccount(params.getClientId());
		secDeposit.setFundAccountName(fundAccountName);
		//收款信息
		Long payeeBankDetailId = params.getPayeeBankDetailId();
		Integer currency = Integer.valueOf(params.getCurrency());
		R<ReceivingBankVO> result = cashBankClient.findReceivingBankById(payeeBankDetailId);
		if (!result.isSuccess()) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_SUBMIT_QUERY_RECEIVE_ACCOUNT_ERROR_NOTICE));
		}
		ReceivingBankVO receivingBank = result.getData();
		if (receivingBank == null) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_SUBMIT_RECEIVE_ACCOUNT_ERROR_NOTICE));
		}
		secDeposit.setSwiftCode(receivingBank.getSwiftCode());
		secDeposit.setReceivingAccount(receivingBank.getAccount());
		secDeposit.setReceivingBankCode(receivingBank.getBankCode());
		secDeposit.setReceivingBankNameCn(receivingBank.getBankName());
		secDeposit.setReceivingBankNameEn(receivingBank.getBankEnName());
		secDeposit.setReceivingBankAddress(receivingBank.getBankAddress());
		secDeposit.setReceivingAccountName(receivingBank.getAccountName());
		secDeposit.setSupportType(Integer.valueOf(params.getSupportType()));
		//汇款信息
		R<DepositBankVO> response = cashBankClient.findDepositBankById(params.getDepositBankId());
		if (!response.isSuccess()) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_SUBMIT_QUERY_BANK_ACCOUNT_ERROR_NOTICE));
		}
		DepositBankVO depositBank = response.getData();
		if (depositBank == null) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_SUBMIT_NOT_SUPPORT_ACCOUNT_ERROR_NOTICE));
		}
		secDeposit.setCurrency(currency);
		secDeposit.setBankName(depositBank.getBankName());
		secDeposit.setBankCode(depositBank.getBankCode());
		secDeposit.setBankType(depositBank.getBankType());
		secDeposit.setRemittanceBankId(params.getBankId());
		secDeposit.setDepositMoney(params.getDepositAmount());
		secDeposit.setChargeMoney(depositBank.getCommission());
		secDeposit.setRemittanceAccountNameEn(fundAccountNameEn);
		secDeposit.setRemittanceBankCode(depositBank.getBankCode());
		secDeposit.setRemittanceBankName(depositBank.getBankName());
		secDeposit.setRemittanceSwiftCode(depositBank.getSwiftCode());
		secDeposit.setBankAccountCategory(params.getBankAccountType());
		secDeposit.setRemittanceBankAccount(params.getDepositAccount());

		String applicationId = ApplicationIdUtil.generateDepositId(AuthUtil.getTenantId());
		secDeposit.setIsCancel(0);
		secDeposit.setIsFind(1);
		secDeposit.setCreatedTime(new Date());
		secDeposit.setModifyTime(new Date());
		secDeposit.setApplicationId(applicationId);
		secDeposit.setTenantId(AuthUtil.getTenantId());
		secDeposit.setEddaApplicationId(applicationId);
		secDeposit.setState(DepositStatusEnum.SecDepositFundsStatus.COMMIT.getCode());
		return secDeposit;
	}

	private void depositParamCheck(DepositVO params) {
		String clientId = params.getClientId();
		if (clientId != null && !AuthUtil.getTenantUser().getAccount().equals(clientId)) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_SUBMIT_NOT_YOUR_ACCOUNT_ERROR_NOTICE));
		}
		BigDecimal amount = params.getDepositAmount();
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_SUBMIT_AMOUNT_ERROR_NOTICE));
		}
		if (!params.getDepositAccount().equals(params.getConfirmDepositAccount())) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_SUBMIT_TWICE_ERROR_NOTICE));
		}
	}

	/**
	 * 转客户入金申请实体
	 *
	 * @param params
	 * @return
	 */
	private SecDepositFundsEntity convertEntity(SubmitDepositBo params) {
		SecDepositFundsEntity entity = new SecDepositFundsEntity();
		BeanUtils.copyProperties(params, entity);
		entity.setUserId(AuthUtil.getTenantCustId());
		entity.setTenantId(AuthUtil.getTenantId());
		entity.setCreatedTime(new Date());
		entity.setModifyTime(new Date());
		//entity.setClientId(AuthUtil.getClientId());
		entity.setClientId(params.getClientId());
		entity.setState(DepositStatusEnum.SecDepositFundsStatus.COMMIT.getCode());
		entity.setIsCancel(0);
		entity.setIsFind(1);
		entity.setPushrecved(0);
		return entity;
	}

	@Override
	public List<SecDepositFundsVO> selectByIsPushedFalse() {
		LambdaQueryWrapper<SecDepositFundsEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SecDepositFundsEntity::getPushrecved, 0);
		queryWrapper.eq(SecDepositFundsEntity::getIsFind, 1);
		queryWrapper.orderByDesc(SecDepositFundsEntity::getCreatedTime);
		return baseMapper.selectList(queryWrapper).stream().map(entity -> {
			SecDepositFundsVO vo = new SecDepositFundsVO();
			BeanUtils.copyProperties(entity, vo);
			return vo;
		}).collect(Collectors.toList());
	}

	@Override
	public SecDepositFundsEntity selectByApplicationId(String applicationId) {
		LambdaQueryWrapper<SecDepositFundsEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SecDepositFundsEntity::getApplicationId, applicationId);
		return baseMapper.selectOne(queryWrapper);
	}
}
