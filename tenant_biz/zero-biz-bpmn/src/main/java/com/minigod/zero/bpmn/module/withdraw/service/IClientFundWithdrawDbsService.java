package com.minigod.zero.bpmn.module.withdraw.service;

import java.util.Map;

/**
 * @ClassName: com.minigod.zero.bpmn.module.withdraw.service.IClientFundWithdrawDbsService
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/6/12 16:23
 * @Version: 1.0
 */
public interface IClientFundWithdrawDbsService {
	void doDbsAutoTransfer(Map<String, Object> map);
}
