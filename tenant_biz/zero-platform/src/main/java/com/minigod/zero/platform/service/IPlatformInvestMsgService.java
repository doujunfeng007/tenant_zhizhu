package com.minigod.zero.platform.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;
import com.minigod.zero.platform.vo.ShowMsg;

import java.util.Date;
import java.util.List;

/**
 * 推送记录表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-11
 */
public interface IPlatformInvestMsgService extends BaseService<PlatformInvestMsgEntity> {
//	/**
//	 * 自定义分页
//	 *
//	 * @param page
//	 * @param platformInvestMsg
//	 * @return
//	 */
//	IPage<PlatformInvestMsgVO> selectPlatformInvestMsgPage(IPage<PlatformInvestMsgVO> page, PlatformInvestMsgVO platformInvestMsg);

	List<ShowMsg> findInvestMsg2(Long userId, Integer[] messageGroups, Integer msgId, Integer page, Integer count);

	List<ShowMsg> findInvestMsg(Long userId, Integer clientType, Integer messageGroup, Long locateVersion, Integer page, Integer count);

	PlatformInvestMsgEntity findLastInvestMsg(Long userId, Integer messageGroup, Integer clientType);

	PlatformInvestMsgEntity findInvestMsgByMsgId(Long msgId);

	/**
	 * 查询用户推送消息记录
	 * @param userId
	 * @param msgCode
	 * @return
	 */
	PlatformInvestMsgEntity findInvestMsgForLastOne(Long userId, Integer msgCode);

	List<PlatformInvestMsgEntity> findAllUnsendInvestMsg(Date date);

	/**
	 * 扫描消息通知表，将定时发送类型中没有发送的数据发送出去
	 * @return
	 */
	void pushUnsendInvestMsg();
}
