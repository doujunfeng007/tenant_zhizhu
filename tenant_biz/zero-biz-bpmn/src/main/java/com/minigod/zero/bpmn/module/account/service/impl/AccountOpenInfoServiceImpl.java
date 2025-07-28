package com.minigod.zero.bpmn.module.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.biz.common.constant.OpenApiConstant;
import com.minigod.zero.biz.common.enums.BankAccountType;
import com.minigod.zero.biz.common.enums.BankCardAuthSign;
import com.minigod.zero.biz.common.enums.BankCardStatus;
import com.minigod.zero.biz.common.utils.Base64;
import com.minigod.zero.biz.common.utils.FileOperaterUtil;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.bpm.enums.OpenAccountEnum;
import com.minigod.zero.bpm.feign.IBpmSecuritiesInfoClient;
import com.minigod.zero.bpmn.module.account.api.*;
import com.minigod.zero.bpmn.module.account.bo.OpenAccountBo;
import com.minigod.zero.bpmn.module.account.bo.OpenAccountCallbackBo;
import com.minigod.zero.bpmn.module.account.bo.OpenSignImgBo;
import com.minigod.zero.bpmn.module.account.ca.request.VerifyFourBo;
import com.minigod.zero.bpmn.module.account.ca.request.VerifyLivingBo;
import com.minigod.zero.bpmn.module.account.ca.response.ValidateStill;
import com.minigod.zero.bpmn.module.account.ca.response.VerifyFourFactor;
import com.minigod.zero.bpmn.module.account.dto.OpenAccountDTO;
import com.minigod.zero.bpmn.module.account.entity.*;
import com.minigod.zero.bpmn.module.account.enums.*;
import com.minigod.zero.bpmn.module.account.gdca.request.*;
import com.minigod.zero.bpmn.module.account.gdca.response.GdCaVerifyResult;
import com.minigod.zero.bpmn.module.account.mapper.*;
import com.minigod.zero.bpmn.module.account.properties.AccountPdfPropertis;
import com.minigod.zero.bpmn.module.account.service.*;
import com.minigod.zero.bpmn.module.account.vo.*;
import com.minigod.zero.bpmn.module.common.service.AddressService;
import com.minigod.zero.bpmn.module.constant.ErrorMessageConstant;
import com.minigod.zero.bpmn.module.constant.OpenAccountMessageConstant;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardInfo;
import com.minigod.zero.bpmn.module.deposit.entity.SecAccImgEntity;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositFundsEntity;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.deposit.mapper.BankCardInfoMapper;
import com.minigod.zero.bpmn.module.deposit.mapper.SecAccImgMapper;
import com.minigod.zero.bpmn.module.deposit.mapper.SecDepositFundsMapper;
import com.minigod.zero.bpmn.module.feign.ICashBankClient;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.*;
import com.minigod.zero.bpmn.module.feign.vo.DepositBankVO;
import com.minigod.zero.bpmn.module.feign.vo.ReceivingBankVO;
import com.minigod.zero.bpmn.utils.*;
import com.minigod.zero.ca.bo.ReqGatewaytokenCertApplyP10ServiceBo;
import com.minigod.zero.ca.bo.ReqGetCertDNBo;
import com.minigod.zero.ca.bo.ReqGetPDFInfoForSignJavaBo;
import com.minigod.zero.ca.bo.ReqSignP7ForPdfJavaBo;
import com.minigod.zero.ca.bo.sz.GetPDF;
import com.minigod.zero.ca.bo.sz.Location;
import com.minigod.zero.ca.bo.sz.request.Apply;
import com.minigod.zero.ca.bo.sz.response.ResultSzCa;
import com.minigod.zero.ca.feign.SzCaCertificateService;
import com.minigod.zero.cms.enums.SupportTypeEnum;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.constant.ProcessConstant;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.flow.core.utils.FlowUtil;
import com.minigod.zero.flow.core.utils.TaskUtil;
import com.minigod.zero.platform.enums.*;
import com.minigod.zero.platform.utils.EmailUtil;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.platform.utils.SmsUtil;
import com.minigod.zero.resource.feign.IOssClient;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.minigod.zero.bpmn.module.account.constants.DictTypeConstant.COUNTRY_OR_REGION;
import static com.minigod.zero.bpmn.module.account.constants.DictTypeConstant.TAX_REASON_TYPE;
import static com.minigod.zero.bpmn.module.account.enums.AccountPdfEnum.*;


/**
 * 客户开户详细资料
 *
 * @author Chill
 */
@Slf4j
@Service
public class AccountOpenInfoServiceImpl extends BaseServiceImpl<AccountOpenInfoMapper, AccountOpenInfoEntity> implements IAccountOpenInfoService {
	@Autowired
	private IAccountOpenImageService customerAccountOpenImageService;
	@Autowired
	private IAccountTaxationInfoService iAccountTaxationInfoService;
	@Autowired
	private IAccountAdditionalFileService openAccountAdditionalFileService;
	@Autowired
	private IAccountOpenApplicationService customerAccountOpenApplicationService;
	@Autowired
	private IFlowClient flowClient;
	@Autowired
	private CaService szCaService;
	@Autowired
	private GdCaService gdCaService;
	@Autowired
	private IAccountBankVerityInfoService openAccountBankVerityInfoService;
	@Autowired
	private SzCaCertificateService szCaCertificateService;
	@Autowired
	private IAccountCaVerityInfoService openAccountCaVerityInfoService;
	@Autowired
	private IAccountDepositInfoService openAccountDepositInfoService;
	@Autowired
	private ICustomerAccOpenReportGenerateService customerAccOpenReportGenerate;
	@Autowired
	private IAccountBackReasonService openAccountBackReasonService;
	@Autowired
	private AddressService sysAddressService;
	@Autowired
	private IDictBizClient dictBizClient;
	@Autowired
	private AccountOpenImageMapper customerAccountOpenImageMapper;
	@Autowired
	private AccountCaVerityInfoMapper openAccountCaVerityInfoMapper;
	@Autowired
	private AccountBankVerityInfoMapper openAccountBankVerityInfoMapper;
	@Autowired
	private AccountPropertyTypeMapper openAccountPropertyTypeMapper;
	@Autowired
	private AccountAdditionalFileMapper openAccountAdditionalFileMapper;
	@Autowired
	private AccountIdentityConfirmMapper openAccountIdentityConfirmMapper;
	@Autowired
	private AccountSupplementCertificateMapper openAccountSupplementCertificateMapper;
	@Autowired
	private OfficerSignatureTatementMapper accountOfficerSignatureTatementMapper;
	@Autowired
	private AccountTaxationInfoMapper openAccountTaxationInfoMapper;
	@Autowired
	private AccountW8benInfoMapper openAccountW8benInfoMapper;
	@Autowired
	private BankCardInfoMapper bankCardInfoMapper;

	@Autowired
	private ICashBankClient cashBankClient;

	@Autowired
	private IBpmSecuritiesInfoClient iBpmSecuritiesInfoClient;
	@Autowired
	private AccountPdfPropertis accountPdfPropertis;

	@Autowired
	private ICustomerInfoClient iCustomerInfoClient;

	@Autowired
	private AccountDepositInfoMapper accountDepositInfoMapper;

	@Autowired
	private SecDepositFundsMapper secDepositFundsMapper;

	@Autowired
	private AccountOpenApplicationMapper accountOpenApplicationMapper;

	@Autowired
	private SecAccImgMapper secAccImgMapper;
	@Resource
	private AccountAdditionalFileMapper accountAdditionalFileMapper;

	@Resource
	private RestTemplateUtil restTemplateUtil;
	@Resource
	private CustomerInvestKnowledgeQuestionnaireService investQuestionnaireService;
	@Resource
	private IOssClient ossClient;


	@Value("${path.defaultAccountPlacePath}")
	private String defaultAccountPlacePathValue;
	@Value("${path.bankProofPlacePath}")
	private String bankProofPlacePathValue;

