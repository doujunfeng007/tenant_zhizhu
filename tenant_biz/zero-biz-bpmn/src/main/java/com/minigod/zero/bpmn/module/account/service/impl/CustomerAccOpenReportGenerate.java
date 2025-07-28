package com.minigod.zero.bpmn.module.account.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.houbb.pinyin.util.PinyinHelper;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.biz.common.utils.PdfOperater;
import com.minigod.zero.bpmn.module.account.api.TaxItem;
import com.minigod.zero.bpmn.module.account.dto.PreviewW8AgreementDTO;
import com.minigod.zero.bpmn.module.account.enums.AccountPdfEnum;
import com.minigod.zero.bpmn.module.account.enums.ImageDescEnum;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.account.mapper.AccountTaxationInfoMapper;
import com.minigod.zero.bpmn.module.account.properties.AccountPdfPropertis;
import com.minigod.zero.bpmn.module.account.properties.PDFTemplace;
import com.minigod.zero.bpmn.module.account.service.*;
import com.minigod.zero.bpmn.module.account.vo.*;
import com.minigod.zero.bpmn.module.common.mapper.AddressMapper;
import com.minigod.zero.bpmn.module.common.service.AddressService;
import com.minigod.zero.bpmn.module.constant.ModifyOpenAccountMessageConstant;
import com.minigod.zero.bpmn.module.constant.OpenAccountMessageConstant;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.utils.FileUtils;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.SpringUtil;
import com.minigod.zero.resource.feign.IOssClient;
import com.minigod.zero.system.feign.IDictBizClient;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.minigod.zero.bpmn.module.account.constants.DictTypeConstant.*;
import static com.minigod.zero.bpmn.module.account.enums.AccountPdfEnum.*;

