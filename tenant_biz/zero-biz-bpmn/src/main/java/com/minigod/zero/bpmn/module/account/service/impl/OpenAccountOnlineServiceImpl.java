package com.minigod.zero.bpmn.module.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.biz.common.utils.GlobalExecutorService;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.enums.OpenAccountEnum;
import com.minigod.zero.bpm.feign.IBpmAccountClient;
import com.minigod.zero.bpmn.module.account.api.InvestQuestionnaires;
import com.minigod.zero.bpmn.module.account.bo.*;
import com.minigod.zero.bpmn.module.account.constants.DictTypeConstant;
import com.minigod.zero.bpmn.module.account.entity.*;
import com.minigod.zero.bpmn.module.account.enums.CustomOpenAccountEnum;
import com.minigod.zero.bpmn.module.account.properties.VideoCompressOptions;
import com.minigod.zero.bpmn.module.account.service.*;
import com.minigod.zero.bpmn.module.account.vo.*;
import com.minigod.zero.bpmn.module.common.bo.OcrReqParams;
import com.minigod.zero.bpmn.module.common.enums.OcrEnum;
import com.minigod.zero.bpmn.module.constant.OpenAccountMessageConstant;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.utils.FileCompressorUtil;
import com.minigod.zero.bpmn.utils.ImageUtils;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.enums.EmailTemplate;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.resource.feign.IOssClient;
import com.minigod.zero.resource.feign.ITencentApiOcrClient;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class OpenAccountOnlineServiceImpl implements IOpenAccountOnlineService {

	private final ICustomOpenCacheInfoService iCustomOpenCacheInfoService;
	private final ICustomOpenImgService iCustomOpenImgService;
	private final ICustomOpenInfoService iCustomOpenInfoService;
	private final IOssClient iOssClient;
	private final IBpmAccountClient bpmAccountClient;
	private final ITencentApiOcrClient tencentApiOcrClient;
	private final IAccountBankVerityInfoService iAccountBankVerityInfoService;
	private final IAccountDepositInfoService iAccountDepositInfoService;
	private final IAccountOpenInfoService iAccountOpenInfoService;
	private final IAccountOpenApplicationService iAccountOpenApplicationService;
	private final IAccountSupplementCertificateService iAccountSupplementCertificateService;
	private final IDictBizClient iDictBizClient;
	private final IPlatformMsgClient platformMsgClient;
	private final VideoCompressOptions videoCompressOptions;

	/**
	 * 获取开户进度
	 *
	 * @param bo
	 * @return
	 */
	@Override
	public OpenUserInfoVo getOpenProgress(OpenProgressBo bo) {
		if (ObjectUtils.anyNull(bo, bo.getFlag())) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_GET_PROGRESS_PARAM_ERROR_NOTICE));
		}
		Long custId = AuthUtil.getTenantCustId();
		OpenUserInfoVo vo = new OpenUserInfoVo();
		CustomOpenInfoEntity customOpenInfoEntity = iCustomOpenInfoService.selectCustomOpenInfo(custId);

		if (customOpenInfoEntity != null) {
			vo.setOpenStatus(customOpenInfoEntity.getStatus());
			vo.setRemoteId(customOpenInfoEntity.getRemoteId());
		}

		AccountOpenInfoEntity accountOpenInfoEntity = iAccountOpenInfoService.queryByUserId(custId);
		if (accountOpenInfoEntity != null) {
			AccountOpenApplicationEntity accountOpenApplicationEntity = iAccountOpenApplicationService.queryByApplicationId(accountOpenInfoEntity.getApplicationId());
			if (accountOpenApplicationEntity != null) {
				vo.setIsBlackList(accountOpenApplicationEntity.getBlacklist() == 1);
			}
		}

		R<BpmTradeAcctRespDto> bpmTradeAcctRespDtoR = bpmAccountClient.bpmTradeAcctInfo();
		if (bpmTradeAcctRespDtoR.isSuccess()) {
			if (bpmTradeAcctRespDtoR.getData() != null) {
				vo.setTradeAccount(bpmTradeAcctRespDtoR.getData().getTradeAccount());
				vo.setFundAccount(bpmTradeAcctRespDtoR.getData().getCapitalAccount());
			}
		}
		vo.setUserId(custId);
		return vo;
	}

	/**
	 * 获取开户缓存数据
	 *
	 * @param params
	 * @return
	 */
	@Override
	public OpenCacheDataVo getCacheData(OpenCacheDataBo params) {
		if (params == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_GET_CACHE_DATA_PARAM_ERROR_NOTICE));
		}
		Integer step = params.getStep();
		// 参数校验 - 基本
		if (step == null || step < 0) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_GET_CACHE_DATA_STEP_VALUE_ERROR_NOTICE));
		}
		Long custId = AuthUtil.getTenantCustId();
		String tenantId = AuthUtil.getTenantId();
		OpenCacheDataVo resVo = new OpenCacheDataVo();

		// 查询所有
		if (step.equals(0)) {
			List<CustomOpenCacheInfoEntity> list = iCustomOpenCacheInfoService.selectByUserId(custId);

			Integer lastStep = 0;

			if (CollectionUtils.isNotEmpty(list)) {
				StringBuffer str = new StringBuffer();
				if (list.size() == 1) {
					CustomOpenCacheInfoEntity cache = list.get(0);
					str.append(cache.getJsonInfo());
				} else {
					for (int i = 0; i < list.size(); i++) {
						CustomOpenCacheInfoEntity cache = list.get(i);
						str.append(cache.getJsonInfo());
					}
				}
				String jsonInfo = str.toString().replace("}{", ",");
				log.info("jsonInfo::{}", jsonInfo);
				JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(jsonInfo);

				lastStep = json.getIntValue("lastStep");
				json.remove("lastStep");

				resVo.setCacheInfos(json);
			}

			resVo.setLastStep(lastStep);
		} else {
			CustomOpenCacheInfoEntity cache = iCustomOpenCacheInfoService.selectOneByUserIdAndStep(custId, step);
			if (cache != null) {
				Map json = JSONObject.parseObject(cache.getJsonInfo());
				resVo.setCacheInfos(json);
			}
		}

		log.info("从缓存查询数据结果{}", resVo.getCacheInfos());

		List<OpenImgVo> resImgList = new ArrayList<>();

		//查询图片信息
		List<CustomOpenImgEntity> imgList = iCustomOpenImgService.selectByUserId(custId);
		for (CustomOpenImgEntity img : imgList) {
			if (!img.getIsError()) {
				OpenImgVo imgResVo = new OpenImgVo();
				imgResVo.setImgUrl(img.getUrl());
				imgResVo.setLocation(img.getLocationKey());
				imgResVo.setType(img.getLocationType());
				resImgList.add(imgResVo);
			}
		}
		resVo.setCacheImages(resImgList);

		return resVo;
	}

	/**
	 * 保存开户缓存数据
	 *
	 * @param params
	 */
	@Override
	public void saveOrUpdateCacheInfoStep(OpenCacheInfoBo params) {
		// 参数校验
		if (params == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SAVE_CACHE_DATA_PARAMS_ERROR_NOTICE));
		}
		Integer step = params.getStep();
		Map info = params.getInfo();

		// 参数校验 - 基本
		if (step == null || info == null || step < 1) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_PACKET_INCORRECT_NOTICE));
		}
		Long userId = AuthUtil.getTenantCustId();
		if (!iCustomOpenInfoService.isCanUpdateOpenInfo(userId)) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_ALREADY_SUBMITED_NOTICE));
		}

		OpenAccountBo openInfo = JSONObject.parseObject(JSONObject.toJSONString(info), OpenAccountBo.class);
		if (openInfo == null) {
			log.error("openInfo(OpenAccountBo)对象为空, 将缓存数据对象(OpenCacheInfo)转换成业务对象(OpenAccountBo)失败!");
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_CONVERSION_FAILED_NOTICE));
		}
		log.info("-->提交到缓存的开户参数对象OpenCacheInfo:{}", JSONObject.toJSONString(openInfo));
		//护照开户身份证信息
		if (step == 2 && openInfo.getIdCardInfo() != null) {
			if (StringUtils.isNotBlank(openInfo.getIdCardInfo().getSupIdCardNumber())) {
				AccountSupplementCertificateVO supIdCardInfo = iAccountSupplementCertificateService.queryBySupIdCardNumber(openInfo.getIdCardInfo().getSupIdCardNumber());
				if (supIdCardInfo != null) {
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_SUPPLEMENTARY_ID_ERROR_NOTICE), openInfo.getIdCardInfo().getSupIdCardNumber()));
				}
			}
		}
		//个人信息(大陆用户和香港用户),校验身份证号和邮箱其它用户是否已经使用过
		if (step == 3 && openInfo.getPersonalInfo() != null) {
			if (StringUtils.isNotBlank(openInfo.getPersonalInfo().getEmail())) {
				AccountOpenInfoVO emailInfo = iAccountOpenInfoService.queryByEmail(openInfo.getPersonalInfo().getEmail());
				if (emailInfo != null) {
					log.error("提交到缓存的开户参数错误,email:{}已被他人使用!", openInfo.getPersonalInfo().getEmail());
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_EMAIL_ERROR_NOTICE), openInfo.getPersonalInfo().getEmail()));
				}
			}
			if (StringUtils.isNotBlank(openInfo.getPersonalInfo().getIdNo())) {
				AccountOpenInfoVO idNoInfo = iAccountOpenInfoService.queryByIdNo(openInfo.getPersonalInfo().getIdNo());
				if (idNoInfo != null) {
					log.error("提交到缓存的开户参数错误,证件号码:{}已被他人使用!", openInfo.getPersonalInfo().getIdNo());
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_ID_ERROR_NOTICE), openInfo.getPersonalInfo().getIdNo()));
				}
			}
		}
		//银行四要素CA认证(大陆用户开户)
		if (step == 13 && openInfo.getVerifyFour() != null) {
			if (StringUtils.isNotBlank(openInfo.getVerifyFour().getBankNo())) {
				AccountBankVerityInfoEntity bankCardInfo = iAccountBankVerityInfoService.queryByBankCard(openInfo.getVerifyFour().getBankNo());
				if (bankCardInfo != null) {
					log.error("提交到缓存的开户参数错误,银行卡号:{}已被他人使用!", openInfo.getVerifyFour().getBankNo());
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_BANK_CARD_NUMBER_ERROR_NOTICE), openInfo.getVerifyFour().getBankNo()));
				}
			}
			if (StringUtils.isNotBlank(openInfo.getVerifyFour().getIdNo())) {
				AccountBankVerityInfoEntity idNoInfo = iAccountBankVerityInfoService.queryByIdNo(openInfo.getVerifyFour().getIdNo());
				if (idNoInfo != null) {
					log.error("提交到缓存的开户参数错误,银行卡证件号码:{}已被他人使用!", openInfo.getVerifyFour().getIdNo());
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_ID_CARD_NUMBER_ERROR_NOTICE), openInfo.getVerifyFour().getIdNo()));
				}
			}
			if (StringUtils.isNotBlank(openInfo.getVerifyFour().getPhoneNumber())) {
				AccountBankVerityInfoEntity phoneNumberInfo = iAccountBankVerityInfoService.queryByPhoneNumber(openInfo.getVerifyFour().getPhoneNumber());
				if (phoneNumberInfo != null) {
					log.error("提交到缓存的开户参数错误,手机号码:{}已被他人使用!", openInfo.getVerifyFour().getPhoneNumber());
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_PHONE_NUMBER_ERROR_NOTICE), openInfo.getVerifyFour().getPhoneNumber()));
				}
			}
		}
		//入金认证(香港用户开户)
		if (step == 16 && openInfo.getDepositInfo() != null) {
			if (StringUtils.isNotBlank(openInfo.getDepositInfo().getBankAccountNumber()) && StringUtils.isNotBlank(openInfo.getDepositInfo().getAccountHolderName())) {
				AccountDepositInfoEntity openAccountDepositInfo = iAccountDepositInfoService.queryByBankAccountNumber(
					openInfo.getDepositInfo().getBankAccountNumber(),
					openInfo.getDepositInfo().getAccountHolderName());
				if (openAccountDepositInfo != null) {
					log.error("提交到缓存的开户参数错误,入金银行账户:{}已被他人使用!", openInfo.getDepositInfo().getBankAccountNumber());
					throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_DATA_BANK_CARD_USED_ERROR_NOTICE), openInfo.getDepositInfo().getBankAccountNumber()));
				}
			}
		}
		// 校验投资知识问卷问题和对应的答案是否为空
		InvestQuestionnaires investQuestionnaires = openInfo.getInvestQuestionnaires();
		if (step == 18 && investQuestionnaires != null) {
			boolean isEmptyFlag = investQuestionnaires.getQuestionAnswers().stream().anyMatch(questionAnswer ->
				StringUtils.isEmpty(questionAnswer.getQuestionNum()) || StringUtils.isEmpty(questionAnswer.getAnswerNum()));
			if (isEmptyFlag) {
				log.error("提交到缓存的开户参数错误,投资调研问卷问题或者答案不能为空!");
				throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.CUSTOMER_QUESTION_ANSWER_IS_EMPTY));
			}
		}


		iCustomOpenCacheInfoService.saveOrUpdateStepInfo(userId, step, JSON.toJSONString(info));
		iCustomOpenCacheInfoService.saveOrUpdateLastStepInfo(userId, step);
	}

	/**
	 * 提交开户资料
	 *
	 * @param params
	 */
	@Override
	public void submitOpenInfo(OpenInfoBo params) {
		// 参数校验
		if (params == null) {
			log.error("-->提交的开户参数对象-OpenInfoBo为空!");
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_PARAM_ERROR_NOTICE));
		}
		log.info("-->提交的开户最原始的参数:{}", JSONObject.toJSONString(params));
		Integer openType = params.getOpenType();
		Integer accessWay = params.getAccessWay();
		Integer fundAccountType = params.getFundAccountType();
		Integer lang = params.getLanguage();
		Integer inviteId = params.getInviteId();
		String channelId = params.getChannelId();
		String phoneNum = params.getPhoneNum();
		String phoneArea = params.getPhoneArea();
		String bankCardNo = params.getBankCardNo();
		ArrayList<Integer> accountMarkets = params.getAccountMarkets();
		Long userId = AuthUtil.getTenantCustId();
		String tenantId = AuthUtil.getTenantId();
		OpenCacheDataBo bo = new OpenCacheDataBo();
		bo.setStep(0);
		Map formData = getCacheData(bo).getCacheInfos();

		CustomOpenAccountEnum.OpenType openTypeInfo = CustomOpenAccountEnum.OpenType.getType(openType);
		CustomOpenAccountEnum.FundAccountType fundAccountTypeInfo = CustomOpenAccountEnum.FundAccountType.getType(fundAccountType);

		// 参数校验 - 基本
		log.info("-->线上提交的开户参数:tenantId={},openType={},accessWay={},fundAccountType={},lang={},inviteId={},channelId={},phoneNum={},phoneArea={},bankCardNo={},accountMarkets={}",
			tenantId, openType, accessWay, fundAccountType,
			lang, inviteId, channelId, phoneNum,
			phoneArea, bankCardNo, accountMarkets);

		if (lang == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_LANG_ERROR_NOTICE));
		}
		if (accessWay == null || accessWay != OpenAccountEnum.OpenAccountAccessWay.H5.getCode()) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_ACCESSWAY_ERROR_NOTICE));
		}
		if (openTypeInfo == null || !CustomOpenAccountEnum.OpenType.isContainCertType(openTypeInfo.getCode())) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_OPENTYPE_ERROR_NOTICE));
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

		// 本地开户记录
		CustomOpenInfoEntity localOpenInfo = iCustomOpenInfoService.selectCustomOpenInfo(userId);
		if (localOpenInfo != null) {
			if (localOpenInfo.getOpenInviteId() != null) {
				inviteId = localOpenInfo.getOpenInviteId();
			}

			if (StringUtils.isNotEmpty(localOpenInfo.getOpenChannel())) {
				channelId = localOpenInfo.getOpenChannel();
			}

			if (localOpenInfo.getStatus() != null) {
				// 不能重复提交
				if (localOpenInfo.getStatus().equals(CustomOpenAccountEnum.OpenStatus.PENDING.getCode()) || localOpenInfo.getStatus().equals(CustomOpenAccountEnum.OpenStatus.SUCCESS.getCode())) {
					CustomOpenAccountEnum.OpenStatus statusInfo = CustomOpenAccountEnum.OpenStatus.getStatus(localOpenInfo.getStatus());
					if (!statusInfo.getIsSubmit()) {
						throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_ALREADY_NOTICE));
					}
				}

				// 异常账户
				if (localOpenInfo.getStatus().equals(CustomOpenAccountEnum.OpenStatus.ACCOUNT_ABO.getCode())) {
					CustomOpenAccountEnum.OpenStatus statusInfo = CustomOpenAccountEnum.OpenStatus.getStatus(localOpenInfo.getStatus());
					if (!statusInfo.getIsSubmit()) {
						throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_ABNORMAL_NOTICE));
					}
				}
			}
		}

		// 补充资料
		formData.put("userId", userId);
		formData.put("phoneNumber", StringUtils.isBlank(AuthUtil.getCustPhone()) ? phoneNum : AuthUtil.getCustPhone());
		formData.put("phoneArea", phoneArea);
		formData.put("bankCardNo", bankCardNo);

		// 账户类型
		formData.put("openAccountType", 1); // 开户类型 [0 = 未知 1 = 互联网 2 = 见证宝 3 = BPM] TODO：此处暂时固定1
		formData.put("openAccountAccessWay", accessWay); // 开户接入方式[1=H5开户 2=App 3=线下开户]
		formData.put("fundAccountType", fundAccountTypeInfo.getCode()); // 账户类型 1：现金账户 2：融资账户
		formData.put("language", lang); // 语言

		formData.put("inviterId", inviteId);
		formData.put("sourceChannelId", channelId);

		OpenAccountBo openInfo = JSON.parseObject(JSONObject.toJSONString(formData), OpenAccountBo.class);

		// 投资知识调查问卷
		JSONArray investQuestionnairesArray = formData.get("investQuestionnaires") instanceof JSONArray ?
			(JSONArray) formData.get("investQuestionnaires") : null;
		List<InvestQuestionnaires.QuestionAnswer> questionAnswers = (investQuestionnairesArray != null && !investQuestionnairesArray.isEmpty()) ?
			investQuestionnairesArray.toJavaList(InvestQuestionnaires.QuestionAnswer.class) : null;
		InvestQuestionnaires investQuestionnaires = new InvestQuestionnaires();
		investQuestionnaires.setQuestionAnswers(questionAnswers);
		openInfo.setInvestQuestionnaires(investQuestionnaires);

		// 参数校验 - 必填项
		this.checkOpenInfo(openInfo);
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
		if (localOpenInfo != null) {
			customOpenInfo.setId(localOpenInfo.getId());
			iCustomOpenInfoService.updateById(customOpenInfo);
		} else {
			iCustomOpenInfoService.save(customOpenInfo);
		}

		String language = WebUtil.getLanguage();
		log.info("提交开户资料成功，当前语言类型->{}", language);
		GlobalExecutorService.getExecutor().execute(() -> {
			R<List<DictBiz>> emailAddress = iDictBizClient.getListByTenantId(tenantId, DictTypeConstant.SYSTEM_EMAIL_ADDRESS);
			if (emailAddress.isSuccess()) {
				List<String> emails = emailAddress.getData().stream().map(DictBiz::getDictKey).collect(Collectors.toList());
				log.info("提交开户资料成功，准备发送审核申请提醒邮件通知->{}", emails);

				// 标题参数
				List<String> titleParams = new ArrayList<>();
				titleParams.add(com.minigod.zero.bpmn.utils.DateUtils.getTime());
				titleParams.add(userName);
				// 内容参数
				List<String> bodyParams = new ArrayList<>();
				bodyParams.add(com.minigod.zero.bpmn.utils.DateUtils.getTime());
				bodyParams.add(userName);
				bodyParams.add(SystemCommonEnum.IdKindType.getDesc(String.valueOf(idKind)));
				bodyParams.add(String.format("+%s-%s", phoneArea, phoneNum));
				// 发送开户邮件
				SendEmailDTO sendEmailDTO = new SendEmailDTO();
				sendEmailDTO.setAccepts(emails);
				sendEmailDTO.setCode(EmailTemplate.ACCOUNT_OPENING_SUBMISSION_APPROVAL_REMINDER.getCode());
				sendEmailDTO.setList(bodyParams);
				sendEmailDTO.setTitleParam(titleParams);
				sendEmailDTO.setLang(language);

				R sendRes = platformMsgClient.sendEmail(sendEmailDTO);
				if (sendRes != null && sendRes.isSuccess()) {
					log.info("提交开户资料->发送审核提醒邮件成功,收件人列表:{}", emails);
				} else {
					log.error("提交开户资料->发送审核提醒邮件失败,收件人列表:{}", emails);
				}
			} else {
				log.error("提交开户资料->发送审核提醒邮件失败,【SYSTEM_EMAIL_ADDRESS配置】收件邮箱地址为空!");
			}
		});
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
		if (openInfo.getRiskDisclosureInfo() == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_RISK_DISCLOSURE_NULL_NOTICE));
		}
		if (openInfo.getAssetInvestmentInfo() == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_ASSET_NULL_NOTICE));
		}
		if (openInfo.getPersonalInfo().getIdKind() == 1 && openInfo.getVerifyFour() == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_BANK_FOUR_NULL_NOTICE));
		}
		if (openInfo.getInvestQuestionnaires() == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_INVEST_QUESTIONNAIRES_NULL_NOTICE));
		}
	}

	/**
	 * 获取年龄
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

				Date birth = com.minigod.zero.biz.common.utils.DateUtils.stringToDate(birthday);
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

	/**
	 * 保存用户开户图片数据
	 *
	 * @param params
	 * @return
	 */
	@Override
	public OpenImgVo saveCacheImg(OpenImgBo params) {
		try {
			String location = params.getLocation();
			String type = params.getType();
			String base64Img = params.getImgBase64();
			// 参数校验 - 基本
			if (StringUtils.isBlank(location) || StringUtils.isBlank(type) || StringUtils.isBlank(base64Img)) {
				log.error("保存用户开户图片数据参数异常: location=%s,type=%s,base64Img=%s", location, type, base64Img);
				throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_IMAGE_ERROR_NOTICE));
			}

			Long userId = AuthUtil.getTenantCustId();
			if (!iCustomOpenInfoService.isCanUpdateOpenInfo(userId)) {
				log.error("重复提交：saveOrUpdateImg_CN");
				throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_ALREADY_NOTICE));
			}
			byte[] b = ImageUtils.base64StringToImage(params.getImgBase64());
			String fileName = userId + "_" + params.getType() + "_" + System.currentTimeMillis() + ".jpg";
			MultipartFile file = FileUtil.getMultipartFile(fileName, b);
			R<ZeroFile> ossResp = null;
			if (FileCompressorUtil.isFileSizeExceeded(file.getSize(), 2)) {
				File compressedImage = FileCompressorUtil.compressImage(file, 50);
				FileInputStream input = new FileInputStream(compressedImage);
				MultipartFile compressedFile = new MockMultipartFile("file", compressedImage.getName(), "image/jpg", input);
				log.info("图片大小超过2MB限制,压缩后的图片文件: {}, 大小:{}", compressedImage.getAbsolutePath(), compressedImage.length());
				ossResp = iOssClient.uploadMinioFile(compressedFile, file.getOriginalFilename());
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
				ossResp = iOssClient.uploadMinioFile(file, file.getOriginalFilename());
			}
			if (null == ossResp || null == ossResp.getData() || StringUtils.isBlank(ossResp.getData().getLink())) {
				throw new ServiceException(ResultCode.H5_DISPLAY_ERROR);
			}
			log.info("上传图片成功, 图片文件路径:{}", ossResp.getData().getLink());
			ZeroFile zeroFile = ossResp.getData();

			// 查询当前步骤缓存数据
			CustomOpenImgEntity customOpenImgEntity = iCustomOpenImgService.selectByUserId(userId, type);

			if (customOpenImgEntity != null) {
				customOpenImgEntity.setUrl(zeroFile.getLink());
				customOpenImgEntity.setUpdateTime(new Date());
				iCustomOpenImgService.updateById(customOpenImgEntity);
			} else {
				customOpenImgEntity = new CustomOpenImgEntity();
				customOpenImgEntity.setUserId(userId);
				customOpenImgEntity.setLocationKey(location);
				customOpenImgEntity.setLocationType(type);
				customOpenImgEntity.setIsError(false);
				customOpenImgEntity.setUrl(zeroFile.getLink());
				customOpenImgEntity.setCreateTime(new Date());
				iCustomOpenImgService.save(customOpenImgEntity);
			}
			OpenImgVo resVo = new OpenImgVo();
			resVo.setImgId(customOpenImgEntity.getId());
			resVo.setImgUrl(zeroFile.getLink());
			resVo.setLocation(customOpenImgEntity.getLocationKey());
			resVo.setType(customOpenImgEntity.getLocationType());
			return resVo;
		} catch (IOException exception) {
			log.error("保存用户开户图片数据异常,异常详情:", exception);
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_IMAGE_EXCEPTION_NOTICE));
		}
	}

	/**
	 * 保存用户开户视频数据
	 *
	 * @param params
	 * @return
	 */
	@Override
	public OpenVideoVo saveCacheVideo(OpenVideoBo params) {
		try {
			// 参数校验
			Long userId = AuthUtil.getTenantCustId();
			String fileName = userId + "_" + System.currentTimeMillis() + ".mp4";
			R<ZeroFile> ossResp = null;
			if (FileCompressorUtil.isFileSizeExceeded(params.getFile().getSize(), 5)) {
				File compressedVideo = FileCompressorUtil.compressVideo(params.getFile(), videoCompressOptions);
				FileInputStream input = new FileInputStream(compressedVideo);
				MultipartFile multipartFile = new MockMultipartFile("file", compressedVideo.getName(), "video/mp4", input);
				log.info("视频大小超过5MB限制,压缩后的视频文件,名称: {},大小:{}", compressedVideo.getAbsolutePath(), compressedVideo.length());
				ossResp = iOssClient.uploadMinioFile(multipartFile, fileName);
				// 上传完成后删除临时文件
				if (compressedVideo != null) {
					final Path outputPath = compressedVideo.toPath();
					try {
						boolean result = Files.deleteIfExists(outputPath);
						log.info("已删除临时目录:{}下的压缩视频文件, 删除结果:{}", outputPath, result ? "成功!" : "失败!");
					} catch (IOException e) {
						log.warn("删除临时目录下输出的压缩视频文件异常,异常详情: {}", outputPath);
					}
				}
			} else {
				log.info("视频未超过5MB,不压缩视频文件,名称: {}, 大小:{}", params.getFile().getOriginalFilename(), params.getFile().getSize());
				ossResp = iOssClient.uploadMinioFile(params.getFile(), fileName);
			}
			if (null == ossResp || null == ossResp.getData() || StringUtils.isBlank(ossResp.getData().getLink())) {
				throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_VIDEO_ERROR_NOTICE));
			}
			log.info("上传视频成功, 视频文件路径: {}", ossResp.getData().getLink());
			ZeroFile zeroFile = ossResp.getData();
			String type = params.getType();
			String location = params.getLocation();

			// 查询当前步骤缓存数据
			CustomOpenImgEntity customOpenImgEntity = iCustomOpenImgService.selectByUserIdAndType(userId, location,type);
			if (customOpenImgEntity != null) {
				customOpenImgEntity.setUrl(zeroFile.getLink());
				customOpenImgEntity.setUpdateTime(new Date());
				iCustomOpenImgService.updateById(customOpenImgEntity);
			} else {
				customOpenImgEntity = new CustomOpenImgEntity();
				customOpenImgEntity.setUserId(userId);
				customOpenImgEntity.setLocationKey(location);
				customOpenImgEntity.setLocationType(type);
				customOpenImgEntity.setIsError(false);
				customOpenImgEntity.setUrl(zeroFile.getLink());
				customOpenImgEntity.setCreateTime(new Date());
				iCustomOpenImgService.save(customOpenImgEntity);
			}

			OpenVideoVo resVo = new OpenVideoVo();
			resVo.setUrl(zeroFile.getLink());
			return resVo;
		} catch (IOException exception) {
			log.error("保存用户开户视频数据参数异常: params={}, 异常详情:{}", params, exception);
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_VIDEO_ERROR_NOTICE));
		}
	}


	/**
	 * ocr识别
	 *
	 * @param params
	 * @return
	 */
	@Override
	public Object ocrByCardType(OcrReqParams params) {
		// 参数校验
		if (params == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_OCR_PARAM_NULL_NOTICE));
		}
		Integer openType = params.getOpenType();
		Integer cardType = params.getCardType();
		OcrReqParams.IdCardOptions idCardOptions = params.getIdCardOptions();
		OcrReqParams.PassportOptions passportOptions = params.getPassportOptions();

		if (!OcrEnum.CardType.isContainCertType(cardType)) {
			throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_OCR_CERTIFICATE_TYPE_ERROR_NOTICE), cardType));
		}
		if (!CustomOpenAccountEnum.OpenType.isContainCertType(openType)) {
			throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_OCR_OPEN_TYPE_ERROR_NOTICE), openType));
		}
		Object ocrResponse = null;
		Long custId = AuthUtil.getTenantCustId();
		// 身份证识别
		if (cardType.equals(OcrEnum.CardType.IdCardOCR.getCode())) {
			// 身份证识别参数校验
			if (idCardOptions == null) {
				throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_OCR_IDCARD_PARAM_NULL_NOTICE));
			}
			OcrEnum.IdCardSide idCardSide = OcrEnum.IdCardSide.getData(idCardOptions.getCardSide());
			if (idCardSide == null) {
				throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_OCR_IDCARD_RECOGNITION_NULL_NOTICE));
			}
			OcrEnum.ImageLocation locationInfo = OcrEnum.ImageLocation.IdCardFront;
			if (idCardSide.getCode().equals(OcrEnum.IdCardSide.BACK.getCode())) {
				locationInfo = OcrEnum.ImageLocation.IdCardBack;
			}
			String imageUrl = getOcrImageUrl(custId, locationInfo);
			ocrResponse = tencentApiOcrClient.getIdCardOCR(imageUrl, idCardSide.getValue(), idCardOptions.getConfig());
		}
		// 银行卡识别
		else if (cardType.equals(OcrEnum.CardType.BankCardOCR.getCode())) {
			// 银行卡识别参数校验
			OcrEnum.ImageLocation locationInfo = OcrEnum.ImageLocation.BankCard;
			String imageUrl = getOcrImageUrl(custId, locationInfo);
			ocrResponse = tencentApiOcrClient.getBankCardOCR(imageUrl);
		}
		// 港澳通行证识别
		else if (cardType.equals(OcrEnum.CardType.PermitOCR.getCode())) {
			// 港澳通行证识别参数校验
			OcrEnum.ImageLocation locationInfo = OcrEnum.ImageLocation.Permit;
			String imageUrl = getOcrImageUrl(custId, locationInfo);
			ocrResponse = tencentApiOcrClient.getPermitOCR(imageUrl);
		}
		// 护照识别
		else if (cardType.equals(OcrEnum.CardType.PassportOCR.getCode())) {
			// 护照识别参数校验
			if (passportOptions == null) {
				throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_OCR_PASSPORT_PARAM_NULL_NOTICE));
			}
			OcrEnum.PassportType passportType = OcrEnum.PassportType.getData(passportOptions.getPassportType());

			if (passportType == null) {
				throw new ServiceException(String.format(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_OCR_PASSPORT_TYPE_ERROR_NOTICE), passportOptions.getPassportType()));
			}
			OcrEnum.ImageLocation locationInfo = OcrEnum.ImageLocation.Passport;
			String imageUrl = getOcrImageUrl(custId, locationInfo);
			ocrResponse = tencentApiOcrClient.getPassportOCR(imageUrl, passportType.getValue());
		}
		//香港身份证
		else if (cardType.equals(OcrEnum.CardType.HKIdCard.getCode())) {
			OcrEnum.ImageLocation locationInfo = OcrEnum.ImageLocation.HKIdCard;
			String imageUrl = getOcrImageUrl(custId, locationInfo);
			ocrResponse = tencentApiOcrClient.getHkIdCardOCR(imageUrl);
		}
		//港澳台地区及境外护照
		else if (cardType.equals(OcrEnum.CardType.MLIDPassport.getCode())) {
			OcrEnum.ImageLocation locationInfo = OcrEnum.ImageLocation.MLIDPassport;
			String imageUrl = getOcrImageUrl(custId, locationInfo);
			ocrResponse = tencentApiOcrClient.getMLIDPassportOCR(imageUrl);
		}
		//港澳台居住证
		else if (cardType.equals(OcrEnum.CardType.HmtResidentPermit.getCode())) {
			OcrEnum.ImageLocation locationInfo = OcrEnum.ImageLocation.HmtResidentPermit;
			String imageUrl = getOcrImageUrl(custId, locationInfo);
			ocrResponse = tencentApiOcrClient.getHmtResidentPermitOCR(imageUrl);
		}
		if (ocrResponse == null) {
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_OCR_RECOGNITION_FAILED_NOTICE));
		}
		return ocrResponse;
	}

	/**
	 * 根据图片类型获取图片地址
	 *
	 * @param custId
	 * @param locationInfo
	 * @return
	 */
	private String getOcrImageUrl(Long custId, OcrEnum.ImageLocation locationInfo) {
		CustomOpenImgEntity openImgEntity = iCustomOpenImgService.selectByUserId(custId, locationInfo.getLocationType());
		if (openImgEntity == null) {
			log.error("参数错误：userId = {}, LocationType ={} 图片信息不存在!", custId, locationInfo.getLocationType());
			throw new ServiceException(I18nUtil.getMessage(OpenAccountMessageConstant.OPEN_ACCOUNT_SUBMIT_DATA_OCR_IMAGE_NOT_EXIST_NOTICE));
		}
		String imageUrl = openImgEntity.getUrl();
		return imageUrl;
	}
}
