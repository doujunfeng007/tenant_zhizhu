package com.minigod.zero.platform.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.sms.model.SmsData;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.platform.core.MessageSendManager;
import com.minigod.zero.platform.core.SendResult;
import com.minigod.zero.platform.core.sms.SmsMessage;
import com.minigod.zero.platform.enums.*;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.platform.common.MobileMsg;
import com.minigod.zero.platform.dispatcher.MobileMsgDispatcher;
import com.minigod.zero.platform.service.IPlatformSmsService;
import com.minigod.zero.platform.vo.SmsCaptchaVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 消息中心短信服务
 *
 * @author
 */
@Slf4j
@Service
@RefreshScope
public class PlatformSmsServiceImpl implements IPlatformSmsService {

	@Resource
	private MobileMsgDispatcher mobileMsgDispatcher;

	@Resource
	private MessageSendManager messageSendManager;



	@Override
	public R sendSmsToPhone(SmsData smsData) {
		log.info("短信发送请求参数：{}", JSONObject.toJSONString(smsData));
		SmsMessage smsMessage = buildSmsMessage(smsData);
		if (smsData.getVerificationCode() != null && smsData.getVerificationCode()){
			SendResult result = messageSendManager.sendVerificationCode(smsMessage);
			if (result.getStatus() != ResultCode.OK.getCode()){
				throw new ZeroException("验证码发送失败");
			}
			return R.data(result.getSid());
		}else{
			SendResult result = messageSendManager.send(smsMessage);
			return R.data(result);
		}
	}



	@Override
	public R pushMobileMsg(List<MobileMsg> mobileMsgs) {
		R rt = R.success();
		if (mobileMsgs == null || CollectionUtil.isEmpty(mobileMsgs)) {
			rt.setCode(ResultCode.FAILURE.getCode());
			rt.setMsg(ResultCode.FAILURE.getMessage());
			return rt;
		}
		mobileMsgDispatcher.add(mobileMsgs);
		return rt;
	}


	@Override
	public R sendSmsCaptcha(SmsCaptchaVO smsCaptcha) {
		SmsMessage smsMessage = buildSmsMessage(smsCaptcha);
		SendResult sendResult = messageSendManager.sendVerificationCode(smsMessage);
		log.info("短信验证码发送结果：{}",JSONObject.toJSONString(sendResult));
		if (sendResult.getStatus() != ResultCode.OK.getCode()){
			throw new ZeroException("验证码发送失败");
		}
		return R.data(Kv.create().set("captchaKey", sendResult.getSid()));
	}


	@Override
	public R validateMessage(SmsData smsData) {
		SmsMessage smsMessage = new SmsMessage();
		smsMessage.setCaptchaCode(smsData.getCaptchaCode());
		smsMessage.setCaptchaKey(smsData.getCaptchaKey());
		SendResult sendResult = messageSendManager.checkVerificationCode(smsMessage);
		Boolean flag = sendResult.getStatus() == ResultCode.OK.getCode();
		return R.data(flag,sendResult.getErrorMsg());
	}

	private SmsMessage buildSmsMessage(SmsCaptchaVO smsCaptcha){
		SmsMessage smsMessage = new SmsMessage();
		smsMessage.setAreaCode(smsCaptcha.getArea());
		smsMessage.setPhoneNumber(smsCaptcha.getPhone());
		if (StringUtil.isNotBlank(smsCaptcha.getLang())){
			smsMessage.setLanguage(smsCaptcha.getLang());
		}else{
			smsMessage.setLanguage(WebUtil.getLanguage());
		}
		smsMessage.setTenantId(WebUtil.getHeader(TokenConstant.TENANT_KEY));
		smsMessage.setTemplateCode(SmsTemplate.LOGIN_VERIFICATION_CODE.getCode());
		return smsMessage;
	}

	private SmsMessage buildSmsMessage(SmsData smsData){
		SmsMessage smsMessage = new SmsMessage();
		smsMessage.setAreaCode(smsData.getAreaCode());
		smsMessage.setPhoneNumber(smsData.getPhone());
		smsMessage.setCaptchaCode(smsData.getCaptchaCode());
		smsMessage.setCaptchaKey(smsData.getCaptchaKey());
		smsMessage.setTemplateCode(Integer.valueOf(smsData.getTempCode()));
		if (StringUtil.isNotBlank(smsData.getTempParam())){
			smsMessage.setTemplateParam(Arrays.asList(smsData.getTempParam().split(",")));
		}
		if (StringUtil.isNotBlank(smsData.getTenantId())){
			smsMessage.setTenantId(smsData.getTenantId());
		}else{
			smsMessage.setTenantId(WebUtil.getHeader(TokenConstant.TENANT_KEY));
		}
		if (StringUtil.isNotBlank(smsData.getLanguage())){
			smsMessage.setLanguage(smsData.getLanguage());
		}else{
			smsMessage.setLanguage(WebUtil.getLanguage());
		}
		return smsMessage;
	}

}
