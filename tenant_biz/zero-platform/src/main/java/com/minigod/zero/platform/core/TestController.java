package com.minigod.zero.platform.core;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.core.email.EmailMessage;
import com.minigod.zero.platform.core.push.PushMessage;
import com.minigod.zero.platform.core.sms.SmsMessage;
import com.minigod.zero.platform.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/12 14:06
 * @description：
 */
@Slf4j
@RestController
@RequestMapping("/message")
public class TestController {

	@Autowired
	private MessageSendManager messageSendManager;

	@PostMapping("/test")
	public R send(){
		//短信验证码
		SendResult sendResult = null;
		try {
			SmsMessage smsMessage = new SmsMessage();
			smsMessage.setAreaCode("86");
			smsMessage.setTenantId("000000");
			smsMessage.setPhoneNumber("18210890192");
			smsMessage.setTemplateCode(SmsTemplate.LOGIN_VERIFICATION_CODE.getCode());
			sendResult = messageSendManager.sendVerificationCode(smsMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("短信验证码发送结果：{}", JSONObject.toJSONString(sendResult));

		try {
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setAccepts(Arrays.asList("929169159@qq.com"));
			emailMessage.setTemplateCode(EmailTemplate.INTEGRATED_MANAGEMENT_BACKEND.getCode());
			emailMessage.setTenantId("000000");
			sendResult = messageSendManager.sendVerificationCode(emailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("邮件验证码发送结果：{}", JSONObject.toJSONString(sendResult));

		try {
//			PushMessage pushMessage = new PushMessage();
//			pushMessage.setTenantId("000000");
//			pushMessage.setDisplayGroup(12007);
//			pushMessage.setPushType(PushTypeEnum.STRONG_MSG.getTypeValue());
//			pushMessage.setTemplateCode(PushTemplate.LOGIN_PASSWORD_MODIFY.getCode());
//			pushMessage.setMsgGroup(InformEnum.MsgGroupEnum.PERSON.getTypeCode());
//			//pushMessage.setRegistrationIds(Arrays.asList("1a0018970b97c36855c"));
//			pushMessage.setUserIds(Arrays.asList(10000199L));
//			sendResult = messageSendManager.send(pushMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("推送发送结果：{}", JSONObject.toJSONString(sendResult));

		return R.data(sendResult);
	}


	@PostMapping("/verification")
	public R verification(String captchaKey,String captchaCode ){
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setCaptchaKey(captchaKey);
		emailMessage.setCaptchaCode(captchaCode);
		emailMessage.setTemplateCode(EmailTemplate.INTEGRATED_MANAGEMENT_BACKEND.getCode());
		emailMessage.setTenantId("000000");
		SendResult sendResult = messageSendManager.checkVerificationCode(emailMessage);
		return R.data(sendResult);
	}


}
