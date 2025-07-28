package com.minigod.zero.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.platform.entity.PlatformPushMsgHistoryEntity;

import java.util.List;

/**
 * 推送消息历史表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-02-27
 */
public interface PlatformPushMsgHistoryMapper extends BaseMapper<PlatformPushMsgHistoryEntity> {

//	/**
//	 * 自定义分页
//	 *
//	 * @param page
//	 * @param pushMsgHistory
//	 * @return
//	 */
//	List<PushMsgHistoryVO> selectPushMsgHistoryPage(IPage page, PushMsgHistoryVO pushMsgHistory);

	void saveNotifyMsg(List<PlatformPushMsgHistoryEntity> msgSends);

	void updateByMsgId(PlatformPushMsgHistoryEntity historyEntity);


}
