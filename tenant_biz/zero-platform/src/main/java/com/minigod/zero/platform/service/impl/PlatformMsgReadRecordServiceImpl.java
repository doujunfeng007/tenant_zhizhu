package com.minigod.zero.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.google.common.collect.Lists;
import com.minigod.zero.platform.mapper.PlatformInvestMsgMapper;
import com.minigod.zero.platform.mapper.PlatformMsgReadRecordMapper;
import com.minigod.zero.platform.service.IPlatformMsgReadRecordService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;
import com.minigod.zero.platform.entity.PlatformMsgReadRecordEntity;
import com.minigod.zero.platform.enums.PlatformOsTypeEnum;
import com.minigod.zero.platform.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息模板扩展表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-02-27
 */
@Service
@RequiredArgsConstructor
public class PlatformMsgReadRecordServiceImpl extends BaseServiceImpl<PlatformMsgReadRecordMapper, PlatformMsgReadRecordEntity> implements IPlatformMsgReadRecordService {

	private final PlatformInvestMsgMapper platformInvestMsgMapper;

	@Override
	public Map<Long, List<PlatformMsgReadRecordEntity>> findMsgReadRecord(List<Long> lstUserId, int msgCode) {
		Map<Long, List<PlatformMsgReadRecordEntity>> mapMsgReadRecord = new HashMap<>();

		// 检查用户 ID 列表是否为空
		if (CollectionUtil.isEmpty(lstUserId)) {
			return mapMsgReadRecord;
		}

		// 查询消息阅读记录
		List<PlatformMsgReadRecordEntity> lstMsgReadRecords = new LambdaQueryChainWrapper<>(baseMapper)
			.in(PlatformMsgReadRecordEntity::getUserId, lstUserId)
			.eq(PlatformMsgReadRecordEntity::getMsgCode, msgCode)
			.eq(PlatformMsgReadRecordEntity::getStatus, 1)
			.eq(PlatformMsgReadRecordEntity::getIsDeleted, 0)
			.list();

		// 如果没有找到记录，直接返回
		if (CollectionUtil.isEmpty(lstMsgReadRecords)) {
			return mapMsgReadRecord;
		}

		// 将消息阅读记录按用户 ID 分组
		for (PlatformMsgReadRecordEntity msgReadRecord : lstMsgReadRecords) {
			mapMsgReadRecord
				.computeIfAbsent(msgReadRecord.getUserId(), k -> new ArrayList<>())
				.add(msgReadRecord);
		}

		return mapMsgReadRecord;
	}

	@Override
	public PlatformMsgReadRecordEntity findMsgReadRecord(Long userId, int msgCode, Integer clientType) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.eq(PlatformMsgReadRecordEntity::getUserId, userId)
			.eq(PlatformMsgReadRecordEntity::getMsgCode, msgCode)
			.eq(PlatformMsgReadRecordEntity::getStatus, 1)
			.eq(PlatformMsgReadRecordEntity::getIsDeleted, 0)
			.eq(PlatformMsgReadRecordEntity::getClientType, clientType)
			.one();
	}

	@Override
	public List<PlatformMsgReadRecordEntity> findUserMsgReadRecord(PlatformMsgReadRecordEntity query) {
		if (null == query) {
			return Lists.newArrayList();
		}

		LambdaQueryWrapper<PlatformMsgReadRecordEntity> queryWrapper = new LambdaQueryWrapper<>();

		if (null != query.getUserId()) {
			queryWrapper.eq(PlatformMsgReadRecordEntity::getUserId,query.getUserId());
		}
		if (null != query.getStatus()) {
			queryWrapper.eq(PlatformMsgReadRecordEntity::getStatus,query.getStatus());
		}
		if (null != query.getMsgCode()) {
			queryWrapper.eq(PlatformMsgReadRecordEntity::getMsgCode,query.getMsgCode());
		}
		queryWrapper.gt(PlatformMsgReadRecordEntity::getUnreadNum, 0);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<PlatformMsgReadRecordEntity> findMsgReadRecords(Long userId, Integer[] messageGroups) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.eq(PlatformMsgReadRecordEntity::getUserId, userId)
			.in(PlatformMsgReadRecordEntity::getMsgCode, messageGroups)
			.eq(PlatformMsgReadRecordEntity::getStatus, 1)
			.eq(PlatformMsgReadRecordEntity::getIsDeleted, 0)
			.list();
	}

	@Override
	public Integer findUnReadMsgNum(Long userId, int msgCode, Long readVersion, Integer clientType) {
		//未读消息数目为发给本人的+发给全站用户的
		Long[] arrayUserId = {userId, Constants.USERID_ALL_USER};
		Integer[] arrayClientType = {clientType, PlatformOsTypeEnum.OS_ALL.getTypeValue()};

		int intNum = new LambdaQueryChainWrapper<>(platformInvestMsgMapper)
			.in(PlatformInvestMsgEntity::getUserId, arrayUserId)
			.gt(PlatformInvestMsgEntity::getUpdVersion, readVersion)
			.eq(PlatformInvestMsgEntity::getDisplayGroup, msgCode)
			.eq(PlatformInvestMsgEntity::getStatus, 1)
			.eq(PlatformInvestMsgEntity::getIsDeleted, 0)
			.in(PlatformInvestMsgEntity::getClientType, arrayClientType)
			.count().intValue();

		return intNum;
	}

	@Override
	public void updateMsgReadRecord(PlatformMsgReadRecordEntity msgReadRecord) {
		baseMapper.update(msgReadRecord);
	}

	@Override
	public void saveMsgReadRecords(List<PlatformMsgReadRecordEntity> lstMsgReadRecords) {
		baseMapper.saveMsgReadRecords(lstMsgReadRecords);
	}

	@Override
	public boolean getIsMessageUnread(Long custId, Integer clientType) {
		List<PlatformMsgReadRecordEntity> list = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(PlatformMsgReadRecordEntity::getUserId, custId)
			.gt(PlatformMsgReadRecordEntity::getUnreadNum, 0)
			.eq(PlatformMsgReadRecordEntity::getStatus, 1)
			.eq(PlatformMsgReadRecordEntity::getIsDeleted, 0)
			.eq(PlatformMsgReadRecordEntity::getClientType, clientType)
			.list();
		return CollectionUtil.isNotEmpty(list);

	}
}
