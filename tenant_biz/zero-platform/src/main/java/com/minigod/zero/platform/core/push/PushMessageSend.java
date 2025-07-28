package com.minigod.zero.platform.core.push;

import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.platform.core.*;
import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;
import com.minigod.zero.platform.entity.PlatformMsgReadRecordEntity;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.vo.CustomerDeviceInfoVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.minigod.zero.platform.constants.Constants.USERID_ALL_USER;


/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/12 16:20
 * @description：
 */
@Component
public class PushMessageSend extends AbstractMessageSend implements InitializingBean {

	@Value("${spring.profiles.active}")
	private String active;

	@Value("${message.pushEnable: true}")
	private Boolean enable;

	public static final String VERSION_PREFIX = "unread_msg_";

	private String DEFAULT_CHANNEL = String.valueOf(com.minigod.zero.platform.enums.PushChannel.JG.getCode());

	private static Map<String, PushChannel> PUSH_CHANNEL_MAP = new HashMap<>();

	private ApplicationContext applicationContext;

	public PushMessageSend(MessageMapperManager contentMapperManger,
							ApplicationContext applicationContext,PushMessageProcess pushMessageProcess,
							ZeroRedis zeroRedis) {
		super(contentMapperManger,pushMessageProcess,zeroRedis);
		this.applicationContext = applicationContext;
	}

	@Override
	protected SendResult doSend(Message message) {
		PushMessage pushMessage = (PushMessage) message;

		setApnsProduction(pushMessage);

		setRegistrationIds(message);

		SendResult sendResult = new SendResult();
		sendResult.setMsgId(message.getMsgId());
		if (enable){
			sendResult = getPushChannel().send(pushMessage);
		}
		else{
			sendResult.setStatus(ResultCode.OK.getCode());
			sendResult.setErrorMsg("配置不发送");
		}
		//发送成功添加未读消息
		if (sendResult.getStatus() == ResultCode.OK.getCode()){
			processUnreadMessages(pushMessage);
		}
		return sendResult;
	}

	@Override
	protected String rateLimiterAccount(Message message) {
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return PushMessage.class.isAssignableFrom(authentication);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String,PushChannel> handlerMap = applicationContext.getBeansOfType(PushChannel.class);
		if (handlerMap != null && handlerMap.size() > 0){
			for (Map.Entry entry: handlerMap.entrySet()) {
				PushChannel channel = (PushChannel) entry.getValue();
				PUSH_CHANNEL_MAP.put(channel.pushChannelType(),channel);
			}
		}
	}


	private void setApnsProduction(PushMessage pushMessage){
		pushMessage.setApnsProduction("prd".equals(active));
	}

	private PushChannel getPushChannel(){
		return PUSH_CHANNEL_MAP.get(DEFAULT_CHANNEL);
	}

	private void setRegistrationIds(Message message) {
		PushMessage pushMessage = (PushMessage) message;

		List<CustomerDeviceInfoVO> deviceList = pushMessage.getDeviceList();
		if (CollectionUtil.isEmpty(deviceList)) {
			throw new ZeroException("发送失败，未找到设备信息");
		}
		//设备信息
		List<String> lstAndroidDeviceIdList = deviceList.stream()
			.map(CustomerDeviceInfoVO::getOpenCode)
			.filter(StringUtils::isNotBlank)
			.collect(Collectors.toList());

		pushMessage.setRegistrationIds(lstAndroidDeviceIdList);
	}


	/**
	 * 处理消息未读
	 * @param pushMessage
	 */
	private void processUnreadMessages(PushMessage pushMessage) {
		if (CollectionUtil.isEmpty(pushMessage.getDeviceList())
			&& pushMessage.getDisplayGroup() != null) {
			return;
		}

		if (InformEnum.MsgGroupEnum.ALL.getTypeCode().equals(pushMessage.getMsgGroup())) {
			saveInvestMsg(Collections.singletonList(buildAllInvestMsg(pushMessage)));
			processAllUnreadMessages(pushMessage);
		} else if (InformEnum.MsgGroupEnum.PERSON.getTypeCode().equals(pushMessage.getMsgGroup())) {
			List<PlatformInvestMsgEntity> investMsgList = new ArrayList<>();
			List<PlatformMsgReadRecordEntity> readRecordList = new ArrayList<>();

			pushMessage.getDeviceList().forEach(device -> {
				/**
				 * 如果参数指定了系统类型，则根据参数指定的，否则根据设备信息来
				 * device.getOsType() + 1 是因为定义的osType枚举值币clientType 枚举值小1
				 */
				Integer clientType = pushMessage.getDevType() > 0 ? pushMessage.getDevType() : device.getOsType() + 1;

				investMsgList.add(buildPersonInvestMsg(device, pushMessage,clientType));
				readRecordList.add(buildPersonUnreadMessages(device.getCustId(),clientType, pushMessage));
			});

			saveInvestMsg(investMsgList);
			processPersonUnreadMessages(readRecordList);
		}
	}

