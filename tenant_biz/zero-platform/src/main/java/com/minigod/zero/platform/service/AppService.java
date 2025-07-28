package com.minigod.zero.platform.service;

import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.dto.MsgDetailDTO;
import com.minigod.zero.platform.dto.PlatformOpenCommonDTO;
import com.minigod.zero.platform.dto.SysMsgDTO;
import com.minigod.zero.platform.dto.UnreadMsgDTO;

/**
 * @author Zhe.Xiao
 */
public interface AppService {

	/**
	 * 获取消息分组
	 * @return
	 */
	R getMessageGroup(CommonReqVO<PlatformOpenCommonDTO> req);

	/**
	 * 获取未读消息
	 * @param req
	 * @return
	 */
	R updateAndFindUnReadMsg(CommonReqVO<UnreadMsgDTO> req);

	/**
	 * 获取用户系统通知
	 * @param req
	 * @return
	 */
	R findSystemMessage(CommonReqVO<SysMsgDTO> req);

	/**
	 * 获取用户APP弹窗系统通知
	 * @param req
	 * @return
	 */
	R findAppPopSystemMessage(CommonReqVO<SysMsgDTO> req);

	/**
	 * 消息详情
	 * @param req
	 * @return
	 */
	R getMessageDetail(CommonReqVO<MsgDetailDTO> req);

	/**
	 * 获取当前是否有未读消息
	 * @param req
	 * @return
	 */
	R getIsMessageUnread(CommonReqVO<PlatformOpenCommonDTO> req);
}
