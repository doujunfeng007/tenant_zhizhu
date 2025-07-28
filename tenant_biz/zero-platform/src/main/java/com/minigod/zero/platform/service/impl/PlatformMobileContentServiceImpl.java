package com.minigod.zero.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.platform.mapper.PlatformMobileContentMapper;
import com.minigod.zero.platform.service.PushMobileMsgService;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.platform.common.MobileMsg;
import com.minigod.zero.platform.entity.PlatformMobileContentEntity;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.service.IPlatformMobileContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlatformMobileContentServiceImpl extends BaseServiceImpl<PlatformMobileContentMapper, PlatformMobileContentEntity> implements IPlatformMobileContentService {

	private final PushMobileMsgService pushMobileMsgService;

	@Override
	public List<PlatformMobileContentEntity> findAllUnsendMobileMsg(Date date) {
		LambdaQueryWrapper<PlatformMobileContentEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(PlatformMobileContentEntity::getSendStatus, InformEnum.SendStatusEnum.NO_SEND.getTypeCode())
			.eq(PlatformMobileContentEntity::getSendType, InformEnum.SendWayTimeEnum.TIMING.getTypeCode())
			.le(PlatformMobileContentEntity::getTiming, date)
			.eq(BaseEntity::getStatus, 1)
			.eq(BaseEntity::getIsDeleted, 0);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public void pushUnsendMobileMsg() {
		Date now = new Date();
		List<PlatformMobileContentEntity> contentList = findAllUnsendMobileMsg(now);
		for (PlatformMobileContentEntity informMobileContent : contentList) {
			MobileMsg mobileMsg = new MobileMsg();
			mobileMsg.setMobile(informMobileContent.getCellPhone());
			mobileMsg.setMsgId(informMobileContent.getId());
			mobileMsg.setMessage(informMobileContent.getContent());
			mobileMsg.setChannel(informMobileContent.getChannel());
			informMobileContent.setSendStatus(InformEnum.SendStatusEnum.NO_SEND.getTypeCode());
			informMobileContent.setUpdateTime(now);
			baseMapper.updateById(informMobileContent);

			pushMobileMsgService.pushMobileMsg(mobileMsg);
		}
	}
}
