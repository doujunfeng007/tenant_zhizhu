package com.minigod.zero.bpmn.module.dbs;

import com.minigod.zero.bpmn.module.edda.entity.ClientEddaFundApplicationEntity;

/**
 * @ClassName: com.minigod.zero.bpmn.module.dbs.DbsEddaFundBusinessService
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/13 19:11
 * @Version: 1.0
 */
public interface DbsEddaFundBusinessService {
	void sendEDDAFund(ClientEddaFundApplicationEntity eddaFund, String o);





}
