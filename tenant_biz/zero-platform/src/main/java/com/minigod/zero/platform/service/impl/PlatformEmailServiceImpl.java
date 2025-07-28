package com.minigod.zero.platform.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.enums.LanguageEnum;
import com.minigod.zero.biz.common.factorys.ClaimThreadFactory;
import com.minigod.zero.biz.common.utils.GlobalExecutorService;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.mq.pulsar.producer.ProducerCollector;
import com.minigod.zero.platform.core.MessageSendManager;
import com.minigod.zero.platform.core.SendResult;
import com.minigod.zero.platform.core.email.EmailMessage;
import com.minigod.zero.platform.dto.EmailHookMessageDTO;
import com.minigod.zero.platform.dto.PlatformEmailStatementDTO;
import com.minigod.zero.platform.dto.PlatformOpenCommonDTO;
import com.minigod.zero.platform.dto.SmsHookMessageDTO;
import com.minigod.zero.platform.entity.PlatformMobileContentEntity;
import com.minigod.zero.platform.enums.EmailEventStatus;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.mapper.PlatformCommonTemplateMapper;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.platform.common.EmailMsg;
import com.minigod.zero.platform.dispatcher.EmailMsgDispatcher;
import com.minigod.zero.platform.entity.PlatformCommonTemplateEntity;
import com.minigod.zero.platform.entity.PlatformEmailContentEntity;
import com.minigod.zero.platform.mapper.PlatformEmailContentMapper;
import com.minigod.zero.platform.mapper.PlatformMobileContentMapper;
import com.minigod.zero.platform.service.IPlatformEmailContentService;
import com.minigod.zero.platform.service.IPlatformEmailService;
import com.minigod.zero.platform.utils.PlatformUtil;
import com.minigod.zero.platform.vo.SmsCaptchaVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 消息中心邮件服务
 *
 * @author minigod
 */
@Slf4j
@Service
@RefreshScope
@RequiredArgsConstructor
public class PlatformEmailServiceImpl implements IPlatformEmailService {

	private final EmailMsgDispatcher emailMsgDispatcher;
	private final IPlatformEmailContentService platformEmailContentService;
	private final PlatformEmailContentMapper platformEmailContentMapper;
	@Resource
	private MessageSendManager messageSendManager;
	@Resource
	private PlatformMobileContentMapper platformMobileContentMapper;

	private final ExecutorService executorService = new ThreadPoolExecutor(5, 10,
		0L, TimeUnit.MILLISECONDS,
		new LinkedBlockingQueue<Runnable>(), new ClaimThreadFactory());

	@Resource
	private ProducerCollector producerCollector;

	@Override
	public R sendEmail(EmailMsg bean) {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setLanguage(bean.getLang());
		emailMessage.setTitle(bean.getTitle());
		emailMessage.setContent(bean.getContent());
		emailMessage.setAccepts(bean.getAccepts());
		emailMessage.setTemplateCode(bean.getCode());
		emailMessage.setTenantId(bean.getTenantId());
		emailMessage.setCarbonCopy(bean.getCarbonCopy());
		emailMessage.setTitleParam(bean.getTitleParam());
		emailMessage.setAttachmentUrls(bean.getAttachmentUrls());
		emailMessage.setBlindCarbonCopy(bean.getBlindCarbonCopy());
		emailMessage.setTemplateParam(bean.getList());
		messageSendManager.send(emailMessage);
		return R.success();
	}

	@Override
	public R pushEmailMsg(EmailMsg emailMsg) {
		R vo = R.success();
		if (emailMsg == null) {
			vo.setCode(ResultCode.PARAMETER_ERROR.getCode());
			vo.setMsg(ResultCode.PARAMETER_ERROR.getMessage());
			return vo;
		}
		emailMsgDispatcher.add(emailMsg);
		return vo;
	}

