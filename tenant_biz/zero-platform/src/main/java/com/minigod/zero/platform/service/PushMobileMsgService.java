package com.minigod.zero.platform.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.sms.AliSmsTemplate;
import com.minigod.zero.core.sms.ChuanglanSmsTemplate;
import com.minigod.zero.core.sms.TencentSmsTemplate;
import com.minigod.zero.core.sms.model.SmsData;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.common.EmailMsg;
import com.minigod.zero.platform.common.MobileMsg;
import com.minigod.zero.platform.constants.Constants;
import com.minigod.zero.platform.entity.PlatformMobileContentEntity;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.SmsChannelType;
import com.minigod.zero.platform.mapper.PlatformMobileContentMapper;
import com.minigod.zero.resource.utils.MsgSmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
@RefreshScope
public class PushMobileMsgService {

	@Value("${platform.sms.isMsgSending:true}")
	private Boolean isMsgSending;
	@Value("${sms.warn.sender.email:unknown}")
	private String sender;
	@Value("${sms.warn.accept.email:unknown}")
	private String accept;
	@Value("${spring.profiles.active:unknown}")
	private String activeProfile;

	@Resource
	private PlatformMobileContentMapper platformMobileContentMapper;
	@Resource
	private IPlatformEmailService platformEmailService;
	@Resource
	private AliSmsTemplate aliSmsTemplate;
	@Resource
	private ChuanglanSmsTemplate chuanglanSmsTemplate;
	@Resource
	private TencentSmsTemplate tencentSmsTemplate;
	@Resource
	private ZeroRedis zeroRedis;

	public void pushMobileMsg(final MobileMsg mobileMsg) {
		log.info("pushMobileMsg thread:{}, bean:{}", Thread.currentThread().getId(), JSONUtil.toJson(mobileMsg));

		if (!isMsgSending) {
			log.info("**********pushMobileMsg平台消息发送通道已经关闭!**********");
			return;
		}
		String mobile = mobileMsg.getMobile();
		String message = mobileMsg.getMessage();
		Integer channel = mobileMsg.getChannel();
		String tempCode = mobileMsg.getTempCode();
		String tempParam = mobileMsg.getTempParam();
		Boolean verificationCode = mobileMsg.getVerificationCode();
		// 校验相关信息是否为空
		if (StringUtil.isAnyBlank(mobile, message) || channel == null) {
			log.error("手机号码、短信内容、短信通道 不能为空, msgId:{}, mobile:{}, message:{}, channel:{}", mobileMsg.getMsgId(), mobile, message, channel);
			updateMsg(R.fail("手机号码、短信内容、短信通道 不能为空"), mobileMsg.getMsgId(), channel);
			return;
		}

		String mobileStr = null;
		String areaCode = null;
		if (mobile.contains("-")) {
			String phoneNos[] = mobile.trim().replace("+", "").split("-");
			areaCode = phoneNos[0];
			mobileStr = phoneNos[1];
		} else {
			mobileStr = mobile.trim();
		}

		boolean isCnMobileNo = MsgSmsUtil.isCnMobileNo(mobile.trim());
		// 校验请求的信息
		boolean isValid = MsgSmsUtil.checkInfo(mobileStr, message);
		// 校验不通过
		if (!isValid) {
			log.error("无效的手机号码或短信信息内容过长, msgId:{}, mobile:{}, message:{}, channel:{}", mobileMsg.getMsgId(), mobile, message, channel);
			updateMsg(R.fail("无效的手机号码或短信信息内容过长"), mobileMsg.getMsgId(), channel);
			return;
		}
		if (!isCnMobileNo && StringUtil.isBlank(areaCode)) {
			log.error("无效的国际手机区号, msgId:{}, mobile:{}, message:{}, channel:{}", mobileMsg.getMsgId(), mobile, message, channel);
			updateMsg(R.fail("无效的国际手机区号"), mobileMsg.getMsgId(), channel);
			return;
		}

		R resp = sendByChannel(channel, message, areaCode, mobileStr, isCnMobileNo, tempCode, tempParam, mobileMsg);

		log.info("pushMobileMsg resp:{}, msgId:{}, channel:{}", JSON.toJSONString(resp), mobileMsg.getMsgId(), channel);
		if (resp == null || !resp.isSuccess()) {
			this.updateMsg(resp, mobileMsg.getMsgId(), channel);
			//失败自动切换通道重试
			Integer nextChannel = zeroRedis.hGet(Constants.SMS_CHANNEL_CACHE_KEY, mobile);
			log.info("短信channel:{}发送失败,自动切换下一个channel:{}, msgId:{}, mobile:{}, message:{}", channel, nextChannel, mobileMsg.getMsgId(), mobile, message);
			resp = sendByChannel(Integer.valueOf(nextChannel), message, areaCode, mobileStr, isCnMobileNo, tempCode, tempParam, mobileMsg);
			log.info("pushMobileMsg retryResp:{}, msgId:{}, channel:{}", JSON.toJSONString(resp), mobileMsg.getMsgId(), nextChannel);
			zeroRedis.hSet(Constants.SMS_CHANNEL_CACHE_KEY, mobile, SmsChannelType.next(nextChannel));
			channel = Integer.valueOf(nextChannel);
		}
		this.updateMsg(resp, mobileMsg.getMsgId(), channel);
	}

