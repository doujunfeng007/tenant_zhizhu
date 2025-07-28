package com.minigod.zero.platform.utils;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.platform.vo.SmsCaptchaVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 李长春
 * @email cloud@bdie.com.cn
 * @WeChat 138264386257
 * @date 2023/4/7
 */
@Component
public class CheckCaptchaCache {

	@Resource
	private ZeroRedis zeroRedis;

	// 校验短信验证码
	public boolean checkCaptcha(String phone, String captcha, String captchaKey) {
		captchaKey = AppConstant.SMS_CAPTCHA_PREFIX + captchaKey;
		SmsCaptchaVO rightCaptcha = zeroRedis.get(captchaKey);
		if (rightCaptcha != null && phone.equals(rightCaptcha.getPhone()) && captcha.equals(rightCaptcha.getCaptcha())) {
			zeroRedis.del(captchaKey);// 用过一次后删除
			zeroRedis.del(rightCaptcha.getCaptchaKey());
			return true;
		}
		return false;
	}

	// 校验邮箱验证码
	public boolean checkEmailCaptcha(String email, String captcha, Integer code, String captchaKey) {
		captchaKey = AppConstant.EMAIL_CAPTCHA_PREFIX + code + captchaKey;
		SmsCaptchaVO rightCaptcha = zeroRedis.get(captchaKey);
		if (rightCaptcha != null && email.trim().toLowerCase().equals(rightCaptcha.getEmail().trim().toLowerCase()) && captcha.equals(rightCaptcha.getCaptcha())) {
			zeroRedis.del(captchaKey);// 用过一次后删除
			zeroRedis.del(rightCaptcha.getCaptchaKey());
			return true;
		}
		return false;
	}

}