	@Override
	public R sendEmailCaptcha(SmsCaptchaVO smsCaptcha) {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setLanguage(smsCaptcha.getLang());
		emailMessage.setCaptchaCode(smsCaptcha.getCaptcha());
		emailMessage.setTemplateCode(smsCaptcha.getCode());
		emailMessage.setAccepts(Arrays.asList(smsCaptcha.getEmail()));
		SendResult sendResult = messageSendManager.sendVerificationCode(emailMessage);
		if (sendResult.getStatus() != ResultCode.OK.getCode()){
			throw new ZeroException("验证码发送失败！");
		}
		return R.data(Kv.create().set("captchaKey", sendResult.getSid()));
	}

	@Override
	public R findEmailMsg(String sendId) {
		try {
			List<PlatformEmailContentEntity> informEmailContents = new LambdaQueryChainWrapper<>(platformEmailContentService.getBaseMapper())
				.eq(PlatformEmailContentEntity::getSendId, sendId)
				.list();
			return R.data(informEmailContents);
		} catch (Exception e) {
			log.error("查询邮件是否发送成功异常", e);
			return R.fail(ResultCode.PARAMETER_ERROR);
		}
	}

	@Override
	public R checkCaptcha(String captchaKey, String captchaCode, Integer code) {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setCaptchaKey(captchaKey);
		emailMessage.setCaptchaCode(captchaCode);
		SendResult sendResult = messageSendManager.checkVerificationCode(emailMessage);
		return R.data(sendResult.getStatus() == ResultCode.OK.getCode());
	}

	@Override
	public R receiveSendCloudCallback(EmailHookMessageDTO emailHookMessageDTO) {
		//接收  request, deliver, unsubscribe, invalid, soft_bounce, route
		EmailEventStatus eventStatus = null;
		if (emailHookMessageDTO.getEvent() != null) {
			try {
				eventStatus = EmailEventStatus.valueOf(emailHookMessageDTO.getEvent().toUpperCase());
				// 如果成功获取到枚举，说明 event 是有效的
				log.info("有效的事件类型: {}", eventStatus.getDescription());
			} catch (IllegalArgumentException e) {
				// 如果抛出异常，说明 event 不是有效的枚举类型
				log.error("无效的事件类型: {}", emailHookMessageDTO.getEvent());
				return R.fail("无效的事件类型: " + emailHookMessageDTO.getEvent());
			}
		} else {
			log.warn("事件类型为空");
			return R.fail("事件类型为空");
		}

		if (eventStatus==EmailEventStatus.REQUEST){
			log.warn("请求成功");
			return R.fail("请求成功");
		}
		EmailEventStatus finalEventStatus = eventStatus;
		executorService.execute(() -> disposeEmail(emailHookMessageDTO, finalEventStatus));

		return R.success();
	}

	@Override
	public R receiveSmsCallback(List<SmsHookMessageDTO>  smsHookMessageDTO) {
		//[{"description":"用户短信送达成功","errmsg":"DELIVRD","mobile":"18569548613","sid":"3363:371824426917334646739214861"}]
		//[{"description":"空号","errmsg":"MK:100F","mobile":"11112222332","sid":"3363:387573885317334647529962233"}]
		PlatformMobileContentEntity platformMobileContentEntity = null;
		for (SmsHookMessageDTO dto : smsHookMessageDTO) {
			log.info("短信发送dto: {}", dto);
			platformMobileContentEntity = platformMobileContentMapper.selectOne(Wrappers.<PlatformMobileContentEntity>lambdaQuery()
				.eq(PlatformMobileContentEntity::getSmsId, dto.getSid())
				.orderByDesc(PlatformMobileContentEntity::getCreateTime)
				.last("limit 1"));
			if (platformMobileContentEntity == null){
				continue;
			}

			if (dto.getErrmsg().equals("DELIVRD")){
				platformMobileContentEntity.setSendStatus(InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());
			}else {
				platformMobileContentEntity.setSendStatus(InformEnum.SendStatusEnum.FAIL_SEND.getTypeCode());
				platformMobileContentEntity.setErrorMsg(dto.getDescription() + ":" + dto.getErrmsg());
			}
			platformMobileContentEntity.setUpdateTime(new Date());
			int i = platformMobileContentMapper.updateById(platformMobileContentEntity);
			if (i!= 1) {
				log.error("更新短信状态失败: {}", platformMobileContentEntity.getId());
			}
		}
		 return R.success();
	}


