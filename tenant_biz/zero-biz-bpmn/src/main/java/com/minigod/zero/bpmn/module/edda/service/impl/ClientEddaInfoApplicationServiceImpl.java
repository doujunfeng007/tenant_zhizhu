package com.minigod.zero.bpmn.module.edda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.common.enums.EddaAuthStatus;
import com.minigod.zero.biz.common.utils.ProtocolUtils;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.bpmn.module.account.constants.DictTypeConstant;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.constant.DepositFundMessageConstant;
import com.minigod.zero.bpmn.module.constant.EDDAMessageConstant;
import com.minigod.zero.bpmn.module.dbs.DbsEddaInfoBusinessService;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositFundsEntity;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.deposit.mapper.SecDepositFundsMapper;
import com.minigod.zero.bpmn.module.edda.bo.*;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaFundApplicationEntity;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoApplicationEntity;
import com.minigod.zero.bpmn.module.edda.mapper.ClientEddaFundApplicationMapper;
import com.minigod.zero.bpmn.module.edda.mapper.ClientEddaInfoApplicationMapper;
import com.minigod.zero.bpmn.module.edda.service.ClientEddaInfoApplicationService;
import com.minigod.zero.bpmn.module.edda.vo.ClientEddaInfoApplicationVO;
import com.minigod.zero.bpmn.module.edda.vo.EddaCustInfoVO;
import com.minigod.zero.bpmn.module.exchange.enums.CurrencyExcEnum;
import com.minigod.zero.bpmn.module.feign.ICashBankClient;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountDetailVO;
import com.minigod.zero.bpmn.module.feign.vo.CustomerDetailVO;
import com.minigod.zero.bpmn.module.feign.vo.DepositBankVO;
import com.minigod.zero.bpmn.module.feign.vo.ReceivingBankVO;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.cms.enums.SupportTypeEnum;
import com.minigod.zero.cms.feign.oms.ICashPayeeBankDetailClient;
import com.minigod.zero.cms.vo.PayeeBankListDTO;
import com.minigod.zero.cms.vo.PayeeBankListVO;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.platform.enums.*;
import com.minigod.zero.platform.utils.EmailUtil;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.platform.utils.SmsUtil;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author dell
 * @description 针对表【client_edda_info_application(DBS edda授权申请表)】的数据库操作Service实现
 * @createDate 2024-05-10 15:44:59
 */
