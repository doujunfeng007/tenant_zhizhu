package com.minigod.zero.platform.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.sms.model.SmsData;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.core.tool.utils.RandomType;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.dto.EmailVerifyDTO;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.enums.PushTypeEnum;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @ClassName TenantSmsUtil.java
 * @Description TODO
 * @createTime 2024年10月10日 14:54:00
 */
@Slf4j
@Component
public class TenantPlatformUtil {

	private static String saasTenantId;

	private static String baseurl;

	public static String getValidateParams() {
		return StringUtil.random(6, RandomType.INT);
	}



	@Value("${saas.customer.tenantId:000000}")
	public void setSaasTenantId(String saasTenantId) {
		TenantPlatformUtil.saasTenantId = saasTenantId;
	}

	@Value("${saas.platform.baseurl:}")
	public void setBaseurl(String baseurl) {
		TenantPlatformUtil.baseurl = baseurl;
	}

	public static void main(String[] args) {
//		SmsData smsData = new SmsData();
//		smsData.setTempCode("1003");
//		smsData.setLanguage("");
//		smsData.setPhone("13037164972");
//		smsData.setAreaCode("86");
//		smsData.setTenantId("337080");
//		sendSms(smsData);

		SendNotifyDTO sendNotifyDTO = new SendNotifyDTO();
		Long custId = 10000311L;
		sendNotifyDTO.setLstToUserId(Arrays.asList(custId));
//		sendNotifyDTO.setLang(WebUtil.getLanguage());
		sendNotifyDTO.setDisplayGroup(MsgStaticType.DisplayGroup.SERVICE_MSG);
		sendNotifyDTO.setPushType(PushTypeEnum.STRONG_MSG.getTypeValue());
		sendNotifyDTO.setTemplateCode(PushTemplate.TRADE_PASSWORD_MODIFY.getCode());
		sendNotify(sendNotifyDTO);

	}

	/**
	 * 发送短信
	 *
	 * @param dto
	 * @return
	 */
	public static R sendSms(SmsData dto) {

		String url = "proxy/tenant/sendSms";
		String result = null;
		try {
			dto.setTenantId(saasTenantId);
			Map<String, String> headers = new HashMap<>();
			headers.put(TokenConstant.SOURCE_TYPE, "h5");
			result = HttpUtil.createPost(baseurl + url).addHeaders(headers).body(JsonUtil.toJson(dto)).execute().body();
			log.info("发送短信返回参数,{}", result);
		} catch (Exception e) {
			log.error("发送短信异常", e);
		}
		return JSONUtil.toBean(result, R.class);
	}


	public static R sendEmail(SendEmailDTO dto) {
		String url = "proxy/tenant/sendEmail";
		String result = null;
		try {
			dto.setTenantId(saasTenantId);
			Map<String, String> headers = new HashMap<>();
			headers.put(TokenConstant.SOURCE_TYPE, "h5");
			result = HttpUtil.createPost(baseurl + url).addHeaders(headers).body(JsonUtil.toJson(dto)).execute().body();
			log.info("发送邮件返回参数,{}", result);
		} catch (Exception e) {
			log.error("发送邮件异常", e);
		}
		return JSONUtil.toBean(result, R.class);
	}

	/**
     * 发送push消息
	 * @param sendNotifyDTO
     * @return
	 */
	public static R sendNotify(SendNotifyDTO sendNotifyDTO) {
		String url = "proxy/tenant/send_notify";
		String result = null;
		try {
			sendNotifyDTO.setTenantId(saasTenantId);
			Map<String, String> headers = new HashMap<>();
			headers.put(TokenConstant.SOURCE_TYPE, "h5");
			result = HttpUtil.createPost(baseurl + url).addHeaders(headers).body(JsonUtil.toJson(sendNotifyDTO)).execute().body();
			log.info("发送push消息返回参数,{}", result);
		} catch (Exception e) {
			log.error("发送push消息异常", e);
		}
		return JSONUtil.toBean(result, R.class);
	}

    /**
     * 验证邮箱验证码
	 * @param emailVerifyDTO
     * @return
	 */

	public static boolean validateEmailCode(EmailVerifyDTO emailVerifyDTO) {

		String url = "proxy/tenant/checkEmailCaptcha";
		String resultStr = null;
		try {
			emailVerifyDTO.setTenantId(saasTenantId);
			Map<String, String> headers = new HashMap<>();
			headers.put(TokenConstant.SOURCE_TYPE, "h5");
			resultStr = HttpUtil.createPost(baseurl + url).addHeaders(headers).body(JsonUtil.toJson(emailVerifyDTO)).execute().body();
			log.info("验证邮箱验证码返回参数,{}", resultStr);
		} catch (Exception e) {
			log.error("验证邮箱验证码异常", e);
		}
		R result = JSONUtil.toBean(resultStr, R.class);
		if (result.isSuccess()) {
			Boolean flag = (Boolean) result.getData();
			return flag;
		} else {
			return false;
		}

	}

	/**
     * 验证短信验证码
	 * @param smsData
     * @return
	 */
	public static Boolean validateSmsCode(SmsData smsData) {

		String url = "proxy/tenant/validate_message";
		String resultStr = null;
		try {
			smsData.setTenantId(saasTenantId);
			Map<String, String> headers = new HashMap<>();
			headers.put(TokenConstant.SOURCE_TYPE, "h5");
			resultStr = HttpUtil.createPost(baseurl + url).addHeaders(headers).body(JsonUtil.toJson(smsData)).execute().body();
			log.info("验证短信验证码返回参数,{}", resultStr);
		} catch (Exception e) {
			log.error("验证短信验证码异常", e);
		}
		R result = JSONUtil.toBean(resultStr, R.class);
		if (result.isSuccess()) {
			Boolean flag = (Boolean) result.getData();
			return flag;
		} else {
			return false;
		}
	}
}
