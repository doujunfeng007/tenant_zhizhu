package com.minigod.zero.platform.core;

import com.google.common.collect.Lists;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.platform.core.push.PushMessage;
import com.minigod.zero.platform.entity.PlatformPushMsgHistoryEntity;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.vo.CustomerDeviceInfoVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/19 9:51
 * @description：短信记录处理
 */
@Component
public class PushMessageProcess implements MessageProcess {

	@Autowired
	private MessageMapperManager messageMapperManager;

	@Override
	public void saveMessageRecord(Message message) {
		PushMessage pushMessage = (PushMessage) message;

		List<CustomerDeviceInfoVO> deviceList = pushMessage.getDeviceList();
		if (CollectionUtil.isEmpty(deviceList)) {
			deviceList = new ArrayList<>();
		}
		/**
		 * 如果是全站推送，不需要去获取设备信息
		 */
		if (CollectionUtil.isNotEmpty(pushMessage.getUserIds())
			&& InformEnum.MsgGroupEnum.PERSON.getTypeCode().equals(pushMessage.getMsgGroup())) {
			R<List<CustomerDeviceInfoVO>> result =
				messageMapperManager.customerInfoClient().customerDeviceList(pushMessage.getUserIds());
			if (result.isSuccess()) {
				deviceList.addAll(result.getData());
			}
		}
		if (InformEnum.MsgGroupEnum.ALL.getTypeCode().equals(pushMessage.getMsgGroup())){
			R<List<CustomerDeviceInfoVO>> result =
				messageMapperManager.customerInfoClient().getAllUserDeviceIdList();
			if (result.isSuccess()) {
				deviceList.addAll(result.getData());
			}
		}

		if (CollectionUtil.isEmpty(deviceList) ) {
			throw new ZeroException("推送信息为空");
		}

		List<PlatformPushMsgHistoryEntity> lists = Lists.newArrayList();
		for (CustomerDeviceInfoVO deviceInfo : deviceList) {
			//推送记录
			lists.add(buildPushMsgHistory(deviceInfo,pushMessage));
		}
		messageMapperManager.pushMsg().saveNotifyMsg(lists);

		//后面添加已读未读消息要用到，这里在set一次
		pushMessage.setDeviceList(deviceList);

		//设备信息
		List<String> lstAndroidDeviceIdList = deviceList.stream()
			.map(CustomerDeviceInfoVO::getOpenCode)
			.filter(StringUtils::isNotBlank)
			.collect(Collectors.toList());

		pushMessage.setRegistrationIds(lstAndroidDeviceIdList);
	}

	@Override
	public void updateMessageRecord(SendResult sendResult, Message message) {
		PlatformPushMsgHistoryEntity historyEntity = new PlatformPushMsgHistoryEntity();
		historyEntity.setMsgId(sendResult.getMsgId());
		historyEntity.setChannel(sendResult.getChannel());

		if (ResultCode.OK.getCode() == sendResult.getStatus()) {
			historyEntity.setRelationId(sendResult.getSid());
			historyEntity.setSendStatus(InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());
		}
		//失败
		else {
			historyEntity.setSendStatus(InformEnum.SendStatusEnum.FAIL_SEND.getTypeCode());
		}
		messageMapperManager.pushMsg().updateByMsgId(historyEntity);
	}


	private PlatformPushMsgHistoryEntity buildPushMsgHistory(CustomerDeviceInfoVO deviceInfo,PushMessage pushMessage){

		/**
		 * 如果参数指定了系统类型，则根据参数指定的，否则根据设备信息来
		 * device.getOsType() + 1 是因为定义的osType枚举值币clientType 枚举值小1
		 */
		Integer clientType = pushMessage.getDevType() > 0 ? pushMessage.getDevType() : deviceInfo.getOsType() + 1;

		PlatformPushMsgHistoryEntity msgHistory = new PlatformPushMsgHistoryEntity();
		msgHistory.setContent(pushMessage.getContent());
		msgHistory.setTargetId(deviceInfo.getCustId());
		msgHistory.setOsType(clientType);
		msgHistory.setMsgGroup(pushMessage.getMsgGroup());
		msgHistory.setSendTime(DateUtil.now());
		msgHistory.setDeviceId(deviceInfo.getOpenCode());
		msgHistory.setContent(pushMessage.getContent());
		msgHistory.setSendStatus(InformEnum.SendStatusEnum.NO_SEND.getTypeCode());
		msgHistory.setCreateTime(DateUtil.now());
		msgHistory.setTenantId(pushMessage.getTenantId());
		msgHistory.setUpdateTime(DateUtil.now());
		msgHistory.setIsDeleted(0);
		msgHistory.setStatus(1);
		msgHistory.setTempCode(pushMessage.getTemplateCode());
		msgHistory.setMsgId(pushMessage.getMsgId());
		return msgHistory;
	}
}
