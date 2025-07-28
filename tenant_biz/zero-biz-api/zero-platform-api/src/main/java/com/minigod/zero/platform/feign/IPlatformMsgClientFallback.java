package com.minigod.zero.platform.feign;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.dto.SendSmsDTO;
import com.minigod.zero.platform.vo.SmsCaptchaVO;
import org.springframework.stereotype.Component;

/**
 * 流程远程调用失败处理类
 *
 * @author Chill
 */
@Component
public class IPlatformMsgClientFallback implements IPlatformMsgClient {

	@Override
	public R sendSms(SendSmsDTO dto) {
		return R.fail("短信发送远程调用失败");
	}

	@Override
	public R sendEmail(SendEmailDTO dto) {
		return R.fail("邮件发送远程调用失败");
	}

	@Override
	public R sendEmailBase64(SendEmailDTO dto) {
		return R.fail("Base64-邮件发送远程调用失败");
	}

	@Override
	public R sendNotify(SendNotifyDTO dto) {
		return R.fail("推送通知远程调用失败");
	}

	@Override
	public R sendCaptcha(String area, String phone) {
		return R.fail("发送短信验证码调用失败");
	}

	@Override
	public R sendEmailCaptcha(String email, int code) {
		return R.fail("发送邮箱验证码调用失败");
	}

	@Override
	public R<Kv> sendEmailCaptchaNew(SmsCaptchaVO smsCaptchaVO) {
		return R.fail("发送邮箱验证码调用失败");
	}

	@Override
	public R<Kv> checkEmailCaptcha(String captchaKey, String captchaCode, Integer code) {
		return R.fail("验证校验失败");
	}

	@Override
	public R statementSendHkSmtp(String type) {
		return R.fail("结单邮件发送香港SMTP远程调用失败");
	}

}
