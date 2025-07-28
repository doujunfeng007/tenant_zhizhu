package com.minigod.zero.cust.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.cust.entity.CustResetLogEntity;

/**
 * 客户密码重置日志 服务类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
public interface ICustResetLogService extends BaseService<CustResetLogEntity> {

	/**
	 * 保存客户密码重置日志
	 * @param custId 用户号
	 * @param restType 0-暂不处理 1-交易密码 2-登录密码
	 */
	void saveLog(Long custId, Integer restType);

}
