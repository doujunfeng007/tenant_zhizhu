package com.minigod.zero.customer.api.service.impl;

import com.minigod.zero.biz.common.exception.BusinessException;
import com.minigod.zero.biz.common.utils.ValidateUtil;
import com.minigod.zero.bpm.feign.IBpmSecuritiesInfoClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.customer.api.service.AppCustomerInfoService;
import com.minigod.zero.customer.api.service.OpenAccountService;
import com.minigod.zero.customer.back.service.ICustomerTradeAccountService;
import com.minigod.zero.customer.dto.*;
import com.minigod.zero.customer.emuns.CustomerStatus;
import com.minigod.zero.customer.entity.*;
import com.minigod.zero.customer.enums.*;
import com.minigod.zero.customer.mapper.*;
import com.minigod.zero.customer.utils.IdGenerateUtils;
import com.minigod.zero.customer.utils.RegexUtils;
import com.minigod.zero.customer.vo.CustomerInfoVO;
import com.minigod.zero.customer.vo.OrganizationOpenAccountVO;
import com.minigod.zero.platform.dto.OpenAccountDTO;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.enums.PushTypeEnum;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.trade.feign.ICounterOpenAccountClient;
import com.minigod.zero.trade.vo.sjmb.req.OpenAccountReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/8 9:55
 * @description： 开户信息同步
 */
@Slf4j
@Service
public class OpenAccountServiceImpl implements OpenAccountService {

	@Autowired
	private CustomerInfoMapper customerInfoMapper;

	@Autowired
	private CustomerBasicInfoMapper customerBasicInfoMapper;

	@Autowired
	private CustomerRealnameVerifyMapper customerRealnameVerifyMapper;
	@Autowired
	private CustomerFinancingAccountMapper customerFinancingAccountMapper;

	@Autowired
	private OrganizationBasicInfoMapper organizationBasicInfoMapper;

	@Autowired
	private IdGenerateUtils idGenerateUtils;

	@Autowired
	private AppCustomerInfoService appCustomerInfoService;

	@Autowired
	private ICounterOpenAccountClient counterOpenAccountClient;

	@Autowired
	private IBpmSecuritiesInfoClient bpmSecuritiesInfoClient;

	@Autowired
	private ICustomerTradeAccountService customerTradeAccountService;

