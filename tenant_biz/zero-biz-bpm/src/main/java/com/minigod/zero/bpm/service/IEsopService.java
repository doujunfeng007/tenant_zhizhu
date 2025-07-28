package com.minigod.zero.bpm.service;

import com.minigod.zero.core.tool.api.R;

public interface IEsopService {

	/**
	 * 点击邮件链接激活ekeeper
	 *
	 * @param params
	 * @return
	 */
	R<String> ekeeperActive(String params);
}
