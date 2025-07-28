package com.minigod.zero.platform.core.sms.tencent;

import cn.jpush.api.push.model.SMS;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.platform.core.Message;
import com.minigod.zero.platform.core.MessageMapperManager;
import com.minigod.zero.platform.core.MessageType;
import com.minigod.zero.platform.core.SendResult;
import com.minigod.zero.platform.core.sms.SmsChannel;
import com.minigod.zero.platform.core.sms.SmsMessage;
import com.minigod.zero.platform.entity.PlatformMessageProperties;
import com.minigod.zero.platform.enums.SmsChannelType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/10 17:00
 * @description：
 */
@Slf4j
@Component
public class TencentCloudSmsSend implements SmsChannel {

	private static volatile SmsMultiSender smsSender;

	private static volatile String sender;

	@Autowired
	private MessageMapperManager platformContentMapperManger;

	@Override
	public SendResult send(Message message) {
		SmsMessage smsMessage = (SmsMessage) message;
		log.info("短信发送参数：{}",JSONObject.toJSONString(smsMessage));
		// 使用 Optional 处理模板参数
		String[] params = CollectionUtil.isNotEmpty(message.getTemplateParam())
			? message.getTemplateParam().toArray(new String[0])
			: new String[0];

		String[] phones = new String[]{smsMessage.getPhoneNumber()};

		SmsMultiSenderResult senderResult = null;

		if (smsSender == null){
			getInstance(smsMessage.getTenantId());
		}

		try {
			senderResult =smsSender.sendWithParam(
				smsMessage.getAreaCode(),
				phones,
				Func.toInt(smsMessage.getTemplateCode()),
				params,
				sender, "", ""
			);
		} catch (Exception e) {
			log.error("短信发送失败：{}", e.getMessage());
			throw new ZeroException("调用第三方发送失败！", e); // 抛出异常时附带原始异常
		}

		// 检查发送结果
		if (senderResult == null) {
			throw new ZeroException("调用第三方发送失败！");
		}

		JSONObject result = JSONObject.parseObject(senderResult.toString());
		log.info("短信发送返回结果:{}",result.toJSONString());
		SendResult sendResult = new SendResult();
		sendResult.setMsgId(message.getMsgId());
		sendResult.setChannel(SmsChannelType.Tencent.getCode());
		sendResult.setMessageType(MessageType.SMS);
		// 设置发送结果
		if (result.getInteger("result") == ResultCode.OK.getCode()) {
			sendResult.setStatus(ResultCode.OK.getCode());
			JSONArray sendDetail = result.getJSONArray("detail");
			if (sendDetail != null){
				JSONObject object = sendDetail.getJSONObject(0);
				if (object != null){
					sendResult.setSid(object.getString("sid"));
				}
			}
		} else {
			sendResult.setStatus(ResultCode.INTERNAL_ERROR.getCode());
			sendResult.setErrorMsg(result.getString("errMsg"));
		}
		return sendResult;
	}

	@Override
	public PlatformMessageProperties messageProperties(String tenantId) {
		return platformContentMapperManger.messageProperties().messageProperties(tenantId,MessageType.SMS.getValue());
	}

	@Override
	public String smsChannelType() {
		return String.valueOf(SmsChannelType.Tencent.getCode());
	}

	public SmsMultiSender getInstance(String tenantId) {
		// 第一次检查实例是否存在
		if (smsSender == null) {
			synchronized (SmsMultiSender.class) {
				// 第二次检查实例是否存在
				if (smsSender == null) {
					PlatformMessageProperties messageProperties = messageProperties(tenantId);
					sender = messageProperties.getSender();
					smsSender = new SmsMultiSender(Integer.valueOf(messageProperties.getAppId()),messageProperties.getAppKey());
				}
			}
		}
		return smsSender;
	}

}
