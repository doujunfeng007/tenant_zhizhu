package com.minigod.zero.platform.core.sms;


import com.minigod.zero.biz.common.enums.LanguageEnum;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.core.*;
import com.minigod.zero.platform.enums.AreaCodeType;
import com.minigod.zero.platform.enums.SmsChannelType;
import com.minigod.zero.platform.core.ValidateUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/10 17:08
 * @description：
 */
@Component
public class SmsMessageSend extends AbstractMessageSend implements InitializingBean {

	@Value("${message.smsEnable: true}")
	private Boolean enable;

	private String DEFAULT_CHANNEL = String.valueOf(SmsChannelType.Tencent.getCode());

	private static Map<String,SmsChannel> SMS_CHANNEL_MAP = new HashMap<>();

	private ApplicationContext applicationContext;

	public SmsMessageSend(MessageMapperManager contentMapperManger,
						  ApplicationContext applicationContext,SmsMessageProcess smsMessageProcess,
						  ZeroRedis zeroRedis) {
		super(contentMapperManger,smsMessageProcess,zeroRedis);
		this.applicationContext = applicationContext;
	}



	@Override
	protected SendResult doSend(Message message) {
		SmsMessage smsMessage = (SmsMessage) message;

		validateParam(smsMessage);

		SendResult result = new SendResult();
		result.setMsgId(message.getMsgId());
		if (enable){
			SmsChannel channel = SMS_CHANNEL_MAP.get(DEFAULT_CHANNEL);
			result = channel.send(smsMessage);
		}else{
			result.setStatus(ResultCode.OK.getCode());
			result.setErrorMsg("配置不发送");
		}
		return result;
	}

	@Override
	protected String rateLimiterAccount(Message message) {
		SmsMessage smsMessage = (SmsMessage) message;
		return smsMessage.getAreaCode() + "-" + smsMessage.getPhoneNumber();
	}

	/**
	 * 初始化发送渠道
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String,SmsChannel> handlerMap = applicationContext.getBeansOfType(SmsChannel.class);
		if (handlerMap != null && handlerMap.size() > 0){
			for (Map.Entry entry: handlerMap.entrySet()) {
				SmsChannel channel = (SmsChannel) entry.getValue();
				SMS_CHANNEL_MAP.put(channel.smsChannelType(),channel);
			}
		}
	}


	@Override
	public boolean supports(Class<?> authentication) {
		return SmsMessage.class.isAssignableFrom(authentication);
	}

	/**
	 * 参数校验
	 * @param smsMessage
	 */
	private void validateParam(SmsMessage smsMessage){
		if (StringUtil.isBlank(smsMessage.getPhoneNumber())){
			throw new ZeroException("发送失败，手机号不能为空");
		}
		if (StringUtil.isBlank(smsMessage.getAreaCode())){
			throw new ZeroException("发送失败，手机区号不能为空");
		}
		boolean flag = ValidateUtil.validatePhone(smsMessage.getAreaCode(), smsMessage.getPhoneNumber());
		if (!flag) {
			throw new ZeroException("发送失败，手机号格式不正确");
		}
	}
}
