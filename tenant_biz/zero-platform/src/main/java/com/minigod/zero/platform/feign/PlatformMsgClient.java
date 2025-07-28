package com.minigod.zero.platform.feign;

import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.mq.pulsar.producer.ProducerCollector;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.dto.SendSmsDTO;
import com.minigod.zero.platform.service.IHkSmtpSendService;
import com.minigod.zero.platform.service.IPlatformEmailService;
import com.minigod.zero.platform.service.IPlatformSmsService;
import com.minigod.zero.platform.vo.SmsCaptchaVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Base64;


/**
 * @author Zhe.Xiao
 */
@NonDS
@Slf4j
@RestController
public class PlatformMsgClient implements IPlatformMsgClient {

	@Value("${spring.profiles.active:unknown}")
	private String activeProfile;
	@Resource
	private IPlatformSmsService platformSmsService;
	@Resource
	private IPlatformEmailService platformEmailService;
	@Resource
	private IHkSmtpSendService hkSmtpSendService;
	@Resource
	private ProducerCollector producerCollector;

	private final String PROFILE_PRD = "prd";

	@Override
	public R sendSms(SendSmsDTO dto) {
		if (StringUtil.isBlank(dto.getPhone()) && CollectionUtil.isEmpty(dto.getPhones())) {
			return R.fail("手机号不能为空！");
		}
		if (StringUtil.isBlank(dto.getMessage()) && dto.getTemplateCode() <= 0) {
			return R.fail("短信内容和模板不能都为空！");
		}

		try {
			producerCollector.getProducer(MqTopics.MOBILE_MSG + "_" + activeProfile).send(dto);
		} catch (PulsarClientException e) {
			log.error("sendSms发送MQ消息失败：{}", e.getMessage(), e);
			return R.fail(ResultCode.INTERNAL_ERROR, "发送短信消息失败");
		}
		return R.success();
	}

	@Override
	public R sendEmail(SendEmailDTO dto) {
		if (CollectionUtil.isEmpty(dto.getAccepts())) {
			return R.fail("收件人不能为空！");
		}
		if (StringUtil.isBlank(dto.getContent()) && dto.getCode() <= 0) {
			return R.fail("邮件内容和模板不能都为空！");
		}

		try {
			producerCollector.getProducer(MqTopics.EMAIL_MSG + "_" + activeProfile).send(dto);
		} catch (PulsarClientException e) {
			log.error("发送邮件消息失败：{}", e.getMessage(), e);
			return R.fail(ResultCode.INTERNAL_ERROR, "发送邮件消息失败");
		}

		return R.success();
	}

	/**
	 * 邮件通知方式
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public R sendEmailBase64(SendEmailDTO dto) {
		if (CollectionUtil.isEmpty(dto.getAccepts())) {
			return R.fail("收件人不能为空！");
		}
		if (StringUtil.isBlank(dto.getContent()) && dto.getCode() <= 0) {
			return R.fail("邮件内容和模板不能都为空！");
		}

		try {
			// Base64 解码
			String decodedContent = new String(Base64.getDecoder().decode(dto.getContent()));
			dto.setContent(decodedContent);
			producerCollector.getProducer(MqTopics.EMAIL_MSG + "_" + activeProfile).send(dto);
		} catch (PulsarClientException e) {
			log.error("Base64-发送邮件消息失败：{}", e.getMessage(), e);
			return R.fail(ResultCode.INTERNAL_ERROR, "发送邮件消息失败");
		}

		return R.success();
	}

	@Override
	public R sendNotify(SendNotifyDTO dto) {
		if (0 >= dto.getDisplayGroup() || (null == dto.getTemplateCode() && StringUtil.isBlank(dto.getMsgContent()))
			|| (CollectionUtil.isEmpty(dto.getLstToUserId()) && CollectionUtil.isEmpty(dto.getDeviceInfoList()))) {
			return R.fail("缺少必填参数信息");
		}
		try {
			producerCollector.getProducer(MqTopics.NOTIFY_MSG + "_" + activeProfile).send(dto);
		} catch (PulsarClientException e) {
			log.error("sendNotify发送MQ消息失败：{}", e.getMessage(), e);
			return R.fail(ResultCode.INTERNAL_ERROR, "发送推送消息失败");
		}
		return R.success();
	}

	@Override
	public R<Kv> sendCaptcha(String area, String phone) {
		if (StringUtil.isBlank(area) || StringUtil.isBlank(phone)) {
			return R.fail("手机号和区号都不能为空");
		}
		SmsCaptchaVO smsCaptcha = new SmsCaptchaVO();
		smsCaptcha.setArea(area);
		smsCaptcha.setPhone(phone);

		return platformSmsService.sendSmsCaptcha(smsCaptcha);
	}

	@Override
	@GetMapping(SEND_EMAIL_CAPTCHA)
	public R<Kv> sendEmailCaptcha(String email, int code) {
		if (StringUtil.isBlank(email) || code == 0) {
			return R.fail("邮箱和邮件模板不能为空");
		}
		SmsCaptchaVO smsCaptcha = new SmsCaptchaVO();
		smsCaptcha.setEmail(email);
		smsCaptcha.setCode(code);
		smsCaptcha.setLang(WebUtil.getLanguage());
		return platformEmailService.sendEmailCaptcha(smsCaptcha);
	}

	@Override
	@PostMapping(SEND_EMAIL_CAPTCHA_NEW)
	public R<Kv> sendEmailCaptchaNew(@RequestBody SmsCaptchaVO smsCaptchaVO) {
		if (StringUtil.isBlank(smsCaptchaVO.getEmail())) {
			return R.fail("邮箱和邮件模板不能为空");
		}
		return platformEmailService.sendEmailCaptcha(smsCaptchaVO);
	}

	@Override
	@GetMapping(CHECK_EMAIL_CAPTCHA)
	public R checkEmailCaptcha(String captchaKey, String captchaCode, Integer code) {
		return platformEmailService.checkCaptcha(captchaKey, captchaCode, code);
	}




	@Override
	public R statementSendHkSmtp(String type) {
		try {
			if (!activeProfile.equals(PROFILE_PRD)) {
				return R.fail(type + "StatementSendHkSmtp 当前环境不允许发送");
			}
			hkSmtpSendService.statementSendHkSmtp(type);
		} catch (Exception e) {
			log.error("{}StatementSendHkSmtp error:{}", type, e.getMessage(), e);
			return R.fail(e.getMessage());
		}
		return R.success();
	}

}
