package com.minigod.zero.platform.feign;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.dto.SendSmsDTO;
import com.minigod.zero.platform.vo.SmsCaptchaVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Zhe.Xiao
 */
@FeignClient(
	value = AppConstant.APPLICATION_PLATFORM_NAME
)
public interface IPlatformMsgClient {

	String SEND_SMS = AppConstant.FEIGN_API_PREFIX + "/send_sms";
	String SEND_EMAIL = AppConstant.FEIGN_API_PREFIX + "/send_email";
	String SEND_EMAIL_BASE64 = AppConstant.FEIGN_API_PREFIX + "/send_email_base64";
	String SEND_NOTIFY = AppConstant.FEIGN_API_PREFIX + "/send_notify";
	String SEND_CAPTCHA = AppConstant.FEIGN_API_PREFIX + "/send_captcha";
	String SEND_EMAIL_CAPTCHA = AppConstant.FEIGN_API_PREFIX + "/send_email_captcha";
	String CHECK_EMAIL_CAPTCHA = AppConstant.FEIGN_API_PREFIX + "/check_email_captcha";
	String STATEMENT_SEND_HKSMTP = AppConstant.FEIGN_API_PREFIX + "/statement_send_hksmtp";
	String SEND_EMAIL_CAPTCHA_NEW = AppConstant.FEIGN_API_PREFIX + "/send_email_captcha_new";

	/**
	 * 短信通知方式（手机号需带上区号)）
	 *
	 * @param dto
	 * @return
	 */
	@PostMapping(SEND_SMS)
	R sendSms(@RequestBody SendSmsDTO dto);


	/**
	 * 邮件通知方式
	 *
	 * @param dto
	 * @return
	 */
	@PostMapping(value = SEND_EMAIL, consumes = MediaType.APPLICATION_JSON_VALUE)
	R sendEmail(@RequestBody SendEmailDTO dto);

	/**
	 * 邮件通知方式
	 *
	 * @param dto
	 * @return
	 */
	@PostMapping(value = SEND_EMAIL_BASE64, consumes = MediaType.APPLICATION_JSON_VALUE)
	R sendEmailBase64(@RequestBody SendEmailDTO dto);

	/**
	 * 推送通知
	 *
	 * @param dto
	 * @return
	 */
	@PostMapping(SEND_NOTIFY)
	R sendNotify(@RequestBody SendNotifyDTO dto);

	/**
	 * 发送短信验证码
	 *
	 * @param area
	 * @param phone
	 * @return
	 */
	@GetMapping(SEND_CAPTCHA)
	R<Kv> sendCaptcha(@RequestParam("area") String area, @RequestParam("phone") String phone);

	/**
	 * 发送邮箱验证
	 *
	 * @param email
	 * @param code
	 * @return
	 */
	@GetMapping(SEND_EMAIL_CAPTCHA)
	R<Kv> sendEmailCaptcha(@RequestParam("email") String email,
						   @RequestParam("code") int code);

	/**
	 * 发送邮箱验证码，可以传入验证码
	 * @param smsCaptchaVO
	 * @return
	 */
	@PostMapping(SEND_EMAIL_CAPTCHA_NEW)
	R<Kv> sendEmailCaptchaNew(@RequestBody SmsCaptchaVO smsCaptchaVO);

	/**
	 * @param captchaKey  验证码key
	 * @param captchaCode 验证码
	 * @param code        模板
	 * @return
	 */
	@GetMapping(CHECK_EMAIL_CAPTCHA)
	R checkEmailCaptcha(@RequestParam("captchaKey") String captchaKey,
						@RequestParam("captchaCode") String captchaCode,
						@RequestParam("code") Integer code);

	/**
	 * 结单发送失败或退信邮件发送至香港SMTP服务器
	 *
	 * @return
	 */
	@GetMapping(STATEMENT_SEND_HKSMTP)
	R statementSendHkSmtp(@RequestParam("type") String type);




}
