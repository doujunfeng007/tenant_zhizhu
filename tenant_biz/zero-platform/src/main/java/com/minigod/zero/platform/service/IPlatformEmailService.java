package com.minigod.zero.platform.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.common.EmailMsg;
import com.minigod.zero.platform.dto.EmailHookMessageDTO;
import com.minigod.zero.platform.dto.SmsHookMessageDTO;
import com.minigod.zero.platform.vo.SmsCaptchaVO;

import java.util.List;

/**
 * 消息中心邮件服务
 *
 * @author minigod
 */
public interface IPlatformEmailService {

	R sendEmail(EmailMsg bean);

	R pushEmailMsg(EmailMsg emailMsg);

	R sendEmailCaptcha(SmsCaptchaVO smsCaptcha);

	R findEmailMsg(String sendId);

	R checkCaptcha(String captchaKey,String captchaCode,Integer code);

	R receiveSendCloudCallback(EmailHookMessageDTO emailHookMessageDTO);

	R receiveSmsCallback(List<SmsHookMessageDTO> smsHookMessageDTO);

}