/**
 * @ClassName: CustomerAccOpenReportGenerate
 * @Description:
 * @Author chenyu
 * @Date 2022/8/18
 * @Version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class CustomerAccOpenReportGenerate implements ICustomerAccOpenReportGenerateService {
	public IAccountOpenInfoService customerAccountOpenInfoService() {
		return SpringUtil.getBean(IAccountOpenInfoService.class);
	}

	private final String language = "en-US";
	private final IAccountOpenApplicationService customerAccountOpenApplicationService;
	private final IAccountIdentityConfirmService openAccountIdentityConfirmService;
	private final IAccountDepositInfoService openAccountDepositInfoService;
	private final AccountTaxationInfoMapper openAccountTaxationInfoMapper;
	private final IAccountOpenImageService customerAccountOpenImageService;
	private final IAccountSupplementCertificateService openAccountSupplementCertificateService;
	private final IDictBizClient dictBizClient;
	private final IAccountAdditionalFileService openAccountAdditionalFileService;
	private final AddressService sysAddressService;
	private final IOfficerSignatureTatementService accountOfficerSignatureTatementService;
	private final IAccountW8benInfoService accountW8benInfoService;
	private final IUserClient userClient;
	private final AccountPdfPropertis accountPdfPropertis;
	private final ICustomerInfoClient customerInfoClient;
	private final AddressMapper addressMapper;
	private final AccountOpenInfoMapper accountOpenInfoMapper;
	//文件上传服务类
	private final IOssClient ossClient;
	@Override
	public String generateReport(String applicationId, AccountPdfEnum reportType) {

		AccountOpenApplicationVO accountOpenApplicationVO = customerAccountOpenApplicationService.queryByApplicationId(applicationId);
		if (null == reportType || null == applicationId) {
			return null;
		}
		log.info("-->开始生成PDF文件,报告类型:{},申请ID:{}", reportType.getName(), applicationId);
		Map<String, Object> reportData = new HashMap<>();
		if (reportType == ACCOUNT_OPEN_REPORT_USER_W8_REPORT) {
			reportData = loadW8ReportData(applicationId);
		} else if (reportType == ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT) {
			reportData = loadSelfReportData(applicationId);
		} else if (reportType == HONG_KONG_STOCK_FEE_SCHEDULE
			|| reportType == EXECUTE_TRADING_DISCLOSURE_DOCUMENTS_ON_OPTIMAL_TERMS
			|| reportType == ACCOUNT_OPEN_REPORT_SECURITIES_TRADING_AGREEMENT
			|| reportType == ACCOUNT_OPEN_REPORT_PERSONAL_DATA_COLLECTION_STATEMENT) {
			reportData.put("applicationId", applicationId);
		} else {
			reportData = loadAllReportData(applicationId);
		}
		log.info("-->填充到PDF的信息:{}", JSON.toJSONString(reportData));
		if (!reportData.isEmpty()) {
			String outputFilePath = makeOutputPath(applicationId, reportType);
			String templateFilePath = getReportTemplatePathByReportType(accountOpenApplicationVO.getTenantId(), reportType);
			log.info("-->PDF模板路径:{}", templateFilePath);
			log.info("-->PDF文件输出路径:{}", outputFilePath);
			if (PdfOperater.fillFile(templateFilePath, outputFilePath, reportData)) {
				log.info("-->PDF文件数据填充成功,数据内容:{},已生成PDF文件:{}", JSON.toJSONString(reportData), outputFilePath);
				return outputFilePath;
			} else {
				log.info("-->PDF文件数据填充失败,数据内容:{},未生成PDF文件!", JSON.toJSONString(reportData));
			}
		} else {
			log.info("-->填充到PDF的开户信息为空,信息内容:{},未生成PDF文件!", JSON.toJSONString(reportData));
		}

		return null;
	}

	/**
	 * 加载w8数据
	 *
	 * @param applicationId
	 * @return
	 */
	public Map<String, Object> loadW8ReportData(String applicationId) {
		AccountOpenInfoVO customerAccountOpenInfoVo = accountOpenInfoMapper.queryByAppId(applicationId);

		log.info("-->加载w8数据,customerAccountOpenInfoVo:{}", JSON.toJSONString(customerAccountOpenInfoVo));
		Map<String, Object> map = new HashMap<>();
		map.putAll(getW8AddressInfoMap(customerAccountOpenInfoVo));
		map.put("familyAddress", customerAccountOpenInfoVo.getFamilyProvinceName()+" "+customerAccountOpenInfoVo.getFamilyCityName()+" "+customerAccountOpenInfoVo.getFamilyDetailAddress());
		map.put("contactAddress", customerAccountOpenInfoVo.getContactProvinceName()+" "+customerAccountOpenInfoVo.getContactCityName()+" "+customerAccountOpenInfoVo.getContactDetailAddress());
		if (map.get("familyAddress") == map.get("contactAddress")){
			map.put("contactAddress", null);
		}
		map.put("familyNameSpell", customerAccountOpenInfoVo.getFamilyNameSpell()+" "+customerAccountOpenInfoVo.getGivenNameSpell());
		map.put("applicationTime", DateUtil.format(new Date(),"MM-dd-yyyy"));
		if (ObjectUtil.isNotEmpty(customerAccountOpenInfoVo.getNationality())){
			map.put("nationality", dictBizClient.getValue(NATIONALITY, customerAccountOpenInfoVo.getNationality(),language).getData());
		}
		map.put("birthday", DateUtil.format(DateUtil.parseDate(customerAccountOpenInfoVo.getBirthday()), "MM-dd-yyyy"));
		map.put("clientName2", customerAccountOpenInfoVo.getFamilyNameSpell()+" "+customerAccountOpenInfoVo.getGivenNameSpell());
		map.putAll(taxW8Info(applicationId));
		map.putAll(signW8Images(applicationId));

		try {
			if( customerAccountOpenInfoVo.getFamilyRepublicName().equals(customerAccountOpenInfoVo.getContactRepublicName())
				&& customerAccountOpenInfoVo.getFamilyProvinceName().equals(customerAccountOpenInfoVo.getContactProvinceName())
				&& customerAccountOpenInfoVo.getFamilyCityName().equals(customerAccountOpenInfoVo.getContactCityName())
				&& customerAccountOpenInfoVo.getFamilyCountyName().equals(customerAccountOpenInfoVo.getContactCountyName())
				&& customerAccountOpenInfoVo.getFamilyDetailAddress().equals(customerAccountOpenInfoVo.getContactDetailAddress())){
				map.put("contactProvinceName", null);
				map.put("contactCityName", null);
				map.put("contactCountyName", null);
				map.put("contactDetailAddress", null);
				map.put("contactRepublicName", null);
			}
		} catch (Exception e) {
		}
		return map;
	}

	private Map<String,?> getW8AddressInfoMap(AccountOpenInfoVO customerAccountOpenInfo) {
		//住宅地址转换
		if (customerAccountOpenInfo.getContactRepublicName()==null){
			customerAccountOpenInfo.setContactRepublicName("");
		}else{
			log.info("联系人国籍:{}",customerAccountOpenInfo.getContactRepublicName());
			customerAccountOpenInfo.setContactRepublicName(
				dictBizClient.getValue(NATIONALITY, customerAccountOpenInfo.getContactRepublicName(),language).getData()==null?
					"":dictBizClient.getValue(NATIONALITY, customerAccountOpenInfo.getContactRepublicName(),language).getData());
		}

		if (customerAccountOpenInfo.getContactProvinceName()==null){
			customerAccountOpenInfo.setContactProvinceName("");
		}else{
			log.info("联系地址省份:{}", customerAccountOpenInfo.getContactProvinceName());
			customerAccountOpenInfo.setContactProvinceName(addressMapper.getAddressInfoByAreaCodeValue(customerAccountOpenInfo.getContactProvinceName(), language)==null?
				"":addressMapper.getAddressInfoByAreaCodeValue(customerAccountOpenInfo.getContactProvinceName(), language));
		}
		if (customerAccountOpenInfo.getContactCityName()==null){
			customerAccountOpenInfo.setContactCityName("");
		}else{
			log.info("联系地址城市:{}", customerAccountOpenInfo.getContactCityName());
			customerAccountOpenInfo.setContactCityName(addressMapper.getAddressInfoByAreaCodeValue(customerAccountOpenInfo.getContactCityName(), language)==null?
				"":addressMapper.getAddressInfoByAreaCodeValue(customerAccountOpenInfo.getContactCityName(), language));
		}
		if (customerAccountOpenInfo.getContactCountyName()==null){
			customerAccountOpenInfo.setContactCountyName("");
		}else{
			log.info("联系地址区县:{}", customerAccountOpenInfo.getContactCountyName());
			customerAccountOpenInfo.setContactCountyName(addressMapper.getAddressInfoByAreaCodeValue(customerAccountOpenInfo.getContactCountyName(), language)==null?
				"":addressMapper.getAddressInfoByAreaCodeValue(customerAccountOpenInfo.getContactCountyName(), language));
		}

		//联系地址转换
		if (customerAccountOpenInfo.getFamilyRepublicName()==null){
			customerAccountOpenInfo.setFamilyRepublicName("");
		}else{
			log.info("家庭地址省份:{}", customerAccountOpenInfo.getFamilyRepublicName());
			customerAccountOpenInfo.setFamilyRepublicName(dictBizClient.getValue(NATIONALITY, customerAccountOpenInfo.getFamilyRepublicName(),language).getData()==null?
				"":dictBizClient.getValue(NATIONALITY, customerAccountOpenInfo.getFamilyRepublicName(),language).getData());
		}
		if (customerAccountOpenInfo.getFamilyProvinceName()==null){
			customerAccountOpenInfo.setFamilyProvinceName("");
		}else{
			log.info("家庭地址省份:{}", customerAccountOpenInfo.getFamilyProvinceName());
			customerAccountOpenInfo.setFamilyProvinceName(addressMapper.getAddressInfoByAreaCodeValue(customerAccountOpenInfo.getFamilyProvinceName(), language)==null?
				"":addressMapper.getAddressInfoByAreaCodeValue(customerAccountOpenInfo.getFamilyProvinceName(), language));
		}
		if (customerAccountOpenInfo.getFamilyCityName()==null){
			customerAccountOpenInfo.setFamilyCityName("");
		}else{
			log.info("家庭地址城市:{}", customerAccountOpenInfo.getFamilyCityName());
			customerAccountOpenInfo.setFamilyCityName(addressMapper.getAddressInfoByAreaCodeValue(customerAccountOpenInfo.getFamilyCityName(), language)==null?
				"":addressMapper.getAddressInfoByAreaCodeValue(customerAccountOpenInfo.getFamilyCityName(), language));
		}
		if (customerAccountOpenInfo.getFamilyCountyName()==null){
			customerAccountOpenInfo.setFamilyCountyName("");
		}else{
			log.info("家庭地址区县:{}", customerAccountOpenInfo.getFamilyCountyName());
			customerAccountOpenInfo.setFamilyCountyName(addressMapper.getAddressInfoByAreaCodeValue(customerAccountOpenInfo.getFamilyCountyName(), language)==null?
				"":addressMapper.getAddressInfoByAreaCodeValue(customerAccountOpenInfo.getFamilyCountyName(), language));
		}
		if (customerAccountOpenInfo.getFamilyDetailAddress()==null){
			customerAccountOpenInfo.setFamilyDetailAddress("");
		}else{
			log.info("家庭详细地址:{}", customerAccountOpenInfo.getFamilyDetailAddress());
			//转拼音
			customerAccountOpenInfo.setFamilyDetailAddress(PinyinHelper.toPinyin(customerAccountOpenInfo.getFamilyDetailAddress())==null?
				"":PinyinHelper.toPinyin(customerAccountOpenInfo.getFamilyDetailAddress()));
		}
		if (customerAccountOpenInfo.getContactDetailAddress()==null){
			customerAccountOpenInfo.setContactDetailAddress("");
		}else{
			log.info("联系详细地址:{}", customerAccountOpenInfo.getContactDetailAddress());
			customerAccountOpenInfo.setContactDetailAddress(PinyinHelper.toPinyin(customerAccountOpenInfo.getContactDetailAddress())==null?
				"":PinyinHelper.toPinyin(customerAccountOpenInfo.getContactDetailAddress()));
		}


		Map<String, Object> customerAccountOpenInfoMap =
			JSON.parseObject(JSON.toJSONString(customerAccountOpenInfo), Map.class);
		return customerAccountOpenInfoMap;
	}


	/**
	 * 自我证明表格
	 * @param applicationId
	 * @return
	 */
	public Map<String, Object> loadSelfReportData(String applicationId) {
		AccountOpenInfoVO customerAccountOpenInfoVo = accountOpenInfoMapper.queryByApplicationId(applicationId);
		Map<String, Object> map = new HashMap<>();
		map.putAll(getW8AddressInfoMap(customerAccountOpenInfoVo));
		map.put("familyNameSpell", customerAccountOpenInfoVo.getFamilyNameSpell());
		map.put("givenNameSpell", customerAccountOpenInfoVo.getGivenNameSpell());
		map.put("applicationTime", DateUtil.format(new Date(),"MM-dd-yyyy"));
		map.put("birthday", DateUtil.format(DateUtil.parseDate(customerAccountOpenInfoVo.getBirthday()), "MM-dd-yyyy"));
		map.put("placeOfBirth", dictBizClient.getValue(NATIONALITY, customerAccountOpenInfoVo.getPlaceOfBirth(),language).getData());
		map.put("clientName2", customerAccountOpenInfoVo.getFamilyNameSpell()+" "+customerAccountOpenInfoVo.getGivenNameSpell());

		try {
			if( customerAccountOpenInfoVo.getFamilyRepublicName().equals(customerAccountOpenInfoVo.getContactRepublicName())
				&& customerAccountOpenInfoVo.getFamilyProvinceName().equals(customerAccountOpenInfoVo.getContactProvinceName())
				&& customerAccountOpenInfoVo.getFamilyCityName().equals(customerAccountOpenInfoVo.getContactCityName())
				&& customerAccountOpenInfoVo.getFamilyCountyName().equals(customerAccountOpenInfoVo.getContactCountyName())
				&& customerAccountOpenInfoVo.getFamilyDetailAddress().equals(customerAccountOpenInfoVo.getContactDetailAddress())){
				map.put("contactProvinceName", null);
				map.put("contactCityName", null);
				map.put("contactCountyName", null);
				map.put("contactDetailAddress", null);
				map.put("contactRepublicName", null);
			}
		} catch (Exception e) {
		}
		map.putAll(cardInfo(applicationId));
		map.putAll(taxW8Info(applicationId));
		map.putAll(signImages(applicationId));
		return map;
	}

	public Map<String, Object> w8AddressInfo(String applicationId) {
		AccountW8benInfoVO accountW8benInfoVo = accountW8benInfoService.queryByApplicationId(applicationId);
		Map<String, Object> w8AddressInfo =
			JSON.parseObject(JSON.toJSONString(accountW8benInfoVo), Map.class);
		if (StringUtils.isBlank(accountW8benInfoVo.getW8ForeignTaxIdentifyingNumber()) || accountW8benInfoVo.getW8ForeignTaxIdentifyingNumber().equals("No tax number")) {
			w8AddressInfo.put("w8TaxRequired", 1);
			w8AddressInfo.put("w8ForeignTaxIdentifyingNumber", null);
		}
		w8AddressInfo.put("w8PermanentResidenceAddressCountry", dictBizClient.getValue(NATIONALITY_EN, accountW8benInfoVo.getW8PermanentResidenceAddressCountry()).getData());
		w8AddressInfo.put("w8MailingAddressCountry", dictBizClient.getValue(NATIONALITY_EN, accountW8benInfoVo.getW8MailingAddressCountry()).getData());
		if (ObjectUtil.isNotNull(accountW8benInfoVo.getW8TreatBenefits()) && accountW8benInfoVo.getW8TreatBenefits() == 1) {
			w8AddressInfo.put("w8TreatBenefitsCountry", dictBizClient.getValue(NATIONALITY_EN, accountW8benInfoVo.getW8TreatBenefitsCountry()).getData());
		} else {
			w8AddressInfo.put("w8TreatBenefitsCountry", "");
		}
		return w8AddressInfo;
	}

	/**
	 * 加载所有数据
	 *
	 * @param applicationId
	 * @return
	 */
	public Map<String, Object> loadAllReportData(String applicationId) {
		AccountOpenInfoVO customerAccountOpenInfo = customerAccountOpenInfoService().queryByApplicationId(applicationId);
		Map<String, Object> customerAccountOpenInfoMap = getAddressInfoMap(customerAccountOpenInfo);

		Map<String, Object> reportData = new HashMap<>();
		reportData.putAll(customerAccountOpenInfoMap);
		String client = customerAccountOpenInfo.getClientId();
		if (StringUtils.isEmpty(client)) {
			R result = null;
			try {
				result = customerInfoClient.selectAccountIdByCustId(customerAccountOpenInfo.getUserId());
				if (result.isSuccess()) {
					client = (String) result.getData();
				}
			} catch (Exception e) {
				log.error("生产开户通知函pdf获取理财账号错误：{}", result.getMsg());
			}
		}
		reportData.put("clientId", client);
		reportData.put("aeCode", customerAccountOpenInfo.getAeCode());
		AccountOpenApplicationVO customerAccountOpenApplication = customerAccountOpenApplicationService.queryByApplicationId(applicationId);
		if (StringUtils.isNotBlank(customerAccountOpenApplication.getLastApprovalUser())) {
			reportData.put("lastApprovalUser", customerAccountOpenApplication.getLastApprovalUser());
			reportData.put("lastApprovalTime", DateUtil.format(customerAccountOpenApplication.getLastApprovalTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		reportData.put("applicationTime", DateUtil.format(customerAccountOpenInfo.getApplicationTime(), "yyyy-MM-dd HH:mm:ss"));
		reportData.put("year", DateUtil.format(customerAccountOpenInfo.getApplicationTime(), "yyyy").substring(2));
		reportData.put("month", DateUtil.format(customerAccountOpenInfo.getApplicationTime(), "MM"));
		reportData.put("day", DateUtil.format(customerAccountOpenInfo.getApplicationTime(), "dd"));


		reportData.put("familyPhoneCode", telSplit(customerAccountOpenInfo.getFamilyPhone())[0]);
		reportData.put("familyPhone", telSplit(customerAccountOpenInfo.getFamilyPhone())[1]);
		reportData.put("nationality", dictBizClient.getValue(NATIONALITY, customerAccountOpenInfo.getNationality()).getData());
		reportData.put("phoneNumberCode", StringUtils.isBlank(phoneSplit(customerAccountOpenInfo.getPhoneNumber())[0])
			? customerAccountOpenInfo.getPhoneArea()
			: phoneSplit(customerAccountOpenInfo.getPhoneNumber())[0]);
		reportData.put("phoneNumber", phoneSplit(customerAccountOpenInfo.getPhoneNumber())[1]);
		reportData.put("companyPhoneNumberCode", telSplit(customerAccountOpenInfo.getCompanyPhoneNumber())[0]);
		reportData.put("companyPhoneNumber", telSplit(customerAccountOpenInfo.getCompanyPhoneNumber())[1]);
		reportData.put("companyFacsimileCode", telSplit(customerAccountOpenInfo.getCompanyFacsimile())[0]);
		reportData.put("companyFacsimile", telSplit(customerAccountOpenInfo.getCompanyFacsimile())[1]);
		reportData.put("tradeFrequencyValue", dictBizClient.getValue(TRADE_STOCK_FREQUENCY, customerAccountOpenInfo.getTradeFrequency().toString()).getData());
		reportData.put("tradeAmountValue", dictBizClient.getValue(TRADE_STOCK_MONEY, customerAccountOpenInfo.getTradeAmount().toString()).getData());
		reportData.put("placeOfBirth", dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfo.getPlaceOfBirth()).getData());
		reportData.put("netAssetOther", StringUtils.isNotBlank(customerAccountOpenInfo.getNetAssetOther()) ? customerAccountOpenInfo.getNetAssetOther() + "亿" : "");
		reportData.put("annualIncomeOther", StringUtils.isNotBlank(customerAccountOpenInfo.getAnnualIncomeOther()) ? customerAccountOpenInfo.getAnnualIncomeOther() + "万" : "");
		if ("99".equals(customerAccountOpenInfo.getJobPosition())) {
			reportData.put("jobPosition", customerAccountOpenInfo.getJobPositionOther());
		} else {
			reportData.put("jobPosition", dictBizClient.getValue(JOB_POSITION, customerAccountOpenInfo.getJobPosition()).getData());
		}
		reportData.put("clientName2", StringUtils.isNotBlank(customerAccountOpenInfo.getClientName()) ? customerAccountOpenInfo.getClientName() : customerAccountOpenInfo.getClientNameSpell());


		if (ObjectUtil.isNotNull(customerAccountOpenInfo.getCompanyBusinessNature()) && customerAccountOpenInfo.getCompanyBusinessNature() == 99) {
			reportData.put("companyBusinessNature", "33");
			reportData.put("companyBusinessNatureOther", customerAccountOpenInfo.getCompanyBusinessNatureOther());
		}

		reportData.putAll(expectedCapitalSource(customerAccountOpenInfo.getExpectedCapitalSource()));
		reportData.putAll(capitalSource(customerAccountOpenInfo.getCapitalSource()));
		reportData.putAll(accountIdentityConfirm(applicationId));
		reportData.putAll(cardInfo(applicationId));
		reportData.put("witnesses", "CA认证");
		if (customerAccountOpenInfo.getIdKind() != 1) {
			reportData.put("witnesses", "香港银行入金认证");
			reportData.putAll(depositInfo(applicationId));
		}

		reportData.putAll(addressInfo(applicationId, false));
		reportData.putAll(taxInfo(applicationId));
		reportData.putAll(signImages(applicationId));
		reportData.putAll(accountOfficerSignatureTatement(applicationId));
		return reportData;
	}

	private Map<String, Object> getAddressInfoMap(AccountOpenInfoVO customerAccountOpenInfo) {
		//公司地址转换
		customerAccountOpenInfo.setCompanyProvinceName(sysAddressService.getAddressName(customerAccountOpenInfo.getCompanyProvinceName()));
		customerAccountOpenInfo.setCompanyCityName(sysAddressService.getAddressName(customerAccountOpenInfo.getCompanyCityName()));
		customerAccountOpenInfo.setCompanyCountyName(sysAddressService.getAddressName(customerAccountOpenInfo.getCompanyCountyName()));

		//联系地址转换
		customerAccountOpenInfo.setContactProvinceName(sysAddressService.getAddressName(customerAccountOpenInfo.getContactProvinceName()));
		customerAccountOpenInfo.setContactCityName(sysAddressService.getAddressName(customerAccountOpenInfo.getContactCityName()));
		customerAccountOpenInfo.setContactCountyName(sysAddressService.getAddressName(customerAccountOpenInfo.getContactCountyName()));

		//永久地址装换
		customerAccountOpenInfo.setPermanentProvinceName(sysAddressService.getAddressName(customerAccountOpenInfo.getPermanentProvinceName()));
		customerAccountOpenInfo.setPermanentCityName(sysAddressService.getAddressName(customerAccountOpenInfo.getPermanentCityName()));
		customerAccountOpenInfo.setPermanentCountyName(sysAddressService.getAddressName(customerAccountOpenInfo.getPermanentCountyName()));

		//证件地址转换
		customerAccountOpenInfo.setIdCardProvinceName(sysAddressService.getAddressName(customerAccountOpenInfo.getIdCardProvinceName()));
		customerAccountOpenInfo.setIdCardCityName(sysAddressService.getAddressName(customerAccountOpenInfo.getIdCardCityName()));
		customerAccountOpenInfo.setIdCardCountyName(sysAddressService.getAddressName(customerAccountOpenInfo.getIdCardCountyName()));

		Map<String, Object> customerAccountOpenInfoMap =
			JSON.parseObject(JSON.toJSONString(customerAccountOpenInfo), Map.class);
		return customerAccountOpenInfoMap;
	}

	/**
	 * 地址信息
	 *
	 * @param applicationId
	 * @param en            是否英文
	 * @return
	 */
	private Map<String, Object> addressInfo(String applicationId, Boolean en) {
		Map<String, Object> reportData = new HashMap<>();
		AccountOpenInfoVO customerAccountOpenInfo = customerAccountOpenInfoService().queryByApplicationId(applicationId);
		if (customerAccountOpenInfo != null) {
			if (StringUtils.isNotBlank(customerAccountOpenInfo.getFamilyDetailAddress())) {
				StringBuilder familyBuilder = new StringBuilder();
				familyBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getFamilyProvinceName()) ? customerAccountOpenInfo.getFamilyProvinceName() : "");
				familyBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getFamilyCityName()) ? customerAccountOpenInfo.getFamilyCityName() : "");
				familyBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getFamilyCountyName()) ? customerAccountOpenInfo.getFamilyCountyName() : "");
				familyBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getFamilyDetailAddress()) ? customerAccountOpenInfo.getFamilyDetailAddress() : "");
				if (en) {
					reportData.put("familyRepublicName", dictBizClient.getValue(COUNTRY_OR_REGION_EN, customerAccountOpenInfo.getFamilyRepublicName()).getData());
					reportData.put("familyAddress", PinyinHelper.toPinyin(familyBuilder.toString()));
				} else {
					reportData.put("familyRepublicName", dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfo.getFamilyRepublicName()).getData());
					reportData.put("familyAddress", familyBuilder.toString());

				}
			}
			if (StringUtils.isNotBlank(customerAccountOpenInfo.getPermanentDetailAddress())) {
				StringBuilder permanentBuilder = new StringBuilder();
				permanentBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getFamilyProvinceName()) ? customerAccountOpenInfo.getPermanentProvinceName() : "");
				permanentBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getPermanentCityName()) ? customerAccountOpenInfo.getPermanentCityName() : "");
				permanentBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getPermanentCountyName()) ? customerAccountOpenInfo.getPermanentCountyName() : "");
				permanentBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getPermanentDetailAddress()) ? customerAccountOpenInfo.getPermanentDetailAddress() : "");
				if (en) {
					reportData.put("permanentRepublicName", dictBizClient.getValue(COUNTRY_OR_REGION_EN, customerAccountOpenInfo.getPermanentRepublicName()).getData());
					reportData.put("permanentAddress", PinyinHelper.toPinyin(permanentBuilder.toString()));
				} else {
					reportData.put("permanentRepublicName", dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfo.getPermanentRepublicName()).getData());
					reportData.put("permanentAddress", permanentBuilder.toString());
				}

			}
			if (StringUtils.isNotBlank(customerAccountOpenInfo.getContactRepublicName())) {
				StringBuilder contactBuilder = new StringBuilder();
				contactBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getContactProvinceName()) ? customerAccountOpenInfo.getContactProvinceName() : "");
				contactBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getContactCityName()) ? customerAccountOpenInfo.getContactCityName() : "");
				contactBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getContactCountyName()) ? customerAccountOpenInfo.getContactCountyName() : "");
				contactBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getContactDetailAddress()) ? customerAccountOpenInfo.getContactDetailAddress() : "");
				if (en) {
					reportData.put("contactRepublicName", dictBizClient.getValue(COUNTRY_OR_REGION_EN, customerAccountOpenInfo.getContactRepublicName()).getData());
					reportData.put("contactAddress", PinyinHelper.toPinyin(contactBuilder.toString()));
				} else {
					reportData.put("contactRepublicName", dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfo.getContactRepublicName()).getData());
					reportData.put("contactAddress", contactBuilder.toString());
				}


			}
			if (StringUtils.isNotBlank(customerAccountOpenInfo.getCompanyDetailAddress())) {
				StringBuilder companyBuilder = new StringBuilder();
				companyBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getCompanyProvinceName()) ? customerAccountOpenInfo.getCompanyProvinceName() : "");
				companyBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getCompanyCityName()) ? customerAccountOpenInfo.getCompanyCityName() : "");
				companyBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getCompanyCountyName()) ? customerAccountOpenInfo.getCompanyCountyName() : "");
				companyBuilder.append(StringUtils.isNotBlank(customerAccountOpenInfo.getCompanyDetailAddress()) ? customerAccountOpenInfo.getCompanyDetailAddress() : "");
				if (en) {
					reportData.put("companyRepublicName", dictBizClient.getValue(COUNTRY_OR_REGION_EN, customerAccountOpenInfo.getCompanyRepublicName()).getData());
					reportData.put("companyAddress", PinyinHelper.toPinyin(companyBuilder.toString()));
				} else {
					reportData.put("companyRepublicName", dictBizClient.getValue(COUNTRY_OR_REGION, customerAccountOpenInfo.getCompanyRepublicName()).getData());
					reportData.put("companyAddress", companyBuilder);
				}

			}
		}

		return reportData;
	}

	/**
	 * 手机号分割
	 *
	 * @param phone
	 * @return
	 */
	private String[] phoneSplit(String phone) {
		String[] phoneArr = new String[]{"", ""};
		if (StringUtils.isNotBlank(phone)) {
			if (phone.contains("-")) {
				phoneArr = phone.split("-");
			} else {
				phoneArr = new String[]{"", phone};
			}
		}
		return phoneArr;
	}

	private static String[] telSplit(String phone) {
		String[] phoneArr = new String[]{"", ""};
		if (StringUtils.isNotBlank(phone)) {
			if (phone.contains("-")) {
				String str1 = phone.substring(0, phone.indexOf("-"));
				phoneArr[0] = str1;
				int index = phone.indexOf("-");
				String result = phone.substring(index + 1);
				phoneArr[1] = result;
			} else {
				phoneArr = new String[]{"", phone};
			}
		}
		return phoneArr;
	}

	/**
	 * 开户证件信息和补充证件信息
	 *
	 * @param applicationId
	 * @return
	 */
	private Map<String, Object> cardInfo(String applicationId) {
		Map<String, Object> map = new HashMap<>();
		map.put("idCardNo", "N/A");
		map.put("signingOrganization", "N/A");
		map.put("hkIdNo", "N/A");
		map.put("passportNo", "N/A");
		map.put("passportSigningOrganization", "N/A");
		AccountSupplementCertificateVO openAccountSupplementCertificateVo = openAccountSupplementCertificateService.queryByApplicationId(applicationId);
		if (openAccountSupplementCertificateVo != null) {
			if (openAccountSupplementCertificateVo.getSupIdKind() != null) {
				switch (openAccountSupplementCertificateVo.getSupIdKind()) {
					case 1:
						map.put("idCardNo", openAccountSupplementCertificateVo.getSupIdCardNumber());
						map.put("signingOrganization", "中国");
						break;
					case 2:
						map.put("hkIdNo", openAccountSupplementCertificateVo.getSupIdCardNumber());
						break;
					case 3:
						map.put("passportNo", openAccountSupplementCertificateVo.getSupIdCardNumber());
						map.put("passportSigningOrganization", dictBizClient.getValue(COUNTRY_OR_REGION, openAccountSupplementCertificateVo.getPlaceOfIssue().toString()).getData());
						break;
					case 4:
						map.put("idCardNo", openAccountSupplementCertificateVo.getSupIdCardNumber());
						map.put("signingOrganization", dictBizClient.getValue(COUNTRY_OR_REGION, openAccountSupplementCertificateVo.getIdCardPlaceOfIssue().toString()).getData());
						break;
					default:
						break;
				}
			}
		}
		AccountOpenInfoVO customerAccountOpenInfo = customerAccountOpenInfoService().queryByApplicationId(applicationId);
		switch (customerAccountOpenInfo.getIdKind()) {
			case 1:
				map.put("idCardNo", customerAccountOpenInfo.getIdNo());
				map.put("signingOrganization", "中国");
				break;
			case 2:
				map.put("hkIdNo", customerAccountOpenInfo.getIdNo());
				break;
			case 3:
				map.put("passportNo", customerAccountOpenInfo.getIdNo());
				map.put("passportSigningOrganization", dictBizClient.getValue(COUNTRY_OR_REGION, openAccountSupplementCertificateVo.getPlaceOfIssue().toString()));
				break;
			default:
				break;
		}
		return map;
	}

	private Map<String, Object> signW8Images(String applicationId) {
		Map<String, Object> map = new HashMap<>();
		AccountOpenImageVO customerAccountOpenImageVo = customerAccountOpenImageService.queryOneByApplicationId(applicationId, ImageDescEnum.W8_SIGN.getLocation(), ImageDescEnum.W8_SIGN.getLocationType());
		if (customerAccountOpenImageVo != null) {
			String url = customerAccountOpenImageVo.getStoragePath();
			map.put("signImage", url);
		}else {
			customerAccountOpenImageVo = customerAccountOpenImageService.queryOneByApplicationId(applicationId, ImageDescEnum.SIGN.getLocation(), ImageDescEnum.SIGN.getLocationType());
			if (customerAccountOpenImageVo != null) {
				String url = customerAccountOpenImageVo.getStoragePath();
				map.put("signImage", url);
			}
		}
		return map;
	}
	private Map<String, Object> signImages(String applicationId) {
		Map<String, Object> map = new HashMap<>();
		AccountOpenImageVO customerAccountOpenImageVo = customerAccountOpenImageService.queryOneByApplicationId(applicationId, 3, 301);
		if (customerAccountOpenImageVo != null) {
			String url = customerAccountOpenImageVo.getStoragePath();

			map.put("signImage", url);
		}
		return map;
	}

	/**
	 * 身份信息确认
	 *
	 * @param applicationId
	 * @return
	 */
	private Map<String, Object> accountIdentityConfirm(String applicationId) {
		AccountIdentityConfirmVO openAccountIdentityConfirm = openAccountIdentityConfirmService.queryByApplicationId(applicationId);
		Map<String, Object> customerAccountOpenInfoMap = new HashMap<>();
		if (openAccountIdentityConfirm != null) {
			customerAccountOpenInfoMap = JSON.parseObject(JSON.toJSONString(openAccountIdentityConfirm), Map.class);
			if (StringUtils.isNotBlank(openAccountIdentityConfirm.getEmployedRelation())) {
				customerAccountOpenInfoMap.put("employedRelation", dictBizClient.getValue(CUSTOMER_RELATIONS, openAccountIdentityConfirm.getEmployedRelation()).getData());
			}
			if (StringUtils.isNotBlank(openAccountIdentityConfirm.getLinealRelativesJobTime())) {
				String[] linealRelativesJobTimes = openAccountIdentityConfirm.getLinealRelativesJobTime().split("-");
				if (linealRelativesJobTimes.length == 2) {
					customerAccountOpenInfoMap.put("linealRelativesJobTimeStart", linealRelativesJobTimes[0]);
					customerAccountOpenInfoMap.put("linealRelativesJobTimeEnd", linealRelativesJobTimes[1]);
				}
				String customerRelations = "";
				if (openAccountIdentityConfirm.getCustomerRelations() != null) {
					customerRelations = dictBizClient.getValue(CUSTOMER_RELATIONS, String.valueOf(openAccountIdentityConfirm.getCustomerRelations())).getData();
				}
				customerAccountOpenInfoMap.put("customerRelations", StringUtils.isNotBlank(customerRelations) ? customerRelations : "");
			}
		}

		return customerAccountOpenInfoMap;
	}

	/**
	 * 入金银行卡信息
	 *
	 * @param applicationId
	 * @return
	 */
	private Map<String, Object> depositInfo(String applicationId) {
		AccountDepositInfoVO openAccountDepositInfoVo = openAccountDepositInfoService.queryByApplicationId(applicationId);
		Map<String, Object> openAccountDepositMap = new HashMap<>();
		if (openAccountDepositInfoVo != null) {
			openAccountDepositMap = JSON.parseObject(JSON.toJSONString(openAccountDepositInfoVo), Map.class);
			openAccountDepositMap.put("depositCurrency", dictBizClient.getValue(DEPOSIT_CURRENCY, openAccountDepositInfoVo.getDepositCurrencyType().toString()).getData());
			openAccountDepositMap.put("outBank", dictBizClient.getValue(OUT_BANK, openAccountDepositInfoVo.getOutBank()).getData());
			openAccountDepositMap.put("bankAccountNumber", openAccountDepositInfoVo.getBankCode() + "-" + openAccountDepositInfoVo.getBankAccountNumber());
		}
		return openAccountDepositMap;
	}

	/**
	 * 税务信息
	 *
	 * @param applicationId
	 * @return
	 */
	private Map<String, Object> taxInfo(String applicationId) {
		Map<String, Object> map = new HashMap<>();
		String additionalDisclosures = "";
		List<AccountTaxationInfoVO> openAccountTaxationInfoList = openAccountTaxationInfoMapper.queryListByApplicationId(applicationId);
		for (Integer i = 1; i <= openAccountTaxationInfoList.size(); i++) {
			map.put("taxCountry" + i, dictBizClient.getValue(COUNTRY_OR_REGION, openAccountTaxationInfoList.get(i - 1).getTaxCountry()).getData());
			map.put("taxNumber" + i, openAccountTaxationInfoList.get(i - 1).getTaxNumber());
			map.put("reasonType" + i, dictBizClient.getValue(TAX_REASON_VALUE, openAccountTaxationInfoList.get(i - 1).getReasonType()).getData());
			map.put("reasonDesc" + i, openAccountTaxationInfoList.get(i - 1).getReasonDesc());
			if (StringUtils.isNotBlank(openAccountTaxationInfoList.get(i - 1).getAdditionalDisclosures())) {
				additionalDisclosures = openAccountTaxationInfoList.get(i - 1).getAdditionalDisclosures();
			}
		}
		map.put("additionalDisclosures", additionalDisclosures);
		return map;
	}
	/**
	 * 税务信息
	 *
	 * @param applicationId
	 * @return
	 */
	private Map<String, Object> taxW8Info(String applicationId) {
		Map<String, Object> map = new HashMap<>();
		String additionalDisclosures = "";
		List<AccountTaxationInfoVO> openAccountTaxationInfoList = openAccountTaxationInfoMapper.queryListByApplicationId(applicationId);
		for (Integer i = 1; i <= openAccountTaxationInfoList.size(); i++) {
			map.put("taxCountry" + i,dictBizClient.getValue(NATIONALITY, openAccountTaxationInfoList.get(i - 1).getTaxCountry(),language).getData() );
			map.put("taxNumber" + i, openAccountTaxationInfoList.get(i - 1).getTaxNumber());
			map.put("reasonType" + i, dictBizClient.getValue(TAX_REASON_VALUE, openAccountTaxationInfoList.get(i - 1).getReasonType()).getData());
			map.put("reasonDesc" + i, openAccountTaxationInfoList.get(i - 1).getReasonDesc());
			if (StringUtils.isNotBlank(openAccountTaxationInfoList.get(i - 1).getAdditionalDisclosures())) {
				additionalDisclosures = openAccountTaxationInfoList.get(i - 1).getAdditionalDisclosures();
			}
		}
		map.put("additionalDisclosures", additionalDisclosures);
		return map;
	}

	/**
	 * @param applicationId
	 * @return
	 */
	private Map<String, Object> accountOfficerSignatureTatement(String applicationId) {
		Map<String, Object> map = new HashMap<>();
		OfficerSignatureTatementVO accountOfficerSignatureTatementVo = accountOfficerSignatureTatementService.queryByApplicationId(applicationId);
		if (ObjectUtil.isNotNull(accountOfficerSignatureTatementVo)) {
			map.put("meetCustomerYears", accountOfficerSignatureTatementVo.getMeetCustomerYears());
			if (accountOfficerSignatureTatementVo.getIntroduceModel() == 2) {
				map.put("introduceModel", accountOfficerSignatureTatementVo.getIntroduceModel());
				map.put("refereesAccount", accountOfficerSignatureTatementVo.getRefereesAccount());
			} else {
				map.put("introduceModel", accountOfficerSignatureTatementVo.getIntroduceModel());
				map.put("introduceModelOther", accountOfficerSignatureTatementVo.getIntroduceModelOther());
			}
			R<User> userR = userClient.userInfoById(accountOfficerSignatureTatementVo.getUpdateUser());
			if (userR.isSuccess()) {
				User sysUser = userR.getData();
				map.put("gro", sysUser.getRealName());
				map.put("groEmail", "Id:" + sysUser.getEmail() + "(Stamped by the system)");
				map.put("groCode", sysUser.getCode());
			}

			map.put("groApplicationTime", DateUtil.format(accountOfficerSignatureTatementVo.getUpdateTime(), "dd-MM-yyyy HH:mm"));
			map.put("groApplicationTimes", "Timestamp:" + DateUtil.format(accountOfficerSignatureTatementVo.getUpdateTime(), "dd-MM-yyyy HH:mm"));
		}
		return map;
	}

	/**
	 * 财富来源
	 *
	 * @param source
	 * @return
	 */
	private Map<String, Object> expectedCapitalSource(String source) {
		Map<String, Object> expectedCapitalSource = new HashMap<>();
		if (StringUtils.isNotBlank(source)) {
			String[] sources = source.split(",");
			for (String s : sources) {
				expectedCapitalSource.put("expectedCapitalSource_" + s, 1);
			}
		}
		return expectedCapitalSource;
	}

	/**
	 * 资金来源
	 *
	 * @param source
	 * @return
	 */
	private Map<String, Object> capitalSource(String source) {
		Map<String, Object> capitalSource = new HashMap<>();
		if (StringUtils.isNotBlank(source)) {
			String[] sources = source.split(",");
			for (String s : sources) {
				capitalSource.put("capitalSource_" + s, 1);
			}
		}
		return capitalSource;
	}

	private Map<String, Object> getActInfo(Map<String, Object> reportData, String applicationId) {
		return new HashMap<>();
	}

	public Map<String, String> getAECodeByChannel(String channelId) {
		return new HashMap<>();
	}

	private Map<String, String> getPackageInfo(String applicationId) {
		return new HashMap<>();
	}


	private Map<String, String> getInterestPackageInfo(String applicationId) {
		Map<String, String> map = new HashMap<>();
		return map;
	}


	private Map<String, Object> contentChannelData(String applicationId) {
		Map<String, Object> map = new HashMap<>();
		return map;
	}


	private Map<String, Object> contentFeeChannel(String applicationId) {
		return new HashMap<>();
	}

	private Map<String, Object> contentFee(String applicationId) {
		return new HashMap<>();
	}


	/**
	 * 根据报表类型获取获取相应模板路径
	 *
	 * @return
	 */
	private String getReportTemplatePathByReportType(String tenantId, AccountPdfEnum reportType) {
		String reportTemplatePath = null;
		String contextRoot = accountPdfPropertis.getTemplacePath();
		PDFTemplace pdfTemplace = accountPdfPropertis.getTenantMap().get(tenantId);
		switch (reportType) {
			case ACCOUNT_OPEN_REPORT_USER_FORM_REPORT:
				reportTemplatePath = pdfTemplace.getUserForm();
				break;
			case ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT:
				reportTemplatePath = pdfTemplace.getUserSelfProve();
				break;
			case ACCOUNT_OPEN_REPORT_USER_W8_REPORT:
				reportTemplatePath = pdfTemplace.getUserW8();
				break;
			case ACCOUNT_OPEN_REPORT_PERSONAL_DATA_COLLECTION_STATEMENT:
				reportTemplatePath = pdfTemplace.getPersonalDataCollectionStatement();
				break;
			case ACCOUNT_OPEN_REPORT_MARGIN_TRADING_AGREEMENT:
				reportTemplatePath = pdfTemplace.getMarginTradingAgreement();
				break;
			case ACCOUNT_OPEN_REPORT_SECURITIES_TRADING_AGREEMENT:
				reportTemplatePath = pdfTemplace.getSecuritiesTradingAgreement();
				break;
			case ACCOUNT_OPEN_REPORT_USER_SELF_CERTIFICATION_ON_US_REPORT:
				reportTemplatePath = pdfTemplace.getUserSelfCertificationOnUs();
				break;
			case ACCOUNT_OPEN_REPORT_STANDING_AUTHORITY:
				reportTemplatePath = pdfTemplace.getStandingAuthority();
				break;
			case ACCOUNT_OPEN_REPORT_CLIENTS_SIGNATURE_CARD:
				reportTemplatePath = pdfTemplace.getClientsSignatureCard();
				break;

			case ACCOUNT_OPEN_REPORT_FUTURES_UNP_USER_SELF_PROVE_REPORT:
				reportTemplatePath = pdfTemplace.getFuturesUnpUserSelfProve();
				break;
			case ACCOUNT_OPEN_REPORT_FUTURES_AGREEMENT:
				reportTemplatePath = pdfTemplace.getFuturesAgreement();
				break;
			case HONG_KONG_STOCK_FEE_SCHEDULE:
				reportTemplatePath = pdfTemplace.getHongKongStockFeeSchedule();
				break;
			case EXECUTE_TRADING_DISCLOSURE_DOCUMENTS_ON_OPTIMAL_TERMS:
				reportTemplatePath = pdfTemplace.getExecuteTradingDisclosureDocumentsOnOptimalTerms();
				break;
			case ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT:
				reportTemplatePath = pdfTemplace.getIFundUserForm();
				break;
			default:
				break;
		}

		if (null == reportTemplatePath) {
			throw new UnsupportedOperationException("无法获取报表模板信息");
		}

		return contextRoot + "/" + reportTemplatePath;
	}

	/**
	 * 生成报表输出路径
	 *
	 * @param applicationId
	 * @param reportType
	 * @return
	 */
	@Override
	public String makeOutputPath(String applicationId, AccountPdfEnum reportType) {
		AccountOpenApplicationVO accountOpenApplicationVO = customerAccountOpenApplicationService.queryByApplicationId(applicationId);
		StringBuilder outPutPath = new StringBuilder(getAccountOpenUserReportRootPath(accountOpenApplicationVO.getTenantId(), accountOpenApplicationVO.getApplicationId()));
		outPutPath.append(reportType.getName());
		outPutPath.append(".pdf");
		return outPutPath.toString();
	}



	public String getAccountOpenUserReportRootPath(String tenantId, String applicationId) {
		return accountPdfPropertis.getPlacePath() + "/" + tenantId + "/" + applicationId + "/";
	}


	@Override
	public R submitPreviewAgreement(PreviewW8AgreementDTO previewW8AgreementDTO) {
		AccountOpenInfoVO accountOpenInfoVO = accountOpenInfoMapper.queryByApplicationId(previewW8AgreementDTO.getApplicationId());
		Map<String, Object> reportData = loadW8ReportData(previewW8AgreementDTO, accountOpenInfoVO);

		String templatePath = getReportTemplatePathByReportType(accountOpenInfoVO.getTenantId(), Objects.requireNonNull(find(previewW8AgreementDTO.getType())));
		String outputPath = makeOutputPath(previewW8AgreementDTO.getApplicationId(), Objects.requireNonNull(find(previewW8AgreementDTO.getType())));
		if (PdfOperater.fillFile(templatePath, outputPath, reportData)) {
			log.info("-->PDF文件数据填充成功,数据内容:{},已生成PDF文件:{}", JSON.toJSONString(reportData), outputPath);
		} else {
			log.info("-->PDF文件数据填充失败,数据内容:{},未生成PDF文件!", JSON.toJSONString(reportData));
			return R.fail(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_GENERATE_PDF_FAILED_NOTICE));
		}
		MultipartFile file = FileUtil.getMultipartFile(FileUtils.file(outputPath));
		R<ZeroFile> ossResp = ossClient.uploadMinioFile(file, file.getOriginalFilename());
		if (ossResp.isSuccess()) {
			String ossUrl = ossResp.getData().getLink();
			log.info("-->OSS文件上传成功,文件路径:{}", ossUrl);
			return R.data(ossUrl);
		} else {
			log.info("-->OSS文件上传失败,原因:{}", ossResp.getMsg());
			return R.fail(I18nUtil.getMessage(ModifyOpenAccountMessageConstant.MODIFY_OPEN_ACCOUNT_GENERATE_PDF_FAILED_NOTICE));
		}
	}
	public Map<String, Object> loadW8ReportData(PreviewW8AgreementDTO previewW8AgreementDTO,AccountOpenInfoVO accountOpenInfoVO) {
		Map<String, Object> map = new HashMap<>();
		map.putAll(getW8AddressInfoMap(previewW8AgreementDTO));
		map.put("familyAddress", previewW8AgreementDTO.getFamilyProvinceName()+" "+previewW8AgreementDTO.getFamilyCityName()+" "+previewW8AgreementDTO.getFamilyDetailAddress());
		map.put("contactAddress", previewW8AgreementDTO.getContactProvinceName()+" "+previewW8AgreementDTO.getContactCityName()+" "+previewW8AgreementDTO.getContactDetailAddress());
		if (map.get("familyAddress") == map.get("contactAddress")){
			map.put("contactAddress", null);
		}
		if (previewW8AgreementDTO.getType()== ACCOUNT_OPEN_REPORT_USER_W8_REPORT.getValue()){
			map.put("familyNameSpell", accountOpenInfoVO.getFamilyNameSpell()+" "+accountOpenInfoVO.getGivenNameSpell());
		}else if (previewW8AgreementDTO.getType()== ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT.getValue()){
			map.put("idNo", accountOpenInfoVO.getIdNo());
			map.put("appellation", accountOpenInfoVO.getAppellation());
			map.put("familyNameSpell", accountOpenInfoVO.getFamilyNameSpell());
			map.put("givenNameSpell", accountOpenInfoVO.getGivenNameSpell());
		}
		map.put("applicationTime", DateUtil.format(new Date(),"MM-dd-yyyy"));
		if (ObjectUtil.isNotEmpty(accountOpenInfoVO.getNationality())){
			map.put("nationality", dictBizClient.getValue(NATIONALITY, accountOpenInfoVO.getNationality(),language).getData());
		}
		map.put("birthday", DateUtil.format(DateUtil.parseDate(accountOpenInfoVO.getBirthday()), "MM-dd-yyyy"));
		map.put("clientName2", accountOpenInfoVO.getFamilyNameSpell()+" "+accountOpenInfoVO.getGivenNameSpell());
		map.putAll(taxW8Info(previewW8AgreementDTO));
		map.put("birthday", DateUtil.format(DateUtil.parseDate(accountOpenInfoVO.getBirthday()), "MM-dd-yyyy"));
		map.put("placeOfBirth", dictBizClient.getValue(NATIONALITY, accountOpenInfoVO.getPlaceOfBirth(),language).getData());

		try {
			if( accountOpenInfoVO.getFamilyRepublicName().equals(accountOpenInfoVO.getContactRepublicName())
				&& accountOpenInfoVO.getFamilyProvinceName().equals(accountOpenInfoVO.getContactProvinceName())
				&& accountOpenInfoVO.getFamilyCityName().equals(accountOpenInfoVO.getContactCityName())
				&& accountOpenInfoVO.getFamilyCountyName().equals(accountOpenInfoVO.getContactCountyName())
				&& accountOpenInfoVO.getFamilyDetailAddress().equals(accountOpenInfoVO.getContactDetailAddress())){
				map.put("contactProvinceName", null);
				map.put("contactCityName", null);
				map.put("contactCountyName", null);
				map.put("contactDetailAddress", null);
				map.put("contactRepublicName", null);
			}
		} catch (Exception e) {
		}

		return map;
	}
	/**
	 * 税务信息
	 *
	 * @return
	 */
	private Map<String, Object> taxW8Info(PreviewW8AgreementDTO previewW8AgreementDTO) {
		Map<String, Object> map = new HashMap<>();
		String additionalDisclosures = "";
		List<TaxItem> openAccountTaxationInfoList = previewW8AgreementDTO.getTaxInfoList();
		for (Integer i = 1; i <= openAccountTaxationInfoList.size(); i++) {
			map.put("taxCountry" + i,dictBizClient.getValue(NATIONALITY, openAccountTaxationInfoList.get(i - 1).getTaxJurisdiction(),language).getData() );
			map.put("taxNumber" + i, openAccountTaxationInfoList.get(i - 1).getTaxCode());
		}
		map.put("additionalDisclosures", additionalDisclosures);
		return map;
	}
	private Map<String,?> getW8AddressInfoMap(PreviewW8AgreementDTO previewW8AgreementDTO) {
		//住宅地址转换
		if (previewW8AgreementDTO.getContactRepublicName()==null){
			previewW8AgreementDTO.setContactRepublicName("");
		}else{
			previewW8AgreementDTO.setContactRepublicName(dictBizClient.getValue(NATIONALITY, previewW8AgreementDTO.getContactRepublicName(),language).getData());
		}

		if (previewW8AgreementDTO.getContactProvinceName()==null){
			previewW8AgreementDTO.setContactProvinceName("");
		}else{
			previewW8AgreementDTO.setContactProvinceName(addressMapper.getAddressInfoByAreaCodeValue(previewW8AgreementDTO.getContactProvinceName(), language));
		}

		if (previewW8AgreementDTO.getContactCityName()==null){
			previewW8AgreementDTO.setContactCityName("");
		}else{
			previewW8AgreementDTO.setContactCityName(addressMapper.getAddressInfoByAreaCodeValue(previewW8AgreementDTO.getContactCityName(), language));
		}
		if (previewW8AgreementDTO.getContactCountyName()==null){
			previewW8AgreementDTO.setContactCountyName("");
		}else{
			previewW8AgreementDTO.setContactCountyName(addressMapper.getAddressInfoByAreaCodeValue(previewW8AgreementDTO.getContactCountyName(), language));
		}

		//联系地址转换
		if (previewW8AgreementDTO.getFamilyRepublicName()==null){
			previewW8AgreementDTO.setFamilyRepublicName("");
		}else{
			previewW8AgreementDTO.setFamilyRepublicName(dictBizClient.getValue(NATIONALITY, previewW8AgreementDTO.getFamilyRepublicName(),language).getData());
		}
		if (previewW8AgreementDTO.getFamilyProvinceName()==null){
			previewW8AgreementDTO.setFamilyProvinceName("");
		}else{
			previewW8AgreementDTO.setFamilyProvinceName(addressMapper.getAddressInfoByAreaCodeValue(previewW8AgreementDTO.getFamilyProvinceName(), language));
		}
		if (previewW8AgreementDTO.getFamilyCityName()==null){
			previewW8AgreementDTO.setFamilyCityName("");
		}else{
			previewW8AgreementDTO.setFamilyCityName(addressMapper.getAddressInfoByAreaCodeValue(previewW8AgreementDTO.getFamilyCityName(), language));
		}
		if (previewW8AgreementDTO.getFamilyCountyName()==null){
			previewW8AgreementDTO.setFamilyCountyName("");
		}else{
			previewW8AgreementDTO.setFamilyCountyName(addressMapper.getAddressInfoByAreaCodeValue(previewW8AgreementDTO.getFamilyCountyName(), language));
		}

		Map<String, Object> previewW8AgreementDTOMap =
			JSON.parseObject(JSON.toJSONString(previewW8AgreementDTO), Map.class);
		return previewW8AgreementDTOMap;
	}


}

