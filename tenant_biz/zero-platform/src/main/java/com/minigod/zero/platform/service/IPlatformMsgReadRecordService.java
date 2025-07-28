package com.minigod.zero.platform.service;

import com.minigod.zero.platform.entity.PlatformMsgReadRecordEntity;

import java.util.List;
import java.util.Map;

public interface IPlatformMsgReadRecordService {

	/**
	 * 获取未读标志
	 */
	Map<Long, List<PlatformMsgReadRecordEntity>> findMsgReadRecord(List<Long> lstUserId, int msgCode);

	/**
	 * 获取未读标志
	 */
	PlatformMsgReadRecordEntity findMsgReadRecord(Long userId, int msgCode, Integer clientType);

	List<PlatformMsgReadRecordEntity> findUserMsgReadRecord(PlatformMsgReadRecordEntity query);

	List<PlatformMsgReadRecordEntity> findMsgReadRecords(Long userId, Integer[] messageGroups);

	Integer findUnReadMsgNum(Long userId, int msgCode, Long readVersion, Integer clientType);

	void updateMsgReadRecord(PlatformMsgReadRecordEntity msgReadRecord);

	void saveMsgReadRecords(List<PlatformMsgReadRecordEntity> lstMsgReadRecords);

	/**
	 * 获取是否有未读消息
	 * @param custId
	 * @return true有 false无
	 */
	boolean getIsMessageUnread(Long custId, Integer clientType);
}
