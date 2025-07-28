package com.minigod.zero.platform.service;

import com.minigod.zero.core.sms.model.SmsData;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.common.MobileMsg;
import com.minigod.zero.platform.dto.SendSmsDTO;
import com.minigod.zero.platform.vo.SmsCaptchaVO;

import java.util.List;

/**
 * 消息中心短信服务
 *
 * @author minigod
 */
public interface IPlatformSmsService {

	/**
	 * 发送短信接口
	 * @param sendSmsDto 短信参数说明
	 * @return
	 */
	R sendSmsToPhone(SmsData smsData);

    R pushMobileMsg(List<MobileMsg> mobileMsgs);

	// 发送短信验证码
	R sendSmsCaptcha(SmsCaptchaVO smsCaptcha);

	R validateMessage(SmsData smsData);

}
