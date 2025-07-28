package com.minigod.zero.platform.feign;

import com.minigod.zero.core.sms.TencentSmsTemplate;
import com.minigod.zero.core.sms.model.SmsData;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.RandomType;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.dto.SendSmsDTO;
import com.minigod.zero.platform.service.IPlatformSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/13 15:45
 * @description：
 */
@RestController
public class SmsClient implements ISmsClient{
	@Autowired
	private TencentSmsTemplate tencentSmsTemplate;

	@Autowired
	private IPlatformSmsService platformSmsService;

	/**
	 * 获取短信验证码参数
	 *
	 * @return 验证码参数
	 */
	private static String getValidateParams() {
		return StringUtil.random(6, RandomType.INT);
	}

	/**
	 * 发送短信
	 * @return 发送结果
	 */
	@Override
	@PostMapping("/client/platform/sendSms")
	public  R sendMessage(@RequestBody SmsData sendSmsDTO) {
		return platformSmsService.sendSmsToPhone(sendSmsDTO);
	}

	/**
	 * 发送验证
	 * @return 发送结果
	 */
	@Override
	@PostMapping("/client/platform/send_validate")
	public R sendValidate(@RequestBody SmsData sendSmsDTO) {
		R result =  platformSmsService.sendSmsToPhone(sendSmsDTO);
		return result;
	}

	/**
	 * 校验短信
	 * @return 发送结果
	 */
	@Override
	@PostMapping("/client/platform/validate_essage")
	public R validateMessage(@RequestBody SmsData sendSmsDTO) {
		return R.data(platformSmsService.validateMessage(sendSmsDTO).getData());
	}
}
