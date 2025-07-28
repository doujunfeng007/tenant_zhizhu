package com.minigod.zero.platform.service.impl;

import com.google.common.collect.Lists;
import com.minigod.zero.platform.service.AppService;
import com.minigod.zero.platform.service.IPlatformMsgReadRecordService;
import com.minigod.zero.platform.vo.*;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.platform.constants.Constants;
import com.minigod.zero.platform.dto.MsgDetailDTO;
import com.minigod.zero.platform.dto.PlatformOpenCommonDTO;
import com.minigod.zero.platform.dto.SysMsgDTO;
import com.minigod.zero.platform.dto.UnreadMsgDTO;
import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;
import com.minigod.zero.platform.entity.PlatformMsgReadRecordEntity;
import com.minigod.zero.platform.entity.PlatformSysMsgEntity;
import com.minigod.zero.platform.enums.EUserMsgGroupEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.service.IPlatformInvestMsgService;
import com.minigod.zero.platform.service.IPlatformSysMsgService;
import com.minigod.zero.platform.utils.PlatformUtil;
import com.minigod.zero.platform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Zhe.Xiao
 */
@Service
@Slf4j
public class AppServiceImpl implements AppService {

	@Resource
	private IPlatformMsgReadRecordService platformMsgReadRecordService;
	@Resource
	private IPlatformInvestMsgService platformInvestMsgService;
	@Resource
	private IPlatformSysMsgService platformSysMsgService;

	@Override
	public R getMessageGroup(CommonReqVO<PlatformOpenCommonDTO> req) {
		Long custId = AuthUtil.getCustId();

		// 交易提醒、行情提醒、新股提醒、服务通知、系统通知、活动推送、热点资讯
		int[] displayGroups = {MsgStaticType.DisplayGroup.TRADE_PUSH, MsgStaticType.DisplayGroup.MTK_NOTIFY, MsgStaticType.DisplayGroup.STK_NEW_REMIND, MsgStaticType.DisplayGroup.SERVICE_MSG, MsgStaticType.DisplayGroup.SYSTEM_MSG, MsgStaticType.DisplayGroup.ACTIVITY_PUSH, MsgStaticType.DisplayGroup.HOT_NEWS};

		List<MessageUnreadVO> result = Lists.newArrayList();
		for (Integer displayGroup : displayGroups) {
			MessageUnreadVO vo = handleMessage(custId, displayGroup);
			vo.setMessageGroupName(EUserMsgGroupEnum.getDescByCode(displayGroup));
			vo.setMessageIcon("");
			vo.setTargetFlag(EUserMsgGroupEnum.getByCode(displayGroup).name());
			vo.setMessageGroup(displayGroup);
			result.add(vo);
		}

		return R.data(result);
	}

	@Override
	public R updateAndFindUnReadMsg(CommonReqVO<UnreadMsgDTO> req) {
		Long custId = AuthUtil.getCustId();

		UnreadMsgDTO params = req.getParams();
		Integer messageGroup = params.getMessageGroup();
		Long locateVersion = params.getLocateVersion();
		Integer count = params.getCount();
		Integer page = params.getPage();
		return updateAndGetUnreadMsg(messageGroup, custId, locateVersion, page, count);
	}