	/**
	 * 处理全站未读消息
	 */
	private void processAllUnreadMessages(PushMessage pushMessage) {
		PlatformMsgReadRecordEntity record = getOrCreateReadRecord(USERID_ALL_USER,1, pushMessage);
		record.setUnreadNum(record.getUnreadNum() + 1);
		saveOrUpdateReadRecord(record);

		record = getOrCreateReadRecord(USERID_ALL_USER,2, pushMessage);
		record.setUnreadNum(record.getUnreadNum() + 1);
		saveOrUpdateReadRecord(record);
	}

	private void processPersonUnreadMessages(List<PlatformMsgReadRecordEntity> readRecordList) {
		readRecordList.forEach(this::saveOrUpdateReadRecord);
	}

	private PlatformMsgReadRecordEntity buildPersonUnreadMessages(Long userId,Integer clientType, PushMessage pushMessage) {
		return getOrCreateReadRecord(userId,clientType, pushMessage);
	}

	private PlatformMsgReadRecordEntity getOrCreateReadRecord(Long userId,Integer clientType, PushMessage pushMessage) {
		PlatformMsgReadRecordEntity param = new PlatformMsgReadRecordEntity();
		param.setUserId(userId);
		param.setMsgCode(pushMessage.getDisplayGroup());
		List<PlatformMsgReadRecordEntity> list = contentMapperManger.msgReadRecord().selectByParam(param);

		PlatformMsgReadRecordEntity result = null;
		if (CollectionUtil.isNotEmpty(list)) {
			result =  list.get(0);
		} else {
			result =  buildReadRecord(userId,pushMessage);
		}
		result.setClientType(clientType);
		result.setUnreadNum(result.getUnreadNum()+1);
		return result;
	}

	private void saveOrUpdateReadRecord(PlatformMsgReadRecordEntity record) {
		if (record.getId() == null) {
			contentMapperManger.msgReadRecord().insert(record);
		} else {
			contentMapperManger.msgReadRecord().update(record);
		}
	}

	private void saveInvestMsg(List<PlatformInvestMsgEntity> investMsgList) {
		contentMapperManger.investMsg().saveInvestMsgs(investMsgList);
	}

	private PlatformInvestMsgEntity buildAllInvestMsg(PushMessage pushMessage) {
		return buildInvestMsg(pushMessage, USERID_ALL_USER, 0); // 对于全站用户，clientType 为 0
	}

	private PlatformInvestMsgEntity buildPersonInvestMsg(CustomerDeviceInfoVO deviceInfo, PushMessage pushMessage,Integer clientType) {
		return buildInvestMsg(pushMessage, deviceInfo.getCustId(), clientType);
	}

	/**
	 * 全站消息只存一条记录，userId = 0
	 * @param pushMessage
	 * @return
	 */
	private PlatformInvestMsgEntity buildInvestMsg(PushMessage pushMessage, Long userId, Integer clientType) {
		PlatformInvestMsgEntity investMsg = new PlatformInvestMsgEntity();
		investMsg.setFromUser(0L);
		investMsg.setDisplayGroup(pushMessage.getDisplayGroup());
		investMsg.setTitle(pushMessage.getTitle());
		investMsg.setMsgContent(pushMessage.getContent());
		investMsg.setStatus(1);
		Date nowDate = new Date();
		investMsg.setCreateTime(nowDate);
		investMsg.setUpdateTime(nowDate);
		investMsg.setSendTime(nowDate);
		investMsg.setSendWay(pushMessage.getSendWay());
		investMsg.setMsgGroup(pushMessage.getMsgGroup());
		investMsg.setClientType(clientType);
		investMsg.setUrl(pushMessage.getUrl());
		investMsg.setIsDeleted(0);
		investMsg.setUserId(userId);
		investMsg.setTenantId(pushMessage.getTenantId());

		Long lupdateVersion = contentMapperManger.sequenceService().nextId(VERSION_PREFIX + pushMessage.getDisplayGroup());
		investMsg.setUpdVersion(lupdateVersion);
		investMsg.setSendStatus(InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());

		return investMsg;
	}

	/**
	 * 已读未读记录表
	 * @param pushMessage
	 * @return
	 */
	private PlatformMsgReadRecordEntity buildReadRecord(Long userId,PushMessage pushMessage) {
		PlatformMsgReadRecordEntity msgReadRecord = new PlatformMsgReadRecordEntity();
		msgReadRecord.setStatus(1);
		msgReadRecord.setReadVersion(0L);
		msgReadRecord.setUnreadNum(1);
		msgReadRecord.setLockVersion(1);
		msgReadRecord.setIsDeleted(0);
		msgReadRecord.setUserId(userId);
		msgReadRecord.setCreateTime(DateUtil.now());
		msgReadRecord.setUpdateTime(DateUtil.now());
		msgReadRecord.setClientType(pushMessage.getDevType());
		msgReadRecord.setMsgCode(pushMessage.getDisplayGroup());
		return msgReadRecord;
	}
}
