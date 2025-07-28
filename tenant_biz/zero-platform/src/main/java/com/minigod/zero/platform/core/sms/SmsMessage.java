package com.minigod.zero.platform.core.sms;

import com.minigod.zero.platform.core.VerificationCodeMessage;
import lombok.Data;


/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/10 18:22
 * @description：
 */
@Data
public class SmsMessage extends VerificationCodeMessage {
	private String areaCode;
	private String phoneNumber;

}