	public R<UnreadMsgRespVO> updateAndGetUnreadMsg(Integer messageGroup, Long custId, Long locateVersion, Integer page, Integer count) {
		Integer clientType = PlatformUtil.getClientType();
		UnreadMsgRespVO objectResult = new UnreadMsgRespVO();

		// 设置默认值
		count = (count == null) ? 30 : count;
		page = (page == null) ? 1 : page;

		// 参数校验
		if (messageGroup == null) {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}

		// 获取未读标志
		PlatformMsgReadRecordEntity msgReadRecord = null;
		if (custId != null && custId.compareTo(AuthUtil.GUEST_CUST_ID) != 0) {
			msgReadRecord = platformMsgReadRecordService.findMsgReadRecord(custId, messageGroup, clientType);
			locateVersion = (locateVersion == null) ? (msgReadRecord == null ? 0L : msgReadRecord.getReadVersion()) : locateVersion;
		}

		// 获取未读消息
		List<ShowMsg> lstUnReadMsgs = platformInvestMsgService.findInvestMsg(
			(custId != null && custId.compareTo(AuthUtil.GUEST_CUST_ID) != 0) ? custId : Constants.USERID_ALL_USER,
			clientType,
			messageGroup,
			locateVersion,
			page,
			count
		);

		// 处理未读消息
		List<UnreadMsgRespVO.UnReadMsgRespVO_Data_Orign> lstDatas = new ArrayList<>();
		long maxVersion = processUnreadMessages(lstUnReadMsgs, msgReadRecord, lstDatas);

		// 设置未读消息数量
		objectResult.setUnreadCount(msgReadRecord != null ? msgReadRecord.getUnreadNum() : 0);
		objectResult.setData(lstDatas);
		objectResult.setVersion(maxVersion);

		// 更新消息阅读记录
		if (msgReadRecord != null && maxVersion >= msgReadRecord.getReadVersion()) {
			updateMsgReadRecord(msgReadRecord, maxVersion);
		}

		return R.data(objectResult);
	}

	private long processUnreadMessages(List<ShowMsg> lstUnReadMsgs, PlatformMsgReadRecordEntity msgReadRecord, List<UnreadMsgRespVO.UnReadMsgRespVO_Data_Orign> lstDatas) {
		long maxVersion = 0L;

		if (CollectionUtils.isNotEmpty(lstUnReadMsgs)) {
			for (ShowMsg unReadMsg : lstUnReadMsgs) {
				UnreadMsgRespVO.UnReadMsgRespVO_Data_Orign data = new UnreadMsgRespVO.UnReadMsgRespVO_Data_Orign();
				data.setMsgId(unReadMsg.getId());
				data.setVersion(unReadMsg.getUpdVersion());
				data.setMsgType(unReadMsg.getMsgType());
				data.setTitle(unReadMsg.getTitle());
				data.setMsgCon(unReadMsg.getMsgContent());
				data.setMessageGroup(unReadMsg.getDisplayGroup());
				data.setuId(unReadMsg.getFromUser().longValue());
				data.setuImg(unReadMsg.getUserIcon());
				data.setuName(unReadMsg.getNickName());
				data.setuType(unReadMsg.getUserType());
				data.setUrl(unReadMsg.getUrl());
				data.setTs(unReadMsg.getCreateTime().getTime());
				data.setIsRead((msgReadRecord != null && unReadMsg.getUpdVersion().longValue() <= msgReadRecord.getReadVersion().longValue()) ? 1 : 0);
				maxVersion = Math.max(maxVersion, unReadMsg.getUpdVersion().longValue());
				data.setExtend(unReadMsg.getExtend());
				lstDatas.add(data);
			}
		}

		return maxVersion;
	}

	private void updateMsgReadRecord(PlatformMsgReadRecordEntity msgReadRecord, long maxVersion) {
		msgReadRecord.setReadVersion(maxVersion);
		msgReadRecord.setUnreadNum(0);
		msgReadRecord.setUpdateTime(new Date());
		platformMsgReadRecordService.updateMsgReadRecord(msgReadRecord);
	}

