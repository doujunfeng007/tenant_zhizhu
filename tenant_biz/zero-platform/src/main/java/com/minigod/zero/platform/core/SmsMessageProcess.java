package com.minigod.zero.platform.core;

import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.platform.core.sms.SmsMessage;
import com.minigod.zero.platform.entity.PlatformMobileContentEntity;
import com.minigod.zero.platform.enums.InformEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/19 9:51
 * @description：短信记录处理
 */
@Component
public class SmsMessageProcess implements MessageProcess{

	@Autowired
	private MessageMapperManager messageMapperManager;

	@Override
	public void saveMessageRecord(Message message) {
		SmsMessage smsMessage = (SmsMessage) message;
		PlatformMobileContentEntity mobileContent = new PlatformMobileContentEntity();
		mobileContent.setTitle(smsMessage.getTitle());
		mobileContent.setMsgId(smsMessage.getMsgId());
		mobileContent.setTenantId(smsMessage.getTenantId());
		mobileContent.setSendType(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode());
		mobileContent.setTempCode(Integer.valueOf(smsMessage.getTemplateCode()));
		mobileContent.setSendStatus(InformEnum.SendStatusEnum.NO_SEND.getTypeCode());
		mobileContent.setCellPhone(smsMessage.getAreaCode() + "-" + smsMessage.getPhoneNumber());
		mobileContent.setContent(smsMessage.getContent());
		messageMapperManager.mobileContent().insert(mobileContent);
	}

	@Override
	public void updateMessageRecord(SendResult sendResult, Message message) {
		PlatformMobileContentEntity mobileContent = new PlatformMobileContentEntity();
		mobileContent.setMsgId(sendResult.getMsgId());
		mobileContent.setChannel(sendResult.getChannel());
		//成功（消息投递成功）
		if (ResultCode.OK.getCode() == sendResult.getStatus()) {
			mobileContent.setSmsId(sendResult.getSid());
			mobileContent.setDiscription(sendResult.getErrorMsg());
			mobileContent.setSendStatus(InformEnum.SendStatusEnum.SEND_ING.getTypeCode());
		}
		//失败
		else {
			mobileContent.setErrorMsg(sendResult.getErrorMsg());
			mobileContent.setSendStatus(InformEnum.SendStatusEnum.FAIL_SEND.getTypeCode());
		}
		messageMapperManager.mobileContent().updateByMsgId(mobileContent);
	}
}
