package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.cust.feign.ICustInfoClient;
import com.minigod.zero.manage.dto.InvestMsgSearchReqDTO;
import com.minigod.zero.manage.dto.SendInvestMsgReqDTO;
import com.minigod.zero.manage.enums.ShowTypeEnum;
import com.minigod.zero.manage.mapper.PlatformInvestMsgMapper;
import com.minigod.zero.manage.service.IPlatformInvestMsgService;
import com.minigod.zero.platform.constants.Constants;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 推送记录表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-11
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PlatformInvestMsgServiceImpl extends BaseServiceImpl<PlatformInvestMsgMapper, PlatformInvestMsgEntity> implements IPlatformInvestMsgService {

	private final ICustInfoClient custInfoClient;
	private final IPlatformMsgClient platformMsgClient;

	@Override
	public void saveUpdateAndSendMsg(SendInvestMsgReqDTO sendInvestMsgReqDTO) {
		ZeroUser zeroUser = AuthUtil.getUser();
		if (zeroUser == null) {
			throw new ZeroException("用户未登录");
		}
		List<Long> userIdLongs = Lists.newArrayList();
		boolean isSend = true;

		//触达用户类型：0=所有用户，1=特定用户，2=渠道白名单，3=渠道黑名单
		if (sendInvestMsgReqDTO.getShowType().equals(ShowTypeEnum.ALL.getCode())) {
			//发送给全站用户
			userIdLongs = Arrays.asList(Constants.USERID_ALL_USER);
		} else if (sendInvestMsgReqDTO.getShowType().equals(ShowTypeEnum.SPECIFIC.getCode())) {
			//发送给特定用户
			Long[] userIds = sendInvestMsgReqDTO.getUserIds();
			if (null != userIds && userIds.length != 0) {
				userIdLongs = Arrays.asList(userIds);
			} else {
				isSend = false;
			}
		} else if (sendInvestMsgReqDTO.getShowType().equals(ShowTypeEnum.CHANNEL_WHITE.getCode())) {
			String[] channelIds = sendInvestMsgReqDTO.getChannelIds();
			if (null != channelIds && channelIds.length != 0) {
				List<Long> userIdList = custInfoClient.findCustInfoInChannels(channelIds);
				if (!CollectionUtils.isEmpty(userIdList)) {
					userIdLongs = userIdList;
				} else {
					isSend = false;
				}
			}
		} else if (sendInvestMsgReqDTO.getShowType().equals(ShowTypeEnum.CHANNEL_BLACK.getCode())) {
			String[] channelIds = sendInvestMsgReqDTO.getChannelIds();
			if (null != channelIds && channelIds.length != 0) {
				List<Long> userIdList = custInfoClient.findUserInfoNotInChannels(channelIds);
				if (!CollectionUtils.isEmpty(userIdList)) {
					userIdLongs = userIdList;
				} else {
					isSend = false;
				}
			}
		}

		if (CollectionUtils.isEmpty(userIdLongs)) {
			log.info("保存并发送服务通知 获取userIdLongs为空");
		}
		SendNotifyDTO bean = new SendNotifyDTO();
		bean.setClientType(sendInvestMsgReqDTO.getClientType());
		//目标用户(列表)
		bean.setLstToUserId(userIdLongs);
		//发送方单个用户（1000用户代表系统）
		bean.setFromUserId(Constants.USERID_ALL_USER);
		//消息分类
		bean.setDisplayGroup(sendInvestMsgReqDTO.getDisplayGroup());
		//通知页面消息标题
		bean.setTitle(sendInvestMsgReqDTO.getTitle());
		//通知页面消息内容
		bean.setMsgContent(sendInvestMsgReqDTO.getContent());

		if (sendInvestMsgReqDTO.getShowType().equals(ShowTypeEnum.ALL.getCode())) {
			//A:全站  P：个人
			bean.setMsgGroup(InformEnum.MsgGroupEnum.ALL.getTypeCode());
		} else {
			//A:全站  P：个人
			bean.setMsgGroup(InformEnum.MsgGroupEnum.PERSON.getTypeCode());
		}
		//0:即时 1：定时
		bean.setSendWay(sendInvestMsgReqDTO.getSendWay());
		//推送时间
		bean.setSendTime(sendInvestMsgReqDTO.getSendTime());
		//跳转链接
		bean.setUrl(sendInvestMsgReqDTO.getUrl());
		// 租户ID
		bean.setTenantId(AuthUtil.getTenantId());
		bean.setPushType(sendInvestMsgReqDTO.getSendType());
		if (isSend) {
			platformMsgClient.sendNotify(bean);
		}
	}

	@Override
	public IPage<PlatformInvestMsgEntity> getInvestMsgList(IPage page, InvestMsgSearchReqDTO dto) {
		LambdaQueryWrapper<PlatformInvestMsgEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Objects.nonNull(dto.getUserId()), PlatformInvestMsgEntity::getUserId, dto.getUserId());
		wrapper.eq(StringUtils.isNotEmpty(dto.getId()), PlatformInvestMsgEntity::getId, dto.getId());
		wrapper.eq(StringUtils.isNotEmpty(dto.getTitle()), PlatformInvestMsgEntity::getTitle, dto.getTitle());
		wrapper.eq(StringUtils.isNotEmpty(dto.getMsgGroup()), PlatformInvestMsgEntity::getMsgGroup, dto.getMsgGroup());
		wrapper.eq(StringUtils.isNotEmpty(dto.getClientType()), PlatformInvestMsgEntity::getClientType, dto.getClientType());
		wrapper.ge(StringUtils.isNotEmpty(dto.getSendTimeStart()), PlatformInvestMsgEntity::getSendTime, dto.getSendTimeStart());
		wrapper.le(StringUtils.isNotEmpty(dto.getSendTimeEnd()), PlatformInvestMsgEntity::getSendTime, dto.getSendTimeEnd());
		wrapper.eq(BaseEntity::getIsDeleted, 0)
			.eq(BaseEntity::getStatus, 1)
			//系统通知单独显示
			.ne(PlatformInvestMsgEntity::getDisplayGroup, MsgStaticType.DisplayGroup.SYSTEM_MSG)
			.orderByDesc(PlatformInvestMsgEntity::getCreateTime);
		log.info("-->查询消息中心消息历史列表,确认是否可以自动增加tenant_id过虑条件!!!");
		return baseMapper.selectPage(page, wrapper);
	}
}
