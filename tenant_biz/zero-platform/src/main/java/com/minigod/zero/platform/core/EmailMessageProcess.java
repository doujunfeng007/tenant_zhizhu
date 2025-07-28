package com.minigod.zero.platform.core;

import cn.hutool.core.bean.BeanUtil;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.core.email.EmailMessage;
import com.minigod.zero.platform.entity.PlatformEmailContentEntity;
import com.minigod.zero.platform.enums.InformEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/19 9:51
 * @description：短信记录处理
 */
@Component
public class EmailMessageProcess implements MessageProcess {

	@Autowired
	private MessageMapperManager messageMapperManager;

	@Override
	public void saveMessageRecord(Message message) {
		EmailMessage emailMessage = (EmailMessage) message;
		PlatformEmailContentEntity emailContent = new PlatformEmailContentEntity();

		// Set common properties\
		emailContent.setSendsmtpStatus(0);
		emailContent.setCreateTime(DateUtil.now());
		emailContent.setUpdateTime(DateUtil.now());
		emailContent.setMsgId(emailMessage.getMsgId());
		emailContent.setUserId(emailMessage.getUserId());
		emailContent.setSendId(emailMessage.getSendId());
		emailContent.setSender(emailMessage.getSender());
		emailContent.setTenantId(emailMessage.getTenantId());
		emailContent.setFromName(emailMessage.getFromName());
		emailContent.setTempCode(emailMessage.getTemplateCode());
		emailContent.setCarbonCopy(StringUtil.join(emailMessage.getCarbonCopy(), ","));
		emailContent.setSendType(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode());
		emailContent.setSendStatus(InformEnum.SendStatusEnum.NO_SEND.getTypeCode());
		emailContent.setBlindCarbonCopy(StringUtil.join(emailMessage.getBlindCarbonCopy(), ","));

		emailContent.setTitle(emailMessage.getTitle());
		emailContent.setContent(emailMessage.getContent());

		// Set attachment status
		if (CollectionUtil.isNotEmpty(emailMessage.getAttachmentUrls())) {
			emailContent.setHasAttach(1);
			emailContent.setAttachUris(StringUtil.join(emailMessage.getAttachmentUrls(), ";"));
		}

		// Handle multiple addresses
		for (String addr : emailMessage.getAccepts()) {
			if (addr != null && !addr.isEmpty()) {
				emailContent.setAddress(addr);
				PlatformEmailContentEntity platformEmailContentEntity = new PlatformEmailContentEntity();
				BeanUtil.copyProperties(emailContent, platformEmailContentEntity);
				messageMapperManager.emailContent().insert(platformEmailContentEntity);
			}
		}
	}

	@Override
	public void updateMessageRecord(SendResult sendResult, Message message) {
		PlatformEmailContentEntity emailContent = new PlatformEmailContentEntity();
		emailContent.setMsgId(sendResult.getMsgId());
		emailContent.setChannel(sendResult.getChannel());
		//成功（消息投递成功）
		if (ResultCode.OK.getCode() == sendResult.getStatus()) {
			emailContent.setEmailId(sendResult.getSid());
			emailContent.setDescription(sendResult.getErrorMsg());
			emailContent.setSendStatus(InformEnum.SendStatusEnum.SEND_ING.getTypeCode());
		}
		//失败
		else {
			emailContent.setErrorMsg(sendResult.getErrorMsg());
			emailContent.setSendStatus(InformEnum.SendStatusEnum.FAIL_SEND.getTypeCode());
		}
		messageMapperManager.emailContent().updateByMsgId(emailContent);
	}
}