	@Override
	public R findSystemMessage(CommonReqVO<SysMsgDTO> req) {
		// 取得用户id
		Long custId = AuthUtil.getCustId();
		Integer clientType = PlatformUtil.getClientType();

		PlatformMsgReadRecordEntity msgReadRecord = null;
		if (custId != null && AuthUtil.GUEST_CUST_ID.compareTo(custId) != 0) {
			msgReadRecord = platformMsgReadRecordService.findMsgReadRecord(custId, MsgStaticType.DisplayGroup.SYSTEM_MSG, clientType);
		} else {
			custId = Constants.USERID_ALL_USER;
		}

		Long lngVersion = req.getParams().getVersion();


		Integer page = req.getParams().getPage();
		if (page == null) {
			page = 1;
		}
		Integer intCount = req.getParams().getCount();
		if (intCount == null) {
			intCount = 30;
		}
		if (lngVersion == null || lngVersion == 0L) {
			if (msgReadRecord != null) {
				lngVersion = msgReadRecord.getReadVersion();
			} else {
				lngVersion = 0L;
			}
		}

		// 查询数据
		List<PlatformSysMsgEntity> lstSysMsgs = platformSysMsgService.findInformSysMsg(lngVersion, page, intCount, custId, clientType);
		SysMsgRespVO resultObject = new SysMsgRespVO();
		if (CollectionUtils.isNotEmpty(lstSysMsgs)) {
			List<SysMsgRespVO.SysMsgRespVO_data> lstdDatas = new ArrayList<SysMsgRespVO.SysMsgRespVO_data>();
			for (PlatformSysMsgEntity sysMsg : lstSysMsgs) {
				if (sysMsg.getUpdVersion() > lngVersion) {
					lngVersion = sysMsg.getUpdVersion();
				}
				SysMsgRespVO.SysMsgRespVO_data data = new SysMsgRespVO.SysMsgRespVO_data();
				// 版本号小于 读取记录的版本号标识为已读
				if (intCount != null || (msgReadRecord != null && sysMsg.getUpdVersion() <= msgReadRecord.getReadVersion())) {
					// 消息是在用户创建时间之后的标志为未读
//					if (custInfo != null && custInfo.getCreateTime().getTime() < sysMsg.getCreateTime().getTime()) {
//						data.setIsRead(0);
//					} else {
						data.setIsRead(1);
//					}
				} else {
					data.setIsRead(0);
				}
				data.setMsgId(sysMsg.getId());
				data.setMsgTitle(sysMsg.getTitle());
				data.setMsgType(sysMsg.getMsgType());
				data.setMsgContent(sysMsg.getContent());
				data.setTs(sysMsg.getCreateTime().getTime());
				data.setMsgLev(sysMsg.getMsgLevel());
				data.setUrl(sysMsg.getUrl());
				data.setStatus(1);
				lstdDatas.add(data);
			}
			resultObject.setData(lstdDatas);
		}

		if (lngVersion != null && msgReadRecord != null && lngVersion.longValue() > msgReadRecord.getReadVersion().longValue()) {
			msgReadRecord.setReadVersion(lngVersion);
			msgReadRecord.setUnreadNum(0);
			msgReadRecord.setUpdateTime(new Date());
			platformMsgReadRecordService.updateMsgReadRecord(msgReadRecord);
		}

		resultObject.setVersion(lngVersion);
		return R.data(resultObject);
	}

	@Override
	public R findAppPopSystemMessage(CommonReqVO<SysMsgDTO> req) {
		// 取得用户id
		Long custId = AuthUtil.getCustId();

		Long lngVersion = req.getParams().getVersion();
		if (lngVersion == null) {
			lngVersion = 0L;
		}
		// 查询数据
		if (custId == null || custId.compareTo(AuthUtil.GUEST_CUST_ID) == 0) {
			custId = Constants.USERID_ALL_USER;
		}
		List<PlatformSysMsgEntity> popSysMsgs = platformSysMsgService.findPopInformSysMsg(lngVersion, custId);

		return R.data(popSysMsgs);
	}

	@Override
	public R getMessageDetail(CommonReqVO<MsgDetailDTO> req) {
		if (req.getParams() == null || StringUtils.isBlank(req.getParams().getMsgId()) || req.getParams().getMessageGroup() == null) {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}

		MsgDetailRespVO vo = new MsgDetailRespVO();

		if (req.getParams().getMessageGroup() == MsgStaticType.DisplayGroup.SYSTEM_MSG) {
			//系统通知
			PlatformSysMsgEntity sysMsg = platformSysMsgService.findInformSysMsgByMsgId(Long.valueOf(req.getParams().getMsgId()));
			if (sysMsg != null) {
				// TODO：根据多语言参数显示
				vo.setTitle(sysMsg.getTitle());
				vo.setContent(sysMsg.getContent());
				vo.setTs(sysMsg.getCreateTime().getTime());
			} else {
				return R.fail(ResultCode.PARAMETER_DISMATCH);
			}
		} else {
			PlatformInvestMsgEntity investMsg = platformInvestMsgService.findInvestMsgByMsgId(Long.valueOf(req.getParams().getMsgId()));
			if (investMsg != null) {
				// TODO：根据多语言参数显示
				vo.setTitle(investMsg.getTitle());
				vo.setContent(investMsg.getMsgContent());
				vo.setTs(investMsg.getCreateTime().getTime());
			} else {
				return R.fail(ResultCode.PARAMETER_DISMATCH);
			}
		}
		return R.data(vo);
	}

