package com.minigod.zero.bpmn.module.account.service;

/**
 * 客户资料修改同步服务
 *
 * @author eric
 * @since 2024-09-05 10:59:04
 */
public interface IAccountOpenInfoModifySyncService {
	/**
	 * 同步修改记录到正式表以及大账户中心
	 *
	 * @param applyId
	 * @param applicationId
	 */
	void sync(Long applyId, String applicationId);
}
