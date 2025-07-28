package com.minigod.zero.platform.core.email.sendcloud;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.core.Message;
import com.minigod.zero.platform.core.MessageMapperManager;
import com.minigod.zero.platform.core.MessageType;
import com.minigod.zero.platform.core.SendResult;
import com.minigod.zero.platform.core.email.EmailChannel;
import com.minigod.zero.platform.core.email.EmailMessage;
import com.minigod.zero.platform.entity.PlatformMessageProperties;
import com.minigod.zero.platform.utils.SmtpUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/11 17:33
 * @description：
 */
@Slf4j
@Component
public class SendCloudEmailSend implements EmailChannel {

	private static volatile EmailProperties emailProperties;

	@Autowired
	private MessageMapperManager platformContentMapperManger;

	@Override
	public SendResult send(Message message) {

		EmailMessage emailMessage = (EmailMessage) message;

		if (emailProperties == null){
			getInstance(message.getTenantId());
		}

		String contentType = StringUtil.join(emailMessage.getCarbonCopy(),",");
		String blindCarbonCopy = StringUtil.join(emailMessage.getBlindCarbonCopy(),",");
		String attachmentUrls  = StringUtil.join(emailMessage.getAttachmentUrls(),",");

		R<String> resp = SmtpUtil.sendSmtpEmail(emailMessage.getMsgId(),
			emailProperties.getSmtpHost(),
			emailProperties.getSmtpPort(),
			emailProperties.getUserName(),
			emailProperties.getPassword(),
			emailProperties.getSender(),
			emailProperties.getSender(),
			emailMessage.getAccepts(),
			emailMessage.getTitle(),
			getContentNoMask(emailMessage),
			emailProperties.getContentType(),
			contentType,
			blindCarbonCopy,
			attachmentUrls);
		log.info("发送邮件返回结果：{}", JSONObject.toJSONString(resp));
		SendResult result = new SendResult();
		result.setMsgId(emailMessage.getMsgId());
		result.setMessageType(MessageType.EMAIL);
		result.setChannel(com.minigod.zero.platform.enums.EmailChannel.SEND_CLOUD.getCode());
		if (resp.getCode() != ResultCode.SUCCESS.getCode()) {
			result.setStatus(ResultCode.INTERNAL_ERROR.getCode());
			result.setErrorMsg(resp.getMsg());
		} else {
			result.setStatus(ResultCode.OK.getCode());
			result.setSid(resp.getData());
		}
		return result;
	}


	@Override
	public PlatformMessageProperties messageProperties(String tenantId) {
		return platformContentMapperManger.messageProperties().messageProperties(tenantId,MessageType.EMAIL.getValue());
	}

	@Override
	public String emailChannelType() {
		return String.valueOf(com.minigod.zero.platform.enums.EmailChannel.SEND_CLOUD.getCode());
	}

	public  EmailProperties getInstance(String tenantId) {
		// 第一次检查实例是否存在
		if (emailProperties == null) {
			synchronized (EmailProperties.class) {
				// 第二次检查实例是否存在
				if (emailProperties == null) {
					PlatformMessageProperties messageProperties = messageProperties(tenantId);

					emailProperties = EmailProperties.builder()
						.sender(messageProperties.getSender())
						.smtpHost(messageProperties.getHost())
						.smtpPort(messageProperties.getPort())
						.userName(messageProperties.getAppId())
						.password(messageProperties.getAppKey())
						.contentType("text/html;charset=utf-8")
						.build();
				}
			}
		}
		return emailProperties;
	}

	@Data
	@Builder
	static class EmailProperties{
		private String smtpHost;
		private String smtpPort;
		private String userName;
		private String password;
		private String sender;
		private String contentType;
	}

	private String getContentNoMask(EmailMessage message){
		return message.getContent().replace("<data_mask>", "").replace("</data_mask>", "");
	}
}
