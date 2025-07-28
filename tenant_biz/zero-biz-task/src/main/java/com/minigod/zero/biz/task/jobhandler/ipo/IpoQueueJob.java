package com.minigod.zero.biz.task.jobhandler.ipo;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.minigod.zero.trade.feign.IIpoApplyClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class IpoQueueJob {

	@Resource
	private IIpoApplyClient ipoApplyClient;


	/** 0/30 * * * * ? **/
	@XxlJob("IpoQueueApplys")
	public void apply() {
		try{
			log.info("融资排队任务开始");
			//lo再ng beginTime = System.currentTimeMillis();
			ipoApplyClient.subQueueApply();
			//long endTime = System.currentTimeMillis();
			//log.debug(">>> 融资排队任务 end, spend={} ms", (endTime - beginTime));
			XxlJobHelper.handleSuccess();
		} catch (Exception e) {
			log.error("融资排队任务异常", e);
			XxlJobHelper.handleFail();
		}

	}

	/** 0 0/10 * * * ? **/
	@XxlJob("IpoQueueCancel")
	public void cancel() {
		try{
			log.info("融资排队撤销申购任务开始");
			//long beginTime = System.currentTimeMillis();
			ipoApplyClient.cancelQueueApply();
			//long endTime = System.currentTimeMillis();
			//log.debug(">>> 融资排队撤销申购任务 end, spend={} ms", (endTime - beginTime));
			XxlJobHelper.handleSuccess();
		} catch (Exception e) {
			log.error("融资排队撤销申购任务异常", e);
			XxlJobHelper.handleFail();
		}

	}
}
