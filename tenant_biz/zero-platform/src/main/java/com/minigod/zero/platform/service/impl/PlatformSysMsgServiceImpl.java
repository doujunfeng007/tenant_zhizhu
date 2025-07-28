package com.minigod.zero.platform.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.platform.mapper.PlatformSysMsgMapper;
import com.minigod.zero.platform.service.IPlatformPushService;
import com.minigod.zero.platform.service.PushNotifyMsgService;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.feign.ICustDeviceClient;
import com.minigod.zero.cust.feign.ICustInfoClient;
import com.minigod.zero.platform.common.NotifyMsg;
import com.minigod.zero.platform.constants.Constants;
import com.minigod.zero.platform.entity.PlatformSysMsgEntity;
import com.minigod.zero.platform.enums.ESysMsgGroup;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PlatformOsTypeEnum;
import com.minigod.zero.platform.service.IPlatformSysMsgService;
import com.minigod.zero.platform.utils.SequenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class PlatformSysMsgServiceImpl extends BaseServiceImpl<PlatformSysMsgMapper, PlatformSysMsgEntity> implements IPlatformSysMsgService {

	@Resource
	private SequenceService sequenceService;
	@Resource
	private IPlatformPushService platformPushService;
	@Resource
	private PushNotifyMsgService pushNotifyMsgService;
	@Resource
	private ICustInfoClient custInfoClient;
	@Resource
	private ICustDeviceClient custDeviceClient;

	@Override
	public List<PlatformSysMsgEntity> findInformSysMsg(Long lngVersion, Integer count, Long userId, Integer clientType) {
		LambdaQueryWrapper<PlatformSysMsgEntity> wrapper = new QueryWrapper<PlatformSysMsgEntity>().lambda();
		// TODO 系统消息只会查询消息记录版本号大于前端传参version的记录，而其他分组的消息（invest_msg）会查询所有记录。这块逻辑是否要改成一样
//		if (lngVersion != null) {
//			wrapper.gt(PlatformSysMsgEntity::getUpdVersion, lngVersion);
//		}
		wrapper.and(wq -> wq
			.eq(PlatformSysMsgEntity::getMsgGroup, ESysMsgGroup.A.toString()).or()
			.eq(PlatformSysMsgEntity::getMsgGroup, ESysMsgGroup.P.toString())
			.eq(PlatformSysMsgEntity::getTargetId, userId));

		if (clientType == PlatformOsTypeEnum.OS_ALL.getTypeValue()) {
			Integer[] arrayClientType = {PlatformOsTypeEnum.OS_ALL.getTypeValue(), PlatformOsTypeEnum.OS_ANDROID.getTypeValue(), PlatformOsTypeEnum.OS_IOS.getTypeValue()};
			wrapper.in(PlatformSysMsgEntity::getClientType, arrayClientType);
		} else {
			Integer[] arrayClientType = {PlatformOsTypeEnum.OS_ALL.getTypeValue(), clientType};
			wrapper.in(PlatformSysMsgEntity::getClientType, arrayClientType);
		}

		wrapper.orderByDesc(PlatformSysMsgEntity::getCreateTime);

		if (count != null) {
			wrapper.last("limit " + count);
		}

		return baseMapper.selectList(wrapper);
	}

	@Override
	public List<PlatformSysMsgEntity> findInformSysMsg(Long lngVersion, Integer page, Integer count, Long userId, Integer clientType) {
		LambdaQueryWrapper<PlatformSysMsgEntity> wrapper = new QueryWrapper<PlatformSysMsgEntity>().lambda();
		// TODO 系统消息只会查询消息记录版本号大于前端传参version的记录，而其他分组的消息（invest_msg）会查询所有记录。这块逻辑是否要改成一样
//		if (lngVersion != null) {
//			wrapper.gt(PlatformSysMsgEntity::getUpdVersion, lngVersion);
//		}
		wrapper.and(wq -> wq
			.eq(PlatformSysMsgEntity::getMsgGroup, ESysMsgGroup.A.toString()).or()
			.eq(PlatformSysMsgEntity::getMsgGroup, ESysMsgGroup.P.toString())
			.eq(PlatformSysMsgEntity::getTargetId, userId));

		if (clientType == PlatformOsTypeEnum.OS_ALL.getTypeValue()) {
			Integer[] arrayClientType = {PlatformOsTypeEnum.OS_ALL.getTypeValue(), PlatformOsTypeEnum.OS_ANDROID.getTypeValue(), PlatformOsTypeEnum.OS_IOS.getTypeValue()};
			wrapper.in(PlatformSysMsgEntity::getClientType, arrayClientType);
		} else {
			Integer[] arrayClientType = {PlatformOsTypeEnum.OS_ALL.getTypeValue(), clientType};
			wrapper.in(PlatformSysMsgEntity::getClientType, arrayClientType);
		}

		wrapper.orderByDesc(PlatformSysMsgEntity::getCreateTime);

		if (count != null && page != null) {
			wrapper.last("limit " + (page - 1) * count + "," + count);
		}

		return baseMapper.selectList(wrapper);
	}

	@Override
	public List<PlatformSysMsgEntity> findPopInformSysMsg(Long lngVersion, Long userId) {
		LambdaQueryWrapper<PlatformSysMsgEntity> wrapper = new QueryWrapper<PlatformSysMsgEntity>().lambda();
		wrapper.and(wq -> wq
			.eq(PlatformSysMsgEntity::getMsgGroup, ESysMsgGroup.A.toString()).or()
			.eq(PlatformSysMsgEntity::getMsgGroup, ESysMsgGroup.P.toString())
			.eq(PlatformSysMsgEntity::getTargetId, userId))
			.gt(PlatformSysMsgEntity::getUpdVersion, lngVersion)
			.eq(PlatformSysMsgEntity::getPopFlag, 1)
			.lt(PlatformSysMsgEntity::getPopInvalidTime, new Date())
			.orderByDesc(PlatformSysMsgEntity::getUpdVersion);
		return baseMapper.selectList(wrapper);
	}

	@Override
	public PlatformSysMsgEntity findInformSysMsgByMsgId(Long msgId) {
		return baseMapper.selectById(msgId);
	}

	@Override
	public PlatformSysMsgEntity findLastInformSysMsg(Long userId, Integer clientType) {
		Long[] arrayUserId = {userId, Constants.USERID_ALL_USER};
		LambdaQueryWrapper<PlatformSysMsgEntity> wrapper = new QueryWrapper<PlatformSysMsgEntity>().lambda();
		wrapper.in(PlatformSysMsgEntity::getTargetId, arrayUserId)
			.eq(PlatformSysMsgEntity::getStatus, 1)
			.eq(PlatformSysMsgEntity::getIsDeleted, 0)
			.eq(PlatformSysMsgEntity::getSendStatus, InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());

		if (clientType == PlatformOsTypeEnum.OS_ALL.getTypeValue()) {
			Integer[] arrayClientType = {PlatformOsTypeEnum.OS_ALL.getTypeValue(), PlatformOsTypeEnum.OS_ANDROID.getTypeValue(), PlatformOsTypeEnum.OS_IOS.getTypeValue()};
			wrapper.in(PlatformSysMsgEntity::getClientType, arrayClientType);
		} else {
			Integer[] arrayClientType = {PlatformOsTypeEnum.OS_ALL.getTypeValue(), clientType};
			wrapper.in(PlatformSysMsgEntity::getClientType, arrayClientType);
		}

		wrapper.orderByDesc(PlatformSysMsgEntity::getUpdVersion)
			.last("limit 1");
		return baseMapper.selectOne(wrapper);
	}

	@Override
	public List<PlatformSysMsgEntity> findAllUnsendSysMsg(Date date) {
		LambdaQueryWrapper<PlatformSysMsgEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(PlatformSysMsgEntity::getSendStatus, InformEnum.SendStatusEnum.NO_SEND.getTypeCode())
			.eq(PlatformSysMsgEntity::getSendWay, InformEnum.SendWayTimeEnum.TIMING.getTypeCode())
			.le(PlatformSysMsgEntity::getSendTime, date)
			.eq(BaseEntity::getStatus, 1)
			.eq(BaseEntity::getIsDeleted, 0);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public void pushUnsendSysMsg() {
		try {
			List<PlatformSysMsgEntity> list = findAllUnsendSysMsg(new Date());
			log.info("PlatformSysMsg实体类集合：{}", list);
			for (PlatformSysMsgEntity sysMsg : list) {
				log.info("pushUnsendSysMsg:{}", JSON.toJSONString(sysMsg));
				pushSysMsg(sysMsg);
			}
		} catch (Exception e) {
			log.error("发送定时系统通知异常", e);
		}
	}

	private void pushSysMsg(PlatformSysMsgEntity sysMsg) {
		log.info("进入推送消息方法：{}", sysMsg);
		NotifyMsg nm = new NotifyMsg();
		nm.setMsgId(IdUtil.getSnowflakeNextIdStr());
		nm.setSendWay(InformEnum.SendWayTimeEnum.TIMING.getTypeCode());
		nm.setDevType(sysMsg.getClientType());
		nm.setMsgGroup(sysMsg.getMsgGroup());
		nm.setDisplayGroup(MsgStaticType.DisplayGroup.SYSTEM_MSG);
		nm.setUrl(sysMsg.getUrl());

		boolean isAll = sysMsg.getMsgGroup().equals(Constants.SysMsgContants.SYS_MSG_GROUP_ALL);
		if (isAll) {
			//全站发送只有一条记录
			//TODO:全站推送根据多语言标签推
			nm.setTitle(sysMsg.getTitle());
			nm.setMsg(sysMsg.getContent());
		} else {
			//个人发送是多条记录中的一条
			if (null == sysMsg.getTargetId()) {
				log.info("sysMsg id:{} 个人发送targetId为空", sysMsg.getId());
				return;
			}
			nm.setUserIds(Arrays.asList(sysMsg.getTargetId()));
			CustDeviceEntity custDevice = custDeviceClient.getCustDevice(sysMsg.getTargetId());
			if (custDevice != null) {
				String lang = custDevice.getLang();
				if (lang.equals(CommonConstant.ZH_CN)) {
					nm.setTitle(sysMsg.getTitle());
					nm.setMsg(sysMsg.getContent());
				} else if (lang.equals(CommonConstant.EN_US)) {
					nm.setTitle(StringUtil.isNotBlank(sysMsg.getTitleEn()) ? sysMsg.getTitleEn() : sysMsg.getTitle());
					nm.setMsg(StringUtil.isNotBlank(sysMsg.getContentEn()) ? sysMsg.getContentEn() : sysMsg.getContent());
				} else {
					nm.setTitle(StringUtil.isNotBlank(sysMsg.getTitleHant()) ? sysMsg.getTitleHant() : sysMsg.getTitle());
					nm.setMsg(StringUtil.isNotBlank(sysMsg.getContentHant()) ? sysMsg.getContentHant() : sysMsg.getContent());
				}
			}
		}

		//发送
		pushNotifyMsgService.pushNotifyMsg(nm);

		PlatformSysMsgEntity update = new PlatformSysMsgEntity();
		update.setId(sysMsg.getId());
		//发送完成后需要将表中数据的状态修改
		update.setSendStatus(InformEnum.SendStatusEnum.SUCCESS_SEND.getTypeCode());
		update.setSendTime(new Date());
		//要同时更新版本号
		long updateVersion = sequenceService.nextId(Constants.SysMsgContants.SYS_MSG_VERSION);
		update.setUpdVersion(updateVersion);
		update.setUpdateTime(new Date());
		baseMapper.updateById(update);

		//修改未读消息数目
		if (isAll) {
			//对于全站类型的消息，需要修改所有用户的未读消息记录
			List<Long> allCustId = custInfoClient.allCustId();
			platformPushService.updateUnReadNum(allCustId, MsgStaticType.DisplayGroup.SYSTEM_MSG, sysMsg.getClientType());
		} else {
			platformPushService.updateUnReadNum(Arrays.asList(sysMsg.getTargetId()), MsgStaticType.DisplayGroup.SYSTEM_MSG, sysMsg.getClientType());
		}
	}

}
