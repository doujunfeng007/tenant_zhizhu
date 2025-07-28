package com.minigod.zero.bpmn.module.fundTrans;

import com.minigod.zero.bpmn.ZeroBpmnApplication;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransInfo;
import com.minigod.zero.bpmn.module.fundTrans.feign.IFundTransClient;
import com.minigod.zero.bpmn.module.fundTrans.service.ClientFundTransInfoService;
import com.minigod.zero.ca.bo.sz.response.ResultSzCa;
import com.minigod.zero.core.test.ZeroBootTest;
import com.minigod.zero.core.test.ZeroSpringExtension;
import com.minigod.zero.core.tool.api.R;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@ExtendWith(ZeroSpringExtension.class)
@SpringBootTest(classes = ZeroBpmnApplication.class)
@ZeroBootTest(appName = "zero-biz-bpmn",profile="fund", enableLoader = true)
public class FundTransTest {
	@Autowired
	private IFundTransClient fundTransClient;
	@Test
	public void fundTransWithdrawJob() {
		R r =  fundTransClient.fundTransWithdrawJob(new HashMap<>());
		System.out.println(r.toString());
	}

	@Test
	public void fundTransDepositJob() {
		R r =  fundTransClient.fundTransDepositJob(new HashMap<>());
		System.out.println(r.toString());
	}


}
