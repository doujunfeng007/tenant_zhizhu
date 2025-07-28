package com.minigod.zero.platform.core;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.biz.common.enums.LanguageEnum;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.RandomType;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.core.email.EmailMessage;
import com.minigod.zero.platform.core.push.PushMessage;
import com.minigod.zero.platform.core.sms.SmsMessage;
import com.minigod.zero.platform.entity.*;
import com.minigod.zero.platform.enums.AreaCodeType;
import com.minigod.zero.platform.utils.PlatformUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/10 16:37
 * @description：
 */
@Slf4j
public abstract class AbstractMessageSend implements MessageSend {
	/**
	 * 验证码是否开启校验 ，true发送，false不发送
	 */
	@Value("${message.enableVerification:true}")
	private Boolean ENABLE_VERIFICATION;

	@Value("${message.tenant: 000000}")
	private String DEFAULT_TENANT_ID ;

	/**
	 * 验证码过期时间，分钟
	 */
	private static Long VERIFICATION_CODE_EXPIRE = 5L;

	private static final long TIME_LIMIT = 30;

	private static String VERIFICATION_CODE_KEY = "verification_code:";

	private static Map<String, String> EN_MAP;

	private static Map<String, String> CN_MAP;

	static {
		// 使用 Map.of() 或 Map.ofEntries() 简化初始化
		EN_MAP = Map.of(
			LanguageEnum.EN.getCode(), LanguageEnum.EN.getValue(),
			LanguageEnum.EN_US.getCode(), LanguageEnum.EN_US.getValue(),
			LanguageEnum.EN_US_LOWER.getCode(), LanguageEnum.EN_US_LOWER.getValue()
		);

		CN_MAP = Map.of(
			LanguageEnum.ZH_CN.getCode(), LanguageEnum.ZH_CN.getValue(),
			LanguageEnum.ZH_CN_LOWER.getCode(), LanguageEnum.ZH_CN_LOWER.getValue(),
			LanguageEnum.ZH_HANS.getCode(), LanguageEnum.ZH_HANS.getValue()
		);
	}

	protected ZeroRedis zeroRedis;

	protected MessageProcess messageProcess;

	protected MessageMapperManager contentMapperManger;



	public AbstractMessageSend(MessageMapperManager contentMapperManger,MessageProcess messageProcess, ZeroRedis zeroRedis) {
		this.zeroRedis = zeroRedis;
		this.messageProcess = messageProcess;
		this.contentMapperManger = contentMapperManger;
	}

	@Override
	public SendResult send(Message message) {

		if (StringUtil.isBlank(message.getTenantId())){
			message.setTenantId(DEFAULT_TENANT_ID);
		}

		setMessageId(message);

		PlatformCommonTemplateEntity template = getTemplate(message);

		processMessageLanguage(message, template);

		saveMessageRecord(message);

		SendResult sendResult = processSend(message);

		updateMessageRecord(sendResult,message);

		return sendResult;
	}

	@Override
	public SendResult sendVerificationCode(Message message) {

		String account = rateLimiterAccount(message);
		if (!rateLimiter(account)){
			throw new ZeroException("验证码获取太频繁");
		}

		VerificationCodeMessage codeMessage = (VerificationCodeMessage) message;
		String verificationCode = StringUtil.isBlank(codeMessage.getCaptchaCode()) ?
			generateCaptchaCode() : codeMessage.getCaptchaCode();
		//验证码参数
		List<String> param = new ArrayList<>();
		param.add(verificationCode);
		param.add(String.valueOf(VERIFICATION_CODE_EXPIRE));
		message.setTemplateParam(param);

		SendResult result = this.send(message);
		if (result.getStatus() != ResultCode.OK.getCode()) {
			throw new ZeroException("验证码发送失败！");
		}
		//设置验证码
		String id = StringUtil.randomUUID();
		this.zeroRedis.setEx(VERIFICATION_CODE_KEY + id, verificationCode, VERIFICATION_CODE_EXPIRE * 60);
		result.setSid(id);

		return result;
	}

