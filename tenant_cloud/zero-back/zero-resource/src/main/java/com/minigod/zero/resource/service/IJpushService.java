package com.minigod.zero.resource.service;

import com.minigod.zero.resource.dto.JPushDTO;
import com.minigod.zero.core.tool.api.R;

/**
 * 极光推送服务类
 * @author Zhe.Xiao
 */
public interface IJpushService {

	/**
	 * 给多个设备推送
	 * @param jPushDTO
	 * @return
	 */
	R saveAndPushMsgToList(JPushDTO jPushDTO);

	/**
	 * 推送消息给所有设备
	 * @param jPushDTO
	 * @return
	 */
	R saveAndPushMsgToApp(JPushDTO jPushDTO);

}