	private R sendByChannel(Integer channel, String message, String areaCode, String mobileStr, boolean isCnMobileNo, String tempCode, String tempParam, MobileMsg mobileMsg) {
		R resp = null;
		if (channel == SmsChannelType.ALIYUN.getCode() && StringUtil.isBlank(tempCode)) {
			log.error("发送阿里云短信，阿里云模板代码不能为空, msgId:{}, mobile:{}, message:{}, channel:{}", mobileMsg.getMsgId(), mobileMsg.getMobile(), message, channel);
			resp = R.fail("发送阿里云短信，阿里云模板代码不能为空");
			sendErrorEmail(mobileMsg.getMobile(), resp);
			return resp;
		}
		if (channel == SmsChannelType.Tencent.getCode()){
			resp = tencentSmsTemplate.send(new SmsData()
					.setMessage(message)
					.setAreaCode(areaCode)
					.setPhone(mobileStr)
					.setIsIntl(!isCnMobileNo)
					.setExpire(mobileMsg.getExpire())
					.setVerificationCode(mobileMsg.getVerificationCode()));
		}
		if (channel == SmsChannelType.CL.getCode()) {
			resp = chuanglanSmsTemplate.send(
				new SmsData()
					.setMessage(message)
					.setAreaCode(areaCode)
					.setPhone(mobileStr)
					.setIsIntl(!isCnMobileNo)
			);
		} else if (channel == SmsChannelType.ALIYUN.getCode()) {
			resp = aliSmsTemplate.send(
				new SmsData()
					.setMessage(message)
					.setAreaCode(areaCode)
					.setPhone(mobileStr)
					.setIsIntl(!isCnMobileNo)
					.setTempCode(tempCode)
					.setTempParam(tempParam)
			);
		} else {
			log.error("无效的短信channel:{}, msgId:{}, mobile:{}, message:{}", channel, mobileMsg.getMsgId(), mobileMsg.getMobile(), message);
			resp = R.fail("无效的短信channel:" + channel);
		}

		if (resp == null || !resp.isSuccess()) {
			sendErrorEmail(mobileMsg.getMobile(), resp);
		}

		return resp;
	}

	private void updateMsg(R resp, Long msgId, Integer channel) {
		PlatformMobileContentEntity smsSend = new PlatformMobileContentEntity();
		smsSend.setId(msgId);
		Date now = new Date();
		if (resp != null && resp.isSuccess()) {
			smsSend.setSendStatus(InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());
			smsSend.setSendTime(new Date());
		} else {
			smsSend.setSendStatus(InformEnum.SendStatusEnum.FAIL_SEND.getTypeCode());
			smsSend.setFailCnt(smsSend.getFailCnt() + 1);
			log.error("send mobileMsg fail:{}, msgId:{}, channel:{}", resp != null ? resp.getMsg() : "", msgId, channel);
		}
		smsSend.setReceiveTime(now);
		smsSend.setUpdateTime(now);
		smsSend.setChannel(channel);
		platformMobileContentMapper.updateById(smsSend);
	}

	private void sendErrorEmail(String mobile, R resp) {
		try {
			String errorStr = new StringBuilder()
				.append("【").append(activeProfile).append("|手机号").append(mobile).append("短信发送错误提醒】").append(resp != null ? JSON.toJSONString(resp) : "")
				.toString();
			log.error(errorStr);
			EmailMsg bean = new EmailMsg();
			bean.setMsgId(IdUtil.getSnowflakeNextIdStr());
			bean.setSender(sender);
			bean.setAccept(accept);
			bean.setTitle("短信发送错误提醒");
			bean.setContent(errorStr);
			platformEmailService.sendEmail(bean);
		} catch (Exception e) {
			log.error("sendErrorEmail fail", e);
		}
	}
}