	@Override
	public SendResult checkVerificationCode(Message message) {
		VerificationCodeMessage codeMessage = (VerificationCodeMessage) message;
		SendResult sendResult = new SendResult();
		try {
			sendResult.setStatus(ResultCode.OK.getCode());
			sendResult.setErrorMsg(ResultCode.OK.getMessage());

			if (!ENABLE_VERIFICATION){
				return sendResult;
			}
			String key = VERIFICATION_CODE_KEY + codeMessage.getCaptchaKey();
			if (!zeroRedis.exists(key)) {
				throw new ZeroException("验证码不存在或已过期");
			}

			String code = zeroRedis.get(key);
			if (!codeMessage.getCaptchaCode().equals(code)) {
				throw new ZeroException("验证码不正确");
			}

			zeroRedis.del(key);
		} catch (ZeroException e) {

			sendResult.setStatus(ResultCode.INTERNAL_ERROR.getCode());
			sendResult.setErrorMsg(e.getMessage());

		}
		return sendResult;
	}

	/**
	 * 真实发送接口，由子类具体实现
	 * @param message
	 * @return
	 */
	protected abstract SendResult doSend(Message message);

	/**
	 * 验证码发送限流账号
	 * @param message
	 * @return
	 */
	protected abstract String rateLimiterAccount(Message message);
	/**
	 * 获取模版
	 *
	 * @return
	 */
	private PlatformCommonTemplateEntity getTemplate(Message message) {
		Integer templateCode = message.getTemplateCode();
		String tenantId = message.getTenantId();
		if (message.getTemplateCode() != null){
			PlatformCommonTemplateEntity template =
				contentMapperManger.commonTemplate().findTemplateByCodeAndTenantId(templateCode, tenantId);
			if (template == null) {
				throw new ZeroException("发送失败，未找到模板");
			}
			return template;
		}
		if (message.getTemplateCode() == null
			&& !StringUtil.isBlank(message.getTitle()) && !StringUtil.isBlank(message.getContent())){
			//不使用模板发送
			return null;
		}
		throw new ZeroException("发送失败，消息内容不能为空");
	}

	/**
	 * 处理消息内容语言以及参数替换，子类可以覆盖，自定义
	 * 目前短信，推送 参数下标从1开始，邮件从0开始，这里需要区别一下
	 *
	 */
	public void processMessageLanguage(Message message, PlatformCommonTemplateEntity commonTemplate) {
		if (commonTemplate == null){
			return;
		}
		LanguageTemplate languageTemplate = languageTemplate(message, commonTemplate);
		log.info("发送短信使用模板：{}", JSONObject.toJSONString(languageTemplate));
		message.setTitle(languageTemplate.getTitle());
		message.setTemplateCode(languageTemplate.getTemplateCode());
		if (message instanceof EmailMessage || message instanceof PushMessage){
			if (message instanceof PushMessage){
				PushMessage pushMessage = (PushMessage) message;
				pushMessage.setUrl(languageTemplate.getUrl());
			}
			message.setContent(PlatformUtil.getMsgStr(languageTemplate.getContent(), message.getTemplateParam()));
		}
		else{
			message.setContent(replaceContent(languageTemplate.getContent(), message.getTemplateParam()));
		}
		if (CollectionUtil.isNotEmpty(message.getTitleParam())){
			message.setTitle(PlatformUtil.getMsgStr(languageTemplate.getTitle(), message.getTitleParam()));
		}
	}

	/**
	 * 生成messageId,子类可以覆盖，自定义
	 * @param message
	 */
	public void setMessageId(Message message){
		message.setMsgId(generateMsgId());
	}

	/**
	 * 处理发送
	 * @param message
	 * @return
	 */
	private SendResult processSend(Message message){
		SendResult sendResult = new SendResult();
		try {
			sendResult = doSend(message);
		} catch (Exception e) {
			log.error("消息{}发送失败，错误信息：{}", message.getMsgId(), e.getMessage());
			sendResult.setErrorMsg(e.getMessage());
			sendResult.setStatus(ResultCode.INTERNAL_ERROR.getCode());
		}
		return sendResult;
	}
	/**
	 * 保存消息记录
	 * @param message
	 */
	private void saveMessageRecord(Message message){
		messageProcess.saveMessageRecord(message);
	}