	private void disposeEmail(EmailHookMessageDTO emailHookMessageDTO,EmailEventStatus eventStatus) {
		log.info("线程池 开始处理邮件事件: emailId:{}, event:{}", emailHookMessageDTO.getEmailId(), emailHookMessageDTO.getEvent());
		String emailId = emailHookMessageDTO.getEmailId();
		if (StringUtils.isEmpty(emailId)){
			log.error("emailId 为空");
			return;
		}
		String email = emailHookMessageDTO.getRecipient();

		int index = emailId.indexOf("inbound");

		if (index != -1) {
			// 提取从开始到 "inbound" 的部分，包括 "inbound"
			 emailId = emailId.substring(0, index + "inbound".length());
			 log.info("emailId: {}", emailId);
		} else {
			log.error("emailId 格式不正确: {}", emailId);
			return;
		}
		// 记录尝试次数   防止回调太快查不到记录
		int attempts = 0;
		PlatformEmailContentEntity platformEmailContentEntity = null;

		while (attempts < 2) {
			platformEmailContentEntity = platformEmailContentMapper.selectOne(Wrappers.<PlatformEmailContentEntity>lambdaQuery()
				.eq(PlatformEmailContentEntity::getEmailId, emailId)
				.eq(PlatformEmailContentEntity::getAddress, email)
				.orderByDesc(PlatformEmailContentEntity::getCreateTime)
				.last("limit 1"));

			if (platformEmailContentEntity != null) {
				// 找到对应的邮件记录，处理逻辑
				break; // 退出循环
			} else {
				log.error("提交银行卡审批申请失败未找到对应的邮件记录: {}", emailId);
				attempts++; // 增加尝试次数
				if (attempts < 2) { // 如果不是最后一次尝试，等待 3 秒
					log.info("等待 3 秒，再次查询");
					try {
						Thread.sleep(3000); // 暂停 3 秒
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt(); // 恢复中断状态
						log.error("线程被中断", e);
					}
				}
			}
		}

// 如果仍然为 null，处理相应逻辑
		if (platformEmailContentEntity == null) {
			log.error("在两次查询后仍未找到对应的邮件记录: {}", emailId);
			return;
		}

		if (eventStatus==EmailEventStatus.DELIVER){
			platformEmailContentEntity.setSendStatus(InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());
		}else {
			platformEmailContentEntity.setSendStatus(InformEnum.SendStatusEnum.FAIL_SEND.getTypeCode());
			Integer subStat = emailHookMessageDTO.getSubStat();
			String subStatDesc = emailHookMessageDTO.getSubStatDesc();
			platformEmailContentEntity.setErrorMsg(eventStatus.getDescription() + ":" + subStatDesc + ":" + subStat);
		}
		platformEmailContentEntity.setUpdateTime(new Date());

		int i = platformEmailContentMapper.updateById(platformEmailContentEntity);
		if (i!= 1) {
			log.error("更新邮件状态失败: {}", emailId);
		}

		//同步邮件状态到结单发送记录表
		if (platformEmailContentEntity.getSendId()!= null) {
			PlatformEmailStatementDTO platformEmailStatementDTO = new PlatformEmailStatementDTO();
			platformEmailStatementDTO.setSendId(platformEmailContentEntity.getSendId());
			platformEmailStatementDTO.setSendStatus(platformEmailContentEntity.getSendStatus());
			platformEmailStatementDTO.setErrorMsg(platformEmailContentEntity.getErrorMsg());
			try {
				producerCollector.getProducer(MqTopics.SENDCLOUD_EMAIL_SYNC_MESSAGE).send(platformEmailStatementDTO);
			} catch (PulsarClientException e) {
				log.error("发送邮件消息失败：{}", e.getMessage(), e);
			}
		}
	}
}
