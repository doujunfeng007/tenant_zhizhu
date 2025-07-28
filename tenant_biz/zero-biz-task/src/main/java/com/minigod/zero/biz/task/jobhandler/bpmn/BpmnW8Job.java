package com.minigod.zero.biz.task.jobhandler.bpmn;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.bpmn.module.feign.IBpmnAccountClient;
import com.minigod.zero.bpmn.module.feign.IBpmnFundClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName: com.minigod.zero.biz.task.jobhandler.bpmn.BpmnW8Job
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/12/25 16:08
 * @Version: 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class BpmnW8Job {

	private final IBpmnAccountClient iBpmnAccountClient;

	@XxlJob("w8ConfirmJob")
	public void w8ConfirmJob() {
		Map<String, Object> map = StringUtil.isNotBlank(XxlJobHelper.getJobParam()) ? JSONObject.parseObject(XxlJobHelper.getJobParam()) : MapUtil.newHashMap();
		log.debug("withdrawToBankJob start");
		try {
			R r = iBpmnAccountClient.w8ConfirmTask(map);
			log.debug("withdrawToBankJob end");
			if (r.isSuccess()) {
				XxlJobHelper.handleSuccess();
			} else {
				XxlJobHelper.handleFail();
			}
		} catch (Exception e) {
			log.error("withdrawToBankJob error", e);
			XxlJobHelper.handleFail();
		}
	}
}