	/**
	 * 修改消息记录
	 * @param sendResult
	 * @param message
	 */
	private void updateMessageRecord(SendResult sendResult,Message message){
		messageProcess.updateMessageRecord(sendResult,message);
	}

	/**
	 * 获取消息语种，子类可以根据需求覆盖
	 *
	 * @param message
	 * @return
	 */
	public String getLanguage(Message message) {
		if (StringUtil.isBlank(message.getLanguage())){
			return LanguageEnum.ZH_HANT.getCode();
		}
		return message.getLanguage();
	}

	/**
	 * 根据语种获取对应的模板内容
	 *
	 * @return LanguageTemplate
	 */
	private LanguageTemplate languageTemplate(Message message, PlatformCommonTemplateEntity commonTemplate) {
		String language = null;
		if (message instanceof SmsMessage){
			SmsMessage smsMessage = (SmsMessage) message;
			String areaCode = smsMessage.getAreaCode();

			if (areaCode.equals(AreaCodeType.CHN.getCode())){
				language = LanguageEnum.ZH_CN.getCode();
			}else if (areaCode.equals(AreaCodeType.CHN_HK.getCode())){
				language = LanguageEnum.ZH_HK.getCode();
			}else{
				language = LanguageEnum.EN_US.getCode();
			}
		}else{
			language = getLanguage(message);
		}

		if (EN_MAP.containsKey(language)) {
			String code = commonTemplate.getTempCodeEn();
			if (StringUtil.isBlank(code)) {
				throw new ZeroException("未配置英文模板");
			}
			return new LanguageTemplate(commonTemplate.getTitleEn(),
				Integer.valueOf(code),
				commonTemplate.getContentEn(),commonTemplate.getUrl());
		}
		if (CN_MAP.containsKey(language)) {
			String code = commonTemplate.getTempCodeHans();
			if (StringUtil.isBlank(code)) {
				throw new ZeroException("未配置简体中文模板");
			}
			return new LanguageTemplate(commonTemplate.getTitle(),
				Integer.valueOf(code),
				commonTemplate.getContent(),commonTemplate.getUrl());
		} else {
			String code = commonTemplate.getTempCodeHant();
			if (StringUtil.isBlank(code)) {
				throw new ZeroException("未配置繁体中文模板");
			}
			return new LanguageTemplate(commonTemplate.getTitleHant(),
				Integer.valueOf(code),
				commonTemplate.getContentHant(),commonTemplate.getUrl());
		}
	}

	/**
	 * 生成messageId
	 *
	 * @return
	 */
	private String generateMsgId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成验证码
	 *
	 * @return
	 */
	private String generateCaptchaCode() {
		return StringUtil.random(6, RandomType.INT);
	}

	/**
	 * 发送验证码频率限制
	 * @param account 手机号/邮箱
	 * @return 发送结果
	 */
	public boolean rateLimiter(String account) {
		String key = VERIFICATION_CODE_KEY + account;

		// 尝试获取当前手机号的发送状态
		Boolean isSent = zeroRedis.getValueOps().setIfAbsent(key, "sent", TIME_LIMIT, TimeUnit.SECONDS);

		if (isSent != null && isSent) {
			return true; // 可以发送
		} else {
			return false; // 不可以发送
		}
	}

	/**
	 * 替换模板里面的参数
	 *
	 * @param content
	 * @param paramStr
	 * @return
	 */
	private String replaceContent(String content, List<String> paramStr) {
		if (CollectionUtil.isNotEmpty(paramStr)) {
			for (int i = 0; i < paramStr.size(); i++) {
				content = content.replace("{" + (i + 1) + "}", paramStr.get(i));
			}
		}
		return content;
	}


}
