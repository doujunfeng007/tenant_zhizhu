package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.minigod.zero.manage.mapper.PlatformSysMsgMapper;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.manage.service.IPlatformSysMsgService;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.dto.SendSysMsgReqDTO;
import com.minigod.zero.platform.dto.SysMsgSearchReqDTO;
import com.minigod.zero.platform.entity.PlatformSysMsgEntity;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.platform.constants.Constants;
import com.minigod.zero.platform.utils.SequenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 系统通知信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-11
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PlatformSysMsgServiceImpl extends BaseServiceImpl<PlatformSysMsgMapper, PlatformSysMsgEntity> implements IPlatformSysMsgService {

	private final SequenceService sequenceService;
	private final IPlatformMsgClient platformMsgClient;

	@Override
	public IPage<PlatformSysMsgEntity> getSysMsgList(SysMsgSearchReqDTO sysMsgSearchReqDTO, Query query) {
		if (StringUtils.isNotEmpty(sysMsgSearchReqDTO.getSendTimeStart())) {
			Date startDate = DateUtil.parse(sysMsgSearchReqDTO.getSendTimeStart() + "00:00:00", "yyyy-MM-ddHH:mm:ss");
			sysMsgSearchReqDTO.setStartDate(startDate);
		}
		if (StringUtils.isNotEmpty(sysMsgSearchReqDTO.getSendTimeEnd())) {
			Date endDate = DateUtil.parse(sysMsgSearchReqDTO.getSendTimeEnd() + "23:59:59", "yyyy-MM-ddHH:mm:ss");
			sysMsgSearchReqDTO.setEndDate(endDate);
		}
		LambdaQueryWrapper<PlatformSysMsgEntity> wrapper = new LambdaQueryWrapper();
		if (sysMsgSearchReqDTO.getId() != null) {
			wrapper.eq(PlatformSysMsgEntity::getId, sysMsgSearchReqDTO.getId());
		}
		if (sysMsgSearchReqDTO.getTargetId() != null) {
			wrapper.eq(PlatformSysMsgEntity::getTargetId, sysMsgSearchReqDTO.getTargetId());
		}
		if (StringUtils.isNotEmpty(sysMsgSearchReqDTO.getTitle())) {
			wrapper.eq(PlatformSysMsgEntity::getTitle, sysMsgSearchReqDTO.getTitle());
		}
		if (sysMsgSearchReqDTO.getStartDate() != null) {
			wrapper.ge(PlatformSysMsgEntity::getSendTime, sysMsgSearchReqDTO.getStartDate());
		}
		if (sysMsgSearchReqDTO.getEndDate() != null) {
			wrapper.le(PlatformSysMsgEntity::getSendTime, sysMsgSearchReqDTO.getEndDate());
		}
		if (sysMsgSearchReqDTO.getSendStatus() != null) {
			wrapper.eq(PlatformSysMsgEntity::getSendStatus, sysMsgSearchReqDTO.getSendStatus());
		}
		if (StringUtils.isNotEmpty(sysMsgSearchReqDTO.getMsgGroup())) {
			wrapper.eq(PlatformSysMsgEntity::getMsgGroup, sysMsgSearchReqDTO.getMsgGroup());
		}
		if (sysMsgSearchReqDTO.getClientType() != null) {
			wrapper.eq(PlatformSysMsgEntity::getClientType, sysMsgSearchReqDTO.getClientType());
		}
		wrapper.orderByDesc(PlatformSysMsgEntity::getCreateTime);
		IPage<PlatformSysMsgEntity> page = baseMapper.selectPage(Condition.getPage(query), wrapper);
		return page;
	}

	/**
	 * 发送系统通知
	 * 发送系统消息 如果发送全站，把已经保存好的消息状态更新为有效，生成一个新的update_version
	 * 如果发送个人，根据userid列表增加系统消息，使用新的统一的update_version
	 */
	@Override
	public void saveUpdateAndSendSysMsg(SendSysMsgReqDTO sendSysMsgReqDTO) {
		//服务通知与系统通知的区别
		//1.系统通知有单独的表，服务通知写表是调用common.saveMsg
		//2.系统通知调用个推的模板代码是1029，服务通知调用的不一样
		List<Long> userIdLongs = null;
		//模板类型由前端传入
		sendSysMsgReqDTO.setSendStatus(Constants.SysMsgContants.SYS_MSG_SENDSTATUS_NOTSNED);//未发送
		if (sendSysMsgReqDTO.getUserIds() != null && sendSysMsgReqDTO.getUserIds().length > 0) {
			userIdLongs = Arrays.asList(sendSysMsgReqDTO.getUserIds());
		}
		long updateVersion = sequenceService.nextId(Constants.SysMsgContants.SYS_MSG_VERSION);
		PlatformSysMsgEntity sysMsg = new PlatformSysMsgEntity();
		BeanUtils.copyProperties(sendSysMsgReqDTO, sysMsg);
		if (StringUtils.isNotEmpty(sendSysMsgReqDTO.getPopInvalidTime())) {
			Date popInvalidTime = DateUtil.parse(sendSysMsgReqDTO.getPopInvalidTime() + "00:00:00", "yyyy-MM-ddHH:mm:ss");
			sysMsg.setPopInvalidTime(popInvalidTime);
		}
		sysMsg.setUpdVersion(updateVersion);
		sysMsg.setOprId(AuthUtil.getUserId());
		sysMsg.setOprName(AuthUtil.getUserName());

		if (sysMsg.getSendWay().equals(Constants.SysMsgContants.SYS_MSG_SENDWAY_GIVINGTIME)) {//定时类型的只保存不发送

			if (sysMsg.getMsgGroup().equals(Constants.SysMsgContants.SYS_MSG_GROUP_ALL)) {
				sysMsg.setTargetId(Constants.SysMsgContants.SYS_MSG_TARGETID_ALL);
				sysMsg.setUpdVersion(0L);
				sysMsg.setSendStatus(InformEnum.SendStatusEnum.NO_SEND.getTypeCode());
				baseMapper.insert(sysMsg);
			} else {
				List<PlatformSysMsgEntity> sysMsgs = new ArrayList<PlatformSysMsgEntity>();
				for (int i = 0; i < userIdLongs.size(); i++) {
					PlatformSysMsgEntity newSysMsg = new PlatformSysMsgEntity();
					BeanUtils.copyProperties(sysMsg, newSysMsg);
					newSysMsg.setTargetId(userIdLongs.get(i));
					newSysMsg.setUpdVersion(0L);
					newSysMsg.setSendStatus(InformEnum.SendStatusEnum.NO_SEND.getTypeCode());
					newSysMsg.setStatus(1);
					newSysMsg.setIsDeleted(0);
					newSysMsg.setCreateTime(new Date());
					newSysMsg.setUpdateTime(new Date());
					newSysMsg.setCreateUser(AuthUtil.getUserId());
					newSysMsg.setUpdateUser(AuthUtil.getUserId());
					sysMsgs.add(newSysMsg);
				}
				baseMapper.saveSysMsgBatch(sysMsgs);
			}
			return;
		}

		SendNotifyDTO sendNotifyDTO = new SendNotifyDTO();
		sendNotifyDTO.setDisplayGroup(MsgStaticType.DisplayGroup.SYSTEM_MSG);
		sendNotifyDTO.setClientType(sysMsg.getClientType());
		sendNotifyDTO.setMsgContent(sysMsg.getContent());
		sendNotifyDTO.setTitle(sysMsg.getTitle());
		sendNotifyDTO.setUrl(sysMsg.getUrl());
		// 发送全站
		if (sysMsg.getMsgGroup().equals(Constants.SysMsgContants.SYS_MSG_GROUP_ALL)) {
			sysMsg.setTargetId(Constants.SysMsgContants.SYS_MSG_TARGETID_ALL);
			sendNotifyDTO.setMsgGroup(InformEnum.MsgGroupEnum.ALL.getTypeCode());
			sendNotifyDTO.setLstToUserId(Lists.newArrayList(0L));
			if (sysMsg.getSendWay().equals(Constants.SysMsgContants.SYS_MSG_SENDWAY_NOW)) {//即时推送类型的会直接推送
				R sendResp = platformMsgClient.sendNotify(sendNotifyDTO);
				if (sendResp.isSuccess()) {
					sysMsg.setSendTime(new Date());
					sysMsg.setSendStatus(InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());//发送状态更新为已发送
				}
			}
			baseMapper.insert(sysMsg);

		} else {
			sendNotifyDTO.setMsgGroup(InformEnum.MsgGroupEnum.PERSON.getTypeCode());
			sendNotifyDTO.setLstToUserId(userIdLongs);
			// 发送个人
			List<PlatformSysMsgEntity> sysMsgs = new ArrayList<>();
			for (int i = 0; i < userIdLongs.size(); i++) {
				PlatformSysMsgEntity newSysMsg = new PlatformSysMsgEntity();
				BeanUtils.copyProperties(sysMsg, newSysMsg);
				newSysMsg.setTargetId(userIdLongs.get(i));
				if (newSysMsg.getSendWay().equals(Constants.SysMsgContants.SYS_MSG_SENDWAY_NOW)) {
					newSysMsg.setSendStatus(InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());//发送状态更新为已发送
					newSysMsg.setSendTime(new Date());
				}
				newSysMsg.setStatus(1);
				newSysMsg.setIsDeleted(0);
				newSysMsg.setCreateTime(new Date());
				newSysMsg.setUpdateTime(new Date());
				newSysMsg.setCreateUser(AuthUtil.getUserId());
				newSysMsg.setUpdateUser(AuthUtil.getUserId());
				sysMsgs.add(newSysMsg);
			}
			if (sysMsgs != null && sysMsgs.size() > 0) {
				baseMapper.saveSysMsgBatch(sysMsgs);
				if (sysMsg.getSendWay().equals(Constants.SysMsgContants.SYS_MSG_SENDWAY_NOW)) {//即时推送类型的会直接推送
					sendNotifyDTO.setSendTime(new Date());
					platformMsgClient.sendNotify(sendNotifyDTO);
				}
			}
		}
	}

	@Override
	public PlatformSysMsgEntity findSysMsg(Long id) {
		LambdaQueryWrapper<PlatformSysMsgEntity> wrapper = new LambdaQueryWrapper();
		wrapper.eq(BaseEntity::getId, id).eq(BaseEntity::getStatus, 1).eq(BaseEntity::getIsDeleted, 0);
		return baseMapper.selectOne(wrapper);
	}
}
