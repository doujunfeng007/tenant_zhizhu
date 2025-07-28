package com.minigod.zero.platform.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HtmlUtil;
import com.alibaba.fastjson2.JSON;
import com.minigod.zero.biz.common.enums.LanguageEnum;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.biz.common.utils.DateUtil;
import com.minigod.zero.platform.client.ICustomerInfoClient;
import com.minigod.zero.platform.mapper.PlatformCommonTemplateMapper;
import com.minigod.zero.platform.mapper.PlatformInvestMsgMapper;
import com.minigod.zero.platform.service.IPlatformMsgReadRecordService;
import com.minigod.zero.platform.service.IPlatformPushService;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.common.NotifyMsg;
import com.minigod.zero.platform.constants.PushUrlProtocol;
import com.minigod.zero.platform.dispatcher.NotifyMsgDispatcher;
import com.minigod.zero.platform.dto.SaveMessageReqDTO;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.entity.PlatformCommonTemplateEntity;
import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;
import com.minigod.zero.platform.entity.PlatformMsgReadRecordEntity;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.PlatformOsTypeEnum;
import com.minigod.zero.platform.utils.CashUtil;
import com.minigod.zero.platform.utils.PlatformUtil;
import com.minigod.zero.platform.utils.SequenceService;
import com.minigod.zero.platform.vo.CustomerAccountDetailVO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.minigod.zero.platform.constants.Constants.USERID_ALL_USER;
import static com.minigod.zero.platform.constants.PushUrlProtocol.convertH5Url;

@Slf4j
@Service
@RefreshScope
@RequiredArgsConstructor
public class PlatformPushServiceImpl implements IPlatformPushService {

	@Value("${jg.tenantId}")
	private String tenantId;

	private final ZeroRedis zeroRedis;
	private final SequenceService sequenceService;
	private final ICustomerInfoClient customerInfoClient;
	private final NotifyMsgDispatcher notifyMsgDispatcher;
	private final PlatformInvestMsgMapper platformInvestMsgMapper;
	private final PlatformCommonTemplateMapper platformCommonTemplateMapper;
	private final IPlatformMsgReadRecordService platformMsgReadRecordService;

	public static final String VERSION_PREFIX = "unread_msg_";

	private final ExecutorService executorService = new ThreadPoolExecutor(10, 50, 60L, TimeUnit.SECONDS,
		new LinkedBlockingQueue<>());

	@Override
	public R sendSysMsgNew(SendNotifyDTO bean) {
		R<Object> rt = R.success();

		try {
			log.info("sendSysMsg|{}", JSON.toJSONString(bean));
			validateSendNotifyDTO(bean);

			String tenantId = resolveTenantId(bean);
			bean.setTenantId(tenantId);
			prepareSendNotifyDTO(bean);

			PlatformCommonTemplateEntity commonTemplate = getCommonTemplate(bean);
			String message = buildMessage(bean, commonTemplate);
			if (StringUtils.isEmpty(message)) {
				log.error("message为空, tempCode:{}", bean.getTemplateCode());
				return R.fail(ResultCode.PARAM_MISS.getCode(), ResultCode.PARAM_MISS.getMessage());
			}
			bean.setMsgContent(message);
			setNotifyTitle(bean, commonTemplate);
			setNotifyUrl(bean, commonTemplate);

			saveInformMessage(bean);
			return rt;
		} catch (Exception e) {
			log.error("推送消息异常：{}", e.getMessage(), e);
			return R.fail(ResultCode.INTERNAL_ERROR.getCode(), ResultCode.INTERNAL_ERROR.getMessage());
		}
	}

	private void validateSendNotifyDTO(SendNotifyDTO bean) {
		if (CollectionUtil.isEmpty(bean.getLstToUserId())) {
			throw new ZeroException("发送失败，用户id不能为空");
		}
	}

