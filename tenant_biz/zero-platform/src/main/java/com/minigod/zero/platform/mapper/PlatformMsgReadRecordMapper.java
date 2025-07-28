package com.minigod.zero.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.platform.entity.PlatformMsgReadRecordEntity;

import java.util.List;

/**
 * 消息读取记录表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-02-27
 */
public interface PlatformMsgReadRecordMapper extends BaseMapper<PlatformMsgReadRecordEntity> {
	void update(PlatformMsgReadRecordEntity msgReadRecord);

	void saveMsgReadRecords(List<PlatformMsgReadRecordEntity> lstMsgReadRecords);

	List<PlatformMsgReadRecordEntity> selectByParam(PlatformMsgReadRecordEntity msgReadRecordParam);
}
