package com.minigod.zero.resource.service;

import com.minigod.zero.resource.dto.UpushDTO;
import com.minigod.zero.core.tool.api.R;

/**
 * 友盟推送服务类
 *
 * @author Zhe.Xiao
 */
public interface IUpushService {
	/**
	 * 指定设备发送
	 *
	 * @param upushDTO
	 * @return
	 */
	R sendByDevice(UpushDTO upushDTO);

	/**
	 * 广播
	 *
	 * @param upushDTO
	 * @return
	 */
	R sendByApp(UpushDTO upushDTO);

	/**
	 * 指定标签发送
	 *
	 * @param upushDTO
	 * @return
	 */
	R sendByTags(UpushDTO upushDTO);
}
