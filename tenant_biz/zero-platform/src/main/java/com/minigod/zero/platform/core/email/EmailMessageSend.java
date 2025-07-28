package com.minigod.zero.platform.core.email;

import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.platform.core.*;
import com.minigod.zero.platform.utils.PlatformUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/10 18:35
 * @description：
 */
@Component
public class EmailMessageSend extends AbstractMessageSend implements InitializingBean{

	@Value("${message.emailEnable: true}")
	private Boolean emailEnable;

	private String DEFAULT_CHANNEL = String.valueOf(com.minigod.zero.platform.enums.EmailChannel.SEND_CLOUD.getCode());

	private static Map<String,EmailChannel> EMAIL_CHANNEL_MAP = new HashMap<>();

	private ApplicationContext applicationContext;

	public EmailMessageSend(MessageMapperManager contentMapperManger,
							ApplicationContext applicationContext, EmailMessageProcess emailMessageProcess,
							ZeroRedis zeroRedis) {
		super(contentMapperManger,emailMessageProcess,zeroRedis);
		this.applicationContext = applicationContext;
	}

	@Override
	protected SendResult doSend(Message message) {
		EmailMessage emailMessage = (EmailMessage) message;

		SendResult sendResult = new SendResult();
		sendResult.setMsgId(message.getMsgId());
		if (emailEnable){
			sendResult = EMAIL_CHANNEL_MAP.get(DEFAULT_CHANNEL).send(emailMessage);
		}
		else{
			sendResult.setStatus(ResultCode.OK.getCode());
			sendResult.setErrorMsg("配置不发送");
		}
		return sendResult;
	}

	@Override
	protected String rateLimiterAccount(Message message) {
		EmailMessage emailMessage = (EmailMessage) message;
		return  StringUtils.join(emailMessage.getAccepts(),",");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String,EmailChannel> handlerMap = applicationContext.getBeansOfType(EmailChannel.class);
		if (handlerMap != null && handlerMap.size() > 0){
			for (Map.Entry entry: handlerMap.entrySet()) {
				EmailChannel channel = (EmailChannel) entry.getValue();
				EMAIL_CHANNEL_MAP.put(channel.emailChannelType(),channel);
			}
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return EmailMessage.class.isAssignableFrom(authentication);
	}
}