@Slf4j
@Service
@AllArgsConstructor
public class ClientEddaInfoApplicationServiceImpl extends ServiceImpl<ClientEddaInfoApplicationMapper, ClientEddaInfoApplicationEntity>
	implements ClientEddaInfoApplicationService {

	private static final String PATTERN_DATE_START = "yyyy-MM-dd 00:00:00";

	private static final String PATTERN_DATE_END = "yyyy-MM-dd 23:59:59";

	@Autowired
	private IDictBizClient dictBizClient;
	@Autowired
	private ICashBankClient cashBankClient;
	@Autowired
	private ICustomerInfoClient customerInfoClient;
	@Autowired
	private AccountOpenInfoMapper accountOpenInfoMapper;
	@Autowired
	private SecDepositFundsMapper secDepositFundsMapper;
	@Autowired
	private ICashPayeeBankDetailClient iCashPayeeBankDetailClient;
	@Autowired
	private ClientEddaInfoApplicationMapper clientEddaInfoApplicationMapper;
	@Autowired
	private ClientEddaFundApplicationMapper clientEddaFundApplicationMapper;

	@Autowired
	private DbsEddaInfoBusinessService dbsEddaInfoBusinessService;
	@Resource
	private RestTemplateUtil restTemplateUtil;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R submit(FundDepositEddaBO fundDepositEddaBO) {

		submitParamCheck(fundDepositEddaBO);

		ClientEddaInfoApplicationEntity info = clientEddaInfoApplicationMapper.selectById(fundDepositEddaBO.getEddaInfoId());
		if (info == null) {
			return R.fail(I18nUtil.getMessage(EDDAMessageConstant.EDDA_AUTH_INFO_NOT_EXIST_NOTICE));
		}
		if (info.getDataState() != SystemCommonEnum.YesNo.YES.getIndex()) {
			return R.fail(String.format(I18nUtil.getMessage(EDDAMessageConstant.EDDA_ACCOUNT_DATA_EXCEPTION_NOTICE), info.getDepositAccount()));
		}
		if (info.getEddaState() != EddaAuthStatus.AUTHORIZE_SUCCESS.getCode()) {
			return R.fail(String.format(I18nUtil.getMessage(EDDAMessageConstant.EDDA_ACCOUNT_NOT_AUTH_NOTICE), info.getDepositAccount()));
		}

		String applicationId = ApplicationIdUtil.generateDepositId(AuthUtil.getTenantId());
		R<ReceivingBankVO> result = cashBankClient.findReceivingBankByDepositBankById(info.getSecDepositBankId(),
			fundDepositEddaBO.getMoneyType().toString(), SupportTypeEnum.EDDA.getType());
		if (!result.isSuccess()) {
			return R.fail(I18nUtil.getMessage(EDDAMessageConstant.EDDA_RECEIVING_BANK_QUERY_FAILED_NOTICE));
		}
		ReceivingBankVO receivingBank = result.getData();
		if (receivingBank == null){
			return R.fail(I18nUtil.getMessage(EDDAMessageConstant.EDDA_RECEIVING_BANK_NOT_CONFIG_NOTICE));
		}
		ClientEddaFundApplicationEntity fund = buildClientEddaFundApplication(fundDepositEddaBO, info,receivingBank);
		fund.setApplicationId(applicationId);
		fund.setFundApplicationId(applicationId);
		clientEddaFundApplicationMapper.insert(fund);

		SecDepositFundsEntity secDeposit = buildDepositFunds(fund.getMoneyType(),fund.getDepositBalance(),info,receivingBank,applicationId);
		secDepositFundsMapper.insert(secDeposit);

		try {
			PushUtil.builder()
				.msgGroup("P")
				.custId(AuthUtil.getUserId())
				.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
				.templateCode(PushTemplate.DEPOSIT_RECEIVED.getCode())
				.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
				.pushAsync();
		} catch (Exception e) {
			log.error("推送消息失败", e);
		}
		return R.data(fund);
	}

	/**
	 * 获取收款银行信息
	 *
	 * @return
	 */
	@Override
	public R<List<PayeeBankListVO>> infoBankList() {
		PayeeBankListDTO payeeBankListDTO = new PayeeBankListDTO();
		payeeBankListDTO.setCurrency(1);
		payeeBankListDTO.setSupportType(6);
		payeeBankListDTO.setEnable(1);
		payeeBankListDTO.setSubEnable(1);
		R<List<PayeeBankListVO>> listR = iCashPayeeBankDetailClient.bankList(payeeBankListDTO);
		if (!listR.isSuccess()) {
			return R.fail(I18nUtil.getMessage(EDDAMessageConstant.EDDA_RECEIVING_BANK_LIST_QUERY_FAILED_NOTICE));
		}

		if (ObjectUtil.isEmpty(listR.getData())) {
			return R.fail(I18nUtil.getMessage(EDDAMessageConstant.EDDA_RECEIVING_BANK_LIST_EMPTY_NOTICE));
		}
		return R.data(listR.getData());
	}

	@Override
	public IPage<ClientEddaInfoApplicationEntity> pages(ClientEddaInfoListBO bo, Query query) {
		Date startTime = null;
		if (StringUtils.isNotEmpty(bo.getApplicationStartTime())) {
			startTime = DateUtil.parseDate(DateUtil.format(DateUtil.parseDate(bo.getApplicationStartTime()), PATTERN_DATE_START));
		}
		Date endTime = null;
		if (StringUtils.isNotEmpty(bo.getApplicationEndTime())) {
			endTime = DateUtil.parseDate(DateUtil.format(DateUtil.parseDate(bo.getApplicationEndTime()), PATTERN_DATE_END));
		}
		LambdaQueryWrapper<ClientEddaInfoApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getApplicationId()), ClientEddaInfoApplicationEntity::getApplicationId, bo.getApplicationId());
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getClientId()), ClientEddaInfoApplicationEntity::getClientId, bo.getClientId());
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getFundAccount()), ClientEddaInfoApplicationEntity::getFundAccount, bo.getFundAccount());
		queryWrapper.like(ObjectUtil.isNotEmpty(bo.getMobile()), ClientEddaInfoApplicationEntity::getMobile, bo.getMobile());
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getEName()), ClientEddaInfoApplicationEntity::getEName, bo.getEName());
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getCName()), ClientEddaInfoApplicationEntity::getCName, bo.getCName());
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getEddaState()), ClientEddaInfoApplicationEntity::getEddaState, bo.getEddaState());
		queryWrapper.gt(ObjectUtil.isNotEmpty(startTime), ClientEddaInfoApplicationEntity::getApplicationTime, startTime);
		queryWrapper.lt(ObjectUtil.isNotEmpty(endTime), ClientEddaInfoApplicationEntity::getApplicationTime, endTime);
		queryWrapper.eq(ObjectUtil.isNotEmpty(bo.getEmail()), ClientEddaInfoApplicationEntity::getEmail, bo.getEmail());
		queryWrapper.orderByDesc(ClientEddaInfoApplicationEntity::getCreateTime);
		IPage<ClientEddaInfoApplicationEntity> pages = super.page(Condition.getPage(query), queryWrapper);
		return pages;
	}

	@Override
	public R refreshStatus(String applicationId) {
		if (StringUtils.isEmpty(applicationId)) {
			throw new ZeroException("刷新失败，applicationId不能为空");
		}
		ClientEddaInfoApplicationEntity clientEddaInfoApplicationEntity = super.getOne(Wrappers.<ClientEddaInfoApplicationEntity>lambdaQuery()
			.eq(ClientEddaInfoApplicationEntity::getApplicationId, applicationId)
			.eq(ClientEddaInfoApplicationEntity::getDataState, 1));
		if (null == clientEddaInfoApplicationEntity) {
			return R.fail("数据不存在");
		}
		//刷新 edda银行状态  授权申请状态
		if (SystemCommonEnum.EddaStateEnum.AUTHORIZING.getCode() == (clientEddaInfoApplicationEntity.getEddaState())) {
			dbsEddaInfoBusinessService.sendEDDAEnquiry(clientEddaInfoApplicationEntity, AuthUtil.getUserName());
		}
		return R.success("已刷新,稍等一会");
	}

	@Override
	public R eddaInfo(String applicationId) {
		LambdaQueryWrapper<ClientEddaInfoApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ClientEddaInfoApplicationEntity::getApplicationId, applicationId);
		ClientEddaInfoApplicationEntity entity = super.getOne(queryWrapper);
		ClientEddaInfoApplicationVO detailVO = new ClientEddaInfoApplicationVO();
		if (entity != null) {
			BeanUtils.copyProperties(entity, detailVO);
			// 获取银行卡类型
			R<List<DictBiz>> dictBankAccountTypes = dictBizClient.getList("bank_account_type");
			if (dictBankAccountTypes.getData() != null && dictBankAccountTypes.getData().size() > 0) {
				detailVO.setBankTypeName(dictBankAccountTypes.getData().stream().filter(d -> d.getDictKey().equals(detailVO.getBankType().toString())).findFirst().get().getDictValue());
			}
		}
		return R.data(detailVO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R authSubmit(FundDepositAuthEddaBO bo) {
		if (ObjectUtil.isEmpty(bo.getBankIdNo())) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_BANK_ID_NO_NOT_NULL_NOTICE));
		}
		if (ObjectUtil.isEmpty(bo.getBankIdKind())) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_BANK_ID_KIND_NOT_NULL_NOTICE));
		}
		if (ObjectUtil.isEmpty(bo.getDepositAccountType())) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_DEPOSIT_ACCOUNT_TYPE_NOT_NULL_NOTICE));
		}
		if (ObjectUtil.isEmpty(bo.getDepositAccount())) {
			throw new ServiceException(I18nUtil.getMessage(DepositFundMessageConstant.DEPOSIT_FUND_DEPOSIT_ACCOUNT_NOT_NULL_NOTICE));
		}
		String language = WebUtil.getLanguage();
		String tenantId = AuthUtil.getTenantId();
		//理财账号信息
		//R<BpmAccountRespDto> bpmAccountRespDtoR = bpmAccountClient.bpmAccountInfo();
		R<CustomerDetailVO> accountInfoR = customerInfoClient.selectCustomerDetail(AuthUtil.getTenantCustId());

		if (!accountInfoR.isSuccess()) {
			throw new ServiceException(accountInfoR.getMsg());
		}
		CustomerDetailVO customerDetailVO = accountInfoR.getData();

		LambdaQueryWrapper<ClientEddaInfoApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ClientEddaInfoApplicationEntity::getDepositAccount, bo.getDepositAccount());
		queryWrapper.eq(ClientEddaInfoApplicationEntity::getClientId, customerDetailVO.getAccountId());
		queryWrapper.orderByDesc(ClientEddaInfoApplicationEntity::getCreateTime);
		queryWrapper.last("limit 1");
		ArrayList<Integer> eddaState = new ArrayList<>();
		eddaState.add(EddaAuthStatus.AUDITED.getCode());
		eddaState.add(EddaAuthStatus.AUDIT.getCode());
		eddaState.add(EddaAuthStatus.AUTHORIZE.getCode());
		eddaState.add(EddaAuthStatus.AUTHORIZE_SUCCESS.getCode());
		queryWrapper.in(ClientEddaInfoApplicationEntity::getEddaState, eddaState);
		ClientEddaInfoApplicationEntity entity = clientEddaInfoApplicationMapper.selectOne(queryWrapper);
		if (entity != null) {
			if (Objects.equals(entity.getEddaState(), EddaAuthStatus.AUTHORIZE_SUCCESS.getCode())) {
				return R.fail(I18nUtil.getMessage(EDDAMessageConstant.EDDA_CURRENT_ACCOUNT_AUTHORIZED));
			} else {
				return R.fail(I18nUtil.getMessage(EDDAMessageConstant.EDDA_CURRENT_ACCOUNT_UNDER_AUTHORIZATION));
			}
		}


		R<DepositBankVO> result = cashBankClient.findDepositBankById(bo.getSecDepositBankId());
		if (!result.isSuccess()) {
			return R.fail(I18nUtil.getMessage(EDDAMessageConstant.EDDA_DEPOSIT_FUNDS_BANK_QUERY_FAIL));
		}
		DepositBankVO depositBank = result.getData();
		if (depositBank == null) {
			return R.fail(I18nUtil.getMessage(EDDAMessageConstant.EDDA_DEPOSIT_FUNDS_BANK_QUERY_ABNORMAL));
		}
		ClientEddaInfoApplicationEntity clientEddaInfoApplicationEntity = new ClientEddaInfoApplicationEntity();
		//汇款银行信息
		clientEddaInfoApplicationEntity.setBankId(bo.getBankId());
		clientEddaInfoApplicationEntity.setMaxAmt(depositBank.getQuota());
		clientEddaInfoApplicationEntity.setBankIdQuick(bo.getBankIdQuick());
		clientEddaInfoApplicationEntity.setAppIcon(depositBank.getAppIcon());
		if (language != null) {
			switch (language) {
				case "zh-hans":
					clientEddaInfoApplicationEntity.setBankName(depositBank.getBankName());
					break;
				case "zh-hant":
					clientEddaInfoApplicationEntity.setBankName(depositBank.getBankHantName());
					break;
				case "en":
					clientEddaInfoApplicationEntity.setBankName(depositBank.getBankEnName());
					break;
				default:
					clientEddaInfoApplicationEntity.setBankName(depositBank.getBankName());
					break;
			}
		}

		clientEddaInfoApplicationEntity.setBankCode(depositBank.getBankCode());
		clientEddaInfoApplicationEntity.setBankType(depositBank.getBankType());
		clientEddaInfoApplicationEntity.setDepositAccount(bo.getDepositAccount());
		clientEddaInfoApplicationEntity.setDepositAccountType(bo.getDepositAccountType());
		//证件信息
		clientEddaInfoApplicationEntity.setBankIdNo(bo.getBankIdNo());
		clientEddaInfoApplicationEntity.setBankIdKind(bo.getBankIdKind());
		//收费信息
		String currency = bo.getCurrency();
		clientEddaInfoApplicationEntity.setChargeMoney(depositBank.getCommission());
		clientEddaInfoApplicationEntity.setSecDepositBankId(bo.getSecDepositBankId());
		clientEddaInfoApplicationEntity.setAmtCcy(CurrencyExcEnum.CurrencyType.getName(Integer.valueOf(currency)));
		//基础信息
		clientEddaInfoApplicationEntity.setCreateTime(new Date());
		clientEddaInfoApplicationEntity.setUpdateTime(new Date());
		clientEddaInfoApplicationEntity.setDataState(SystemCommonEnum.YesNo.YES.getIndex());
		clientEddaInfoApplicationEntity.setEddaState(SystemCommonEnum.EddaStateEnum.UNCONMMIT.getCode());

		String applicationId = ApplicationIdUtil.generateCommonId(tenantId);
		clientEddaInfoApplicationEntity.setApplicationId(applicationId);

		clientEddaInfoApplicationEntity.setClientId(customerDetailVO.getAccountId());
		clientEddaInfoApplicationEntity.setFundAccount(customerDetailVO.getAccountId());
		clientEddaInfoApplicationEntity.setCName(customerDetailVO.getCustomerName());
		clientEddaInfoApplicationEntity.setEName(customerDetailVO.getCustomerEnName());
		clientEddaInfoApplicationEntity.setMobile(customerDetailVO.getAreaCode() + "-" + customerDetailVO.getPhoneNum());
		clientEddaInfoApplicationEntity.setEmail(customerDetailVO.getCustEmail());
		//资料信息
		R<CustomerDetailVO> customerDetailR = customerInfoClient.selectCustomerDetail(AuthUtil.getTenantCustId());
		if (customerDetailR.isSuccess()) {
			CustomerDetailVO customerDetailData = customerDetailR.getData();
			clientEddaInfoApplicationEntity.setIdType(customerDetailData.getIdKind());
			clientEddaInfoApplicationEntity.setIdCode(customerDetailData.getIdCard());
			clientEddaInfoApplicationEntity.setDepositAccountName(customerDetailData.getCustomerName());
		}

		clientEddaInfoApplicationEntity.setApplicationTime(new Date());
		int insert = clientEddaInfoApplicationMapper.insert(clientEddaInfoApplicationEntity);
		if (insert == 1){
			try {
				R<CustomerAccountDetailVO> accountDetail = customerInfoClient.selectCustomerDetailByAccountId(clientEddaInfoApplicationEntity.getClientId());
				if (accountDetail.isSuccess()) {
					CustomerAccountDetailVO customerAccountDetailVO = accountDetail.getData();
					R<List<DictBiz>> emailAddress = dictBizClient.getListByTenantId(AuthUtil.getTenantId()
						, DictTypeConstant.SYSTEM_EMAIL_ADDRESS);
					//ops发送审核邮件
					List<DictBiz> data = emailAddress.getData();
					List<String> emailList = data.stream().map(DictBiz::getDictKey).collect(Collectors.toList());
					String now = DateUtil.format(new Date(), DateUtil.PATTERN_DATETIME);
					String name = customerAccountDetailVO.getClientName();
					if (StringUtils.isEmpty(name)) {
						name = customerAccountDetailVO.getGivenNameSpell();
					}
					List<String> titleList = new ArrayList<>();
					titleList.add(now);
					titleList.add(name);

					List<String> paramList = new ArrayList<>();
					paramList.add(now);
					paramList.add(name);
					paramList.add(depositBank.getBankName());
					paramList.add(ProtocolUtils.bankCardFormatter(bo.getDepositAccount()));


					EmailUtil.builder()
						.lang(WebUtil.getLanguage())
						.titleParams(titleList)
						.accepts(emailList)
						.paramList(paramList)
						.templateCode(EmailTemplate.EDDA_AUTHORIZATION_REQUEST_APPROVAL_NOTIFICATION.getCode())
						.sendAsync();
				}
			} catch (Exception e) {
				log.error("发送邮件失败 {}", e.getMessage());
			}
		}
		return R.success("添加成功 " + clientEddaInfoApplicationEntity.getApplicationId());
	}

	@Override
	public R eddaCustInfo() {
		R<CustomerDetailVO> customerDetailR = customerInfoClient.selectCustomerDetail(AuthUtil.getTenantCustId());
		EddaCustInfoVO eddaCustInfoVO = new EddaCustInfoVO();
		if (customerDetailR.isSuccess()) {
			CustomerDetailVO customerDetailData = customerDetailR.getData();
			eddaCustInfoVO.setIdKind(customerDetailData.getIdKind());
			eddaCustInfoVO.setIdCard(customerDetailData.getIdCard());
			eddaCustInfoVO.setGivenName(customerDetailData.getCustomerName());
		} else {
			return R.fail(String.format(I18nUtil.getMessage(EDDAMessageConstant.EDDA_OBTAIN_CUSTOMER_INFO_FAIL), customerDetailR.getMsg()));
		}

		return R.data(eddaCustInfoVO);
	}

	@Override
	public R eddaAccountList(FundDepositEddaAccountListBO fundDepositEddaAccountListBO) {
		R<CustomerDetailVO> accountInfoR = customerInfoClient.selectCustomerDetail(AuthUtil.getTenantCustId());

		if (!accountInfoR.isSuccess()) {
			log.error("获取交易账号失败，custId：{}", AuthUtil.getTenantCustId());
			throw new ServiceException(accountInfoR.getMsg());
		}
		String client = accountInfoR.getData().getAccountId();
		String fundAccount = accountInfoR.getData().getAccountId();
		Date effDate = null;
		Date expDate = null;
		if (ObjectUtil.isNotEmpty(fundDepositEddaAccountListBO.getEddaState()) && fundDepositEddaAccountListBO.getEddaState()==3){
			effDate = new Date();
			expDate = new Date();
		}
		Query query = new Query();
		query.setCurrent(fundDepositEddaAccountListBO.getCurrent());
		query.setSize(fundDepositEddaAccountListBO.getSize());
		LambdaQueryWrapper<ClientEddaInfoApplicationEntity> queryWrapper = Wrappers.<ClientEddaInfoApplicationEntity>lambdaQuery()
			.eq(ClientEddaInfoApplicationEntity::getClientId, client)
			.eq(ClientEddaInfoApplicationEntity::getDataState, 1)
			.eq(ObjectUtil.isNotEmpty(fundDepositEddaAccountListBO.getEddaState()), ClientEddaInfoApplicationEntity::getEddaState, fundDepositEddaAccountListBO.getEddaState())
			.ge(ObjectUtil.isNotEmpty(effDate), ClientEddaInfoApplicationEntity::getEffDate, effDate)
			.lt(ObjectUtil.isNotEmpty(expDate), ClientEddaInfoApplicationEntity::getExpDate, expDate)
			.eq(ClientEddaInfoApplicationEntity::getFundAccount, fundAccount)
			.orderByDesc(ClientEddaInfoApplicationEntity::getCreateTime);
		IPage<ClientEddaInfoApplicationEntity> page = baseMapper.selectPage(Condition.getPage(query), queryWrapper);
		return R.data(page);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R approval(EddaInfoApprovalBO eddaInfoApprovalBO) {
		String applicationId = eddaInfoApprovalBO.getApplicationId();
		Integer state = eddaInfoApprovalBO.getState();
		String stateDesc = "";
		if (StringUtils.isNotBlank(applicationId)) {
			ClientEddaInfoApplicationEntity clientEddaInfoApplicationEntity = baseMapper.selectOne(Wrappers.<ClientEddaInfoApplicationEntity>lambdaQuery()
				.eq(ClientEddaInfoApplicationEntity::getApplicationId, applicationId)
				.eq(ClientEddaInfoApplicationEntity::getDataState, 1));

			if (null == clientEddaInfoApplicationEntity) {
				return R.fail("数据不存在");
			}
			LambdaQueryWrapper<SecDepositFundsEntity> openVerifyWrapper = new LambdaQueryWrapper<>();
			ClientEddaInfoApplicationEntity entity = new ClientEddaInfoApplicationEntity();
			entity.setId(clientEddaInfoApplicationEntity.getId());
			entity.setUpdateTime(new Date());
			if (state == 1) {
				//edda定时任务扫描获取任务
				entity.setEddaState(SystemCommonEnum.EddaStateEnum.CHECK.getCode());
				stateDesc = "通过";
			}
			if (state == 2) {
				entity.setEddaState(SystemCommonEnum.EddaStateEnum.FAIL.getCode());
				entity.setRejDescription(eddaInfoApprovalBO.getRejDescription());
				stateDesc = "拒绝";
				try {
					R<CustomerAccountDetailVO> accountDetail = customerInfoClient.selectCustomerDetailByAccountId(clientEddaInfoApplicationEntity.getClientId());
					if (accountDetail.isSuccess()) {
						CustomerAccountDetailVO customerAccountDetailVO = accountDetail.getData();
						List<String> params = new ArrayList<>();
						params.add(clientEddaInfoApplicationEntity.getBankName());
						params.add(clientEddaInfoApplicationEntity.getDepositAccount().substring(clientEddaInfoApplicationEntity.getDepositAccount().length() - 4));
						params.add(dictBizClient.getValue("company", "CO_Phone_HK").getData());
						PushUtil.builder()
							.msgGroup("P")
							.custId(customerAccountDetailVO.getCustId())
							.params(params)
							.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
							.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
							.templateCode(PushTemplate.EDDA_AUTH_FAIL.getCode())
							.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
							.pushAsync();
					}
				} catch (Exception e) {
					log.error("推送失败", e);
				}
			}
			int count = baseMapper.updateById(entity);
			if (count == 1) {
				if (clientEddaInfoApplicationEntity.getMobile() != null && state == 2) {
					String phoneNumber[] = clientEddaInfoApplicationEntity.getMobile().split("-");
					String bankName = clientEddaInfoApplicationEntity.getBankName();
					String depositAccount = clientEddaInfoApplicationEntity.getDepositAccount();
					if (phoneNumber.length == 2) {
						Integer code = SmsTemplate.EDDA_AUTHORIZATION_FAILED.getCode();
						String contact = dictBizClient.getValue("company", "CO_Phone_HK").getData();
						SmsUtil.builder()
							.templateCode(code)
							.param(bankName)
							.param(this.getLastFour(depositAccount))
							.param(eddaInfoApprovalBO.getRejDescription())
							.param(contact)
							.phoneNumber(phoneNumber[1])
							.areaCode(phoneNumber[0])
							.sendAsync();
						log.info(String.format("发送短信EDDA授权[%s]提醒成功！", stateDesc));
					} else {
						log.error(String.format("手机号格式错误,未能发送短信EDDA授权[%s]提醒！", stateDesc));
					}
				} else {
					log.error(String.format("手机号为空或审批结果为通过,未发送短信EDDA授权提醒,审批结果:%s！", stateDesc));
				}
				return R.success("审批操作成功 " + clientEddaInfoApplicationEntity.getId());
			} else {
				return R.fail("审批操作失败!");
			}
		}
		return R.fail("数据异常!");
	}


	/**
	 * 查询需要上送给银行的edda数据(授权申请)
	 *
	 * @return
	 */
	@Override
	public List<ClientEddaInfoApplicationEntity> queryNotStateEddaListInfo() {
		List<ClientEddaInfoApplicationEntity> clientEddaInfoApplicationEntities = baseMapper.selectList(Wrappers.<ClientEddaInfoApplicationEntity>lambdaQuery()
			.eq(ClientEddaInfoApplicationEntity::getEddaState, SystemCommonEnum.EddaStateEnum.CHECK.getCode())

			//.eq(ClientEddaInfoApplicationEntity::getId, 1562)
			.eq(ClientEddaInfoApplicationEntity::getDataState, 1));
		return clientEddaInfoApplicationEntities;
	}

	/**
	 * 手动删除银行卡
	 *
	 * @param applicationId
	 * @return
	 */
	@Override
	@Transactional
	public R eddaDel(String applicationId) {
		ClientEddaInfoApplicationEntity clientEddaInfoApplicationEntity = baseMapper.selectOne(Wrappers.<ClientEddaInfoApplicationEntity>lambdaQuery()
			.eq(ClientEddaInfoApplicationEntity::getApplicationId, applicationId));

		if (null == clientEddaInfoApplicationEntity) {
			return R.fail(I18nUtil.getMessage(EDDAMessageConstant.EDDA_DATA_NOT_EXIST));
		}
		if (!((SystemCommonEnum.EddaStateEnum.FAIL.getCode() == (clientEddaInfoApplicationEntity.getEddaState()) ||
			SystemCommonEnum.EddaStateEnum.SUCCESS.getCode() == (clientEddaInfoApplicationEntity.getEddaState())))) {
			return R.fail(I18nUtil.getMessage(EDDAMessageConstant.EDDA_NO_REVOKE));
		}
		ClientEddaInfoApplicationEntity entity = new ClientEddaInfoApplicationEntity();
		entity.setId(clientEddaInfoApplicationEntity.getId());
		entity.setDataState(0);
		entity.setDeleteTime(new Date());
		entity.setUpdateTime(new Date());
		int i = clientEddaInfoApplicationMapper.updateById(entity);
		if (i == 1) {
			return R.success("删除成功");
		}
		return R.fail(I18nUtil.getMessage(EDDAMessageConstant.EDDA_DELETE_FAIL));
	}

	private SecDepositFundsEntity buildDepositFunds(Integer currency, BigDecimal depositAmount, ClientEddaInfoApplicationEntity info, ReceivingBankVO receivingBank, String applicationId) {
		SecDepositFundsEntity secDeposit = new SecDepositFundsEntity();

		secDeposit.setUserId(AuthUtil.getTenantCustId());
		secDeposit.setClientId(info.getClientId());
		secDeposit.setFundAccount(info.getFundAccount());
		secDeposit.setSwiftCode(receivingBank.getSwiftCode());
		secDeposit.setReceivingAccount(receivingBank.getAccount());
		secDeposit.setReceivingBankCode(receivingBank.getBankCode());
		secDeposit.setFundAccountName(info.getDepositAccountName());
		secDeposit.setReceivingBankNameCn(receivingBank.getBankName());
		secDeposit.setReceivingBankNameEn(receivingBank.getBankEnName());
		secDeposit.setReceivingBankAddress(receivingBank.getBankAddress());
		secDeposit.setReceivingAccountName(receivingBank.getAccountName());
		secDeposit.setSupportType(Integer.valueOf(SupportTypeEnum.EDDA.getType()));

		secDeposit.setCurrency(currency);
		secDeposit.setDepositMoney(depositAmount);
		secDeposit.setBankName(info.getBankName());
		secDeposit.setBankCode(info.getBankCode());
		secDeposit.setBankType(info.getBankType());
		secDeposit.setChargeMoney(info.getChargeMoney());
		secDeposit.setRemittanceBankId(info.getBankId());
		secDeposit.setRemittanceBankCode(info.getBankCode());
		secDeposit.setRemittanceBankName(info.getBankName());
		secDeposit.setRemittanceAccountNameEn(info.getCName());
		secDeposit.setRemittanceBankAccount(info.getDepositAccount());
		secDeposit.setBankAccountCategory(info.getDepositAccountType());

		secDeposit.setIsCancel(0);
		secDeposit.setIsFind(1);
		secDeposit.setCreatedTime(new Date());
		secDeposit.setModifyTime(new Date());
		secDeposit.setTenantId(AuthUtil.getTenantId());
		secDeposit.setApplicationId(applicationId);
		secDeposit.setEddaApplicationId(applicationId);
		secDeposit.setState(DepositStatusEnum.SecDepositFundsStatus.COMMIT.getCode());
		return secDeposit;
	}


	private ClientEddaFundApplicationEntity buildClientEddaFundApplication(FundDepositEddaBO fundDepositEddaBO, ClientEddaInfoApplicationEntity info, ReceivingBankVO receivingBank) {
		ClientEddaFundApplicationEntity fund = new ClientEddaFundApplicationEntity();
		//用户信息
		fund.setEName(info.getEName());
		fund.setCName(info.getCName());
		fund.setIdType(info.getIdType());
		fund.setIdCode(info.getIdCode());
		fund.setMobile(info.getMobile());
		fund.setEmail(info.getEmail());
		//汇款信息
		fund.setDepositBank(info.getBankName());
		fund.setDepositBankId(info.getBankId());
		fund.setDepositBankCode(info.getBankCode());
		fund.setDepositNo(info.getDepositAccount());
		fund.setDepositAccount(info.getDepositAccountName());
		fund.setMoneyType(fundDepositEddaBO.getMoneyType());
		fund.setEddaInfoId(fundDepositEddaBO.getEddaInfoId());
		fund.setAcctType(info.getDepositAccountType().toString());
		fund.setDepositBalance(fundDepositEddaBO.getDepositAmount());
		fund.setDepositType(Integer.valueOf(SupportTypeEnum.EDDA.getType()));
		fund.setRemittanceType(Integer.valueOf(SupportTypeEnum.EDDA.getType()));
		fund.setBankState(SystemCommonEnum.EDDABankStateTypeEnum.UN_COMMNIT.getValue());
		//收款信息
		fund.setSwiftCode(receivingBank.getSwiftCode());
		fund.setBenefitBank(receivingBank.getBankName());
		fund.setBenefitBankCode(receivingBank.getBankCode());
		fund.setBenefitNo(receivingBank.getAccount());
		fund.setBenefitAccount(receivingBank.getAccountName());

		Date now = new Date();
		fund.setCreateTime(now);
		fund.setUpdateTime(now);
		fund.setApplicationTime(now);
		R<CustomerDetailVO> result = customerInfoClient.selectCustomerDetail(AuthUtil.getTenantCustId());
		if (!result.isSuccess()) {
			throw new ServiceException(I18nUtil.getMessage(EDDAMessageConstant.EDDA_OBTAIN_TRADE_ACCOUNT_FAIL));
		}

		CustomerDetailVO customerDetail = result.getData();
		if (StringUtils.isEmpty(customerDetail.getAccountId())) {
			throw new ServiceException(I18nUtil.getMessage(EDDAMessageConstant.EDDA_OBTAIN_TRADE_ACCOUNT_ABNORMAL));
		}

		fund.setClientId(customerDetail.getAccountId());
		fund.setFundAccount(customerDetail.getAccountId());
		return fund;
	}


	private void submitParamCheck(FundDepositEddaBO fundDepositEddaBO) {
		if (fundDepositEddaBO.getEddaInfoId() == null) {
			throw new ServiceException(I18nUtil.getMessage(EDDAMessageConstant.EDDA_GRANT_BANK_CARD_ID_NOT_EMPTY));
		}
		if (fundDepositEddaBO.getMoneyType() == null) {
			throw new ServiceException(I18nUtil.getMessage(EDDAMessageConstant.EDDA_DEPOSIT_FUNDS_CURRENCY_NOT_EMPTY));
		}
		if (fundDepositEddaBO.getDepositAmount() == null
			|| fundDepositEddaBO.getDepositAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new ServiceException(I18nUtil.getMessage(EDDAMessageConstant.EDDA_INPUT_CORRECT_MONEY));
		}
	}

	/**
	 * 截取银行卡后4位
	 *
	 * @param cardNo
	 * @return
	 */
	private String getLastFour(String cardNo) {
		if (StringUtils.isEmpty(cardNo)) {
			return "";
		}
		return cardNo.substring(cardNo.length() - 4);
	}
}




