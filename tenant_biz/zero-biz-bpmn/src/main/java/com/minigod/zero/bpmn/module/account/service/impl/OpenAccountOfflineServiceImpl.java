package com.minigod.zero.bpmn.module.account.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.biz.common.utils.DateUtils;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.bpm.enums.OpenAccountEnum;
import com.minigod.zero.bpmn.module.account.api.InvestQuestionnaires;
import com.minigod.zero.bpmn.module.account.bo.OpenAccountBo;
import com.minigod.zero.bpmn.module.account.bo.OpenAttachmentBo;
import com.minigod.zero.bpmn.module.account.bo.OpenImgBo;
import com.minigod.zero.bpmn.module.account.bo.OpenInfoBo;
import com.minigod.zero.bpmn.module.account.entity.AccountBankVerityInfoEntity;
import com.minigod.zero.bpmn.module.account.entity.AccountDepositInfoEntity;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenImgEntity;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.enums.CustomOpenAccountEnum;
import com.minigod.zero.bpmn.module.account.service.*;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import com.minigod.zero.bpmn.module.account.vo.AccountSupplementCertificateVO;
import com.minigod.zero.bpmn.module.account.vo.OpenAttachmentVo;
import com.minigod.zero.bpmn.module.account.vo.OpenImgVo;
import com.minigod.zero.bpmn.module.constant.OpenAccountMessageConstant;
import com.minigod.zero.bpmn.module.feign.IOfflineAccountOpenInfoClient;
import com.minigod.zero.bpmn.module.feign.dto.OfflineOpenAccountDTO;
import com.minigod.zero.bpmn.utils.ImageUtils;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.constant.ZeroConstant;
import com.minigod.zero.resource.feign.IOssClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 线下开户
 *
 * @author eric
 * @since 2024-016-12 17:52:01
 */
@Slf4j
@AllArgsConstructor
@Service
public class OpenAccountOfflineServiceImpl implements IOpenAccountOfflineService {
	private final IOssClient iOssClient;
	private final ICustomOpenInfoService iCustomOpenInfoService;
	private final ICustomOpenImgService iCustomOpenImgService;
	private final IAccountOpenInfoService iAccountOpenInfoService;
	private final IAccountDepositInfoService iAccountDepositInfoService;
	private final IOfflineAccountOpenInfoClient iOfflineAccountOpenInfoClient;
	private final IAccountBankVerityInfoService iAccountBankVerityInfoService;
	private final IAccountSupplementCertificateService iAccountSupplementCertificateService;