	@Override
	public R getIsMessageUnread(CommonReqVO<PlatformOpenCommonDTO> req) {
		R rt = R.success();

		try {
			Long custId = AuthUtil.getCustId();
			Integer clientType = PlatformUtil.getClientType();
			if (custId == null) {
				log.info("找不到设备号{}对应的custId", req.getParams().getDeviceCode());
				rt.setData(false);
				return rt;
			}
			if (custId.compareTo(AuthUtil.GUEST_CUST_ID) == 0) {
				log.info("设备号{}当前为游客", req.getParams().getDeviceCode());
				rt.setData(false);
				return rt;
			}

			boolean isMessageUnread = platformMsgReadRecordService.getIsMessageUnread(custId, clientType);
			rt.setData(isMessageUnread);
		} catch (Exception e) {
			log.error("getIsMessageUnread error:", e);
			rt.setData(false);
		}

		return rt;
	}

	/**
	 * 处理消息数据
	 * 返回未读数量
	 */
	private MessageUnreadVO handleMessage(Long custId, int displayGroup) {
		Integer clientType = PlatformUtil.getClientType();

		MessageUnreadVO message = new MessageUnreadVO();
		String title = "";
		String msgContent = "";

		if (custId == null || custId.compareTo(AuthUtil.GUEST_CUST_ID) == 0) {
			message.setUnReadMessageCount("0");
			// 游客只显示全站推送的消息
			custId = Constants.USERID_ALL_USER;
		} else {
			PlatformMsgReadRecordEntity record = platformMsgReadRecordService.findMsgReadRecord(custId, displayGroup, clientType);
			if (record == null) {
				message.setUnReadMessageCount("0");
			} else {
				Integer num = record.getUnreadNum();
				message.setUnReadMessageCount(String.valueOf(num));

				Integer maxValue = 99;
				if (num > maxValue) {
					message.setUnReadMessageCount("99+");
				}
			}
		}

		if (displayGroup == MsgStaticType.DisplayGroup.SYSTEM_MSG) {
			PlatformSysMsgEntity lastSysMsg = platformSysMsgService.findLastInformSysMsg(custId, clientType);
			if (lastSysMsg != null) {
				title = lastSysMsg.getTitle() == null ? "" : lastSysMsg.getTitle();
				msgContent = lastSysMsg.getContent() == null ? "" : lastSysMsg.getContent();
				message.setTs(lastSysMsg.getCreateTime().getTime());
			}
		} else {
			PlatformInvestMsgEntity lastInvestMsg = platformInvestMsgService.findLastInvestMsg(custId, displayGroup, clientType);
			if (lastInvestMsg != null) {
				title = lastInvestMsg.getTitle() == null ? "" : lastInvestMsg.getTitle();
				msgContent = lastInvestMsg.getMsgContent() == null ? "" : lastInvestMsg.getMsgContent();
				message.setTs(lastInvestMsg.getCreateTime().getTime());
			}
		}

		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(msgContent)) {
			sb.append(title).append(" | ").append(msgContent);
		} else if (StringUtils.isBlank(title) && StringUtils.isNotBlank(msgContent)) {
			sb.append(msgContent);
		} else if (StringUtils.isBlank(msgContent) && StringUtils.isNotBlank(title)) {
			sb.append(title);
		}
		if (sb.length() > 33) {
			sb.delete(33, sb.length());
			sb.append("...");
		}
		String messagePreview = sb.toString();
		message.setMessagePreview(messagePreview);

		return message;
	}

}
