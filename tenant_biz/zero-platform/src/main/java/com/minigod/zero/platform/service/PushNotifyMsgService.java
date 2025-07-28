package com.minigod.zero.platform.service;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.platform.common.NotifyMsg;
import com.minigod.zero.platform.constants.PushUrlProtocol;
import com.minigod.zero.platform.entity.PlatformPushMsgHistoryEntity;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.PushTypeEnum;
import com.minigod.zero.platform.mapper.PlatformPushMsgHistoryMapper;
import com.minigod.zero.platform.utils.PlatformUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
@RefreshScope
public class PushNotifyMsgService {

	@Value("${jg.enable}")
	private Boolean isMsgSending;

	@Resource
	private PushNotifyMsgAsyncNew pushNotifyMsgAsync;
	@Resource
	private PlatformPushMsgHistoryMapper platformPushMsgHistoryMapper;

	public void pushNotifyMsg(NotifyMsg notifyMsg) {
		try {
			log.info("pushNotifyMsg:{}, thread:{}", JSON.toJSON(notifyMsg), Thread.currentThread().getId());
			boolean isOfflinePush = notifyMsg.isOfflinePush();
			String msgGroup = notifyMsg.getMsgGroup();
			int devType = notifyMsg.getDevType();
			List<Long> lstUser = notifyMsg.getUserIds();
			String title = notifyMsg.getTitle();
			String msg = notifyMsg.getMsg();
			Integer displayGroup = notifyMsg.getDisplayGroup();
			String url = notifyMsg.getUrl();
			Integer relationId = null;
			String notificationExtras = getNotificationExtras(displayGroup, msg, title, url);

			R resp = null;

			if (!isMsgSending) {
				log.info("**********pushNotifyMsg平台消息发送通道已经关闭!**********");
				return;
			}
			Integer pushType = notifyMsg.getPushType();
			if (pushType == null){
				pushType = PushTypeEnum.WEAK_MSG.getTypeValue();
			}
			if (InformEnum.MsgGroupEnum.ALL.getTypeCode().equals(msgGroup) && (CollectionUtil.isEmpty(lstUser) || lstUser.get(0) == 0)) {
				resp = pushNotifyMsgAsync.sendMsgToApp(devType, notificationExtras, title, pushType);
			} else {
				resp = pushNotifyMsgAsync.sendMsgToUserList(devType, lstUser, title, notificationExtras, isOfflinePush,pushType,notifyMsg.getDeviceList());
			}

			if (resp != null) {
				if (ResultCode.SUCCESS.getCode() != resp.getCode()) {
					log.error("pushNotifyMsg fail resp:{}, msgId:{}", JSON.toJSON(resp), notifyMsg.getMsgId());
				}
				log.info("pushNotifyMsg success msgId:{}", notifyMsg.getMsgId());
				Set<Long> setUser = (Set<Long>) resp.getData();
				if (CollectionUtils.isNotEmpty(setUser)) {
					this.savePushRecord(notifyMsg, setUser, relationId);
				}
			}
		} catch (Exception e) {
			log.error("pushNotifyMsg error:{}, msgId:{}", e.getMessage(), notifyMsg.getMsgId(), e);
		}
	}

	private String getNotificationExtras(Integer displayGroup, String msg, String title, String url) {
		Map<String, Object> extrasMap = new HashMap<>();

		if (StringUtils.isNotEmpty(msg) && msg.length() > 500) {
			msg = msg.substring(0, 50) + "...";
		}

		extrasMap.put("group", displayGroup);
		extrasMap.put("msg", msg);
		extrasMap.put("title", StringUtils.isNotBlank(title) ? title : "DigiFin");
		if (StringUtils.isBlank(url)) {
			url = PlatformUtil.getMsgStr(PushUrlProtocol.AppUrlEnum.DISPLAY_GROUP.getUrlProtocol(), Arrays.asList(displayGroup.toString()));
		}
		extrasMap.put("url", url);
		return JsonUtil.toJson(extrasMap);
	}

	private void savePushRecord(NotifyMsg notifyMsg, Set<Long> setUser, Integer relationId) {
		Date now = new Date();
		List<Long> lstUsers = Lists.newArrayList();
		switch (notifyMsg.getMsgGroup()) {
			case "A":
				lstUsers.add(0L);
				break;
			case "P":
				if (CollectionUtil.isNotEmpty(setUser)) {
					lstUsers.addAll(setUser);
				}
				break;
			default:
				break;
		}
		List<PlatformPushMsgHistoryEntity> lists = Lists.newArrayList();
		for (Long deviceId : lstUsers) {
			PlatformPushMsgHistoryEntity msgHistory = new PlatformPushMsgHistoryEntity();
			msgHistory.setContent(notifyMsg.getMsg());
			msgHistory.setRelationId(String.valueOf(relationId));
			msgHistory.setTargetId(deviceId);
			msgHistory.setOsType(notifyMsg.getDevType());
			msgHistory.setMsgGroup(notifyMsg.getMsgGroup());
			msgHistory.setSendTime(now);
			msgHistory.setSendStatus(InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());
			msgHistory.setCreateTime(now);
			msgHistory.setUpdateTime(now);
			msgHistory.setIsDeleted(0);
			msgHistory.setStatus(1);
			lists.add(msgHistory);
		}
		platformPushMsgHistoryMapper.saveNotifyMsg(lists);
	}
}