	private String resolveTenantId(SendNotifyDTO bean) {
		Long userId = bean.getLstToUserId().get(0);
		if (StringUtils.isEmpty(bean.getTenantId())) {
			R<CustomerAccountDetailVO> result = customerInfoClient.selectCustomerDetailByCustId(userId);
			if (!result.isSuccess()) {
				throw new ZeroException("查询用户租户id失败!");
			}
			return result.getData().getTenantId();
		}
		return bean.getTenantId();
	}

	private void prepareSendNotifyDTO(SendNotifyDTO bean) {
		bean.setFromUserId(USERID_ALL_USER);
		bean.setMsgGroup(Optional.ofNullable(bean.getMsgGroup()).orElse(InformEnum.MsgGroupEnum.PERSON.getTypeCode()));
		bean.setSendWay(Optional.ofNullable(bean.getSendWay()).orElse(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode()));
		bean.setClientType(Optional.ofNullable(bean.getClientType()).orElse(PlatformOsTypeEnum.OS_ALL.getTypeValue()));
		bean.setSendTime(Optional.ofNullable(bean.getSendTime()).orElse(new Date()));

		if (StringUtil.isNotBlank(bean.getSendTimeStr())) {
			bean.setSendTime(DateUtil.parseDate(bean.getSendTimeStr(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		}
	}

	private PlatformCommonTemplateEntity getCommonTemplate(SendNotifyDTO bean) {
		if (bean.getTemplateCode() != null) {
			return platformCommonTemplateMapper.findTemplateByCodeAndTenantId(bean.getTemplateCode(), bean.getTenantId());
		}
		return null;
	}

	private String buildMessage(SendNotifyDTO bean, PlatformCommonTemplateEntity commonTemplate) {
		String lang = bean.getLang();
		String tempContent = getTemplateContent(commonTemplate, lang);
		List<String> params = bean.getParams();
		return CollectionUtil.isNotEmpty(params) ? PlatformUtil.getMsgStr(tempContent, params) : tempContent;
	}

	private String getTemplateContent(PlatformCommonTemplateEntity commonTemplate, String lang) {
		if (commonTemplate == null) return null;

		if (LanguageEnum.ZH_HK.getCode().equals(lang) || LanguageEnum.ZH_TW.getCode().equals(lang)) {
			return commonTemplate.getContentHant();
		} else if (LanguageEnum.EN_US.getCode().equals(lang) || LanguageEnum.EN.getCode().equals(lang)) {
			return commonTemplate.getContentEn();
		} else {
			return commonTemplate.getContent();
		}
	}

	private void setNotifyTitle(SendNotifyDTO bean, PlatformCommonTemplateEntity commonTemplate) {
		if (StringUtils.isEmpty(bean.getTitle())) {
			bean.setTitle(Optional.ofNullable(commonTemplate)
				.map(PlatformCommonTemplateEntity::getTitle)
				.orElse("消息通知"));
		}
	}

	private void setNotifyUrl(SendNotifyDTO bean, PlatformCommonTemplateEntity commonTemplate) {
		String url = bean.getUrl();
		if (StringUtils.isBlank(url) && commonTemplate != null) {
			url = commonTemplate.getUrl();
		}
		if (StringUtils.isNotBlank(url)) {
			url = HtmlUtil.unescape(url);
			boolean isAppUrl = PushUrlProtocol.isAppUrl(url);
			if (isAppUrl) {
				if (CollectionUtil.isNotEmpty(bean.getUrlParams())) {
					url = PlatformUtil.getMsgStr(url, bean.getUrlParams());
				}
			} else if (url.startsWith(PushUrlProtocol.H5_URL_PREFIX_HTTP) || url.startsWith(PushUrlProtocol.H5_URL_PREFIX_HTTPS)) {
				url = convertH5Url(url);
			}
		}
		bean.setUrl(url);
	}

	@Override
	public void saveInformMessage(SendNotifyDTO req) {
		if (StringUtils.isEmpty(req.getMsgContent())) {
			log.error("message为空, 不可推送");
			return;
		}
		List<Long> userIds = req.getLstToUserId();
		SaveMessageReqDTO sendMsgReq = new SaveMessageReqDTO();
		BeanUtils.copyProperties(req, sendMsgReq);

		NotifyMsg nmsg = new NotifyMsg();
		nmsg.setMsgId(IdUtil.getSnowflakeNextIdStr());
		nmsg.setTitle(req.getTitle());
		nmsg.setDevType(req.getClientType());
		nmsg.setMsgGroup(req.getMsgGroup());
		nmsg.setUserIds(userIds);
		nmsg.setMsg(req.getMsgContent());
		nmsg.setUrl(req.getUrl());
		nmsg.setDisplayGroup(req.getDisplayGroup());
		nmsg.setDeviceList(req.getDeviceInfoList());
		nmsg.setPushType(req.getPushType());
		sendMsgReq.setNotifyMsg(nmsg);
		saveMessageExt(sendMsgReq);
	}

	@Override
	public void saveMessageExt(final SaveMessageReqDTO req) {
		if (CollectionUtil.isEmpty(req.getLstToUserId())) {
			return;
		}

		if (req.isAutoPreventResent()) {
			handleAutoPreventResent(req);
		}

		final long lupdateVersion = sequenceService.nextId(VERSION_PREFIX + req.getDisplayGroup());
		final List<Long> lstAllUserId = req.getLstToUserId();

		executorService.execute(() -> {
			long begin = System.currentTimeMillis();
			log.info("后台消息发送开始,发送内容" + req.getMsgContent() + "，线程号：" + Thread.currentThread().getId());
			try {
				saveMessageSplitExt(req, lupdateVersion, lstAllUserId);
			} catch (Throwable e) {
				log.error("发送给客户的信息执行失败,若有需要请手工补发通知！,总体请求参数：" + JSON.toJSONString(req), e);
			}
			log.info("后台消息发送结束，发送内容:" + req.getMsgContent() + "，线程号：" + Thread.currentThread().getId() + "耗时(毫秒)："
				+ (System.currentTimeMillis() - begin));
		});
	}

	private void handleAutoPreventResent(SaveMessageReqDTO req) {
		int reSentPreventSeconds = req.getReSentPreventSeconds();
		if (reSentPreventSeconds <= 0) {
			throw new RuntimeException("防重发的秒数不能为空，且必须大于0");
		}

		req.setReSentPreventSeconds(1);
		String key = CashUtil.MD5.getObjectMD5(req);
		req.setReSentPreventSeconds(reSentPreventSeconds);

		synchronized (this) {
			PreventMessageResent prevent = zeroRedis.findObject(PreventMessageResent.class, key);
			if (prevent == null) {
				zeroRedis.saveUpdate(new PreventMessageResent(), key, reSentPreventSeconds);
			}
		}
	}

	public void saveMessageSplitExt(SaveMessageReqDTO req, long lupdateVersion, List<Long> lstSplitToUserId) {
		List<PlatformInvestMsgEntity> lstInvestMsgs = new ArrayList<>();
		req.setSendWay(Optional.ofNullable(req.getSendWay()).orElse(0));

		if (InformEnum.MsgGroupEnum.ALL.getTypeCode().equals(req.getMsgGroup())) {
			saveMessageForAllUsers(req, lstInvestMsgs, lupdateVersion);
		} else {
			saveMessageForIndividualUsers(req, lstSplitToUserId, lstInvestMsgs, lupdateVersion);
		}
	}

	private void saveMessageForAllUsers(SaveMessageReqDTO req, List<PlatformInvestMsgEntity> lstInvestMsgs, long lupdateVersion) {
		PlatformInvestMsgEntity investMsg = new PlatformInvestMsgEntity();
		copyMsg(req, investMsg);
		investMsg.setIsDeleted(0);
		investMsg.setStatus(1);
		investMsg.setUserId(USERID_ALL_USER);
		investMsg.setFromUser(USERID_ALL_USER);
		investMsg.setTenantId(req.getTenantId());

		if (req.getSendWay() == InformEnum.SendWayTimeEnum.TIMING.getTypeCode()) {
			investMsg.setUpdVersion(0L);
			investMsg.setSendStatus(InformEnum.SendStatusEnum.NO_SEND.getTypeCode());
		} else {
			investMsg.setUpdVersion(lupdateVersion);
			investMsg.setSendStatus(InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());
			updateUnReadNum(Collections.singletonList(USERID_ALL_USER), req.getDisplayGroup(), req.getClientType());
			pushNotifyMsg(req.getNotifyMsg());
		}
		lstInvestMsgs.add(investMsg);
		platformInvestMsgMapper.saveInvestMsgs(lstInvestMsgs);
	}

	private void saveMessageForIndividualUsers(SaveMessageReqDTO req, List<Long> lstSplitToUserId, List<PlatformInvestMsgEntity> lstInvestMsgs, long lupdateVersion) {
		for (Long userId : lstSplitToUserId) {
			PlatformInvestMsgEntity investMsg = new PlatformInvestMsgEntity();
			copyMsg(req, investMsg);
			investMsg.setUserId(userId);
			investMsg.setTenantId(req.getTenantId());
			investMsg.setUpdVersion(req.getSendWay() == InformEnum.SendWayTimeEnum.TIMING.getTypeCode() ? 0L : lupdateVersion);
			investMsg.setSendStatus(req.getSendWay() == InformEnum.SendWayTimeEnum.TIMING.getTypeCode() ? InformEnum.SendStatusEnum.NO_SEND.getTypeCode() : InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());
			investMsg.setIsDeleted(0);
			investMsg.setStatus(1);
			lstInvestMsgs.add(investMsg);
		}
		platformInvestMsgMapper.saveInvestMsgs(lstInvestMsgs);
		updateUnReadNum(lstSplitToUserId, req.getDisplayGroup(), req.getClientType());
		pushNotifyMsg(req.getNotifyMsg());
	}

	@Override
	public R pushNotifyMsg(NotifyMsg notifyMsg) {
		if (notifyMsg == null || notifyMsg.getDisplayGroup() == null) {
			return R.fail(ResultCode.PARAMETER_ERROR.getCode(), ResultCode.PARAMETER_ERROR.getMessage());
		}
		notifyMsgDispatcher.add(notifyMsg);
		return R.success();
	}

	@Override
	public void updateUnReadNum(List<Long> lstUserId, int messageGroup, Integer clientType) {
		if (CollectionUtil.isEmpty(lstUserId)) {
			return;
		}

		try {
			Map<Long, List<PlatformMsgReadRecordEntity>> mapMsgReadRecord = platformMsgReadRecordService.findMsgReadRecord(lstUserId, messageGroup);
			List<PlatformMsgReadRecordEntity> addMsgReadRecords = new ArrayList<>();

			// 遍历用户 ID 列表并更新阅读记录
			lstUserId.forEach(userId -> {
				List<PlatformMsgReadRecordEntity> msgReadRecords = mapMsgReadRecord.get(userId);
				updateReadRecords(userId, messageGroup, clientType, msgReadRecords, addMsgReadRecords);
			});

			// 批量保存新增的阅读记录
			if (!addMsgReadRecords.isEmpty()) {
				platformMsgReadRecordService.saveMsgReadRecords(addMsgReadRecords);
			}
		} catch (Exception e) {
			log.error("updateUnReadNum fail ", e);
		}
	}

	private void updateReadRecords(Long userId, int messageGroup, Integer clientType, List<PlatformMsgReadRecordEntity> msgReadRecords, List<PlatformMsgReadRecordEntity> addReadRecords) {
		Date now = new Date();

		if (CollectionUtil.isEmpty(msgReadRecords)) {
			// 创建新的阅读记录并添加到列表
			addReadRecords.add(createNewReadRecord(userId, messageGroup, now, clientType));
		} else {
			// 更新现有的阅读记录
			msgReadRecords.forEach(msgReadRecord -> updateExistingReadRecord(userId, messageGroup, clientType, msgReadRecord, now));
		}
	}

	private void updateExistingReadRecord(Long userId, int messageGroup, Integer clientType, PlatformMsgReadRecordEntity msgReadRecord, Date now) {
		Integer unreadNum = platformMsgReadRecordService.findUnReadMsgNum(userId, messageGroup, msgReadRecord.getReadVersion(), clientType);
		msgReadRecord.setUnreadNum(unreadNum);
		msgReadRecord.setUpdateTime(now);

		try {
			platformMsgReadRecordService.updateMsgReadRecord(msgReadRecord);
		} catch (Exception e) {
			log.error("***********updateUnReadNum-userId:" + userId + ",messageGroup:" + messageGroup + ";准备重试****", e);
			//retryUpdateReadRecord(userId, messageGroup, clientType, msgReadRecord, now);
		}
	}

	private PlatformMsgReadRecordEntity createNewReadRecord(Long userId, int messageGroup, Date now, Integer clientType) {
		PlatformMsgReadRecordEntity msgReadRecord = new PlatformMsgReadRecordEntity();
		msgReadRecord.setCreateTime(now);
		msgReadRecord.setStatus(1);
		msgReadRecord.setMsgCode(messageGroup);
		msgReadRecord.setReadVersion(0L);
		msgReadRecord.setUnreadNum(1);
		msgReadRecord.setUpdateTime(now);
		msgReadRecord.setUserId(userId);
		msgReadRecord.setLockVersion(1);
		msgReadRecord.setIsDeleted(0);
		msgReadRecord.setClientType(clientType);
		return msgReadRecord;
	}

	private void retryUpdateReadRecord(Long userId, int messageGroup, Integer clientType, PlatformMsgReadRecordEntity msgReadRecord, Date now) {
		PlatformMsgReadRecordEntity msgreadrecorderr = platformMsgReadRecordService.findMsgReadRecord(userId, messageGroup, clientType);
		Integer intnumerr = platformMsgReadRecordService.findUnReadMsgNum(userId, messageGroup, msgreadrecorderr.getReadVersion(), clientType);
		msgreadrecorderr.setUnreadNum(intnumerr);
		msgreadrecorderr.setUpdateTime(now);
		platformMsgReadRecordService.updateMsgReadRecord(msgreadrecorderr);
	}

	private void copyMsg(SaveMessageReqDTO req, PlatformInvestMsgEntity investMsg) {
		investMsg.setSendId(req.getSendId());
		investMsg.setFromUser(req.getFromUserId());
		investMsg.setDisplayGroup(req.getDisplayGroup());
		investMsg.setTitle(req.getTitle());
		investMsg.setMsgContent(getMaxLen(req.getMsgContent(), 1024));
		investMsg.setStatus(1);
		Date nowDate = new Date();
		investMsg.setCreateTime(nowDate);
		investMsg.setUpdateTime(nowDate);
		investMsg.setSendTime(req.getSendTime());
		investMsg.setSendWay(req.getSendWay());
		investMsg.setMsgGroup(req.getMsgGroup());
		if (req.getClientType() != null) {
			investMsg.setClientType(req.getClientType());
		}
		investMsg.setUrl(req.getUrl());
	}

	private String getMaxLen(String msgContent, int len) {
		return (msgContent == null) ? null : (msgContent.length() > len) ? msgContent.substring(0, len) : msgContent;
	}

	@Data
	public static class PreventMessageResent implements Serializable {
		private static final long serialVersionUID = 1L;
		private boolean tag = true;
	}
}
