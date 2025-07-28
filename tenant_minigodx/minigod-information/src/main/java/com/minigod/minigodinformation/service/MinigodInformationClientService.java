package com.minigod.minigodinformation.service;

import java.util.Date;

/**
 * @ClassName: com.minigod.minigodinformation.service.MinigodInformationClientService
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/11/7 14:22
 * @Version: 1.0
 */
public interface MinigodInformationClientService {
	/**
	 * 获取交易所通知
	 */
	void syExchangeAnnouncement(Date startTime, Date endTime);

	/**
	 * 获取披露公告
	 */
	void syExchangeDisclosureAnnouncement(Date startTime, Date endTime);
}