	@Override
	public AccountOpenInfoVO queryByApplicationId(String applicationId) {
		AccountOpenInfoVO customerAccountOpenInfoVo = baseMapper.queryByApplicationId(applicationId);
		if (customerAccountOpenInfoVo == null) {
			log.warn("根据申请编号(applicationId:" + applicationId + ")未查询到客户开户详细资料!");
			return null;
		}
		//公司地址转换
		customerAccountOpenInfoVo.setCompanyRepublicName(dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfoVo.getCompanyRepublicName()).getData());
		customerAccountOpenInfoVo.setCompanyProvinceName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getCompanyProvinceName()));
		customerAccountOpenInfoVo.setCompanyCityName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getCompanyCityName()));
		customerAccountOpenInfoVo.setCompanyCountyName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getCompanyCountyName()));

		//联系地址转换
		customerAccountOpenInfoVo.setContactRepublicName(dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfoVo.getContactRepublicName()).getData());
		customerAccountOpenInfoVo.setContactProvinceName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getContactProvinceName()));
		customerAccountOpenInfoVo.setContactCityName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getContactCityName()));
		customerAccountOpenInfoVo.setContactCountyName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getContactCountyName()));

		//永久地址装换
		customerAccountOpenInfoVo.setPermanentRepublicName(dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfoVo.getPermanentRepublicName()).getData());
		customerAccountOpenInfoVo.setPermanentProvinceName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getPermanentProvinceName()));
		customerAccountOpenInfoVo.setPermanentCityName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getPermanentCityName()));
		customerAccountOpenInfoVo.setPermanentCountyName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getPermanentCountyName()));

		//证件地址转换
		customerAccountOpenInfoVo.setIdCardProvinceName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getIdCardProvinceName()));
		customerAccountOpenInfoVo.setIdCardCityName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getIdCardCityName()));
		customerAccountOpenInfoVo.setIdCardCountyName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getIdCardCountyName()));

		//住宅地址
		customerAccountOpenInfoVo.setFamilyRepublicName(dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfoVo.getFamilyRepublicName()).getData());
		customerAccountOpenInfoVo.setFamilyProvinceName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getFamilyProvinceName()));
		customerAccountOpenInfoVo.setFamilyCityName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getFamilyCityName()));
		customerAccountOpenInfoVo.setFamilyCountyName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getFamilyCountyName()));

		return customerAccountOpenInfoVo;
	}

	@Override
	public AccountOpenInfoVO queryByEmail(String email) {
		return baseMapper.queryByEmail(email, AuthUtil.getTenantCustId());
	}

	@Override
	public AccountOpenInfoVO queryByIdNo(String idNo) {
		return baseMapper.queryByIdNo(idNo, AuthUtil.getTenantCustId());
	}

	/**
	 * 根据用户ID查询开户信息
	 *
	 * @param userId
	 */
	@Override
	public AccountOpenInfoVO queryAccountOpenInfoByUserId(Long userId) {
		AccountOpenInfoVO customerAccountOpenInfoVo = baseMapper.queryByUserId(userId);
		if (customerAccountOpenInfoVo == null) {
			log.warn("根据用户ID(userId:" + userId + ")未查询到客户开户详细资料!");
			return null;
		}
		//公司地址转换
		customerAccountOpenInfoVo.setCompanyProvinceName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getCompanyProvinceName()));
		customerAccountOpenInfoVo.setCompanyCityName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getCompanyCityName()));
		customerAccountOpenInfoVo.setCompanyCountyName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getCompanyCountyName()));

		//联系地址转换
		customerAccountOpenInfoVo.setContactProvinceName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getContactProvinceName()));
		customerAccountOpenInfoVo.setContactCityName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getContactCityName()));
		customerAccountOpenInfoVo.setContactCountyName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getContactCountyName()));

		//永久地址装换
		customerAccountOpenInfoVo.setPermanentProvinceName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getPermanentProvinceName()));
		customerAccountOpenInfoVo.setPermanentCityName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getPermanentCityName()));
		customerAccountOpenInfoVo.setPermanentCountyName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getPermanentCountyName()));

		//证件地址转换
		customerAccountOpenInfoVo.setIdCardProvinceName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getIdCardProvinceName()));
		customerAccountOpenInfoVo.setIdCardCityName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getIdCardCityName()));
		customerAccountOpenInfoVo.setIdCardCountyName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getIdCardCountyName()));


		//住宅地址
		customerAccountOpenInfoVo.setFamilyProvinceName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getFamilyProvinceName()));
		customerAccountOpenInfoVo.setFamilyCityName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getFamilyCityName()));
		customerAccountOpenInfoVo.setFamilyCountyName(sysAddressService.getAddressName(customerAccountOpenInfoVo.getFamilyCountyName()));

		return customerAccountOpenInfoVo;
	}

	/**
	 * 根据用户ID查询开户信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public AccountOpenInfoVO queryByUserId(Long userId) {
		return baseMapper.queryByUserId(userId);
	}

	@Override
	public AccountDetailVO queryDetailByApplicationId(String applicationId) {
		AccountDetailVO customerAccountDetailVo = new AccountDetailVO();
		AccountOpenInfoVO customerAccountOpenInfoVo = baseMapper.queryByApplicationId(applicationId);
		customerAccountOpenInfoVo.setPlaceOfBirth(dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfoVo.getPlaceOfBirth()).getData());
		customerAccountOpenInfoVo.setNationality(dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfoVo.getNationality()).getData());
		//公司地址转换
		StringBuilder companyAddressDetail = new StringBuilder();
		companyAddressDetail.append(sysAddressService.getAddressName(customerAccountOpenInfoVo.getCompanyCountyName()));
		companyAddressDetail.append(customerAccountOpenInfoVo.getCompanyDetailAddress());
		customerAccountOpenInfoVo.setCompanyDetailAddress(companyAddressDetail.toString());
		customerAccountOpenInfoVo.setCompanyRepublicName(dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfoVo.getCompanyRepublicName()).getData());
		customerAccountOpenInfoVo.setCompanyProvinceName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getCompanyProvinceName()));
		customerAccountOpenInfoVo.setCompanyCityName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getCompanyCityName()));
		customerAccountOpenInfoVo.setCompanyCountyName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getCompanyCountyName()));

		//联系地址转换
		StringBuilder contactAddressDetail = new StringBuilder();
		contactAddressDetail.append(sysAddressService.getAddressName(customerAccountOpenInfoVo.getContactCountyName()));
		contactAddressDetail.append(customerAccountOpenInfoVo.getContactDetailAddress());
		customerAccountOpenInfoVo.setContactDetailAddress(contactAddressDetail.toString());
		customerAccountOpenInfoVo.setContactRepublicName(dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfoVo.getContactRepublicName()).getData());
		customerAccountOpenInfoVo.setContactProvinceName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getContactProvinceName()));
		customerAccountOpenInfoVo.setContactCityName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getContactCityName()));
		customerAccountOpenInfoVo.setContactCountyName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getContactCountyName()));

		//永久地址装换
		StringBuilder permanentAddressDetail = new StringBuilder();
		permanentAddressDetail.append(sysAddressService.getAddressName(customerAccountOpenInfoVo.getPermanentCountyName()));
		permanentAddressDetail.append(customerAccountOpenInfoVo.getPermanentDetailAddress());
		customerAccountOpenInfoVo.setPermanentDetailAddress(permanentAddressDetail.toString());
		customerAccountOpenInfoVo.setPermanentRepublicName(dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfoVo.getPermanentRepublicName()).getData());
		customerAccountOpenInfoVo.setPermanentProvinceName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getPermanentProvinceName()));
		customerAccountOpenInfoVo.setPermanentCityName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getPermanentCityName()));
		customerAccountOpenInfoVo.setPermanentCountyName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getPermanentCountyName()));


		//证件地址转换
		StringBuilder idCardAddressDetail = new StringBuilder();
		idCardAddressDetail.append(sysAddressService.getAddressName(customerAccountOpenInfoVo.getIdCardCountyName()));
		idCardAddressDetail.append(customerAccountOpenInfoVo.getIdCardDetailAddress());
		customerAccountOpenInfoVo.setIdCardDetailAddress(idCardAddressDetail.toString());
		customerAccountOpenInfoVo.setIdCardProvinceName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getIdCardProvinceName()));
		customerAccountOpenInfoVo.setIdCardCityName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getIdCardCityName()));
		customerAccountOpenInfoVo.setIdCardCountyName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getIdCardCountyName()));


		//家庭地址转换
		StringBuilder familyAddressDetail = new StringBuilder();
		familyAddressDetail.append(sysAddressService.getAddressName(customerAccountOpenInfoVo.getFamilyCountyName()));
		familyAddressDetail.append(customerAccountOpenInfoVo.getFamilyDetailAddress());
		customerAccountOpenInfoVo.setFamilyDetailAddress(familyAddressDetail.toString());
		customerAccountOpenInfoVo.setFamilyRepublicName(dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfoVo.getFamilyRepublicName()).getData());
		customerAccountOpenInfoVo.setFamilyProvinceName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getFamilyProvinceName()));
		customerAccountOpenInfoVo.setFamilyCityName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getFamilyCityName()));
		customerAccountOpenInfoVo.setFamilyCountyName(sysAddressService.getAddressCode(customerAccountOpenInfoVo.getFamilyCountyName()));


		customerAccountDetailVo.setInfo(customerAccountOpenInfoVo);
		customerAccountDetailVo.setBankVerityInfo(openAccountBankVerityInfoMapper.queryByApplicationId(applicationId));
		customerAccountDetailVo.setCaVerityInfo(openAccountCaVerityInfoMapper.queryByApplicationId(applicationId));
		customerAccountDetailVo.setImages(customerAccountOpenImageMapper.queryListByApplicationId(applicationId, null, null));
		List<AccountTaxationInfoVO> taxList = openAccountTaxationInfoMapper.queryListByApplicationId(applicationId);
		for (AccountTaxationInfoVO vo : taxList) {
			vo.setTaxCountry(dictBizClient.getValue(COUNTRY_OR_REGION, vo.getTaxCountry()).getData());
			vo.setReasonType(dictBizClient.getValue(TAX_REASON_TYPE, vo.getReasonType()).getData());
		}
		customerAccountDetailVo.setTaxationInfos(taxList);
		customerAccountDetailVo.setPropertyTypes(openAccountPropertyTypeMapper.queryListByApplicationId(applicationId));
		customerAccountDetailVo.setAdditionalFiles(openAccountAdditionalFileMapper.queryListByApplicationId(applicationId, null));
		customerAccountDetailVo.setAccountIdentityConfirm(openAccountIdentityConfirmMapper.queryByApplicationId(applicationId));
		customerAccountDetailVo.setAccountW8benInfo(openAccountW8benInfoMapper.queryByApplicationId(applicationId));
		customerAccountDetailVo.setAccountSupplementCertificate(openAccountSupplementCertificateMapper.queryByApplicationId(applicationId));
		customerAccountDetailVo.setAccountDepositInfo(openAccountDepositInfoService.queryByApplicationId(applicationId));
		customerAccountDetailVo.setAccountOfficerSignatureTatement(accountOfficerSignatureTatementMapper.queryByApplicationId(applicationId));
		return customerAccountDetailVo;
	}

	@Override
	public void amlCreate(String applicationId) {

	}

	@Override
	public void szCaAuth(String applicationId) {
		KeyPair keyPair = PKCSUtil.generageKeyPair();
		AccountOpenInfoVO customerAccountOpenInfoVo = baseMapper.queryByApplicationId(applicationId);
		AccountBankVerityInfoVO openAccountBankVerityInfo = openAccountBankVerityInfoService.queryByApplicationId(applicationId);
		AccountOpenImageVO imageOne = customerAccountOpenImageService.queryOneByApplicationId(applicationId, 1, 101);
		AccountOpenImageVO imageTwo = customerAccountOpenImageService.queryOneByApplicationId(applicationId, 1, 102);
		AccountOpenImageVO liveVideo = customerAccountOpenImageService.queryOneByApplicationId(applicationId, 9, 901);
		AccountOpenImageVO imageSign = customerAccountOpenImageService.queryOneByApplicationId(applicationId, 3, 301);
		String token = szCaService.token();

		//银行四要素
		VerifyFourBo verifyFourBo = new VerifyFourBo();
		verifyFourBo.setBankNo(openAccountBankVerityInfo.getBankCard());
		verifyFourBo.setName(openAccountBankVerityInfo.getClientName());
		verifyFourBo.setPhoneNumber(openAccountBankVerityInfo.getPhoneNumber());
		verifyFourBo.setIdNo(openAccountBankVerityInfo.getIdNo());
		VerifyFourFactor verifyFourFactor = szCaService.verifyFourFactor(verifyFourBo);
		if (!verifyFourFactor.getSuccess()) {
			throw new ServiceException("银行四要素认证失败");
		}

		//静默活体
		VerifyLivingBo verifyLivingBo = new VerifyLivingBo();
		verifyLivingBo.setUrl(liveVideo.getStoragePath());
		verifyLivingBo.setIdNo(openAccountBankVerityInfo.getIdNo());
		verifyLivingBo.setName(openAccountBankVerityInfo.getClientName());
		ValidateStill validateStill = szCaService.validateStill(verifyLivingBo);

		ReqGetCertDNBo reqGetCertDNBo = new ReqGetCertDNBo(openAccountBankVerityInfo.getClientName(), openAccountBankVerityInfo.getIdNo());
		log.info(JSON.toJSONString(reqGetCertDNBo));
		ResultSzCa<String> res = szCaCertificateService.reqGetCertDN(reqGetCertDNBo);
		if (res.getCode() == 0) {
			String certDn = res.getObj();
			Apply apply = new Apply();
			apply.setToken(token);

			apply.setCardedExpiryDate(customerAccountOpenInfoVo.getIdCardValidDateStart() + "-" + customerAccountOpenInfoVo.getIdCardValidDateEnd());
			apply.setSex(customerAccountOpenInfoVo.getSex() == 0 ? "男" : "女");
			apply.setMobileNo(openAccountBankVerityInfo.getPhoneNumber());
			apply.setCard(openAccountBankVerityInfo.getBankCard());
			apply.setUserName(openAccountBankVerityInfo.getClientName());
			apply.setIdNo(openAccountBankVerityInfo.getIdNo());

			apply.setProvince(PKCSUtil.parseCertDN(certDn, "ST"));
			apply.setCity(PKCSUtil.parseCertDN(certDn, "L"));
			apply.setContactAddr(customerAccountOpenInfoVo.getIdCardDetailAddress() + "");
			apply.setCardedPlace(StringUtils.isNotBlank(customerAccountOpenInfoVo.getSigningOrganization()) ? customerAccountOpenInfoVo.getSigningOrganization() : apply.getProvince() + apply.getCity());
			apply.setCertDn(certDn);
			apply.setCertP10(PKCSUtil.genereatePkcs10(keyPair, certDn));
			apply.setIdentityImgOne(ImageUtils.loadImgBase64ByUrl(imageOne.getStoragePath()).replace("data:image/jpeg;base64,", ""));
			apply.setIdentityImgTwo(ImageUtils.loadImgBase64ByUrl(imageTwo.getStoragePath()).replace("data:image/jpeg;base64,", ""));
			apply.setHumanBodyImg(validateStill.getImage());
			apply.setFourFactorNo(verifyFourFactor.getOrderNo());
			apply.setLivingOrderNo(validateStill.getOrderNo());
			ReqGatewaytokenCertApplyP10ServiceBo reqGatewaytokenCertApplyP10ServiceBo = new ReqGatewaytokenCertApplyP10ServiceBo(apply);
			log.info(JSON.toJSONString(reqGatewaytokenCertApplyP10ServiceBo));
			ResultSzCa<String> certSnResult = szCaCertificateService.reqGatewaytokenCertApplyP10Service(reqGatewaytokenCertApplyP10ServiceBo);
			log.info(JSON.toJSONString(certSnResult));
			if (certSnResult.getCode() == 0) {
				String certSn = certSnResult.getObj();

				GetPDF getPDF = new GetPDF();
				getPDF.setCertDN(certDn);
				getPDF.setUserName(openAccountBankVerityInfo.getClientName());
				getPDF.setCertSn(certSn);
				getPDF.setSignImg(ImageUtils.loadImgBase64ByUrl(imageSign.getStoragePath()).replace("data:image/jpeg;base64,", ""));
				getPDF.setIdCode(openAccountBankVerityInfo.getIdNo());
				getPDF.setXdpi("300");
				getPDF.setYdpi("300");
				List<Location> locations = new ArrayList<>();
				Location location = new Location();
				location.setPageNo(12);
				location.setX(50);
				location.setY(900);
				locations.add(location);
				getPDF.setLocations(locations);
				String path = customerAccOpenReportGenerate.makeOutputPath(applicationId, ACCOUNT_OPEN_REPORT_USER_FORM_REPORT);
				if (StringUtils.isBlank(path)) {
					path = customerAccOpenReportGenerate.generateReport(applicationId, ACCOUNT_OPEN_REPORT_USER_FORM_REPORT);
				}
				//File file = ImageUtils.pathToFile(path);
				File file = new File(path);
				ReqGetPDFInfoForSignJavaBo reqGetPDFInfoForSignJavaBo = new ReqGetPDFInfoForSignJavaBo(file, getPDF);
				log.info("reqGetPDFInfoForSignJava:" + JSON.toJSONString(reqGetPDFInfoForSignJavaBo));
				ResultSzCa<String> signJavaResult = szCaCertificateService.reqGetPDFInfoForSignJava(reqGetPDFInfoForSignJavaBo);
				log.info(JSON.toJSONString(signJavaResult));
				if (signJavaResult.getCode() == 0) {
					AccountCaVerityInfoEntity openAccountCaVerityInfoBo = new AccountCaVerityInfoEntity();
					openAccountCaVerityInfoBo.setApplicationId(applicationId);
					openAccountCaVerityInfoBo.setCaCertDn(certDn);
					openAccountCaVerityInfoBo.setUserId(customerAccountOpenInfoVo.getUserId());
					openAccountCaVerityInfoBo.setCaCertSn(certSn);
					openAccountCaVerityInfoBo.setFileHash(signJavaResult.getFileHash());
					openAccountCaVerityInfoBo.setFileId(signJavaResult.getFileID());
					openAccountCaVerityInfoBo.setCertTime(new Date());
					openAccountCaVerityInfoBo.setTenantId(customerAccountOpenInfoVo.getTenantId());
					openAccountCaVerityInfoBo.setCreateDept(customerAccountOpenInfoVo.getCreateDept());
					openAccountCaVerityInfoBo.setCreateTime(new Date());
					openAccountCaVerityInfoBo.setUpdateTime(new Date());
					openAccountCaVerityInfoBo.setCreateUser(customerAccountOpenInfoVo.getUserId());
					openAccountCaVerityInfoBo.setUpdateUser(customerAccountOpenInfoVo.getUserId());
					openAccountCaVerityInfoService.save(openAccountCaVerityInfoBo);
					String p1Code = PKCSUtil.genereatePkcs1(keyPair, openAccountCaVerityInfoBo.getFileHash());
					ReqSignP7ForPdfJavaBo reqSignP7ForPdfJavaBo = new ReqSignP7ForPdfJavaBo(openAccountCaVerityInfoBo.getCaCertDn(), openAccountCaVerityInfoBo.getCaCertSn(), openAccountCaVerityInfoBo.getFileId(), p1Code);

					ResultSzCa<String> resultSzCa = szCaCertificateService.reqSignP7ForPdfJava(reqSignP7ForPdfJavaBo);
					if (resultSzCa.getCode() != 0) {
						throw new ServiceException(resultSzCa.getMsg());
					} else {
						byte[] imgByte = Base64.decode(resultSzCa.getFile());
						InputStream inputStream = new ByteArrayInputStream(imgByte);
						String accountOpenPath = customerAccOpenReportGenerate.makeOutputPath(applicationId, ACCOUNT_OPEN_REPORT_USER_FORM_REPORT);
						File openAccountFile = new File(accountOpenPath);
						FileUtils.writeFromStream(inputStream, openAccountFile);
					}
					log.info(JSON.toJSONString(resultSzCa));
				} else {
					throw new ServiceException(signJavaResult.getMsg());
				}
			} else {
				throw new ServiceException(certSnResult.getMsg());
			}
		} else {
			throw new ServiceException(res.getMsg());
		}
	}

	/**
	 * 广东CA认证
	 *
	 * @param applicationId
	 */
	@Override
	public void gdCaAuth(String applicationId) {
		KeyPair keyPair = PKCSUtil.generageKeyPair();
		//客户开户详细资料
		AccountOpenInfoVO customerAccountOpenInfoVo = baseMapper.queryByApplicationId(applicationId);
		if (customerAccountOpenInfoVo == null) {
			log.error("-->客户开户详细资料不存在,applicationId:{}", applicationId);
			throw new ServiceException(String.format("客户开户详细资料不存在,applicationId:%s", applicationId));
		}
		if (customerAccountOpenInfoVo.getOpenAccountAccessWay() == OpenAccountEnum.OpenAccountAccessWay.OFFLINE.getCode()) {
			log.info("-->线下开户不需要进行CA认证!");
			return;
		}
		//银行卡四要素验证信息
		AccountBankVerityInfoVO openAccountBankVerityInfo = openAccountBankVerityInfoService.queryByApplicationId(applicationId);
		if (openAccountBankVerityInfo == null) {
			log.error("-->银行卡四要素验证信息不存在,applicationId:{}", applicationId);
			throw new ServiceException(String.format("银行卡四要素验证信息不存在,applicationId:%s", applicationId));
		}
		//银行卡四要素验证次数
		if (openAccountBankVerityInfo.getVerifyCount() >= 3) {
			log.error("-->银行卡四要素验证已超过三次,跳过该申请记录的CA认证,不再进行认证,请退回并修改相应信息,applicationId:{}", applicationId);
			throw new ServiceException(String.format("银行卡四要素验证失败次数已超过3次,不再进行认证,请退回并修改相应信息,applicationId:%s", applicationId));
		}
		//身份证人像面
		AccountOpenImageVO imageFront = customerAccountOpenImageService.queryOneByApplicationId(applicationId, 1, 101);
		if (imageFront == null) {
			log.error("-->身份证人像面不存在,applicationId:{}", applicationId);
			throw new ServiceException(String.format("身份证人像面不存在,applicationId:%s", applicationId));
		}
		//身份证国徽面
		AccountOpenImageVO imageBack = customerAccountOpenImageService.queryOneByApplicationId(applicationId, 1, 102);
		if (imageBack == null) {
			log.error("-->身份证国徽面不存在,applicationId:{}", applicationId);
			throw new ServiceException(String.format("身份证国徽面不存在,applicationId:%s", applicationId));
		}
		//活体视频
		AccountOpenImageVO liveVideo = customerAccountOpenImageService.queryOneByApplicationId(applicationId, 9, 901);
		if (liveVideo == null) {
			log.error("-->活体视频不存在,applicationId:{}", applicationId);
			throw new ServiceException(String.format("活体视频不存在,applicationId:%s", applicationId));
		}
		//签章图片
		AccountOpenImageVO imageSign = customerAccountOpenImageService.queryOneByApplicationId(applicationId, 3, 301);
		if (imageSign == null) {
			log.error("-->签章图片不存在,applicationId:{}", applicationId);
			throw new ServiceException(String.format("签章图片不存在,applicationId:%s", applicationId));
		}

		//1.银行卡信息四要素对比
		UnionPayVerifyBo unionPayVerifyBo = new UnionPayVerifyBo();
		unionPayVerifyBo.setBankNo(openAccountBankVerityInfo.getBankCard());
		unionPayVerifyBo.setName(openAccountBankVerityInfo.getClientName());
		unionPayVerifyBo.setBankMobileArea(openAccountBankVerityInfo.getPhoneArea());
		unionPayVerifyBo.setBankMobile(openAccountBankVerityInfo.getPhoneNumber());
		unionPayVerifyBo.setIdCode(openAccountBankVerityInfo.getIdNo());
		GdCaVerifyResult unionPayVerifyResult = gdCaService.unionPayVerifyAndUpdate(unionPayVerifyBo);
		if (!StringUtils.isNotBlank(unionPayVerifyResult.getBankAuthId())) {
			log.error("-->银行卡四要素对比失败,applicationId:{}", applicationId);
			throw new ServiceException("银行四要素认证失败!");
		} else {
			log.info("-->银行卡四要素对比成功,applicationId:{}", applicationId);
		}

		//2.活体对比
		LivingBodyValidateBo verifyLivingBo = new LivingBodyValidateBo();
		verifyLivingBo.setVideoUrl(liveVideo.getStoragePath());
		verifyLivingBo.setIdCode(customerAccountOpenInfoVo.getIdNo());
		verifyLivingBo.setName(customerAccountOpenInfoVo.getClientName());
		verifyLivingBo.setNationality(customerAccountOpenInfoVo.getNationality());
		verifyLivingBo.setBirth(customerAccountOpenInfoVo.getBirthday());
		verifyLivingBo.setSex(customerAccountOpenInfoVo.getSex() == 0 ? "男" : "女");
		verifyLivingBo.setCity(customerAccountOpenInfoVo.getIdCardCityName());
		verifyLivingBo.setProvince(customerAccountOpenInfoVo.getIdCardProvinceName());
		verifyLivingBo.setAddress(customerAccountOpenInfoVo.getIdCardDetailAddress());
		verifyLivingBo.setFrontUrl(imageFront.getStoragePath());
		verifyLivingBo.setBackUrl(imageBack.getStoragePath());
		GdCaVerifyResult livingBodyValidateResult = gdCaService.livingBodyValidate(verifyLivingBo);
		if (StringUtils.isBlank(livingBodyValidateResult.getAuthenticationId())) {
			log.error("-->静默活体检测失败,applicationId:{}", applicationId);
			throw new ServiceException("静默活体检测失败!");
		} else {
			log.info("-->静默活体检测成功,applicationId:{}", applicationId);
		}

		CertDnByGdCABo certDnByGdCABo = new CertDnByGdCABo();
		certDnByGdCABo.setName(openAccountBankVerityInfo.getClientName());
		certDnByGdCABo.setIdCode(openAccountBankVerityInfo.getIdNo());
		certDnByGdCABo.setCity(customerAccountOpenInfoVo.getIdCardCityName());
		certDnByGdCABo.setProvince(customerAccountOpenInfoVo.getIdCardProvinceName());
		//3. 申请证书主题
		GdCaVerifyResult certDnByGdCAResult = gdCaService.getCertDnByGdCA(certDnByGdCABo);
		if (StringUtils.isNotBlank(certDnByGdCAResult.getSubjectDn())) {
			log.info("-->申请证书主题成功,applicationId:{}, 证书主题:{}", applicationId, certDnByGdCAResult.getSubjectDn());
			String certDn = certDnByGdCAResult.getSubjectDn();
			ApplyPersonalCertBO applyPersonalCertBO = new ApplyPersonalCertBO();
			applyPersonalCertBO.setBankAuthId(unionPayVerifyResult.getBankAuthId());
			applyPersonalCertBO.setAuthenticationId(livingBodyValidateResult.getAuthenticationId());
			applyPersonalCertBO.setP10(PKCSUtil.genereatePkcs10(keyPair, certDn));
			//4. 申请数字证书
			GdCaVerifyResult gdCaVerifyResult = gdCaService.applyPersonalCert(applyPersonalCertBO);
			if (StringUtils.isNotBlank(gdCaVerifyResult.getSignCertDn())
				&& StringUtils.isNotBlank(gdCaVerifyResult.getSignCertSn())
				&& StringUtils.isNotBlank(gdCaVerifyResult.getExpireDate())) {
				log.info("-->申请数字证书成功,applicationId:{},证书序列号:{}", applicationId, gdCaVerifyResult.getSignCertSn());
				String certSn = gdCaVerifyResult.getSignCertSn();
				String path = customerAccOpenReportGenerate.generateReport(applicationId, ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT);
				log.info("-->生成完成的PDF路径:{}", path);
				//待签名的PDF
				File pdfFile = new File(path);
				CreateEmptySignatureBo createEmptySignatureBo = new CreateEmptySignatureBo();
				//盖章图片
				try {
					File signatureImage = ImageUtils.urlToFile(imageSign.getStoragePath());
					createEmptySignatureBo.setSignatureImage(signatureImage);
				} catch (MalformedURLException e) {
					log.error("盖章图片读取异常,盖章图片路径:" + imageSign.getStoragePath() + ",异常详情:", e);
					throw new ServiceException("盖章图片读取异常,异常详情:" + e.getMessage());
				}
				createEmptySignatureBo.setPdf(pdfFile);
				//--这里暂时不需要往PDF上盖章,PDF文件里面已经配置了签字图片的位置, 并可以跟据URL显示用户的签名图片，但由于推送PDF接口签名图片和位置是必填项,所以这里还需要传。
				createEmptySignatureBo.setWidth(0);
				createEmptySignatureBo.setHeight(0);

				//5. 推送PDF
				GdCaVerifyResult createEmptySignatureResult = gdCaService.createEmptySignature(createEmptySignatureBo);
				if (!StringUtils.isBlank(createEmptySignatureResult.getSignHash())) {
					AccountCaVerityInfoEntity openAccountCaVerityInfoBo = new AccountCaVerityInfoEntity();
					openAccountCaVerityInfoBo.setApplicationId(applicationId);
					openAccountCaVerityInfoBo.setCaCertDn(certDn);
					openAccountCaVerityInfoBo.setUserId(customerAccountOpenInfoVo.getUserId());
					openAccountCaVerityInfoBo.setCaCertSn(certSn);
					openAccountCaVerityInfoBo.setFileHash(createEmptySignatureResult.getSignHash());
					openAccountCaVerityInfoBo.setFileId(createEmptySignatureResult.getPdfId());
					openAccountCaVerityInfoBo.setTenantId(customerAccountOpenInfoVo.getTenantId());
					openAccountCaVerityInfoBo.setCreateDept(customerAccountOpenInfoVo.getCreateDept());
					openAccountCaVerityInfoBo.setCertTime(new Date());
					openAccountCaVerityInfoBo.setCreateTime(new Date());
					openAccountCaVerityInfoBo.setUpdateTime(new Date());
					openAccountCaVerityInfoBo.setCreateUser(customerAccountOpenInfoVo.getUserId());
					openAccountCaVerityInfoBo.setUpdateUser(customerAccountOpenInfoVo.getUserId());
					openAccountCaVerityInfoService.save(openAccountCaVerityInfoBo);

					log.info("-->推送PDF成功,applicationId:{},待签名的hash:{}", applicationId, createEmptySignatureResult.getSignHash());
					String p1Code = PKCSUtil.genereatePkcs1(keyPair, createEmptySignatureResult.getSignHash());
					if (StringUtils.isBlank(p1Code)) {
						log.error("-->生成p1Code失败,applicationId:{}", applicationId);
						throw new ServiceException("生成p1Code失败!");
					}
					log.info("-->生成p1Code成功,applicationId:{}, PdfId:{}, SignCertSn:{}, 私钥签名数据:{}",
						applicationId,
						createEmptySignatureResult.getPdfId(),
						openAccountCaVerityInfoBo.getCaCertSn(),
						p1Code);

					MergePdfHashBo mergePdfHashBo = new MergePdfHashBo();
					mergePdfHashBo.setSignCertSn(gdCaVerifyResult.getSignCertSn());
					mergePdfHashBo.setPdfId(createEmptySignatureResult.getPdfId());
					mergePdfHashBo.setP1Data(p1Code);

					//6.签署合成
					GdCaVerifyResult mergePdfHashResult = gdCaService.mergePdfHash(mergePdfHashBo);
					if (mergePdfHashResult == null || StringUtils.isBlank(mergePdfHashResult.getSignPdf())) {
						log.error("-->签署合成失败,applicationId:", applicationId);
						throw new ServiceException("签署合成失败!!");
					} else {
						byte[] imgByte = Base64.decode(mergePdfHashResult.getSignPdf());
						InputStream inputStream = new ByteArrayInputStream(imgByte);
						String accountOpenPath = customerAccOpenReportGenerate.makeOutputPath(applicationId, ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT);
						File openAccountFile = new File(accountOpenPath);
						FileUtils.writeFromStream(inputStream, openAccountFile);
						log.info("-->签署合成完成的PDF路径:{}", accountOpenPath);
					}

					//7.更新账户开户信息
					customerAccountOpenInfoVo.setCaSignHashCode(createEmptySignatureResult.getSignHash());
					int count = baseMapper.updateById(customerAccountOpenInfoVo);
					if (count > 0) {
						log.info("-->更新账户开户信息成功,applicationId:{}", applicationId);
					} else {
						log.error("-->更新账户开户信息失败,applicationId:{}", applicationId);
						throw new ServiceException("更新账户开户信息失败!");
					}
				} else {
					log.error("-->推送PDF失败,applicationId:", applicationId);
					throw new ServiceException("推送PDF失败!");
				}
			} else {
				log.error("-->认证并申请证书失败,applicationId:", applicationId);
				throw new ServiceException("认证并申请证书失败!");
			}
		} else {
			log.error("-->申请证书主题失败,applicationId:", applicationId);
			throw new ServiceException("申请证书主题失败!");
		}
	}

	@Override
	public void updateClientId(String applicationId, String clientId, Integer idKind) {
		AccountOpenInfoVO customerAccountOpenInfo = baseMapper.queryByClientId(clientId);
		if (customerAccountOpenInfo != null) {
			throw new ServiceException("该客户号已存在!");
		}
		baseMapper.updateClientId(applicationId, clientId);
	}

	@Override
	public String generateClientId(Integer idKind) {
		return null;
	}

	@Override
	public void downloadDoc(String applicationId, HttpServletResponse httpServletResponse) {
		AccountOpenInfoVO customerAccountOpenInfoVo = baseMapper.queryByApplicationId(applicationId);
		String zipFileName = (StringUtils.isNotBlank(customerAccountOpenInfoVo.getClientName()) ? customerAccountOpenInfoVo.getClientName() : customerAccountOpenInfoVo.getClientNameSpell()) + "-" +
			(StringUtils.isNotBlank(customerAccountOpenInfoVo.getClientId()) ? customerAccountOpenInfoVo.getClientId() + "-" : "") +
			new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String path = accountPdfPropertis.getPlacePath() + "/" + customerAccountOpenInfoVo.getTenantId() + "/" + applicationId;
		FolderToZipUtil.zip(zipFileName, path, httpServletResponse);
	}

	@Override
	public void generatePdf(String applicationId) {
		/*try {
			String path1 = customerAccOpenReportGenerate.generateReport(applicationId, ACCOUNT_OPEN_REPORT_USER_FORM_REPORT);
			log.info("path1:" + path1);
			if (StringUtils.isNotBlank(path1)) {
				openAccountAdditionalFileService.deleteByApplicationAndTypeAndFileType(applicationId, 3, ACCOUNT_OPEN_REPORT_USER_FORM_REPORT.getValue());
				openAccountAdditionalFileService.uploadAdditionalFile(applicationId, 3, ACCOUNT_OPEN_REPORT_USER_FORM_REPORT.getValue(), ACCOUNT_OPEN_REPORT_USER_FORM_REPORT.getName(), FileUtil.getMultipartFile(FileUtils.file(path1)));
			}
		} catch (Exception e) {
			log.error("ACCOUNT_OPEN_REPORT_USER_FORM_REPORT 异常:", e);
		}
		try {
			String path2 = customerAccOpenReportGenerate.generateReport(applicationId, HONG_KONG_STOCK_FEE_SCHEDULE);
			log.info("path2:" + path2);
			if (StringUtils.isNotBlank(path2)) {
				openAccountAdditionalFileService.deleteByApplicationAndTypeAndFileType(applicationId, 3, HONG_KONG_STOCK_FEE_SCHEDULE.getValue());
				openAccountAdditionalFileService.uploadAdditionalFile(applicationId, 3, HONG_KONG_STOCK_FEE_SCHEDULE.getValue(), HONG_KONG_STOCK_FEE_SCHEDULE.getName(), FileUtil.getMultipartFile(FileUtils.file(path2)));
			}
		} catch (Exception e) {
			log.error("HONG_KONG_STOCK_FEE_SCHEDULE 异常:", e);
		}
		try {
			String path3 = customerAccOpenReportGenerate.generateReport(applicationId, EXECUTE_TRADING_DISCLOSURE_DOCUMENTS_ON_OPTIMAL_TERMS);
			log.info("path3:" + path3);
			if (StringUtils.isNotBlank(path3)) {
				openAccountAdditionalFileService.deleteByApplicationAndTypeAndFileType(applicationId, 3, EXECUTE_TRADING_DISCLOSURE_DOCUMENTS_ON_OPTIMAL_TERMS.getValue());
				openAccountAdditionalFileService.uploadAdditionalFile(applicationId, 3, EXECUTE_TRADING_DISCLOSURE_DOCUMENTS_ON_OPTIMAL_TERMS.getValue(), EXECUTE_TRADING_DISCLOSURE_DOCUMENTS_ON_OPTIMAL_TERMS.getName(), FileUtil.getMultipartFile(FileUtils.file(path3)));
			}
		} catch (Exception e) {
			log.error("EXECUTE_TRADING_DISCLOSURE_DOCUMENTS_ON_OPTIMAL_TERMS 异常:", e);
		}
		try {
			openAccountAdditionalFileService.deleteByApplicationAndTypeAndFileType(applicationId, 3, ACCOUNT_OPEN_REPORT_SECURITIES_TRADING_AGREEMENT.getValue());
			String path4 = customerAccOpenReportGenerate.generateReport(applicationId, AccountPdfEnum.ACCOUNT_OPEN_REPORT_SECURITIES_TRADING_AGREEMENT);
			log.info("path4:" + path4);
			if (StringUtils.isNotBlank(path4)) {
				openAccountAdditionalFileService.uploadAdditionalFile(applicationId, 3, ACCOUNT_OPEN_REPORT_SECURITIES_TRADING_AGREEMENT.getValue(), ACCOUNT_OPEN_REPORT_SECURITIES_TRADING_AGREEMENT.getName(), FileUtil.getMultipartFile(FileUtils.file(path4)));
			}
		} catch (Exception e) {
			log.error("ACCOUNT_OPEN_REPORT_SECURITIES_TRADING_AGREEMENT异常:", e);
		}*/
		try {
			openAccountAdditionalFileService.deleteByApplicationAndTypeAndFileType(applicationId, 3, ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT.getValue());
			String path5 = customerAccOpenReportGenerate.generateReport(applicationId, AccountPdfEnum.ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT);
			log.info("path5:" + path5);
			if (StringUtils.isNotBlank(path5)) {
				openAccountAdditionalFileService.uploadAdditionalFile(applicationId, 3, ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT.getValue(), ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT.getName(), FileUtil.getMultipartFile(FileUtils.file(path5)));
			}
		} catch (Exception e) {
			log.error("ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT异常:", e);
		}
	}

	@Override
	public void generateW8Pdf(String applicationId) {
		try {
			String path5 = customerAccOpenReportGenerate.generateReport(applicationId, ACCOUNT_OPEN_REPORT_USER_W8_REPORT);
			log.info("path5:" + path5);
			if (StringUtils.isNotBlank(path5)) {
				openAccountAdditionalFileService.deleteByApplicationAndTypeAndFileType(applicationId, 3, ACCOUNT_OPEN_REPORT_USER_W8_REPORT.getValue());
				openAccountAdditionalFileService.uploadAdditionalFile(applicationId, 3, ACCOUNT_OPEN_REPORT_USER_W8_REPORT.getValue(), ACCOUNT_OPEN_REPORT_USER_W8_REPORT.getName(), FileUtil.getMultipartFile(FileUtils.file(path5)));
				upW8Time(applicationId);
			}
		} catch (Exception e) {
			log.error("ACCOUNT_OPEN_REPORT_USER_W8_REPORT异常:", e);
		}
	}

	private void upW8Time(String applicationId) {
		AccountOpenInfoVO customerAccountOpenInfoVo = baseMapper.queryByApplicationId(applicationId);
		if (customerAccountOpenInfoVo == null) {
			log.error("更新W8时间失败,applicationId:{}", applicationId);
			return;
		}
		customerAccountOpenInfoVo.setW8AgreementTime(new Date());
		baseMapper.updateById(customerAccountOpenInfoVo);
	}


	@Override
	public void generateSelfPdf(String applicationId) {
		try {
			openAccountAdditionalFileService.deleteByApplicationAndTypeAndFileType(applicationId, 3, ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT.getValue());
			String path5 = customerAccOpenReportGenerate.generateReport(applicationId, ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT);
			log.info("path5:" + path5);
			if (StringUtils.isNotBlank(path5)) {
				openAccountAdditionalFileService.uploadAdditionalFile(applicationId, 3, ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT.getValue(), ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT.getName(), FileUtil.getMultipartFile(FileUtils.file(path5)));
			}
		} catch (Exception e) {
			log.error("ACCOUNT_OPEN_REPORT_USER_W8_REPORT异常:", e);
		}
	}

	@Override
	public void generateMarginPdf(String applicationId) {
		try {
			String path3 = customerAccOpenReportGenerate.generateReport(applicationId, AccountPdfEnum.ACCOUNT_OPEN_REPORT_MARGIN_TRADING_AGREEMENT);
			log.info("path3:" + path3);
			if (StringUtils.isNotBlank(path3)) {
				openAccountAdditionalFileService.deleteByApplicationAndTypeAndFileType(applicationId, 3, ACCOUNT_OPEN_REPORT_MARGIN_TRADING_AGREEMENT.getValue());
				openAccountAdditionalFileService.uploadAdditionalFile(applicationId, 3, ACCOUNT_OPEN_REPORT_MARGIN_TRADING_AGREEMENT.getValue(), ACCOUNT_OPEN_REPORT_MARGIN_TRADING_AGREEMENT.getName(), FileUtil.getMultipartFile(FileUtils.file(path3)));
			}
		} catch (Exception e) {
			log.error("ACCOUNT_OPEN_REPORT_MARGIN_TRADING_AGREEMENT 异常:", e);
		}
	}

	@Override
	public void generatePlaceFile(String applicationId) {
		AccountOpenInfoVO customerAccountOpenInfoVo = baseMapper.queryByApplicationId(applicationId);
		String toDay = DateUtil.format(new Date(), "YYYYMMdd");
		List<AccountOpenImageVO> depositImage = customerAccountOpenImageService.queryListByApplicationId(applicationId, null);
		Map<String, Integer> codeIndexMap = new HashMap<>();
		String imagePath = "";

		String defaultPlacePath = defaultPlacePath(toDay);
		List<AccountAdditionalFileVO> openFiles = openAccountAdditionalFileService.queryListByApplicationId(applicationId, null, null);
		for (AccountAdditionalFileVO file : openFiles) {
			if (file.getType() == 3 && (file.getFileType() == ACCOUNT_OPEN_REPORT_SECURITIES_TRADING_AGREEMENT.getValue() || file.getFileType() == HONG_KONG_STOCK_FEE_SCHEDULE.getValue() || file.getFileType() == EXECUTE_TRADING_DISCLOSURE_DOCUMENTS_ON_OPTIMAL_TERMS.getValue())) {
				continue;
			}
			CmsPdfPlaceEnum imagePlaceEnum = CmsPdfPlaceEnum.find(file.getType(), file.getFileType());
			String key = imagePlaceEnum.getCode();
			if (codeIndexMap.get(key) == null) {
				codeIndexMap.put(key, 1);
			} else {
				codeIndexMap.put(key, codeIndexMap.get(key) + 1);
			}
			String fileName = generateSecFileName(customerAccountOpenInfoVo.getClientId(), customerAccountOpenInfoVo.getAeCode(), toDay, imagePlaceEnum.getCode(), codeIndexMap.get(key));
			if (imagePlaceEnum == CmsPdfPlaceEnum.BANK_PROOF || imagePlaceEnum == CmsPdfPlaceEnum.BANK_PROOF_ADDITIONAL) {
				imagePath = bankProofPlacePath(customerAccountOpenInfoVo.getClientId(), toDay);
				String filePath = imagePath + fileName + file.getFileExtName();
				if (filePath.endsWith(".png") || filePath.endsWith(".jpg") || filePath.endsWith(".gif") || filePath.endsWith(".jpeg") || filePath.endsWith(".tif")) {
					ImageToPDF.imageUrlToPdf(file.getFilePath(), imagePath + fileName + ".pdf");
				} else {
					FileOperaterUtil.fileUpload(filePath, ImageUtils.loadInputStream(file.getFilePath()));
				}
			}
			String path = generateSecFileName(customerAccountOpenInfoVo.getClientId(), customerAccountOpenInfoVo.getAeCode(), toDay, imagePlaceEnum.getCode(), codeIndexMap.get(key));
			String filePath = defaultPlacePath + path + file.getFileExtName();
			if (filePath.endsWith(".png") || filePath.endsWith(".jpg") || filePath.endsWith(".gif") || filePath.endsWith(".jpeg") || filePath.endsWith(".tif")) {
				ImageToPDF.imageUrlToPdf(file.getFilePath(), defaultPlacePath + path + ".pdf");
			} else {
				FileOperaterUtil.fileUpload(filePath, ImageUtils.loadInputStream(file.getFilePath()));
			}

		}
		for (AccountOpenImageVO image : depositImage) {
			CmsImagePlaceEnum imagePlaceEnum = CmsImagePlaceEnum.find(image.getImageLocation());
			if (image.getImageLocation() == 9) {
				continue;
			}
			String key = imagePlaceEnum.getCode();
			if (codeIndexMap.get(key) == null) {
				codeIndexMap.put(key, 1);
			} else {
				codeIndexMap.put(key, codeIndexMap.get(key) + 1);
			}
			String fileName = generateSecFileName(customerAccountOpenInfoVo.getClientId(), customerAccountOpenInfoVo.getAeCode(), toDay, imagePlaceEnum.getCode(), codeIndexMap.get(key));
			if (imagePlaceEnum == CmsImagePlaceEnum.BANK_PROOF || imagePlaceEnum == CmsImagePlaceEnum.GOLDEN_CREDENTIALS) {
				imagePath = bankProofPlacePath(customerAccountOpenInfoVo.getClientId(), toDay);
				String filePath = imagePath + fileName + image.getExtName();
				if (filePath.endsWith(".png") || filePath.endsWith(".jpg") || filePath.endsWith(".gif") || filePath.endsWith(".jpeg") || filePath.endsWith(".tif")) {
					ImageToPDF.imageUrlToPdf(image.getStoragePath(), imagePath + fileName + ".pdf");
				} else {
					FileOperaterUtil.fileUpload(filePath, ImageUtils.loadInputStream(image.getStoragePath()));
				}
			}
			imagePath = defaultPlacePath(toDay);
			String filePath = imagePath + fileName + image.getExtName();
			if (filePath.endsWith(".png") || filePath.endsWith(".jpg") || filePath.endsWith(".gif") || filePath.endsWith(".jpeg") || filePath.endsWith(".tif")) {
				ImageToPDF.imageUrlToPdf(image.getStoragePath(), imagePath + fileName + ".pdf");
			} else {
				FileOperaterUtil.fileUpload(filePath, ImageUtils.loadInputStream(image.getStoragePath()));
			}
		}
	}

	private String defaultPlacePath(String openTime) {
		StringBuilder defaultPlacePath = new StringBuilder();
		String path = defaultAccountPlacePathValue;
		defaultPlacePath.append(path);
		defaultPlacePath.append("/");
		return defaultPlacePath.toString();
	}

	/**
	 * 银行证明归档路径
	 *
	 * @param openTime
	 * @return
	 */
	private String bankProofPlacePath(String clientId, String openTime) {
		StringBuilder bankProofPlacePath = new StringBuilder();
		String path = bankProofPlacePathValue;
		bankProofPlacePath.append(path);
		bankProofPlacePath.append("/" + clientId + "_" + openTime + "/");
		return bankProofPlacePath.toString();
	}

	/**
	 * 客户归档目录
	 *
	 * @param clientId
	 * @param openTime
	 * @return
	 */
	private String clientFilePath(String clientId, String openTime) {
		StringBuilder clientFilePathName = new StringBuilder(clientId);
		clientFilePathName.append("_");
		clientFilePathName.append(openTime);
		return clientFilePathName.toString();
	}

	private String generateSecFileName(String clientId, String aeCode, String openTime, String code, Integer index) {
		return generateFileName(clientId, aeCode, "SEC", openTime, code, index);
	}

	/**
	 * 归档文件名称
	 *
	 * @param clientId 账户号码
	 * @param aeCode   邀请码
	 * @param category 账户类别 SEC 证券账户 FUT 期货账户 FX 外汇账户
	 * @param openTime 开户时间 YYYYMMDD
	 * @param code     文件类别 CmsPdfPlaceEnum、CmsImagePlaceEnum
	 * @param index    序列号 相同文件类别自增
	 * @return
	 */
	private String generateFileName(String clientId, String aeCode, String category, String openTime, String code, Integer index) {
		StringBuilder fileName = new StringBuilder();
		fileName.append(clientId);
		fileName.append("_");
		fileName.append(aeCode);
		fileName.append("_");
		fileName.append(category);
		fileName.append("_");
		fileName.append(code);
		fileName.append("_");
		fileName.append(openTime);
		fileName.append("_");
		fileName.append(index);
		log.info("generateFileName:" + fileName);
		return fileName.toString();
	}

	@Override
	public OpenAccountCallbackBo submitInfo(OpenAccountBo bo) {
		String applicationId = null;
		AccountOpenInfoEntity customerAccountOpenInfo = new AccountOpenInfoEntity();
		if (StringUtils.isNotBlank(bo.getApplicationId())) {
			applicationId = bo.getApplicationId();
			AccountOpenInfoVO customerAccountOpenInfoVo = baseMapper.queryByApplicationId(applicationId);
			BeanUtil.copyProperties(customerAccountOpenInfoVo, customerAccountOpenInfo);
		} else {
			applicationId = ApplicationIdUtil.generateOpenOnlineId(bo.getTenantId());
		}
		BeanUtil.copyProperties(bo, customerAccountOpenInfo);

		//开户证件及账户类型
		AccountInfo accountInfo = bo.getAccountInfo();
		if (!StringUtils.isBlank(bo.getPhoneArea())) {
			customerAccountOpenInfo.setPhoneArea(bo.getPhoneArea());
		} else if (bo.getVerifyFour() != null && StringUtils.isNotBlank(bo.getVerifyFour().getPhoneArea())) {
			customerAccountOpenInfo.setPhoneArea(bo.getVerifyFour().getPhoneArea());
		}

		if (!StringUtils.isBlank(bo.getPhoneNumber())) {
			customerAccountOpenInfo.setPhoneNumber(bo.getPhoneNumber());
		} else if (bo.getVerifyFour() != null && StringUtils.isNotBlank(bo.getVerifyFour().getPhoneNumber())) {
			customerAccountOpenInfo.setPhoneNumber(bo.getVerifyFour().getPhoneNumber());
		}

		customerAccountOpenInfo.setAcceptRisk(bo.getAcceptRisk());
		customerAccountOpenInfo.setExpiryDate(bo.getExpiryDate());
		customerAccountOpenInfo.setFundAccountType(accountInfo.getFundAccountType());
		customerAccountOpenInfo.setAeCode(accountInfo.getInviteCode());
		customerAccountOpenInfo.setIsOpenHkStockMarket(0);
		customerAccountOpenInfo.setIsOpenUsaStockMarket(0);
		customerAccountOpenInfo.setNorthTrade(0);
		customerAccountOpenInfo.setAgainstDataPromotion(0);
		customerAccountOpenInfo.setAgainstDataPromotionToCompany(0);
		customerAccountOpenInfo.setApplicationId(applicationId);
		customerAccountOpenInfo.setApplicationTime(new Date());
		customerAccountOpenInfo.setTenantId(bo.getTenantId());

		for (String s : accountInfo.getTreadMarket()) {
			if ("1".equals(s)) {
				customerAccountOpenInfo.setIsOpenHkStockMarket(1);
			}
			if ("2".equals(s)) {
				customerAccountOpenInfo.setIsOpenUsaStockMarket(1);
			}
			if ("3".equals(s)) {
				customerAccountOpenInfo.setNorthTrade(1);
			}
		}
		BeanUtil.copyProperties(accountInfo, customerAccountOpenInfo);
		//地址
		AddressInfo addressInfo = bo.getAddressInfo();
		BeanUtil.copyProperties(addressInfo, customerAccountOpenInfo);
		//个人
		PersonalInfo personalInfo = bo.getPersonalInfo();
		BeanUtil.copyProperties(personalInfo, customerAccountOpenInfo);
		if (StringUtils.isNotBlank(personalInfo.getAreaNumber())) {
			String familyPhone = concatTel(personalInfo.getAreaCode(), personalInfo.getCityCode(), personalInfo.getAreaNumber());
			customerAccountOpenInfo.setFamilyPhone(familyPhone);
		}
		customerAccountOpenInfo.setSigningOrganization(personalInfo.getAuthority());
		customerAccountOpenInfo.setClientName(customerAccountOpenInfo.getFamilyName() + customerAccountOpenInfo.getGivenName());
		customerAccountOpenInfo.setClientNameSpell(customerAccountOpenInfo.getFamilyNameSpell() + " " + customerAccountOpenInfo.getGivenNameSpell());
		//职业
		OccupationInfo occupationInfo = bo.getOccupationInfo();
		if (occupationInfo != null) {
			BeanUtil.copyProperties(occupationInfo, customerAccountOpenInfo);
			if (StringUtils.isNotBlank(occupationInfo.getCompanyPhoneNumber())) {
				String companyPhone = concatTel(occupationInfo.getCompanyPhoneAreaCode(), occupationInfo.getCompanyPhoneCityCode(), occupationInfo.getCompanyPhoneNumber());
				customerAccountOpenInfo.setCompanyPhoneNumber(companyPhone);
			}
			if (StringUtils.isNotBlank(occupationInfo.getCompanyFacsimile())) {
				String companyFacsimile = concatTel(occupationInfo.getCompanyFacsimileAreaCode(), occupationInfo.getCompanyFacsimileCityCode(), occupationInfo.getCompanyFacsimile());
				customerAccountOpenInfo.setCompanyFacsimile(companyFacsimile);
			}
		}
		//投资信息
		AssetInvestmentInfo assetInvestmentInfo = bo.getAssetInvestmentInfo();
		if (assetInvestmentInfo != null) {
			BeanUtil.copyProperties(assetInvestmentInfo, customerAccountOpenInfo);
		}
		//税务
		TaxInfo taxInfo = bo.getTaxInfo();
		List<AccountTaxationInfoEntity> taxationInfos = new ArrayList<>();
		List<TaxItem> taxInfoList = taxInfo.getTaxInfoList();
		String finalApplicationId = applicationId;
		taxInfoList.forEach(item -> {
			AccountTaxationInfoEntity openAccountTaxationInfo = new AccountTaxationInfoEntity();
			openAccountTaxationInfo.setHasTaxNumber(item.getHasTaxCode());
			openAccountTaxationInfo.setTaxNumber(item.getTaxCode());
			openAccountTaxationInfo.setReasonDesc(item.getReasonDesc());
			openAccountTaxationInfo.setReasonType(item.getTaxFeasonType());
			openAccountTaxationInfo.setTaxCountry(item.getTaxJurisdiction());
			openAccountTaxationInfo.setAdditionalDisclosures(taxInfo.getAdditionalDisclosures());
			openAccountTaxationInfo.setApplicationId(finalApplicationId);
			openAccountTaxationInfo.setTenantId(bo.getTenantId());
			taxationInfos.add(openAccountTaxationInfo);
		});

		if (taxationInfos.size() > 0) {
			if (StringUtils.isNotBlank(bo.getApplicationId())) {
				iAccountTaxationInfoService.deleteByApplicationId(applicationId);
			}
			iAccountTaxationInfoService.saveBatch(taxationInfos);
		}
		List<ImageInfo> imageInfos = bo.getImageList();
		if (StringUtils.isNotBlank(bo.getApplicationId())) {
			customerAccountOpenImageService.deleteByApplicationId(applicationId);
		}
		customerAccountOpenImageService.uploadImageInfoList(imageInfos, applicationId, bo.getTenantId());

		IdentityConfirmInfo identityConfirmInfo = bo.getIdentityConfirmInfo();
		if (identityConfirmInfo != null) {
			AccountIdentityConfirmEntity openAccountIdentityConfirm = new AccountIdentityConfirmEntity();
			BeanUtil.copyProperties(identityConfirmInfo, openAccountIdentityConfirm);
			openAccountIdentityConfirm.setApplicationId(applicationId);
			openAccountIdentityConfirm.setTenantId(bo.getTenantId());
			openAccountIdentityConfirm.setCreateTime(new Date());
			openAccountIdentityConfirm.setCreateUser(AuthUtil.getUserId());
			openAccountIdentityConfirm.setUpdateTime(new Date());
			openAccountIdentityConfirm.setUpdateUser(AuthUtil.getUserId());
			if (StringUtils.isNotBlank(bo.getApplicationId())) {
				openAccountIdentityConfirmMapper.deleteByApplicationId(applicationId);
			}
			openAccountIdentityConfirmMapper.insert(openAccountIdentityConfirm);
		}

		IDCardInfo idCardInfo = bo.getIdCardInfo();
		if (idCardInfo != null) {
			AccountSupplementCertificateEntity supplementCertificate = new AccountSupplementCertificateEntity();
			supplementCertificate.setApplicationId(applicationId);
			supplementCertificate.setUserId(bo.getUserId());
			supplementCertificate.setTenantId(bo.getTenantId());
			BeanUtil.copyProperties(idCardInfo, supplementCertificate);
			//[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
			if (customerAccountOpenInfo.getIdKind() == 3) {
				supplementCertificate.setIsHkIdCard(idCardInfo.getPassportHkIdCard());
				supplementCertificate.setPlaceOfIssue(personalInfo.getPlaceOfIssue());
			}
			//国民身份证签发地
			if (ObjectUtil.isNotNull(supplementCertificate.getPassportCitizenIdCard()) && supplementCertificate.getPassportCitizenIdCard() == 1) {
				supplementCertificate.setIdCardPlaceOfIssue(idCardInfo.getPlaceOfIssue());
			}
			if (StringUtils.isNotBlank(bo.getApplicationId())) {
				openAccountSupplementCertificateMapper.deleteByApplicationId(applicationId);
			}
			supplementCertificate.setCreateTime(new Date());
			supplementCertificate.setCreateUser(AuthUtil.getUserId());
			supplementCertificate.setUpdateTime(new Date());
			supplementCertificate.setUpdateUser(AuthUtil.getUserId());
			openAccountSupplementCertificateMapper.insert(supplementCertificate);
		}
		VerifyFour verifyFour = bo.getVerifyFour();
		try {
			//大陆身份证开户
			if (customerAccountOpenInfo.getIdKind() == 1) {
				AccountBankVerityInfoEntity openAccountBankVerityInfo = new AccountBankVerityInfoEntity();
				openAccountBankVerityInfo.setClientName(verifyFour.getName());
				openAccountBankVerityInfo.setApplicationId(applicationId);
				openAccountBankVerityInfo.setBankCard(verifyFour.getBankNo());
				openAccountBankVerityInfo.setIdNo(verifyFour.getIdNo());
				openAccountBankVerityInfo.setUserId(verifyFour.getUserId());
				openAccountBankVerityInfo.setVerifyTime(verifyFour.getVerifyTime());
				openAccountBankVerityInfo.setLiveVerifyTime(verifyFour.getLiveVerifyTime());
				openAccountBankVerityInfo.setPhoneArea(verifyFour.getPhoneArea());
				openAccountBankVerityInfo.setPhoneNumber(verifyFour.getPhoneNumber());
				openAccountBankVerityInfo.setTenantId(bo.getTenantId());
				//添加四要素信息
				if (StringUtils.isNotBlank(bo.getApplicationId())) {
					log.warn("-->先删除现有的AccountBankVerityInfoEntity银行卡信息,ApplicationID:{}", applicationId);
					openAccountBankVerityInfoService.deleteByApplicationId(applicationId);
				}
				log.info("-->开始保存AccountBankVerityInfoEntity银行卡信息:{}", JSONObject.toJSONString(openAccountBankVerityInfo));
				openAccountBankVerityInfoService.save(openAccountBankVerityInfo);
			} else {
				log.warn("-->当前身份证不是大陆的, 跳过银行卡信息保存逻辑, 当前身份证类型:{},银行卡信息:{}", customerAccountOpenInfo.getIdKind(), JSONObject.toJSONString(verifyFour));
			}
		} catch (Exception e) {
			//不抛异常
			log.error("-->大陆身份证开户银行卡信息保存异常,异常详情:", e);
		}
		customerAccountOpenInfo.setUserId(bo.getUserId());
		customerAccountOpenInfo.setOpenAccountType(bo.getOpenAccountType());
		customerAccountOpenInfo.setOpenAccountAccessWay(bo.getOpenAccountAccessWay());
		if (StringUtils.isNotBlank(bo.getApplicationId())) {
			AccountOpenInfoVO customerAccountOpenInfoVo = baseMapper.queryByApplicationId(bo.getApplicationId());
			if (ObjectUtil.isNotNull(customerAccountOpenInfoVo)) {
				customerAccountOpenInfo.setAeCode(customerAccountOpenInfoVo.getAeCode());
			}
		}
		if (StringUtils.isNotBlank(bo.getApplicationId())) {
			baseMapper.deleteByApplicationId(applicationId);
		}
		baseMapper.insert(customerAccountOpenInfo);
		AccountOpenApplicationEntity applicationBo = new AccountOpenApplicationEntity();
		applicationBo.setTenantId(bo.getTenantId());
		applicationBo.setApplicationId(applicationId);
		applicationBo.setApplicationTitle("互联网开户[" + customerAccountOpenInfo.getClientName() + "]");
		//如果是线下开户的,就不提供入金审核的数据对象.
		if (customerAccountOpenInfo.getIdKind() != 1
			&& customerAccountOpenInfo.getOpenAccountAccessWay() != OpenAccountEnum.OpenAccountAccessWay.OFFLINE.getCode()) {
			DepositInfo depositInfo = bo.getDepositInfo();
			//如果有填写入金信息
			if (depositInfo != null) {
				AccountDepositInfoEntity openAccountDepositInfo = new AccountDepositInfoEntity();
				BeanUtil.copyProperties(depositInfo, openAccountDepositInfo);
				openAccountDepositInfo.setApplicationId(applicationId);
				openAccountDepositInfo.setStatus(0);
				openAccountDepositInfo.setDepositMoney(depositInfo.getDepositMoney() == null ? BigDecimal.valueOf(0.00) : depositInfo.getDepositMoney().setScale(2));
				openAccountDepositInfo.setTenantId(bo.getTenantId());
				if (StringUtils.isNotBlank(bo.getApplicationId())) {
					log.info("-->先删除现有的AccountDepositInfoEntity入金信息,ApplicationID:{}", applicationId);
					openAccountDepositInfoService.deleteByApplicationId(applicationId);
				}
				Boolean result = openAccountDepositInfoService.insert(openAccountDepositInfo);
				if (!result) {
					log.error("-->入金信息保存失败,入金信息:{}", JSONObject.toJSONString(depositInfo));
				}
			}
		}
		R flowResult = null;
		if (StringUtils.isNotBlank(bo.getApplicationId())) {
			AccountOpenApplicationEntity customerAccountOpenApplication = customerAccountOpenApplicationService.queryByApplicationId(applicationId);
			customerAccountOpenApplication.setRefKey(applicationBo.getRefKey());
			customerAccountOpenApplication.setStatus(applicationBo.getStatus());
			customerAccountOpenApplicationService.updateById(customerAccountOpenApplication);
			Map<String, Object> variables = new LinkedMap<>();
			variables.put(TaskConstants.ID_KIND, customerAccountOpenInfo.getIdKind());
			variables.put(TaskConstants.IS_BACK, 0);
			flowResult = flowClient.completeTask(customerAccountOpenApplication.getTaskId(), "重新提交", variables);
			openAccountBackReasonService.deleteByApplicationId(applicationId);
		} else {
			customerAccountOpenApplicationService.save(applicationBo);
			String key = ProcessConstant.OPEN_ACCOUNT_KEY;
			String table = FlowUtil.getBusinessTable(key);
			String dictKey = FlowUtil.getBusinessDictKey(key);
			Map<String, Object> variables = new LinkedMap<>();
			variables.put(TaskConstants.ID_KIND, customerAccountOpenInfo.getIdKind());
			variables.put(TaskConstants.IS_BACK, 0);
			variables.put(TaskConstants.PROCESS_INITIATOR, TaskUtil.getTaskUser());
			variables.put(TaskConstants.APPLICATION_TABLE, table);
			variables.put(TaskConstants.APPLICATION_DICT_KEY, dictKey);
			variables.put(TaskConstants.APPLICATION_ID, applicationId);
			variables.put(TaskConstants.TENANT_ID, bo.getTenantId());
			flowResult = flowClient.startProcessInstanceByKey(key, variables);
		}
		if (!flowResult.isSuccess()) {
			log.error("启动" + applicationId + "流程失败：" + flowResult.getMsg());
		}
		openAccountAdditionalFileService.deleteByApplicationAndType(applicationId, 3);
		if (customerAccountOpenInfo.getIsOpenUsaStockMarket() == 1) {
			AccountW8benInfoEntity openAccountW8benInfo = new AccountW8benInfoEntity();
			openAccountW8benInfo.setW8IndividualName(taxInfo.getEnglishName());
			openAccountW8benInfo.setW8CitizenshipCountry(taxInfo.getEnglishCountry());
			openAccountW8benInfo.setW8BirthDay(taxInfo.getBirthDate());
			openAccountW8benInfo.setW8ForeignTaxIdentifyingNumber(taxInfo.getIdentifyingNumber());
			openAccountW8benInfo.setW8TreatBenefits(taxInfo.getTreatyBenefits() ? 1 : 0);
			openAccountW8benInfo.setW8TreatBenefitsCountry(taxInfo.getTreatyBenefitsCountry());
			//w8永久地址
			if (StringUtils.isNotBlank(taxInfo.getPermanentCountryAddress())) {
				openAccountW8benInfo.setW8PermanentResidenceAddressCountry(taxInfo.getPermanentCountryAddress());
				//大陆和台湾地区省市区拆分（1=大陆，4=台湾）
				if (taxInfo.getPermanentCountryAddress().equals("1")) {
					String address = taxInfo.getPermanentProvinceName() + ", " + taxInfo.getPermanentCityName() + ", " + taxInfo.getPermanentCountyName();
					openAccountW8benInfo.setW8PermanentResidenceProvinceCityCounty(address);
				}
				openAccountW8benInfo.setW8PermanentResidenceAddress(taxInfo.getPermanentAddress());
			}
			//w8通讯地址
			if (StringUtils.isNotBlank(taxInfo.getMailingCountryAddress())) {
				openAccountW8benInfo.setW8MailingAddressCountry(taxInfo.getMailingCountryAddress());
				//大陆和台湾地区省市区拆分（1=大陆，4=台湾）
				if (taxInfo.getMailingCountryAddress().equals("1")) {
					String address = taxInfo.getMailingProvinceName() + ", " + taxInfo.getMailingCityName() + ", " + taxInfo.getMailingCountyName();
					openAccountW8benInfo.setW8MailingAddressProvinceCityCounty(address);
				}
				openAccountW8benInfo.setW8MailingAddress(taxInfo.getMailingAddress());
			}
			openAccountW8benInfo.setApplicationId(applicationId);
			openAccountW8benInfo.setTenantId(bo.getTenantId());
			if (StringUtils.isNotBlank(bo.getApplicationId())) {
				openAccountW8benInfoMapper.deleteByApplicationId(applicationId);
			}
			openAccountW8benInfoMapper.insert(openAccountW8benInfo);
		}

		// 保存投资知识问卷调查表
		if (StringUtils.isNotBlank(bo.getApplicationId())) {
			investQuestionnaireService.delByApplicationId(applicationId);
		}
		InvestQuestionnaires investQuestionnaires = bo.getInvestQuestionnaires();
		CustomerInvestKnowledgeQuestionnaire investQuestionnaire = new CustomerInvestKnowledgeQuestionnaire();
		investQuestionnaire.setApplicationId(applicationId);
		investQuestionnaire.setCustId(bo.getUserId());
		investQuestionnaire.setTenantId(bo.getTenantId());
		if (investQuestionnaires != null) {
			investQuestionnaire.setOptionData(JSON.toJSONString(investQuestionnaires));
		}
		investQuestionnaireService.save(investQuestionnaire);


		OpenAccountCallbackBo openAccountCallbackBo = new OpenAccountCallbackBo();
		openAccountCallbackBo.setApplicationId(applicationId);
		openAccountCallbackBo.setUserId(bo.getUserId());
		openAccountCallbackBo.setStatus(1);
		return openAccountCallbackBo;
	}


	@Value("${email.sendAttachments}")
	private Boolean sendAttachments;

	@Override
	@Transactional
	public R openAccount(String applicationId) {
		AccountOpenInfoVO accountOpenInfoVO = baseMapper.queryByApplicationId(applicationId);
		List<AccountTaxationInfoVO> accountTaxationInfoVOList =
			openAccountTaxationInfoMapper.queryListByApplicationId(applicationId);
		/**
		 * 开户图片
		 */
		List<AccountOpenImageVO> accountOpenImageVOs =
			customerAccountOpenImageService.queryListByApplicationId(applicationId);

		/**
		 * 调用账户中心开户
		 */

		Map<String, String> data = this.openAccount(accountOpenInfoVO, accountTaxationInfoVOList, accountOpenImageVOs);
		if (data == null) {
			log.error("用户custId={}，phone={}账号中心开户失败！", accountOpenInfoVO.getUserId(),
				accountOpenInfoVO.getPhoneNumber());
			return R.fail("柜台开户失败");
		}
		String name = data.get("name");
		String account = data.get("accountId");
		String initPassword = data.get("password");

		Integer idKind = accountOpenInfoVO.getIdKind();
		// 1是ca认证，否则入金认证
		if (idKind != 1) {
			AccountDepositInfoVO accountDepositInfo = accountDepositInfoMapper.queryByApplicationId(applicationId);
			if (accountDepositInfo != null) {
				// 同步入金记录
				try {
					openAccountDeposit(accountOpenInfoVO.getUserId(), account, name, accountDepositInfo, applicationId);
				} catch (Exception e) {
					log.error("入金开户同步入金记录异常：{}", e.getMessage());
				}
				// //入金
				// AmountDTO amount = new AmountDTO();
				// amount.setAccountId(account);
				// amount.setAmount(accountDepositInfo.getDepositMoney());
				// amount.setBusinessId(accountDepositInfo.getApplicationId());
				// amount.setThawingType(ThawingType.OPEN_ACCOUNT_DEPOSIT.getCode());
				// amount.setCurrency(CashConstant.MoneyTypeDescEnum.getDesc(accountDepositInfo.getDepositCurrencyType().toString()));
				// log.info("开户 入金请求入账 res:{}", com.alibaba.fastjson.JSONObject.toJSONString(amount));
				// R result = iCustomerInfoClient.goldDeposit(amount);
				// log.info("开户 入金请求结果 R:{}", com.alibaba.fastjson.JSONObject.toJSONString(result));
				// if (result.isSuccess()) {
				// try {
				// depositBankCardBinding(accountDepositInfo, account, accountOpenInfoVO.getTenantId(),
				// accountOpenInfoVO.getUserId());
				// } catch (Exception e) {
				// log.error("开户clientId={}，入金开户绑定银行卡{}失败，错误信息：{}", account, accountDepositInfo.getBankAccountNumber(),
				// e.getMessage());
				// }
				// }
			}
		}
		baseMapper.updateOpenAccountSuccess(applicationId, account);
//        BpmSecuritiesInfoEntity bpmSecuritiesInfoEntity = new BpmSecuritiesInfoEntity();
//        BeanUtil.copyProperties(accountOpenInfoVO, bpmSecuritiesInfoEntity);
//        bpmSecuritiesInfoEntity.setId(null);
//        bpmSecuritiesInfoEntity.setAppointmentId(applicationId);
//        bpmSecuritiesInfoEntity.setCustId(accountOpenInfoVO.getUserId());
//        bpmSecuritiesInfoEntity.setCustName(accountOpenInfoVO.getClientName());
//        bpmSecuritiesInfoEntity.setCustNameSpell(accountOpenInfoVO.getClientNameSpell());
//        bpmSecuritiesInfoEntity.setIdCard(accountOpenInfoVO.getIdNo());
//        bpmSecuritiesInfoEntity.setGender(accountOpenInfoVO.getSex());
//        bpmSecuritiesInfoEntity.setIdCardAddress(accountOpenInfoVO.getIdCardDetailAddress());

		// 1=现金账户 2=融资账户
//        R r = iBpmSecuritiesInfoClient.securitiesInfoPlace(bpmSecuritiesInfoEntity, account,
//            accountOpenInfoVO.getFundAccountType());
//        if (!r.isSuccess()) {
//            throw new ServiceException("柜台开户数据归档失败" + r.getMsg());
//        }

		List<AccountAdditionalFileVO> fileEntity =
			openAccountAdditionalFileMapper.queryListByApplicationId(applicationId, 3);
		Optional<AccountAdditionalFileVO> openAccountFile = fileEntity.stream().filter(file -> {
			return file.getFileType() == ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT.getValue();
		}).findFirst();

		String fileLink = null;
		if (openAccountFile.isPresent() && sendAttachments) {
			fileLink = openAccountFile.get().getFilePath();
		}

		PushUtil.builder()
			.msgGroup("P")
			.custId(accountOpenInfoVO.getUserId())
			.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
			.templateCode(PushTemplate.ACCOUNT_OPEN_SUCCESS.getCode())
			.pushType(PushTypeEnum.STRONG_MSG.getTypeValue())
			.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
			.pushAsync();

		EmailUtil.Builder builder = EmailUtil.builder().param(name).param(name).param(account).param(account).param(initPassword)
			.accepts(Arrays.asList(accountOpenInfoVO.getEmail()))
			.templateCode(EmailTemplate.OPEN_WEALTH_MANAGEMENT_ACCOUNT.getCode());
		if (!StringUtil.isBlank(fileLink)) {
			builder.attachmentUrls(Arrays.asList(fileLink));
		}
		builder.sendAsync();

		SmsUtil.builder()
			.templateCode(SmsTemplate.OPEN_ACCOUNT_SUCCESS.getCode())
			.param(name)
			.phoneNumber(accountOpenInfoVO.getPhoneNumber())
			.areaCode(accountOpenInfoVO.getPhoneArea())
			.sendAsync();

		return R.success();
	}

	@Override
	public Page<CustomerOpenAccountVO> openAccountUserList(String keyword, String startTime, String endTime, Page page) {
		List<AccountOpenInfoEntity> openInfoList = baseMapper.queryList(page, keyword, startTime, endTime);
		if (CollectionUtil.isEmpty(openInfoList)) {
			return page;
		}
		List<CustomerOpenAccountVO> list = new ArrayList<>();
		for (AccountOpenInfoEntity account : openInfoList) {
			CustomerOpenAccountVO openAccount = new CustomerOpenAccountVO();
			openAccount.setCustId(account.getUserId());
			openAccount.setName(account.getClientName());
			openAccount.setOpenEmail(account.getEmail());
			openAccount.setOpenStatus(account.getPhoneArea());
			openAccount.setOpenPhone(account.getPhoneArea() + "-" + account.getPhoneNumber());
			openAccount.setCreateTime(account.getCreateTime());
			openAccount.setEnName(account.getGivenNameSpell());
			openAccount.setOpenAccountTime(account.getOpenAccountTime());
			openAccount.setApplicationTime(account.getApplicationTime());
			list.add(openAccount);
		}
		return page.setRecords(list);
	}

	@Override
	public Map<String, String> openAccount(AccountOpenInfoVO accountOpenInfoVO,
										   List<AccountTaxationInfoVO> accountTaxationInfoVOList,
										   List<AccountOpenImageVO> accountOpenImageVOs) {
		// 参数对象
		CustomerOpenAccountDTO openAccount = new CustomerOpenAccountDTO();
		// 用户信息
		CustomerInfoDTO customerInfo = buildCustomerInfo(accountOpenInfoVO);
		// 基础资料
		CustomerBasicInfoDTO basicInfo = buildBasicInfo(accountOpenInfoVO);
		// 实名认证信息
		CustomerRealnameVerifyDTO realNameVerify = buildRealNameVerify(accountOpenInfoVO);
		// 税务信息
		List<CustomerTaxationInfoDTO> accountTaxationInfoList = buildTaxationInfo(accountTaxationInfoVOList);
		openAccount.setCustomerTaxationInfoDTOList(accountTaxationInfoList);

		// 图片信息
		openAccount.setCustomerOpenImageDTOList(buildImageInfo(accountOpenImageVOs));

		openAccount.setBasicInfo(basicInfo);
		openAccount.setCustomerInfo(customerInfo);
		openAccount.setRealnameVerify(realNameVerify);
		openAccount.setCustId(accountOpenInfoVO.getUserId());
		openAccount.setOpenChannel(accountOpenInfoVO.getOpenAccountAccessWay());
		// 开通账户类型
		if (StringUtils.isNotEmpty(accountOpenInfoVO.getStockTypes())) {
			List<String> stockTypeList = Arrays.asList(accountOpenInfoVO.getStockTypes().split(","));
			openAccount.setOpenStockTypeList(stockTypeList);
		}
		openAccount.setRiskLevel(accountOpenInfoVO.getAcceptRisk());
		openAccount.setExpiryDate(accountOpenInfoVO.getExpiryDate());
		log.info("用户{}提交账号中心开通统一户资料", accountOpenInfoVO.getUserId());
		R result = iCustomerInfoClient.openAccountNew(openAccount);
		log.info("用户{}提交账号中心开通统一户资料返回结果{}", accountOpenInfoVO.getUserId(), JSONObject.toJSONString(result));
		Map<String, String> data = null;
		if (result != null && result.getCode() == ResultCode.SUCCESS.getCode()) {
			data = (Map<String, String>)result.getData();
		}
		return data;
	}

	@Override
	public BackCustomerDetailVO openAccountUserDetail(Long userId) {
		AccountOpenInfoEntity openInfo = baseMapper.openAccountUserDetail(userId);
		if (openInfo == null) {
			return new BackCustomerDetailVO();
		}
		BackCustomerDetailVO customerDetail = new BackCustomerDetailVO();
		customerDetail.setFamilyName(openInfo.getFamilyName());
		customerDetail.setGivenName(openInfo.getGivenName());
		customerDetail.setClientNameSpell(openInfo.getClientNameSpell());
		customerDetail.setCustomerName(customerDetail.getFamilyName() + customerDetail.getGivenName());
		customerDetail.setFamilyNameSpell(openInfo.getFamilyNameSpell());
		customerDetail.setGivenNameSpell(openInfo.getGivenNameSpell());
		customerDetail.setCustomerEName(customerDetail.getFamilyNameSpell() + customerDetail.getGivenNameSpell());
		customerDetail.setAddress(openInfo.getIdCardDetailAddress());
		customerDetail.setOpenAccountTime(com.minigod.zero.core.tool.utils.DateUtil.formatDate(openInfo.getApplicationTime()));
		customerDetail.setOpenAccountEmail(openInfo.getEmail());
		Integer openAccountAccessWay = openInfo.getOpenAccountAccessWay();
		customerDetail.setOpenAccountType(openAccountAccessWay == null ? null : openAccountAccessWay.toString());
		Integer accountType = openInfo.getFundAccountType();
		customerDetail.setAccountType(accountType == null ? null : accountType.toString());
		Integer idKind = openInfo.getIdKind();
		customerDetail.setIdKindType(idKind == null ? null : idKind.toString());
		customerDetail.setNationality(openInfo.getNationality());
		Integer gender = openInfo.getSex();
		customerDetail.setGender(gender == null ? null : gender.toString());
		customerDetail.setIdCardNo(openInfo.getIdNo());
		return customerDetail;
	}

	@Override
	public R agreementStatus(Integer type) {
		AgreementStatusVO agreementStatusVO = new AgreementStatusVO();
		agreementStatusVO.setStatus(AgreementStatus.NOT_SIGNED.getCode());
		Long custId = AuthUtil.getCustId();
		AccountOpenInfoEntity accountOpenInfoEntity = baseMapper.openAccountUserDetail(custId);
		if (accountOpenInfoEntity == null) {
			return R.fail(I18nUtil.getMessage(ErrorMessageConstant.ERROR_GET_ACCOUNT_INFO_FAILED_NOTICE));
		}
		if (type.equals(ACCOUNT_OPEN_REPORT_USER_W8_REPORT.getValue())) {
			if (accountOpenInfoEntity.getW8AgreementTime() == null) {
				return R.data(agreementStatusVO);
			}
			LocalDateTime threeYearsAgo = LocalDateTime.now().minusYears(3);
			Date w8AgreementTime = accountOpenInfoEntity.getW8AgreementTime();
			if (w8AgreementTime == null ||
				LocalDateTime.ofInstant(w8AgreementTime.toInstant(), ZoneId.systemDefault()).isBefore(threeYearsAgo)) {
				return R.data(agreementStatusVO);
			}
		}
		if (type.equals(ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT.getValue())) {
			if (accountOpenInfoEntity.getW8AgreementTime() == null) {
				return R.data(agreementStatusVO);
			}
			LocalDateTime threeYearsAgo = LocalDateTime.now().minusYears(1);
			Date selfAgreementTime = accountOpenInfoEntity.getSelfAgreementTime();
			if (selfAgreementTime == null ||
				LocalDateTime.ofInstant(selfAgreementTime.toInstant(), ZoneId.systemDefault()).isBefore(threeYearsAgo)) {
				return R.data(agreementStatusVO);
			}
		}
		agreementStatusVO.setStatus(AgreementStatus.SIGNED.getCode());
		return R.data(agreementStatusVO);
	}

	@Override
	public R saveSignImg(OpenSignImgBo params) {

		String location = params.getLocation();
		String type = params.getType();
		String base64 = params.getImgBase64();
		// 参数校验 - 基本
		if (StringUtils.isBlank(location) || StringUtils.isBlank(type) || StringUtils.isBlank(base64)) {
			log.error("参数异常: OpenImgResVo_Value_CN");
			throw new ServiceException(I18nUtil.getMessage(ErrorMessageConstant.ERROR_PARAMETER_EXCEPTION_NOTICE));
		}
		Long userId = AuthUtil.getCustId();
		//Long userId = 1l;
		if (userId == null) {
			throw new ServiceException(I18nUtil.getMessage(ErrorMessageConstant.ERROR_PARAMETER_EXCEPTION_NOTICE));
		}
		ImageDescEnum imageDescEnum = ImageDescEnum.find(Integer.parseInt(location), Integer.parseInt(type));
		if (imageDescEnum == ImageDescEnum.UNKNOWN) {
			throw new ServiceException(I18nUtil.getMessage(ErrorMessageConstant.ERROR_PARAMETER_EXCEPTION_NOTICE));
		}
		AccountOpenInfoEntity accountOpenInfoEntity = baseMapper.openAccountUserDetail(userId);
		//w8PdfGenerate(accountOpenInfoEntity);
		//oss上传图片
		String link = putImgToOss(params);
		//上传图片到数据库
		postOpenImage(accountOpenInfoEntity, imageDescEnum, link);

		if (imageDescEnum == ImageDescEnum.W8_SIGN) {
			//w8处理
			//生成w8pdf
			w8PdfGenerate(accountOpenInfoEntity);
			//确认协议
			confirmationProtocol(ACCOUNT_OPEN_REPORT_USER_W8_REPORT.getValue());

			//开债券户
			OpenAccountDTO openAccount = new OpenAccountDTO();
			openAccount.setExtAccountId(AuthUtil.getUserAccount());
			openAccount.setCustId(AuthUtil.getCustId());
			iCustomerInfoClient.openOtcAccount(openAccount);
		}
		return R.success();
	}


	private void w8PdfGenerate(AccountOpenInfoEntity accountOpenInfoEntity) {
		AccountPdfEnum accountPdfEnum = find(ACCOUNT_OPEN_REPORT_USER_W8_REPORT.getValue());
		String path = customerAccOpenReportGenerate.generateReport(accountOpenInfoEntity.getApplicationId(), accountPdfEnum);
		if (StringUtils.isNotBlank(path)) {

			if (StringUtils.isNotBlank(path)) {
				openAccountAdditionalFileService.deleteByApplicationAndTypeAndFileType(accountOpenInfoEntity.getApplicationId(), 3, accountPdfEnum.getValue());
				openAccountAdditionalFileService.uploadAdditionalFile(accountOpenInfoEntity.getApplicationId(), 3, accountPdfEnum.getValue(), accountPdfEnum.getName(), FileUtil.getMultipartFile(FileUtils.file(path)));
			}
		} else {
			log.error("生成PDF文件失败");
			throw new ServiceException(I18nUtil.getMessage(ErrorMessageConstant.ERROR_UPLOAD_IMAGE_FAILED_NOTICE));
		}
	}

	private void postOpenImage(AccountOpenInfoEntity accountOpenInfoEntity, ImageDescEnum imageDescEnum, String link) {
		AccountOpenImageEntity customerAccountOpenImageEntity = new AccountOpenImageEntity();
		customerAccountOpenImageEntity.setApplicationId(accountOpenInfoEntity.getApplicationId());
		customerAccountOpenImageEntity.setFileName(imageDescEnum.getFileName());
		customerAccountOpenImageEntity.setFileStorageName("");
		customerAccountOpenImageEntity.setExtName("");
		customerAccountOpenImageEntity.setImageLocation(imageDescEnum.getLocation());
		customerAccountOpenImageEntity.setImageLocationType(imageDescEnum.getLocationType());
		customerAccountOpenImageEntity.setStoragePath(link);
		customerAccountOpenImageEntity.setTenantId(AuthUtil.getTenantId());
		customerAccountOpenImageEntity.setCreateTime(new Date());
		customerAccountOpenImageEntity.setUpdateTime(new Date());

		customerAccountOpenImageEntity.setImageLocationType(imageDescEnum.getLocationType());
		customerAccountOpenImageEntity.setTenantId(AuthUtil.getTenantId());
		customerAccountOpenImageEntity.setCreateTime(new Date());
		int insert = customerAccountOpenImageMapper.insert(customerAccountOpenImageEntity);
		if (insert <= 0) {
			throw new ServiceException(I18nUtil.getMessage(ErrorMessageConstant.ERROR_UPLOAD_IMAGE_FAILED_NOTICE));
		}
	}

	private String putImgToOss(OpenSignImgBo params) {
		byte[] b = ImageUtils.base64StringToImage(params.getImgBase64());
		String fileName = AuthUtil.getCustId() + "_" + params.getType() + "_" + System.currentTimeMillis() + ".jpg";
		MultipartFile file = FileUtil.getMultipartFile(fileName, b);
		R<ZeroFile> ossResp = null;
		if (FileCompressorUtil.isFileSizeExceeded(file.getSize(), 2)) {
			File compressedImage = FileCompressorUtil.compressImage(file, 50);
			FileInputStream input = null;
			MultipartFile compressedFile = null;
			try {
				input = new FileInputStream(compressedImage);
				compressedFile = new MockMultipartFile("file", compressedImage.getName(), "image/jpg", input);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			log.info("图片大小超过2MB限制,压缩后的图片文件: {}, 大小:{}", compressedImage.getAbsolutePath(), compressedImage.length());
			ossResp = ossClient.uploadMinioFile(compressedFile, file.getOriginalFilename());
			// 上传完成后删除临时文件
			if (compressedImage != null) {
				final Path outputPath = compressedImage.toPath();
				try {
					boolean result = Files.deleteIfExists(outputPath);
					log.info("已删除临时目录:{}下的压缩图片文件, 删除结果:{}", outputPath, result ? "成功!" : "失败!");
				} catch (IOException e) {
					log.warn("删除临时目录下输出的压缩图片文件异常,异常详情: {}", outputPath);
				}
			}
		} else {
			log.info("图片未超过2MB,不压缩图片文件: {}, 大小:{}", file.getOriginalFilename(), file.getSize());
			ossResp = ossClient.uploadMinioFile(file, file.getOriginalFilename());
		}
		if (null == ossResp || null == ossResp.getData() || org.apache.commons.lang.StringUtils.isBlank(ossResp.getData().getLink())) {
			throw new ServiceException(ResultCode.H5_DISPLAY_ERROR);
		}
		log.info("上传图片成功, 图片文件路径:{}", ossResp.getData().getLink());
		ZeroFile zeroFile = ossResp.getData();
		String link = zeroFile.getLink();
		return link;
	}


	@Override
	public List<String> queryW8ApplicationId() {
		return baseMapper.queryW8ApplicationId(ACCOUNT_OPEN_REPORT_USER_W8_REPORT.getValue());
	}

	@Override
	public List<String> querySelfDeclareApplicationId() {
		return baseMapper.queryW8ApplicationId(ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT.getValue());
	}

	@Override
	public R confirmationProtocol(Integer type) {
		if (AccountPdfEnum.find(type) == null) {
			throw new ServiceException(I18nUtil.getMessage(ErrorMessageConstant.ERROR_PARAMETER_EXCEPTION_NOTICE));
		}
		List<AccountAdditionalFileEntity> accountAdditionalFileEntities = accountAdditionalFileMapper.queryListByType(AuthUtil.getUserId(), type);
		if (CollectionUtil.isEmpty(accountAdditionalFileEntities)) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.FILE_NOT_EXIST));
		}

		if (type.equals(ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT.getValue())) {
			AccountOpenInfoEntity accountOpenInfoEntity = baseMapper.openAccountUserDetail(AuthUtil.getCustId());
			accountOpenInfoEntity.setSelfAgreementTime(new Date());
			accountOpenInfoEntity.setUpdateTime(new Date());
			baseMapper.updateById(accountOpenInfoEntity);
		}
		if (type.equals(ACCOUNT_OPEN_REPORT_USER_W8_REPORT.getValue())) {
			AccountOpenInfoEntity accountOpenInfoEntity = baseMapper.openAccountUserDetail(AuthUtil.getCustId());
			accountOpenInfoEntity.setW8AgreementTime(new Date());
			accountOpenInfoEntity.setUpdateTime(new Date());
			baseMapper.updateById(accountOpenInfoEntity);
		}
		return R.success();
	}

	@Override
	public List<AccountOpenInfoEntity> selW8ConfirmList(int w8Year) {
		return baseMapper.selW8ConfirmList(w8Year);
	}

	@Override
	public List<AccountOpenInfoEntity> selSelfConfirmList(int selfYear) {
		return baseMapper.selSelfConfirmList(selfYear);
	}

	@Override
	public R gen(String applicationId, Integer type) {
		AccountPdfEnum accountPdfEnum = AccountPdfEnum.find(type);
		String path = customerAccOpenReportGenerate.generateReport(applicationId, accountPdfEnum);
		if (StringUtils.isNotBlank(path)) {
			File pdfFile = new File(path);
			openAccountAdditionalFileService.deleteByApplicationAndTypeAndFileType(applicationId, 3, accountPdfEnum.getValue());
			openAccountAdditionalFileService.uploadAdditionalFile(applicationId, 3, accountPdfEnum.getValue(), accountPdfEnum.getName(), FileUtil.getMultipartFile(FileUtils.file(path)));

			if (pdfFile != null) {
				return R.data(String.format("生成PDF文件成功->路径:%s,名称:%s", pdfFile.getPath(), pdfFile.getName()));
			} else {
				return R.fail("生成失败!");
			}

		} else {
			return R.fail("生成失败!");
		}
	}

	@Override
	public R w8SelfDeclare() {
		List<String> applicationIds = queryW8ApplicationId();
		for (String applicationId : applicationIds) {
			try {
				gen(applicationId, AccountPdfEnum.ACCOUNT_OPEN_REPORT_USER_W8_REPORT.getValue());
			} catch (Exception e) {
				log.error("W8报告生成失败, applicationId:{}, error:{}", applicationId, e.getMessage());
			}
		}
		//自我声明的applicationId
		List<String> selfDeclareApplicationIds = querySelfDeclareApplicationId();
		for (String applicationId : selfDeclareApplicationIds) {
			try {
				gen(applicationId, AccountPdfEnum.ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT.getValue());
			} catch (Exception e) {
				log.error("自我声明报告生成失败, applicationId:{}, error:{}", applicationId, e.getMessage());
			}
		}
		return R.success();
	}


	public void openAccountDeposit(Long custId, String clientId, String custName,
								   AccountDepositInfoVO accountDepositInfo, String applicationId) {
		SecDepositFundsEntity secDeposit = new SecDepositFundsEntity();
		secDeposit.setUserId(custId);
		secDeposit.setClientId(clientId);
		secDeposit.setFundAccount(clientId);
		secDeposit.setFundAccountName(custName);
		// 收款信息
		Integer currency = accountDepositInfo.getDepositCurrencyType();
		R<ReceivingBankVO> result = cashBankClient.findReceivingBankNew(currency, SupportTypeEnum.OPEN_ACCOUNT.getType());
		if (!result.isSuccess()) {
			throw new ZeroException("查询收款账户信息异常");
		}
		ReceivingBankVO receivingBank = result.getData();
		if (receivingBank == null) {
			throw new ZeroException("收款账户信息异常");
		}
		secDeposit.setSwiftCode(receivingBank.getSwiftCode());
		secDeposit.setReceivingAccount(receivingBank.getAccount());
		secDeposit.setReceivingBankCode(receivingBank.getBankCode());
		secDeposit.setReceivingBankNameCn(receivingBank.getBankName());
		secDeposit.setReceivingBankNameEn(receivingBank.getBankEnName());
		secDeposit.setReceivingBankAddress(receivingBank.getBankAddress());
		secDeposit.setReceivingAccountName(receivingBank.getAccountName());
		secDeposit.setSupportType(Integer.valueOf(SupportTypeEnum.OPEN_ACCOUNT.getType()));
		// 汇款信息
		R<DepositBankVO> response = cashBankClient.findDepositBankById(accountDepositInfo.getDepositBankId());
		if (!response.isSuccess()) {
			throw new ZeroException("查询入金银行信息异常");
		}
		DepositBankVO depositBank = response.getData();
		if (depositBank == null) {
			throw new ZeroException("不支持该银行入金");
		}
		secDeposit.setCurrency(currency);
		secDeposit.setBankName(depositBank.getBankName());
		secDeposit.setBankCode(depositBank.getBankCode());
		secDeposit.setBankType(depositBank.getBankType());
		//前端利用bankCode存了客户选择的bankId，所以这里这样设置
		secDeposit.setRemittanceBankId(accountDepositInfo.getBankCode());
		secDeposit.setDepositMoney(accountDepositInfo.getDepositMoney());
		secDeposit.setSettlementAmt(accountDepositInfo.getDepositMoney());
		secDeposit.setChargeMoney(depositBank.getCommission());
		secDeposit.setRemittanceBankCode(depositBank.getBankCode());
		secDeposit.setRemittanceBankName(depositBank.getBankName());
		secDeposit.setRemittanceSwiftCode(depositBank.getSwiftCode());
		secDeposit.setBankAccountCategory(BankAccountType.getByCode(currency).getCode());
		secDeposit.setRemittanceBankAccount(accountDepositInfo.getBankAccountNumber());
		secDeposit.setRemittanceAccountNameEn(accountDepositInfo.getAccountHolderName());

		secDeposit.setIsCancel(0);
		secDeposit.setIsFind(1);
		//secDeposit.setSettlementDt(new Date());
		secDeposit.setCreatedTime(new Date());
		secDeposit.setModifyTime(new Date());
		secDeposit.setApplicationId(applicationId);
		secDeposit.setEddaApplicationId(applicationId);
		secDeposit.setTenantId(accountDepositInfo.getTenantId());
//		//入金开户最后审核人
//		AccountOpenApplicationVO accountOpenApplication = accountOpenApplicationMapper.queryByApplicationId(applicationId);
//		if (accountOpenApplication != null){
//			secDeposit.setBackPerson(accountOpenApplication.getLastApprovalUser());
//		}
		secDeposit.setState(DepositStatusEnum.SecDepositFundsStatus.COMMIT.getCode());
		secDepositFundsMapper.insert(secDeposit);

		List<AccountOpenImageVO> imageList =
			customerAccountOpenImageService.queryListByApplicationId(applicationId, CmsImagePlaceEnum.GOLDEN_CREDENTIALS.getLocation());
		if (!CollectionUtil.isEmpty(imageList)) {
			imageList.forEach(image -> {
				//保存凭证
				SecAccImgEntity accImgEntity = new SecAccImgEntity();
				accImgEntity.setServiceType(1);
				accImgEntity.setIsFind(true);
				accImgEntity.setUserId(custId);
				accImgEntity.setErrorStatus(false);
				accImgEntity.setImageLocationType(1);
				accImgEntity.setCreateTime(new Date());
				accImgEntity.setTransactId(secDeposit.getId());
				accImgEntity.setAccImgPath(image.getStoragePath());
				accImgEntity.setTenantId(accountDepositInfo.getTenantId());
				secAccImgMapper.insert(accImgEntity);
			});
		}
	}

	/**
	 * 绑定银行卡
	 *
	 * @param accountDepositInfo
	 * @param clientId
	 * @param tenantId
	 */
	public void depositBankCardBinding(AccountDepositInfoVO accountDepositInfo, String clientId, String tenantId, Long custId) {
		String bankCardNo = accountDepositInfo.getBankAccountNumber();
		LambdaQueryWrapper<BankCardInfo> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(BankCardInfo::getStatus, 1);
		queryWrapper.eq(StringUtil.isNotBlank(AuthUtil.getTenantId()), BankCardInfo::getTenantId, AuthUtil.getTenantId());
		queryWrapper.eq(BankCardInfo::getBankNo, bankCardNo);
		queryWrapper.eq(BankCardInfo::getClientId, clientId);
		queryWrapper.last("limit 1");
		BankCardInfo bankCard = bankCardInfoMapper.selectOne(queryWrapper);
		if (bankCard != null) {
			log.info("客户{}的银行卡{}已存在", clientId, bankCardNo);
			return;
		}
		R<DepositBankVO> result = cashBankClient.findDepositBankById(accountDepositInfo.getDepositBankId());
		if (!result.isSuccess()) {
			log.error("{}入金开户绑定银行开，获取入金银行信息失败：{}", clientId, result.getMsg());
			return;
		}
		DepositBankVO depositBank = result.getData();
		log.info("账号：{}入金绑定银行{}账户{}", clientId, depositBank.getBankName(), bankCardNo);
		BankCardInfo bankCardInfo = new BankCardInfo();
		bankCardInfo.setBankNo(bankCardNo);
		bankCardInfo.setClientId(clientId);
		bankCardInfo.setUserId(custId);
		bankCardInfo.setRegisterTime(new Date());
		bankCardInfo.setBankCode(depositBank.getBankCode());
		bankCardInfo.setBankName(depositBank.getBankName());
		bankCardInfo.setBankType(depositBank.getBankType());
		//前端利用bankCode存了客户选择的bankId，所以这里这样设置
		bankCardInfo.setBankId(accountDepositInfo.getBankCode());
		bankCardInfo.setBindSource("入金开户");
		bankCardInfo.setTenantId(tenantId);
		bankCardInfo.setCreateTime(new Date());
		bankCardInfo.setSwiftCode(depositBank.getSwiftCode());
		bankCardInfo.setStatus(BankCardStatus.EFFECTIVE.getCode());
		bankCardInfo.setAuthSign(BankCardAuthSign.CERTIFIED.getCode());
		bankCardInfo.setBankAccount(accountDepositInfo.getAccountHolderName());
		bankCardInfo.setBankAccountCategory(accountDepositInfo.getAccountCurrencyType());
		bankCardInfoMapper.insert(bankCardInfo);
	}


	private List<CustomerOpenImageDTO> buildImageInfo(List<AccountOpenImageVO> accountOpenImageVOs) {
		List<CustomerOpenImageDTO> result =
			com.minigod.zero.core.tool.utils.BeanUtil.copy(accountOpenImageVOs, CustomerOpenImageDTO.class);
		return result;
	}

	private List<CustomerTaxationInfoDTO> buildTaxationInfo(List<AccountTaxationInfoVO> accountTaxationInfoVOList) {

		List<CustomerTaxationInfoDTO> result =
			com.minigod.zero.core.tool.utils.BeanUtil.copy(accountTaxationInfoVOList, CustomerTaxationInfoDTO.class);
		return result;
	}

	private Map<String, String> openAccount(AccountOpenInfoVO accountOpenInfoVO) {
		//参数对象
		CustomerOpenAccountDTO openAccount = new CustomerOpenAccountDTO();
		//用户信息
		CustomerInfoDTO customerInfo = buildCustomerInfo(accountOpenInfoVO);
		//基础资料
		CustomerBasicInfoDTO basicInfo = buildBasicInfo(accountOpenInfoVO);
		//实名认证信息
		CustomerRealnameVerifyDTO realNameVerify = buildRealNameVerify(accountOpenInfoVO);
		openAccount.setBasicInfo(basicInfo);
		openAccount.setCustomerInfo(customerInfo);
		openAccount.setRealnameVerify(realNameVerify);
		openAccount.setCustId(accountOpenInfoVO.getUserId());
		openAccount.setOpenChannel(accountOpenInfoVO.getOpenAccountAccessWay());
		openAccount.setRiskLevel(accountOpenInfoVO.getAcceptRisk());
		openAccount.setW8AgreementTime(accountOpenInfoVO.getW8AgreementTime());
		openAccount.setExpiryDate(accountOpenInfoVO.getExpiryDate());
		log.info("用户{}提交账号中心开通统一户资料", accountOpenInfoVO.getUserId());
		R result = iCustomerInfoClient.openAccount(openAccount);
		log.info("用户{}提交账号中心开通统一户资料返回结果{}", accountOpenInfoVO.getUserId(), JSONObject.toJSONString(result));
		Map<String, String> data = null;
		if (result != null && result.getCode() == ResultCode.SUCCESS.getCode()) {
			data = (Map<String, String>) result.getData();
		}
		return data;
	}


	private CustomerRealnameVerifyDTO buildRealNameVerify(AccountOpenInfoVO accountOpenInfoVO) {
		CustomerRealnameVerifyDTO realNameVerify = new CustomerRealnameVerifyDTO();
		realNameVerify.setIdCard(accountOpenInfoVO.getIdNo());
		realNameVerify.setGender(accountOpenInfoVO.getSex());
		realNameVerify.setBirthday(accountOpenInfoVO.getBirthday());
		realNameVerify.setIdCardAddress(accountOpenInfoVO.getIdCardAddress());
		realNameVerify.setIdCardValidDateStart(accountOpenInfoVO.getIdCardValidDateStart());
		realNameVerify.setIdCardValidDateEnd(accountOpenInfoVO.getIdCardValidDateEnd());
		realNameVerify.setRealName(accountOpenInfoVO.getClientName());
		realNameVerify.setIdKind(accountOpenInfoVO.getIdKind().toString());
		return realNameVerify;
	}

	private CustomerBasicInfoDTO buildBasicInfo(AccountOpenInfoVO accountOpenInfoVO) {
		CustomerBasicInfoDTO basicInfo = new CustomerBasicInfoDTO();
		basicInfo.setCustId(accountOpenInfoVO.getUserId());
		BeanUtils.copyProperties(accountOpenInfoVO, basicInfo);
		return basicInfo;
	}

	private CustomerInfoDTO buildCustomerInfo(AccountOpenInfoVO accountOpenInfoVO) {
		CustomerInfoDTO customerInfo = new CustomerInfoDTO();
		String inviterId = accountOpenInfoVO.getInviterId();
		if (StringUtils.isNotBlank(inviterId)) {
			customerInfo.setInvCustId(Long.valueOf(inviterId));
		}
		return customerInfo;
	}

	private String concatTel(String areaCode, String cityCode, String areaNumber) {
		StringBuilder tel = new StringBuilder();
		if (StringUtils.isNotBlank(areaCode)) {
			tel.append(areaCode);
			tel.append("-");
		}
		if (StringUtils.isNotBlank(cityCode)) {
			tel.append(cityCode);
			tel.append("-");
		}
		if (StringUtils.isNotBlank(areaNumber)) {
			tel.append(areaNumber);
		}
		return tel.toString();
	}

}
