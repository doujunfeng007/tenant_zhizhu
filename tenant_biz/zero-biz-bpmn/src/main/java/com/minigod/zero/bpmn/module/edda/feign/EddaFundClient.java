package com.minigod.zero.bpmn.module.edda.feign;

import com.minigod.zero.bpmn.module.dbs.DbsEddaFundBusinessService;
import com.minigod.zero.bpmn.module.dbs.DbsEddaInfoBusinessService;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaFundApplicationEntity;
import com.minigod.zero.bpmn.module.edda.entity.ClientEddaInfoApplicationEntity;
import com.minigod.zero.bpmn.module.edda.service.ClientEddaFundApplicationService;
import com.minigod.zero.bpmn.module.edda.service.ClientEddaInfoApplicationService;
import com.minigod.zero.bpmn.module.feign.IEddaFundClient;
import com.minigod.zero.core.tool.api.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: com.minigod.zero.bpmn.module.edda.feign.EddaFundClient
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/17 15:48
 * @Version: 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
public class EddaFundClient implements IEddaFundClient {


	@Autowired
	private ClientEddaFundApplicationService clientEddaFundApplicationService;
	@Autowired
	private DbsEddaFundBusinessService dbsEddaFundBusinessService;
	@Autowired
	private ClientEddaInfoApplicationService clientEddaInfoApplicationService;
	@Autowired
	private DbsEddaInfoBusinessService dbsEddaInfoBusinessService;

	@Override
	@PostMapping(FUND_PAYMENT_TASK)
	public R dbsEddaFundPaymentTask() {
		try {
			String jobName = "TASK-【入金】快捷入金资金实时划入-DBS-EDDA";
			log.info(jobName + "任务开始");
			List<ClientEddaFundApplicationEntity> eddaFundSendList = new ArrayList<>();
			eddaFundSendList = clientEddaFundApplicationService.queryNotSendBankFundList();
			log.info("edda 入金数据上送DBS:" + eddaFundSendList.size() + "条");
			for (ClientEddaFundApplicationEntity eddaFund : eddaFundSendList) {
				log.info("edda 入金上送DBS开始进行:" + eddaFund.getApplicationId() + "数据上送");
				dbsEddaFundBusinessService.sendEDDAFund(eddaFund, null);
			}
			log.info(jobName + "任务结束");
		} catch (Exception e) {
			return R.fail("快捷入金资金实时划入任务异常 "+e.getMessage());
		}
		return R.success();
	}

	@Override
	@PostMapping(INFO_BIND_TASK)
	public R dbsEddaInfoBindTask() {
		try {
			String jobName = "TASK-【入金】快捷入金上传授权申请-DBS-EDDA";
			log.info(jobName + "任务开始");
			List<ClientEddaInfoApplicationEntity> eddaInfoApplicationList = clientEddaInfoApplicationService.queryNotStateEddaListInfo();
			log.info("edda 上送数据:" + eddaInfoApplicationList.size() + "条");
			for (ClientEddaInfoApplicationEntity eddaInfo : eddaInfoApplicationList) {
				log.info("edda 开始进行:" + eddaInfo.getApplicationId() + "数据上送");
				//todo 工作流用户id
				dbsEddaInfoBusinessService.sendEDDAInitiation(eddaInfo, null);
			}
			log.info(jobName + "任务结束");
		} catch (Exception e) {
			return R.fail("快捷入金上传授权任务异常 "+e.getMessage());
		}
		return R.success();
	}
}
