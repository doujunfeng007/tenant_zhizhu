package com.minigod.zero.platform.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.common.NotifyMsg;
import com.minigod.zero.platform.dto.SaveMessageReqDTO;
import com.minigod.zero.platform.dto.SendNotifyDTO;

import java.util.List;

/**
 * 消息中心推送服务
 *
 * @author
 */
public interface IPlatformPushService {

	/**
	 * 发送推送通知
	 * @param req
	 * @return
	 */
	R sendSysMsgNew(SendNotifyDTO req);

	/**
	 * 保存服务通知，股价系统等非投资圈消息
	 *
	 * @param req
	 */
	void saveInformMessage(SendNotifyDTO req);

	/**
	 * 保存信息到数据库更新未读条数及透传
	 * 对saveMessage方法的扩展，增加对全站用户(用户分类)的定时发送的支持
	 *
	 * @param req
	 */
	void saveMessageExt(SaveMessageReqDTO req);

	R pushNotifyMsg(NotifyMsg notifyMsg);

	/**
	 * 更新未读条数
	 * @param lstUserId
	 */
	void updateUnReadNum(List<Long> lstUserId, int messageGroup, Integer clientType);

}