	/**
	 * 提交线下开户资料
	 *
	 * @param params
	 */
	@Override
	public void submitOpenInfo(OpenInfoBo params) {
		if (params == null) {
			log.error("-->提交的线下开户参数对象-OpenInfoBo为空!");
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_PARAM_ERROR_NOTICE));
		}
		log.info("-->提交的线下开户最原始的参数:{}", JSONObject.toJSONString(params));
		Integer openType = params.getOpenType();
		Integer accessWay = params.getAccessWay();
		Integer acceptRisk = params.getAcceptRisk();
		Integer fundAccountType = params.getFundAccountType();
		Integer lang = params.getLanguage();
		Integer inviteId = params.getInviteId();
		String channelId = params.getChannelId();
		String phoneNum = params.getPhoneNum();
		String phoneArea = params.getPhoneArea();
		String bankCardNo = params.getBankCardNo();
		ArrayList<Integer> accountMarkets = params.getAccountMarkets();
		List<String> stockTypes = params.getStockTypes();
		String tenantId = AuthUtil.getTenantId();

		log.info("-->线下提交的开户参数:tenantId={},openType={},accessWay={},fundAccountType={},lang={},inviteId={},channelId={},phoneNum={},phoneArea={},bankCardNo={},accountMarkets={}",
			tenantId, openType, accessWay, fundAccountType,
			lang, inviteId, channelId, phoneNum,
			phoneArea, bankCardNo, accountMarkets);

		Map formData = params.getFormData();
		CustomOpenAccountEnum.OpenType openTypeInfo = CustomOpenAccountEnum.OpenType.getType(openType);
		CustomOpenAccountEnum.FundAccountType fundAccountTypeInfo = CustomOpenAccountEnum.FundAccountType.getType(fundAccountType);

		if (lang == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_LANG_ERROR_NOTICE));
		}
		if (accessWay == null || accessWay != OpenAccountEnum.OpenAccountAccessWay.OFFLINE.getCode()) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_OFFLINE_SUBMIT_DATA_ACCESSWAY_ERROR_NOTICE));
		}
		if (openTypeInfo == null || !CustomOpenAccountEnum.OpenType.OFFLINE.getCode().equals(openTypeInfo.getCode())) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_OFFLINE_SUBMIT_DATA_OPENTYPE_ERROR_NOTICE));
		}
		if (fundAccountTypeInfo == null || !CustomOpenAccountEnum.FundAccountType.isContainCertType(fundAccountTypeInfo.getCode())) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_FUNDACCOUNTTYPE_ERROR_NOTICE));
		}
		if (accountMarkets == null || accountMarkets.size() == 0) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_ACCOUNTMARKETS_ERROR_NOTICE));
		}
		if (formData == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_FORMDATA_ERROR_NOTICE));
		}
		if (StringUtils.isEmpty(phoneNum)) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_PHONENUM_ERROR_NOTICE));
		}
		if (StringUtils.isEmpty(phoneArea)) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_PHONEAREA_ERROR_NOTICE));
		}
		// 推荐人、渠道等字段，保留，后期可能使用
		if (inviteId == null || inviteId == 0) {
			inviteId = 1;
		}
		if (StringUtils.isEmpty(channelId)) {
			channelId = "1";
		}

		OpenAccountBo openInfo = JSONObject.parseObject(JSONObject.toJSONString(formData), OpenAccountBo.class);
		if (openInfo == null) {
			log.error("-->线下开户表单数据转换失败,将FormData转换成OpenAccountBo失败!");
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_OFFLINE_FORM_DATA_CONVERT_ERROR_NOTICE));
		}
		// 投资知识调查问卷
		List<InvestQuestionnaires.QuestionAnswer> questionAnswers = new ArrayList<>();
		ArrayList<LinkedHashMap> questionArray = formData != null ? (ArrayList<LinkedHashMap>) formData.get("investQuestionnaires") : null;
		for (LinkedHashMap linkedHashMap : questionArray) {
			String questionNum = (String) linkedHashMap.get("questionNum");
			String questionDesc = (String) linkedHashMap.get("questionDesc");
			String answerNum = (String) linkedHashMap.get("answerNum");
			String answerDesc = (String) linkedHashMap.get("answerDesc");
			InvestQuestionnaires.QuestionAnswer questionAnswer = new InvestQuestionnaires.QuestionAnswer();
			questionAnswer.setQuestionNum(questionNum);
			questionAnswer.setQuestionDesc(questionDesc);
			questionAnswer.setAnswerNum(answerNum);
			questionAnswer.setAnswerDesc(answerDesc);
			questionAnswers.add(questionAnswer);
		}

		InvestQuestionnaires investQuestionnaires = new InvestQuestionnaires();
		investQuestionnaires.setQuestionAnswers(questionAnswers);
		openInfo.setInvestQuestionnaires(investQuestionnaires);

		// 参数校验 - 必填项
		this.checkOpenInfo(openInfo);
		//护照开户身份证信息
		if (openInfo.getIdCardInfo() != null) {
			if (StringUtils.isNotBlank(openInfo.getIdCardInfo().getSupIdCardNumber())) {
				AccountSupplementCertificateVO supIdCardInfo = iAccountSupplementCertificateService.queryBySupIdCardNumber(openInfo.getIdCardInfo().getSupIdCardNumber());
				if (supIdCardInfo != null) {
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_SUPPLEMENTARY_ID_ERROR_NOTICE), openInfo.getIdCardInfo().getSupIdCardNumber()));
				}
			}
		}
		//个人信息(大陆用户和香港用户),校验身份证号和邮箱其它用户是否已经使用过
		if (openInfo.getPersonalInfo() != null) {
			if (StringUtils.isNotBlank(openInfo.getPersonalInfo().getEmail())) {
				AccountOpenInfoVO emailInfo = iAccountOpenInfoService.queryByEmail(openInfo.getPersonalInfo().getEmail());
				if (emailInfo != null) {
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_EMAIL_ERROR_NOTICE), openInfo.getPersonalInfo().getEmail()));
				}
			}
			if (StringUtils.isNotBlank(openInfo.getPersonalInfo().getIdNo())) {
				AccountOpenInfoVO idNoInfo = iAccountOpenInfoService.queryByIdNo(openInfo.getPersonalInfo().getIdNo());
				if (idNoInfo != null) {
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_ID_ERROR_NOTICE), openInfo.getPersonalInfo().getIdNo()));
				}
			}
		}
		//银行四要素CA认证(大陆用户开户)
		if (openInfo.getVerifyFour() != null) {
			if (StringUtils.isNotBlank(openInfo.getVerifyFour().getBankNo())) {
				AccountBankVerityInfoEntity bankCardInfo = iAccountBankVerityInfoService.queryByBankCard(openInfo.getVerifyFour().getBankNo());
				if (bankCardInfo != null) {
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_BANK_CARD_NUMBER_ERROR_NOTICE), openInfo.getVerifyFour().getBankNo()));
				}
			}
			if (StringUtils.isNotBlank(openInfo.getVerifyFour().getIdNo())) {
				AccountBankVerityInfoEntity idNoInfo = iAccountBankVerityInfoService.queryByIdNo(openInfo.getVerifyFour().getIdNo());
				if (idNoInfo != null) {
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_ID_CARD_NUMBER_ERROR_NOTICE), openInfo.getVerifyFour().getIdNo()));
				}
			}
			if (StringUtils.isNotBlank(openInfo.getVerifyFour().getPhoneNumber())) {
				AccountBankVerityInfoEntity phoneNumberInfo = iAccountBankVerityInfoService.queryByPhoneNumber(openInfo.getVerifyFour().getPhoneNumber());
				if (phoneNumberInfo != null) {
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_PHONE_NUMBER_ERROR_NOTICE), openInfo.getVerifyFour().getPhoneNumber()));
				}
			}
		}
		//入金认证(香港用户开户)
		if (openInfo.getDepositInfo() != null) {
			if (StringUtils.isNotBlank(openInfo.getDepositInfo().getBankAccountNumber()) && StringUtils.isNotBlank(openInfo.getDepositInfo().getAccountHolderName())) {
				AccountDepositInfoEntity openAccountDepositInfo = iAccountDepositInfoService.queryByBankAccountNumber(
					openInfo.getDepositInfo().getBankAccountNumber(),
					openInfo.getDepositInfo().getAccountHolderName());
				if (openAccountDepositInfo != null) {
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_BANK_CARD_USED_ERROR_NOTICE), openInfo.getDepositInfo().getBankAccountNumber()));
				}
			}
		}

		// 校验投资知识问卷问题和对应的答案是否为空
		if (openInfo.getInvestQuestionnaires() != null) {
			boolean isEmptyFlag = false;
			for (InvestQuestionnaires.QuestionAnswer questionAnswer : openInfo.getInvestQuestionnaires().getQuestionAnswers()) {
				if (StringUtils.isEmpty(questionAnswer.getQuestionDesc())) {
					isEmptyFlag = true;
				}
				if (StringUtils.isEmpty(questionAnswer.getAnswerDesc())) {
					isEmptyFlag = true;
				}
				if (StringUtils.isEmpty(questionAnswer.getQuestionNum())) {
					isEmptyFlag = true;
				}
				if (StringUtils.isEmpty(questionAnswer.getAnswerNum())) {
					isEmptyFlag = true;
				}
				if (isEmptyFlag) {
					throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.CUSTOMER_QUESTION_ANSWER_IS_EMPTY));
				}
			}
		}

		OfflineOpenAccountDTO offlineOpenAccountDTO = new OfflineOpenAccountDTO();
		offlineOpenAccountDTO.setCellPhone(phoneNum);
		offlineOpenAccountDTO.setAreaCode(phoneArea);
		offlineOpenAccountDTO.setTenantId(tenantId);
		R<Map<String, Object>> result = iOfflineAccountOpenInfoClient.customerRegister(offlineOpenAccountDTO);
		if (!result.isSuccess()) {
			log.error("线下开户注册APP账户失败->原因:" + result.getMsg());
			throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_OFFLINE_REGISTRATION_APP_USER_ERROR_NOTICE), result.getMsg()));
		}

		if (result.getData() == null) {
			log.error("注册APP账户失败->原因:返回数据为空!");
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_OFFLINE_REGISTRATION_APP_USER_NULL_NOTICE));
		}

		Long userId = result.getData().get("id") != null ? Long.valueOf(result.getData().get("id").toString()) : null;
		if (userId == null) {
			log.error("注册APP账户失败->原因:返回CustId为空!");
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_OFFLINE_REGISTRATION_APP_USER_CUSTID_NULL_NOTICE));
		}

		// 补充资料
		formData.put("acceptRisk", acceptRisk);
		formData.put("userId", userId);
		formData.put("phoneNumber", StringUtils.isBlank(AuthUtil.getCustPhone()) ? phoneNum : AuthUtil.getCustPhone());
		formData.put("phoneArea", phoneArea);
		formData.put("bankCardNo", bankCardNo);

		// 账户类型
		formData.put("openAccountType", 1); // 开户类型 [0 = 未知 1 = 互联网 2 = 见证宝 3 = BPM]
		formData.put("openAccountAccessWay", accessWay); // 开户接入方式[1=H5开户 2=App 3=线下开户]
		formData.put("fundAccountType", fundAccountTypeInfo.getCode()); // 账户类型 1：现金账户 2：融资账户
		formData.put("language", lang); // 语言

		formData.put("inviterId", inviteId);
		formData.put("sourceChannelId", channelId);

		Integer idKind = openInfo.getPersonalInfo().getIdKind();
		String email = openInfo.getPersonalInfo().getEmail();
		String idCard = openInfo.getPersonalInfo().getIdNo();
		String backCard = openInfo.getBankCardNo();
		String userName = openInfo.getPersonalInfo().getFamilyName() + openInfo.getPersonalInfo().getGivenName();

		// 线上内地开户，判断身份证、银行卡校验情况
		if (openTypeInfo.getCode().equals(CustomOpenAccountEnum.OpenType.ONLINE_CN.getCode())) {
			// 18岁成年校验
			int age = getAge(idCard);
			if (age < 18) {
				throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_18_AGE_NOTICE));
			}
		}

		CustomOpenInfoEntity customOpenInfo = new CustomOpenInfoEntity();
		customOpenInfo.setUserId(userId);
		customOpenInfo.setIdKind(idKind);
		customOpenInfo.setIdCard(idCard);
		customOpenInfo.setOpenType(openType);
		customOpenInfo.setAccessWay(accessWay);
		customOpenInfo.setBankCard(backCard);
		customOpenInfo.setFundAccountType(fundAccountTypeInfo.getCode());
		customOpenInfo.setAccountMarkets(JSONUtil.toJson(accountMarkets));
		customOpenInfo.setStockTypes(StringUtils.join(stockTypes,","));
		customOpenInfo.setFormData(JSONUtil.toJson(formData));
		customOpenInfo.setStatus(CustomOpenAccountEnum.OpenStatus.PENDING.getCode()); // 开户中
		customOpenInfo.setPendingType(CustomOpenAccountEnum.PendingStatusType.DOING.getCode()); // 预批中
		customOpenInfo.setFailType(CustomOpenAccountEnum.FailStatusType.UN_KNOW.getCode());
		customOpenInfo.setCaStatus(CustomOpenAccountEnum.CaStatus.NONE.getCode());
		customOpenInfo.setCaValidId(null);
		customOpenInfo.setPushErrCount(0);
		customOpenInfo.setIsPushed(false);
		customOpenInfo.setIsNoticed(false);
		customOpenInfo.setIsSend(false);
		customOpenInfo.setOpenInviteId(inviteId);
		customOpenInfo.setOpenChannel(channelId);
		customOpenInfo.setCreateTime(new Date());
		customOpenInfo.setUpdateTime(new Date());
		customOpenInfo.setTenantId(tenantId);
		customOpenInfo.setUpdateUser(AuthUtil.getUserId());
		boolean submitSuccess = iCustomOpenInfoService.save(customOpenInfo);
		if (submitSuccess) {
			log.info("线下开户资料提交成功->userId:{}", userId);
		} else {
			log.error("线下开户资料提交失败->userId:{}", userId);
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_OFFLINE_SUBMIT_FAILED_NOTICE));
		}

		if (params.getFileIds() != null && params.getFileIds().size() > 0) {
			int rows = iCustomOpenImgService.updateAttachmentUserId(params.getFileIds(), userId);
			if (rows > 0) {
				log.info("线下开户资料更新附件成功->userId:" + userId + "->fileIds:" + params.getFileIds());
			} else {
				log.error("线下开户资料更新附件失败->userId:" + userId + "->fileIds:" + params.getFileIds());
				throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_OFFLINE_UPDATE_ATTACHMENT_FAILED_NOTICE));
			}
		}
	}

	/**
	 * 上传附件
	 *
	 * @param params
	 * @return
	 */
	@Override
	public OpenAttachmentVo uploadAttachment(OpenAttachmentBo params) {
		String originalFileName = params.getFile().getOriginalFilename();
		String suffix = org.apache.commons.lang3.StringUtils.substring(originalFileName, originalFileName.lastIndexOf("."), originalFileName.length());
		String uuid = IdUtil.fastSimpleUUID();
		String fileName = "offline-" + uuid + suffix;
		R<ZeroFile> ossResp = iOssClient.uploadMinioFile(params.getFile(), fileName);
		if (!ossResp.isSuccess()) {
			log.error("线下开户上传附件失败->失败原因:{}", ossResp.getMsg());
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_OFFLINE_UPLOAD_ATTACHMENT_FAILED_NOTICE));
		}
		ZeroFile zeroFile = ossResp.getData();
		String type = params.getType();
		String location = params.getLocation();

		CustomOpenImgEntity customOpenImgEntity = new CustomOpenImgEntity();
		customOpenImgEntity.setLocationKey(location);
		customOpenImgEntity.setLocationType(type);
		customOpenImgEntity.setIsError(false);
		customOpenImgEntity.setUrl(zeroFile.getLink());
		customOpenImgEntity.setCreateTime(new Date());
		customOpenImgEntity.setTenantId(ZeroConstant.ADMIN_TENANT_ID);
		customOpenImgEntity.setUpdateUser(AuthUtil.getUserId());
		iCustomOpenImgService.save(customOpenImgEntity);

		OpenAttachmentVo resVo = new OpenAttachmentVo();
		resVo.setAttachmentId(customOpenImgEntity.getId());
		resVo.setAttachmentUrl(zeroFile.getLink());
		resVo.setLocation(customOpenImgEntity.getLocationKey());
		resVo.setType(customOpenImgEntity.getLocationType());
		return resVo;
	}

	/**
	 * 保存用户开户图片数据
	 *
	 * @param params
	 * @return
	 */
	@Override
	public OpenImgVo saveOpenAccountImg(OpenImgBo params) {
		String location = params.getLocation();
		String type = params.getType();
		String base64Img = params.getImgBase64();
		// 参数校验 - 基本
		if (StringUtils.isBlank(location) || StringUtils.isBlank(type) || StringUtils.isBlank(base64Img)) {
			log.error("保存用户开户图片数据参数异常: location=%s,type=%s,base64Img=%s", location, type, base64Img);
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_IMAGE_ERROR_NOTICE));
		}


		byte[] b = ImageUtils.base64StringToImage(params.getImgBase64());
		String fileName = "offline-" + params.getType() + "_" + System.currentTimeMillis() + ".jpg";

		MultipartFile file = FileUtil.getMultipartFile(fileName, b);

		R<ZeroFile> ossResp = iOssClient.uploadMinioFile(file, file.getOriginalFilename());
		if (null == ossResp || null == ossResp.getData() || StringUtils.isBlank(ossResp.getData().getLink())) {
			throw new ServiceException(ResultCode.H5_DISPLAY_ERROR);
		}
		ZeroFile zeroFile = ossResp.getData();

		// 保存开户图片数据
		CustomOpenImgEntity customOpenImgEntity = new CustomOpenImgEntity();
		customOpenImgEntity.setLocationKey(location);
		customOpenImgEntity.setLocationType(type);
		customOpenImgEntity.setIsError(false);
		customOpenImgEntity.setUrl(zeroFile.getLink());
		customOpenImgEntity.setCreateTime(new Date());
		iCustomOpenImgService.save(customOpenImgEntity);

		OpenImgVo resVo = new OpenImgVo();
		resVo.setImgId(customOpenImgEntity.getId());
		resVo.setImgUrl(zeroFile.getLink());
		resVo.setLocation(customOpenImgEntity.getLocationKey());
		resVo.setType(customOpenImgEntity.getLocationType());
		return resVo;
	}

	/**
	 * 获取用户附件
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List<OpenAttachmentVo> queryAttachmentList(Long userId) {
		List<OpenAttachmentVo> attachmentVos = new ArrayList<>();

		//查询附件信息
		List<CustomOpenImgEntity> imgList = iCustomOpenImgService.selectByUserId(userId, "0", "0");
		int count = 1;
		for (CustomOpenImgEntity img : imgList) {
			if (!img.getIsError()) {
				OpenAttachmentVo attachmentVo = new OpenAttachmentVo();
				attachmentVo.setAttachmentUrl(img.getUrl());
				attachmentVo.setLocation(img.getLocationKey());
				attachmentVo.setType(img.getLocationType());
				attachmentVo.setAttachmentId(img.getId());
				attachmentVo.setAttachmentName("附件-" + count);
				attachmentVo.setCreateTime(img.getCreateTime());
				attachmentVo.setUpdateTime(img.getUpdateTime());
				attachmentVos.add(attachmentVo);
				count++;
			}
		}
		return attachmentVos;
	}

	/**
	 * 开户信息校验
	 *
	 * @param openInfo
	 * @throws ServiceException
	 */
	private void checkOpenInfo(OpenAccountBo openInfo) throws ServiceException {
		if (openInfo == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_PARAMETERS_NULL_NOTICE));
		}
		if (openInfo.getAccountInfo() == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_ACCOUNT_NULL_NOTICE));
		}
		if (openInfo.getPersonalInfo() == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_PERSONAL_NULL_NOTICE));
		}
		if (openInfo.getAddressInfo() == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_ADDRESS_NULL_NOTICE));
		}
		if (openInfo.getOccupationInfo() == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_CAREER_NULL_NOTICE));
		}
		if (openInfo.getTaxInfo() == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_TAX_NULL_NOTICE));
		}
		if (openInfo.getIdentityConfirmInfo() == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_IDENTITY_NULL_NOTICE));
		}
		if (openInfo.getAssetInvestmentInfo() == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_ASSET_NULL_NOTICE));
		}
		if (openInfo.getInvestQuestionnaires() == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_INVEST_QUESTIONNAIRES_NULL_NOTICE));
		}
	}

	/**
	 * idNumber 获取年龄
	 *
	 * @param idNumber
	 * @return
	 */
	private int getAge(String idNumber) {
		try {
			//如果是，定义正则表达式提取出身份证中的出生日期
			Pattern birthpPattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");
			//通过Pattern获得Matcher
			Matcher birthmMatcher = birthpPattern.matcher(idNumber);
			if (birthmMatcher.find()) {
				String year = birthmMatcher.group(1);
				String month = birthmMatcher.group(2);
				String date = birthmMatcher.group(3);

				String birthday = year + "-" + month + "-" + date + "";

				Date birth = DateUtils.stringToDate(birthday);
				Calendar cal = Calendar.getInstance();
				if (cal.before(birth)) {
					throw new IllegalArgumentException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_BIRTHDAY_ERROR_NOTICE));
				}
				int yearNow = cal.get(Calendar.YEAR);
				int monthNow = cal.get(Calendar.MONTH);
				int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
				cal.setTime(birth);
				int yearBirth = cal.get(Calendar.YEAR);
				int monthBirth = cal.get(Calendar.MONTH);
				int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH) + 1;
				int age = yearNow - yearBirth;
				if (monthNow <= monthBirth) {
					if (monthNow == monthBirth) {
						if (dayOfMonthNow < dayOfMonthBirth) {
							age--;
						}
					} else {
						age--;
					}
				}
				return age;
			}
		} catch (Exception e) {
			log.error("获取身份证生日异常", e);
		}
		return 0;
	}
}
