package com.minigod.zero.platform.service;

import com.minigod.zero.platform.entity.PlatformSysMsgEntity;
import com.minigod.zero.core.mp.base.BaseService;

import java.util.Date;
import java.util.List;

/**
 * 系统通知信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-11
 */
public interface IPlatformSysMsgService extends BaseService<PlatformSysMsgEntity> {

//	/**
//	 * 自定义分页
//	 *
//	 * @param page
//	 * @param platformSysMsg
//	 * @return
//	 */
//	IPage<PlatformSysMsgVO> selectPlatformSysMsgPage(IPage<PlatformSysMsgVO> page, PlatformSysMsgVO platformSysMsg);

	List<PlatformSysMsgEntity> findInformSysMsg(Long lngVersion, Integer intCount, Long userId, Integer clientType);

	List<PlatformSysMsgEntity> findInformSysMsg(Long lngVersion, Integer page, Integer intCount, Long userId, Integer clientType);

	/**
	 * 返回当前用户的APP弹窗系统消息
	 * @param lngVersion
	 * @param userId
	 * @return
	 */
	List<PlatformSysMsgEntity> findPopInformSysMsg(Long lngVersion, Long userId);

	PlatformSysMsgEntity findInformSysMsgByMsgId(Long msgId);

	PlatformSysMsgEntity findLastInformSysMsg(Long userId, Integer clientType);

	List<PlatformSysMsgEntity> findAllUnsendSysMsg(Date date);

	void pushUnsendSysMsg();
}
