package com.minigod.zero.platform.core;

import com.minigod.zero.core.tool.exception.ZeroException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/12 15:25
 * @description：
 */
@Component
public class MessageSendManager implements InitializingBean {

	private List<MessageSend> sendHandlers = new ArrayList<>();

	private ApplicationContext applicationContext;
	public MessageSendManager(ApplicationContext applicationContext){
		this.applicationContext = applicationContext;
	}

	/**
	 * 普通消息
	 * @param message
	 * @return
	 */
	public SendResult send(Message message) {
		return getSendHandler(message).send(message);
	}
	/**
	 * 验证码消息
	 * @param message
	 * @return
	 */
	public SendResult sendVerificationCode(Message message){
		return getSendHandler(message).sendVerificationCode(message);
	}
	/**
	 * 校验验证码
	 * @param message
	 * @return
	 */
	public SendResult checkVerificationCode(Message message){
		return getSendHandler(message).checkVerificationCode(message);
	}


	private MessageSend getSendHandler(Message message){
		Class<? extends Message> messageClass = message.getClass();
		for (MessageSend sendHandler : sendHandlers){
			if (sendHandler.supports(messageClass)){
				return sendHandler;
			}
		}
		throw new ZeroException("未找到 sendHandler");
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, MessageSend> handlerMap = applicationContext.getBeansOfType(MessageSend.class);
		if (handlerMap != null && handlerMap.size() > 0){
			for (Map.Entry entry: handlerMap.entrySet()) {
				MessageSend sendHandler = (MessageSend) entry.getValue();
				sendHandlers.add(sendHandler);
			}
		}
	}
}
