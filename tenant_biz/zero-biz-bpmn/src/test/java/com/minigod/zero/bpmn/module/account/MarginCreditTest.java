package com.minigod.zero.bpmn.module.account;

import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.bpmn.ZeroBpmnApplication;
import com.minigod.zero.bpmn.module.margincredit.feign.IMarginCreditClient;
import com.minigod.zero.bpmn.module.margincredit.vo.MarginCreditAdjustApplyDTO;
import com.minigod.zero.core.test.ZeroBootTest;
import com.minigod.zero.core.test.ZeroSpringExtension;
import com.minigod.zero.core.tool.api.R;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @ClassName MarginCreditTest.java
 * @Description TODO
 * @createTime 2024年03月12日 14:38:00
 */
@ExtendWith(ZeroSpringExtension.class)
@SpringBootTest(classes = ZeroBpmnApplication.class)
@ZeroBootTest(appName = "zero-biz-bpmn",profile="sit", enableLoader = true)
public class MarginCreditTest {

	@Autowired
	private IMarginCreditClient marginCreditClient;

	@Test
	public void sumbit() {
		MarginCreditAdjustApplyDTO dto =new MarginCreditAdjustApplyDTO();

		dto.setTradeAccount("C56518084");
		dto.setCapitalAccount("C56518084");
		dto.setClientName("测试");
		dto.setClientNameSpell("TEST");
		dto.setCustId(1000793l);
		List<MarginCreditAdjustApplyDTO.LineCreditInfo> lineCreditInfo =new ArrayList<>();
		MarginCreditAdjustApplyDTO.LineCreditInfo hk =new MarginCreditAdjustApplyDTO.LineCreditInfo();
		hk.setCurrency("HKD");
		hk.setLineCreditApply(new BigDecimal("1000"));
		hk.setLineCreditBefore(new BigDecimal("0"));
		lineCreditInfo.add(hk);
		dto.setLineCreditInfo(lineCreditInfo);
		R result =  marginCreditClient.submit(dto);
		System.out.println(JSONUtil.toCompatibleJson(result));
	}

}