	@Transactional(rollbackFor = Exception.class)
	public R openAccount(CustomerOpenAccountDTO customerOpenAccount) {
		//用户信息校验
		CustomerInfoDTO customerInfo = customerOpenAccount.getCustomerInfo();
		if (customerInfo == null) {
			return R.fail(ResultCode.FAILURE, "缺少客户信息");
		}
		Long custId = customerOpenAccount.getCustId();
		if (custId == null) {
			return R.fail(ResultCode.FAILURE, "用户标识不能为空");
		}
		CustomerInfoEntity customerInfoEntity = customerInfoMapper.selectById(custId);
		CustomerBasicInfoDTO basicInfo = customerOpenAccount.getBasicInfo();
		basicInfo.setTenantId(customerInfoEntity.getTenantId());
		if (basicInfo == null) {
			return R.fail(ResultCode.FAILURE, "缺少基础资料");
		}
		CustomerRealnameVerifyDTO realnameVerifyDTO = customerOpenAccount.getRealnameVerify();
		if (realnameVerifyDTO == null) {
			return R.fail(ResultCode.FAILURE, "缺少实名认证信息");
		}
		realnameVerifyDTO.setCustId(custId);
		realnameVerifyDTO.setTenantId(customerInfoEntity.getTenantId());

		//基础资料
		CustomerBasicInfoEntity basicInfoEntity = customerBasicInfoMapper.selectByCustId(custId);
		String initPassword = basicInfo.getInitialAccountPassword();
		basicInfo.setAccountLevel(3);
		if (basicInfoEntity == null) {
			basicInfo.setCreateTime(new Date());
			basicInfo.setTenantId(customerInfoEntity.getTenantId());
			customerBasicInfoMapper.insert(basicInfo);
			log.info("用户custId = {}首次开户保存基础资料成功！", custId);
		} else {
			basicInfo.setUpdateTime(new Date());
			basicInfo.setId(basicInfoEntity.getId());
			customerBasicInfoMapper.updateByPrimaryKeySelective(basicInfo);
			log.info("用户custId = {}修改基础资料成功！", custId);
		}
		//实名信息
		CustomerRealnameVerifyEntity realnameVerify = customerRealnameVerifyMapper.selectByCustId(custId);
		if (realnameVerify == null) {
			realnameVerifyDTO.setIsDeleted(0);
			realnameVerifyDTO.setCreateTime(new Date());
			realnameVerifyDTO.setIdKind(basicInfo.getIdKind() == null ? null : String.valueOf(basicInfo.getIdKind()));
			realnameVerifyDTO.setTenantId(customerInfoEntity.getTenantId());
			customerRealnameVerifyMapper.insertSelective(realnameVerifyDTO);
			log.info("用户custId = {}首次实名认证保存成功！", custId);
		} else {
			realnameVerifyDTO.setId(realnameVerify.getId());
			customerRealnameVerifyMapper.updateByPrimaryKeySelective(realnameVerifyDTO);
		}
		//理财账户
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		String accountId = idGenerateUtils.generateId();
		initPassword = StringUtil.isNotBlank(initPassword) ? initPassword : idGenerateUtils.lastSixDigitsOfTheIdCard(realnameVerifyDTO.getIdCard());

		//用户信息
		customerInfoEntity.setCellEmail(basicInfo.getEmail());
		if (customerInfoEntity.getPassword() == null || customerInfoEntity.getPassword().isEmpty()){
			//登录密码为空的情况下，默认设置密码为交易密码
			customerInfoEntity.setPassword(new BCryptPasswordEncoder().encode(initPassword));
			log.info("用户custId = {}首次开户设置默认密码成功！", custId);
		}
		customerInfoEntity.setUpdateTime(new Date());
		customerInfoMapper.updateByPrimaryKeySelective(customerInfoEntity);
		if (financingAccount == null) {
			financingAccount = new CustomerFinancingAccountEntity();
			financingAccount.setCustId(customerInfoEntity.getId());
			financingAccount.setAccountId(accountId);
			financingAccount.setPassword(new BCryptPasswordEncoder().encode(initPassword));
			financingAccount.setTenantId(customerInfoEntity.getTenantId());
			financingAccount.setStatus(FinancingAccountStatus.PRE_APPROVED.getCode());
			financingAccount.setCreateTime(new Date());
			financingAccount.setAccountType(FinancingAccountType.PERSONAL.getCode());
			customerFinancingAccountMapper.insert(financingAccount);
		} else {
			financingAccount.setCreateTime(new Date());
			financingAccount.setUpdateTime(new Date());
			financingAccount.setStatus(FinancingAccountStatus.PRE_APPROVED.getCode());
			financingAccount.setAccountType(FinancingAccountType.PERSONAL.getCode());
			financingAccount.setPassword(new BCryptPasswordEncoder().encode(initPassword));
			customerFinancingAccountMapper.updateByPrimaryKeySelective(financingAccount);
			accountId = financingAccount.getAccountId();
		}

		if (customerOpenAccount.getW8AgreementTime() != null){
			//开通债券户
			OpenAccountDTO openAccount = new OpenAccountDTO();
			openAccount.setCustId(custId);
			openAccount.setAccountType(1);
			openAccount.setExtAccountId(accountId);
			appCustomerInfoService.openOtcAccount(openAccount);
		}

		if (customerOpenAccount.getOpenChannel() != null
			&& customerOpenAccount.getOpenChannel().equals(OpenChannel.OFFLINE.getCode())) {
			log.info("线下个人开户custId={}，开通各个交易账号", custId);
			OpenAccountDTO openAccountDTO = new OpenAccountDTO();
			openAccountDTO.setPiFlag(1);
			openAccountDTO.setCustId(custId);
			openAccountDTO.setAccountType(1);
			openAccountDTO.setRiskLevel(customerOpenAccount.getRiskLevel());
			openAccountDTO.setExtAccountId(financingAccount.getAccountId());
			openAccountDTO.setExpiryDate(customerOpenAccount.getExpiryDate());
			appCustomerInfoService.fundOpenAccount(openAccountDTO);
//			appCustomerInfoService.fundOpenAccount(openAccountDTO);
//			log.info("个人户{}基金开户成功",custId);
//            appCustomerInfoService.bondOpenAccount(openAccountDTO);
//            log.info("个人户{}债券易开户成功", custId);
//            appCustomerInfoService.hldOpenAccount(openAccountDTO);
//            log.info("个人户{}活利得开户成功", custId);
		}

		String name = basicInfo.getClientName();
		if (StringUtils.isEmpty(name)) {
			name = basicInfo.getGivenNameSpell();
		}
		Map<String, String> data = new HashMap<>();
		data.put("name", name);
		data.put("accountId", accountId);
		data.put("password", initPassword);
		return R.data(data);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R organizationOpenAccount(OrganizationOpenAccountVO organizationOpenAccount) {
		Long custId = organizationOpenAccount.getCustId();
		if (custId == null) {
			CustomerInfoVO customerInfo = defaultRegister(organizationOpenAccount.getContactsMobile(),
				organizationOpenAccount.getAreaCode(), organizationOpenAccount.getTenantId());
			custId = customerInfo.getId();
		}
		organizationOpenAccount.setCustId(custId);
		OrganizationBasicInfo basicInfo = organizationBasicInfoMapper.selectByCustId(custId);
		organizationOpenAccount.setCustId(custId);
		if (basicInfo == null) {
			organizationOpenAccount.setCreateTime(new Date());
			organizationBasicInfoMapper.insertSelective(organizationOpenAccount);
		} else {
			organizationOpenAccount.setId(basicInfo.getId());
			organizationOpenAccount.setUpdateTime(new Date());
			organizationBasicInfoMapper.updateByPrimaryKeySelective(organizationOpenAccount);
		}
		String initPassword = idGenerateUtils.generatePassword();
		String accountId = idGenerateUtils.generateOrganizationId();
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (financingAccount == null) {
			financingAccount = new CustomerFinancingAccountEntity();
			financingAccount.setCustId(custId);
			financingAccount.setAccountId(accountId);
			financingAccount.setPassword(new BCryptPasswordEncoder().encode(initPassword));
			financingAccount.setTenantId(organizationOpenAccount.getTenantId());
			financingAccount.setStatus(FinancingAccountStatus.NORMAL.getCode());
			financingAccount.setCreateTime(new Date());
			financingAccount.setAccountType(FinancingAccountType.ORGANIZATION.getCode());
			customerFinancingAccountMapper.insert(financingAccount);
		} else {
			financingAccount.setAccountId(accountId);
			financingAccount.setCreateTime(new Date());
			financingAccount.setUpdateTime(new Date());
			financingAccount.setStatus(FinancingAccountStatus.NORMAL.getCode());
			financingAccount.setAccountType(FinancingAccountType.ORGANIZATION.getCode());
			financingAccount.setPassword(new BCryptPasswordEncoder().encode(initPassword));
			customerFinancingAccountMapper.updateByPrimaryKeySelective(financingAccount);
			accountId = financingAccount.getAccountId();
		}
		//线上开户不会传riskLevel,这里默认给增长型
		Integer riskLevel = organizationOpenAccount.getRiskLevel();
		if (riskLevel == null) {
			riskLevel = FundRiskLevel.GROWTH_ORIENTED.getCode();
		}
		Date expiryDate = organizationOpenAccount.getExpiryDate();
		if (expiryDate == null) {
			expiryDate = DateUtil.plusDays(new Date(), 365);
		}
		//更新风险等级
		CustomerInfoEntity customerInfoEntity = customerInfoMapper.selectById(custId);
		customerInfoEntity.setRiskLevel(riskLevel);
		customerInfoEntity.setPiRiskLevel(riskLevel);
		customerInfoEntity.setPiLevel(4);
		customerInfoEntity.setNickName(organizationOpenAccount.getName());
		customerInfoEntity.setCellEmail(organizationOpenAccount.getContactsEmail());
		customerInfoEntity.setUpdateTime(new Date());
		customerInfoEntity.setRiskExpiryDate(expiryDate);
		if (customerInfoEntity.getPassword() == null || customerInfoEntity.getPassword().isEmpty()){
			//登录密码为空的情况下，默认设置密码为交易密码
			customerInfoEntity.setPassword(new BCryptPasswordEncoder().encode(initPassword));
			log.info("机构户custId = {}首次开户设置默认密码成功！", custId);
		}

		customerInfoMapper.updateByPrimaryKeySelective(customerInfoEntity);
		//实名信息
		CustomerBasicInfoEntity customerBasicInfo = new CustomerBasicInfoEntity();
		customerBasicInfo.setCustId(custId);
		String name = organizationOpenAccount.getName();
		customerBasicInfo.setClientName(name);
		//不包含汉字就当成英文名
		if (!RegexUtils.containsChinese(name)) {
			customerBasicInfo.setGivenNameSpell(name);
		}
		customerBasicInfo.setPhoneArea(customerInfoEntity.getAreaCode());
		customerBasicInfo.setPhoneNumber(organizationOpenAccount.getContactsMobile());
		customerBasicInfo.setAccountType(1);
		customerBasicInfo.setAccountLevel(3);
		customerBasicInfo.setCreateTime(new Date());
		customerBasicInfo.setIdKind(IdKindType.DEFAULT.getIdKind());
		customerBasicInfo.setTenantId(customerInfoEntity.getTenantId());
		customerBasicInfo.setEmail(organizationOpenAccount.getContactsEmail());
		customerBasicInfoMapper.insertSelective(customerBasicInfo);
		OpenAccountDTO openAccountDTO = new OpenAccountDTO();
		openAccountDTO.setPiFlag(1);
		openAccountDTO.setCustId(custId);
		openAccountDTO.setAccountType(1);
		openAccountDTO.setRiskLevel(riskLevel);
		openAccountDTO.setExpiryDate(expiryDate);
		openAccountDTO.setExtAccountId(financingAccount.getAccountId());
		appCustomerInfoService.fundOpenAccount(openAccountDTO);
		PushUtil.builder().custId(custId).group(MsgStaticType.DisplayGroup.SERVICE_MSG)
			.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
			.templateCode(PushTemplate.ACCOUNT_OPEN_SUCCESS.getCode()).pushAsync();
		Map<String, String> data = new HashMap<>();
		data.put("name", organizationOpenAccount.getName());
		data.put("accountId", accountId);
		data.put("password", initPassword);
		data.put("custId", custId.toString());
		return R.data(data);
	}

	@Override
	public R customerOpenAccount(CustomerOpenAccountDTO customerOpenAccount) {
		//用户信息校验
		CustomerInfoDTO customerInfo = customerOpenAccount.getCustomerInfo();
		if (customerInfo == null) {
			return R.fail(ResultCode.FAILURE, "缺少客户信息");
		}
		Long custId = customerOpenAccount.getCustId();
		if (custId == null) {
			return R.fail(ResultCode.FAILURE, "用户标识不能为空");
		}
		CustomerInfoEntity customerInfoEntity = customerInfoMapper.selectById(custId);
		CustomerBasicInfoDTO basicInfo = customerOpenAccount.getBasicInfo();
		basicInfo.setTenantId(customerInfoEntity.getTenantId());
		if (basicInfo == null) {
			return R.fail(ResultCode.FAILURE, "缺少基础资料");
		}
		CustomerRealnameVerifyDTO realnameVerifyDTO = customerOpenAccount.getRealnameVerify();
		if (realnameVerifyDTO == null) {
			return R.fail(ResultCode.FAILURE, "缺少实名认证信息");
		}
		realnameVerifyDTO.setCustId(custId);
		realnameVerifyDTO.setTenantId(customerInfoEntity.getTenantId());
		//用户信息
		customerInfo.setCellEmail(basicInfo.getEmail());
		customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
		//基础资料
		CustomerBasicInfoEntity basicInfoEntity = customerBasicInfoMapper.selectByCustId(custId);
		String initPassword = basicInfo.getInitialAccountPassword();
		basicInfo.setAccountLevel(3);
		if (basicInfoEntity == null) {
			basicInfo.setCreateTime(new Date());
			basicInfo.setTenantId(customerInfoEntity.getTenantId());
			customerBasicInfoMapper.insert(basicInfo);
			log.info("用户custId = {}首次开户保存基础资料成功！", custId);
		} else {
			basicInfo.setUpdateTime(new Date());
			basicInfo.setId(basicInfoEntity.getId());
			customerBasicInfoMapper.updateByPrimaryKeySelective(basicInfo);
			log.info("用户custId = {}修改基础资料成功！", custId);
		}
		//实名信息
		CustomerRealnameVerifyEntity realnameVerify = customerRealnameVerifyMapper.selectByCustId(custId);
		if (realnameVerify == null) {
			realnameVerifyDTO.setIsDeleted(0);
			realnameVerifyDTO.setCreateTime(new Date());
			realnameVerifyDTO.setIdKind(basicInfo.getIdKind() == null ? null : String.valueOf(basicInfo.getIdKind()));
			realnameVerifyDTO.setTenantId(customerInfoEntity.getTenantId());
			customerRealnameVerifyMapper.insertSelective(realnameVerifyDTO);
			log.info("用户custId = {}首次实名认证保存成功！", custId);
		} else {
			realnameVerifyDTO.setId(realnameVerify.getId());
			customerRealnameVerifyMapper.updateByPrimaryKeySelective(realnameVerifyDTO);
		}
		//理财账户
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		String accountId = idGenerateUtils.generateId();
		initPassword = StringUtil.isNotBlank(initPassword) ? initPassword : idGenerateUtils.lastSixDigitsOfTheIdCard(realnameVerifyDTO.getIdCard());
		if (financingAccount == null) {
			financingAccount = new CustomerFinancingAccountEntity();
			financingAccount.setCustId(customerInfo.getId());
			financingAccount.setAccountId(accountId);
			financingAccount.setPassword(new BCryptPasswordEncoder().encode(initPassword));
			financingAccount.setTenantId(customerInfo.getTenantId());
			financingAccount.setStatus(FinancingAccountStatus.PRE_APPROVED.getCode());
			financingAccount.setCreateTime(new Date());
			financingAccount.setAccountType(FinancingAccountType.PERSONAL.getCode());
			customerFinancingAccountMapper.insert(financingAccount);
		} else {
			financingAccount.setCreateTime(new Date());
			financingAccount.setUpdateTime(new Date());
			financingAccount.setStatus(FinancingAccountStatus.PRE_APPROVED.getCode());
			financingAccount.setAccountType(FinancingAccountType.PERSONAL.getCode());
			financingAccount.setPassword(new BCryptPasswordEncoder().encode(initPassword));
			customerFinancingAccountMapper.updateByPrimaryKeySelective(financingAccount);
			accountId = financingAccount.getAccountId();
		}

//		if (customerOpenAccount.getOpenChannel() != null
//			&& customerOpenAccount.getOpenChannel().equals(OpenChannel.OFFLINE.getCode())) {
//			log.info("线下个人开户custId={}，开通各个交易账号", custId);
//			OpenAccountDTO openAccountDTO = new OpenAccountDTO();
//			openAccountDTO.setPiFlag(1);
//			openAccountDTO.setCustId(custId);
//			openAccountDTO.setAccountType(1);
//			openAccountDTO.setRiskLevel(customerOpenAccount.getRiskLevel());
//			openAccountDTO.setExtAccountId(financingAccount.getAccountId());
//			openAccountDTO.setExpiryDate(customerOpenAccount.getExpiryDate());
//			appCustomerInfoService.fundOpenAccount(openAccountDTO);
//			log.info("个人户{}基金开户成功", custId);
//			appCustomerInfoService.bondOpenAccount(openAccountDTO);
//			log.info("个人户{}债券易开户成功", custId);
//			appCustomerInfoService.hldOpenAccount(openAccountDTO);
//			log.info("个人户{}活利得开户成功", custId);
//		}

		String name = basicInfo.getClientName();
		if (StringUtils.isEmpty(name)) {
			name = basicInfo.getGivenNameSpell();
		}
		Map<String, String> data = new HashMap<>();
		data.put("name", name);
		data.put("accountId", accountId);
		data.put("password", initPassword);
		return R.data(data);
	}

	@Override
	public R customerFundOpenAccount(CustomerOpenAccountDTO customerOpenAccount) {
		Long custId = customerOpenAccount.getCustId();
		CustomerFinancingAccountEntity financingAccount = customerFinancingAccountMapper.selectByCustId(custId);
		if (customerOpenAccount.getOpenChannel() != null
			&& customerOpenAccount.getOpenChannel().equals(OpenChannel.OFFLINE.getCode())) {
			log.info("线下个人开户custId={}，开通各个交易账号", custId);
			OpenAccountDTO openAccountDTO = new OpenAccountDTO();
			openAccountDTO.setPiFlag(1);
			openAccountDTO.setCustId(custId);
			openAccountDTO.setAccountType(1);
			openAccountDTO.setRiskLevel(customerOpenAccount.getRiskLevel());
			openAccountDTO.setExtAccountId(financingAccount.getAccountId());
			openAccountDTO.setExpiryDate(customerOpenAccount.getExpiryDate());
			appCustomerInfoService.fundOpenAccount(openAccountDTO);
			log.info("个人户{}基金开户成功", custId);
			appCustomerInfoService.bondOpenAccount(openAccountDTO);
			log.info("个人户{}债券易开户成功", custId);
			appCustomerInfoService.hldOpenAccount(openAccountDTO);
			log.info("个人户{}活利得开户成功", custId);
		}
		return R.success();
	}

	@Override
	public R customerStockOpenAccount(CustomerOpenAccountDTO customerOpenAccount) {

		String stockId = idGenerateUtils.generateStockId();

		OpenAccountReq openAccountReq = new OpenAccountReq();
		openAccountReq.setTradeAccount(stockId);
		openAccountReq.setFundAccount(stockId);
		CustomerBasicInfoDTO basicInfo = customerOpenAccount.getBasicInfo();
		CustomerRealnameVerifyDTO realnameVerify = customerOpenAccount.getRealnameVerify();

		openAccountReq.setClientName(basicInfo.getClientName());
		openAccountReq.setFirstName(basicInfo.getGivenName());
		openAccountReq.setLastName(basicInfo.getFamilyName());
		openAccountReq.setFirstNameEn(basicInfo.getGivenNameSpell());
		openAccountReq.setLastNameEn(basicInfo.getFamilyNameSpell());
		openAccountReq.setMaritalStatus(basicInfo.getMaritalStatus());
		openAccountReq.setPlaceOfBirth(basicInfo.getPlaceOfBirth());
		openAccountReq.setIdCardValidDateStart(realnameVerify.getIdCardValidDateStart());
		openAccountReq.setIdCardValidDateEnd(realnameVerify.getIdCardValidDateEnd());
		openAccountReq.setClientNameSpell(basicInfo.getClientNameSpell());
		openAccountReq.setEducationLevel(basicInfo.getEducationLevel());
		openAccountReq.setProfessionCode(basicInfo.getProfessionCode());
		openAccountReq.setAnnualIncome(basicInfo.getAnnualIncome());
		openAccountReq.setNetAsset(basicInfo.getNetAsset());
		openAccountReq.setExpectedCapitalSource(basicInfo.getExpectedCapitalSource());
		openAccountReq.setTradeFrequency(basicInfo.getTradeFrequency());
		openAccountReq.setFundAccountType(basicInfo.getFundAccountType());
		/**
		 * 国籍待处理，修改字典用国籍三位编码表示
		 */
		openAccountReq.setNationality("HKG");
		openAccountReq.setBirthDate(realnameVerify.getBirthday());
		openAccountReq.setGender(realnameVerify.getGender() == 1 ? "MALE" : "FEMALE");
		openAccountReq.setAreaCode(basicInfo.getAeCode());
		openAccountReq.setPhoneNumber(basicInfo.getPhoneNumber());
		openAccountReq.setEmail(basicInfo.getEmail());
		openAccountReq.setIdType(Integer.valueOf(realnameVerify.getIdKind()));
		openAccountReq.setIdNo(realnameVerify.getIdCard());
		/**
		 * 通讯地址国家 修改字典用国籍三位编码表示
		 */
		openAccountReq.setContactCountry("HKG");
		openAccountReq.setContactState(basicInfo.getContactProvinceName());
		openAccountReq.setContactCity(basicInfo.getContactCityName());
		openAccountReq.setContactDistrict(basicInfo.getContactCountyName());
		openAccountReq.setContactAddress(basicInfo.getContactDetailAddress());
		/**
		 * 通讯邮编
		 */
		openAccountReq.setContactPostal("123456");
		openAccountReq.setAccountType(basicInfo.getFundAccountType());
		/**
		 * 是否为证券从业者
		 */
		openAccountReq.setBrokerPractitioner(false);
		/**
		 * 是否为控股股东
		 */
		openAccountReq.setDecisionMaker(false);
		openAccountReq.setLanguage("zh_HK");
		openAccountReq.setUserRegion("CN");
		/**
		 * 开通三个市场
		 */
		openAccountReq.setIsOpenCnMarket(1);
		openAccountReq.setIsOpenHkMarket(1);
		openAccountReq.setIsOpenUsMarket(1);

		/**
		 * 税务信息
		 */
		List<CustomerTaxationInfoDTO> customerTaxationInfoDTOList = customerOpenAccount.getCustomerTaxationInfoDTOList();
		if (!customerTaxationInfoDTOList.isEmpty()) {
			/**
			 *
			 */
			openAccountReq.setCountryOfTax("HKG");
			openAccountReq.setTaxNumber(customerTaxationInfoDTOList.get(0).getTaxNumber());
		}
		/**
		 * 图片处理
		 */
		List<CustomerOpenImageDTO> customerOpenImageDTOList = customerOpenAccount.getCustomerOpenImageDTOList();

		// 签名图片
		List<CustomerOpenImageDTO> signatureImageList =
			customerOpenImageDTOList.stream().filter(vo -> 3 == vo.getImageLocation())
				.collect(Collectors.toList());
		if (null != signatureImageList && signatureImageList.size() > 0) {
			openAccountReq.setSignImage(signatureImageList.get(0).getStoragePath());
		}
		//地址图片
		List<CustomerOpenImageDTO> addressImageList =
			customerOpenImageDTOList.stream().filter(vo -> 6 == vo.getImageLocation())
				.collect(Collectors.toList());
		if (null != addressImageList && addressImageList.size() > 0) {
			openAccountReq.setAddressImageUrl(addressImageList.get(0).getStoragePath());
		}
		//身份证图片
		List<CustomerOpenImageDTO> idCardImageList =
			customerOpenImageDTOList.stream().filter(vo -> 1 == vo.getImageLocation())
				.collect(Collectors.toList());
		if (null != idCardImageList && idCardImageList.size() > 0) {
			openAccountReq.setIdCardIdFrontUrl(idCardImageList.get(0).getStoragePath());
		}

		log.info("请求柜台参数openAccountReq:{}", JsonUtil.toJson(openAccountReq));

		R result =counterOpenAccountClient.openAccount(openAccountReq);
		log.info("柜台返回参数result:{}", JsonUtil.toJson(result));
		if(result.isSuccess()){
			// 写入交易账号数据
			R res =customerTradeAccountService.saveTradeAccountAndSubAccount(openAccountReq,basicInfo.getCustId());
            log.info("写入交易账号数据,{}",JsonUtil.toJson(res));

		}else{
			return result;
		}
		return R.success();
	}

	public CustomerInfoVO defaultRegister(String phone, String areaCode, String tenantId) {
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
		//entity.setPassword(new BCryptPasswordEncoder().encode(GoldDictFactory.builder().code(DictConstant.DEFAULT_PASSWORD).getKey()));
		customerInfoMapper.insertSelective(entity);

		//本地理财账户开通
		CustomerFinancingAccountEntity financingAccount = new CustomerFinancingAccountEntity();
		financingAccount.setCustId(entity.getId());
		financingAccount.setTenantId(tenantId);
		financingAccount.setAccountId(idGenerateUtils.generateId());
		financingAccount.setStatus(FinancingAccountStatus.NOT_ACTIVE.getCode());
		customerFinancingAccountMapper.insert(financingAccount);

		CustomerInfoEntity newCustomer = customerInfoMapper.loadCustomerByUsername(phone, areaCode, tenantId);
		CustomerInfoVO customerInfoVO = new CustomerInfoVO();
		BeanUtils.copyProperties(newCustomer, customerInfoVO);
		customerInfoVO.setAccount(financingAccount.getAccountId());
		return customerInfoVO;
	}
}
